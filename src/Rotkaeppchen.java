import java.util.Optional;

public class Rotkaeppchen extends VerwunschenerWald implements Person{

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
            gesundheit = gesundheit - wert;

            if(gesundheit < 0){
                gesundheit = 0;
            }
    }

    public void setGesundheit(int gesundheit) {
        this.gesundheit = gesundheit;
    }

    public boolean istNochLebendig(){
        return gesundheit > 0;
    }

    @Override
    public String getName() {
        return "R";
    }

    @Override
    public void sprechen(Person konversationspartner, int zaehler) {
        if(zaehler == 1 ){
            System.out.println("Hallo, Oma");
            zaehler ++;
            konversationspartner.sprechen(this, zaehler);
        }

        if(zaehler == 3){
            System.out.println("Tschuess, Oma");
        }
    }
}
