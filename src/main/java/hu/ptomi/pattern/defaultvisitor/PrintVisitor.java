package hu.ptomi.pattern.defaultvisitor;

public class PrintVisitor implements DefaultVisitor {
    protected final StringBuilder sb = new StringBuilder();

    public void visitPlus(Plus s) {
        sb.append("+ ");
    }

    public void visitNumber(Number n) {
        sb.append(n.getValue()).append(' ');
    }

    @Override
    public void visitExpression(Expression expression) {
        sb.append("? ");
    }

    public String toString() {
        return sb.toString().trim();
    }
}
