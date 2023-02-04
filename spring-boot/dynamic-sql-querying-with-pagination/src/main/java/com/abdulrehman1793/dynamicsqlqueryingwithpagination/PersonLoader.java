package com.abdulrehman1793.dynamicsqlqueryingwithpagination;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PersonLoader implements CommandLineRunner {

    private final PersonRepository personRepository;

    public PersonLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading entries...");

        String[] firstnames = {"Tom", "Max", "Anna", "Hanna", "Mike", "Duke", "Fred",
                "Tim", "Paul", "Luke", "Tobias", "Timi", "Michelle", "Thomas", "Andrew"};
        String[] lastnames = {"Smith", "Taylor", "Williams", "Hammer", "Lewis", "Jones",
                "Evans", "Harris", "Mayer", "Schmid"};

        LocalDateTime initDate = LocalDateTime.of(1990, 12, 12, 12, 12);

        List<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000; i++) {
            Person p = new Person();
            p.setBudget(ThreadLocalRandom.current().nextInt(10000));
            p.setDob(Instant.ofEpochSecond(initDate.plusDays(i).toEpochSecond(ZoneOffset.UTC)));
            p.setFirstname(firstnames[ThreadLocalRandom.current().nextInt(0, firstnames.length)]);
            p.setLastname(lastnames[ThreadLocalRandom.current().nextInt(0, lastnames.length)]);
            persons.add(p);
        }
        personRepository.saveAll(persons);

        System.out.println("...Finished loading 10.000 entities");
    }

}
