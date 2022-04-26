package hu.ptomi.pattern.proxy;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Proxy Pattern = Clients speak to the real object via a substitute.
 * <p>
 * Use Case:
 * JDK use case: JavaBeans, MBeans, Collections.xyzList (Collections.unmodifiableList is a protection proxy)
 */
public class ProxyPattern {
    // Other example chain of proxies.
    private interface A {
    }

    // real
    private static class B implements A {
        private final int state;

        private B(int state) {
            this.state = state;
        }

        @Override
        public boolean equals(Object o) {
            System.out.println("B equals called!");
            if (this == o) return true;
            if (o == null) return false;
            if (getClass() != o.getClass()) {
                if (o instanceof C) {
                    C c = (C) o;
                    return c.a.equals(this);
                } else return false;
            }
            B b = (B) o;
            return state == b.state;
        }

        @Override
        public int hashCode() {
            System.out.println("B hash called!");
            return Objects.hash(state);
        }
    }

    // proxy
    private static class C implements A {
        private final A a;

        private C(A a) {
            requireNonNull(a);
            this.a = a;
        }

        @Override
        public boolean equals(Object o) {
            System.out.println("C equals called!");
            return a.equals(o);
        }

        @Override
        public int hashCode() {
            System.out.println("C hash called!");
            return a.hashCode();
        }
    }

    public static void main(String[] args) {
        PrimeGenerator subject = new VirtualPrimeGenerator(50);
        System.out.println(subject.calculatePrime());

        // Chain of proxies, equality should work in a transitive was, should be symmetric between real subject and its proxies.
        A aOne = new C(new C(new C(new C(new B(10)))));
        A aTwo = new C(aOne);

        B bOne = new B(10);
        B bTwo = new B(10);

        System.out.println(bOne.equals(bTwo));
        System.out.println(bTwo.equals(bOne));

        System.out.println(aOne.equals(bOne));
        System.out.println(bOne.equals(aTwo));
    }
}
