package com.abdulrehman1793.restapitest.web.controller;

import com.abdulrehman1793.restapitest.exception.BadRequestException;
import com.abdulrehman1793.restapitest.services.ControllerHelperService;
import com.abdulrehman1793.restapitest.services.StudentService;
import com.abdulrehman1793.restapitest.web.model.StudentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @MockBean
    StudentService studentService;

    @MockBean
    ControllerHelperService controllerHelperService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    UUID studentId;

    @Captor
    ArgumentCaptor<Pageable> pageableArgumentCaptor;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
    }

    @Test
    void studentPageSuccess() throws Exception {
        when(controllerHelperService.sortRequestParameterToSort(StudentController.FIELDS, new String[]{"fistName:DESC"}))
                .thenReturn(Sort.by(Direction.DESC, "firstName"));

        mockMvc.perform(get("/students").param("sort", "fistName:DESC"))
                .andExpect(status().isOk());

        verify(studentService).studentPage(pageableArgumentCaptor.capture());

        Pageable pageable = pageableArgumentCaptor.getValue();

        Order order=pageable.getSort().get().toList().get(0);

        assertNotNull(pageable);
        assertEquals(order.getDirection(), Direction.DESC);
        assertEquals(order.getProperty(), "firstName");
        assertEquals(pageable.getPageSize(), 10);
        assertEquals(pageable.getPageNumber(), 0);
    }

    @Test
    void studentPageInvalidParam() throws Exception {
        mockMvc.perform(get("/students").param("page", "a"))
                .andExpect(status().is4xxClientError());

        verifyNoInteractions(controllerHelperService);
        verifyNoInteractions(studentService);
    }

    @Test
    void createStudent() throws Exception {
        String request = """
                {
                    "fullName": {
                        "firstName": "Abdulrehman",
                        "displayName": "abdulrehman1793"
                    },
                    "gender": "MALE"
                }
                """;

        mockMvc.perform(post("/students")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createStudentInvalidPayload() throws Exception {
        String request = """
                {
                    "fullName": {
                        "firstName": "Abdulrehman",
                        "displayName": ""
                    },
                    "gender": "MALE"
                }
                """;

        mockMvc.perform(post("/students")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(studentService);
    }

    @Test
    void updateStudent() throws Exception {
        when(studentService.updateStudent(studentId, StudentDto.builder().build()))
                .thenReturn(StudentDto.builder().id(studentId).build());

        mockMvc.perform(put("/students/{studentId}", studentId)
                        .content(objectMapper.writeValueAsString(StudentDto.builder().build()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteStudentSuccess() throws Exception {
        doNothing().when(studentService).deleteStudent(studentId);

        mockMvc.perform(delete("/students/{studentId}", studentId)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteStudentFail() throws Exception {
        doThrow(BadRequestException.class).when(studentService).deleteStudent(studentId);

        mockMvc.perform(delete("/students/{studentId}", studentId)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(studentService, times(1)).deleteStudent(studentId);
    }
}