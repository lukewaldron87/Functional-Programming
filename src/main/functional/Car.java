package main.functional;

import java.util.*;

public class Car {

    private final int gasLevel;
    private final String color;
    private final List<String> passengers;
    private final List<String> trunkContents;

    private Car(int gasLevel, String color, List<String> passengers, List<String> trunkContents) {
        this.gasLevel = gasLevel;
        this.color = color;
        this.passengers = passengers;
        this.trunkContents = trunkContents;
    }

    public static Car withGasColorPassengers(int gas, String color, String... passengers) {
        List<String> p = Collections.unmodifiableList(Arrays.asList(passengers));
        Car self = new Car(gas, color, p, null);
        return self;
    }

    public static Car withGasColorPassengersAndTrunk(int gas, String color, String... passengers) {
        List<String> p = Collections.unmodifiableList(Arrays.asList(passengers));
        Car self = new Car(gas, color, p, Arrays.asList("jack", "wrench", "spare wheel"));
        return self;
    }

    public int getGasLevel() {
        return gasLevel;
    }

    public Car addGas(int g) {
        return new Car(gasLevel + g, color, passengers, trunkContents);
    }

    public String getColor() {
        return color;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    @Override
    public String toString() {
        return "Car{" + "gasLevel=" + gasLevel + ", color=" + color + ", passengers=" + passengers
                + (trunkContents != null ? ", trunkC" +
                "contents=" + trunkContents : " no trunk") + '}';
    }

    public static Criterion<Car> getFourPassengerCriterion(){
        return c -> c.getPassengers().size() == 4;
    }

    public static Criterion getRedCarCriterion(){
        //return new RedCarCriterion();
        return RED_CAR_CRITERION;
    }

    //private static final CarCriterion RED_CAR_CRITERION = (Car c) -> c.getColor().equals("Red");
    private static final Criterion<Car> RED_CAR_CRITERION = c -> c.getColor().equals("Red");

    public static Criterion getGasLevelCriterion(int threshold) {
        return new Criterion<Car>() {
            @Override
            public boolean test(Car c) {
                return c.getGasLevel() >= threshold;
            }
        };
    }

    public static Criterion getPassengerNumberCriterion(int requiredPassengerNumber){
        return new PassengerNumberCriterion(requiredPassengerNumber);
    }

    private static class PassengerNumberCriterion implements Criterion<Car> {

        private int requiredPassengerNumber;

        public PassengerNumberCriterion(int requiredPassengerNumber){
            this.requiredPassengerNumber = requiredPassengerNumber;
        }

        @Override
        public boolean test(Car c) {
            return c.getPassengers().size() >= requiredPassengerNumber;
        }
    }

    public static Comparator<Car> getGasComparator(){
        return GAS_COMPARATOR;
    }
    private static final Comparator<Car> GAS_COMPARATOR =
            (o1, o2) -> o1.getGasLevel() - o2.getGasLevel();
}
