package com.abdulrehman1793.dockerhelloworld;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String helloWorld() {
        return "Hello world";
    }
}
