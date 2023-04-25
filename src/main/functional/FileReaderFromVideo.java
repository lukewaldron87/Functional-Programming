package main.functional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReaderFromVideo {

    private static final Pattern WORD_BREAK = Pattern.compile("\\W+");
    private static final Comparator<Map.Entry<String, Long>> VALUE_ORDER =
            Map.Entry.comparingByValue();
    private static final Comparator<Map.Entry<String, Long>> REVERSED_VALUE =
            VALUE_ORDER.reversed();

    public static <E, F> Function<E, Optional<F>> wrap(ExceptionFunction<E, F> op){
        return e -> {
          try {
              return Optional.of(op.apply(e));
          }catch (Throwable t){
              return Optional.empty();
          }
        };
    }

    /*public static Optional<Stream<String>> lines(Path p){
        try{
            return Optional.of(Files.lines(p));
        }catch (IOException ioe){
            System.out.println("File read failed: " + ioe.getMessage());
            return Optional.empty();
        }
    }*/

    public static void main(String args[]) throws Throwable{

        List<String> fileNames = Arrays.asList(
                "PrideAndPrejudice.txt",
                "BadBook.txt",
                "Emma.txt",
                "SenseAndSensibility.txt"
        );

        String fileName = "PrideAndPrejudice.txt";

        fileNames.stream()
                .map(Paths::get)// get a file from the list
                //.map(FileReaderFromVideo::lines)// read the lines of the file
                .map(wrap(p -> Files.lines(p)))// read the lines of the file
                .peek(o -> {if (!o.isPresent()) System.err.println("Bad File!");})// check if the optional is empty
                .filter(Optional::isPresent)//discard empty optionals
                .flatMap(Optional::get)//extract the stream of string for the valid optionals
                //.flatMap(l -> WORD_BREAK.splitAsStream(l)) // get the words from each line
                .flatMap(WORD_BREAK::splitAsStream) // get the words from each line
                .filter(w -> w.length() > 0)
                //.map(w -> w.toLowerCase())
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))// get a map of words and their number of occurances
                .entrySet().stream()
                .sorted(REVERSED_VALUE)
                .limit(200)// get the first 200 words
                .map(l -> String.format("%20s : %5d", l.getKey(), l.getValue()))
                .forEach(l -> System.out.println(l));
    }
}
