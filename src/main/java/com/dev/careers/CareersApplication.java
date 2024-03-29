package com.dev.careers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author junehee
 */
@EnableCaching
@SpringBootApplication
public class CareersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareersApplication.class, args);
    }
}
