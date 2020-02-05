package designPattern.decoration.cafe.decorate;

import designPattern.decoration.cafe.Cafe;

public class MochaDecorator extends Decorate {

    public MochaDecorator(Cafe cafe) {
        super(cafe);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + "add mocha ";
    }

    @Override
    public float getPrice() {
        return super.getPrice() + 5.0f;
    }
}
