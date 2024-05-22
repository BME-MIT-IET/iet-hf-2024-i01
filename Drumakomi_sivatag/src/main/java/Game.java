import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A jatek elinditasaert es lezaresaert felelos, implementalja a Notify
 * interfeszt
 */
public class Game implements Notify { // Barni
    /**
     * A jatek ideje koronkent (1 kor=ameg az osszes szabotor es mechanic lelepi a
     * lepeseit egyszer)
     */
    private int time;
    /**
     * A jatekosok ennyit lephetnek fejenkent egy korben
     */
    private int steps;
    /**
     * Ennyi szabotor es szerelo lesz a jatekban
     */
    private int NumberOfPlayers;
    /**
     * Olvassa a felhasznalo inputjat
     */
    private final Scanner sc = new Scanner(System.in);
    /**
     * A randomsagot ez allitja
     */
    public static boolean rand = true;
    /**
     * A pumpa generalas ideje
     */
    public static int PumpGenTime;
    /**
     * A cso generalas ideje
     */
    public static int PipeGenTime;
    /**
     * Kor szamlalo
     */
    private int roundtime = 1;
    /**
     * Konstruktor
     */

    // GUI -s módosítások, állapotgép lesz a Game
    int currentTime = 0; // Körökszáma, ha elég nagy lesz, vége a játéknak
    int roundI = 0; // Játékosok
    int roundJ = 0; // steppek
    boolean currentTeam = false; // False: Szabotőr, True: Mechanic
    Player currentPlayer; // Az éppen aktív csapaton belül a já
    boolean ended = false; // Ha true: vége a játéknak
    //

    public Game(int t, int s, int np) {
        time = t;
        steps = s;
        NumberOfPlayers = np;
    }

    /**
     * Letrehozza a palyat, a jatekosokat elotte inicializal, majd elkezdi a jatekot
     */
    public void Start() {
        Initialization();
        CreatePlayers();
        while (time > 0) {
            Round();
        }
        TimerNotify();
    }

    /**
     * Letrehozza a jatekosokat
     */
    public void CreatePlayers() {
        for (int i = 0; i < NumberOfPlayers; i++) {
            Mechanic me = new Mechanic(i, 0, PipeSystem.getEndpoints().get(0));
            PipeSystem.getEndpoints().get(0).SetPlayer(me);
            Saboteur sa = new Saboteur(i + 1, 0, PipeSystem.getEndpoints().get(1));
            PipeSystem.getEndpoints().get(1).SetPlayer(sa);
            PipeSystem.getMechanic().add(me);
            PipeSystem.getSaboteur().add(sa);
        }
    }

    /**
     * Beallitja a pumpak es csovek generalasanak idejet, ha a randomsag ki van
     * kapcsolva
     * 
     * @param pugt A pumpa generalas ideje
     * @param pigt A cso generalas ideje
     */
    public static void Settings(int pugt, int pigt) {
        PumpGenTime = pugt;
        PipeGenTime = pigt;
        rand = false;
    }

    /**
     * Ertesiti az osztalyt, hogy vege van a jateknak
     */
    public void TimerNotify() {
        System.out.println("Az ido lejart, a jatek veget ert!");
        String winner = "";
        if (PipeSystem.getM_water() > PipeSystem.getS_water()) {
            winner = "Mechanic";
        } else if (PipeSystem.getM_water() < PipeSystem.getS_water()) {
            winner = "Saboteur";
        } else {
            System.out.println("Dontetlen lett a jatek");
            /*
             * System.out.println("""
             * ⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢾⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠏⠃⠀⠀⣀⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠋⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣶⣿⣿⣿⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⣿⣿⣿⡟⠀⠀⢀⡀⠀⠀⠀⠀⠀⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣿⣿⣿⣿⠷⠛⣫⣭⡉⠉⠲⣾⡿⠋⠉⣩⣭⣍⠛⢶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣼⣿⣿⣿⣿⣿⣿⣿⡇⠀⢸⣿⣿⣿⠀⠀⠟⠧⠀⠸⣿⣿⣿⠀⠈⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⠀⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⡀⠉⠋⠁⣠⣆⣀⣀⣠⣄⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⢈⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⢀⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⢀⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡄⠈⠉⠛⠻⠿⢿⣿⡿⠟⠛⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠀⠀⠀⠀
             * ⠀⠀⠀⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡼⠀⠀⠀⠀
             * ⠀⠀⠀⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⡇⠀⠀⠀
             * ⠀⠀⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣏⠀⢀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣿⠁⢃⠀⠀⠀
             * ⠀⠀⣿⣿⣿⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠻⠿⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⡇⢸⠀⠀⠀
             * ⠀⢸⣿⣿⡇⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣶⣶⣦⣤⣄⣀⡀⠀⠀⠀⠀⠀⠀⣀⣤⡄⠀⠀⠀⠀⠀⣸⣿⣿⣿⣿⢿⣷⢸⡀⠀⠀
             * ⠀⣼⣿⣿⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠀⠀⠀⠀⣠⣿⣿⣿⣿⠃⠀⣿⡏⡇⠀⠀
             * ⠀⣿⣿⡇⠀⠀⠀⠘⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⣀⣀⣤⣶⣿⣿⣿⠟⠁⠀⠀⠉⠁⡇⠀⠀
             * ⠀⡿⠛⠃⠀⠀⠀⠀⠀⠛⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠀⠀⠀⠀⠀⠀⡇⠀⠀
             * ⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠉⠀⠀⠀⠀⠀⠀⡀⠀⡇⠀⠀
             * ⠀⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⡟⠛⠿⠿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⢠⠀⡇⠀⠀
             * ⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠁⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠁⠀⠀⠀⢹⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⣷⠀⠀
             * ⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⣿⠀⠀
             * ⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⢹⡄⠀
             * ⢠⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⡀
             * ⢸⣿⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣣
             * ⣾⣿⠀⠀⢹⡀⠀⠀⠀⠀⠀⠀⠀⠀⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⢀⣿
             * ⢿⢿⡄⠀⠀⠳⡀⠀⠀⠀⠀⠀⠀⠀⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⡄⠁⠀⣾⡟
             * ⠸⡈⢧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⡿⠁
             * ⠀⠑⢌⣷⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠴⠞⠉⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠃⠀⠀⣶⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠀⠀⢠⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⣤⡴⠋⠀⢀⣠⢿⡇⠀⣀⠔⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⢸⣿⠀⠀⠀⠻⣀⣀⠀⠀⠀⠀⠀⠀⠀
             * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠁⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠢⠀⠸⠛⠓⠢⠄⠀⠉⠉⠁⠀⠀⠀⠀⠀⠀""");
             */
        }
        /*
         * if(!winner.equals("")){System.out.println("A "
         * +winner+" csapat-e lett a Victory Royale!");
         * System.out.println("""
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣶⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⠟⠛⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣾⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣾⣿⣿⣿⠿⣿⣿⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⡁⠀⢿⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠭⠛⠻⣿⣿⣿⣿⣤⣼⣿⣿⣿⣿⣿⣷⣶⣶⣄⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣆
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⠀⠀⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠛⠉⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣤⣀⣤⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⣿⣿⣿⠿⠿⠿⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠈⠙⣿⣿⣿⣿⣿⣿⡆⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⣿⣿⣿⢿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⣀⣠⡀⢀⣿⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⡿⠃⠀⠀⠀⠀⠀
         * ⣴⣾⣶⣶⣿⣿⣷⣿⣿⡟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⠀
         * ⠻⠿⣿⣿⣿⣿⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠉⠉⠉⠿⠿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣶⡧⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣿⣿⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
         * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⡿⠿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀""");}
         */
    }

    /**
     * A szabotorok es szerelok egy-egy koret szimulalja, es ha lejart az ido akkor
     * befejezi a jatekot
     */
    public void Round() {
        // Végigmegyünk az összes játékoson, és feldolgozzuk a körüket mint Szabotőr
        for (int i = 0; i < NumberOfPlayers; i++) {
            processPlayerRound(PipeSystem.getSaboteur().get(i), "Saboteur");
        }

        // Végigmegyünk az összes játékoson, és feldolgozzuk a körüket mint Szerelő
        for (int i = 0; i < NumberOfPlayers; i++) {
            processPlayerRound(PipeSystem.getMechanic().get(i), "Mechanic");
        }

        // Csökkentjük az időt és növeljük a kör idejét
        time--;
        roundtime++;
        System.out.println("A " + roundtime + " korben vagyunk eppen");

        // Értesítjük az összes csövet, hogy az időzítő lejárt
        for (Pipe pipe : PipeSystem.getPipes()) {
            pipe.TimerNotify();
        }
    }

    // Egy játékos körének feldolgozása
    private void processPlayerRound(Player player, String role) {
        System.out.println("_________________________________________________________________________");
        System.out.println("Az " + player.getId() + " id-ju " + role + " kore van eppen!");

        // A játékos lépéseit feldolgozzuk
        for (int j = 0; j < steps;) {
            String[] event = sc.nextLine().toLowerCase().split(" ");
            try {
                // Ha az esemény feldolgozása sikeres, növeljük a lépésszámot és értesítünk
                // mindenkit
                if (processEvent(player, event)) {
                    j++;
                    NotifyAllTimePassed();
                }
            } catch (NumberFormatException e) {
                System.out.println("Rossz parametert adtal meg");
            }
        }
    }

    // Esemény feldolgozása
    private boolean processEvent(Player player, String[] event) {
        switch (event[0]) {
            case "damage":
                return player.getElement().Damage();
            case "move":
                return handleMoveEvent(player, event);
            case "setstucky":
                return player.SetStucky();
            case "setslippery":
                return player.SetStucky();
            case "setpipe":
                return handleSetPipeEvent(player, event);
            case "skip":
                return true;
            case "help":
                printHelp();
                return false;
            case "info":
                handleInfoEvent(player, event);
                return false;
            case "settings":
                handleSettingsEvent(event);
                return false;
            case "exit":
                System.exit(0);
                return false;
            case "repair":
                if (player instanceof Mechanic) {
                    return ((Mechanic) player).Repair();
                }
                return false;
            case "pickuppump":
                if (player instanceof Mechanic) {
                    return ((Mechanic) player).Pickuppump();
                }
                return false;
            case "cutpipe":
                if (player instanceof Mechanic) {
                    return ((Mechanic) player).CutPipe();
                }
                return false;
            case "setpump":
                return handleSetPumpEvent(player, event);
            case "saveandexit":
                return handleSaveAndExitEvent(event);
            default:
                System.out.println("Hibas a megadott parancs! A Help parancsal tudod a parancsok listajat kilistazni.");
                return false;
        }
    }

    // A "move" parancs kezelése
    private boolean handleMoveEvent(Player player, String[] event) {
        if (event.length == 2) {
            return player.Move(Integer.parseInt(event[1]));
        } else {
            System.out.println("Keves parametert adtal meg! A masodik parameterben meg kell adni a szomszed indexet.");
            return false;
        }
    }

    // A "setpipe" parancs kezelése
    private boolean handleSetPipeEvent(Player player, String[] event) {
        if (event.length == 3) {
            // Itt implementáld a logikát a cső átállításához
            // Ha a ChangePipe metódus implementálva van, a következő sort oldd fel
            // return player.getElement().ChangePipe(Integer.parseInt(event[1]),
            // Integer.parseInt(event[2]));
        } else {
            System.out.println("Tul keves parameter lett megadva.");
        }
        return false;
    }

    // Az "info" parancs kezelése
    private void handleInfoEvent(Player player, String[] event) {
        if (event.length == 1) {
            player.Info();
        } else if (event.length == 2) {
            player.Info(Integer.parseInt(event[1]));
        }
    }

    // A "settings" parancs kezelése
    private void handleSettingsEvent(String[] event) {
        if (event.length == 4) {
            Settings(Integer.parseInt(event[2]), Integer.parseInt(event[3]));
        } else {
            System.out.println("Tul keves parameter lett megadva.");
        }
    }

    // A "setpump" parancs kezelése
    private boolean handleSetPumpEvent(Player player, String[] event) {
        if (player instanceof Mechanic) {
            // Ha szükséges, oldd fel és implementáld a setPump logikát
            // return ((Mechanic) player).setPump(Integer.parseInt(event[1]),
            // Integer.parseInt(event[2]));
        }
        return false;
    }

    // A "saveandexit" parancs kezelése
    private boolean handleSaveAndExitEvent(String[] event) {
        int j = 0;
        int i = 0;
        if (event.length == 2 && j + 1 == steps && i + 1 == NumberOfPlayers) {
            try {
                SaveAndExit(event[1]);
                System.exit(0);
            } catch (Exception e) {
                System.out.println("Valami hiba tortent");
            }
        } else {
            System.out.println("Ez a muvelet most nem lehetseges");
        }
        return false;
    }

    // A segítség (help) parancs kinyomtatása
    private void printHelp() {
        System.out.println("A kovetkezo parancsokat tudod kiadni:");
        System.out.println("Damage - Eltorod a pumpat, amin allsz, ha az pumpa.");
        System.out.println("Move - A parameterben megadott indexu szomszedra lepsz, ha az lehetseges.");
        System.out.println("SetStucky - Ragadossa teszed a csovet, amin allsz, ha csovon allsz.");
        System.out.println("SetSlippery - Csuszossa teszed a csovet, amin allsz, ha csovon allsz.");
        System.out.println(
                "SetPipe - Atkotod a csovet, az elso parameterben kell megadni, hogy az inputjat(0) vagy outputjat(1) akarok atkotni a masik parameterben pedig, hogy a rendszerben levo pumpak kozul hanyadikba (pl. 0, ha a legeloszor felvett pumpaba).");
        System.out.println("Skip - Skippeled az egyik lepesi lehetosegedet.");
        System.out.println("Info - Kiirja az elem adatait amin allsz.");
        System.out.println("Info - Kiirja a parameterben megadott elem adatait.");
        System.out.println(
                "Settings - Az elso parameterben meg kell adni, hogy a random dolgok random tortenjenek-e off, ha nem, illetve on ha igen a masodik parameterben a pumpa generalasanak az idejet kell megadni a ciszternanal, a harmadikban pedig a cso generalasanak idejet a ciszternanal ");
    }

    /**
     * Lementi a jatekot es kilep
     * 
     * @param file A file amibe ment
     */
    public void SaveAndExit(String file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            ArrayList<Object> list = new ArrayList<>();
            ArrayList<Integer> ints = new ArrayList<>();
            ints.add((time - 1));
            ints.add(steps);
            ints.add(PipeSystem.getM_water());
            ints.add(PipeSystem.getS_water());

            list.add(PipeSystem.getMechanic());
            list.add(PipeSystem.getSaboteur());
            list.add(PipeSystem.getPipes());
            list.add(PipeSystem.getPumpes());
            list.add(PipeSystem.getEndpoints());
            list.add(ints);
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
        } catch (Exception e) {
            System.err.println("Valami hiba tortent.");
        }
    }

    public void Load(String file) {
        try (FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);) {
            ArrayList<Object> list = (ArrayList<Object>) objectInputStream.readObject();
            PipeSystem.setMechanic((ArrayList<Mechanic>) list.get(0));
            PipeSystem.setSaboteur((ArrayList<Saboteur>) list.get(1));
            PipeSystem.setPipes((ArrayList<Pipe>) list.get(2));
            PipeSystem.setPumpes((ArrayList<Pump>) list.get(3));
            PipeSystem.setEndpoints((ArrayList<Node>) list.get(4));
            ArrayList<Integer> ints = (ArrayList<Integer>) list.get(5);
            objectInputStream.close();
            NumberOfPlayers = PipeSystem.getMechanic().size();
            time = ints.get(0);
            steps = ints.get(1);
            PipeSystem.setWater(ints.get(2), ints.get(3));
            while (time > 0) {
                Round();
            }
            TimerNotify();
        } catch (Exception e) {
            System.err.println("Valami hiba tortent.");
        }
    }

    /**
     * Meghivja az osszes olyan osztaly TimerNotify-at, aminek lepnie kell, mert
     * telt ido
     */
    public void NotifyAllTimePassed() {

        resetWater();

        PipeSystem.getEndpoints().get(1).TimerNotify();
        for (int i = 0; i < PipeSystem.getPumpes().size(); i++) {
            PipeSystem.getPumpes().get(i).TimerNotify();
        }
        PipeSystem.getEndpoints().get(0).TimerNotify();

    }

    public void resetWater() {
        for (int i = 0; i < PipeSystem.getPipes().size(); i++) {
            PipeSystem.getPipes().get(i).SetVizezett(false);
        }

        for (int i = 0; i < PipeSystem.getPumpes().size(); i++) {
            PipeSystem.getPumpes().get(i).SetVizezett(false);
        }
    }

    /**
     * Inicializalja a jatekot, beallitja a jatekosok szamat, a jatek idejet es a
     * szerelok/szabotorok lepeseinek a szamat
     */
    public void Initialization() {
        PipeSystem.resetAll();

        Cisterns c = new Cisterns();
        Mountains m = new Mountains();
        Pipe p1 = new Pipe();
        Pipe p2 = new Pipe();
        Pump pu = new Pump();
        PipeSystem.getPipes().add(p1);
        PipeSystem.getPipes().add(p2);
        PipeSystem.getEndpoints().add(m);
        PipeSystem.getEndpoints().add(c);
        PipeSystem.getPumpes().add(pu);
        m.SetNeighbour(p1);
        p1.SetNeighbour(m);
        p1.SetNeighbour(pu);
        pu.SetNeighbour(p1);
        pu.SetNeighbour(p2);
        p2.SetNeighbour(pu);
        p2.SetNeighbour(c);
        c.SetNeighbour(p2);
        pu.setInput(p1);
        pu.setOutput(p2);
    }

    // Interfész :D
    public void Repair() { // KÉSZ
        if (currentTeam) {
            Mechanic m = (Mechanic) currentPlayer;
            if (m.Repair()) {
                NotifyAllTimePassed();
                stepUsed();
            }
        }
    }

    public void Damage() { // KÉSZ
        if (currentPlayer.getElement().Damage()) {
            NotifyAllTimePassed();
            stepUsed();
        }
    }

    public void PickUpPump() { // KÉSZ
        if (currentTeam) {
            Mechanic m = (Mechanic) currentPlayer;
            if (m.Pickuppump()) {
                NotifyAllTimePassed();
                stepUsed();
            }
        }
    }

    public boolean CutPipe() {
        if (currentTeam) { // Mechanic
            Mechanic m = (Mechanic) currentPlayer;
            boolean siker = m.CutPipe();
            if (siker) {
                NotifyAllTimePassed();
                stepUsed();
            }
            return siker;
        }
        return false;
    }

    public void SetStucky() {
        if (currentPlayer.SetStucky()) {
            NotifyAllTimePassed();
            stepUsed();
        }
    }

    public void SetSlippery() { // KÉSZ
        if (!currentTeam) {
            Saboteur s = (Saboteur) currentPlayer;
            if (s.SetSlippery()) {
                NotifyAllTimePassed();
                stepUsed();
            }
        }
    }

    public void ChangePipe(Pump neighbourPump, Pump toConnect) {
        if (currentPlayer.getElement().ChangePipe(neighbourPump, toConnect)) {
            NotifyAllTimePassed();
            stepUsed();
        }
    }

    public void SetPump(Pipe i, Pipe o) { // KÉSZ
        if (currentTeam) {
            Mechanic m = (Mechanic) currentPlayer;
            if (m.setPump(i, o)) {
                NotifyAllTimePassed();
                stepUsed();
            }
        }

    }

    public void Skip() { // KÉSZ
        NotifyAllTimePassed();
        stepUsed();
    }

    public void Move(Element n) {
        if (currentPlayer.MoveToElement(n)) {
            System.out.println("Move..");
            NotifyAllTimePassed();
            stepUsed();
        }
    }

    void stepUsed() {
        if (!ended) {
            roundJ++;
            if (roundJ >= steps) {
                roundI++;
                roundJ = 0;
            }
            if (roundI >= NumberOfPlayers) {
                if (currentTeam)
                    currentTime++;
                currentTeam = !currentTeam;
                roundI = 0;
                roundJ = 0;
            }

            // Beállítjuk a kövi játékost
            if (currentTeam) // mechanic
                currentPlayer = PipeSystem.getMechanic().get(roundI);
            else // szabotőr
                currentPlayer = PipeSystem.getSaboteur().get(roundI);

            if (currentTime >= time) {
                System.out.println("VÉGE A JÁTÉKNAK");
                ended = true;
            }

            System.out.println("[JELENLEGI ÁLLAPOT: \ncurrentTime: " + currentTime + "\n" +
                    "roundI: " + roundI + "\n" +
                    "roundJ: " + roundJ + "\n" +
                    "currentTeam: " + currentTeam + "\n" +
                    "vege: " + ended);
        }

    }

    public void setupRound() {
        // GUIS alapállapot
        currentTime = 0; // Körökszáma, ha elég nagy lesz, vége a játéknak
        roundI = 0; // Játékos egy csapaton belül
        roundJ = 0; // steppek
        currentTeam = false; // False: Szabotőr, True: Mechanic
        currentPlayer = PipeSystem.getSaboteur().get(0); // Az éppen aktív csapaton belül a já
        ended = false; // Ha true: vége a játéknak
    }

    public boolean getEnded() {
        return ended;
    }

    public int getPipeGenTime() {
        return PipeGenTime;
    }

    public int getPumpGenTime() {
        return PumpGenTime;
    }
}