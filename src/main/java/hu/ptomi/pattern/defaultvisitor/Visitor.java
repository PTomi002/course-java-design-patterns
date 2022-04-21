package hu.ptomi.pattern.defaultvisitor;

public interface Visitor {
    void visitNumber(Number n);

    void visitPlus(Plus p);

    void visitMinus(Minus m);
}
