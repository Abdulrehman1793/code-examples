package com.abdulrehman1793.restapitest.services;

import com.abdulrehman1793.restapitest.web.model.PagedList;
import com.abdulrehman1793.restapitest.web.model.StudentDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StudentService {
    PagedList<StudentDto> studentPage(Pageable pageable);

    StudentDto createStudent(StudentDto studentDto);

    StudentDto updateStudent(UUID studentId, StudentDto studentDto);

    void deleteStudent(UUID studentId);
}
