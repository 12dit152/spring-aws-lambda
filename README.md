# 🚀 Spring Boot AWS Lambda Hello World

**Author:** Samar Dash

Welcome to the **Spring Boot AWS Lambda Hello World** project!  
This project demonstrates how to run a modern Spring Boot application as a serverless AWS Lambda function, seamlessly integrated with API Gateway and exposed via a custom domain.

---

## 🌟 Live Demo

**Test the live API:**  
[https://api.samardash.com/api/v1/hello](https://api.samardash.com/api/v1/hello)

**Sample Response:**
```json
{"message": "Hello from AWS Lambda"}
```

---

## 🛠️ Tech Stack

- **Spring Boot 3**
- **AWS Lambda** (Java 21)
- **API Gateway**
- **AWS Route 53** (for custom domain)
- **Maven** (with Shade Plugin for Lambda compatibility)
- **aws-serverless-java-container-springboot3**

---

## 🏗️ How It Works

1. **Spring Boot App**  
   REST endpoints are built as usual with Spring Boot.

2. **AWS Lambda**  
   The app is packaged as a Lambda function using the [aws-serverless-java-container](https://github.com/awslabs/aws-serverless-java-container) library, which simulates a servlet environment inside Lambda (no Tomcat needed!).

3. **API Gateway**  
   API Gateway receives HTTP requests and forwards them as Lambda events.

4. **Custom Domain with Route 53**  
   - A custom domain (`api.samardash.com`) is set up in API Gateway.
   - Route 53 provides an **A record** pointing to the API Gateway domain.

---

## 🌍 Public Endpoints

- **Custom Domain:**  
  [https://api.samardash.com/api/v1/hello](https://api.samardash.com/api/v1/hello)

- **Direct Lambda URL:**  
  [https://wz7rge7od3.execute-api.eu-west-1.amazonaws.com/dev/api/v1/hello](https://wz7rge7od3.execute-api.eu-west-1.amazonaws.com/dev/api/v1/hello)

---

## 🚦 Quick Start

1. **Clone the repo**
   ```sh
   git clone https://github.com/yourusername/spring-lambda-hello.git
   cd spring-lambda-hello
   ```

2. **Build the project**
   ```sh
   mvn clean package
   ```

3. **Deploy to AWS Lambda**
   - Upload the shaded JAR from `target/` to your Lambda function.

---

## 🔀 Deployment Options

### Option 1: AWS API Gateway (Default)

**Handler:** `com.samardash.lamda.LambdaHandler`

**Setup:**
1. Connect your Lambda to API Gateway
2. Set up custom domain in API Gateway
3. Create A record in Route 53

**Sample Test Event:**
```json
{
  "resource": "/{proxy+}",
  "path": "/api/v1/hello",
  "httpMethod": "GET",
  "headers": {
    "Accept": "application/json"
  },
  "queryStringParameters": null,
  "body": null,
  "isBase64Encoded": false,
  "requestContext": {
    "stage": "dev",
    "requestId": "test-request-id"
  }
}
```

### Option 2: Kong Gateway (Direct)

**Handler:** `com.samardash.lamda.KongLambdaHandler`

**Kong Configuration:**
```yaml
services:
- name: lambda-service
  url: https://lambda.us-east-1.amazonaws.com/2015-03-31/functions/your-function/invocations
  
routes:
- name: lambda-route
  service: lambda-service
  paths:
  - /api/v1/hello

plugins:
- name: aws-lambda
  service: lambda-service
  config:
    aws_key: your-aws-key
    aws_secret: your-aws-secret
    aws_region: us-east-1
    function_name: your-function-name
```

**Sample Kong Event:**
```json
{
  "request": {
    "method": "GET",
    "uri": "/api/v1/hello",
    "headers": {
      "accept": "application/json"
    }
  }
}
```

### Option 3: Kong Gateway with API Gateway Compatibility

**Handler:** `com.samardash.lamda.LambdaHandler` (same as Option 1)

**Kong Configuration with Request Transformer:**
```yaml
services:
- name: lambda-service
  url: https://lambda.us-east-1.amazonaws.com/2015-03-31/functions/your-function/invocations

routes:
- name: lambda-route
  service: lambda-service
  paths:
  - /api/v1/hello

plugins:
- name: request-transformer
  service: lambda-service
  config:
    add:
      body:
      - 'httpMethod:$(request.method)'
      - 'path:$(request.uri)'
      - 'headers:$(request.headers)'
      - 'queryStringParameters:$(request.query_params)'
      - 'body:$(request.body)'
      - 'isBase64Encoded:false'
      - 'requestContext.stage:prod'
    
- name: aws-lambda
  service: lambda-service
  config:
    aws_key: your-aws-key
    aws_secret: your-aws-secret
    aws_region: us-east-1
    function_name: your-function-name
    forward_request_method: true
    forward_request_uri: true
    forward_request_headers: true
```

**Benefits of Option 3:**
- Uses existing `LambdaHandler` (no code changes)
- Kong transforms requests to API Gateway format
- Full compatibility with Spring Boot serverless container

---

---

## 🧪 Testing

**Local Testing:**
```bash
mvn spring-boot:run
curl http://localhost:8080/api/v1/hello
```

**Lambda Console Testing:**
Use the sample events above in AWS Lambda console test function.

**Kong Testing:**
```bash
curl -X GET http://your-kong-gateway/api/v1/hello
```

---

## 💡 Why Serverless Spring Boot?

- **No servers to manage**
- **Auto-scaling**
- **Pay only for usage**
- **Easy integration with AWS ecosystem**

---

## 📬 Feedback

Feel free to open issues or PRs for improvements!  
Enjoy serverless Spring Boot 🚀

---
