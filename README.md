# URL Shortener – Spring Boot + AWS DynamoDB (Local / Cloud)

A lightweight, production-ready URL Shortening backend service built using Spring Boot and Amazon DynamoDB.
This project exposes clean REST APIs for creating and resolving shortened URLs.

## Features

Shorten long URLs into unique short codes

Resolve short codes back to original URLs

Store mappings in DynamoDB (supports AWS Cloud & DynamoDB Local)

Error handling (duplicate URL, missing records, invalid input)

Stateless REST API design

Easily deployable to any cloud platform (Render / Railway / EC2 / LocalStack)

## Tech Stack 

| Component  | Technology                    |
| ---------- | ----------------------------- |
| Backend    | Spring Boot 3+, Java 17       |
| Database   | AWS DynamoDB / DynamoDB Local |
| SDK        | AWS Java SDK v2               |
| Build Tool | Maven                         |

## DynamoDB Table Structure

| Attribute     | Type                   | Description                            |
| ------------- | ---------------------- | -------------------------------------- |
| `shortCode`   | String (Partition Key) | Unique shortcode generated for the URL |
| `originalUrl` | String                 | Actual full URL                        |

## API Endpoints

#### 1. Create Short URL
POST /api/shorten

Params:
url: Original URL to shorten

Response:``Shortened URL code: <shortCode>``

