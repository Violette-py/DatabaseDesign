package com.example.springdatajpatest;

import com.example.springdatajpatest.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDataJpaTestApplication {
    @Autowired
    private DatabaseService databaseService;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaTestApplication.class, args);
//        databaseService.insertRoomDataFromCsv();
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            databaseService.insertRoomDataFromCsv();
            databaseService.insertStudentDataFromCsv();
        };
    }

}
