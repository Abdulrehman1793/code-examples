package com.abdulrehman1793.dockerlayerdemo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoostrapStudent implements CommandLineRunner {
    private final Faker faker;
    private final StudentRepository studentRepository;

    public BoostrapStudent(Faker faker, StudentRepository studentRepository) {
        this.faker = faker;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Student> students = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            students.add(new Student(
                    faker.name().fullName(),
                    faker.funnyName().name().toLowerCase().replaceAll(" ","") + "@gmail.com",
                    faker.address().fullAddress(),
                    faker.phoneNumber().phoneNumber()
            ));
        }

        studentRepository.saveAll(students);
    }
}
