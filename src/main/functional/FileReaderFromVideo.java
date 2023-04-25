package main.functional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileReaderFromVideo {

    private static final Pattern WORD_BREAK = Pattern.compile("\\W+");
    private static final Comparator<Map.Entry<String, Long>> VALUE_ORDER =
            Map.Entry.comparingByValue();
    private static final Comparator<Map.Entry<String, Long>> REVERSED_VALUE =
            VALUE_ORDER.reversed();

    public static void main(String args[]) throws Throwable{

        String fileName = "PrideAndPrejudice.txt";

        // read file to stream
        Files.lines(Paths.get(fileName))// get all the lines form the document
                .flatMap(l -> WORD_BREAK.splitAsStream(l)) // get the words from each line
                .filter(w -> w.length() > 0)
                .map(w -> w.toLowerCase())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))// get a map of words and their number of occurances
                .entrySet().stream()
                .sorted(REVERSED_VALUE)
                .limit(200)// get the first 200 words
                .forEach(l -> System.out.printf("%20s : %5d\n", l.getKey(), l.getValue()));
    }
}
