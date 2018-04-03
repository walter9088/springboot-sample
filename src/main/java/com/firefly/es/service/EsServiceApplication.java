package com.firefly.es.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableAutoConfiguration
public class EsServiceApplication {

    public static void main(String[] args){
        SpringApplication.run(EsServiceApplication.class,args);
    }

}
