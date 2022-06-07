package hu.ptomi.pattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AbstractFactoryPattern = provide an interface for creating families of objects/products
 * <p>
 * These implementations usually a singleton.
 * Client does not see the actual implementation, hidden from view.
 * <p>
 * Known as: Kit
 * <p>
 * JDK Use Case: java.awt.ToolKit
 */
public class AbstractFactoryPattern {

    public static abstract class CollectionFactory {
        private volatile static CollectionFactory factory;

        public static CollectionFactory getInstance() {
            if (factory == null) {
                synchronized (CollectionFactory.class) {
                    if (factory == null) {
//                        if ("unsafe".equals(System.getProperty("COL_FACT_TYPE", "unsafe"))) {
                        if ("unsafe".equals(System.getProperty("COL_FACT_TYPE", "safe"))) {
                            factory = new UnsafeCollectionFactory();
                        } else {
                            factory = new SafeCollectionFactory();
                        }
                    }
                }
            }
            return factory;
        }

        protected CollectionFactory() {
        }

        public abstract <K> List<K> createList();

        public abstract <K, V> Map<K, V> createMap();
    }

    public static class SafeCollectionFactory extends CollectionFactory {

        @Override
        public <K> List<K> createList() {
            return Collections.synchronizedList(new ArrayList<>());
        }

        @Override
        public <K, V> Map<K, V> createMap() {
            return new ConcurrentHashMap<>();
        }
    }

    public static class UnsafeCollectionFactory extends CollectionFactory {

        @Override
        public <K> List<K> createList() {
            return new ArrayList<>();
        }

        @Override
        public <K, V> Map<K, V> createMap() {
            return new HashMap<>();
        }
    }

    public static void main(String[] args) {
        System.out.println(CollectionFactory.getInstance().createList().getClass().getSimpleName());
        System.out.println(CollectionFactory.getInstance().createMap().getClass().getSimpleName());
    }
}
