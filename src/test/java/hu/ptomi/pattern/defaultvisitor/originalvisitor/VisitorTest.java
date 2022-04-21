/*
 * This class forms part of the Design Patterns Course by
 * Dr Heinz Kabutz from JavaSpecialists.eu and may not be
 * distributed without written consent.
 *
 * Copyright 2001-2018, Heinz Kabutz, All rights reserved.
 */
package hu.ptomi.pattern.defaultvisitor.originalvisitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitorTest {
    @Test
    public void onePlusTwoPlusThreePlusFourCounting() {
        Expression expression = new Plus(new Number(1), new Plus(new Number(2), new Plus(new Number(3), new Number(4))));
        NumberCountVisitor counter = new NumberCountVisitor();
        expression.accept(counter);
        assertEquals(4, counter.getCount());
    }

    @Test
    public void onePlusTwoPlusThreePlusFourNestedCounting() {
        Expression expression = new Plus(new Plus(new Number(1), new Number(2)), new Plus(new Number(3), new Number(4)));
        NumberCountVisitor counter = new NumberCountVisitor();
        expression.accept(counter);
        assertEquals(4, counter.getCount());
    }

    @Test
    public void onePlusTwoPlusThreePlusFourEval() {
        Expression expression = new Plus(new Number(1), new Plus(new Number(2), new Plus(new Number(3), new Number(4))));
        EvalVisitor eval = new EvalVisitor();
        expression.accept(eval);
        assertEquals(10, eval.getValue());
    }

    @Test
    public void onePlusTwoPlusThreePlusFourNestedEval() {
        Expression expression = new Plus(new Plus(new Number(1), new Number(2)), new Plus(new Number(3), new Number(4)));
        EvalVisitor eval = new EvalVisitor();
        expression.accept(eval);
        assertEquals(10, eval.getValue());
    }

    @Test
    public void onePlusTwoPlusThreePlusFourPrinting() {
        Expression expression = new Plus(new Number(1), new Plus(new Number(2), new Plus(new Number(3), new Number(4))));
        PrintVisitor print = new PrintVisitor();
        expression.accept(print);
        assertEquals("1 2 3 4 + + +", print.toString());
    }

    @Test
    public void onePlusTwoPlusThreePlusFourNestedPrinting() {
        Expression expression = new Plus(new Plus(new Number(1), new Number(2)), new Plus(new Number(3), new Number(4)));
        PrintVisitor print = new PrintVisitor();
        expression.accept(print);
        assertEquals("1 2 + 3 4 + +", print.toString());
    }
}
