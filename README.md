# gcpemulator
This is for creating gcp emulator pubsub/spanner

How to work with Google emulators pubsub/spanner

1. Overview

This document provides step-by-step instructions for setting up Google Cloud Spanner and Pub/Sub emulators locally on macOS and Windows. 
The guide includes all the tools, configurations, sample code, and commands used during the development process in a Spring Boot application.

2. Prerequisites

Install the following tools:
- Java 21 (OpenJDK)
- Maven 3.9+
- Docker Desktop (latest)
- IntelliJ IDEA (or your preferred IDE)
- Google Cloud SDK 
Create virtual GCP project 

gcloud config set project test-project.   (name here is test-project)
3. Setting Up Pub/Sub Emulator
3.1 Using Docker:
Run the following command to start the Pub/Sub emulator:

docker run -d \
  -p 8085:8085 \
  --name pubsub-emulator \
  google/cloud-sdk:latest \
  gcloud beta emulators pubsub start --host-port=0.0.0.0:8085
export pubsub_EMULATOR_HOST=localhost:8085


3.2 to create topic for this
curl -X PUT http://localhost:8085/v1/projects/test-project/topics/test-topic    // Topic name is test-topic


4. Setting Up spanner Emulator

Start the emulator:- 

docker run --rm -d \
  -p 9010:9010 -p 9020:9020 \
  --name spanner-emulator \
  gcr.io/cloud-spanner-emulator/emulator

export SPANNER_EMULATOR_HOST=localhost:9010

gcloud config set api_endpoint_overrides/spanner http://localhost:9020/

create a db instance
gcloud spanner instances create test-instance \
  --config=emulator-config \
  --description="Test Spanner Emulator Instance" \
  --nodes=1



Create db

gcloud spanner databases create test-db --instance=test-instance


create table

gcloud spanner databases ddl update test-db \ 
  --instance=test-instance \
  --ddl="CREATE TABLE Users (UserId STRING(36) NOT NULL, Name STRING(100), Email STRING(100)) PRIMARY KEY (UserId);"

---
# 🧪 Local Google Cloud Emulator Setup (Windows)

This guide will help you configure **Google Cloud Pub/Sub** and **Cloud Spanner** emulators locally using Docker and the `gcloud` CLI on **Windows CMD**.

---

## 🔧 Step 1: Set Your GCP Project

```cmd
gcloud config set project test-project
```

## 📦 Step 2: Install Required gcloud Components

```cmd
gcloud components install beta
gcloud components install pubsub-emulator (optional)
```

## 🚀 Step 3: Start Pub/Sub Emulator (Docker)
```cmd
docker run -d -p 8085:8085 --name pubsub-emulator ^
  google/cloud-sdk:latest gcloud beta emulators pubsub start --host-port=0.0.0.0:8085
  ```
- If the container is already created, just start it:
```cmd
docker start pubsub-emulator
```
- Set the environment variable for the emulator:
```cmd
set PUBSUB_EMULATOR_HOST=localhost:8085
curl -X PUT http://localhost:8085/v1/projects/test-project/topics/test-topic
```

## 🧪 Step 4: Start Spanner Emulator (Docker)
```cmd
docker run --rm -d -p 9010:9010 -p 9020:9020 ^
  --name spanner-emulator gcr.io/cloud-spanner-emulator/emulator
```
- Set the Spanner emulator host variable:
```cmd
set SPANNER_EMULATOR_HOST=localhost:9010
```

## ⚙️ Step 5: Configure gcloud to Use Emulator
```cmd
gcloud config set api_endpoint_overrides/spanner http://localhost:9020/
gcloud config set auth/disable_credentials true
```

## 🛠️ Step 6: Create Spanner Instance and Database
```cmd
gcloud spanner instances create test-instance ^
  --config=emulator-config ^
  --description="Test Spanner Emulator Instance" ^
  --nodes=1
```
```cmd
gcloud spanner databases create test-db --instance=test-instance
```
```cmd
gcloud spanner databases ddl update test-db --instance=test-instance --ddl="CREATE TABLE Users (UserId STRING(36) NOT NULL, Name STRING(100), Email STRING(100)) PRIMARY KEY (UserId);"
```
