package hu.ptomi.pattern;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Abstract Class Pattern = Define the interface and general impl. then defer the specific impl. to the subclasses.
 * <p>
 * Types of Polymorphism: subclasses offering different implementations
 * 1)   Method Overloading    =   multiple methods of the same name in the same class, and all the methods work in different ways
 * 2)   Method Overriding     =   subclass or a child class has the same method as declared in the parent class
 * <p>
 * Polymorphism can be classified into two types:
 * 1)   Static / Compile-Time Polymorphism  =   Method Overloading / Operator Overloading (not supported in Java)
 * 2)   Dynamic / Runtime Polymorphism      =   Method Overriding via subclassing / interface impl
 */
public class AbstractClassPattern {

    public static void main(String[] args) {
        List<Integer> list = new ReadOnlyArrayList<>(1, -10, 30);
        System.out.println(list);

        // Upcasting
        A a = new B(0, 0);
        A b = new B(1, 1);
        A c = new C(1);
        Stream.of(a, b, c).forEach(A::call);
    }

    private static class ReadOnlyArrayList<E> extends AbstractList<E> {
        // Generics only work for compile time, these types will be erased by the compiler.
        // E will be replaced with Object and class cast are applied in front of them where needed.
        private final E[] objects;

        @SafeVarargs
        private ReadOnlyArrayList(E... objects) {
            // This is wrong: ClassCastException: Object[] is not a Integer[] in case of Integer list.
//            this.objects = (E[]) new Object[objects.length];
            // Pointing to the new collection checked compile time.
            this.objects = objects;
        }

        @Override
        public E get(int index) {
            Objects.checkIndex(index, objects.length);
            // compiled to: return (Integer) objects[index]; in case of Integer list.
            return objects[index];
        }

        @Override
        public int size() {
            return objects.length;
        }
    }

    private static abstract class A {
        protected final int commonState;

        private A(int state) {
            this.commonState = state;
        }

        public abstract void execute();

        public void call() {
            System.out.println("Call A.");
        }
    }

    private static class B extends A {
        private final int bState;

        private B(int commonState, int bState) {
            super(commonState);
            this.bState = bState;
        }

        @Override
        public void execute() {
            System.out.println("Execute B");
        }
    }

    private static class C extends A {

        private C(int state) {
            super(state);
        }

        @Override
        public void call() {
            System.out.println("Call C");
        }

        @Override
        public void execute() {
            System.out.println("Execute C");
        }
    }
}
