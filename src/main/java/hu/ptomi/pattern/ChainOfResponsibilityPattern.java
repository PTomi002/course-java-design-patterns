package hu.ptomi.pattern;

/**
 * ChainOfResponsibilityPattern = lets you pass requests along a chain of handlers, each handler decide what to do or pass it to the next handler
 * <p>
 * JDK Use Case: Mouse Listeners can be chained
 */
public class ChainOfResponsibilityPattern {

    static abstract class MailHandler {
        protected final MailHandler next;

        protected MailHandler(MailHandler next) {
            this.next = next;
        }

        abstract void handle(Object o);
    }

    static class SPAMHandler extends MailHandler {

        public SPAMHandler(MailHandler next) {
            super(next);
        }

        @Override
        void handle(Object o) {
            String mail = (String) o;

            if (mail.contains("spam"))
                throw new IllegalArgumentException("mail contains spam");

            next.handle(o);
        }
    }

    static class JavaTopicHandler extends MailHandler {

        protected JavaTopicHandler(MailHandler next) {
            super(next);
        }

        @Override
        void handle(Object o) {
            String mail = (String) o;

            if (mail.length() != 10)
                throw new IllegalArgumentException("mail is too long");

            next.handle(o);
        }
    }

    public static void main(String[] args) {
        MailHandler handler = new SPAMHandler(new JavaTopicHandler(null));

        handler.handle("ok");
        handler.handle("this string is too long");
    }
}
