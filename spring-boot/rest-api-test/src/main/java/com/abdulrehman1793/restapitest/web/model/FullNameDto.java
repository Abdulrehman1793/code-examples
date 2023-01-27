package com.abdulrehman1793.restapitest.web.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FullNameDto {
    @NotEmpty(message = "Student first name is required")
    private String firstName;
    private String lastName;
    @NotEmpty(message = "Student display name is required")
    private String displayName;
}
