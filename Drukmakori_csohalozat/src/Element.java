import java.io.Serializable;
import java.util.ArrayList;
/**
 * A palyat alkoto elemek ososztalya.
 */
public abstract class Element implements IEvents, Serializable {
    /**
     * az elem szomszedai
     */
    protected ArrayList<Element> neighbours = new ArrayList<>();
    /**
     * az elemen allo jatekosok
     */
    protected ArrayList<Player> player = new ArrayList<>();
    /**
     * az elemben jelenleg tarolt vizmennyiseg
     */
    protected int storage = 0;
    /**
     * az elemben maximalis lehetseges viz kapacitasa
     */
    protected int maxstorage = 3;
    /**
     * megmutatja, hogy hanyszor lett meghivva az elemen a Path fuggveny
     */
    protected int counter = 0;

    /**
     * Kapott-e vizet az térkép adott állapotában
     */
    protected boolean vizezett = false;

    /**
     * Vizezés gettere, ami egyben false-ra is állítja.
     */
    public boolean GetVizezett() {
        return vizezett;
    }

    public void SetVizezett(boolean v){
        vizezett = v;
    }

    /**
     * Beallitja az element storage-et.
     * @param s Az uj storage ertek
     */
    public void SetStorage(int s) {
        this.storage = s;
    }

    /**
     * Visszaadja az element storage-et.
     * @return A storage erteke
     */
    public int GetStorage() {
        return this.storage;
    }

    /**
     * Beallitja az element player tombjet.
     * @param p - az elementre lepo player
     */
    public void SetPlayer(Player p) {
        player.add(p);
    }

    /**
     * Hozzaad egy elemet az element neighbours tombjehez.
     * @param e - az uj szomszedja az element peldanynak
     */
    public void SetNeighbour(Element e) {
        neighbours.add(e);
    }

    /**
     * Visszaadja az element neighbours tombjet.
     */
    public ArrayList<Element> GetNeighbours() {
        return neighbours;
    }

    /**
     * Visszaadja az element player tombjet.
     */
    public ArrayList<Player> GetPlayers() {
        return player;
    }

    /**
     * Rateszi a playert az elementre, ha a megfelelo feltetelek teljesulnek.
     * @param p - az elementre lepo player
     * @return - megmutatja hogy sikerult-e ralepni erre az element peldanyra
     */
    abstract public boolean Move(Player p);

    /**
     * Torli a playert az elementrol.
     * @param p - az elementre lepo player
     */
    public void Remove(Player p){
        player.remove(p);
    }

    /**
     * Viz folyasaert felelos fuggveny.
     * @param e - Az elem ahonnan jon a viz.
     */
    abstract public void Path(Element e);

    /**
     * Idozito hatasara meghivott fuggveny.
     */
    abstract public void TimerNotify();

    /**
     * Kiirja az elem tulajdonsagait:
     *      Rajta allo jatekosok
     */
    public void Info(){
        System.out.print("Rajta allo jatekosok: ");
        int n=0;
        for (Player p: player) {
            System.out.printf(p.getId() + " ");
            n++;
        }
        System.out.println("\n"+n+" db jatekos all az elemen.");
    }

    /**
     * Kiirja a megadott elem tulajdonsagait:
     * szomszed - az az elem amelyinel szeretnenk lekerni az info-t
     */
    public void Info(int szomszed){
        if (neighbours.size() > szomszed && szomszed >= 0 && neighbours.get(szomszed) != null){
            neighbours.get(szomszed).Info();
        }else{System.out.println("Rossz a parameter.");}
    }
}