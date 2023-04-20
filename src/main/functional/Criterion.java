package main.functional;

@FunctionalInterface
public interface Criterion<E> {
    boolean test(E e);

    default Criterion<E> negate(){
        return x -> !this.test(x);
    }

    //assignment: create AND criterion
    default Criterion <E> and(Criterion<E> second){
        return x -> this.test(x) && second.test(x);
    }

    //assignment: create OR criterion
    default Criterion <E> or(Criterion<E> second){
        return x -> this.test(x) || second.test(x);
    }
}
