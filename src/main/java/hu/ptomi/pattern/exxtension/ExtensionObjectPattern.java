package hu.ptomi.pattern.exxtension;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ExtensionObjectPattern = make an objects interface extendable by adding more interfaces, without affecting already existing clients, without direct subclassing
 * <p>
 * Known name: Facet
 * <p>
 * JDK Use Case: security certificates works almost like this
 */
public class ExtensionObjectPattern {

    // marker interface
    interface UnitExtension {
    }

    // extension registry and abstract business class
    static abstract class Unit {
        private Map<Class<? extends UnitExtension>, UnitExtension> extensions = new ConcurrentHashMap<>();
        public String name;

        public Unit(String name) {
            this.name = name;
        }

        protected void registerUnitExtension(Class<? extends UnitExtension> aClass, UnitExtension extension) {
            extensions.put(aClass, extension);
        }

        // Generics only work for compile time, these types will be erased by the compiler.
        // E will be replaced with Object and class cast are applied in front of them where needed.
        public <E extends UnitExtension> Optional<E> getUnitExtension(Class<E> aClass) {
            return Optional.ofNullable((E) extensions.get(aClass));
        }
    }

    // business class
    static class SoldierUnit extends Unit {
        public SoldierUnit(String name) {
            super(name);
        }
    }

    // extension class
    interface SoldierUnitExtension extends UnitExtension {
        boolean isSoldierReady();
    }

    static class SoldierUnitExtensionImpl implements SoldierUnitExtension {
        private SoldierUnit soldierUnit;

        public SoldierUnitExtensionImpl(SoldierUnit soldierUnit) {
            this.soldierUnit = soldierUnit;
        }

        @Override
        public boolean isSoldierReady() {
            return true;
        }
    }

    public static void main(String[] args) {
        var soldier = new SoldierUnit("unit_1");

        soldier.registerUnitExtension(
                SoldierUnitExtension.class,
                new SoldierUnitExtensionImpl(soldier)
        );

        // soldier interfaces
        System.out.println(soldier.name);

        // extension interfaces
        System.out.println(
                soldier
                        .getUnitExtension(SoldierUnitExtension.class)
                        .map(SoldierUnitExtension::isSoldierReady)
                        .orElse(false)
        );
    }
}
