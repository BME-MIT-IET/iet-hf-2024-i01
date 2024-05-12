/**
 * A palyat alkoto csomopontok ososztalya (bovithetoseg miatt fontos)
 */
public abstract class Node extends Element implements Notify{
    /**
     * Ellenorzi, hogy a jatekos mozoghat-e az elemre
     * @param p - A jatekos aki lepni szeretne.
     * @return Lephet-e a jatekos
     */
    public boolean Move(Player p){
        return true;
    }
}

