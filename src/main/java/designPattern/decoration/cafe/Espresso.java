package designPattern.decoration.cafe;

import designPattern.decoration.Test;

public class Espresso implements Cafe, Test {

    private final String description;

    public Espresso() {
        description = "this is a DeCaf ";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public float getPrice() {
        return 1.1f;
    }

    @Override
    public void test() {

    }
}
