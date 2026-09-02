package com.dumboj;

import com.dumboj.case1.model.AiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AiProperties.class)
public class HelloLangchainApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloLangchainApplication.class, args);
    }

}
