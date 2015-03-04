package daten;

import figuren.Figur;
import gui.Feld;

public class RochadenZug extends Zug {
    
    private Figur koenig;
    private Feld startfeldK;
    private Figur turm;
    private Feld startfeldT;

    public RochadenZug(Figur koenig, Feld startfeldK, Figur turm, 
        Feld startfeldT, int zugzeit) {
        this.koenig = koenig;
        this.startfeldK = startfeldK;
        this.turm = turm;
        this.startfeldT = startfeldT;
        setZugzeit(zugzeit);
    }
    
    public Figur getKoenig() {
        return koenig;
    }
    
    public Feld getStartfeldK() {
        return startfeldK;
    }
    
    public Figur getTurm() {
        return turm;
    }
    
    public Feld getStartfeldT() {
        return startfeldT;
    }
    
    
}
