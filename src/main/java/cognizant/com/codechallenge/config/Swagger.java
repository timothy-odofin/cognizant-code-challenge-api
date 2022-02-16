/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cognizant.com.codechallenge.config;

/**
 *
 * @author JIDEX
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class Swagger {

      private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Cognizant Application Programmable Interface").description("")
                .termsOfServiceUrl("https://cognizant.com")
                .contact(new springfox.documentation.service.Contact("Cognizant", "https://cognizant.com", "admin@cognizant.com"))
                .license("Open Source").licenseUrl("https://cognizant.com").version("1.0.0").build();
    }

    @Bean
    public Docket api() {
        Set<String> producesAndConsumes = new HashSet<>();
        producesAndConsumes.add("application/json");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .produces(producesAndConsumes)
                .consumes(producesAndConsumes)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cognizant.com.codechallenge.route"))
                .paths(PathSelectors.any()).build();

    }

}
