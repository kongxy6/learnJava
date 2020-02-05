package designPattern.decoration.cafe.decorate;

import designPattern.decoration.cafe.Cafe;

public class MilkDecorator extends Decorate {

    public MilkDecorator(Cafe cafe) {
        super(cafe);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + "add milk ";
    }

    @Override
    public float getPrice() {
        return super.getPrice() + 5.0f;
    }
}
