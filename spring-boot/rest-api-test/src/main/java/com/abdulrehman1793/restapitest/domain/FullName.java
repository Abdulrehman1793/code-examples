package com.abdulrehman1793.restapitest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FullName {
    @NotEmpty(message = "Student first name is required.")
    private String firstName;
    private String lastName;

    @NotEmpty(message = "Student display name is required.")

    @Column(unique = true, updatable = false)
    private String displayName;

    @Transient
    private String fullName;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
