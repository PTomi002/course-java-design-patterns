package hu.ptomi.pattern;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Flyweight Pattern =  The flyweight pattern is useful when dealing with large numbers of objects with simple repeated elements that would use a large amount of memory if individually stored.
 * It is common to hold shared data in external data structures and pass it to the objects temporarily when they are used.
 * <p>
 * Use Case: string, in cache, etc...
 */
public class FlyWeightPattern {

    public FlyWeightPattern() throws SQLException {
        throw new SQLException("Injected error!");
    }

    public static void main(String[] args) throws
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {

        // IllegalAccessException: can not access method
//        Runtime.class.newInstance();
        // InstantiationException: e.g. it is not a class
//        Serializable.class.newInstance();
        // NoSuchMethodException: e.g.: can not find a constructor with list parameter
//        String.class.getDeclaredConstructor(List.class).newInstance();
        // InvocationTargetException
//        ArrayList.class.getDeclaredConstructor(Collection.class).newInstance((Collection) null);
//        FlyWeightPattern.class.getDeclaredConstructor().newInstance();
        FlyWeightPattern.class.newInstance(); // without getDeclaredConstructor() method the root cause is not wrapped into a InvocationTargetException

        String.class.newInstance();
        String.class.getDeclaredConstructor().newInstance();
    }
}
