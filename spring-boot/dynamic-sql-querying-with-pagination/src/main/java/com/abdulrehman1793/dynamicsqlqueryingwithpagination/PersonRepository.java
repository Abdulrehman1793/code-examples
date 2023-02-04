package com.abdulrehman1793.dynamicsqlqueryingwithpagination;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PersonRepository extends JpaRepository<Person, Long>,
        QuerydslPredicateExecutor<Person>, JpaSpecificationExecutor<Person> {
}
