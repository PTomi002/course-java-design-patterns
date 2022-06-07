package hu.ptomi.pattern.singleton;

public class InitializingExceptionTest {

    // Use Case: this is the best implementation so far
    // this is lazily initialized example
    // only inited first time you call getInstance() (classloader do it for us) the class is loaded and the static methods initialized
    public static class LazySingleton {
        private static final LazySingleton INSTANCE = new LazySingleton();

        private LazySingleton() {
            throw new IllegalStateException("injected error");
        }

        public static LazySingleton getInstance() {
            return INSTANCE;
        }
    }

    // Use Case: sometimes if the constructor can throw an exception, we do not want to shadow the original problem with NoClassDefFoundException
    public static class DoubleCheckedSingleton {
        private static volatile DoubleCheckedSingleton INSTANCE;

        private DoubleCheckedSingleton() {
            throw new IllegalStateException("injected error");
        }

        public static DoubleCheckedSingleton getInstance() {
            if (INSTANCE == null) {
                synchronized (DoubleCheckedSingleton.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new DoubleCheckedSingleton();
                    }
                }
            }
            return INSTANCE;
        }
    }

    // Use Case: if fields should be initialized lazily
    public static class WrappedSingleton {
        private static class SingletonHolder {
            // only inited when getInstance() called and will be faster than with getter methods
            // classloader solves the synchronization
            static final WrappedSingleton instance = makeSingleton();
        }

        private static WrappedSingleton makeSingleton() {
            return new WrappedSingleton();
        }

        public static WrappedSingleton getInstance() {
            return SingletonHolder.instance;
        }

        private WrappedSingleton() {
            throw new IllegalStateException("injected error");
        }
    }

    // motivation: class loader ensures it is created before available to clients
    // but it is the worst impl way
    public enum EnumSingleton {
        INSTANCE
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try {
//                LazySingleton.getInstance(); // Exception is turned into NoClassDefFoundException after the first run!
//                DoubleCheckedSingleton.getInstance();
                WrappedSingleton.getInstance();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
