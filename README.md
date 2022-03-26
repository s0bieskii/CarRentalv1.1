# Car Rental :car:
#### It is my first back-end app for my portfolio. The app is written with Java 11, Spring Boot 2, and MySQL database.
The application is for the management of a car rental network. I tried to use good practices in a project like SOLID, DTO, Clean Code and Loggers, etc.
I created a basic REST API and now I'm working on Model View Controller with Thymeleaf and Bootstrap because I will create a simple GUI (and it will teach me what frontend requires from the backend). The app is not finished in 100% but I working on it all the time and progress is still updating there.

### Application allows us to:
- Create branch office of our car rental network, assign cars and employees to this office.
- Dynamic car search with filtering by params (e.g red cars with 5 doors and BMW brand, violet cabriolet available from 12.06.2022 to 20.06.2022)
- Configure delay between two rents of the same car (delay in hours for cleaning up the car and preparing for next customer)
- Rent a car for a specific user
- Show rents for specific user
- Show all rents (for employee usage)
- Add return report to returning the car
- All CRUD operation and more on entities (not yet secured with Spring Secure and not assigned ROLE)
- And more other stuff

### TODO:
- Finish front-end
- Login view secure
- Secure some endpoint to USER_ROLE and ADMIN_ROLE
- Add creating a PDF file from ReturnReport entity
- Implement verification email validation by token


### How to run:
- Open project with InteliJ, go to: **src/main/java/com/car/rental/Application.java -> right click to Application.java -> Run 'Application.main()'**
- Open CarRentalv1.1-main folder -> Open command line and type: **mvn spring-boot:run**
- Open the app at: https://carrental-portfolio.herokuapp.com/

**Application will start on http://localhost:8080**


## What you can find in my project: 🎉
- REST API
- ModelViewController
- CRUD
- DTO
- Dynamic entity search
- Project patterns like Builder
- Custom validators
- Application logs
- Clean code
- Integration tests


## Tech/framework used 🔧
- Java 11
- Spring Boot 2
- Spring Validator
- JPA
- Criteria API
- MySQL
- Thymeleaf
- MapStruct
- Lombok
- JUnit
- Bootstrap
- CSS/HTML
