package main.functional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class PassengerCounterOrder implements Comparator<Car>{

    @Override
    public int compare(Car o1, Car o2) {
        return o1.getPassengers().size() - o2.getPassengers().size();
    }
}

public class CarScratch {

    public static <E> void showAll(List<E> lc) {
        for (E c : lc) {
            System.out.println(c);
        }
        System.out.println("-------------------------------------");
    }

    public static <E> List<E> getByCriterion(Iterable<E> in, Criterion<E> crit) {
        //System.out.println(crit.getClass().getSimpleName());

        List<E> output = new ArrayList<>();
        for (E c: in){
            if(crit.test(c)){
                output.add(c);
            }
        }
        return output;
    }

    public static void main(String[] args) {
        List<Car> cars = Arrays.asList(
                Car.withGasColorPassengers(6, "Red", "Fred", "Jim", "Sheila"),
                Car.withGasColorPassengers(3, "Octarine", "Rincewind", "Ridcully"),
                Car.withGasColorPassengers(9, "Black", "Weatherwax", "Magrat"),
                Car.withGasColorPassengers(7, "Green", "Valentine", "Gillian", "Anne", "Dr. Mahmoud"),
                Car.withGasColorPassengers(6, "Red", "Ender", "Hyrum", "Locke", "Bonzo")
        );

        showAll(cars);

        System.out.println("Red Cars");
        showAll(getByCriterion(cars, Car.getRedCarCriterion()));

        System.out.println("Gas Level");
        showAll(getByCriterion(cars, Car.getGasLevelCriterion(6)));

        System.out.println("Passengers");
        showAll(getByCriterion(cars, Car.getPassengerNumberCriterion(3)));

        //cars.sort(new PassengerCounterOrder());
        showAll(cars);
        cars.sort(Car.getGasComparator());
        System.out.println("Sorted by gas level");
        showAll(cars);

        System.out.println("2 passengers");
        showAll(getByCriterion(cars, c -> c.getPassengers().size() == 2));

        System.out.println("4 passengers");
        showAll(getByCriterion(cars, Car.getFourPassengerCriterion()));

        /*List<String> colours = Arrays.asList("LightCoral", "pink", "Orange", "Gold", "plum", "Blue", "limegreen");

        System.out.println("Colours with greater than 4 characters");
        showAll(getByCriterion(colours, st -> st.length() > 4));

        System.out.println("Colours with capital letters");
        showAll(getByCriterion(colours, st -> Character.isUpperCase(st.charAt(0))));

        LocalDate today = LocalDate.now();
        List<LocalDate> dates = Arrays.asList(today, today.plusDays(1), today.plusDays(7), today.minusDays(1));

        System.out.println("appointments in the future");
        showAll(getByCriterion(dates, localDate -> localDate.isAfter(today)));*/

        /*System.out.println("gas lever 7");
        showAll(getByCriterion(cars, Car.getGasLevelCriterion(7)));
        System.out.println("gas lever 4");
        showAll(getByCriterion(cars, Car.getGasLevelCriterion(4)));

        System.out.println("colour criterion");
        String[] colours = {"Red", "Black"};
        showAll(getByCriterion(cars, Car.getColourCriterion(colours)));*/

        System.out.println("negate test");
        Criterion<Car> level7 = Car.getGasLevelCriterion(7);
        showAll(getByCriterion(cars,level7));
        Criterion<Car> notLevel7 = Criterion.negate(level7);
        showAll(getByCriterion(cars,notLevel7));

        System.out.println("AND test");
        Criterion<Car> gasLevel6 = Car.getGasLevelCriterion(6);
        Criterion<Car> redCar = Car.getRedCarCriterion();
        showAll(getByCriterion(cars, Criterion.and(gasLevel6, redCar)));
        System.out.println("AND test from video");
        Criterion<Car> isRed = Car.getColourCriterion("Red");
        Criterion<Car> fourPassengers = Car.getFourPassengerCriterion();
        Criterion<Car> redFourPassengers = Criterion.and(fourPassengers, isRed);
        showAll(getByCriterion(cars, redFourPassengers));

        System.out.println("OR test");
        Criterion<Car> gasLevel7 = Car.getGasLevelCriterion(7);
        showAll(getByCriterion(cars, Criterion.or(gasLevel7, isRed)));
        System.out.println("OR test from video");
        Criterion<Car> isBlack = Car.getColourCriterion("Black");
        Criterion<Car> blackFourPassengers = Criterion.or(fourPassengers, isBlack);
        showAll(getByCriterion(cars, blackFourPassengers));
    }
}
