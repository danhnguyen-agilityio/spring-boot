# OAUTH2 PRACTICE

## RUN APP

- Run `docker-compose up`
- Run sql in file database.sql (root/password)
- Run `cd authorization-server`
- Run `./mvnw spring-boot:run`
- Run `cd ../resource-server`
- Run `./mvnw spring-boot:run`

## TEST APP

- Run `cd authorization-server`
- Run `./mvnw test`
- Run `cd ../resource-server`
- Run `./mvnw test`

## REF

- https://pattern-match.com/blog/2019/02/12/springboot2-and-oauth2-authorization-and-revocation/
- https://www.baeldung.com/spring-security-multiple-entry-points
- https://www.baeldung.com/spring-mock-rest-template
