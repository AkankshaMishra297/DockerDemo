package com.neo.spring.boot.docker.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootDockerApplication {

	@GetMapping("/message")
	public String getMessage() {
		return "Welcome..!!";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDockerApplication.class, args);
	}

}

/*
###Build Docker image 

docker build -t spring-boot-docker.jar .

###run image

docker run -p 9090:8080 spring-boot-docker.jar


###DockerFile
FROM openjdk:8
EXPOSE 8080
ADD target/spring-boot-docker.jar spring-boot-docker.jar 
ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]


###Push and Pull images

docker login

docker image ls

(docker tag spring-boot-docker.jar akankshamishra297/spring-boot-docker.jar) docker tag imageName akankshamishra297/imageName

(docker push akankshamishra297/spring-boot-docker.jar) docker push akankshamishra297/imageName

(docker pull akankshamishra297/spring-boot-docker.jar) docker pull akankshamishra297/imageName

(docker run -p 9095:8080 akankshamishra297/spring-boot-docker.jar) docker run -p portOnUwantToRun:applicationPort akankshamishra297/imageName

####Connect to db using terminal
docker exec -it application-db /bin/bash

mysql -u root - p
root

 */

//#########################################how to work with multiple container ?????
		
/*		

1-create springboot+mysql application
2-application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/tp2
spring.jpa.hibernate.ddl-auto=update
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.generate-ddl=true

3-create Dockerfile in project

FROM openjdk:8
EXPOSE 8080
ADD target/spring-boot-docker-mysql.jar spring-boot-docker-mysql.jar 
ENTRYPOINT ["java","-jar","/spring-boot-docker-mysql.jar"]

4- add finalName in pom.xml

<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
		<finalName>spring-boot-docker-mysql</finalName>
</build>

5-run as maven install

6- go to the derectory , where Dockerfile is presend and run the terminal to create image

docker build -t spring-boot-docker-mysql.jar .

7- now spring-boot-docker-mysql.jar image is created, push it to dockerhub

docker tag spring-boot-docker-mysql.jar akankshamishra297/spring-boot-docker-mysql.jar

docker push akankshamishra297/spring-boot-docker-mysql.jar

8-now pull the image from docker hub

docker pull akankshamishra297/spring-boot-docker-mysql.jar


9- create docker-compose.yml file as follows

version: '3'
services:
  application-db:
    restart: always
    container_name: application-db
    image: 'mysql:5.7.30'
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: application_database
      MYSQL_USER: root
      MYSQL_PASSWORD: root
    ports:
      - '3000:3306'
    volumes:
      - './initial.sql:/docker-entrypoint-initdb.d/initial.sql'
  application-app:
    restart: on-failure

    image: akankshamishra297/spring-boot-docker-mysql.jar

    build: ./
    expose:
      - '8080'
    ports:
      - '8080:8080'
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://application-db:3306/application_database?useSSL=false&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
    depends_on:
      - application-db



10- now save docker-compose.yml anywhere and from that folder open terminal

docker-compose config
docker-compose up


11- now go to port 8080 n run ur application

12- to see  the database n table

docker exec -it application-db /bin/bash

mysql -u root - p
root

show databases;
show tables;

13-to connect db to mysql workbench

create new connection with host as ur IP, and port as 3000 ,user as root, pwd as root as specified in docker-compose.yml


####how to run in another machine

1-share docker-compose.yml and run docker-compose up
 
*/

//################## For sprinboot+mysql+angular ############################

/*
version: '3'
services:
  img-db:
    restart: always
    container_name: img-db
    image: 'mysql:5.7.30'
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: img_database
      MYSQL_USER: root
      MYSQL_PASSWORD: root
    ports:
      - '3991:3306'
    volumes:
      - './initial.sql:/docker-entrypoint-initdb.d/initial.sql'
  image-upload-backendapp:
    restart: on-failure

    image: akankshamishra297/sboota-image.jar

    build: ./
    expose:
      - '9090'
    ports:
      - '9090:9090'
    environment:


      SPRING_DATASOURCE_URL: jdbc:mysql://img-db:3306/img_database?useSSL=false&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
    depends_on:
      - img-db

  image-upload-frontendapp:
    restart: on-failure

    image: akankshamishra297/angular-image

    build: ./
    expose:
      - '4200'
    ports:
      - '4301:4200'
    
    depends_on:
      - image-upload-backendapp
      
      
 */
 

