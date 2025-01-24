package com.volvo.emspdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class EmspDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmspDemoApplication.class, args);
    }

}
