package hu.ptomi.pattern;

/**
 * StatePattern = allows an object to change its behaviour based on its internal state
 * <p>
 * Very similar to StrategyPattern, but this case the behaviour changes during runtime.
 * <p>
 * JDK Use Case: there isn't a pure implementation, but e.g.: Thread has a state machine but solved with enums and masking.
 */
public class StatePattern {

    // state machine class + intrinsic state
    public static class OrderFullFillment {
        private State state = new StartState();

        public void print() {
            state.print();
        }

        public void accepted() {
            state.accepted();
        }

        private void setState(State newState) {
            System.out.println(state + " ---> " + newState);
            state = newState;
        }

        // state classes, can be shared e.g.: flyweight
        // private non-static classes as the outer is inited before the inner
        private abstract class State {
            void print() {
                throw new IllegalStateException("can not use this event in the current state: " + state);
            }

            void accepted() {
                // throw an exception or use default methods
            }

            @Override
            public String toString() {
                return getClass().getSimpleName();
            }
        }

        private class StartState extends State {

            @Override
            void print() {
                System.out.println("start state print");
                setState(new InnerState());
            }
        }

        private class InnerState extends State {
            @Override
            public void print() {
                System.out.println("inner state print");
                setState(new InnerState());
            }

            @Override
            public void accepted() {
                System.out.println("inner state accepted");
                setState(new TerminateState());
            }
        }

        private class TerminateState extends State {
        }
    }

    public static void main(String[] args) {
        var order = new OrderFullFillment();
        order.accepted();
        order.print();
        order.print();
        order.print();
        order.accepted();
    }
}
