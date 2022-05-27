package hu.ptomi;

import java.util.ArrayList;
import java.util.List;

public class JavaGenerics {

    public static void main(String[] args) {
        // Producer extends and Consumer super explanation
        // (1) Originally the problem is:
        List<Number> listOne = new ArrayList<>();
        // I can add an int or a double as both is a number type
        listOne.add(11);
        listOne.add(12.12);
        // I can read them
        listOne.forEach(System.out::println);

        // (2) But I can not do this:
//        List<Number> listTwo = new ArrayList<Integer>(); // Compiler error, why?
        List<Integer> listTwo = new ArrayList<>();
        listTwo.add(11);
        listTwo.add(12);

        // Because I would be able to do this:
//        List<Number> listThree = listTwo; // as integer is a number, but
//        listThree.add(12.12); // This would cause heap pollution as in the memory it is an integer memory block.
//        testInvariant(listTwo); // the same problem happens here.

        // (3) The same happens with array, but we can trick the compiler but not the runtime:
//        Integer[] arrayOne = {1, 2, 3, 4};
//        Number[] arrayTwo = arrayOne; // as integer is a number, but
//        arrayTwo[0] = 3.14; // Exception in thread "main" java.lang.ArrayStoreException: java.lang.Double

        // (4) Thus generics provided "? extends T" for reading:
        // You want to go through the collection and do things with each item.
        List<Integer> listFour = new ArrayList<>();
        listFour.add(11);
        listFour.add(12);
        testCovariance(listFour);

        // (5) Thus generics provided "? super T" for writing:
        // You want to add things to the collection.
        List<Number> listFive = new ArrayList<>();
        listFive.add(11.12);
        listFive.add(12L);
        testContravariance(listFive);
    }

    // I can read and write the collection.
    private static void testInvariant(List<Number> listOne) {
        listOne.add(12);
        Number one = listOne.get(0);
    }

    // I can only read from the list knowing it is 100% a number.
    private static void testCovariance(List<? extends Number> listOne) { // Producer`extends`
//        listOne.add(new Object()); // Compiler error, why?
//        listOne.add(12); // Compiler error, why?
//        listOne.add(12.12); // Compiler error, why?
//        listOne.add(12L); // Compiler error, why?
        Number first = listOne.get(0); // list acts as a producer only
    }

    // I can only write to a list, but cant get a number as I dont know what kind of number is this.
    private static void testContravariance(List<? super Number> listOne) { // Consumer`super`
//        listOne.add(new Object()); // Compiler error, why? object is not type of shape
        listOne.add(12);
        listOne.add(12.12);
        listOne.add(12L);
//        Number one = listOne.get(0); // Compiler error, why? I dont know what kind of number is this. int or double or ....
    }
}
