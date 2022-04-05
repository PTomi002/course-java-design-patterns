package hu.ptomi.pattern.template;

/**
 * Template Method Pattern = define skeleton of an algorithm and defer some steps (primitive ones) to the subclasses without changing the structure of the algorithm
 * vs AbstractClass = does provide interface with a generic implementation, and defer the specific part to the subclasses
 * vs Strategy = subclasses implement the whole algorithm, only shared part is the interface
 * <p>
 * Drawbacks: inheritance is only one way it is very static, leads to class explosion (in the means on number of subclasses)
 * <p>
 * JDK Use Case: ThreadPoolExecutor lifecycle / execution hooks before...after method.
 */
public class TemplateMethodPattern {
    public static void main(String[] args) {

    }
}
