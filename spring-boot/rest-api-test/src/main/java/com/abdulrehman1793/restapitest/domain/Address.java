package com.abdulrehman1793.restapitest.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Address extends BaseEntity {
    @Builder
    public Address(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String name, String line, String city, String country, String postalCode, String phone, Student student) {
        super(id, version, createdDate, lastModifiedDate);
        this.name = name;
        this.line = line;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
        this.student = student;
    }

    private String name;
    private String line;
    private String city;
    private String country;
    private String postalCode;
    private String phone;

    @ManyToOne(optional = false)
    private Student student;
}
