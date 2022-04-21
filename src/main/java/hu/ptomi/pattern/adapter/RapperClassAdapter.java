package hu.ptomi.pattern.adapter;

public class RapperClassAdapter extends Rapper implements Singer {
    @Override
    public void sing() {
        super.talk();
    }
}
