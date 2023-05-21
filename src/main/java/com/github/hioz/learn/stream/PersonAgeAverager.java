package com.github.hioz.learn.stream;

import com.github.hioz.learn.data.Person;

public class PersonAgeAverager {

    private double sum = 0;

    private int count = 0;

    void accumulate(Person person) {
        sum += person.getAge();
        count++;
    }

    PersonAgeAverager combine(PersonAgeAverager other) {
        sum += other.sum;
        count += other.count;
        return this;
    }

    double average() {
        return count == 0 ? 0 : sum / count;
    }

}
