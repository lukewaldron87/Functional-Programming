package main.functional;

public interface ExceptionFunction<E, F> {
    F apply(E e) throws Throwable;
}
