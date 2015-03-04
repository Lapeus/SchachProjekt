package daten;

import figuren.Figur;
import gui.Feld;

public class EnPassantZug extends Zug {

    private Figur ausfuehrenderBauer;
    private Feld startfeld;
    private Figur geschlagenderBauer;
    
    public EnPassantZug(Figur ausfuehrenderBauer, Feld startfeld
        , Figur geschlagenerBauer, int zugzeit) {
        this.ausfuehrenderBauer = ausfuehrenderBauer;
        this.startfeld = startfeld;
        this.geschlagenderBauer = geschlagenerBauer;
        setZugzeit(zugzeit);
    }
    
    public Figur getAusfuehrer() {
        return ausfuehrenderBauer;
    }
    
    public Feld getStartfeld() {
        return startfeld;
    }
    
    public Figur getGeschlagenen() {
        return geschlagenderBauer;
    }
  
}
