package ru.diasoft.micro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;

import ru.diasoft.micro.model.Template;
import ru.diasoft.micro.repository.TemplateRepository;
import ru.diasoft.micro.service.TemplateServiceImplPrimary;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TemplateServiceTest {

    @Mock
    private TemplateRepository repo;

    @Mock
    private MessageSource messageSource;

    @Autowired
    private TemplateServiceImplPrimary service;
    
    private Template template;

    @Before
    public void init() {
        service = new TemplateServiceImplPrimary(repo, messageSource);
        
        template = new Template();
        template.setId(Long.valueOf(1000));
        template.setTestKey("new");
        template.setTestInt(100);
        template.setTestBalance(new BigDecimal(100.10).setScale(10, BigDecimal.ROUND_HALF_UP));
        template.setTestDate(LocalDate.now());
        
        when(repo.save(template)).thenReturn(template);
        when(repo.findById(template.getId())).thenReturn(Optional.of(template));
    }
    
    @Test
    public void templateCreateTest() throws Exception {
        Template createdEntity = (Template) service.templateCreate(template).getBody();
        assertThat(createdEntity).isEqualTo(template);
    }
    
    @Test
    public void templateFindByIdTest() throws Exception {
        Template foundEntity = (Template) service.tempalteFindById(template.getId()).getBody();
        assertThat(foundEntity).isEqualTo(template);
    }

    @Test
    public void templateModifyTest() throws Exception {
        Template modifiedEntity = (Template) service.templateModify(template.getId(), template).getBody();
        assertThat(modifiedEntity).isEqualTo(template);
    }
}
