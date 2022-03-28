package hu.ptomi.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxiesJdk {

    interface Animal {
    }

    interface Dog extends Animal {
        String bark();
    }

    // actual implementation
    private static class BullDog implements Dog {
        @Override
        public String bark() {
            return "I bark as a bulldog!";
        }
    }

    // proxy handler
    private record DogInvocationHandler(Dog real) implements InvocationHandler {
        // called whenever any method is called on the interface array
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Invoked method on proxy: " + method.getName());
            // reroute the invocations to the real object
            return method.invoke(
                    real,
                    args
            );
        }
    }

    public static void main(String[] args) {
        Dog proxy = (Dog) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),  // lets the user specify into which ClassLoader we want our class to be injected
                new Class[]{Dog.class},      // proxy implements these interfaces
                new DogInvocationHandler(new BullDog())
        );

        // check generated proxy class
        System.out.println(proxy.getClass().toString());
        System.out.println(proxy.bark());
    }
}
