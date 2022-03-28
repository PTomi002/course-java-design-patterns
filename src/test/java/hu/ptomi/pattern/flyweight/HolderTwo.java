package hu.ptomi.pattern.flyweight;

public class HolderTwo {
    public String name;

    {
        name = "Test name"; // String created this way goes to string pool
    }
}
