/**
 * Ahonnan folyik a jatek soran a viz (kezdoallomas)
 */
//Csaba
public class Mountains extends Node {

    /**
     * Konstruktor
     */
    public Mountains() {
        //PipeSystem.getViews().put(this, new MountainsView());
    }

    /**
     * Segitsegevel a Mountains class, illetve a fuggvenyei az ido fuggvenyeben kepesek
     * cselekedni
     */
    @Override
    public void TimerNotify(){
        counter=1;
        this.Path(null);
    }

    /**
     * Meghivja a kovetkezo elem Path-jet (a viz folyasat hivatott jelezni).
     * @param e - A cso ahonnan jon a viz
     */
    public void Path(Element e){
        if(counter==1) {
            System.out.println("Mountains");
            counter++;
            for (Element neighbour : this.neighbours) {
                if(neighbour!=null){
                    neighbour.Path(this);
                }
            }
        }
    }
    /**
    *
    * Mountainsrol kiirja a fontosabb adatokat
    */
    public void Info(){
        System.out.println("Mountains info:");
        System.out.println("Hozza kapcsolodo csovek szama: " + this.neighbours.size());
        super.Info();
    }

    /**
     * Visszaadja a benne talalhato vizet.
     * @return Integer.MAX_VALUE
     */
    public int GetStorage() {
        return Integer.MAX_VALUE;
    }

    /**
     * Felul irja az elment SetStorage-et.
     * Azert nem csinal semmit, mert vegtelen sok viz van benne minden idopontban.
     * @param n Az uj storage ertek
     */
    public void SetStorage(int n) {
    }
}

