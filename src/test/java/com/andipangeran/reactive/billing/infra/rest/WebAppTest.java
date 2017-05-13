package com.andipangeran.reactive.billing.infra.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAutoConfiguration
@ComponentScan(
    basePackages = {"com.andipangeran.reactive.billing.*", "com.andipangeran.reactive.billing.infra.rest.*"}
)
public class WebAppTest {

    public static void main(String[] args) {
        SpringApplication.run(WebAppTest.class, args);
    }
}
