package ru.diasoft.micro.service;

import static ru.diasoft.micro.util.ServiceConstants.TEMPLATE_CREATION_ERROR;
import static ru.diasoft.micro.util.ServiceConstants.TEMPLATE_MODIFICATION_ERROR;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import lombok.RequiredArgsConstructor;
import ru.diasoft.micro.mdp.lib.utils.exception.APIException;
import ru.diasoft.micro.config.aop.Loggable;
import ru.diasoft.micro.model.QTemplate;
import ru.diasoft.micro.model.Template;
import ru.diasoft.micro.repository.TemplateRepository;
import ru.diasoft.micro.rest.TemplateService;

@RequiredArgsConstructor
@Primary
@Service
@Loggable
public class TemplateServiceImplPrimary implements TemplateService {

    private final Logger logger = LogManager.getLogger(this.getClass());
    
    private final TemplateRepository templateRepository;
    private final MessageSource messageSource;
    
    @Override
    public ResponseEntity<?> hello(String name) {
        if(logger.isDebugEnabled()) {
            logger.debug(messageSource.getMessage("template.example", new Object[] {name}, LocaleContextHolder.getLocale()));        
        }
        String message = "Hello, " + name + "!";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @Override
    public ResponseEntity<?> ping() {
        if(logger.isDebugEnabled()) {
            logger.debug("ping in action!");        
        }
        
        return ResponseEntity.status(HttpStatus.OK).body("ping OK");
    }

    @Override
    public ResponseEntity<?> templateCreate(Template template) {
        String testKey = template.getTestKey();        
        Optional<Template> foundEntity = templateRepository.findOne(QTemplate.template.testKey.eq(testKey));        
        if(foundEntity.isPresent()) {
            throw new APIException(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Template with testKey " + testKey + " is already exists.", TEMPLATE_CREATION_ERROR);
        }        
        Template createdEntity = templateRepository.save(template);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity) ;
    }

    @Override
    public ResponseEntity<?>templateFindByParams(Predicate predicate, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(templateRepository.findAll(predicate, pageable));
    }
    
    @Override
    public ResponseEntity<Template> tempalteFindById(Long templateId) {
        return ResponseEntity.status(HttpStatus.OK).body(templateRepository.findById(templateId).orElse(null)) ;
    }

    @Override
    public ResponseEntity<?> templateModify(Long templateId, Template template) {
        Optional<Template> foundTemplate = templateRepository.findById(templateId);
        Template result = null;        
        if(foundTemplate.isPresent()) {
            template.setId(templateId);
            result = templateRepository.save(template);
        } else {
            throw new APIException(HttpStatus.NOT_FOUND.value(), "Template is not found by Identifier = [" + templateId + "]", TEMPLATE_MODIFICATION_ERROR);
        }        
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
