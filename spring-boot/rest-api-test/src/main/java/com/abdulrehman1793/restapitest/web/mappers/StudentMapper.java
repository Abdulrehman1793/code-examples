package com.abdulrehman1793.restapitest.web.mappers;

import com.abdulrehman1793.restapitest.domain.Student;
import com.abdulrehman1793.restapitest.web.model.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = DateMapper.class)
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDto studentToStudentDto(Student student);

    Student studentDtoToStudent(StudentDto studentDto);
}
