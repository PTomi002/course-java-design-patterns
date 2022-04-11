package hu.ptomi.pattern.acyclicvisitor.visitor;

public record Number(int num) implements Expression {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
