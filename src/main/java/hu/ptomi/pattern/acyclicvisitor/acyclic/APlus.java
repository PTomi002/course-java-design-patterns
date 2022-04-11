package hu.ptomi.pattern.acyclicvisitor.acyclic;

public record APlus(AExpression first, AExpression second) implements AExpression {
    @Override
    public void accept(AVisitor aVisitor) {
        first.accept(aVisitor);
        second.accept(aVisitor);
        if (aVisitor instanceof APlusVisitor) ((APlusVisitor) aVisitor).visit(this); // just ignore if it is not a Plus visitor
    }
}
