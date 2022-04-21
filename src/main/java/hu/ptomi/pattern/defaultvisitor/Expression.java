package hu.ptomi.pattern.defaultvisitor;

public abstract class Expression {
    public abstract void accept(Visitor v);
}
