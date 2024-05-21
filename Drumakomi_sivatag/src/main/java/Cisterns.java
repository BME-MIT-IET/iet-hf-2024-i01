import java.util.Random;

//Gergo
/**
 * Ide folyik a jatek soran a viz
 */
public class Cisterns extends Node {

    /**
     * Randomizer
     */
    private Random random = new Random();

    public void setAvailablePumps(int availablePumps) {
        this.availablePumps = availablePumps;
    }

    /**
     * Az elerheto pumpak szama
     */
    private int availablePumps = 0;
    /**
     * A cso generalas idejenek szamlaloja
     */
    private int pipegen = 0;
    /**
     * A pumpa generalas idejenek szamlaloja
     */
    private int pumpgen = 0;

    /**
     * Felvesz a jatekos egy pumpat a ciszternakrol
     */
    public boolean PickUpPump() {
        if (this.availablePumps > 0) {
            // System.out.println("1 pumpa felvetele sikeresen megtortent");
            this.availablePumps--;
            return true;
        } else {
            System.out.println("A pumpa felvetele sikertelen, mert nincs eleg pumpa a ciszternan");
            return false;
        }
    }

    /**
     * Elérhető
     */
    public int getAvailablePumps() {
        return availablePumps;
    }

    /**
     * (Noveli a pumpak szamat.) Egy uj felveheto pumpa item generalasa, amit a
     * szerelok fel tudnak venni.
     */
    public void CreatePump() {
        this.availablePumps++;
        // System.out.println("Generalodott egy pumpa a ciszternaknal");
    }

    /**
     * Egy uj cso generalasa, aminek az egyik vege a ciszternakon van, masik vege
     * szabad
     */
    public void CreatePipe() {
        System.out.println("Generalodott egy cso a ciszternaknal");
        Pipe p = new Pipe();
        p.SetNeighbour(this);
        neighbours.add(p);
    }

    /**
     * Viz jutott a ciszternakba, szerelok kapnak pontot :)
     */
    public void AddMWater(int water) {
        PipeSystem.incMWater(water);
    }

    /**
     * A Cisterns TimerNotify fuggvenye. Ekkor dol el, hogy generalodik-e uj cso
     * vagy felveheto pumpa item.
     */
    @Override
    public void TimerNotify() {
        if (Game.rand) {
            int number = random.nextInt(100);
            if (number > 1 && number < 20) {
                CreatePipe();
            } else if (number > 30 && number < 75) {
                CreatePump();
            }
        } else {
            if (pipegen == Game.PipeGenTime) {
                CreatePipe();
                pipegen = 0;
            }
            if (pumpgen == Game.PumpGenTime) {
                CreatePump();
                pumpgen = 0;
            }
            pipegen++;
            pumpgen++;
        }
    }

    /**
     * Kiirja a ciszterna tulajdonsagait:
     * Rajta allo jatekosok
     * Szomszedok
     * Felszedheto pumpak szama
     */
    public void Info() {
        System.out.println("Ciszterna info:");
        System.out.println("Felszedheto pumpak szama: " + availablePumps);
        super.Info();
    }

    /**
     * Viz folyasaert felelos fuggveny.
     * 
     * @param e - A cso ahonnan jon a viz. (Amelyik meghivta a fuggvenyt)
     */
    public void Path(Element e) {
        System.out.println("Ciszterna");
        AddMWater(e.GetStorage());
        e.SetStorage(0);
    }
}