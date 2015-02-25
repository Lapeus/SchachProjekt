package daten;

import java.util.ArrayList;
import java.util.List;

import figuren.Bauer;
import figuren.Figur;
import gui.Feld;

public class Spielfeld {

    private List<Figur> schwarzeFiguren = new ArrayList<Figur>();
    private List<Figur> weisseFiguren = new ArrayList<Figur>();
    private List<Figur> geschlagenWeiss = new ArrayList<Figur>();
    private List<Figur> geschlagenSchwarz = new ArrayList<Figur>();
    private boolean richtigAusgerichtet = true;
    
    public Spielfeld() {
        // 8 schwarze Bauern erstellen und auf die richtige Position stellen
        for (int i = 1; i <= 8; i++) {
            schwarzeFiguren.add(new Bauer(new Feld(i,7), false));
        }
        // 8 weisse Bauern erstellen und auf die richtige Position stellen
        for (int i = 1; i <= 8; i++) {
           weisseFiguren.add(new Bauer(new Feld(i,2), false));
        }
        
    }
    
    public List<Figur> getSchwarzeFiguren() {
        return schwarzeFiguren;
    }
    
    public List<Figur> getWeisseFiguren() {
        return weisseFiguren;
    }
    
    public List<Figur> getGeschlagenSchwarz() {
        return geschlagenSchwarz;
    }
    
    public List<Figur> getGeschlagenWeiss() {
        return geschlagenWeiss;
    }
    
    
    
    
}