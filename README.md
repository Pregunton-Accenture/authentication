###### Authentication API

* [Description](#description)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Start Up](#start-up)
* [Endpoints](#endpoints)

## Description

___
Spring Security Authentication to Pregunton API.

## Prerequisites

___
I will provide a list with the dependencies, and their download link:

* Java 8 ([Download link](https://www.java.com/es/download/))
* Maven ([Download link](https://maven.apache.org/))
* Git ([Download link](https://git-scm.com/downloads))
* MariaDB ([Download link](https://downloads.mariadb.org/))

## Installation

___

1. Open your command prompt and go to a root projects folder.
2. Then clone the project with ```git clone https://github.com/Pregunton-Accenture/authentication.git``` command.
3. Execute the authentication_db.sql in ../authentication/src/main/resources/db/authentication_db.sql on MariaDB.
4. Create a new file named: ```database.properties``` into ../pregunton/src/main/resources/ with the following data:
    * spring.datasource.url=jdbc:mariadb://localhost:3306/accenture_pregunton
    * spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
    * spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
    * spring.jpa.hibernate.ddl-auto=none
    * spring.datasource.username = ```your username```
    * spring.datasource.password = ```your password``` (if you have not set any password, you can omit this line)
    * spring.datasource.initialization-mode=always
5. Create a new file named: ```jwt.properties``` into ../authentication/src/main/resources/ with the following data:
    * jwt.secret.key=```your secret key``` (Note: your secret key can be any word you want.)
6. After the installation, you must jump into the project folder with your command prompt.
7. Finally, you must execute the next command: ```mvn clean install```.

## Start Up

___

1. Open your command prompt and go to the application folder.
2. Then, you have to start up the application with ```mvn spring-boot:run``` command.
3. If the process executed successfully, you could make requests to the application endpoints via http://localhost:8081.

**NOTE:** You could access to a user interface with http://localhost:8081/swagger-ui.html to do those requests and
provides to you some hints to know the data to send.

## Endpoints

___

* http://localhost:8081
    * /
        * __POST__ method
            * /authenticate => retrieves a token and refresh_token of the provided credentials.
            * /refresh => generates a new set of token and refresh_token with the refresh_token provided.
            * /validate => validates if the provided token is expired
    * /users
        * __POST__ method
            * / => saves the new user credentials.