package hu.ptomi.pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import static java.util.Objects.nonNull;

/**
 * Factory Method Pattern = Define an interface and let the subclasses decide what class to instantiate.
 * <p>
 * JDK Use Case: Collection iterators, SQL.prepareStatement(...) (Oracle Database Connection creates OracleStatement)
 * <p>
 * Factory METHOD is not like a static method e.g.: DriverManager.getConnection(...) that returns an object, that is a Simple Factory, but we are talking about Factory METHOD.
 */
public class FactoryMethodPattern {
    public static void main(String[] args) {
        // Factory method should not be called from superclass constructor.
        SwingUtilities.invokeLater(FactoryMethodPattern::init);
    }

    private static void init() {
        var frame = new JFrame("Messy 3x4 table factory call");
        // Just to initialize all static / cached stuff in the class.
        new MyTable(3, 4);

        var table = new FixedTable(3, 4); // Debug point 1.
        frame.add(new JScrollPane(table));
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static class MyTable extends JTable {
        private final int rows, columns;

        public MyTable(int numRows, int numColumns) { // Debug point 2. (enabled after first MyTable construction): Debug this line and Force Step Into it, go into JTable(null, null, null) method.
            rows = numRows;
            columns = numColumns;
        }

        // default constructor of JTable will be called and from there JDK calls createDefaultDataModel(..) factory method, so in our subclass every property will be default or null.
        @Override
        protected TableModel createDefaultDataModel() { // We call this method even before setting the rows and columns variables, so the table model will be empty.
            return new DefaultTableModel(rows, columns);
        }
    }

    static class FixedTable extends JTable {
        // make it thread-safe
        private static ThreadLocal<int[]> cache = ThreadLocal.withInitial(() -> null);

        private final int rows, columns;

        public FixedTable(int numRows, int numColumns) { // Debug point 2. (enabled after first MyTable construction): Debug this line and Force Step Into it, go into JTable(null, null, null) method.
            // fixing it with calling a hacky super(...)
            super(saveCache(numRows, numColumns));
            rows = numRows;
            columns = numColumns;
            cache.remove();
        }

        @Override
        protected TableModel createDefaultDataModel() {
            if (nonNull(cache.get())) {
                return new DefaultTableModel(cache.get()[0], cache.get()[1]);
            } else
                return new DefaultTableModel(rows, columns);
        }

        private static TableModel saveCache(int row, int col) {
            cache.set(new int[]{row, col});
            return null;
        }

    }

}
