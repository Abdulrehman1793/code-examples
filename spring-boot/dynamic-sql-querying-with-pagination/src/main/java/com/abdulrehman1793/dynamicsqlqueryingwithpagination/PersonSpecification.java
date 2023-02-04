package com.abdulrehman1793.dynamicsqlqueryingwithpagination;

import org.springframework.data.jpa.domain.Specification;

public class PersonSpecification {
    public static Specification<Person> hasFirstName(String firstName) {
        if (firstName != null && !firstName.isBlank())
            return ((root, query, criteriaBuilder) -> {
                return criteriaBuilder.equal(root.get("firstname"), firstName);
            });
        return null;
    }
}
