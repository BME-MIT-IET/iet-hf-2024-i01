import java.util.ArrayList;
import java.util.HashMap;
//Kristof

/**
 * Tarolja a sivatagba kiomlo viz mennyiseget. Ezenkivul a jatekosokat is tarolja
 */
public class PipeSystem {
    /**
     * A szabotorok vizmennyisege
     */
    private static int s_water = 0;
    /**
     * A szerelok vizmennyisege
     */
    private static int m_water = 0;
    /**
     * A szerelok listaja a jatekban
     */
    private static ArrayList<Mechanic> mechanic=new ArrayList<>();
    /**
     * A szabotorok listaja a jatekban
     */
    private static ArrayList<Saboteur> saboteur=new ArrayList<>();
    /**
     * A csovek listja a jatekban
     */
    private static ArrayList<Pipe> pipes=new ArrayList<>();
    /**
     * A pumpak listja a jatekban
     */
    private static ArrayList<Pump> pumpes=new ArrayList<>();
    /**
     * A vegpontok(hegy es ciszterna) listaja
     */
    private static ArrayList<Node> endpoints=new ArrayList<>(2);

    private static HashMap<Element,View> views=new HashMap<Element,View>();
    public static HashMap<Element,View> getViews(){return views;}
    public static void resetAll() {
        views = new HashMap<Element,View>();
        s_water = 0;
        m_water = 0;
        mechanic.clear();
        saboteur.clear();
        pipes.clear();
        pumpes.clear();
        endpoints.clear();
    }
    /**
     * A saboteurok gyujtott vizet noveli
     */
    public static void Addswater(int quantity) {
        int old = s_water;
        s_water+= quantity;
        System.out.println("Kiomlott viz novekedett, szabotorok kapnak pontot. Regi ertek: " + old + " uj ertek: " + s_water);
    }

    /**
     * A Szerelok kapnak pontot
     */

    public static void incMWater(int quantity) {
        int old = m_water;
        m_water+= quantity;
        System.out.println("Szerelok kapnak pontot. Regi ertek: " + old + " uj ertek: " + m_water);
    }

    /**
     * A csovek listajanak gettere
     * @return a csovek
     */
    public static ArrayList<Pipe> getPipes(){
        return pipes;
    }

    /**
     * A pumpak listajanak gettere
     * @return a pumpak
     */
    public static ArrayList<Pump> getPumpes(){
        return pumpes;
    }

    /**
     * A vegpontok listajanak gettere
     * @return a vegpontok listaja
     */
    public static ArrayList<Node> getEndpoints(){
        return endpoints;
    }

    /**
     * A szerelok listajanak gettere
     * @return a szerelok
     */
    public static ArrayList<Mechanic> getMechanic(){
        return mechanic;
    }

    /**
     * Visszaadja a szabotorok tombot.
     * @return saboteur
     */
    public static ArrayList<Saboteur> getSaboteur(){
        return saboteur;
    }

    /**
     * A csovek listajanak settere
     */
    public static void setPipes(ArrayList<Pipe> list){ pipes = list;}

    /**
     * A pumpak listajanak settere
     */
    public static void setPumpes(ArrayList<Pump> list){
        pumpes = list;
    }

    /**
     * A vegpontok listajanak settere
     */
    public static void setEndpoints(ArrayList<Node> list){endpoints = list;}

    /**
     * A szerelok listajanak settere
     */
    public static void setMechanic(ArrayList<Mechanic> list){
        mechanic = list;
    }

    /**
     * Visszaadja a szabotorok tombot.
     */
    public static void setSaboteur(ArrayList<Saboteur> list){
        saboteur = list;
    }

    /**
     * Visszaadja a szabotorok vizet.
     * @return s_water
     */
    public static int getS_water(){
        return s_water;
    }

    /**
     * Visszaadja a szerelok vizet.
     * @return m_water
     */
    public static int getM_water(){
        return m_water;
    }

    /**
     * A szabotorok es szerelok vizenek a kozos settere
     * @param mwater m_water uj erteke
     * @param swater s_water uj erteke
     */
    public static void setWater(int mwater,int swater){
        m_water=mwater;
        s_water=swater;
    }
}

