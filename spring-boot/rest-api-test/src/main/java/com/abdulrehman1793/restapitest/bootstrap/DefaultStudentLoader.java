package com.abdulrehman1793.restapitest.bootstrap;

import com.abdulrehman1793.restapitest.domain.Address;
import com.abdulrehman1793.restapitest.domain.FullName;
import com.abdulrehman1793.restapitest.domain.Gender;
import com.abdulrehman1793.restapitest.domain.Student;
import com.abdulrehman1793.restapitest.repositories.StudentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class DefaultStudentLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;

    private final ObjectMapper objectMapper;

    @Value("classpath:student_data.json")
    Resource resourceFile;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    @Override
    public void run(String... args) throws IOException {
        var values = objectMapper.readValue(resourceFile.getFile(), ArrayNode.class);

        List<Student> students = new ArrayList<>();
        if (values != null) {
            values.forEach(value -> {
                if (students.size() > 25) return;
                Student student = Student.builder()
                        .fullName(FullName.builder()
                                .firstName(value.get("first_name").textValue())
                                .lastName(value.get("last_name").textValue())
                                .displayName(value.get("displayName").textValue()).build())
                        .gender(Gender.valueOf(value.get("gender").textValue().toUpperCase()))
                        .phone(value.get("phone").textValue())
                        .email(value.get("email").textValue())
                        .avatarUrl(value.get("avatarUrl").textValue())
                        .dob(LocalDate.parse(value.get("dob").textValue(), formatter))
                        .build();

                JsonNode postalCode = value.get("postalCode");
                if (!postalCode.isNull()) {
                    student.addAddress(Address.builder()
                            .name(student.getFullName().getFullName())
                            .city(value.get("city").textValue())
                            .country(value.get("country").textValue())
                            .phone(value.get("phone").textValue())
                            .postalCode(postalCode.textValue())
                            .line(value.get("street").textValue())
                            .build());
                }

                students.add(student);
            });
            studentRepository.saveAll(students);
        }
    }
}
