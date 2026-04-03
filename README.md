# REGISTER USER

The module shows how to register a new user user to the application. The code will do the following:

1. Endpoint: /registration
   - Takes a RegistrationRequest object containing the following:
     - firstName
     - lastName
     - email - This will serve as the user name during login.
     - password - This will be saved encrypted in the database.
2. Application will send an email to the provided email with a link to activate the account.
3. Once activated, the user can go into the other pages of the application.
   - /products - Will only be accessible for authenticated users

### Tools used:

1. Postgresql - Used the latest Docker image to run as a container. Once the Springboot application is started, the tables represented by the Entities will be created, if they don't exist.
2. Docker
3. Postman - to test registration endpoint

### Spring Initializer

- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Spring Security
- Lombok
- Java Mail Sender
- Validation
