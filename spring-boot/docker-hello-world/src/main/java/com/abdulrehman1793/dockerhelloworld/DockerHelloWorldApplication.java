package com.abdulrehman1793.dockerhelloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DockerHelloWorldApplication {
	public static void main(String[] args) {
		SpringApplication.run(DockerHelloWorldApplication.class, args);
	}
}
