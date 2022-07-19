package hu.ptomi.pattern.bridge;

/**
 * BridgePattern = decouple an abstraction from its implementation so that the two can vary independently
 * <p>
 * JDK Use Case: java.awt.* Component(abstraction) and ComponentPeer(implementor/implementation)
 */
public class BridgePattern {

    // abstraction for operators: Abstraction
    static abstract class Shape {
        // bridge prop.
        protected Color color;

        public Shape(Color color) {
            this.color = color;
        }

        // my operation is how to draw a shape
        abstract void draw();
    }

    // concrete operation: RefinedAbstraction
    static class Triangle extends Shape {
        public Triangle(Color color) {
            super(color);
        }

        @Override
        void draw() {
            color.fill();
        }

        void setColor(Color color) {
            this.color = color;
        }
    }

    // Implementor
    interface Color {
        void fill();
    }

    // this class know exactly how to draw a shape or fill its part (ConcreteImplementor1)
    static class Red implements Color {
        @Override
        public void fill() {
            System.out.println("filling shape with red");
        }
    }

    // ConcreteImplementor2
    static class Blue implements Color {
        @Override
        public void fill() {
            System.out.println("filling shape with blue");
        }
    }

    // client
    public static void main(String[] args) {
        var triangle = new Triangle(new Blue());
        triangle.draw();
        // can change runtime implementation of draw
        triangle.setColor(new Red());
        triangle.draw();
    }
}
