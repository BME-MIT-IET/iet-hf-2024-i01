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

    public int getcantMove(){
        return cantMove;
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
    public boolean Move(int n) { //nem ezt hasznbaljuk
        if (cantMove > 0) {
            System.out.println("A jatekos ideje, ameg nem lephet csokkent eggyel, meg "+cantMove+" korig nem fog tudni elmozdulni.");
            cantMove--;
            return false;
        }
        ArrayList<Element> elemek = element.GetNeighbours();
        if (n < 0 || elemek.size() <= n) {
            System.out.println("Nem letezik ilyen szomszedja.");
            return false;
        } else if (elemek.get(n) != null) {
            Element szomszed = elemek.get(n);
            if (!szomszed.Move(this)) {
                System.out.println("A mozgas nem engedelyezett");
                return false;
            } else {
                element.Remove(this);
                if(szomszed instanceof Pipe){
                    if(((Pipe) szomszed).isSlippery()){
                    }else{
                        element = szomszed;
                        element.SetPlayer(this);
                    }
                } else {
                    element = szomszed;
                    element.SetPlayer(this);
                }
                return true;
            }
        }
        System.out.println("A mozgas nem engedelyezett");
        return false;
    }

    /**
     *A fuggveny meghivasakor a jatekos kivalasztja a megfelelo elemet es annak meghivja
     * Move(p: Player) fuggvenyet
     */
    public boolean MoveToElement(Element n) {
        if (cantMove > 0) {
            System.out.println("A jatekos ideje, ameg nem lephet csokkent eggyel, meg "+cantMove+" korig nem fog tudni elmozdulni.");
            cantMove--;
            return false;
        }

        ArrayList<Element> elemek = element.GetNeighbours();
        boolean volte=false;
        for (int i=0; i<elemek.size(); i++) {
            if(elemek.get(i)==n){
                volte=true;
            }
        }
        if(volte!=true){
            System.out.println("Nem letezik ilyen szomszedja.");
            return false;
        }
        if (n != null) {
            Element szomszed = n;
            if (!szomszed.Move(this)) {
                System.out.println("A mozgas nem engedelyezett");
                return false;
            } else {
                element.Remove(this);
                if(szomszed instanceof Pipe){
                    if(((Pipe) szomszed).isSlippery()){
                    }else{
                        element = szomszed;
                        element.SetPlayer(this);
                    }
                } else {
                    element = szomszed;
                    element.SetPlayer(this);
                }
                return true;
            }
        }
        System.out.println("A mozgas nem engedelyezett");
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
