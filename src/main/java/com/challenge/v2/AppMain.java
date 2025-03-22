package com.challenge.v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AppMain {

    public static void main(String[] args) {
    	SpringApplication.run(AppMain.class, args);
    }

}