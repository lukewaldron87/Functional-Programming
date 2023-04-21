package main.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class SuperIterable <E> implements Iterable<E>{

    private Iterable<E> self;

    public SuperIterable(Iterable<E> s){
        self = s;
    }

    public <F> SuperIterable<F> flatMap(Function<E, SuperIterable<F>> op){
        List<F> results = new ArrayList<>();
        self.forEach(e -> op.apply(e).forEach(f -> results.add(f)));
        return  new SuperIterable<>(results);
    }

    public <F> SuperIterable<F> map(Function<E, F> op){
        List<F> results = new ArrayList<>();
        self.forEach(e -> results.add(op.apply(e)));
        return new SuperIterable<>(results);
    }

    public SuperIterable<E> filter(Predicate<E> pred){
        List<E> results = new ArrayList<>();

        /*for (E e: self){
            if(pred.test(e)){
                results.add(e);
            }
        }*/

        self.forEach( e -> {
            if(pred.test(e)){
                results.add(e);
            }
        });

        return new SuperIterable<>(results);
    }

    /*public void forEvery(Consumer<E> cons){
        for (E e: self){
            cons.accept(e);
        }
    }*/

    @Override
    public Iterator<E> iterator() {
        return self.iterator();
    }

    public static void main(String[] args){
        SuperIterable<String> strings = new SuperIterable<>(
                Arrays.asList("LightCoral", "pink", "Orange", "Gold", "plum", "Blue", "limegreen")
        );

        strings.forEach(s -> System.out.println("> "+s));

        SuperIterable<String> upperCase =
                strings.filter(s -> Character.isUpperCase(s.charAt(0)));

        System.out.println("---------------------------------");
        upperCase.forEach(s -> System.out.println("> "+s));

        System.out.println("---------------------------------");
        strings.forEach(s -> System.out.println("> "+s));

        System.out.println("---------------------------------");
        strings
                .filter(s -> Character.isUpperCase(s.charAt(0)))
                .map(x -> x.toUpperCase())
                .forEach(x -> System.out.println("> "+x));

        System.out.println("---------------------------------");
        strings.forEach(s -> System.out.println("> "+s));



        System.out.println("---------------------------------");
        SuperIterable<Car> carIter = new SuperIterable<>(
            Arrays.asList(
                Car.withGasColorPassengers(6, "Red", "Fred", "Jim", "Sheila"),
                Car.withGasColorPassengers(3, "Octarine", "Rincewind", "Ridcully"),
                Car.withGasColorPassengers(9, "Black", "Weatherwax", "Magrat"),
                Car.withGasColorPassengers(7, "Green", "Valentine", "Gillian", "Anne", "Dr. Mahmoud"),
                Car.withGasColorPassengers(6, "Red", "Ender", "Hyrum", "Locke", "Bonzo")
            )
        );

        carIter.filter(c -> c.getGasLevel() > 6)
                .map(c -> c.getPassengers().get(0) + " is driving a " + c.getColor()
                    + " car with lots of fuel")
                .forEach(c -> System.out.println("> "+c));


        System.out.println("---------------------------------");
        System.out.println("addGas");
        carIter
            .map(c -> c.addGas(4))
            .forEach(c -> System.out.println(">> " + c));


        System.out.println("---------------------------------");
        carIter
                .filter(c -> c.getPassengers().size() < 4)
                .flatMap(c -> new SuperIterable<>(c.getPassengers()))
                .map(s -> s.toUpperCase())
                .forEach(s -> System.out.println(s));

        System.out.println("---------------------------------");
        carIter
                .flatMap(c -> new SuperIterable<>(c.getPassengers())
                        .map(p -> p + " is riding in a " + c.getColor() + " car"))
                .forEach(s -> System.out.println(s));
    }
}
