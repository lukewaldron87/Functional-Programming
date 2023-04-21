package main.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class SuperIterable <E> implements Iterable<E>{

    private Iterable<E> self;

    public SuperIterable(Iterable s){
        self = s;
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
    }
}
