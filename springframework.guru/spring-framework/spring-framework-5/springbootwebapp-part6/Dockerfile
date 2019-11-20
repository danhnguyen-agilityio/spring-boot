FROM centos

RUN yum install -y java

VOLUME /tmp
ADD target/spring-boot-web-0.0.1-SNAPSHOT.jar usr/project/myapp.jar
WORKDIR usr/project
RUN sh -c 'touch myapp.jar'
ENTRYPOINT ["java", "-jar", "myapp.jar"]

# docker build -t spring-boot-docker .
# docker run -d -p 8080:8080 spring-boot-docker
# Go localhost:8080