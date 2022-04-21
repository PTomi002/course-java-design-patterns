/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package hu.ptomi.pattern.defaultvisitor.originalvisitor;

import java.util.concurrent.atomic.LongAdder;

public class NumberCountVisitor implements Visitor {
    private final LongAdder count = new LongAdder();

    public void visitNumber(Number n) {
        count.increment();
    }

    public void visitPlus(Plus p) {
        // do nothing
    }

    public long getCount() {
        return count.longValue();
    }
}
