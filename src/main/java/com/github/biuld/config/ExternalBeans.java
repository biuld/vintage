package com.github.biuld.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalBeans {

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
