package com.github.hioz.learn.stream;

import com.github.hioz.learn.data.Person;

import java.util.stream.Collector;

public class CustomCollector {

    public static Collector<Person, PersonAgeAverager, Double> newUserAgeAverager() {
        return Collector.of(
                // supplier
                PersonAgeAverager::new,
                // accumulator
                PersonAgeAverager::accumulate,
                // combiner
                PersonAgeAverager::combine,
                // finisher
                PersonAgeAverager::average
        );
    }

}
