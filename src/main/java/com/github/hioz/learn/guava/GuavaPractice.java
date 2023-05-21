package com.github.hioz.learn.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuavaPractice {

    public static void main(String[] args) {
        practiceLists();
        practiceJoiner();
        practiceSplitter();
    }

    private static void practiceLists() {
        System.out.println("\npractice Lists methods");

        ArrayList<Integer> intList = Lists.newArrayList(1, 2, 3, 4, 5);
        System.out.println("original list = " + intList);

        List<Integer> reversedList = Lists.reverse(intList);
        System.out.println("reversed list = " + reversedList);

        List<List<Integer>> partition = Lists.partition(intList, 2);
        System.out.println("partition = " + partition);
    }

    private static void practiceJoiner() {
        System.out.println("\npractice Joiner methods");
        final String delimiter = ",";

        List<String> list = Lists.newArrayList("a", null, "b", "1", "2");
        String result1 = Joiner.on(delimiter).skipNulls().join(list);
        System.out.println("result1 = " + result1);

        Map<String, Integer> map = Maps.newHashMap();
        map.put("a", 1);
        map.put("b", 2);
        String result2 = Joiner.on(delimiter).withKeyValueSeparator(":").join(map);
        System.out.println("result2 = " + result2);
    }

    private static void practiceSplitter() {
        System.out.println("\npractice Splitter methods");
        final String delimiter = ",";

        String str = "  abc, 123,,haha  ";
        List<String> result1 = Splitter.on(delimiter).splitToList(str);
        System.out.println("split list" + result1);

        List<String> result2 = Splitter.on(delimiter).trimResults().splitToList(str);
        System.out.println("split list with trimmed element" + result2);

        List<String> result3 = Splitter.on(delimiter).omitEmptyStrings().splitToList(str);
        System.out.println("split list (omit empty string)" + result3);
    }

}
