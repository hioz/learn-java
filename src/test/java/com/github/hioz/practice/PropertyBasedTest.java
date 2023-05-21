package com.github.hioz.practice;

import com.github.hioz.learn.data.Person;
import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import net.jqwik.api.lifecycle.BeforeContainer;
import org.assertj.core.data.Offset;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyBasedTest {

    private static Arbitrary<Person> persons;

    @BeforeContainer
    static void beforeContainer() {
        persons = providePersonsByCombinator();
    }

    private static Arbitrary<Person> providePersonsByCombinator() {
        Arbitrary<String> names = Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(20);
        Arbitrary<Integer> ages = Arbitraries.integers().between(1, 100);

//      return Combinators.combine(names, ages).as((name, age) -> new Person(name, age));
        return Combinators.combine(names, ages).as(Person::new);
    }


    // return a boolean value that signifies success (true) or failure (false) of this property
    @Property
    boolean abs_numbers_except_min_value_is_positive(
            @ForAll @IntRange(min = Integer.MIN_VALUE + 1) int anInteger) {
        return Math.abs(anInteger) >= 0;
    }

    // return nothing (void)
    // In that case you will probably use assertions to check the property’s invariant
    @Property
    void length_of_concatenated_string_is_greater_equal_length_of_each(
            @ForAll String str1, @ForAll @WithNull String str2) {
        if (str2 == null) {
            str2 = "";
        }
        String concatenated = str1 + str2;
        assertThat(concatenated.length()).isGreaterThanOrEqualTo(str1.length());
        assertThat(concatenated.length()).isGreaterThanOrEqualTo(str2.length());
    }


    // jqwik also supports example-based testing
    @Example
    void square_root_of_16_is_4() {
        assertThat(Math.sqrt(16)).isCloseTo(4.0, Offset.offset(0.01));
    }

    @Example
    boolean add_1_plus_2_is_3() {
        return (1 + 2) == 3;
    }


    // Customized Parameter Generation
    @Property
    void maps_from_Number_to_string(@ForAll("provideMap") Map<Integer, String> map) {
        assertThat(map.keySet()).allMatch(key -> key >= 0 && key <= 1000);
        assertThat(map.values()).allMatch(value -> value.length() == 5);
    }

    // Arbitrary Provider Method
    @Provide
    Arbitrary<Map<Integer, String>> provideMap() {
        Arbitrary<Integer> keys = Arbitraries.integers().between(1, 100);
        Arbitrary<String> values = Arbitraries.strings().alpha().ofLength(5);
        return Arbitraries.maps(keys, values);
    }

    @Property
    boolean concatenating_string_with_int(
            @ForAll("provide_short_strings") String aShortString,
            @ForAll("10 to 99") int aNumber) {
        String concatenated = aShortString + aNumber;
        return concatenated.length() > 2 && concatenated.length() < 11;
    }

    @Provide
    Arbitrary<String> provide_short_strings() {
        return Arbitraries.strings().withCharRange('a', 'z')
                .ofMinLength(1).ofMaxLength(8);
    }

    // Assign a reference name
    @Provide("10 to 99")
    Arbitrary<Integer> provide_10_to_99_numbers() {
        return Arbitraries.integers().between(10, 99);
    }


    // Uniqueness Constraints
    @Property
    void unique_int_list(
            @ForAll @Size(5) @UniqueElements List<@IntRange(min = 0, max = 10) Integer> list) {
        assertThat(list).size().isEqualTo(5);
        assertThat(list).doesNotHaveDuplicates();
        assertThat(list).allMatch(anInt -> anInt >= 0 && anInt <= 10);
    }

    @Property
    void list_of_strings_the_first_char_must_be_unique(
            @ForAll @Size(max = 25) @UniqueElements(by = FirstChar.class)
            List<@AlphaChars @StringLength(min = 1, max = 10) String> listOfStrings) {
        Iterable<Character> firstCharacters = listOfStrings.stream()
                .map(s -> s.charAt(0))
                .collect(Collectors.toList());
        assertThat(firstCharacters).doesNotHaveDuplicates();
    }

    private static class FirstChar implements Function<String, Object> {
        @Override
        public Object apply(String aString) {
            return aString.charAt(0);
        }
    }


    // Provide Object
    @Property
    void age_between_1_and_100(@ForAll("providePersonByBuilder") Person person) {
        assertThat(person.getAge()).isBetween(1, 100);
    }

    @Provide
    Arbitrary<Person> providePersonByBuilder() {
        Arbitrary<String> names = Arbitraries.strings().alpha().ofMinLength(3).ofMaxLength(20);
        Arbitrary<Integer> ages = Arbitraries.integers().between(1, 100);

        return Builders.withBuilder(() -> new Person(null, -1))
                // has 50% probability to do not set name
                .use(names).withProbability(0.5).inSetter(Person::setName)
                .use(ages).inSetter(Person::setAge)
                .build();
    }

    @Property
    void list_of_people_with_unique_names(@ForAll("providePersonList") List<Person> people) {
        List<String> names = people.stream().map(Person::getName).collect(Collectors.toList());
        assertThat(names).doesNotHaveDuplicates();
    }

    @Provide
    Arbitrary<List<Person>> providePersonList() {
        return persons.list().uniqueElements(Person::getName);
    }


    // Functional Types
    @Property
    void from_int_to_string(@ForAll Function<Integer, @StringLength(5) String> function) {
        assertThat(function.apply(42)).hasSize(5);
        assertThat(function.apply(1)).isEqualTo(function.apply(1));
    }


    // Generic Types
    @Property
    <T> boolean unbounded_generic_types_are_resolved(@ForAll List<T> items, @ForAll T newItem) {
        items.add(newItem);
        return items.contains(newItem);
    }

}
