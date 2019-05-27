-- create the database for OAuth 2.0 provider
CREATE DATABASE auth_server;

use auth_server;

create table users (
id int auto_increment primary key,
username varchar(50),
password varchar(255),
roles varchar(255)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token LONG VARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication LONG VARBINARY,
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token LONG VARBINARY,
  authentication LONG VARBINARY
);

-- create the database for resource server
CREATE DATABASE resource_server;