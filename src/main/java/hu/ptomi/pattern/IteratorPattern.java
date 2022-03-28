package hu.ptomi.pattern;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Iterator Pattern = Provide uniform interface to traverse aggregated data structures.
 * <p>
 * JDK Use Case: Collections FW.
 */
public class IteratorPattern {
    public static void main(String[] args) {
        var names = new Vector<String>();
        Collections.addAll(names, "John", "Anton", "Heinz", "Zach");

        enumeration(new Vector<>(names));
        java7Way(new Vector<>(names));
//        iterator(new Vector<>(names)); // ConcurrentModificationException
        iterator(new CopyOnWriteArrayList<>(names)); // Iterator runs on the created array for the last write.
        iterator(new ConcurrentSkipListSet<>(names)); // Weakly-Consistent = not necessarily see all the changes that had done during iteration.
        weakConsistency(new ConcurrentSkipListSet<>());
    }

    // Peter is not seen in the list but it is there.
    private static void weakConsistency(
            Collection<String> names
    ) {
//        names.add("Suzie"); // Uncommenting this, you would see Suzie.
        var iterator = names.iterator();
        names.add("Peter");
        while (iterator.hasNext()) {
            System.out.println("it.next= " + iterator.next());
        }
        System.out.println("added names=" + names);
    }

    // ConcurrentModificationException
    private static void iterator(
            Collection<String> names
    ) {
        var en = names.iterator();
        while (en.hasNext()) {
            var name = en.next();
            if (name.contains("n")) names.remove(name);
        }
        System.out.println("names=" + names);
    }

    // Much better way.
    private static void java7Way(
            Collection<String> names
    ) {
        names.removeIf(n -> n.contains("n"));
        System.out.println("names=" + names);
    }

    // We jump over Anton as Anton takes John's place but the enumeration count just increments.
    private static void enumeration(
            Vector<String> names
    ) {
        var en = names.elements();
        while (en.hasMoreElements()) {
            var name = en.nextElement();
            if (name.contains("n")) names.remove(name);
        }
        System.out.println("names=" + names);
    }

}
