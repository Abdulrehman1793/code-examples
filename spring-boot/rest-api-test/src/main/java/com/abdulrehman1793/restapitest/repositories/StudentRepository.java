package com.abdulrehman1793.restapitest.repositories;

import com.abdulrehman1793.restapitest.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsStudentByFullName_DisplayName(String displayName);
}
