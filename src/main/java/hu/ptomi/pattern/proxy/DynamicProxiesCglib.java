package hu.ptomi.pattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Bytecode instrumentation lib allows manipulating or creating classes after the compilation phase of a program.
 * <p>
 * Use Case: Spring, Hibernate, Mock fws.
 */
public class DynamicProxiesCglib {

    interface Animal {
    }

    interface Dog extends Animal {
        String bark();
    }

    // actual implementation
    private static class Whippet implements Dog {
        public Whippet() {
        }

        @Override
        public String bark() {
            return "I bark as a whippet!";
        }
    }

    // proxy handler
    private record DogMethodInterceptor(Dog real) implements MethodInterceptor {
        // called whenever any method is called on the subclass
        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println("Invoked method on proxy: " + method.getName());
            return method.invoke(
                    real,
                    args
            );
        }
    }

    // Start with Add VM option: --add-opens java.base/java.lang=ALL-UNNAMED
    public static void main(String[] args) {
        var enhancer = new Enhancer();
        enhancer.setSuperclass(Whippet.class);
        enhancer.setCallback(
                new DogMethodInterceptor(new Whippet())
        );
        var proxy = (Whippet) enhancer.create();

        // check generated proxy class
        System.out.println(proxy.getClass().toString());
        System.out.println(proxy.bark());
    }
}
