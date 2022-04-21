package hu.ptomi.pattern.adapter;

public class Bass implements Singer {
    public Bass() {
        System.out.println("Bass prepares");
    }

    @Override
    public void sing() {
        System.out.println("Bass sings....");
    }
}
