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
   - Set the handler to:  
     ```
     com.samardash.lamda.LambdaHandler
     ```

4. **API Gateway & Custom Domain**
   - Connect your Lambda to API Gateway.
   - Set up a custom domain in API Gateway.
   - Create an **A record** in Route 53 pointing to your API Gateway domain.

---

## 📝 Example Lambda Event

```json
{
  "resource": "/{proxy+}",
  "path": "/api/v1/hello",
  "httpMethod": "GET",
  "headers": {
    "Accept": "application/json"
  },
  "body": null,
  "isBase64Encoded": false
}
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
