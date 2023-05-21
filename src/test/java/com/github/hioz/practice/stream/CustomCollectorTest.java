package com.github.hioz.practice.stream;

import com.github.hioz.learn.data.Person;
import com.github.hioz.learn.stream.CustomCollector;
import com.github.hioz.learn.stream.PersonAgeAverager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import static org.junit.jupiter.api.Assertions.*;

class CustomCollectorTest {

    private static final List<Person> persons = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        persons.add(new Person("John", 15));
        persons.add(new Person("Mary", 18));
        persons.add(new Person("Tom", 27));
    }

    @Test
    void average() {
        Collector<Person, PersonAgeAverager, Double> averager = CustomCollector.newUserAgeAverager();
        Double ageMean = persons.stream().collect(averager);

        // (15 + 18 + 27) / 3 = 20
        assertEquals(20, ageMean);
    }

}