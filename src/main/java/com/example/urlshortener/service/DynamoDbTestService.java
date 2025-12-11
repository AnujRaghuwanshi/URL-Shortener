package com.example.urlshortener.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

@Service
public class DynamoDbTestService {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDbTestService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void listTables(){
        ListTablesResponse res = dynamoDbClient.listTables();
        System.out.println("✅ DynamoDB Tables: " + res.tableNames());
    }
}
