package hu.ptomi.pattern.defaultvisitor;

/**
 * DefaultVisitorPattern = Same as visitor with providing default implementations thus take advance of inheritance.
 * <p>
 * JDK Use Case: sun.jvm.hotspot.oops.HeapVisitor and sun.jvm.hotspot.oops.DefaultHeapVisitor.
 * <p>
 * Useful: when using Visitor, but want more flexibility.
 */
public class DefaultVisitorPattern {
    public static void main(String[] args) {
        // Run the tests!
        // After adding Minus with visitMinus and try to run the tests we get compiler error for EvalVisitor.
    }
}
