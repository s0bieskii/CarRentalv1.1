# About project: :car:
It is my main project which I'm still developing, it is a Spring web car rental application, that provides REST API for communication and MVC pattern.
The data is stored in a relational database which is accessed through JPA/Hibernate. The application is divided into client, employee, and administrator layers, thanks to which I increase its security. The main security features of the application are the use of DTOs, Spring Validation, and the most important Spring Security, which is responsible for user authentication.
- As a customer of a rental company we can register, login, dynamically filter available cars, rent a car, manage rentals, edit rentals and account details, delete account
- As an employee we can manage a calendar of rentals, control the current status of the rental, edit car data, receive and give cars to customers with the creation of reports of issuance and return report.
- As an administrator we can add new cars and assign them to rental branches, manage employees, add new employees, generate monthly reports. I created the front-end layer using Thymeleaf and Bootstrap.

### Live app: https://good-cars.herokuapp.com/
<br>

### TODO 📌 
 <table class="table table-dark table-striped">
                        <thead>
                        <tr>
                          <th scope="col">DONE ✔</th>
                          <th scope="col">IN PROGRESS ❗</th>
                        </thead>
  <tbody>
                        <tr>
                          <td>• Design project architecture</td>
                          <td>• Management panel view for employee </td>
                        </tr>
                        <tr>
                          <td>• Implement REST API</td>
                          <td>• Management panel view for admin </td>
                        </tr>
    <tr>
                          <td>• Create dynamically cars filter</td>
                          <td>• Add car image </td>
                        </tr>
    <tr>
                          <td>• Implement ModelViewController</td>
                          <td>• Add maps with marker to rental view </td>
                        </tr>
    <tr>
                          <td>• Create site layout</td>
                          <td>• Confirm registration by email link with JWT </td>
                        </tr>
    <tr>
                          <td>• Create site views</td>
                          <td>• Fix some bugs </td>
                        </tr>
    <tr>
                          <td>• Implement pagination</td>
                          <td> </td>
                        </tr>
    <tr>
                          <td>• Add Spring Security</td>
                          <td> </td>
                        </tr>
    <tr>
                          <td>• Implement login</td>
                          <td> </td>
                        </tr>
    <tr>
                          <td>• Implement register</td>
                          <td> </td>
                        </tr>
    <tr>
                          <td>• Add Spring Validation</td>
                          <td> </td>
                        </tr>
    <tr>
                          <td>• Implement User stuff</td>
                          <td> </td>
                        </tr>
    <tr>
                          <td>• Add email sender</td>
                          <td> </td>
                        </tr>
    <tr>
                          <td>• Add bug report</td>
                          <td> </td>
                        </tr>
    </tbody>
                      </table>


### How to run:
- Open project with InteliJ, go to: **src/main/java/com/car/rental/Application.java -> right click to Application.java -> Run 'Application.main()'**
- Open CarRentalv1.1-main folder -> Open command line and type: **mvn spring-boot:run**
- Open the app at: 

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
<table class="table table-dark table-striped">
                        <thead>
                        <tr>
                          <th scope="col">Tech & framework used</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                          <td>• Java 11</td>
                        </tr>
                        <tr>
                          <td>• Spring Framework (MVC, Data JPA, Security, Validation)</td>
                        </tr>
                        <tr>
                          <td>• Spring Boot</td>
                        </tr>
                        <tr>
                          <td>• Hibernate/JPA</td>
                        </tr>
                        <tr>
                          <td>• H2Database/SQL</td>
                        </tr>
                        <tr>
                          <td>• Criteria API</td>
                        </tr>
                        <tr>
                          <td>• JUnit</td>
                        </tr>
                        <tr>
                          <td>• Flyway</td>
                        </tr>
                        <tr>
                          <td>• MapStruct</td>
                        </tr>
                        <tr>
                          <td>• Thymeleaf</td>
                        </tr>
                        <tr>
                          <td>• Bootstrap (HTML/CSS)</td>
                        </tr>
                        </tbody>
                      </table>
