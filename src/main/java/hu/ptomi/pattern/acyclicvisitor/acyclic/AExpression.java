package hu.ptomi.pattern.acyclicvisitor.acyclic;

public interface AExpression {
    void accept(AVisitor aVisitor);
}
