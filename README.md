# Varo Backend Assessment
## Pre-requisites
- Docker
## Architecture
Below is the **architecture** of this project
![](https://i.ibb.co/FKJP5QF/proposed-1.jpg)
## Data Model
Below is the **data model** used in this project
![](https://i.ibb.co/MMhdYh2/Database-ER-diagram-crow-s-foot.jpg)
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

- To stop the application, run the below command.
> docker-compose down

- To scale up the application, use the below command.
> docker-compose up -d --build --scale app=3

This will create multiple instances of the spring boot application hosted in tomacat(8080 port). An Nginx load balancer will be created to balance the traffic to these instances. This balancer would be present on 4000 port in docker and would get exposed to 4000 port of the local system as per docker-compose.yml

## Testing the APIs
Please Use the below curl commands for Windows:
- Upsert User
> curl --location --request POST "http://localhost:4000/upsertUser" --header "Content-Type: application/json" --data-raw "{    \\"userId\\":\\"saiakhil\\", \\"firstName\\":\\"Sai Akhil\\", \\"lastName\\":\\"Sreekakula\\", \\"email\\":\\"saiakhil29@gmail.com\\", \\"phoneNumber\\":\\"2108952571\\", \\"address\\": { \\"addressLine1\\":\\"3505 Telford Street\\", \\"addressLine2\\":\\"Apt 2\\", \\"city\\":\\"Cincinnati\\", \\"state\\":\\"Ohio\\", \\"zipCode\\":\\"45220\\"}}"
- IsUserExists
> curl --location --request POST "http://localhost:4000/isUserExists" --header "Content-Type: application/json" --data-raw "{ \\"email\\":\\"saiakhil29@gmail.com\\"}"
- ListUsers
> curl --location --request GET "http://localhost:4000/listUsers"
- GetUserDetails
> curl --location --request POST "http://localhost:4000/getUserDetails" --header "Content-Type: application/json" --data-raw "{ \\"userId\\":\\"saiakhil\\" }"
- DeleteUser
> curl --location --request DELETE "http://localhost:4000/deleteUser" --header "Content-Type: application/json" --data-raw "{ \\"userId\\":\\"saiakhil\\" }"
- UpdateAddress
> curl --location --request POST "http://localhost:4000/updateAddress" --header "Content-Type: application/json" --data-raw "{ \\"userId\\":\\"saiakhil\\", \\"addressLine1\\":\\"521 Martin Luther King Dr W\\", \\"addressLine2\\":\\"Apt C36\\", \\"city\\":\\"Cincinnati\\", \\"state\\":\\"Ohio\\", \\"zipCode\\":\\"45220\\"}"

