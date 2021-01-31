import java.util.ArrayList;
import java.util.Random;

public class Maerchenwelt {

    private int x; //Breite Waldstück
    private int y; //Höhe Waldstück
    private VerwunschenerWald[][] karte; //aktuelle Karte des Waldstücks
    private Oma oma;
    private Rotkaeppchen rotkaeppchen;
    private boolean foundOma = false;
    private boolean isLebending = true;
    private boolean isHome = false;

    public Maerchenwelt(int x, int y, int gefahrenAnzahl, int baumAnzahl) throws IllegalArgumentException {
        this.x = x;
        this.y = y;
        if ((gefahrenAnzahl + baumAnzahl) > (x * y - 3)) {
            throw new IllegalArgumentException("Reduzieren Sie die Anzahl der Baume und Gefahren.");
        }
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Vergroessern Sie den verwunschenen Wald.");
        }
        karte = new VerwunschenerWald[x][y];
        positionRotkaeppchen();
        positionOma();
        positionWolf();
        positionGefahr(gefahrenAnzahl);
        positionBaum(baumAnzahl);
    }

    public void positionRotkaeppchen() {
        Position r = new Position(0, 0);
        rotkaeppchen = new Rotkaeppchen(r);
        karte[0][0] = rotkaeppchen;
    }

    public void positionOma() {
        Random rand_x = new Random();
        Random rand_y = new Random();
        int min_x = x - 8;
        int max_x = x - 1;
        int oma_x = rand_x.nextInt(max_x - min_x) + min_x;

        int min_y = y - 8;
        int max_y = y - 1;
        int oma_y = rand_y.nextInt(max_y - min_y) + min_y;

        Position o = new Position(oma_x, oma_y);

        oma = new Oma(o);
        karte[oma_x][oma_y] = oma;


    }

    public void positionWolf() {
        Random rand_x = new Random();
        int min_x = 0;
        int min_y = 0;
        int wolf_x = rand_x.nextInt(x - min_x) + min_x;

        Random rand_y = new Random();
        int wolf_y = rand_y.nextInt(y - min_y) + min_y;

        Position w = new Position(wolf_x, wolf_y);
        if (karte[wolf_x][wolf_y] == null) {
            karte[wolf_x][wolf_y] = new Wolf(w);
        } else {
            positionWolf();
        }
    }

    public void positionGefahr(int gefahrenAnzahl) {
        while (gefahrenAnzahl > 0) {

            Random rand_x = new Random();
            int min_x = 0;
            int min_y = 0;
            int gefahr_x = rand_x.nextInt(x - min_x) + min_x;

            Random rand_y = new Random();
            int gefahr_y = rand_y.nextInt(y - min_y) + min_y;

            Position g = new Position(gefahr_x, gefahr_y);
            if (karte[gefahr_x][gefahr_y] == null) {
                karte[gefahr_x][gefahr_y] = new Gefahr(g);
                gefahrenAnzahl--;
            }
        }
    }

    public void positionBaum(int baumAnzahl) {

        while (baumAnzahl > 0) {

            Random rand_x = new Random();
            int min_x = 0;
            int min_y = 0;
            int baum_x = rand_x.nextInt(x - min_x) + min_x;

            Random rand_y = new Random();
            int baum_y = rand_y.nextInt(y - min_y) + min_y;

            Position b = new Position(baum_x, baum_y);
            if (karte[baum_x][baum_y] == null) {
                karte[baum_x][baum_y] = new Baum(b);
                baumAnzahl--;
            }
        }
    }

    public VerwunschenerWald[][] getKarte() {
        return karte;
    }

    public Oma getOma() {
        return oma;
    }

    public Rotkaeppchen getRotkaeppchen() {
        return rotkaeppchen;
    }

    public ArrayList<Position> wegFinden(Position ziel) {
        ArrayList<Position> weg = new ArrayList<Position>();
        int zuege = 500;

        while (zuege > 0 && !isHome) {
            Random random_bewegung = new Random();
            int bewegung = random_bewegung.nextInt(4);
            Position r = rotkaeppchen.getPosition();
            int r_x = r.getX();
            int r_y = r.getY();

            switch (bewegung) {
                case 0:
                    if (r_y - 1 >= 0 && r_y - 1 < y) {
                        if (karte[r_x][r_y - 1] == null) {
                            rotkaeppchen.geheHoch();
                            deletePosition(r_x, r_y);
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                        } else if (karte[r_x][r_y - 1].getName().equals("W")) {
                            rotkaeppchen.geheHoch();
                            decreaseHealth(r_x, r_y - 1);
                            deletePosition(r_x,r_y);

                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                            break;
                        } else if (karte[r_x][r_y - 1].getName().equals("G")) {
                            rotkaeppchen.geheHoch();
                            decreaseHealth(r_x, r_y - 1);
                            deletePosition(r_x, r_y);
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                            break;
                        }
                        if ((karte[r_x][r_y - 1].position.equals(ziel))) {
                            if (foundOma) {
                                isHome = true;
                            } else {
                                zuege = arrivalOma(zuege);
                                Position neu = new Position(r.getX(), r.getY());
                                weg.add(neu);
                            }
                        }
                    }
                    break;
                case 1:
                    if (((r_x - 1) >= 0) && ((r_x - 1) < x)) {
                        if (karte[r_x - 1][r_y] == null) {
                            rotkaeppchen.geheLinks();
                            deletePosition(r_x, r_y);
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                        } else if (karte[r_x - 1][r_y].getName().equals("W")) {
                            rotkaeppchen.geheLinks();
                            decreaseHealth(r_x - 1, r_y);
                            deletePosition(r_x, r_y);
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                            break;
                        } else if (karte[r_x - 1][r_y].getName().equals("G")) {
                            rotkaeppchen.geheLinks();
                            decreaseHealth(r_x - 1, r_y);
                            deletePosition(r_x, r_y);
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                            break;
                        }
                        if ((karte[r_x - 1][r_y].position.equals(ziel))) {
                            if (foundOma) {
                                isHome = true;
                            } else {
                                zuege = arrivalOma(zuege);
                                Position neu = new Position(r.getX(), r.getY());
                                weg.add(neu);
                            }
                        }
                    }
                    break;
                case 2:
                    if ((r_x + 1) >= 0 && (r_x + 1) < x) {
                        if (karte[r_x + 1][r_y] == null) {
                            rotkaeppchen.geheRechts();
                            deletePosition(r_x, r_y);
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                        } else if (karte[r_x + 1][r_y].getName().equals("W")) {
                            rotkaeppchen.geheRechts();
                            decreaseHealth(r_x + 1, r_y);
                            deletePosition(r_x, r_y);
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);

                        } else if (karte[r_x + 1][r_y].getName().equals("G")) {
                            rotkaeppchen.geheRechts();
                            decreaseHealth(r_x + 1, r_y);
                            deletePosition(r_x, r_y);

                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                            break;

                        }
                        if ((karte[r_x + 1][r_y].position.equals(ziel))) {
                            if (foundOma) {
                                isHome = true;
                            } else {
                                zuege = arrivalOma(zuege);
                                Position neu = new Position(r.getX(), r.getY());
                                weg.add(neu);
                            }
                        }
                    }
                    break;
                case 3:
                    if ((r_y + 1) >= 0 && (r_y + 1) < y) {
                        if (karte[r_x][r_y + 1] == null) {
                            rotkaeppchen.geheRunter();
                            deletePosition(r_x, r_y);
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                        } else if (karte[r_x][r_y + 1].getName().equals("W")) {
                            rotkaeppchen.geheRunter();
                            decreaseHealth(r_x, r_y + 1);
                            deletePosition(r_x, r_y);
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                            break;

                        } else if (karte[r_x][r_y + 1].getName().equals("G")) {
                            rotkaeppchen.geheRunter();
                            decreaseHealth(r_x, r_y + 1);
                            deletePosition(r_x, r_y);

                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                            break;

                        }
                        if ((karte[r_x][r_y + 1].position.equals(ziel))) {
                            if (foundOma) {
                                isHome = true;
                            } else {
                                zuege = arrivalOma(zuege);
                                Position neu = new Position(r.getX(), r.getY());
                                weg.add(neu);
                            }

                        }
                    }
                    break;
                default:
                    break;
            }
            zuege--;
        }
        if (zuege == 0 && isLebending) {
            if (foundOma) {
                printWald();
                System.out.println("Rotkaeppchen hat sich auf dem Heimweg verlaufen.");
            } else {
                printWald();
                foundOma = false;
                System.out.println("Rotkaeppchen hat sich auf dem Weg zur Oma verlaufen.");
            }
        }
        if (isHome) {
            printWald();
            System.out.println("Rotkaeppchen ist wieder zu Hause angekommen.");
        }
        return weg;
    }

    public int arrivalOma(int züge) {
        printWald();
        System.out.println("Rotkaeppchen ist bei Oma angekommen.");
        züge = 0;
        foundOma = true;
        return züge;
    }

    public void printWald() {
        // Rahmen: linke obere Ecke
        System.out.print("+");
        // Rahmen: erste Zeile
        for (int i = 0; i < x; i++) {
            System.out.print("-");
        }
        // Rahmen: rechte obere Ecke
        System.out.println("+");
        for (int j = 0; j < y; j++) {
            // Rahmen: linker Rand
            System.out.print("|");
            // Die eigentliche Karte
            for (int i = 0; i < x; i++) {
                if (karte[i][j] != null) {
                    System.out.print(karte[i][j].getName());
                } else {
                    System.out.print(" ");
                }
            }
            // Rahmen: rechter Rand
            System.out.println("|");
        }
        // Rahmen: linke untere Ecke
        System.out.print("+");
        // Rahmen: letzte Zeile
        for (int i = 0; i < x; i++) {
            System.out.print("-");
        }
        // Rahmen: rechte untere Ecke
        System.out.println("+");
    }

    public void start() {
        printWald();
        Position ziel = oma.getPosition();
        wegFinden(ziel);
        if (foundOma) {
            rotkaeppchen.setGesundheit(100);
            rotkaeppchen.sprechen(oma, 1);
            Position zuhause = new Position(0, 0);
            wegFinden(zuhause);
        }

        if (!isLebending) {
            if (!foundOma) {
                System.out.println("Rotkaeppchen ist nicht bei der Oma angekommen.");
            } else if (!isHome) {
                System.out.println("Rotkaeppchen ist nicht wieder zu Hause angekommen.");
            }
        }
    }

    public void deletePosition(int i, int j) {
        if (karte[i][j] instanceof Rotkaeppchen) {
            karte [i] [j] = null;
        }

        if(karte[rotkaeppchen.position.getX()][rotkaeppchen.position.getY()] == null){
            karte[rotkaeppchen.position.getX()][rotkaeppchen.position.getY()] = rotkaeppchen;
        }
    }

    public void decreaseHealth(int x, int y) {
        rotkaeppchen.gesundheitVerringern(karte[x][y].schaden);
        isLebending = rotkaeppchen.istNochLebendig();


    }
}
