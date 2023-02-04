package com.abdulrehman1793.dynamicsqlqueryingwithpagination;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Data
@Entity
@QueryEntity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private Instant dob;
    private Integer budget;
}
