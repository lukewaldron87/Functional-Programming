package main.functional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

    public static void main(String args[]){

        String fileName = "C:\\Users\\Luke\\projects\\Functional Programming\\PrideAndPrejudice.txt";

        // read file to stream
        try(Stream<String> stream = Files.lines(Paths.get(fileName))){
            //print each line
            //stream.forEach(s -> System.out.println(s));

            // extract words from lines
            Pattern pattern = Pattern.compile("\\W+");;
            Stream<String> wordsStream = stream
                    .flatMap(line -> pattern.splitAsStream(line))
                    .filter(x -> !x.isEmpty())
                    .map(w -> w.toLowerCase());
            //wordsStream.forEach(w -> System.out.println(w));

            //count the number of word occurrences
            Map<String, Long> wordCount = wordsStream.
                    collect(Collectors.groupingBy(word -> word.toString(), Collectors.counting()));

            // sort ascending
            Comparator<Map.Entry<String, Long>> comparator =
                    Map.Entry.comparingByValue();

            //print the table
            wordCount.entrySet().stream().sorted(comparator)
                    .forEach(e -> System.out.println("Word \"" + e.getKey() + "\" appears " + e.getValue() + " times"));

        }catch (IOException e){
            System.out.println(e);
        }
    }
}
