# Xogito Project-User Application

This is a brief description of Project-User Spring Boot application.

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java Development Kit (JDK) 8 or later
- Spring Boot ~> 2.7.13
- Apache Maven 3.6.0 or later
- Your favorite IDE (e.g. IntelliJ IDEA, Eclipse, VS Code)

## Database
- H2
- Derby 

### Project structure
This project consists of 1 deploy-able artefacts:

### Deploy-able
- xogito-assessment-1.0.0

```
+-- xogito-assessment
+-- .idea
+-- .mvn
|   +-- src/
|   +-- .gitignore
|   +-- pom.xml
+-- .gitignore
+-- pom.xml
+-- README.md
```
---


### Local development
Run the below commands from the project root:
1. Build the project
```
mvn -B  clean install --file pom.xml
```

2. Open the dev environment application file using an editor of choice
```
vi application.yml
```
3. credentials for the h2 database under the datasource section
```
datasource:
    username: admin
    password: admin
    url: jdbc:h2:mem:~/xogitoprojectdb
    
```

4. Running the application
```
mvn spring-boot:run

Landing Page is localhost:9099 

1. Navigate to http://localhost:9099/xogito-assessment/swagger-ui/index.html# for swagger ui 
2. Then you are now able to start testing using the Try It Out option
```
5. Navigate into the xogito-assessment/src/main/resources/
```
cd xogito-assessment/src/main/resources/
```
6. Update dev profile with credentials for the H2 database under the datasource section
```
datasource:
    username: admin
    password: admin
    url: jdbc:h2:mem:~/xogitoprojectdb
```
7. Navigate into xogito-assessment from the root directory in order to run the service
```
cd xogito-assessment
```
8. Run the application
```
mvn spring-boot:run
```

9. Access the application H2 database on the browser using the below url
```
localhost:9099/h2-console/

```

localhost:9090/h2-console/

**Notes:**
- The service is exposed on `http://localhost:9099` by default.
- The openapi swagger is exposed on `http://localhost:9099/xogito-assessment/swagger-ui/index.html#` by default.
- Spring Boot uses the `application-*.yml` file(s).

**Improvements:**
- Improvements would be to deploy the jar in docker file
- Expose usage of the Actuator to expose endpoints for tracing and checking endpoints health
- Deploy application in AWS using ECR and managed through EKS
- Store file or files in S3 Bucket

---

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
- [Maven](https://maven.apache.org/) - Dependency Management
- [MySQL](https://www.mysql.com/) - Database Management System

## Authors

- **Evans K F Chikuni** - [Your website or Github Profile](https://github.com/vance04real)

## License

This project is licensed under the Xogito License - see the [Xogito.md](LICENSE.md) file for details



