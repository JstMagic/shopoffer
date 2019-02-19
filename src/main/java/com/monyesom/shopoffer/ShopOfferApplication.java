package com.monyesom.shopoffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableJpaRepositories
@EnableAutoConfiguration
@EnableScheduling
public class ShopOfferApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopOfferApplication.class, args);
    }

}
