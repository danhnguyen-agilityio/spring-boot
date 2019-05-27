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

### Security
- https://pattern-match.com/blog/2019/02/12/springboot2-and-oauth2-authorization-and-revocation/
- https://www.baeldung.com/spring-security-multiple-entry-points **(not read)**

### Junit
- https://www.baeldung.com/spring-mock-rest-template
- https://thepracticaldeveloper.com/2018/05/10/write-bdd-unit-tests-with-bddmockito-and-assertj/
- http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html

