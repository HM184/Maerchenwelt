public class Rotkaeppchen extends VerwunschenerWald {

    private int gesundheit = 100;

    public Rotkaeppchen(Position position) {
        super(position);
    }

    public void geheHoch() {
        int newPosition = this.position.getY() - 1;
        this.position.setY(newPosition);
    }

    public void geheRunter() {
        int newPosition = this.position.getY() + 1;
        this.position.setY(newPosition);
    }

    public void geheLinks() {
        int newPosition = this.position.getX() - 1;
        this.position.setX(newPosition);
    }

    public void geheRechts() {
        int newPosition = this.position.getX() + 1;
        this.position.setX(newPosition);
    }

    public void gesundheitVerringern(int wert) {
        if ((gesundheit - wert) >= 0)
            gesundheit = gesundheit - wert;
    }

    public boolean istNochLebendig(){
        return gesundheit > 0;
    }

    @Override
    public String getName() {
        return "R";
    }
}
