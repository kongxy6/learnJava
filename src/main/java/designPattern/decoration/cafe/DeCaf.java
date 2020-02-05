package designPattern.decoration.cafe;

import designPattern.decoration.Test;

public class DeCaf implements Cafe, Test {

    private String description;

    public DeCaf() {
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
