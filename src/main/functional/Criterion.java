package main.functional;

@FunctionalInterface
public interface Criterion<E> {
    boolean test(E e);

    static <E> Criterion<E> negate(Criterion<E> crit){
        return x -> !crit.test(x);
    }

    //assignment: create AND criterion
    static <E> Criterion <E> and(Criterion<E> first, Criterion<E> second){
        return x -> first.test(x) && second.test(x);
    }

    //assignment: create OR criterion
    static <E> Criterion <E> or(Criterion<E> first, Criterion<E> second){
        return x -> first.test(x) || second.test(x);
    }
}
