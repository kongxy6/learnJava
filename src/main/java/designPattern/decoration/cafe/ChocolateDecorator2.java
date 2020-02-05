package designPattern.decoration.cafe;

public class ChocolateDecorator2 implements Cafe {

    private Cafe cafe;

    public ChocolateDecorator2(Cafe cafe) {
        this.cafe = cafe;
    }

    @Override
    public String getDescription() {
        return cafe.getDescription() + "add chocolate ";
    }

    @Override
    public float getPrice() {
        return cafe.getPrice() + 5.0f;
    }
}
