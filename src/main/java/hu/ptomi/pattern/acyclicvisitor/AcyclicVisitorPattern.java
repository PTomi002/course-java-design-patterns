package hu.ptomi.pattern.acyclicvisitor;

import hu.ptomi.pattern.acyclicvisitor.acyclic.ANumber;
import hu.ptomi.pattern.acyclicvisitor.acyclic.ANumberCountVisitor;
import hu.ptomi.pattern.acyclicvisitor.acyclic.APlus;
import hu.ptomi.pattern.acyclicvisitor.visitor.Number;
import hu.ptomi.pattern.acyclicvisitor.visitor.NumberCountingVisitor;
import hu.ptomi.pattern.acyclicvisitor.visitor.Plus;

/**
 * AcyclicVisitorPattern = same as Visitor Pattern but breaking the cycle.
 * cycle = Visitor knows about the ConcreteA/B classes (NOT in this pattern), ConcreteA/B classes know about the visitor
 * <p>
 * we can extend acyclic visitor easier as Visitor is just a marker interface -> we dont have to update all the visitors if we extend the operations
 * <p>
 * Use Case: Atlassian's JIRA -> AbstractCustomFieldType
 */
public class AcyclicVisitorPattern {
    public static void main(String[] args) {
        // visitor
        var ten = new Number(10);
        var five = new Number(5);
        var minusOne = new Number(-1);

        var plus = new Plus(ten, five);
        var otherPlus = new Plus(plus, minusOne);

        var visitor = new NumberCountingVisitor();
        otherPlus.accept(visitor);
        System.out.println(visitor.getSum());

        //  acyclic visitor
        var aTen = new ANumber(10);
        var aFive = new ANumber(5);
        var aMinusOne = new ANumber(-1);

        var aPlus = new APlus(aTen, aFive);
        var aOtherPlus = new APlus(aPlus, aMinusOne);

        var aVisitor = new ANumberCountVisitor();
        aOtherPlus.accept(aVisitor);
        System.out.println(aVisitor.getSum());
    }
}
