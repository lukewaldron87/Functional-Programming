package main.functional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Student {

    private static final NavigableMap<Integer, String> gradeLetters = new TreeMap<>();

    static {
        gradeLetters.put(90, "A");
        gradeLetters.put(80, "B");
        gradeLetters.put(70, "C");
        gradeLetters.put(60, "D");
        gradeLetters.put(50, "E");
        gradeLetters.put(0, "F");
    }

    private String name;
    private int score;

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getLetterGrade() {
        return gradeLetters.floorEntry(score).getValue();
    }

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + ", " + score + "%, grade is " + getLetterGrade();
    }

    public static void main(String[] args) {
        List<Student> school = Arrays.asList(
                new Student("Fred", 71),
                new Student("Jim", 38),
                new Student("Sheila", 97),
                new Student("Weatherwax", 100),
                new Student("Ogg", 56),
                new Student("Rincewind", 28),
                new Student("Ridcully", 65),
                new Student("Magrat", 79),
                new Student("Valentine", 93),
                new Student("Gillian", 87),
                new Student("Anne", 91),
                new Student("Dr. Mahmoud", 88),
                new Student("Ender", 91),
                new Student("Hyrum", 72),
                new Student("Locke", 91),
                new Student("Bonzo", 57));

        school.forEach(s -> System.out.println(s));

        Map<String, List<Student>> table = school.stream()
                .collect(Collectors.groupingBy(s -> s.getLetterGrade()));

        //Comparator<Map.Entry<String, List<Student>>> comparator =
        //        (e1, e2) -> e2.getKey().compareTo(e1.getKey());

        Comparator<Map.Entry<String, List<Student>>> comparator =
                Map.Entry.comparingByKey();
        comparator = comparator.reversed();

        table.entrySet().stream()
                .sorted(comparator)
                .forEach(e -> System.out.println(
                        "Students " + e.getValue() +
                        "have grade " + e.getKey()));

        System.out.println("-------------------------------");

        Map<String, Long> table2 = school.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getLetterGrade(),
                        Collectors.counting()));

        table2.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println(e.getValue() + " students got grade " + e.getKey()));

        System.out.println("-------------------------------");

        // create output ordered by grade letter
        // output form: students (fred, jim, sheila) achieve grade LETTER
        // use 2 downstream collectors in a stream
        // 1 will take the student and extract the name
        // the other will concatenate string values

        Map<String, List<String>> assignmentTable = school.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getLetterGrade(),
                        Collectors.mapping(student -> student.getName(),Collectors.toList())));

        assignmentTable.entrySet().stream()
                .forEach(e -> System.out.println(
                                e.getValue().stream().map(s -> s.toString()+" ").reduce(" ", String::concat) +
                                "achieve grade " + e.getKey()));
    }
}
