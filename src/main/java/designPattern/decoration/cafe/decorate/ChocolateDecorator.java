package designPattern.decoration.cafe.decorate;

import designPattern.decoration.cafe.Cafe;

public class ChocolateDecorator extends Decorate {

    public ChocolateDecorator(Cafe cafe) {
        super(cafe);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + "add chocolate ";
    }

    @Override
    public float getPrice() {
        return super.getPrice() + 5.0f;
    }
}
