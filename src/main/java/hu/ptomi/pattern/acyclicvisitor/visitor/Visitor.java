package hu.ptomi.pattern.acyclicvisitor.visitor;

public interface Visitor {
    default void visit(Number number) { // ConcreteA
    }

    default void visit(Plus plus) { // ConcreteB
    }
}
