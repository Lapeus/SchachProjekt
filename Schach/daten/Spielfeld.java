package daten;

import java.util.ArrayList;
import java.util.List;

import figuren.Figur;

public class Spielfeld {

    private List<Figur> schwarzeFiguren = new ArrayList<Figur>();
    private List<Figur> weisseFiguren = new ArrayList<Figur>();
    private List<Figur> geschlagenWeiss = new ArrayList<Figur>();
    private List<Figur> geschlagenSchwarz = new ArrayList<Figur>();
    private boolean richtigAusgerichtet = true;
    
    public Spielfeld() {
        
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