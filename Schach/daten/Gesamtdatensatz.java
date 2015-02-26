package daten;

import java.util.ArrayList;
import java.util.List;

public class Gesamtdatensatz {

    private List<Spieler> spielerListe = new ArrayList<Spieler>();
    private List<Spiel> gespeicherteSpiele = new ArrayList<Spiel>();
    
    public Gesamtdatensatz() {
        
    }
    
    public Gesamtdatensatz(List<Spieler> spielerListe
        , List<Spiel> gespeicherteSpiele) {
        this.spielerListe = spielerListe;
        this.gespeicherteSpiele = gespeicherteSpiele;
    }
    
    public void speichern() {
        
    }
    
    public void laden() {
        
    }
    
    public List<Spieler> getSpielerListe() {
        return spielerListe;
    }
    public List<Spiel> getSpieleListe() {
        return gespeicherteSpiele;
    }
}
