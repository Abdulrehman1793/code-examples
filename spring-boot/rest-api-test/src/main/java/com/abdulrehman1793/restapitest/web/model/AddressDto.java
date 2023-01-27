package com.abdulrehman1793.restapitest.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AddressDto extends BaseItem {
    @Builder
    public AddressDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, String name, String line, String city, String country, String postalCode, String phone, UUID studentID) {
        super(id, version, createdDate, lastModifiedDate);
        this.name = name;
        this.line = line;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
        this.studentID = studentID;
    }

    private String name;
    private String line;
    private String city;
    private String country;
    private String postalCode;
    private String phone;

    private UUID studentID;
}
