package hu.ptomi.pattern.exxtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * sequential groupingBy() # in 483ms // creates one hashmap
 * parallelEffectivelyImmutable groupingBy() ################################################################ in 107ms // "in divide and conquer" the vm creates a lot of hashmaps and merge them together which takes a lot of time
 * parallelThreadSafe groupingBy() # in 1860ms // it ends up in contention and slows down
 * <p>
 * On a very good machine 52 cores and two cpu sockets the parallelThreadSafe is the slowest and slower then in my machine,
 * it is obvious because there is a lot of shared memory between the two sockets and they move data between each other,
 * which takes a lot of extra time beside the task the cpu is executing.
 */
public class CollectorsTest {
    private static final int NUMBERS = 10_000_000;
    private static Collector<Integer, ?, Map<Integer, Long>> NORMAL_GROUPER =
            Collectors
                    .groupingBy(
                            Function.identity(), // all keys = 0,1,2,3,4 10 million times each
                            () -> {
                                System.out.print("#"); // signs that a new hashmap is created
                                return new HashMap<>();
                            },
                            Collectors.counting() // count all 0,1,2,3,4 keys, each 10 million times
                    );
    private static Collector<Integer, ?, ConcurrentMap<Integer, Long>> CONCURRENT_GROUPER =
            Collectors
                    .groupingByConcurrent(
                            Function.identity(),
                            () -> {
                                System.out.print("#");
                                return new ConcurrentHashMap<>();
                            },
                            Collectors.counting()
                    );

    public static void main(String[] args) {
        var numbers = new ArrayList<Integer>();
        long nanos = System.nanoTime();
        try {
            for (int i = 0; i < NUMBERS; i++) {
                for (int j = 0; j < 4; j++) {
                    numbers.add(j); // 0,1,2,3,4,  0,1,2,3,4 repeats 10 million times
                }
            }
            Collections.shuffle(numbers);
        } finally {
            nanos = System.nanoTime() - nanos;
            System.out.printf("time = %dms%n", (nanos / 1_000_000));
            System.out.println();
        }
        for (int i = 0; i < 10; i++) { // to prevent wrong test data due to vm compiler and optimizer
            test(numbers);
        }
    }

    private static void test(List<Integer> numbers) {
        sequential(numbers);
        parallelEffectivelyImmutable(numbers);
        parallelThreadSafe(numbers);
        System.out.println();
    }

    private static void sequential(List<Integer> numbers) {
        System.out.print("sequential groupingBy() ");
        timer(numbers.stream(), NORMAL_GROUPER); // 4 hashmap will be created
    }

    private static void parallelEffectivelyImmutable(List<Integer> numbers) {
        System.out.print("parallelEffectivelyImmutable groupingBy() ");
        timer(numbers.stream().parallel(), NORMAL_GROUPER); // every time we have a parallel fork we create a new hashmap and merge these hashmaps together
    }

    private static void parallelThreadSafe(List<Integer> numbers) {
        System.out.print("parallelThreadSafe groupingByConcurrent() ");
        timer(numbers.stream().parallel(), CONCURRENT_GROUPER); // every time we have a parallel fork we create a new hashmap and merge these hashmaps together
    }

    private static <M extends Map<Integer, Long>> void timer(
            Stream<Integer> numbers,
            Collector<Integer, ?, M> collector
    ) {
        long time = System.nanoTime();
        try {
            Map<Integer, Long> group = numbers.collect(collector);
            for (int i = 0; i < 4; i++) {
                if (group.get(i) != NUMBERS) {
                    throw new AssertionError("Error in grouping");
                }
            }
        } finally {
            time = System.nanoTime() - time;
            System.out.printf(" in %dms%n", (time / 1_000_000));
        }
    }
}
