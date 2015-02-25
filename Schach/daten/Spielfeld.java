package daten;

import java.util.ArrayList;
import java.util.List;

import figuren.Bauer;
import figuren.Figur;
import figuren.Springer;
import gui.Feld;

public class Spielfeld {

    private List<Feld> felder = new ArrayList<Feld>();
    private List<Figur> schwarzeFiguren = new ArrayList<Figur>();
    private List<Figur> weisseFiguren = new ArrayList<Figur>();
    private List<Figur> geschlagenWeiss = new ArrayList<Figur>();
    private List<Figur> geschlagenSchwarz = new ArrayList<Figur>();
    private boolean richtigAusgerichtet = true;
    
    public Spielfeld(List<Feld> felder) {
        this.felder = felder;
        // ACHTUNG!!! BEIM ERSTELLEN DER FIGUREN DUERFEN KEINE NEUEN FELDER
        // ERSTELLT WERDEN. DIESE MUESSEN BEREITS IN GUI ANGELEGT SEIN
        
        // Acht schwarze Bauern erstellen und auf die richtige Position stellen
        for (int i = 1; i <= 8; i++) {
            schwarzeFiguren.add(new Bauer(new Feld(i,7), false));
        }
        // Acht weisse Bauern erstellen und auf die richtige Position stellen
        for (int i = 1; i <= 8; i++) {
           weisseFiguren.add(new Bauer(new Feld(i,2), true));
        }
        // Zwei schwarze Springer erstellen
        schwarzeFiguren.add(new Springer(new Feld(2,8), false));
        schwarzeFiguren.add(new Springer(new Feld(7,8), false));
        // Zwei weisse Springer erstellen
        weisseFiguren.add(new Springer(new Feld(2,1), true));
        weisseFiguren.add(new Springer(new Feld(7,1), true));
        // Zwei schwarze Laeufer erstellen
        
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