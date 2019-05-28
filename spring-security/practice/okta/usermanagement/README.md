# USER MANAGEMENT PRACTICE USE OKTA

## Client Info

- Machine to machine App
  - Client ID: 0oan53eo1MfOiC3iE356
  - Client Secret: AzukYn7lCGvqMhFj5Di6KSzXJghl4MbkB-MqqYx0
 
- Web App
  - Client ID: 0oan4mt8yDscBwAOF356
  - Client Secret: chql1xkgkwWGf7WxIik4tH-GNnXcM9zRX-v5ERj6
  - Redirect URL: https://oidcdebugger.com/debug

## API

- Get access token
  - https://dev-343362.okta.com/oauth2/default/v1/authorize?client_id=0oan4mt8yDscBwAOF356&redirect_uri=https%3A%2F%2Foidcdebugger.com%2Fdebug&scope=openid%20profile&response_type=token&response_mode=fragment&state=none&nonce=qjhoh4wox7c
  
- export TOKEN=eyJraWQiOiJJVHUyZVZTaWVrR2N2d19FRkYwYmgwMXlZSmV1Uy1ZMzFrd0NDV09Yc1k4IiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULlYtNVdlbE13Nmh4YzBTUEdzd0p0WFlXaDlodkY5cVkxT0c3bjhMVmdlMnciLCJpc3MiOiJodHRwczovL2Rldi0zNDMzNjIub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTU5MDEzMzEyLCJleHAiOjE1NTkwMTY5MTIsImNpZCI6IjBvYW40bXQ4eURzY0J3QU9GMzU2IiwidWlkIjoiMDB1bjRqenlwNFJ5a2JiRWszNTYiLCJzY3AiOlsib3BlbmlkIl0sInN1YiI6ImRhbmhuZ3V5ZW50a0BnbWFpbC5jb20ifQ.QEM-JTeD0mm9mA6tR9XsqRTjcudVrEZz0cbaHRbCimO4ds1Ne6tds3DWyDKe3YSyueneZ5k9ZBnO9znxFpcKBGzWU-_cUnO1IACKLR_wmy0KlvKDzFNMGdoHd8aithU7PebTm2D9i2x_0OtQ5JRI1Xf3WsATAwrSVQoYYoJYVFx7GbzZwT9-8UdE8LeE5weDjNd_InlvJRkfzDn1U6IaVSFREqDOIFR3EF43tTMSv7xMQWBDw9nDmRYqO7v5PaaIazO6pr7PZijcBHjmmkIkOlPh0AbO5n5YRhyi8fjRJrtRoji2xuEaR5lj2casuxfKbqESTHjuWYfY-A0hk46nzw

- curl http://localhost:8080/

- curl http://localhost:8080/ -H "Authorization: Bearer $TOKEN" 

## REFERENCE LINKS

### Okta

- [Spring Rest API with Okta](https://developer.okta.com/blog/2018/12/18/secure-spring-rest-api)
- [Apply Okta version 1.2.0](https://github.com/okta/okta-spring-boot#configure-your-properties)