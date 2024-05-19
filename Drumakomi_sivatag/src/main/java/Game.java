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
    public void Settings(int pugt, int pigt) {
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
        } /*
           * else{System.out.println("Dontetlen lett a jatek");System.out.println("""
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
           * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠁⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠢⠀⠸⠛⠓⠢⠄⠀⠉⠉⠁⠀⠀⠀⠀⠀⠀""");}
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
        for (int i = 0; i < NumberOfPlayers; i++) {
            System.out.println("_________________________________________________________________________");
            int j = 0;
            Saboteur s = PipeSystem.getSaboteur().get(i);
            System.out.println("Az " + s.getId() + " id-ju Szabotor kore van eppen!");
            while (j < steps) {
                String[] event = sc.nextLine().toLowerCase().split(" ");
                try {
                    switch (event[0]) {
                        case "damage":
                            if (s.getElement().Damage()) {
                                j++;
                                NotifyAll();
                            }
                            break;
                        case "move":
                            if (event.length == 2) {
                                if (s.Move(Integer.parseInt(event[1]))) {
                                    j++;
                                    NotifyAll();
                                }
                            } else {
                                System.out.println(
                                        "Keves parametert adtal meg! A masodik parameterben meg kell adni a szomszed indexet.");
                            }
                            break;
                        case "setstucky":
                            if (s.SetStucky()) {
                                j++;
                                NotifyAll();
                            }
                            break;
                        case "setslippery":
                            if (s.SetSlippery()) {
                                j++;
                                NotifyAll();
                            }
                            break;
                        case "setpipe":
                            if (event.length == 3) {
                                /*
                                 * if (s.getElement().ChangePipe(Integer.parseInt(event[1]),
                                 * Integer.parseInt(event[2]))) {
                                 * j++;
                                 * NotifyAll();
                                 * }
                                 */
                            } else {
                                System.out.println("Tul keves parameter lett megadva.");
                            }
                            break;
                        case "skip":
                            j++;
                            NotifyAll();
                            break;
                        case "help":
                            System.out.println("A kovetkezo parancsokat tudod kiadni:");
                            System.out.println("Damage - Eltorod a pumpat, amin allsz, ha az pumpa.");
                            System.out.println(
                                    "Move - A parameterben megadott indexu szomszedra lepsz, ha az lehetseges.");
                            System.out.println("SetStucky - Ragadossa teszed a csovet, amin allsz, ha csovon allsz.");
                            System.out.println("SetSlippery - Csuszossa teszed a csovet, amin allsz, ha csovon allsz.");
                            System.out.println(
                                    "SetPipe - Atkotod a csovet,az elso parameterben kell megadni, hogy az inputjat(0) vagy outputjat(1) akarok atkotni a masik parameterben pedig, hogy a rendszerben levo pumpak kozul hanyadikba (pl. 0, ha a legeloszor felvett pumpaba).");
                            System.out.println("Skip - Skippeled az egyik lepesi lehetosegedet.");
                            System.out.println("Info - Kiirja az elem adatait amin allsz.");
                            System.out.println("Info - Kiirja a parameterben megadott elem adatait.");
                            System.out.println(
                                    "Settings - Az elso parameterben meg kell adni, hogy a random dolgok random tortenjenek-e off, ha nem, illetve on ha igen a masodik parameterben a pumpa generalasanak az idejet kell megadni a ciszternanal, a harmadikban pedig a cso generalasanak idejet a ciszternanal ");
                            break;
                        case "info":
                            if (event.length == 1) {
                                s.Info();
                            }
                            if (event.length == 2) {
                                s.Info(Integer.parseInt(event[1]));
                            }
                            break;
                        case "settings":
                            if (event.length == 4) {
                                Settings(Integer.parseInt(event[2]), Integer.parseInt(event[3]));
                            } else {
                                System.out.println("Tul keves parameter lett megadva.");
                            }
                            break;
                        case "exit":
                            System.exit(0);
                        default:
                            System.out.println(
                                    "Hibas a megadott parancs! A Help parancsal tudod a parancsok listajat kilistazni.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Rossz parametert adtal meg");
                }
            }
        }
        for (int i = 0; i < NumberOfPlayers; i++) {
            System.out.println("_________________________________________________________________________");
            int j = 0;
            Mechanic m = PipeSystem.getMechanic().get(i);
            System.out.println("Az " + m.getId() + " id-ju Mechanic kore van eppen!");
            while (j < steps) {
                String[] event = sc.nextLine().toLowerCase().split(" ");
                try {
                    switch (event[0]) {
                        case "damage":
                            if (m.getElement().Damage()) {
                                j++;
                                NotifyAll();
                            }
                            break;
                        case "move":
                            if (event.length == 2) {
                                if (m.Move(Integer.parseInt(event[1]))) {
                                    j++;
                                    NotifyAll();
                                }
                            } else {
                                System.out.println(
                                        "Keves parametert adtal meg! A masodik parameterben meg kell adni a szomszed indexet.");
                            }
                            break;
                        case "repair":
                            if (m.Repair()) {
                                j++;
                                NotifyAll();
                            }
                            break;
                        case "pickuppump":
                            if (m.Pickuppump()) {
                                j++;
                                NotifyAll();
                            }
                            break;
                        case "cutpipe":
                            if (m.CutPipe()) {
                                j++;
                                NotifyAll();
                            }
                            break;
                        case "setstucky":
                            if (m.SetStucky()) {
                                j++;
                                NotifyAll();
                            }
                            break;
                        case "setpump":
                            /*
                             * if (m.setPump(Integer.parseInt(event[1]), Integer.parseInt(event[2]))) {
                             * j++;
                             * NotifyAll();
                             * }
                             */
                            break;
                        case "setpipe":
                            // if (event.length == 3) {
                            // if (m.getElement().ChangePipe(Integer.parseInt(event[1]),
                            // Integer.parseInt(event[2]))) {
                            // j++;
                            // NotifyAll();
                            // }
                            // } else {
                            // System.out.println("Tul keves parameter lett megadva.");
                            // }
                            break;
                        case "exit":
                            System.exit(0);
                        case "skip":
                            j++;
                            NotifyAll();
                            break;
                        case "help":
                            System.out.println("A kovetkezo parancsokat tudod kiadni:");
                            System.out.println("Damage - Eltorod a pumpat, amin allsz, ha az pumpa.");
                            System.out.println(
                                    "Move - A parameterben megadott indexu szomszedra lepsz, ha az lehetseges.");
                            System.out.println("SetStucky - Ragadossa teszed a csovet, amin allsz, ha csovon allsz.");
                            System.out.println("RepairPump - Megjavitod az elemet amin allsz.");
                            System.out.println(
                                    "PickupPump - A ciszernarol felveszel egy pumpat, ha van rajta es te a ciszternan allsz.");
                            System.out.println(
                                    "CutPipe - Kettefureszeled a csovet, amin allsz, ha csovon allsz es ha van nalad legalabb egy pumpa.");
                            System.out.println(
                                    "SetPump - Az elso parameterben a pumpa inputjat kell megadni(hanyas indexu szomszed legyen az input), a masodik parameterben az outputot(hanyas indexu szomszed legyen az output)");
                            System.out.println(
                                    "SetPipe - Atkotod a csovet,az elso parameterben kell megadni, hogy az inputjat(0) vagy outputjat(1) akarok atkotni a masik parameterben pedig, hogy a rendszerben levo pumpak kozul hanyadikba (pl. 0, ha a legeloszor felvett pumpaba).");
                            System.out.println("Skip - Skippeled az egyik lepesi lehetosegedet.");
                            System.out.println("Info - Kiirja az elem adatait amin allsz.");
                            System.out.println("Info - Kiirja a parameterben megadott elem adatait.");
                            System.out.println(
                                    "Settings - Az elso parameterben meg kell adni, hogy a random dolgok random tortenjenek-e off, ha nem, illetve on ha igen a masodik parameterben a pumpa generalasanak az idejet kell megadni a ciszternanal, a harmadikban pedig a cso generalasanak idejet a ciszternanal ");
                            System.out.println(
                                    "SaveAndExit - Elmenti a jatekot, ha az utolso szabotor utolso lepesekent a mentest valasztja");
                            break;
                        case "info":
                            if (event.length == 1) {
                                m.Info();
                            }
                            if (event.length == 2) {
                                m.Info(Integer.parseInt(event[1]));
                            }
                            break;
                        case "settings":
                            if (event.length == 4) {
                                Settings(Integer.parseInt(event[2]), Integer.parseInt(event[3]));
                            } else {
                                System.out.println("Tul keves parameter lett megadva.");
                            }
                            break;
                        case "saveandexit":
                            if (event.length == 2) {
                                if (j + 1 == steps && i + 1 == NumberOfPlayers) {
                                    try {
                                        SaveAndExit(event[1]);
                                        System.exit(0);
                                    } catch (Exception e) {
                                        System.out.println("Valami hiba tortent");
                                    }
                                } else {
                                    System.out.println("Ez a muvelet most nem lehetseges");
                                }
                            } else {
                                System.out.println("Tul keves parameter lett megadva.");
                            }
                            break;
                        default:
                            System.out.println(
                                    "Hibas a megadott parancs! A Help parancsal tudod a parancsok listajat kilistazni.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Rossz parametert adtal meg");
                }
            }
        }
        time--;
        roundtime++;
        System.out.println("A " + roundtime + " korben vagyunk eppen");
        for (int i = 0; i < PipeSystem.getPipes().size(); i++) {
            PipeSystem.getPipes().get(i).TimerNotify();
        }
    }

    /**
     * Lementi a jatekot es kilep
     * 
     * @param file A file amibe ment
     */
    public void SaveAndExit(String file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
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
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
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
    public void NotifyAll() {

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
                NotifyAll();
                stepUsed();
            }
        }
    }

    public void Damage() { // KÉSZ
        if (currentPlayer.getElement().Damage()) {
            NotifyAll();
            stepUsed();
        }
    }

    public void PickUpPump() { // KÉSZ
        if (currentTeam) {
            Mechanic m = (Mechanic) currentPlayer;
            if (m.Pickuppump()) {
                NotifyAll();
                stepUsed();
            }
        }
    }

    public boolean CutPipe() {
        if (currentTeam) { // Mechanic
            Mechanic m = (Mechanic) currentPlayer;
            boolean siker = m.CutPipe();
            if (siker) {
                NotifyAll();
                stepUsed();
            }
            return siker;
        }
        return false;
    }

    public void SetStucky() {
        if (currentPlayer.SetStucky()) {
            NotifyAll();
            stepUsed();
        }
    }

    public void SetSlippery() { // KÉSZ
        if (!currentTeam) {
            Saboteur s = (Saboteur) currentPlayer;
            if (s.SetSlippery()) {
                NotifyAll();
                stepUsed();
            }
        }
    }

    public void ChangePipe(Pump neighbourPump, Pump toConnect) {
        if (currentPlayer.getElement().ChangePipe(neighbourPump, toConnect)) {
            NotifyAll();
            stepUsed();
        }
    }

    public void SetPump(Pipe i, Pipe o) { // KÉSZ
        if (currentTeam) {
            Mechanic m = (Mechanic) currentPlayer;
            if (m.setPump(i, o)) {
                NotifyAll();
                stepUsed();
            }
        }

    }

    public void Skip() { // KÉSZ
        NotifyAll();
        stepUsed();
    }

    public void Move(Element n) {
        if (currentPlayer.MoveToElement(n)) {
            System.out.println("Move..");
            NotifyAll();
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