package hu.ptomi.pattern.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * AdapterPattern = Change a class to make it work with another class by wrapping it into a new class. (Allows us to connect things that otherwise do not work together)
 * <p>
 * Types: class adaptor (inheritance) vs object adaptor (contains the adaptee)
 * <p>
 * JDK Use Case: InputStreamReader adapts InputStream
 */
public class AdapterPattern {

    // Easy example: object adapter
    // object adapter vs class adapter: class adapter uses inheritance while object adapter wraps the adaptee
    public static class EnumerationAdaptor<E> implements Enumeration<E> {
        private final Iterator<E> adaptee;

        public EnumerationAdaptor(Iterator<E> adaptee) {
            this.adaptee = adaptee;
        }

        @Override
        public boolean hasMoreElements() {
            return adaptee.hasNext();
        }

        @Override
        public E nextElement() {
            return adaptee.next();
        }
    }

    public static class MusicFeast {
        private final Collection<Singer> singers = new ArrayList<>();

        public void addSinger(Singer singer) {
            singers.add(singer);
        }

        public void singAll() {
            singers.forEach(Singer::sing);
        }
    }

    public static void main(String[] args) {
        // Lambda rapper
        MusicFeast musicFeast = new MusicFeast();
        musicFeast.addSinger(new RapperClassAdapter());
        musicFeast.addSinger(new Bass());

        // Singer interface contains only one abstract method, you can use lambdas
        musicFeast.addSinger(() -> new Rapper().talk()); // adding a lambda reference here, and the lambda creates the object in the forEach, and then it calls talk in the forEach
        musicFeast.addSinger(new Rapper()::talk); // not equivalent with line 55: it creates the object when we create the lambda, then the talk(..) method called in the forEach

        musicFeast.singAll();
        System.out.println("-------------------------");
        musicFeast.singAll();
    }
}
