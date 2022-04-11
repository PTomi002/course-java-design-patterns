package hu.ptomi.pattern.acyclicvisitor.visitor;

public interface Expression {
    void accept(Visitor visitor);
}
