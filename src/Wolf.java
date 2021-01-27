public class Wolf extends VerwunschenerWald {

    public Wolf(Position position) {
        super(position);
        schaden = 5;
    }

    @Override
    public String getName() {
        return "W";
    }
}
