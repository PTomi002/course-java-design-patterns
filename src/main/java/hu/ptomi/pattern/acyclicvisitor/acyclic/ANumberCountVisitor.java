package hu.ptomi.pattern.acyclicvisitor.acyclic;

import java.util.concurrent.atomic.LongAdder;

public class ANumberCountVisitor implements AVisitor, ANumberVisitor {
    private final LongAdder sum = new LongAdder();

    @Override
    public void visit(ANumber aNumber) {
        sum.add(aNumber.num());
    }

    public long getSum() {
        return sum.sum();
    }
}
