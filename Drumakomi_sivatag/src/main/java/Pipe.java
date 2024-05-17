import java.util.Random;
import static java.lang.Math.min;
/**
 * A csorendszer eleme, amiben folyik a viz a pumpak kozott
 */
public class Pipe extends Element {
    /**
     * Randomizer
     */
    private Random random = new Random();

    /**
     * Torott a cso vagy nem
     */
    private boolean damaged=false;
    /**
     * Hany korig csuszos a cso
     */
    private int slippery = 0;
    /**
     * Hany korig nem rongalhato a cso
     */
    private int notDamageable = 0;
    /**
     * Hany korig ragados a cso
     */
    private int stucky = 0;

    /**
     * Storage getter
     * @return storage erteke
     */
    public Pipe(){
        //PipeSystem.getViews().put(this,new PipeView());
    }
    public int GetStorage(){
        return storage;
    }
    public boolean getDamaged(){return damaged;}
    public boolean isSlippery(){return (slippery>0);}
    public boolean isnotDamageable(){return notDamageable>0;}
    public boolean isstucky(){return stucky>0;}
    /**
     * Storage setter
     * @param uj Az uj storage ertek
     */
    public void SetStorage(int uj){
        storage = uj;
    }
    /**
     * Megallapitja, hogy a jatekos lephet-e az adott elemre, ha a feltetelek megfelelnek
     * @param p - az elementre lepo player
     * @return - lephet-e a jatekos az adott elemre
     */
    @Override
    public boolean Move(Player p){
        if (this.player.size() == 0){
            if (stucky > 0) {
                p.setCantMove(stucky);
                stucky = 0;
                System.out.println("A jatekos sikeresen atmozgott a kivalasztott elemre.");
                return true;
            }
            if(slippery>0){
                int r = random.nextInt(3) - 1;
                p.getElement().Remove(p);
                p.setElement(this.GetNeighbours().get(r));
                this.player.clear();
                this.GetNeighbours().get(r).SetPlayer(p);
                System.out.println("A jatekos elcsuszott(, +1 lepest kapott a jatekos a kellemetlensegekert 😎).");
                /*System.out.println("""
                        ___  ___  _ __ _ __ _   _\s
                        / __|/ _ \\| '__| '__| | | |
                        \\__ \\ (_) | |  | |  | |_| |
                        |___/\\___/|_|  |_|   \\__, |
                                              __/ |
                                             |___/\s""");*/
                this.GetNeighbours().get(r).Info();
                return false;
            }
            System.out.println("A jatekos sikeresen atmozgott a kivalasztott elemre.");
            return true;
        }
        System.out.println("A csore nem lehetett mozogni, mert mar allnak rajta.");
        return false;
    }

    /**
     * Megjavitja a csovet
     * @return Sikeres volt-e a muvelet
     */
    public boolean Repair() {
        if (damaged) {
            damaged = false;
            if(Game.rand){
                notDamageable = random.nextInt(3) - 1;
            }else{notDamageable=3;}
            System.out.println("A cso meg lett javitva.");
            return true;
        }
        System.out.println("A cso nem is volt elromolva, ezert nem lehetett megjavitani.");
        return false;
    }
    /**
     * Megrongalja a csovet
     * @return Sikeres volt-e a muvelet
     */
    public boolean Damage() {
        if (!damaged && notDamageable==0) {
            damaged = true;
            System.out.println("A cso sikeresen meg lett rongolva.");
            return true;
        }
        System.out.println("A csot nem lehet megrongalni.");
        return false;
    }

    /**
     * Ragadossa teszi a csovet
     * @return Sikeres volt-e a muvelet
     */
    public boolean SetStucky() {
        if (stucky == 0) {
            if(Game.rand){
                stucky = random.nextInt( 3) - 1;
            }else{stucky=4;}
            System.out.println("A cso ragadosra lett allitva " + stucky + " korig.");
            return true;
        }
        System.out.println("A muvelet nem vegezgeto el, mert a cso mar ragados.");
        return false;
    }

    /**
     * Csuszossa teszi a csovet
     * @return Sikeres volt-e a muvelet
     */
    public boolean SetSlippery() {
        if (slippery == 0) {
            if(Game.rand){
                slippery = random.nextInt(3) ;
            }else{slippery=4;}
            System.out.println("A cso csuszossa lett allitva " + slippery + " korig.");
            return true;
        }
        System.out.println("A cso mar csuszos, ezert nem lehet duplan csuszossa tenni.");
        return false;
    }

    /**
     * A vizfolyasat szimulalja a csorendszerben. Ha ez a metodus meghivodik, akkor az azt jelenti, hogy idaig eljutott a viz.
     * @param e - az element ahonnan jon a viz
     */
    public void Path(Element e) {
    System.out.println("Cso");
    vizezett = true;

    if (e != null) {
        // Viz tárolása
        storeWater(e);

        // Ha a cső sérült, a víz a rendszerbe kerül
        if (damaged) {
            PipeSystem.Addswater(storage);
            storage = 0;
        } else {
            // További hívás szomszédos elemekre
            forwardWater(e);
        }
    }
}

// Viz tárolása az aktuális csőben
private void storeWater(Element e) {
    if (storage < maxstorage) {
        int kiszed = Math.min(e.GetStorage(), maxstorage - storage);
        e.SetStorage(e.GetStorage() - kiszed);
        storage += kiszed;
    }
}

// További hívás a szomszédos elemekre
private void forwardWater(Element e) {
    if (neighbours.size() >= 2) {
        Element neighbour1 = neighbours.get(0);
        Element neighbour2 = neighbours.get(1);

        if (neighbour1 == e && neighbour2 != null) {
            neighbour2.Path(this);
        } else if (neighbour2 == e && neighbour1 != null) {
            neighbour1.Path(this);
        } else if (neighbour1 == null || neighbour2 == null) {
            System.out.println("A viz a sivatagba folyik.");
        }
    }
}

    /**
     * atkot egy csovet egy masik pumpaba.
     * @param szomszed - input(0), output(1)
     * @param pump - pumpa tomb valahanyadik eleme;
     * @return Visszaadja, hogy sikeres volt-e az atkotes
     */
    public boolean ChangePipe(Pump szomszed, Pump pump){

        int neighbourIndex;
        if (neighbours.get(0) == szomszed)
            neighbourIndex = 0;
        else if (neighbours.get(1) == szomszed) {
            neighbourIndex = 1;
        }
        else {
            System.out.println("Nem szomszed a cső!");
            return false;
        }

        int pumpIndex = -2;
        for (int i = 0; i < PipeSystem.getPumpes().size(); i++) {
            if (PipeSystem.getPumpes().get(i) == pump) {
                pumpIndex = i;
                break;
            }
        }
        if (pumpIndex == -2)
            return false;


        ///

        if (pumpIndex >= PipeSystem.getPumpes().size() || pumpIndex < -1 || neighbourIndex < 0 || neighbourIndex > 1) {
            System.out.println("A parameterekkel problema van (a tartomanyt erdemes ellenorizni)");
            return false;
        }
        if (pumpIndex == -1) {
            if (neighbourIndex == 0){ //bemenet allitasa
                Element kimenet = neighbours.get(1);
                Element bemenet = neighbours.get(0);
                if (bemenet != PipeSystem.getEndpoints().get(0)) { // bemenet != hegy
                    if (bemenet != null) {
                        bemenet.GetNeighbours().remove(this);
                        neighbours.clear();
                        neighbours.add(null);
                        neighbours.add(kimenet);
                    } else {
                        neighbours.clear();
                        neighbours.add(PipeSystem.getPumpes().get(pumpIndex));
                        neighbours.add(kimenet);
                    }
                } else{
                    System.out.println("Hegybol nem lehet kikotni csovet.");
                    return false;
                }
            } else { //kimenet allitasa
                Element bemenet = neighbours.get(0);
                Element kimenet = neighbours.get(1);
                if (kimenet != PipeSystem.getEndpoints().get(PipeSystem.getEndpoints().size()-1)) { // kimenet != ciszterna
                    if (kimenet != null){
                        kimenet.GetNeighbours().remove(this);
                        neighbours.clear();
                        neighbours.add(bemenet);
                        neighbours.add(null);
                    } else {
                        neighbours.clear();
                        neighbours.add(PipeSystem.getPumpes().get(pumpIndex));
                        neighbours.add(null);
                    }
                } else{
                    System.out.println("Ciszternabol nem lehet kikotni csovet.");
                    return false;
                }
            }
        } else {
            if (neighbourIndex == 0){ //bemenet allitasa
                Element kimenet = neighbours.get(1);
                Element bemenet = neighbours.get(0);
                if (bemenet != PipeSystem.getEndpoints().get(0)) { // bemenet != hegy
                    if (bemenet != null){
                        bemenet.GetNeighbours().remove(this);
                    }
                    neighbours.clear();
                    neighbours.add(PipeSystem.getPumpes().get(pumpIndex));
                    neighbours.add(kimenet);
                } else{
                    System.out.println("Hegybol nem lehet kikotni csovet.");
                    return false;
                }
            } else { //kimenet allitasa
                Element bemenet = neighbours.get(0);
                Element kimenet = neighbours.get(1);
                if (kimenet != PipeSystem.getEndpoints().get(PipeSystem.getEndpoints().size()-1)) { // kimenet != ciszterna
                    if (kimenet != null){
                        kimenet.GetNeighbours().remove(this);
                        neighbours.clear();
                        neighbours.add(bemenet);
                        neighbours.add(PipeSystem.getPumpes().get(pumpIndex));
                    } else {
                        neighbours.clear();
                        neighbours.add(PipeSystem.getPumpes().get(pumpIndex));
                        neighbours.add(null);
                    }
                } else{
                    System.out.println("Ciszternabol nem lehet kikotni csovet.");
                    return false;
                }
            }
        }
        System.out.println("Az atkotes sikeresen megtortent.");
        return true;
    }

    /**
     * Csokkenti a kor vegen a bizonyos ertekeket
     */
    public void TimerNotify(){
        if (slippery > 0)
                slippery--;
        if (notDamageable > 0)
            notDamageable--;
        if (stucky > 0)
            stucky--;
    }

    /**
     * Kiirja a csonek az adatait
     */
    public void Info(){
        System.out.println("Cso info:");
        System.out.println("Torott: "+(damaged ? "Igen" : "Nem"));
        System.out.println("Storage: " + storage);
        System.out.println("MaxStorage: "+maxstorage);
        System.out.println("Csuszos a cso: "+(slippery>0 ? "Igen":"Nem"));
        System.out.println("Rongalhato a cso: "+(notDamageable>0 ? "Igen":"Nem"));
        System.out.println("Ragados a cso: "+(stucky>0 ? "Igen":"Nem"));
        super.Info();
    }

    /**
     * Kettefureszeli a csovet
     * @return Visszaadja, hogy sikeres volt-e a kettefureszeles
     */
    public boolean CutPipe(){
        Mechanic m= (Mechanic) player.get(0);
        if(m.getPumpes()>0) {
            Element regiPumpa2 = this.GetNeighbours().get(1);
            Pump newPump = new Pump();
            Pipe newPipe = new Pipe();

            regiPumpa2.neighbours.remove(this); //regi cso remove hegybol
            this.neighbours.remove(regiPumpa2); // eredeti cso kimenetenek a beallitasa
            this.neighbours.add(newPump);
            newPump.SetNeighbour(this);
            newPump.setInput(this); // uj pumpa bemenete a regi cso
            newPump.SetNeighbour(newPipe);
            newPump.setOutput(newPipe); // uj pumpa kimenete az uj cso
            newPipe.SetNeighbour(newPump); // uj cso bemenete az uj pumpa
            newPipe.SetNeighbour(regiPumpa2); // uj cso kimenete a regi pumpa
            regiPumpa2.SetNeighbour(newPipe); //regi pumpa2 bemenete az uj cso

            PipeSystem.getPumpes().add(newPump);
            PipeSystem.getPipes().add(newPipe);
            System.out.println("A csot sikeresen kettofureszelted");
            return true;
        }
        System.out.println("Nincs nalad pumpa ahhoz, hogy a csovet ketto tudd fureszelni");
        return false;
    }
}