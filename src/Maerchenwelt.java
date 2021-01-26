import java.util.ArrayList;
import java.util.Random;

public class Maerchenwelt {

    private int x; //Breite Waldstück
    private int y; //Höhe Waldstück
    private VerwunschenerWald[][] karte; //aktuelle Karte des Waldstücks
    private Oma oma;
    private Rotkaeppchen rotkaeppchen;

    public Maerchenwelt(int x, int y, int gefahrenAnzahl, int baumAnzahl) throws IllegalArgumentException {
        this.x = x;
        this.y = y;
        if ((gefahrenAnzahl + baumAnzahl) > (x * y - 3)) {
            throw new IllegalArgumentException("Reduzieren Sie die Anzahl der Baume und Gefahren.");
        }
        if (x < 2 || y < 2) {
            throw new IllegalArgumentException("Vergroessern Sie den verwunschenen Wald.");
        }
        karte = new VerwunschenerWald[x][y];
        Position r = new Position(0, 0);
        rotkaeppchen = new Rotkaeppchen(r);
        karte[0][0] = rotkaeppchen;
        initializeOma();
        positionBaum(baumAnzahl);


    }

    public void initializeOma() {
        Random rand_x = new Random();
        Random rand_y = new Random();
        int min_x = x - 8;
        int max_x = x - 1;
        int oma_x = rand_x.nextInt(max_x - min_x) + min_x;

        int min_y = y - 8;
        int max_y = y - 1;
        int oma_y = rand_y.nextInt(max_y - min_y) + min_y;

        //  Position o = new Position(oma_x, oma_y);
        Position o = new Position(5, 5);
        oma = new Oma(o);
        // karte[oma_x][oma_y] = oma;
        karte[5][5] = oma;


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
        int züge = 500;

        while (züge > 0) {
            Random random_bewegung = new Random();
            int bewegung = random_bewegung.nextInt(4);
            Position r = rotkaeppchen.getPosition();
            int r_x = r.getX();
            int r_y = r.getY();

            switch (bewegung) {
                case 0:
                    if (((r_y - 1) >= 0) && ((r_y - 1) < y)) {
                        if (karte[r_x][r_y - 1] == null) {
                            rotkaeppchen.geheHoch();
                            karte[r_x][r_y] = null;
                            karte[r.getX()][r.getY()] = rotkaeppchen;
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                        } else if ((karte[r_x][r_y - 1].position.equals(ziel))) {
                            System.exit(0);
                        }
                    }

                    break;
                case 1:
                    if (((r_x - 1) >= 0) && ((r_x - 1) < x)) {
                        if (karte[r_x - 1][r_y] == null) {
                            rotkaeppchen.geheLinks();
                            karte[r_x][r_y] = null;
                            karte[r.getX()][r.getY()] = rotkaeppchen;
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                        } else if ((karte[r_x - 1][r_y].position.equals(ziel))) {
                            System.exit(0);
                        }
                    }

                    break;
                case 2:
                    if ((r_x + 1) >= 0 && (r_x + 1) < x) {
                        if (karte[r_x + 1][r_y] == null) {
                            rotkaeppchen.geheRechts();
                            karte[r_x][r_y] = null;
                            karte[r.getX()][r.getY()] = rotkaeppchen;
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                        } else if ((karte[r_x + 1][r_y].position.equals(ziel))) {
                            System.exit(0);
                        }
                    }


                    break;
                case 3:
                    if ((r_y + 1) >= 0 && (r_y + 1) < y) {
                        if (karte[r_x][r_y + 1] == null) {
                            rotkaeppchen.geheRunter();
                            karte[r_x][r_y] = null;
                            karte[r.getX()][r.getY()] = rotkaeppchen;
                            Position neu = new Position(r.getX(), r.getY());
                            weg.add(neu);
                        } else if ((karte[r_x][r_y + 1].position.equals(ziel))) {
                            System.exit(0);
                        }
                    }
                    break;

                default:
                    break;
            }

            züge--;
            printWald();

              /*
            if ((rotkaeppchen.position).equals(ziel)) {
                System.out.println("Rotkaeppchen ist bei Oma angekommen.");
                printWald();
                break;
            }
            if (züge == 0) {
                System.out.println("Rotkaeppchen hat sich auf dem Weg zur Oma verlaufen.");
                printWald();
            }*/
        }

        if ((rotkaeppchen.position).equals(ziel)) {
            System.out.println("Rotkaeppchen ist bei Oma angekommen.");
            printWald();
            System.out.println(züge);
        }
        if (züge == 0) {
            //  System.out.println("Rotkaeppchen hat sich auf dem Weg zur Oma verlaufen.");
            // printWald();
            // System.out.println(züge);
        }


        return weg;
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
    }


}
