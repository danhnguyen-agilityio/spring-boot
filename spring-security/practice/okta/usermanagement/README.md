# USER MANAGEMENT PRACTICE USE OKTA

## OKTA INFO

- Web App
  - Client ID: 0oan4mt8yDscBwAOF356
  - Client Secret: chql1xkgkwWGf7WxIik4tH-GNnXcM9zRX-v5ERj6
  - Redirect URL: https://oidcdebugger.com/debug
  
- Account Admin: danhnguyentk@gmail.com / Deptrai07
- Account with role user: user@gmail.com / Deptrai07
- Account with role manager: manager@gmail.com / Deptrai07
- Account with role admin: admin@gmail.com / Deptrai07


## RUN APP
- Run App
  - `docker-compose up`
  - `mvn clean spring-boot:run -Dspring.profiles.active=dev`
- Test App
  - `mvn clean test -Dspring.profiles.active=dev`

## API
 
- Get access token
  - Implicit Flow
    - https://dev-343362.okta.com/oauth2/default/v1/authorize?client_id=0oan4mt8yDscBwAOF356&redirect_uri=https%3A%2F%2Foidcdebugger.com%2Fdebug&scope=openid%20profile%20email&response_type=id_token%20token&response_mode=fragment&state=none&nonce=qjhoh4wox7c
  - Resource owner password Flow
    - curl --request POST \
      --url https://dev-343362.okta.com/oauth2/default/v1/token \
      --header 'accept: application/json' \
      --header 'authorization: Basic MG9hcWlwc2g1Y2VjdGlSeDAzNTY6X3I0b1FqbFZtU1c3X2RPa1c1QmtCSHMxc2poTlY5c2NGNF9sb3otMQ==' \
      --header 'content-type: application/x-www-form-urlencoded' \
      --data 'grant_type=password&username=user%40gmail.com&password=Deptrai07&scope=openid'
      
- export TOKEN=eyJraWQiOiJJVHUyZVZTaWVrR2N2d19FRkYwYmgwMXlZSmV1Uy1ZMzFrd0NDV09Yc1k4IiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULlYtNVdlbE13Nmh4YzBTUEdzd0p0WFlXaDlodkY5cVkxT0c3bjhMVmdlMnciLCJpc3MiOiJodHRwczovL2Rldi0zNDMzNjIub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTU5MDEzMzEyLCJleHAiOjE1NTkwMTY5MTIsImNpZCI6IjBvYW40bXQ4eURzY0J3QU9GMzU2IiwidWlkIjoiMDB1bjRqenlwNFJ5a2JiRWszNTYiLCJzY3AiOlsib3BlbmlkIl0sInN1YiI6ImRhbmhuZ3V5ZW50a0BnbWFpbC5jb20ifQ.QEM-JTeD0mm9mA6tR9XsqRTjcudVrEZz0cbaHRbCimO4ds1Ne6tds3DWyDKe3YSyueneZ5k9ZBnO9znxFpcKBGzWU-_cUnO1IACKLR_wmy0KlvKDzFNMGdoHd8aithU7PebTm2D9i2x_0OtQ5JRI1Xf3WsATAwrSVQoYYoJYVFx7GbzZwT9-8UdE8LeE5weDjNd_InlvJRkfzDn1U6IaVSFREqDOIFR3EF43tTMSv7xMQWBDw9nDmRYqO7v5PaaIazO6pr7PZijcBHjmmkIkOlPh0AbO5n5YRhyi8fjRJrtRoji2xuEaR5lj2casuxfKbqESTHjuWYfY-A0hk46nzw

- curl http://localhost:8080/

- curl http://localhost:8080/ -H "Authorization: Bearer $TOKEN" 

## REFERENCE LINKS

### Security
- https://pattern-match.com/blog/2019/02/12/springboot2-and-oauth2-authorization-and-revocation/
- https://www.baeldung.com/spring-security-multiple-entry-points **(not read)**
- [SSL](https://stackoverflow.com/questions/47700115/tomcatembeddedservletcontainerfactory-is-missing-in-spring-boot-2)

### Junit
- https://www.baeldung.com/spring-mock-rest-template
- https://thepracticaldeveloper.com/2018/05/10/write-bdd-unit-tests-with-bddmockito-and-assertj/
- http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html

### Okta

- [Spring Rest API with Okta](https://developer.okta.com/blog/2018/12/18/secure-spring-rest-api)
- [Apply Okta version 1.2.0](https://github.com/okta/okta-spring-boot#configure-your-properties)
- [Role base access control](https://developer.okta.com/blog/2017/10/13/okta-groups-spring-security)
- [Unable to generate access token in Postman](https://devforum.okta.com/t/unable-to-generate-access-token-for-my-application-using-postman/4943)
- [Okta Spring Sdk](https://github.com/okta/okta-sdk-java)
- [Remote token validation](https://developer.okta.com/docs/guides/validate-access-tokens/overview/)
- [Remote token validation](https://github.com/okta/okta-jwt-verifier-java)
- [Remote token validation](https://developer.okta.com/docs/reference/api/oidc/#introspect)
- [Remote token validation](https://github.com/okta/okta-spring-boot/issues/98)

### Spring with Docker
- https://hellokoding.com/mapping-jpa-hibernate-entity-and-dto-with-mapstruct/