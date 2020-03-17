package ru.diasoft.micro.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ru.diasoft.micro.model.Template;
import ru.diasoft.micro.service.TemplateServiceImplPrimary;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TemplateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TemplateServiceImplPrimary service;

    @Test
    public void templateFindByIdTest() throws Exception {
        Template template = new Template(Long.valueOf(1000), "0004", 103,
                new BigDecimal(10.1).setScale(10, BigDecimal.ROUND_HALF_UP), LocalDate.now());
        when(service.tempalteFindById(template.getId())).thenReturn(ResponseEntity.ok(template));
        
        mvc.perform(get("/v1/template/1000"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(template.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.testKey").value(template.getTestKey()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.testInt").value(template.getTestInt()));
    }
}
