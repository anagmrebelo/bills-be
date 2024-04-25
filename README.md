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
## Controllers and Routes structure

Here is a general overview of the controllers and routes in this project:

| Route               | HTTP Method | Controller | Action | Description                                                           |
|---------------------|-------------|------------|--------|-----------------------------------------------------------------------|
| `/flats`            | GET         | FlatController | `getAllFlats` | Returns a list of all flats                                           |
| `/flats/{id}`       | GET         | FlatController | `getFlat` | Returns a specific flat by its ID                                     |
| `/flats`            | POST        | FlatController | `createFlat` | Creates a new flat                                                    |
| `/flatmates`        | GET         | FlatmateController | `getAllFlatmates` | Returns a list of all flatmates                                       |
| `/flatmates/{id}`   | GET         | FlatmateController | `getFlatmate` | Returns a specific flatmate by its ID                                 |
| `/flatmates`        | POST        | FlatmateController | `createFlatmate` | Creates a new flatmate                                                |
| `/flatmates/{id}`   | PATCH       | FlatmateController | `updateFlatmate` | Updates a specific flatmate                                           |
| `/attendance`       | GET         | AttendanceController | `getAllAttendance` | Returns attendances for a flat or flatmate in a specific month        |
| `/attendance`       | POST        | AttendanceController | `createAttendance` | Creates a new attendance record for a flatmate in a specicif month    |
| `/bills/{flatId}`   | GET         | BillController | `getBill` | Returns all the bills for a specific flat                             |
| `/bills/{billType}` | POST         | BillController | `updateBill` | Creates a new bill for a flat for a specifi month                     |
| `/bills/{id}`       | DELETE      | BillController | `deleteBill` | Deletes a specific bill                                               |
| `/debt`             | GET         | DebtController | `getDebts` | Returns a flatmate list of debts for all bills or for a specific bill |
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