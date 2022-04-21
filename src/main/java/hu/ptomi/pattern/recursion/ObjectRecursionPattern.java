package hu.ptomi.pattern.recursion;

/**
 * ObjectRecursionPattern = Breaking down the problem into smaller tasks and delegate work downstream.
 * <p>
 * Use Case: broadcast a message through a linked object structore to all nodes
 * <p>
 * JDK Use Case: toString(), hashCode(), equals(), ...
 * Recurser: Stream.map(), Stream.filter()
 * Terminators: Stream.forEach()
 */
public class ObjectRecursionPattern {
    public static void main(String[] args) {
        var employee = new Employee("test@test.hu", new Address("Budapest"));
        System.out.println(employee.append(new StringBuilder()));
    }
}
