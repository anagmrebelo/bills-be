# Bills Split

## Description of the project

Bills Split is a comprehensive solution designed to simplify the process of splitting bills among flatmates. This application allows users to register flats, flatmates, and bills, and keep track of each flatmate's attendance per month.

The primary goal of Bills Split is to provide a fair and transparent system for managing and splitting bills. By registering a flat, users can create a shared living space within the application. Each flatmate can then be added to the flat, creating a digital representation of the living arrangement.

Bills can be added to the flat on a monthly basis. Each bill is then split among the flatmates based on their attendance for that month. This ensures that each flatmate pays an appropriate share of the bills, based on their actual usage of the flat's resources.


## Class Diagram
(Include the class diagram here. You can use a tool like draw.io to create the diagram and then include the image in the README)

## Setup
1. Clone the repository:
```
git clone https://github.com/your-username/your-repo-name.git
```

2. IntelliJ IDEA will automatically import dependencies from the pom.xml file. If you are using another IDE, you may need to manually import the dependencies.
3. Before running the application, make sure you have MySQL installed and running on your machine. You can download it from [here](https://dev.mysql.com/downloads/installer/). 
4. Create a new database in MySQL:
```
CREATE DATABASE your_database_name;
```

5. In the project directory, you will find a file named `application-local-example.properties`. Rename this file to `application-local.properties`. 
6. Open `application-local.properties` and replace the placeholders with your actual MySQL credentials and database name:
```
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name 
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```
8. Run the application: `mvn spring-boot:run`

## Technologies Used
- Java 21
- Spring Boot
- Maven
- MySQL
- springdoc-openapi

## Controllers and Routes structure
(Describe the structure of your controllers and routes here. You can include a table with the route, HTTP method, and a brief description of what the route does)

Access the API Documentation at `http://localhost:8080/swagger-ui/index.html` while the server is running

## Extra links
- [Project Board](https://www.baeldung.com/spring-rest-openapi-documentation)
- [Presentation Slides](URL_TO_PRESENTATION_SLIDES)

## Future Work
Check [project board](https://github.com/users/anagmrebelo/projects/10/views/1) extra column to know future implementations on the pipeline

## Resources
- [Defining Unique Constraints in JPA](https://www.baeldung.com/jpa-unique-constraints)
- [Spring Profiles](https://www.baeldung.com/spring-profiles)
- [Documenting a Spring REST API](https://www.baeldung.com/spring-rest-openapi-documentation)