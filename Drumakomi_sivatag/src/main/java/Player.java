import java.io.Serializable;
import java.util.ArrayList;

/**
 * A jatekos tipusoknak (jelenleg mechanic es saboteur) az ososztalya. Itt talalhatoak azok az
 * attributumok es fuggvenyek, amelyek minden jatekosnak vannak. Ez egy absztrakt osztaly.
 */
//Tomi
public abstract class Player implements Serializable {
    /**
     * A jatekos egyedi id-ja
     */
    private final int id;


    /**
     * Error üzenet
     */
    private final String notAbleToMove = "A mozgas nem engedelyezett";

    /**
     * A jatekos ennyi korig nem kepes mozdulni
     */
    private int cantMove;

    /**
     * Palyaelem, amin a jatekos eppen all
     */
    private Element element;

    /**
     * Konstruktor
     */
    public Player(int id, int cantMove, Element element) {
        this.id = id;
        this.cantMove = cantMove;
        this.element = element;
    }

    /**
     * Visszaadja a jatekos id-jat.
     * @return az id
     */
    public int getId() {
        return id;
    }

    /**
     * Visszaadja a player element-jet.
     * @return az element
     */
    public Element getElement() {
        return element;
    }

    /**
     * Bellitja, hogy hany korig ne tudjon lepni a jatekos.
     * @param cantMove cantMove uj erteke
     */
    public void setCantMove(int cantMove) {
        this.cantMove = cantMove;
    }

    /**
     * Beallitja a player element attributumat
     * @param element az uj element
     */
    public void setElement(Element element) {
        this.element = element;
    }

    /**
     *A fuggveny meghivasakor a jatekos kivalasztja a megfelelo elemet es annak meghivja
     * Move(p: Player) fuggvenyet
    */
   public boolean Move(int n) {
    if (isMovementRestricted()) {
        return false;
    }

    ArrayList<Element> neighbours = element.GetNeighbours();
    if (!isValidNeighbourIndex(n, neighbours)) {
        return false;
    }

    Element target = neighbours.get(n);
    if (target == null || !target.Move(this)) {
        System.out.println(notAbleToMove);
        return false;
    }

    updatePlayerPosition(target);
    return true;
}

// Ellenőrzi, hogy a játékos mozgása korlátozott-e
private boolean isMovementRestricted() {
    if (cantMove > 0) {
        System.out.println("A jatekos ideje, ameg nem lephet csokkent eggyel, meg " + cantMove + " korig nem fog tudni elmozdulni.");
        cantMove--;
        return true;
    }
    return false;
}

// Ellenőrzi, hogy a megadott szomszéd index érvényes-e
private boolean isValidNeighbourIndex(int n, ArrayList<Element> neighbours) {
    if (n < 0 || neighbours.size() <= n) {
        System.out.println("Nem letezik ilyen szomszedja.");
        return false;
    }
    return true;
}

// Frissíti a játékos pozícióját az új elemre
private void updatePlayerPosition(Element target) {
    element.Remove(this);
    if (target instanceof Pipe && ((Pipe) target).isSlippery()) {
        // Ha a célcső csúszós, nem állítjuk be a játékos új helyét
        return;
    }
    element = target;
    element.SetPlayer(this);
}


    /**
     *A fuggveny meghivasakor a jatekos kivalasztja a megfelelo elemet es annak meghivja
     * Move(p: Player) fuggvenyet
     */
    public boolean MoveToElement(Element n) {
    if (isMovementRestricted()) {
        return false;
    }

    if (!isNeighbour(n)) {
        System.out.println("Nem letezik ilyen szomszedja.");
        return false;
    }

    if (!n.Move(this)) {
        System.out.println(notAbleToMove);
        return false;
    }

    updatePlayerPosition(n);
    return true;
}


// Ellenőrzi, hogy a megadott elem szomszédja-e az aktuális elemnek
private boolean isNeighbour(Element n) {
    ArrayList<Element> neighbours = element.GetNeighbours();
    for (Element neighbour : neighbours) {
        if (neighbour == n) {
            return true;
        }
    }
    return false;
}

    /**
     * Visszater a player elementjen meghivott SetStucky() ertekevel (true/false)
     * @return Sikeres volt-e a muvelet
     */
    public boolean SetStucky(){
    	return element.SetStucky();
    }

    /**
     * Kiirja a parameterben megadott szomszedu elemrol(szomszedok listajanak a p-edikrol) az
     * informaciokat(mindent amit lehet rola tudni pl.stucky-e, allnak-e mar rajta stb.).
     * Pontosabban ez a fuggveny fogja meghivni az adott elemnek, amit kivalasztott az Info()
     * fuggvenyet, ami fogja kiirni az adatokat az elemrol.
     *
     */
    public void Info(int p){
    	element.Info(p);
    }

    /**
     * Kiirja a jelenlegi elem informacioit(mindent amit lehet rola tudni pl.stucky-e, allnak-e mar rajta
     * stb.). Pontosabban ez a fuggveny fogja meghivni az adott elemnek, amit kivalasztott az Info()
     * fuggvenyet, ami fogja kiirni az adatokat az elemrol
     */
    public void Info(){
    	element.Info();
    }
}
