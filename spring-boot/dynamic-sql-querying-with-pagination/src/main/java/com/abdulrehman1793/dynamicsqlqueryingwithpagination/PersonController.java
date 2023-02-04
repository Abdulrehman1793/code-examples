package com.abdulrehman1793.dynamicsqlqueryingwithpagination;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public Page<Person> getPersons(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "500") int size,
            @RequestParam(name = "firstname", required = false) String firstname,
            @RequestParam(name = "lastname", required = false) String lastname,
            @RequestParam(name = "budget", required = false) Integer budget,
            @RequestParam(name = "dobLimit", required = false) Long dobLimit) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (firstname != null && !firstname.isEmpty()) {
            booleanBuilder.and(QPerson.person.firstname.eq(firstname));
        }

        if (lastname != null && !lastname.isEmpty()) {
            booleanBuilder.and(QPerson.person.lastname.eq(lastname));
        }

        if (budget != null && budget != 0) {
            booleanBuilder.and(QPerson.person.budget.goe(budget));
        }

        if (dobLimit != null && dobLimit != 0) {
            booleanBuilder.and(
                    QPerson.person.dob.before(Instant.ofEpochSecond(dobLimit)));
        }

        return personRepository.findAll(booleanBuilder.getValue(),
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
    }

    @GetMapping("/simplified")
    public Page<Person> getPersonsSimplified(
            @QuerydslPredicate(root = Person.class) Predicate predicate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "500") int size) {

        return personRepository.findAll(predicate, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
    }

    @GetMapping("/specification")
    public Page<Person> getUsingSpecification(
            @RequestParam(name = "firstname", required = false) String firstname,
            @RequestParam(name = "lastname", required = false) String lastname,
            @RequestParam(name = "budget", required = false) Integer budget,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "500") int size) {

        Specification<Person> specification = Specification.where(PersonSpecification.hasFirstName(firstname));

        return personRepository.findAll(specification,
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
    }

}

