# Varo Backend Assessment
## Pre-requisites
- Docker
## Architecture
Below is the **architecture** of this project
![](https://i.ibb.co/GngLc9T/proposed.jpg)
## Data Model
Below is the **data model** used in this project
![enter image description here](https://i.ibb.co/MMhdYh2/Database-ER-diagram-crow-s-foot.jpg)
## Instructions to Setup and Run
- Use the below command to clone the repository from **Github**.
> git clone https://github.com/saiakhilsreekakula/varo-backend-assessment.git
- Use the below command to **build the application**.
> mvnw clean install -DskipTests
- Run the below Docker compose command to build image.
> docker-compose build
- Run the below Docker compose command to start the application and database.
> docker-compose up -d

This will first create the database and run the migrations in **init.sql** and creates all the required tables, indexes,triggers etc. It will also load the data from local volumes if any. Later, it will **start the spring boot** application once database is initialized.
## Testing the APIs
Please Use the below curl commands for Windows systems:
- Upsert User
> curl --location --request POST "http://localhost:8080/upsertUser" --header "Content-Type: application/json" --data-raw "{    \\"userId\\":\\"saiakhil\\", \\"firstName\\":\\"Sai Akhil\\", \\"lastName\\":\\"Sreekakula\\", \\"email\\":\\"saiakhil29@gmail.com\\", \\"phoneNumber\\":\\"2108952571\\", \\"address\\": { \\"addressLine1\\":\\"3505 Telford Street\\", \\"addressLine2\\":\\"Apt 2\\", \\"city\\":\\"Cincinnati\\", \\"state\\":\\"Ohio\\", \\"zipCode\\":\\"45220\\"}}"
- IsUserExists
> curl --location --request POST "http://localhost:8080/isUserExists" --header "Content-Type: application/json" --data-raw "{ \\"email\\":\\"saiakhil29@gmail.com\\"}"
- ListUsers
> curl --location --request GET "http://localhost:8080/listUsers"
- GetUserDetails
> curl --location --request POST "http://localhost:8080/getUserDetails" --header "Content-Type: application/json" --data-raw "{ \\"userId\\":\\"saiakhil\\" }"
- DeleteUser
> curl --location --request DELETE "http://localhost:8080/deleteUser" --header "Content-Type: application/json" --data-raw "{ \\"userId\\":\\"saiakhil\\" }"
- UpdateAddress
> curl --location --request POST "http://localhost:8080/updateAddress" --header "Content-Type: application/json" --data-raw "{ \\"userId\\":\\"saiakhil\\", \\"addressLine1\\":\\"521 Martin Luther King Dr W\\", \\"addressLine2\\":\\"Apt C36\\", \\"city\\":\\"Cincinnati\\", \\"state\\":\\"Ohio\\", \\"zipCode\\":\\"45220\\"}"

