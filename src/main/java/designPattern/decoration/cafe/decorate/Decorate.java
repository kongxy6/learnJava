package designPattern.decoration.cafe.decorate;

import designPattern.decoration.cafe.Cafe;

public class Decorate implements Cafe {

    Cafe cafe;

    public Decorate(Cafe cafe) {
        this.cafe = cafe;
    }

    @Override
    public String getDescription() {
        return cafe.getDescription();
    }

    @Override
    public float getPrice() {
        return cafe.getPrice();
    }
}
