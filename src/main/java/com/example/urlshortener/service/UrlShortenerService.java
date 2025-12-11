package com.example.urlshortener.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UrlShortenerService {
    private final DynamoDbClient dynamoDbClient;
    private static final String TABLE_NAME = "ShortUrls";

    public UrlShortenerService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public String shortenUrl(String originalUrl) {
        String shortCode = UUID.randomUUID().toString().substring(0, 6); // random 6-char code

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("shortCode", AttributeValue.builder().s(shortCode).build());
        item.put("originalUrl", AttributeValue.builder().s(originalUrl).build());
        item.put("createdAt", AttributeValue.builder().s(Instant.now().toString()).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .conditionExpression("attribute_not_exists(shortCode)") // prevent overwriting
                .build();

        try {
            String existingShortCode = findExistingShortCode(originalUrl);
            if (existingShortCode != null) {
                return  existingShortCode;
            }
            dynamoDbClient.putItem(request);
            return shortCode;
        } catch (ConditionalCheckFailedException e) {
            return shortenUrl(originalUrl); // retry if collision
        }
    }

    //check enteries before inserting
    private String findExistingShortCode(String originalUrl) {
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":url", AttributeValue.builder().s(originalUrl).build());

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(TABLE_NAME)
                .filterExpression("originalUrl = :url")
                .expressionAttributeValues(expressionValues)
                .build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);

        if (!response.items().isEmpty()) {
            return response.items().get(0).get("shortCode").s();
        }
        return null;
    }

    public String getOriginalUrl(String shortCode) {
        Map<String, AttributeValue> key = Map.of(
                "shortCode", AttributeValue.builder().s(shortCode).build()
        );

        GetItemRequest request = GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build();

        GetItemResponse response = dynamoDbClient.getItem(request);

        if (!response.hasItem() || response.item().isEmpty()) {
            return "No URL found for short code: " + shortCode;
        }

        return response.item().get("originalUrl").s();
    }
}
