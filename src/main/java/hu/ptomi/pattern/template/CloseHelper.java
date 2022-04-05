package hu.ptomi.pattern.template;

public abstract class CloseHelper<R, S, A, E extends Exception> {

    @SafeVarargs
    public final R run(A... args) throws E { // skeleton
        S s = setUp(args); // template
        class Closer implements AutoCloseable { // lambda would not be the best here as we want to throw an exception
            @Override
            public void close() throws E {
                tearDown(s, args); // template
            }
        }
        try (Closer closer = new Closer()) {
            return doExecute(s, args); // template
        }
    }

    protected abstract S setUp(A[] args) throws E;

    protected abstract R doExecute(S s, A[] args) throws E;

    protected abstract void tearDown(S s, A[] args) throws E;
}
