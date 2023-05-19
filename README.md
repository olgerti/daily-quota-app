# Quota App
The Quota App is a simple application that allows you to manage quotes and their associated tags. 
It provides functionality to create, retrieve, update, and delete quotes. 
One of the key features of the app is the ability to generate a daily quote based on the 
priority of quotes with more likes.

## Prerequisites
To get started with the Quota App:

* Java Development Kit (JDK) 11 or higher installed
* Gradle build tool installed
* MySQL database installed

## Setup
1. Clone the repository:
`git clone https://github.com/olgerti/daily-quota-app.git`

2. Open the project in your favorite IDE.

3. Configure the database connection by updating the `application.properties` file located 
 in `src/main/resources` with your local database credentials.
4. Build the project using Gradle: `./gradlew build`
5. Run the application: `./gradlew bootRun`


The application will start running on http://localhost:8080.

## API Documentation (Swagger)
The Quota App provides an interactive API documentation using Swagger. You can access the Swagger UI by navigating to http://localhost:8080/swagger-ui/index.html#/. It provides detailed information about the available endpoints, request/response payloads, and allows you to test the API directly from the documentation.

## Running Tests
To run the tests, execute the following command:
`./gradlew test`
This will execute the unit tests for the Quota App and provide you with the test results.


Feel free to explore the application and manage your quotes effortlessly.