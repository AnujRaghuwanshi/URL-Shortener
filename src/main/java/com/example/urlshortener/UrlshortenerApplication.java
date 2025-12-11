package com.example.urlshortener;

import com.example.urlshortener.service.DynamoDbTestService;
import com.example.urlshortener.service.UrlShortenerTableService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlshortenerApplication implements CommandLineRunner {

    private final UrlShortenerTableService tableService;
    private final DynamoDbTestService testService;

    public UrlshortenerApplication(UrlShortenerTableService tableService, DynamoDbTestService testService) {
        this.tableService = tableService;
        this.testService = testService;
    }


    public static void main(String[] args) {
        SpringApplication.run(UrlshortenerApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        tableService.CreateTable();
        testService.listTables();
    }
}
