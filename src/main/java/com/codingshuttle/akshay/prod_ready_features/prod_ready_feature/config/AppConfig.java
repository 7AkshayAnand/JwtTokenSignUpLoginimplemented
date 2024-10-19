package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration

public class AppConfig {
    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }

}