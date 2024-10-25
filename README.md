## Wallet API

This is a Wallet API project developed using Spring Boot, providing functionalities to manage wallets, including creating wallets, checking balances, depositing, withdrawing, and transferring funds.

## Table of Contents
Prerequisites
Clone the Repository
Running the Application
Postman Collection
Technical Comments

## Prerequisites
Make sure you have the following installed on your machine:

Java 17 or higher
Maven
Git

## Clone the Repository
To clone the repository, run:
git clone git@github.com:gabocara/recargapay_wallet.git

## Running the Application
./mvnw spring-boot:run

Alternatively, if you prefer to run the application as a JAR file, package it first:
./mvnw clean package
java -jar target/recargapay_wallet-0.0.1-SNAPSHOT.jar

## Postman Collection
You can use the following Postman collection to test the API endpoints. Make sure to create wallets for users with IDs 1 and 2 before executing the requests.

{
  "info": {
    "name": "Wallet API Collection",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Create Wallet",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"userId\": 1\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/wallet/create",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "wallet", "create"]
        }
      }
    },
    {
      "name": "Get Wallet Balance",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/wallet/1/balance",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "wallet", "1", "balance"]
        }
      }
    },
    {
      "name": "Get Historical Wallet Balance",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "http://localhost:8080/api/wallet/1/balance/historical?timestamp=2024-10-25T10:00:00",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "wallet", "1", "balance", "historical"],
          "query": [
            {
              "key": "timestamp",
              "value": "2024-10-25T10:00:00"
            }
          ]
        }
      }
    },
    {
      "name": "Deposit to Wallet",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"walletId\": 1,\n    \"amount\": 100.00\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/wallet/deposit",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "wallet", "deposit"]
        }
      }
    },
    {
      "name": "Withdraw from Wallet",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"walletId\": 1,\n    \"amount\": 50.00\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/wallet/withdraw",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "wallet", "withdraw"]
        }
      }
    },
    {
      "name": "Transfer between Wallets",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n    \"fromWalletId\": 1,\n    \"toWalletId\": 2,\n    \"amount\": 25.00\n}"
        },
        "url": {
          "raw": "http://localhost:8080/api/wallet/transfer",
          "protocol": "http",
          "host": ["localhost"],
          "port": "8080",
          "path": ["api", "wallet", "transfer"]
        }
      }
    }
  ]
}

## OpenAPI Documentation
The OpenAPI documentation for this API is enabled and can be accessed at the following link:
http://localhost:8080/swagger-ui/index.html

## Technical Comments
Message Queues for Scalability: It is a good practice to use message queues to improve scalability; however, this was not implemented due to time constraints for the challenge.
Embedded H2 Database: An embedded H2 database was used for testing purposes, but in a production environment, I would recommend using a relational database for better data integrity and performance.
Caching: A caching mechanism was implemented to enhance the performance of repeated queries.

## Conclusion
This Wallet API provides a robust solution for managing wallets and transactions, ensuring that users can easily deposit, withdraw, and transfer funds. For any further questions or contributions, please feel free to reach out!
