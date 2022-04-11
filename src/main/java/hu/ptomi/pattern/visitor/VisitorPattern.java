package hu.ptomi.pattern.visitor;

/**
 * VisitorPattern = Represents an operation that can be performed on any element of object structure.
 * With visitor you can add new operation without changing the classes of the elements.
 * <p>
 * To simplify: adding functionality without changing classes.
 * To simplify: concrete elements (ConcreteElementA and ConcreteElementB) are accepting a Visitor, simply allowing it to visit them
 * <p>
 * Drawback: cyclic dependencies between the visitors and the elements -> thus Acyclic version was founded
 * <p>
 * JDK Use Case: Annotation processors can visit inspected elements
 */
public class VisitorPattern {
    public static void main(String[] args) {
        // Run the following command to start annotation processor:
//        Paulin Tamás@DESKTOP-NO2RFK5 MINGW64 ~/Workplace/demo/course-design-patterns-applied-in-java (master)
//        $ pwd
//        /c/Users/Paulin Tamás/Workplace/demo/course-design-patterns-applied-in-java
//
//        Paulin Tamás@DESKTOP-NO2RFK5 MINGW64 ~/Workplace/demo/course-design-patterns-applied-in-java (master)
//        $ /c/Program\ Files/Java/jdk-16.0.2/bin/javac.exe -cp target/classes -processor hu.ptomi.pattern.visitor.DeprecatedProcessor hu.ptomi.pattern.visitor.TestClass
//        warning: java.lang.Deprecated (hu.ptomi.pattern.visitor.TestClass, i, log())
//        1 warning
    }
}
