package com.github.hioz.practice;

import com.github.hioz.learn.data.Animal;
import com.github.hioz.learn.data.Person;
import com.github.hioz.learn.data.Pet;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MockTest {

    @Tested
    private Person person;

    @Test
    void mock_entire_object(@Mocked Person person) {
        new Expectations() {{
            // mock instance method
            person.getAge();
            result = 18;

            // mock static method
            Person.getAncestor(anyString);
            result = "God";
        }};

        assertThat(person.getName()).isNull();
        assertThat(person.getAge()).isEqualTo(18);
        assertThat(Person.getAncestor("Alice")).isEqualTo("God");
    }

    @Test
    void partial_mock() {
        Person person = new Person("Bob", 18);
        new Expectations(person) {{
            person.getAge();
            result = 20;

            Person.getAncestor(anyString);
            result = "God";
        }};

        assertThat(person.getName()).isEqualTo("Bob");
        assertThat(person.getAge()).isEqualTo(20);
        assertThat(Person.getAncestor("Bob")).isEqualTo("God");
    }

    @Test
    void mock_nested_member(@Injectable Pet pet) {
        new Expectations() {{
            pet.getAnimal();
            result = Animal.DOG;
        }};

        // person is using @Tested annotation
        assertThat(person.getPet().getAnimal()).isEqualTo(Animal.DOG);
        assertThat(person.getPet().getName()).isNull();
    }

}
