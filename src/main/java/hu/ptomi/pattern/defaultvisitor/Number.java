package hu.ptomi.pattern.defaultvisitor;

public final class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void accept(Visitor v) {
        v.visitNumber(this);
    }
}
