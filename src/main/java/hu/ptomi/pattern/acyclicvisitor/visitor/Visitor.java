package hu.ptomi.pattern.acyclicvisitor.visitor;

public interface Visitor {
    void visit(Number number); // ConcreteA

    void visit(Plus plus); // ConcreteB
}
