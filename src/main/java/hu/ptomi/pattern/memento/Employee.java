package hu.ptomi.pattern.memento;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.UUID;

public class Employee {
    public enum Position {PROGRAMMER, TESTER, MANAGER}

    private final String id = UUID.randomUUID().toString();
    private int salary = 1000;
    private int balance = 2000;
    private Position position = Position.TESTER;

    public void pay() {
        balance += salary;
    }

    public void promote() {
        switch (position) {
            case TESTER -> {
                position = Position.PROGRAMMER;
                salary *= 2;
            }
            case PROGRAMMER -> {
                position = Position.MANAGER;
                salary *= 4;
            }
            case MANAGER -> salary *= 2;
        }
    }

    // Memento save and restore methods.
    public Memento createMemento() {
        return new MementoImpl(this);
    }

    public void setMemento(Memento memento) {
        var m = (MementoImpl) memento;

        if (this == m.employeeRef.get()) {
            salary = m.salary;
            balance = m.balance;
            position = m.position;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", salary=" + salary +
                ", balance=" + balance +
                ", position=" + position +
                '}';
    }

    // private static class to access Outer Class private properties.
    // static nested class = Static Class does not have ref. back to the Outer Class
    // non-static nested class = Inner Class has an implicit reference back to the Outer Class
    // We want to prevent memory leaks as Employee can not be garbage collected till the memento is alive somewhere in a collection!
    private static class MementoImpl implements Memento {
        private final int salary;
        private final int balance;
        private final Position position;

        //        private final Employee employee; // strong ref
        private final Reference<Employee> employeeRef;

        public MementoImpl(Employee employee) {
            salary = employee.salary;
            balance = employee.balance;
            position = employee.position;
            employeeRef = new WeakReference<>(employee);
        }
    }
}
