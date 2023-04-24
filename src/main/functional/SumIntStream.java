package main.functional;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class SumIntStream {
    public static void main(String[] args){
        OptionalInt optionalInt = IntStream.iterate(0, i -> i + 1)
                .limit(100)
                .reduce((a, b) -> a + b);

        optionalInt.ifPresent(i -> System.out.println(i));
    }
}
