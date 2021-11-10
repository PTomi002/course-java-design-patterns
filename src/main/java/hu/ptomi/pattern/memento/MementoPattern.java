package hu.ptomi.pattern.memento;

/**
 * Memento Pattern = Can expose an internal state of an object without violating encapsulation (without getter/setter) and able to save and restore it.
 * <p>
 * JDK Use Case: Serialization mechanism
 */
public class MementoPattern {

    public static void main(String[] args) {
        BigCompany company = new BigCompany();

        Employee employee = new Employee();
        Employee otherEmployee = new Employee();

        company.pay(otherEmployee);
        company.promote(employee);
        company.pay(employee);
        company.promote(employee);
        company.pay(employee);

        company.undo(employee);
        company.undo(otherEmployee);
    }
}
