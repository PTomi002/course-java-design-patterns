package hu.ptomi.pattern;

import java.util.concurrent.*;

/**
 * Builder Pattern = Avoid telescoping constructors caused by defaults and named parameters.
 * <p>
 * Uee Case: assemble complex object from parts, e.g.: buildXYZ then a director to build the complex whole product
 * JDK use case: Calendar Builder
 */
public class BuilderPattern {

    public static void main(String[] args) {
        var director = new ThreadPoolDirector(new FixedThreadPoolBuilder(5));
        director.construct();

        var otherDirector = new ThreadPoolDirector(new CachedThreadPoolBuilder());
        otherDirector.construct();

        var server = new Server.Builder()
                .port(443)
                .host("www.google.hu")
                .build();
        System.out.println(server);

        var thirdDirector = new ReworkedThreadPoolDirector.Builder()
                .size(10)
                .queue(new SynchronousQueue<>())
                .build();
        System.out.println(thirdDirector.construct());
    }

    // ===== classic builder pattern, little bit over designed =====
    private interface ThreadPoolBuilder {
        ThreadPoolParameters buildSizeParameters();

        BlockingQueue<Runnable> buildWorkQueue();

        default ThreadFactory buildThreadFactory() {
            return Executors.defaultThreadFactory();
        }

        default RejectedExecutionHandler buildRejectedExecutionHandler() {
            return new ThreadPoolExecutor.AbortPolicy();
        }
    }

    // Avoiding telescoping params in the constructor.
    private record ThreadPoolDirector(ThreadPoolBuilder builder) {
        public ExecutorService construct() {
            var params = builder.buildSizeParameters();
            return new ThreadPoolExecutor(
                    params.size,
                    params.workQueueSize,
                    params.keepAlive,
                    params.timeUnit,
                    builder.buildWorkQueue(),
                    builder.buildThreadFactory(),
                    builder.buildRejectedExecutionHandler()
            );
        }
    }

    // Encompass all the telescoping parameters.
    private record ThreadPoolParameters(
            int size,
            int workQueueSize,
            long keepAlive,
            TimeUnit timeUnit) {
    }

    // Fixed sized pool with unbounded work queue capacity.
    private record FixedThreadPoolBuilder(
            int size
    ) implements ThreadPoolBuilder {

        @Override
        public ThreadPoolParameters buildSizeParameters() {
            return new ThreadPoolParameters(
                    size,
                    size,
                    0,
                    TimeUnit.MILLISECONDS
            );
        }

        @Override
        public BlockingQueue<Runnable> buildWorkQueue() {
            return new LinkedBlockingQueue<>();
        }
    }

    // Caching pool.
    private static class CachedThreadPoolBuilder implements ThreadPoolBuilder {

        public CachedThreadPoolBuilder() {
        }

        @Override
        public ThreadPoolParameters buildSizeParameters() {
            return new ThreadPoolParameters(
                    0,
                    Integer.MAX_VALUE,
                    1,
                    TimeUnit.MINUTES
            );
        }

        @Override
        public BlockingQueue<Runnable> buildWorkQueue() {
            return new SynchronousQueue<>();
        }
    }

    // ===== builder pattern from Effective Java book =====
    private record Server(int port, String host) {
        // default parameters and telescoping variables are solved, static inner class
        private static class Builder {
            private int port = 8000;
            private String host = "localhost";

            public Builder port(int port) {
                this.port = port;
                return this;
            }

            public Builder host(String host) {
                this.host = host;
                return this;
            }

            public Server build() {
                return new Server(port, host);
            }
        }
    }

    // ===== reworked classic thread pool executor to mirror Effective Java book Builder Pattern =====
    private record ReworkedThreadPoolDirector(
            int size,
            int workQueueSize,
            long keepAlive,
            TimeUnit tu,
            BlockingQueue<Runnable> queue,
            ThreadFactory th,
            RejectedExecutionHandler exH
    ) {

        public ExecutorService construct() {
            return new ThreadPoolExecutor(
                    size,
                    workQueueSize,
                    keepAlive,
                    tu,
                    queue,
                    th,
                    exH
            );
        }

        private static class Builder {
            private int size = Runtime.getRuntime().availableProcessors();
            private int workQueueSize = Runtime.getRuntime().availableProcessors() * 2;
            private long keepAlive = 1;
            private TimeUnit unit = TimeUnit.MINUTES;
            private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
            private ThreadFactory th = Executors.defaultThreadFactory();
            private RejectedExecutionHandler exHandler = new ThreadPoolExecutor.AbortPolicy();

            public Builder size(int size) {
                this.size = size;
                return this;
            }

            public Builder queue(BlockingQueue<Runnable> queue) {
                this.queue = queue;
                return this;
            }

            public ReworkedThreadPoolDirector build() {
                return new ReworkedThreadPoolDirector(
                        size,
                        workQueueSize,
                        keepAlive,
                        unit,
                        queue,
                        th,
                        exHandler
                );
            }
        }
    }
}

