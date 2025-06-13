package com.Hazavao.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.Hazavao.demo.PojaGenerated;

@SpringBootApplication
@PojaGenerated 
public class PojaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PojaApplication.class, args);
    }
}