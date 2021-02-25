package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamsHomeWork {
    public static void main(String[] args) {
        int[] values = {2, 4, 2, 6, 3, 4, 8, 6, 5, 8, 4, 2, 4, 6, 9, 5, 6, 9, 1};
        System.out.println(minValue(values));

        List<Integer> integers = Arrays.asList(1, 4, 2, 3, 1, 1);
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(IntStream.of(values).distinct().sorted().toArray()).reduce(0, (left, right) -> left * 10 + right);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().reduce(0, Integer::sum) % 2 == 0 ?
                integers.stream().filter(x -> x % 2 != 0).collect(Collectors.toList()) :
                integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
    }
}
