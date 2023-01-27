package com.abdulrehman1793.restapitest.services;

import com.abdulrehman1793.restapitest.domain.FullName;
import com.abdulrehman1793.restapitest.domain.Student;
import com.abdulrehman1793.restapitest.exception.BadRequestException;
import com.abdulrehman1793.restapitest.exception.DuplicateDataException;
import com.abdulrehman1793.restapitest.repositories.StudentRepository;
import com.abdulrehman1793.restapitest.web.mappers.StudentMapper;
import com.abdulrehman1793.restapitest.web.model.PagedList;
import com.abdulrehman1793.restapitest.web.model.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Override
    public PagedList<StudentDto> studentPage(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findAll(pageable);

        List<StudentDto> students =
                studentPage.stream().map(studentMapper::studentToStudentDto).toList();

        return new PagedList<>(students,
                PageRequest.of(
                        studentPage.getPageable().getPageNumber(),
                        studentPage.getPageable().getPageSize()),
                studentPage.getTotalElements());
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {

        String displayName = studentDto.getFullName().getDisplayName();
        if (studentRepository.existsStudentByFullName_DisplayName(displayName)) {
            throw new DuplicateDataException("Student with " + displayName + " display name already exists. ");
        }

        Student student = studentMapper.studentDtoToStudent(studentDto);

        Student savedStudent = studentRepository.save(student);

        return studentMapper.studentToStudentDto(savedStudent);
    }

    @Override
    public StudentDto updateStudent(UUID studentId, StudentDto studentDto) {

        Student fetchStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new BadRequestException("Student record not found for " + studentId));

        FullName fullName = fetchStudent.getFullName();
        fullName.setFirstName(studentDto.getFullName().getFirstName());
        fullName.setLastName(studentDto.getFullName().getLastName());

        fetchStudent.setFullName(fullName);
        fetchStudent.setDob(studentDto.getDob());
        fetchStudent.setEmail(studentDto.getEmail());
        fetchStudent.setGender(studentDto.getGender());
        fetchStudent.setAvatarUrl(studentDto.getAvatarUrl());

        Student saveStudent = studentRepository.save(fetchStudent);

        return studentMapper.studentToStudentDto(saveStudent);
    }

    @Override
    public void deleteStudent(UUID studentId) {
        Student fetchStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new BadRequestException("Student record not found for " + studentId));

        studentRepository.delete(fetchStudent);
    }
}
