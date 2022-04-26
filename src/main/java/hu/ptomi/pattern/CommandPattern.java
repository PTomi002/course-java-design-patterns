package hu.ptomi.pattern;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Optional;

/**
 * CommandPattern = encapsulate the request as an operation and let the clients parametrize, queue them
 * <p>
 * JDK Use Case: streams, forEach, Runnable, Callable (a command that can be passed to a thread or execute in a timer)
 * <p>
 * Command vs Strategy Pattern:
 * command = defines what needs to be done
 * strategy = defines how something to be done
 */
public class CommandPattern {

    // command classes = encapsulate all the information required for executing the command on the receiver
    // the implementors of the commands knows everything about how to execute the specific action
    @FunctionalInterface
    interface Command<T> {
        T execute();
    }

    static class ExistCommand implements Command<Boolean> {
        private final TextFile file;

        public ExistCommand(TextFile file) {
            Objects.requireNonNull(file);
            this.file = file;
        }

        @Override
        public Boolean execute() {
            return file.exists();
        }
    }

    // receiver classes = object that performs the specific actions
    static class TextFile extends File {

        public TextFile(String pathname) {
            super(pathname);
        }

        @Override
        public boolean exists() {
            System.out.println("perform exist command");
            return super.exists();
        }

    }

    // invoker classes = knows the command but knows nothing about its implementation
    // can store and queue commands
    // decoupled from the command
    static class TextFileExecutor {
        private final Deque<Command<?>> commands = new ArrayDeque<>();

        public void add(Command<?> command) {
            commands.offerFirst(command);
        }

        public Optional<?> execute() {
            return Optional
                    .ofNullable(commands.pollLast())
                    .map(Command::execute);
        }
    }

    // The client class is the CommandPattern.class in this case, it controls the invoker and the commands which are being executed.
    public static void main(String[] args) {
        TextFileExecutor executor = new TextFileExecutor();

        executor.add(new ExistCommand(new TextFile("./text.txt")));
        executor.add(() -> "Executing functional command!");

        System.out.println(executor.execute());
        System.out.println(executor.execute());
    }

}
