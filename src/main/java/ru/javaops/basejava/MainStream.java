package ru.javaops.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        int[] array = {3, 1, 2, 3, 3, 2, 3};
        System.out.println(minValue(array));

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(oddOrEven(list));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().reduce(0, Integer::sum) % 2 == 0 ?
                filterOddOrEven(integers, p -> p % 2 != 0) : filterOddOrEven(integers, p -> p % 2 == 0);
    }

    private static List<Integer> filterOddOrEven(List<Integer> integers, Predicate<Integer> predicate) {
        return integers.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
