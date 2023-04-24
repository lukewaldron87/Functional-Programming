package main.functional;


import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

class Averager {

    private double total;
    private long count;

    public Averager(){

    }

    public void include(double d){
        total += d;
        count++;
    }

    public void merge(Averager other){
        total += other.total;
        count += other.count;
    }

    public double get(){
        return total / count;
    }
}
public class CollectAverage {

    public static void main(String[] args){
        long start = System.nanoTime();
        Averager result =
        DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-Math.PI, Math.PI))
                //.parallel()
                //.unordered() // Stream.generate is already unordered...
                .limit(200_000_000L)
                .map(x -> Math.sin(x))
                .collect(() -> new Averager(), (b, i) -> b.include(i), (b1, b2) -> b1.merge(b2));
        long end = System.nanoTime();

        System.out.println("Average is " + result.get() + " computation took " +
                ((end - start) / 1_000_000) + " ms");
    }
}
