package hu.ptomi.pattern.flyweight;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertSame;

@Disabled
public class FlyWeightPatternTest {
    @Test
    public void testSameness() {
        compare("hello", "hello"); // constant strings goes to pool in the heap
        var greeting = new String("hello");
//        compare("hello", greeting); // equals but not the same
        greeting = greeting.intern(); // flyweight to string pool
        compare("hello", greeting);

        compare("goodbye", "goodbye");
        compare(
                new HolderOne().name,
                new HolderTwo().name
        );

        // int cache: -128 -> 127, pool can be increased with VM opts
        // -XX:+PrintFlagsFinal  intx AutoBoxCacheMax                          = 128                                    {C2 product} {default}
        compare(123, 123); // gets it from integer cache, flyweight pattern
        compare(13, 13); // in the cache there is a limited range of integers, 321 is out of it, autoboxe creates two different Integers with the same content
        compare(321, 321); // Different boxed integers on different memory pointers.
        // Running with VM option: -XX:AutoBoxCacheMax=10000 this test passes

        compare(extrinsicLambda(), extrinsicLambda());
        compare(extrinsicLambdaTwo(), extrinsicLambdaTwo());
        compare(anonymousClass(), anonymousClass());
//        compare(intrinsicLambda("Tamas"), intrinsicLambda("Tamas"));
    }

    // this lambda does not have Extrinsic state, does not know anything about the outer world
    // that's why it goes to a function pool
    private static Runnable extrinsicLambda() {
        return () -> {
            System.out.println("I~m a lambda function.");
        };
    }

    // Captures the value on the stack, thus have an intrinsic state.
    private static Runnable intrinsicLambda(
            String name
    ) {
        return () -> System.out.println("I~m a lambda function " + name);
    }

    // Does not capture the string.
    private static Consumer<String> extrinsicLambdaTwo() {
        return name -> System.out.println("I~m a lambda function " + name);
    }

    // Anonymous classes are always different.
    private static Consumer<String> anonymousClass() {
        return new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("I~m a lambda function " + s);
            }
        };
    }

    private static void compare(
            Object object,
            Object other
    ) {
        System.out.println(System.identityHashCode(object));
        System.out.println(System.identityHashCode(other));
        assertSame(object, other);
        System.out.println();
    }

}
