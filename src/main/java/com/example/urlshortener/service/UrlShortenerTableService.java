package com.example.urlshortener.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@Service
public class UrlShortenerTableService {
    private final DynamoDbClient dynamoDbClient;

    public UrlShortenerTableService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void CreateTable(){
        String tableName = "ShortUrls";
        try{
           CreateTableRequest request = CreateTableRequest.builder().tableName(tableName)
                   .keySchema(KeySchemaElement.builder()
                           .attributeName("shortCode")
                           .keyType(KeyType.HASH)
                           .build())
                   .attributeDefinitions(AttributeDefinition.builder()
                           .attributeName("shortCode")
                           .attributeType(ScalarAttributeType.S)
                           .build())
                   .provisionedThroughput(ProvisionedThroughput.builder()
                           .readCapacityUnits(5L)
                           .writeCapacityUnits(5L)
                           .build())
                   .build();

           dynamoDbClient.createTable(request);
           System.out.println("Table Created Succesfully: "+tableName);

        } catch (ResourceInUseException e){
            System.out.println("Table already exists: "+tableName);
        } catch (Exception e) {
            System.out.println("Error creating table: "+e.getMessage());
        }
    }
}
