/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package hu.ptomi.pattern.defaultvisitor.originalvisitor;

// E2
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
