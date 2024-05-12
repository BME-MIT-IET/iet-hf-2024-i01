/**
 * Saboteur szerepben levo jatekosok
 */
//Tomi
public class Saboteur extends Player {
    /**
     * id = azonosito
     * cm = a jatekos ennyi ideig nem tud mozdulni
     * e = melyik elemen all
     * @param id A szabotor idja
     * @param cm A cantmove erteke
     * @param e Az elem amin all
     */
    public Saboteur(int id,int cm,Element e){
        super(id,cm,e);
    }

    /**
     * Csuszosra allitja a csovet amin a szabotor van.
     * @return Sikeres volt-e a muvelet
     */
    public boolean SetSlippery(){
    	return this.getElement().SetSlippery();
    }
}

