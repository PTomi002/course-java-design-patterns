package hu.ptomi.pattern;

import java.lang.invoke.StringConcatFactory;
import java.util.zip.Adler32;

/**
 * Strategy Pattern = Define the interface and define algorithms to implement it.
 * <p>
 * JDK Use Case: AWT/Swing, Comparator interface
 * <p>
 * 1) Similar to AbstractClassPattern but does not provide general implementation, just the interface.
 * 2) Most cases we prefer UML composition (property can not escape the outer class)/ UML aggregation over inheritance.
 * - An example I like to use is java.util.Properties. This was made as a subclass of java.util.Hashtable back in Java 1.0. It would have been better to use composition, in other words, for java.util.Properties to contain a Hashtable and to use that to maintain the properties.
 * 3) @IntrinsicCandidate -> Replaced by native handwritten code, very fast.
 */
public class StrategyPattern {
    public static void main(String[] args) {
        Adler32 adler32 = new Adler32();
        // Till Java 8 it was replaced to this by compiler.
        // Check the compiled code for this.
        System.out.println(
                new StringBuilder()
                        .append("Hello ")
                        .append(args[0])
                        .toString()
        );
        System.out.println("Hello " + args[0]);
        // javap -v -c .\target\classes\hu\ptomi\pattern\startegy\StrategyPattern.class
        //  InvokeDynamic #0:makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;
        // Java 16 has a much better string handling.
        StringConcatFactory s;
    }
}
