package hu.ptomi.pattern.recursion;

public record Employee(
        String email,
        Address address
) {
    public StringBuilder append(StringBuilder builder) {
        builder
                .append("Employee{email=").append(email)
                .append(",address=");
        address // recursively append the object graph elements
                .append(builder);
        builder
                .append("}");
        return builder;
    }
}
