# 🏋️‍♂️ Fitness AI Monolith Backend

A robust, monolithic Spring Boot backend designed to track user fitness activities and generate personalized, highly-structured AI coaching recommendations using the Llama-3 model via Groq and Spring AI.

## 🚀 Tech Stack
* **Framework:** Spring Boot 3.x
* **Language:** Java 17+
* **Database:** MySQL & Spring Data JPA (Hibernate)
* **Security:** Spring Security with Stateless JWT Authentication
* **AI Integration:** Spring AI (Groq / Llama-3.3-70b-versatile)
* **API Documentation:** OpenAPI / Swagger UI

---

## 🛠️ Local Setup Instructions

Follow these steps to get the project running on your local machine.

### 1. Prerequisites
* Java 17 or higher installed.
* Maven installed.
* A running instance of MySQL.
* A free [Groq API Key](https://console.groq.com/keys).

### 2. Database Configuration
Create a new MySQL database for the application:
```sql
CREATE DATABASE fitness_db;


### 3. Environment Variables
This project relies on a .env file for secure credentials. Create a file named .env in the root directory of the project and add the following:

Code snippet
DB_PASSWORD=your_mysql_database_password
OPENAI_API_KEY=gsk_your_groq_api_key_here
4. Build and Run
Open your terminal, navigate to the project root, and run:

Bash
mvn clean install
mvn spring-boot:run
The server will start on http://localhost:8080.

📖 API Documentation & Testing (Swagger)
Once the application is running, you can interact with all APIs directly through the Swagger UI:

🔗 Swagger UI URL: http://localhost:8080/swagger-ui/index.html

🔐 How to Authenticate in Swagger
Use the Register or Login endpoints below to generate a JWT token.

Copy the token from the response.

Scroll to the top of the Swagger page and click the green Authorize button.

Paste your token (prefix with Bearer  if required by your configuration) and click Authorize.

🔌 Core API Endpoints
1. User Authentication
Register a New User

Endpoint: POST /api/auth/register

JSON Body:

JSON
{
  "email": "testuser@example.com",
  "password": "Password123!",
  "firstName": "John",
  "lastName": "Doe"
}
Login

Endpoint: POST /api/auth/login

JSON Body:

JSON
{
  "email": "testuser@example.com",
  "password": "Password123!"
}
Expected Response:

JSON
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
2. Activity Tracking (Requires JWT)
Log a New Workout

Endpoint: POST /api/activities

JSON Body:

JSON
{
  "type": "RUNNING",
  "duration": 45,
  "caloriesBurned": 450
}
Expected Response:

JSON
{
  "id": "b302532b-6448-43fe-a057-645e18f16d2a",
  "type": "RUNNING",
  "duration": 45,
  "caloriesBurned": 450,
  "createdAt": "2026-04-20T12:00:00Z"
}
3. AI Coaching Recommendation (Requires JWT)
Generate AI Feedback for an Activity

Endpoint: POST /api/recommendation/generate-ai/{activityId}

Path Variable: Paste the id from the Activity response above.

No JSON Body Required.

Expected Response (Structured JSON from Groq LLM):

JSON
{
  "id": "804c8265-8836-45a5-baf0-ddde79ee70f5",
  "type": "AI_GENERATED",
  "recommendation": "Great job on completing your 45-minute run and burning 450 calories, be sure to stretch and foam roll your legs to aid in recovery.",
  "improvements": [
    "Focus on landing midfoot or forefoot instead of heel striking to reduce impact and improve efficiency."
  ],
  "suggestions": [
    "Try a 30-minute cycling workout tomorrow to give your running muscles a break and work on cardiovascular endurance."
  ],
  "safety": [
    "Make sure to stay hydrated by drinking plenty of water before, during, and after your runs to prevent dehydration and reduce the risk of injury."
  ],
  "createdAt": "2026-04-20T12:05:00Z",
  "updatedAt": "2026-04-20T12:05:00Z"
}