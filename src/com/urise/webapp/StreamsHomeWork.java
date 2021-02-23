package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.pow;

public class StreamsHomeWork {
    public static void main(String[] args) {
        int[] values = {2, 4, 2, 6, 3, 4, 8, 6, 5, 8, 4, 2, 4, 6, 9, 5, 6, 9, 1};
        System.out.println(minValue(values));

        List<Integer> integers = Arrays.asList(1, 4, 2, 3, 1);
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        int[] array = IntStream.of(values).distinct().sorted().toArray();
        int size = array.length;
        int min = 0;
        for (int element : array) {
            min += pow(10, size - 1) * element;
            size--;
        }
        return min;
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(Integer::sum).get();
        if (sum % 2 == 0) {
            return integers.stream().filter(x -> x % 2 != 0).collect(Collectors.toList());
        } else {
            return integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        }
    }
}
