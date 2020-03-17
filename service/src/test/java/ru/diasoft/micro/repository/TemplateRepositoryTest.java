package ru.diasoft.micro.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QSort;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import ru.diasoft.micro.TemplateApplication;
import ru.diasoft.micro.model.QTemplate;
import ru.diasoft.micro.model.Template;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TemplateApplication.class})
public class TemplateRepositoryTest {

    @Autowired
    private TemplateRepository repo;

    @Before
    public void init() {
        repo.deleteAll();
    }
    
    @Test
    public void userFindByUserNameTest() {
        Template firstEntity = new Template();
        firstEntity.setTestKey("first");
        firstEntity.setTestInt(100);
        repo.save(firstEntity);

        Template secondEntity = new Template();
        secondEntity.setTestKey("second");
        secondEntity.setTestInt(101);
        repo.save(secondEntity);
        
        Optional<Template> checkEntity = repo.findOne(QTemplate.template.testKey.eq("second"));
        Assert.assertEquals(secondEntity, checkEntity.orElse(new Template()));
        
        checkEntity = repo.findOne(QTemplate.template.testKey.eq("none"));
        Assert.assertEquals(Optional.empty(), checkEntity);
    }

    @Test
    public void crudTest() {
        Template newEntity = new Template();
        newEntity.setTestKey("newEntity");
        newEntity.setTestInt(1);        
        Template createdEntity = repo.save(newEntity);
        
        Optional<Template> foundEntity = repo.findById(createdEntity.getId()); 
        Assert.assertEquals(newEntity, foundEntity.orElse(new Template()));
        
        foundEntity.ifPresent(t -> t.setTestInt(200));
        if(foundEntity.isPresent()) {
            repo.save(foundEntity.get());
        }
        
        foundEntity = repo.findById(createdEntity.getId());
        Assert.assertEquals(newEntity.getTestKey(), foundEntity.orElse(new Template()).getTestKey());
        Assert.assertNotEquals(newEntity.getTestInt(), foundEntity.orElse(new Template()).getTestInt());
        
        repo.delete(foundEntity.get());
        foundEntity = repo.findOne(QTemplate.template.testKey.eq("newEntity"));
        Assert.assertEquals(Optional.empty(), foundEntity);
    }
    
    @Test
    public void templateFindByParamsTest() throws Exception {
        LocalDate currentDate = LocalDate.now(); 
        
        Template entity1 = repo.save(new Template(null, "first", 100, new BigDecimal(10.1).setScale(10, BigDecimal.ROUND_HALF_UP), currentDate));
        Template entity2 = repo.save(new Template(null, "second", 101, new BigDecimal(11.1).setScale(10, BigDecimal.ROUND_HALF_UP), currentDate));
        Template entity3 = repo.save(new Template(null, "third", 101, new BigDecimal(12.1).setScale(10, BigDecimal.ROUND_HALF_UP), currentDate));
        Template entity4 = repo.save(new Template(null, "first_additional", 102, new BigDecimal(10.1).setScale(10, BigDecimal.ROUND_HALF_UP), currentDate.minusDays(1)));

        Predicate predicate = new BooleanBuilder();
        Pageable pageable = PageRequest.of(0, 10);

        assertThat(repo.findAll(predicate, pageable)).contains(entity1, entity2, entity3, entity4);
        
        //Paging
        pageable = PageRequest.of(0, 1);
        assertThat(repo.findAll(predicate, pageable)).hasSize(1);
        
        //Search by testKey and asc sorting by TemplateID
        QSort sort = new QSort(QTemplate.template.id.asc());
        pageable = PageRequest.of(0, 10, sort);
        predicate = QTemplate.template.testKey.eq("first");
        assertThat(repo.findAll(predicate, pageable)).hasSize(1).containsExactly(entity1);
        
        //Search by testInt and asc sorting by TemplateID
        pageable = PageRequest.of(0, 10, sort);
        predicate = QTemplate.template.testInt.eq(101);
        assertThat(repo.findAll(predicate, pageable)).hasSize(2).containsExactly(entity2, entity3);

        //Search by testInt between 100 and 101 and asc sorting by TemplateID
        pageable = PageRequest.of(0, 10, sort);
        predicate = QTemplate.template.testInt.between(100, 101);
        assertThat(repo.findAll(predicate, pageable)).hasSize(3).containsExactly(entity1, entity2, entity3);

        //Search by like expression "first%"
        predicate = QTemplate.template.testKey.like("first%");
        assertThat(repo.findAll(predicate, pageable)).hasSize(2).containsExactly(entity1, entity4);
        
        //Search by currentDate
        predicate = QTemplate.template.testDate.eq(currentDate);
        assertThat(repo.findAll(predicate, pageable)).hasSize(3).containsExactly(entity1, entity2, entity3);

        //Search all templates with testDate before currentDate
        predicate = QTemplate.template.testDate.before(currentDate);
        assertThat(repo.findAll(predicate, pageable)).hasSize(1).containsExactly(entity4);
        
        //Search by testBalance > 10.1
        predicate = QTemplate.template.testBalance.gt(new BigDecimal(10.2));
        assertThat(repo.findAll(predicate, pageable)).hasSize(2).containsExactly(entity2, entity3);
    }

}
