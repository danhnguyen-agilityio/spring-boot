package com.agility.usermanagement;


import com.agility.usermanagement.configs.OktaClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataInitialize implements ApplicationRunner {

    @Autowired
    private OktaClientProperties oktaClientProperties;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}
