package daten;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle spielspezifischen Daten wie die Spielzeit und die
 * durchgef&uuml;hrten Z&uuml;ge.
 */
public class Spieldaten {

    private List<Zug> zugListe = new ArrayList<Zug>();
    
    public Spieldaten() {
        
    }
    
    public List<Zug> getZugListe() {
        return zugListe;
    }
}
