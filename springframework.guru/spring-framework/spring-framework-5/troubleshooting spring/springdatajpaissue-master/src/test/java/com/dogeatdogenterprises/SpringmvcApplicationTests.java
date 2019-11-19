package com.dogeatdogenterprises;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;

// sfg version: DEPRECATED
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringmvcApplication.class)
@WebAppConfiguration

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpringmvcApplicationTests {

    @Test
    public void contextLoads() {
    }

}
