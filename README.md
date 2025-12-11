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

Response: ``Shortened URL code: <shortCode>``

#### 2. Get Original URL
GET /api/redirect/{shortCode}

Response: ``https://original-url.com/some-path``
If shortCode is not found → returns 404 with error message.

## How to Run with DynamoDB Local

#### 1. Install DynamoDB Local (if not done)
``mkdir dynamodb-local<br>
cd dynamodb-local<br>
wget https://s3.us-west-2.amazonaws.com/dynamodb-local/dynamodb_local_latest.zip<br>
unzip dynamodb_local_latest.zip``
#### 2. Start DynamoDB Local
``java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb``  

It will run on: ``http://localhost:8000``
#### 3. Update application.properties
``aws.region=ap-south-1<br>
aws.dynamodb.endpoint=http://localhost:8000<br>
aws.accessKey=fakeAccessKey<br>
aws.secretKey=fakeSecretKey``
#### 4. Create Table
You can use AWS CLI:
``aws dynamodb create-table \<br>
    --table-name UrlTable \<br>
    --attribute-definitions AttributeName=shortCode,AttributeType=S \<br>
    --key-schema AttributeName=shortCode,KeyType=HASH \<br>
    --billing-mode PAY_PER_REQUEST \<br>
    --endpoint-url http://localhost:8000``
## How to Run the Application
``mvn spring-boot:run``

## Project Structure
``src/<br>
 ├── main/java/com/example/urlshortener/<br>
 │     ├── controller/        # REST Controllers<br>
 │     ├── service/           # Business Logic<br>
 │     ├── model/             # DynamoDB Entity<br>
 │     └── config/            # AWS DynamoDB configuration<br>
 └── main/resources/<br>
       └── application.properties
``
