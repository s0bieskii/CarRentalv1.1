# Car Rental :car:
#### It is my first back-end app for portfolio. The app is write with Java 11, Spring Boot 2 and MySQL database.
The application is for menagment a car rental network. I tried to use good practice in project like SOLID, DTO, Clean Code and Loggers etc.
I created basic REST API and now i'm working on Model View Controller with Thymeleaf and Bootstrap beacuse i will create simple GUI (and it will teach me what frontend requires from backend). App is not finish in 100% but i working on it all the time and progress is still updating there.

### Application allow us to:
- Create branch office of our car rental network, assign cars and employee to this office.
- Dynamic car search with filtering by params (e.g red cars with 5 doors and BMW brand, violet cabriolet available from 12.06.2022 to 20.06.2022)
- Configure delay betwen two rents of the same car (delay in hours for cleaning up car and preapare for next customer)
- Rent a car for specify user
- Show rents for specify user
- Show all rents (for employee usage)
- Add return report to returning car
- All CRUD operation and more on entities (not yet secured with Spring Secure and not assigned ROLE)
- And more other stuff

### TODO:
- Finish front-end
- Login view secure
- Secure some endpoint to USER_ROLE and ADMIN_ROLE
- Add creating PDF file from ReturnReport entity
- Implement verification email validation by token


### How to run:
- Open project with InteliJ, go to: **src/main/java/com/car/rental/Application.java -> right click to Application.java -> Run 'Application.main()'**
- Open CarRentalv1.1-main folder -> Open command line and type: **mvn spring-boot:run**
- Open app in: https://carrental-portfolio.herokuapp.com/

**Application will start on: http://localhost:8080**


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
- Loombok
- JUnit
- Bootstrap
- CSS/HTML


## Screenshots 📺
<p align="center">
    <img src="https://user-images.githubusercontent.com/42815359/158565591-23301d8f-6ca5-4afe-84c3-bd8869e738d1.png" alt="Screenshot">
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/42815359/158565915-fe33b056-8058-4b72-9f98-28b567ec2269.png" alt="Screenshot">
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/42815359/158566385-1ac18c01-8a37-43c5-8765-23085f6256e9.png" alt="Screenshot">
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/42815359/158566736-5d82b2d2-9d04-47b3-b912-667805c42b3e.png" alt="Screenshot">
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/42815359/158566870-57b14615-2c38-4867-9750-dc824e94833c.png" alt="Screenshot">
</p>

<p align="center">
    <img src="https://user-images.githubusercontent.com/42815359/158567041-7b3be5f3-f07c-41c5-926f-21b234f58d78.png" alt="Screenshot">
</p>
