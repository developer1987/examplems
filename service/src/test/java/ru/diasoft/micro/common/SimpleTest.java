package ru.diasoft.micro.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ru.diasoft.micro.rest.TemplateController;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SimpleTest {

    @Autowired
    private TemplateController controller;

    @Test
    @WithMockUser(authorities = {"dca"})
    public void testGreetingWithMockUser() {
        String result = (String) controller.hello("User").getBody();
        assertEquals("Hello, User!", result);
    }
    
}
