package com.abdulrehman1793.restapitest.web.controller;

import com.abdulrehman1793.restapitest.services.ControllerHelperService;
import com.abdulrehman1793.restapitest.services.StudentService;
import com.abdulrehman1793.restapitest.web.model.PagedList;
import com.abdulrehman1793.restapitest.web.model.StudentDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final ControllerHelperService controllerHelperService;

    final static String[] FIELDS = new String[]{"firstName", "email", "phone", "lastModifiedDate"};

    @GetMapping
    public ResponseEntity<PagedList<StudentDto>> studentPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "lastModifiedDate:DESC", required = false) String[] sorts) {

        Pageable pageable = PageRequest.of(page, size, controllerHelperService.sortRequestParameterToSort(FIELDS, sorts));

        return ResponseEntity.ok(studentService.studentPage(pageable));
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.createStudent(studentDto));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(
            @PathVariable("studentId") UUID id,
            @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDto));
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") UUID studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok("Record deleted successfully");
    }
}
