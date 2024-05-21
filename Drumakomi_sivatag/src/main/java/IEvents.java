public interface IEvents {
    /**
     * Egyseges interface miatt kell
     * @return false
     */
    default boolean Repair(){
        //System.out.println("Ez a cselekves nem engedelyezett az adott elemen!");
        return false;
    }
    /**
     * Egyseges interface miatt kell
     * @return false
     */
    default boolean PickUpPump(){
        //System.out.println("Ez a cselekves nem engedelyezett az adott elemen!");
        return false;
    }
    /**
     * Egyseges interface miatt kell
     * @return false
     */
    default boolean CutPipe(){
        //System.out.println("Ez a cselekves nem engedelyezett az adott elemen!");
        return false;
    }
    /**
     * Egyseges interface miatt kell
     * @return false
     */
    default boolean Damage(){
        //System.out.println("Ez a cselekves nem engedelyezett az adott elemen!");
        return false;
    }
    /**
     * Egyseges interface miatt kell
     * @return false
     */
    default boolean SetStucky(){
        //System.out.println("Ez a cselekves nem engedelyezett az adott elemen!");
        return false;
    }
    /**
     * Egyseges interface miatt kell
     * @return false
     */
    default boolean SetPump(Pipe i, Pipe o){
        //System.out.println("Ez a cselekves nem engedelyezett az adott elemen!");
        return false;
    }
    /**
     * Egyseges interface miatt kell
     * @return false
     */
    default boolean ChangePipe(Pump neighbourPump, Pump toConnect){
        //System.out.println("Ez a cselekves nem engedelyezett az adott elemen!");
        return false;
    }
    /**
     * Egyseges interface miatt kell
     * @return false
     */
    default boolean SetSlippery(){
        //System.out.println("Ez a cselekves nem engedelyezett az adott elemen!");
        return false;
    }
}
