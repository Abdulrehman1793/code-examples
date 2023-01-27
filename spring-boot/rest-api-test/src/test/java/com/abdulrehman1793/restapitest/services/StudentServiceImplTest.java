package com.abdulrehman1793.restapitest.services;

import com.abdulrehman1793.restapitest.domain.FullName;
import com.abdulrehman1793.restapitest.domain.Student;
import com.abdulrehman1793.restapitest.exception.BadRequestException;
import com.abdulrehman1793.restapitest.exception.DuplicateDataException;
import com.abdulrehman1793.restapitest.repositories.StudentRepository;
import com.abdulrehman1793.restapitest.web.mappers.StudentMapper;
import com.abdulrehman1793.restapitest.web.model.FullNameDto;
import com.abdulrehman1793.restapitest.web.model.PagedList;
import com.abdulrehman1793.restapitest.web.model.StudentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    UUID studentId;
    StudentDto studentDto;
    Student student;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
        studentDto = StudentDto.builder().id(studentId).fullName(FullNameDto.builder().build()).build();
        student = Student.builder().id(studentId).fullName(FullName.builder().build()).build();
    }

    @Test
    void studentPageSuccessfulWithEmptyResponse() {
        Pageable pageable = PageRequest.of(0, 10);

        when(studentRepository.findAll(pageable)).thenReturn(Page.empty(pageable));

        PagedList<StudentDto> studentPage = studentService.studentPage(pageable);

        assertNotNull(studentPage);

        verify(studentRepository).findAll(pageable);
        // skip to convert student->studentDto in case of empty record from database
        verifyNoInteractions(studentMapper);
    }

    @Test
    void studentPageSuccessfulWithResponse() {
        Pageable pageable = PageRequest.of(0, 10);

        PageImpl<Student> page = new PageImpl<>(
                List.of(Student.builder().id(UUID.randomUUID()).build(),
                        Student.builder().id(UUID.randomUUID()).build()), pageable, 5);

        when(studentRepository.findAll(pageable)).thenReturn(page);

        PagedList<StudentDto> studentPage = studentService.studentPage(pageable);

        assertNotNull(studentPage);

        InOrder inOrder = inOrder(studentRepository, studentMapper);

        inOrder.verify(studentRepository).findAll(pageable);
        inOrder.verify(studentMapper, atLeast(1)).studentToStudentDto(any());

        verify(studentMapper, times(studentPage.getNumberOfElements())).studentToStudentDto(any());

    }

    @Test
    void createStudentSuccessful() {
        when(studentRepository.existsStudentByFullName_DisplayName(any())).thenReturn(false);
        when(studentMapper.studentDtoToStudent(studentDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.studentToStudentDto(student)).thenReturn(studentDto);

        StudentDto response = studentService.createStudent(studentDto);

        assertNotNull(response);

        InOrder inOrder = inOrder(studentRepository, studentMapper);

        inOrder.verify(studentRepository).existsStudentByFullName_DisplayName(any());
        inOrder.verify(studentMapper).studentDtoToStudent(any());
        inOrder.verify(studentRepository).save(any());
        inOrder.verify(studentMapper).studentToStudentDto(any());
    }

    @Test
    void createStudentFail() {
        when(studentRepository.existsStudentByFullName_DisplayName(any())).thenReturn(true);

        assertThrows(DuplicateDataException.class, () -> studentService.createStudent(studentDto));

        verify(studentRepository).existsStudentByFullName_DisplayName(any());
        verify(studentRepository, never()).save(any());
        verifyNoInteractions(studentMapper);
    }

    @Test
    void updateStudentSuccessful() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.studentToStudentDto(student)).thenReturn(studentDto);

        StudentDto response = studentService.updateStudent(studentId, studentDto);

        assertNotNull(response);

        InOrder inOrder = inOrder(studentRepository, studentMapper);

        inOrder.verify(studentRepository).findById(any());
        inOrder.verify(studentRepository).save(any());
        inOrder.verify(studentRepository, never()).delete(any());
        inOrder.verify(studentMapper).studentToStudentDto(any());
        inOrder.verify(studentMapper, never()).studentDtoToStudent(any());
    }

    @Test
    void updateStudentFailure() {
        when(studentRepository.findById(studentId)).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> studentService.updateStudent(studentId, any()));

        verify(studentRepository).findById(any());
        verify(studentRepository, never()).save(any());
        verifyNoInteractions(studentMapper);
    }

    @Test
    void deleteStudentSuccess() {
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        studentService.deleteStudent(studentId);

        verify(studentRepository).findById(studentId);
        verify(studentRepository).delete(student);

        InOrder inOrder = inOrder(studentRepository);

        inOrder.verify(studentRepository).findById(studentId);
        inOrder.verify(studentRepository).delete(student);
    }

    @Test
    void deleteStudentFailure() {
        when(studentRepository.findById(any())).thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> studentService.deleteStudent(any()));

        verify(studentRepository).findById(any());
        verify(studentRepository, never()).delete(any(Student.class));
    }
}