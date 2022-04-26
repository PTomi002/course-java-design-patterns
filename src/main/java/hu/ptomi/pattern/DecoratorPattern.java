package hu.ptomi.pattern;

/**
 * DecoratorPattern = attach additional responsibility to an object dynamically
 * <p>
 * Known as: Filter
 * <p>
 * JDK Use Case: ObjectOutputStream, FileOutputStream, GZIPOutputStream, (Http)Servlet Filter classes
 */
public class DecoratorPattern {

    // Covariant return type refers to return type of an overriding method.
    // It allows to narrow down return type of an overridden method without any need to cast the type or check the return type.
    static class Parent {
        public Parent it() {
            System.out.println("it=Parent");
            return this;
        }
    }

    static class Child extends Parent {
        public Child it() { // covariant
            System.out.println("it=Child");
            return this;
        }
    }

    public static void main(String[] args) {
        Parent p = new Child(); // upcasting
        System.out.println(p.it());
    }
}
