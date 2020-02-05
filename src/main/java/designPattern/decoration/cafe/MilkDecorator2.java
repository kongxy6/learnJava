package designPattern.decoration.cafe;

public class MilkDecorator2 implements Cafe {

    private Cafe cafe;

    public MilkDecorator2(Cafe cafe) {
        this.cafe = cafe;
    }

    @Override
    public String getDescription() {
        return cafe.getDescription() + "add milk ";
    }

    @Override
    public float getPrice() {
        return cafe.getPrice() + 5.0f;
    }
}
