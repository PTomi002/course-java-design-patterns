package hu.ptomi.pattern.acyclicvisitor.acyclic;

public record ANumber(int num) implements AExpression {

    @Override
    public void accept(AVisitor aVisitor) {
        if (aVisitor instanceof ANumberVisitor) ((ANumberVisitor) aVisitor).visit(this);
    }
}
