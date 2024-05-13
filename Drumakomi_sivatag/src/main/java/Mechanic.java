/**
 * Egy Jatekos tipus, amely a rendszert karban tartja. O a szerelo
 */
//Tomi
public class Mechanic extends Player{
    /**
     * A szerelonel hany lerakhato pumpa van.
     */
    private int pumpes;

    /**
     * Konstruktor
     */
    public Mechanic(int id,int cm,Element e){
        super(id,cm,e);
        pumpes=0;
    }
    /**
     * A pumpes settere
     * @return A szerelonel levo pumpak szama
     */
    public int getPumpes() {
        return pumpes;
    }
    /**
     * A pumpnak amin a szerelo al allitja at az inputjat es outputjat
     * @param i Az elemnek amin allunk az i-edik szomszedja
     * @param o Az elemnek amin allunk az o-adik szomszedja
     * @return Sikeres volt-e a muvelet
     */
    public boolean setPump(Pipe i, Pipe o){
    	return this.getElement().SetPump(i, o);
    }
    /**
     * Megjavitja a csovet vagy pumpat
     * @return Sikeres volt-e a muvelet
     */
    public boolean Repair() {
        return this.getElement().Repair();
    }
    /**
     * Felvesz egy pumpat a ciszternarol
     * @return Sikeres volt-e a muvelet
     */
    public boolean Pickuppump() {
        if (this.getElement().PickUpPump()) {
            pumpes ++;
            return true;
        }else{
            return false;
        }
    }
    /**
     * Kettefureszel egy csovet
     * @return Sikeres volt-e a muvelet
     */
    public boolean CutPipe() {
    	return (this.getElement().CutPipe());
    }
}

