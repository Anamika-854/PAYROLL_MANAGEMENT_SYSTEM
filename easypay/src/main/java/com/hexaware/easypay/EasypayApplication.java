package com.hexaware.easypay;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.hexaware.easypay",
    "com.hexaware.easypay.security",
    "com.hexaware.easypay.config"
})
public class EasypayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasypayApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
}
