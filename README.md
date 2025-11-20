# 🚀 Spring Boot AWS Lambda Hello World

**Author:** Samar Dash

Welcome to the **Spring Boot AWS Lambda Hello World** project!  
This project demonstrates how to run a modern Spring Boot application as a serverless AWS Lambda function with comprehensive logging, environment profiles, and AWS Parameter Store integration.

---

## 🌟 Live Demo

**Test the live API:**  
[https://api.samardash.com/api/v1/hello](https://api.samardash.com/api/v1/hello)

**Chat Interface:**  
[https://api.samardash.com/](https://api.samardash.com/)

---

## 🛠️ Tech Stack

- **Spring Boot 3.5** (Java 21)
- **AWS Lambda** with custom logging and correlation IDs
- **API Gateway** with custom domain
- **AWS Parameter Store** for environment-specific configuration
- **Spring Actuator** with custom endpoints
- **Modern Chat UI** with glassmorphism design
- **Maven Shade Plugin** for Lambda optimization

---

## 🏗️ Architecture

1. **Spring Boot REST API**  
   - `/api/v1/hello` - Hello World endpoint with system info
   - `/api/v1/post` - HTTP request inspector (like httpbin.org)
   - `/api/v1/actuator/health` - Health check endpoint
   - `/` - Modern chat interface

2. **AWS Lambda Integration**  
   - Uses `aws-serverless-java-container-springboot3`
   - Custom correlation ID logging with AWS request IDs
   - Environment-specific configuration via Parameter Store

3. **Logging & Monitoring**  
   - Structured HTTP request/response logging
   - AWS correlation ID integration
   - Environment-aware configuration
   - Comprehensive request inspection

---

## 🌍 Available Endpoints

- **Hello World:** `GET /api/v1/hello` - System information and active profile
- **Request Inspector:** `POST /api/v1/post` - HTTP request details in JSON format
- **Health Check:** `GET /api/v1/actuator/health` - Application health status
- **Chat Interface:** `GET /` - Modern AI-style chat UI

**Live URLs:**
- [https://api.samardash.com/api/v1/hello](https://api.samardash.com/api/v1/hello)
- [https://api.samardash.com/](https://api.samardash.com/)

---

## 🚦 Quick Start

1. **Clone and build**
   ```sh
   git clone https://github.com/yourusername/spring-lambda-hello.git
   cd spring-lambda-hello
   mvn clean package
   ```

2. **Local development**
   ```sh
   mvn spring-boot:run
   # Access: http://localhost:8080/api/v1/hello
   ```

3. **Deploy to AWS Lambda**
   - Upload `target/spring-lambda-hello-1.0-SNAPSHOT.jar`
   - Set handler: `com.samardash.lambda.LambdaHandler`
   - Set environment: `SPRING_PROFILES_ACTIVE=prod`

---

## ⚙️ Configuration

### Environment Profiles

- **Local:** `application-local.properties` - Full logging, all actuator endpoints
- **Dev:** `application-dev.properties` - Debug logging, all endpoints
- **Prod:** `application-prod.properties` - Minimal logging, health only

### AWS Parameter Store Integration

Parameters are loaded from `/myapp/{profile}/` path:

```bash
# Store parameters
aws ssm put-parameter --name "/myapp/prod/database.password" --value "secret123" --type "SecureString"
aws ssm put-parameter --name "/myapp/prod/api.key" --value "abc123" --type "String"
```

### IAM Permissions

Ensure Lambda execution role has permissions for CloudWatch Logs and SSM Parameter Store access.

---

## 🧪 Testing

**Sample Lambda Test Events:**

*GET /api/v1/hello:*
```json
{
  "resource": "/api/v1/hello",
  "path": "/api/v1/hello",
  "httpMethod": "GET",
  "headers": {"Accept": "application/json"},
  "requestContext": {"stage": "dev", "requestId": "test-123"},
  "isBase64Encoded": false
}
```

*POST /api/v1/post:*
```json
{
  "resource": "/api/v1/post",
  "path": "/api/v1/post",
  "httpMethod": "POST",
  "headers": {"Content-Type": "application/json"},
  "queryStringParameters": {"query": "test-value"},
  "requestContext": {"stage": "dev", "requestId": "test-123", "identity": {"sourceIp": "127.0.0.1"}},
  "body": "{\"message\":\"Hello from test\"}",
  "isBase64Encoded": false
}
```

## 🎨 Features

- **Modern Chat UI** - Glassmorphism design with mobile responsiveness
- **Comprehensive Logging** - HTTP request/response logging with correlation IDs
- **Environment Profiles** - Local, dev, prod configurations
- **Parameter Store** - AWS SSM integration for secrets management
- **Health Monitoring** - Spring Actuator with custom endpoints
- **Request Inspection** - HTTP request details in JSON format

---

## 🚀 Why This Stack?

- **Serverless** - No infrastructure management
- **Cost Effective** - Pay per request
- **Scalable** - Auto-scaling with AWS Lambda
- **Observable** - Comprehensive logging and monitoring
- **Secure** - Parameter Store for secrets

---

## 📬 Feedback

Feel free to open issues or PRs for improvements!  
Enjoy modern serverless Spring Boot 🚀
