package hu.ptomi.pattern.acyclicvisitor.visitor;

import java.util.concurrent.atomic.LongAdder;

public class NumberCountingVisitor implements Visitor {
    private final LongAdder sum = new LongAdder();

    @Override
    public void visit(Number number) {
        sum.add(number.num());
    }

    @Override
    public void visit(Plus plus) { // Why do we need this? if it is empty

    }

    public long getSum() {
        return sum.sum();
    }
}
