package guru.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// docker logs <container name> => see console output
// docker logs -f <container name> => see tail console output

// docker run mongo
// docker run -d mongo

// volume data from host dir to container dir
// docker run -p 27017:27017 -v /user/database/mongo:/data/db -d mongo => for linux and mac

// docker volume create --name=mongodata
// docker run -p 27017:27017 -v mongodata:/data/db -d mongo => for windown 10

// docker run -d --hostname guru-rabbit --name some-rabbit -p 8080:15672 -p 5671:5671 -p 5672:5672 rabbitmq:3-management

// docker run --name guru-mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -v sqldb:/var/lib/mysql -p 3306:3306 -d mysql

// build docker image
// From the directory of the Dockerfile run
// docker build -t <tag name>

// specify an environment variable for a docker container
// docker run -e MY_VAR=my_prop <image name>

// shell into a running docker container
// docker exec -it <container name> bash

@SpringBootApplication
public class SpringBootMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMongodbApplication.class, args);
	}
}
