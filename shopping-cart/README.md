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
/cart-items|GET|200, [{id:1, name:'shoes'},{id:2, name:'dish'}]|Get all cart item
/cart-items|POST {productId: 5, shoppingCartIid: 7, quantity: 10}|201, no content in body, the value of HTTP response header **Location** is the uri of the new created cart item|Create a new cart item
/cart-items/{id}?shoppingCartId={shoppingCartId}|GET|200, {quantity: 7, product: ..., shoppingCart: ...}|Get a cart item by id 
/cart-items/{id}|PUT {shoppingCartIid: 7, quantity: 10}|204, no content in body|Update a cart item by id
/cart-items/{id}?shoppingCartId={shoppingCartId}|DELETE|204, no content|Delete a cart item by id

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
- [Spring response entity](https://www.baeldung.com/spring-response-entity)
- [Custom login api](https://github.com/hantsy/springboot-jwt-sample)
- [Json view](https://www.baeldung.com/jackson-json-view-annotation)
- [Multi view on @JsonView](https://stackoverflow.com/questions/34535599/spring-and-jsonview-with-multiple-identifiers)
- [Custom view by role user](https://stackoverflow.com/questions/23665107/select-jsonview-in-the-spring-mvc-controller)
- [Custom view by role user](https://stackoverflow.com/questions/28878488/dynamic-selection-of-jsonview-in-spring-mvc-controller/29103449#29103449)
- [Get UserDetails from security context](https://stackoverflow.com/questions/6161985/get-userdetails-object-from-security-context-in-spring-mvc-controller)
- [Custom exception thrown in filter](https://stackoverflow.com/questions/34595605/how-to-manage-exceptions-thrown-in-filters-in-spring)
- [Custom exception thrown in filter](http://tutorials.jenkov.com/java-servlets/servlet-filters.html)
- [Custom exception thrown in filter](https://stackoverflow.com/questions/17715921/exception-handling-for-filter-in-spring/46332119#46332119)
- [Access control CORS](https://developer.mozilla.org/vi/docs/Web/HTTP/Access_control_CORS)
- [XMLHttpRequest - Create Http request to server in javascript](https://completejavascript.com/xmlhttprequest-tao-http-request-den-server-trong-javascript/)
- [Spring boot Security CORS](https://stackoverflow.com/questions/40286549/spring-boot-security-cors)
- [Expression-Based Access Control](https://docs.spring.io/spring-security/site/docs/3.1.x/reference/el-access.html)
- [Difference between @Secured and @PreAuthorized](https://stackoverflow.com/questions/3785706/whats-the-difference-between-secured-and-preauthorize-in-spring-security-3)
- [@Secure not working](https://stackoverflow.com/questions/31186826/spring-security-method-security-annotation-secured-is-not-working-java-con)
- [Apply Https in spring boot](https://www.thomasvitale.com/https-spring-boot-ssl-certificate/)
- [“Request method 'POST' not supported” in HTTPS](https://stackoverflow.com/questions/39528694/spring-boot-request-method-get-not-supported-while-redirecting-post-request)
- [Install the SSL certificate on Mac OSX](https://support.securly.com/hc/en-us/articles/206058318-How-to-install-the-Securly-SSL-certificate-on-Mac-OSX-)
- [New version Chrome not work with self-signed certificate](https://stackoverflow.com/questions/7580508/getting-chrome-to-accept-self-signed-localhost-certificate)
- [Date API `not read`](https://docs.oracle.com/javase/7/docs/api/java/util/Date.html)
- [Automatic Spring data jpa auditing saving `not read`](https://programmingmitra.blogspot.com/2017/02/automatic-spring-data-jpa-auditing-saving-CreatedBy-createddate-lastmodifiedby-lastmodifieddate-automatically.html)
- [Use enum validator in spring `not read`](https://funofprograming.wordpress.com/2016/09/29/java-enum-validator/)
- [Spring jwt `not read`](https://www.linkedin.com/pulse/json-web-token-jwt-spring-security-real-world-example-boris-trivic)
- [Spring jwt `not read`](https://www.devglan.com/spring-security/spring-boot-jwt-auth)
