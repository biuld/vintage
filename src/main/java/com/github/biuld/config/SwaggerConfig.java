package com.github.biuld.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket bkApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.biuld.controller.backstage"))
                .paths(PathSelectors.any())
                .build()
                .groupName("后台管理")
                .securitySchemes(Lists.newArrayList(apiKey()));
    }

    @Bean
    public Docket frApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.biuld.controller.frontend"))
                .paths(PathSelectors.any())
                .build()
                .groupName("前台管理")
                .securitySchemes(Lists.newArrayList(apiKey()));
    }

    private ApiKey apiKey() {
        return new ApiKey("apikey", "x-access-token", "header");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("vintage API文档")
                .description("Hello, world!")
                .version("1.0")
                .build();
    }
}
