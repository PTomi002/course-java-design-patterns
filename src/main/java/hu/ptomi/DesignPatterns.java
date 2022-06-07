package hu.ptomi;

/**
 * We use patterns because of code reusage!.
 */
public class DesignPatterns {

    public static void main(String[] args) {

    }

    // Intrinsic state classes.
    interface IntrinsicStrategy {
        void execute();
    }

    // Extrinsic state classes.
    interface ExtrinsicStrategy {
        void execute(Context ctx);
    }

    /**
     * An object memory needs.
     * 64 bit machine with compressedOops (pointers are in a format like in a 32 bit machine, 4 bytes).
     */
    private static class Context {                              // object header    =   12 bytes
        private final IntrinsicStrategy strategy;               // obj reference    =   4 bytes
        private final int state;                                // primitive int    =   4 bytes

        // sum              =   20 but rounded up to 24 (always aligned with 8 byte boundaries)
        private Context(IntrinsicStrategy name, int state) {
            this.strategy = name;
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

    private class IntrinsicStrategyImpl implements IntrinsicStrategy {  // object header    =   12 bytes
        private final Context ctx;                                      // object ref       =   4 bytes (16 bytes sum with rounding)

        // with a million context it is a lot of extra memory
        // but provides better encapsulation
        private IntrinsicStrategyImpl(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public void execute() {
            System.out.println("Executed: " + ctx.getState());
        }
    }

    private class ExtrinsicStrategyImpl implements ExtrinsicStrategy {  // object header    =   12 bytes (16 bytes sum with rounding)

        // less memory, and can be shared between multiple context, but worse encapsulation
        public ExtrinsicStrategyImpl() {
        }

        @Override
        public void execute(Context ctx) {
            System.out.println("Executed: " + ctx.getState());
        }
    }
}
