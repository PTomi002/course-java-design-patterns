package hu.ptomi.pattern;

import java.util.ArrayList;

/**
 * PrototypePattern = create a prototype object and clone it for any other usage
 * <p>
 * JDK Use Case: reflection cache data
 * <p>
 * Use Case: maybe cloning an array, but easier to use a copy constructor (shallow vs deep clone)
 */
public class PrototypePattern {
    public static void main(String[] args) throws ReflectiveOperationException {
        var fields = ArrayList.class.getDeclaredFields(); // when first time called VM cache these prototype objects and makes a copy as response
        var sameFields = ArrayList.class.getDeclaredFields(); // second time just copies the prototype
        // cache class = private static class ReflectionData<T> { ..... }
        var field = ArrayList.class.getDeclaredField("size");

        // little fun here
//        var value = String.class.getDeclaredField("value");
//        value.setAccessible(true);
//        value.set("Hello!", value.get("Cheers!"));
//        System.out.println("Hello!");
//        should write 'cheers'

        // Check SecurityManager, java.policy file

        // covariant return type = return type can vary in the direction we subclass
        // public class AccessibleObject {
        //      public AccessibleObject copy() { return this; }
        // }
        // public class Field extends AccessibleObject {
        //      public Field copy() { return new Field(); }
        // }
    }
}
