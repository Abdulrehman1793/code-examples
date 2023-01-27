package com.abdulrehman1793.restapitest.web.model;

import com.abdulrehman1793.restapitest.domain.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StudentDto extends BaseItem {
    @Builder
    public StudentDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, FullNameDto fullName, Gender gender, LocalDate dob, String avatarUrl, String email, String phone, Set<AddressDto> addresses) {
        super(id, version, createdDate, lastModifiedDate);
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.phone = phone;
        this.addresses = addresses;
    }

    private @Valid FullNameDto fullName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    private LocalDate dob;
    private String avatarUrl;

    @Email
    private String email;

    private String phone;
    private Set<AddressDto> addresses = new HashSet<>();
}
