package hu.ptomi.pattern.defaultvisitor;

public final class Plus extends Expression {
    private final Expression first, second;

    public Plus(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }

    public void accept(Visitor v) {
        first.accept(v);
        second.accept(v);
        v.visitPlus(this);
    }
}
