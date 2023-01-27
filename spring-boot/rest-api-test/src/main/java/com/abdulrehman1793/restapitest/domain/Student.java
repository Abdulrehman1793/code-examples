package com.abdulrehman1793.restapitest.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Student extends BaseEntity {
    @Builder
    public Student(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, FullName fullName, String avatarUrl, String email, String phone, Gender gender, LocalDate dob, Set<Address> addresses) {
        super(id, version, createdDate, lastModifiedDate);
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.addresses = addresses;
    }

    @Embedded
    private @Valid FullName fullName;

    @URL
    private String avatarUrl;

    @Email
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dob;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "student")
    private Set<Address> addresses;

    public void addAddress(Address address) {
        if (addresses == null)
            addresses = new HashSet<>();
        addresses.add(address);
        address.setStudent(this);
    }
}
