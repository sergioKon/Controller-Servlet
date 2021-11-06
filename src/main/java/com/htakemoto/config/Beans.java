package com.htakemoto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
	
    @Bean
    public AccessLog accessLog() {
        return new AccessLog();
    }
	

}
