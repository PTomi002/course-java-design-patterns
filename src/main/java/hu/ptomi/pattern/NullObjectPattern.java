package hu.ptomi.pattern;

/**
 * Null Object Pattern = Strategy with do nothing attitude, to prevent exceptions.
 * <p>
 * JDK Use Case: Collections.emptyList(...), Function.identity(...), Optional.empty(...)
 */
public class NullObjectPattern {
    public static void main(String[] args) {
        // Problems with NPE
        // 1. After a while JVM optimizes the stack trace and deletes it from the NPE, you won't know where it happened if happens often.
        // 2. Does not tell you what was null.
        // Turn optimization off with: -XX:-OmitStackTraceInFastThrow
        Exception previous = null;
        for (int i = 0; i < 1000_000; i++) {
            try {
                Integer a = null;
                Integer.compare(a, 12);
            } catch (NullPointerException ex) {
                // After a while NPE is a shared instance, so it's a flyweight.
                ex.printStackTrace();
                if (ex == previous) {
                    System.out.println("Flyweight shared object returned.");
                    return;
                } else {
                    previous = ex;
                }
            }
        }
    }
}
