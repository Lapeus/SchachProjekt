package daten;

import java.util.ArrayList;
import java.util.List;

public class Spieldaten {

    private List<Zug> zugListe = new ArrayList<Zug>();
    
    public Spieldaten() {
        
    }
    
    public List<Zug> getZugListe() {
        return zugListe;
    }
}
