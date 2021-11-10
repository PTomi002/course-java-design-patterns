package hu.ptomi.pattern.memento;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

// Caretaker in the UML diagram, which handle memento objects.
public class BigCompany {
    private final Map<Employee, Deque<Memento>> history = new HashMap<>();

    public void promote(Employee employee) {
        snapshot(employee);
        employee.promote();
    }

    public void pay(Employee employee) {
        snapshot(employee);
        employee.pay();
    }

    private void snapshot(Employee employee) {
        var dequeue = history.computeIfAbsent(employee, e -> new ArrayDeque<>());
        dequeue.offerLast(employee.createMemento());
    }

    public void undo(Employee employee) {
        var dequeue = history.get(employee);
        if (nonNull(dequeue) && !dequeue.isEmpty()) {
            var memento = dequeue.pollLast();
            employee.setMemento(memento);
        }
    }

}
