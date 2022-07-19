package hu.ptomi.pattern;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * MediatorPattern = defines an object that orchestrates the interactions between a set of objects
 * <p>
 * JDK Use Case: java 7 streams, ForkJoinPool
 */
public class MediatorPattern {
    public static void main(String[] args) {
        System.out.println(
                // mediator is the reference pipeline as stream is recursively composed
                IntStream
                        .range(1, 1_000)
                        .parallel()
                        .map(i -> {
//                            System.out.println(Thread.currentThread().getName());
                            return i * ThreadLocalRandom.current().nextInt(1, 100);
                        })
                        .sum()
        );
    }
}
