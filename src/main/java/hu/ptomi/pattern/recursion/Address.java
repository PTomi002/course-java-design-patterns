package hu.ptomi.pattern.recursion;

public record Address(
        String city
) {
    public StringBuilder append(StringBuilder builder) {
        return builder.append("Address{city=").append(city).append("}");
    }
}
