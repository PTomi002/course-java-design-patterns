package hu.ptomi.pattern.acyclicvisitor.visitor;

public record Plus(Expression first, Expression second) implements Expression {

    @Override
    public void accept(Visitor visitor) {
        first.accept(visitor); // delegate the visitor downstream to leaves
        second.accept(visitor);
        visitor.visit(this);
    }
}
