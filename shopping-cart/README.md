# RESTful Web Services Practice: [Shopping Cart Practice](https://docs.google.com/document/d/1S6CjwUC3Q7_RlmTVschJod-9VKfzcgHE9IyePgG1tYQ/edit?usp=sharing)

## Overview
This code base provides APIs for Shopping cart

## Technical Stack

### 1. Languages

##### Java

### 2. Framework

##### Spring framework

### 3. Tools

##### Maven

##### Spring boot CLI (1.5.15.RELEASE)

### 4. Library

##### jjwt
see [github](https://github.com/jwtk/jjwt) 
> Support for creating and verifying JSON Web Tokens (JWTs)

##### mysql-connector-java
see [github](https://github.com/mysql/mysql-connector-j)
> MySQL provides connectivity for client applications developed in the Java programming language with MySQL Connector/J,
 a driver that implements the Java Database Connectivity (JDBC) API. 

##### lombok
see [lombok](https://projectlombok.org/)
> Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.

##### mapstruct-jdk8
see [mapstruct](http://mapstruct.org/)
> MapStruct is a code generator that greatly simplifies the implementation of mappings between Java bean types based on
 a convention over configuration approach.

##### javafaker
see [github](https://github.com/DiUS/java-faker)
> Support for generating fake data

##### jackson-datatype-jsr310
see [github](https://github.com/FasterXML/jackson-datatype-jsr310)
> Support for serialize and deserialize and Java 8 date/time types

## REST APIs

##### REST APIs for Product resource

URI|Request|Response|Description
---|---|---|---
/products|GET|200, [{id:1, name:'shoes'},{id:2, name:'dish'}]|Get all product
/products|POST {name: 'title', url:http://show.png}|200, [{id:1, name:'shoes'},{id:2, name:'dish'}]|Create a new product

## Run app
`mvn clean spring-boot:run -Dspring.profiles.active=dev`

## Reference link support in practice
- [Create a list with specific size of elements](https://stackoverflow.com/questions/8267348/how-to-create-a-list-with-specific-size-of-elements)
- [Use MultiValueMap apply when set params of request in Mockito](https://www.programcreek.com/java-api-examples/index.php?api=org.springframework.util.MultiValueMap)
- [Initialize Hashset value by construction](https://stackoverflow.com/questions/2041778/how-to-initialize-hashset-values-by-construction)
- [Allow get, but not post, put for some role](https://stackoverflow.com/questions/37883822/allow-get-but-not-post-put-for-some-role)
- [MapStruct add a new calculated field to the dto](https://stackoverflow.com/questions/45500779/mapstruct-add-a-new-calculated-field-to-the-dto?rq=1)
- [Many to one](https://stackoverflow.com/questions/7197181/jpa-unidirectional-many-to-one-and-cascading-delete/38495206)
- [Pick random value from Enum](https://stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum)
- [Jpa fetchtypes](https://www.thoughts-on-java.org/entity-mappings-introduction-jpa-fetchtypes/)
- [Date format in the json output using spring boot](https://stackoverflow.com/questions/29027475/date-format-in-the-json-output-using-spring-boot)
- [Date format in the json output using spring boot](http://lewandowski.io/2016/02/formatting-java-time-with-spring-boot-using-json/)
- [Date format in the json output using spring boot](https://stackoverflow.com/questions/45662820/how-to-set-format-of-string-for-java-time-instant-using-objectmapper)
- [Define bean for filter in security](https://stackoverflow.com/questions/34233856/spring-security-authenticationmanager-must-be-specified-for-custom-filter)
- [Date API `not read`](https://docs.oracle.com/javase/7/docs/api/java/util/Date.html)
- [Automatic Spring data jpa auditing saving `not read`](https://programmingmitra.blogspot.com/2017/02/automatic-spring-data-jpa-auditing-saving-CreatedBy-createddate-lastmodifiedby-lastmodifieddate-automatically.html)
- [Use enum validator in spring `not read`](https://funofprograming.wordpress.com/2016/09/29/java-enum-validator/)
- [Spring jwt `not read`](https://www.linkedin.com/pulse/json-web-token-jwt-spring-security-real-world-example-boris-trivic)
- [Spring jwt `not read`](https://www.devglan.com/spring-security/spring-boot-jwt-auth)
