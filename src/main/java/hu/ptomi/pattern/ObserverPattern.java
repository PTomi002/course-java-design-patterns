package hu.ptomi.pattern;

import javax.management.NotificationEmitter;
import javax.management.openmbean.CompositeData;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryNotificationInfo;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ObserverPattern = One-to-Many relation between objects, one object status changes, we update all its dependencies.
 * <p>
 * JDK Use Case: Flow as Observer became deprecated in Java 9, MemoryWarningSystem
 * <p>
 * Known as: Publisher Subscriber
 */
public class ObserverPattern {

    interface Listener {
        void memoryUsageLow(long usedMemory, long maxMemory);
    }

    static class MemoryWarningSystem {
        private final static MemoryPoolMXBean tenuredGenPool = findTenuredGenPool();
        private final Collection<Listener> listeners = new CopyOnWriteArrayList<>();

        public MemoryWarningSystem() {
            var emitter = (NotificationEmitter) ManagementFactory.getMemoryMXBean();
            emitter.addNotificationListener((notification, handback) -> {
                if (MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED.equals(notification.getType())) {
                    var data = (CompositeData) notification.getUserData();
                    listeners.forEach(listener -> listener.memoryUsageLow(
                            (long) ((CompositeData) data.get("usage")).get("used"),
                            (long) ((CompositeData) data.get("usage")).get("max")
                    ));
                }
            }, null, null);
        }

        public void addListener(Listener listener) {
            listeners.add(listener);
        }

        public void removeListener(Listener listener) {
            listeners.remove(listener);
        }

        public static void setPercentageUsageThreshold(double percentage) {
            if (percentage < 0.0 || percentage > 1.0)
                throw new IllegalArgumentException("percentage can not be: " + percentage);
            var maxMemory = tenuredGenPool.getUsage().getMax();
            tenuredGenPool.setUsageThreshold((long) (maxMemory * percentage));
        }

        private static MemoryPoolMXBean findTenuredGenPool() {
            return ManagementFactory.getMemoryPoolMXBeans()
                    .stream()
                    .filter(pool -> MemoryType.HEAP == pool.getType() && pool.isUsageThresholdSupported())
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("could not find memory management pool"));
        }
    }


    public static void main(String[] args) throws InterruptedException {
        var warningSystem = new MemoryWarningSystem();
        warningSystem.addListener((usedMemory, maxMemory) -> {
            System.out.println("used = " + usedMemory);
            System.out.println("max = " + maxMemory);
        });
        MemoryWarningSystem.setPercentageUsageThreshold(0.5);

        // simulate some leakage
        List<byte[]> leakage = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            System.out.println("i = " + i);
            leakage.add(new byte[(int) (Runtime.getRuntime().maxMemory() / 100)]);
            Thread.sleep(200);
        }
    }
}
