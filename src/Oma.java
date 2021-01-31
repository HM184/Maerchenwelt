public class Oma extends VerwunschenerWald implements Person {

    public Oma(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "O";
    }

    @Override
    public void sprechen(Person konversationspartner, int zaehler) {
        if (zaehler == 2) {
            System.out.println("Hallo Rotkaeppchen");
            zaehler++;
            konversationspartner.sprechen(this, zaehler);
        }
    }
}
