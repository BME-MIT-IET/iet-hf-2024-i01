import java.util.Random;
/**
 * A csorendszer eleme, amely vizet pumpal a csovekbe.
 */
//Csaba
public class Pump extends Node {
    /**
     * Randomizer
     */
    private Random random = new Random();

    /**
     * Bemeneti cso
     */
    private Pipe input;
    /**
     * Kimeneti cso
     */
    private Pipe output;
    /**
     * Jelzi, hogy el van -e torve a cso.
     * True, ha el van.
     * False, ha nem.
     */
    private boolean broken = false;

    public boolean getBroken(){return broken;}
    /**
     * Beallitja a pumpa bemenetet.
     * @param input - uj input cso
     */
    public void setInput(Pipe input) {
        this.input = input;
    }

    /**
     * Beallitja a pumpa kimenetet.
     * @param output - uj output cso
     */
    public void setOutput(Pipe output) {
        this.output = output;
    }

    public Pump(){
        //PipeSystem.getViews().put(this,new PumpView());
    }
    /**
     * Random idokozonkent elromlik a pumpa
     */
    @Override
    public void TimerNotify(){
        counter=0;
        if(Game.rand){
            if (random.nextInt(11)>8){
                broken = true;
            }
        }else{broken=false;}

        if (Game.PumpGenTime == -1 && Game.PipeGenTime == -1) {
            broken = true;
        }
    }

    /**
     * A kimeneti csobe pumpalja a vizet, amit a bemeneten kapott, ha mukodik a pumpa
     * @param e - amelyik csobol jon a viz
     */
    public void Path(Element e){
        if (broken) {
            return;
        }
        vizezett = true;
        System.out.println("Pumpa");
        counter ++;
        if (counter >=10){
            return;
        }
        if (input == e){
            if (e.GetStorage() > maxstorage - storage){
                e.SetStorage(e.GetStorage() - (maxstorage - storage));
                storage = maxstorage;
            }else{
                int bemenetiCsoStorage = e.GetStorage();
                e.SetStorage(0);
                storage += bemenetiCsoStorage;
            }
        }else{
            System.out.println("Adott cso nem a pumpa bemeneti csove");
            return;
        }
        if(output!=null) {
            output.Path(this);
        }
    }

    /**
     * Megjavul a pumpa, ha el volt rontva
     */
    public boolean Repair(){
        if (broken){
            System.out.println("A pumpa meg lett javitva");
            broken = false;
        }else{
            System.out.println("A pumpa nem is volt elromolva");
        }
        return true;
    }
    /**
    * Pumpa objektumrol fontosabb informacio kiirasa
    * */
    public void Info(){
        System.out.println("Pumpa info:");
        System.out.println("Pumpa broken: " + broken);
        System.out.println("Pumpaban levo viz: " + storage);
        System.out.println("Pumpa maximalis kapacitasa: " + maxstorage);
        super.Info();
    }

    /**
     * Beallitja a pumpa be es kimenetet.
     * @param i - input cso
     * @param o - output cso
     * @return - Visszaadja, hogy sikeres volt -e
     */
    public boolean SetPump(Pipe i, Pipe o) {

        boolean talaltI =false;
        boolean talaltO=false;
        for (Element neighbour : neighbours) {
            if (neighbour == i) {
                talaltI = true;
            }
            if (neighbour == o) {
                talaltO = true;
            }
        }

        if (!talaltI || !talaltO ){
            System.out.println("Sikertelen volt a pumpa atallitasa.");
            return false;
        }

        setInput(i);
        setOutput(o);
        System.out.println("Sikeres pumpa atallitas.");
        return true;
    }
}

