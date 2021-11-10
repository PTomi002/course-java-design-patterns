package hu.ptomi.pattern;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static java.util.Objects.isNull;

/**
 * Essence Pattern = Construct a complex object and make sure that it is initialized before using in client side (ensure object integrity).
 * <p>
 * Use Case: Better to use builder pattern.
 * JDK Use Case: URL(...).openConnection(...)
 */
public class EssencePattern {

    public static void main(String[] args) throws IOException {
        var essence = new Essence();
        essence.setRequiredOne("localhost");
        essence.setRequiredTwo(8000);
//        essence.setRequiredTwo(-500);

        var creationTarget = essence.create();
        creationTarget.setOptionalOne("https");

        System.out.println(creationTarget);

        // ===== real life example from JDK =====
        var urlEssence = new URL("https", "www.google.com", 443, "");
        // openConnection() -> validateParameters + creation
        var urlCreationTarget = urlEssence.openConnection();
        // Optional parameters.
        urlCreationTarget.setReadTimeout(10_000);
        try (InputStream in = urlCreationTarget.getInputStream()) {
            in.transferTo(System.out);
        }
    }

    // The essence class: defines all required attributes + validation + creation.
    private static class Essence {
        private String requiredOne;
        private int requiredTwo;

        public Essence() {
        }

        public CreationTarget create() {
            validateParameters();
            return new CreationTarget(
                    requiredOne,
                    requiredTwo
            );
        }

        private void validateParameters() {
            if (isNull(requiredOne) || requiredOne.length() == 0)
                throw new IllegalArgumentException();
            if (requiredTwo < 0)
                throw new IllegalArgumentException();
        }

        public String getRequiredOne() {
            return requiredOne;
        }

        public void setRequiredOne(String requiredOne) {
            this.requiredOne = requiredOne;
        }

        public int getRequiredTwo() {
            return requiredTwo;
        }

        public void setRequiredTwo(int requiredTwo) {
            this.requiredTwo = requiredTwo;
        }
    }

    // Gets all the required via constructor and add optional fields if needed.
    private static class CreationTarget {
        private final String requiredOne;
        private final int requiredTwo;
        private String optionalOne;

        public CreationTarget(String requiredOne, int requiredTwo) {
            this.requiredOne = requiredOne;
            this.requiredTwo = requiredTwo;
        }

        public String getRequiredOne() {
            return requiredOne;
        }

        public int getRequiredTwo() {
            return requiredTwo;
        }

        public String getOptionalOne() {
            return optionalOne;
        }

        public void setOptionalOne(String optionalOne) {
            this.optionalOne = optionalOne;
        }

        @Override
        public String toString() {
            return "CreationTarget{" +
                    "requiredOne='" + requiredOne + '\'' +
                    ", requiredTwo=" + requiredTwo +
                    ", optionalOne='" + optionalOne + '\'' +
                    '}';
        }
    }

}
