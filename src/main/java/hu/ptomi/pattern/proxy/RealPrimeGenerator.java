package hu.ptomi.pattern.proxy;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import static java.math.BigInteger.probablePrime;

public record RealPrimeGenerator(int state) implements PrimeGenerator {

    @Override
    public BigInteger calculatePrime() {
        return probablePrime(state, ThreadLocalRandom.current());
    }
}
