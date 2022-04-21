package hu.ptomi.pattern.defaultvisitor;

public final class Minus extends Expression {
    private final Expression first, second;

    public Minus(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }

    public void accept(Visitor v) {
        first.accept(v);
        second.accept(v);
        v.visitMinus(this);
    }
}
