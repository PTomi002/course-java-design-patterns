package hu.ptomi.pattern.singleton;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SingletonPattern = ensure the class has only one instance and provides global access to it
 * <p>
 * More flexible than static methods, e.g.: subclassing.
 * <p>
 * JDK Use Case: Runtime.getRuntime(), ThreadLocalRandom.current(), ...
 */
public class SingletonPattern {

    private static final String SINGLETON_CLASS = "hu.ptomi.pattern.singleton.SingletonPattern$Singleton";
    private static final String SINGLETON_CLASS_JAR = "./target/course-design-patterns-applied-in-java-1.0-SNAPSHOT.jar";

    public static class ReloadingClassLoader extends URLClassLoader {

        // class loader without parent delegation
        public ReloadingClassLoader(URL[] urls) {
            super(urls, ArrayList.class.getClassLoader()); // with bootloader parent it does not find the loaded Singleton.class
//            super(urls, SingletonPattern.class.getClassLoader()); // with apploader (from classpath) it finds the Singleton.class
        }

    }

    public static class Singleton {
        private static final AtomicInteger counter = new AtomicInteger(0);
        private static final Singleton INSTANCE = new Singleton();

        private Singleton() {
        }

        public static int getCounter() {
            return counter.get();
        }

        public static Singleton getInstance() {
            counter.incrementAndGet();
            return INSTANCE;
        }
    }

    // build the project with maven install to be able to find the target .jar
    public static void main(String[] args) {
        try {
            var systemClassLoader = SingletonPattern.class.getClassLoader();
            var reloadingClassLoader = new ReloadingClassLoader(
                    new URL[]{Paths.get(SINGLETON_CLASS_JAR).toUri().toURL()}
            );

            var systemLoadedSingleton = (Class<Singleton>) systemClassLoader.loadClass(SINGLETON_CLASS);
            var urlLoadedSingleton = (Class<Singleton>) reloadingClassLoader.loadClass(SINGLETON_CLASS);

            var systemInstance = systemLoadedSingleton.getMethod("getInstance").invoke(null);
            var urlInstance = urlLoadedSingleton.getMethod("getInstance").invoke(null);

            System.out.println("SYSTEM: " + systemInstance.getClass().getName() + " - " + systemInstance.hashCode());
            System.out.println("URL: " + urlInstance.getClass().getName() + " - " + urlInstance.hashCode());

            var systemCounter = (int) systemLoadedSingleton.getMethod("getCounter").invoke(null);
            var urlCounter = (int) urlLoadedSingleton.getMethod("getCounter").invoke(null);

            // counter is different with the bootstrap and apploader
            System.out.println("SYSTEM: " + systemCounter);
            System.out.println("URL: " + urlCounter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
