package main.functional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamVersion {

    public static void main(String[] args){
        List<String> strings=
        Arrays.asList("LightCoral", "pink", "Orange", "Gold", "plum", "Blue", "limegreen");

        strings.stream().forEach(s -> System.out.println("> " + s));

        System.out.println("---------------------------------");

        Stream<String> upperCase =
                strings.stream().filter(s -> Character.isUpperCase(s.charAt(0)));
        upperCase.forEach(s -> System.out.println("> "+s));

        System.out.println("---------------------------------");
        strings.forEach(s -> System.out.println("> "+s));

        System.out.println("---------------------------------");
        strings.stream()
                .filter(s -> Character.isUpperCase(s.charAt(0)))
                .map(x -> x.toUpperCase())
                .forEach(x -> System.out.println("> "+x));

        System.out.println("---------------------------------");
        strings.forEach(s -> System.out.println("> "+s));

        System.out.println("---------------------------------");
        List<Car> carList =
            Arrays.asList(
                    Car.withGasColorPassengers(6, "Red", "Fred", "Jim", "Sheila"),
                    Car.withGasColorPassengers(3, "Octarine", "Rincewind", "Ridcully"),
                    Car.withGasColorPassengers(9, "Black", "Weatherwax", "Magrat"),
                    Car.withGasColorPassengers(7, "Green", "Valentine", "Gillian", "Anne", "Dr. Mahmoud"),
                    Car.withGasColorPassengers(6, "Red", "Ender", "Hyrum", "Locke", "Bonzo")
            );

        carList.stream().filter(c -> c.getGasLevel() > 6)
                .map(c -> c.getPassengers().get(0) + " is driving a " + c.getColor()
                        + " car with lots of fuel")
                .forEach(c -> System.out.println("> "+c));


        System.out.println("---------------------------------");
        System.out.println("addGas");
        carList.stream()
                .map(c -> c.addGas(4))
                .forEach(c -> System.out.println(">> " + c));


        System.out.println("---------------------------------");
        carList.stream()
                .filter(c -> c.getPassengers().size() < 4)
                .flatMap(c -> c.getPassengers().stream())
                .map(s -> s.toUpperCase())
                .forEach(s -> System.out.println(s));

        System.out.println("---------------------------------");
        carList.stream()
                .flatMap(c -> c.getPassengers().stream()
                        .map(p -> p + " is riding in a " + c.getColor() + " car"))
                .forEach(s -> System.out.println(s));
    }
}
