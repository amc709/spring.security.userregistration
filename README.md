# REGISTER USER

The module shows how to register a new user to the application. The code will do the following:

1. Endpoint: /registration
   - Takes a RegistrationRequest object containing the following:
     - firstName
     - lastName
     - email - This will serve as the user name during login.
     - password - This will be saved encrypted in the database.
2. Application will do the following:
   - Checks if user (via email) already exists. If not, create the user in the database
   - Create a token that will be sent in the confirmation email with an activation link.
   - Once the user has activated the account, he will be redirected to the login page. Once authenticated, the user is sent to the welcome page.
3. Once activated, the user can go into the other pages of the application, after logging in.
   - /products - Will only be accessible for authenticated users.

### Tools used:

1. Docker
2. Postgresql - Used the latest Docker image to run as a container. Once the Springboot application is started, the tables represented by the Entities will be created, if they don't exist.

   Docker command:
   `docker run --name <CONTAINER NAME> 
-e POSTGRES_PASSWORD=<DB PASSWORD> 
-e POSTGRES_USER=<DB USER> 
-e POSTGRES_DB=<DB NAME> 
-p 5432:5432 
-d postgres`

3. Postman - to test registration endpoint

### Spring Initializer

- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Spring Security
- Lombok
- Java Mail Sender
- Validation
