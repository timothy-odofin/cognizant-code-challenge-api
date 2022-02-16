# Cognizant Code Challenge

To access the full project visit this [link](https://github.com/timothy-odofin/cognizant-code-challenge-api "cognizant code challenge")

## Property File

The property file links the application to the database. The property file is externalized i.e it is not included in the whole project structure, this is done by referencing the file itself in the project entry point using the @PropertySource annotation and the file url to access it.

## MySQL database

In the MySQL database, we have three tables as mentioned below:

- The Task table
- The list of supported languages
- The task_result, that contains the solution submitted by the users with additional view that counts the number of successful solution by each user.

## Running the Application Project

These are the steps to follow in order to run the application

1. Restore Database script.
1. Define the connection string in the application property referenced(See the entry point of the application CodeChallengeApplication).
1. Define the compiler credentials i.e auth_token, client_url, client_secret and base_url in the application.properties file

### To run in development

```cmd
Download, install and configure Java Runtime Environment see-> https://www.oracle.com/java/technologies/downloads/
```

```cmd
Download, install and configure Apache maven see-> https://maven.apache.org/download.cgi
```

```cmd
mvn clean spring-boot:run
```

### To compile for production

```cmd
mvn clean install
```

### Swagger Property file accessment

[Swagger](http://localhost:8080/cognizant-code-challenge/swagger-ui.html "Access swagger")

## Contributors

| Name           | Email                   |
| -------------- | ----------------------- |
| Odofin Timothy | odofintimothy@gmail.com |

## Project structure

This is the project layout of the application.

- #### Route

  It consist of the exposed integrated endpoints.

- #### Service
  It is an interface that the service implementation implements where the business logic resides.
- #### Service Implementation

  This is where the business logic resides, it contains the runnable code for the application route.

- #### Model

  This provides direct link to the Database tables.

- #### Repository

  it interacts with the Model to obtain data from the database.

- #### Configuration

  it consists of the application configurations.

- #### DTO

  it contains the data transfer object of the application.

- #### Utils

  it contains the helper methods and common or general application logic.

- #### Exception
  This is where the global exception of the application is defined.
