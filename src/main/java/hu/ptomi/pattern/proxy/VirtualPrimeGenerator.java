package hu.ptomi.pattern.proxy;

import java.math.BigInteger;

public class VirtualPrimeGenerator implements PrimeGenerator {
    // This ref. can be anything else to be able to identify the real subject in a local or remote machine.
    private PrimeGenerator primeGenerator;
    private final int state;

    public VirtualPrimeGenerator(int state) {
        // invariant: state must be greater than 1
        if (state < 1)
            throw new IllegalArgumentException();
        this.state = state;
    }

    @Override
    public BigInteger calculatePrime() {
        return realPrimeGenerator().calculatePrime();
    }

    // Delegate equals and hashcode to the real subject or until we reach the real subject.
    @Override
    public boolean equals(Object o) {
        return primeGenerator.equals(o);
    }

    @Override
    public int hashCode() {
        return primeGenerator.hashCode();
    }

    // Lazy instantiate the real subject, only when needed, on-demand.
    private PrimeGenerator realPrimeGenerator() {
        if (primeGenerator == null) {
            // Expensive to create and calculate later.
            return (primeGenerator = new RealPrimeGenerator(state));
        }
        return primeGenerator;
    }
}
