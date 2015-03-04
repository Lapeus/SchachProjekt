package daten;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltet alle wichtigen Daten f&uuml;r ein Spiel. <br>
 * Ein Spiel besteht aus einem Namen, den beteiligten Spielern, dem 
 * zugehörigen Spielfeld, sowie s&auml;mtlichen ben&ouml;tigten Daten und 
 * Einstellungen.
 * @author Christian Ackermann
 */
public class Spiel {

    /**
     * Der Name des Spiels. <br>
     * Wird beim Laden eines Spiels ben&ouml;tigt.
     */
    private String spielname;
    
    /**
     * Der erste Spieler.
     */
    private Spieler spieler1;
    
    /**
     * Der zweite Spieler.
     */
    private Spieler spieler2;
    
    /**
     * Das zugeh&ouml;rige Spielfeld. <br>
     * Stellt die Figurenanordnung auf dem Brett
     * zur Verf&uuml;gung.
     */
    private Spielfeld spielfeld;
    
    /**
     * Alle spielbezogenen Einstellungen, die beim Laden &uuml;bernommen werden
     * m&uuml;ssten.
     */
    //private Einstellungen einstellungen;
    
    /**
     * Legt ein neues Spiel an, welches gespielt und sp&auml;ter gespeichert
     * werden kann. <br>
     * Einziger Konstruktor dieser Klasse.
     * @param spielname : Name des Spiels. Ben&ouml;tigt f&uuml;r die eindeutige
     * Zuordnung beim Laden
     * @param spieler1 : Der eine teilnehmende Spieler
     * @param spieler2 : Der andere Spieler
     * @param spielfeld : Das zugeh&ouml;rige Spielfeld
     */
    public Spiel(String spielname, Spieler spieler1, Spieler spieler2,
        Spielfeld spielfeld/*, Einstellungen einstellungen*/) {
        this.spielname = spielname;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
        this.spielfeld = spielfeld;
        //this.einstellungen = einstellungen;
    }
    
    /**
     * Wertet das Spiel aus und ermittelt das Ergebnis und den Sieger.<br>
     * Wird aus der GUI aufgerufen und &uuml;bergibt dieser eine Liste mit den
     * ermittelten Daten.
     * @return Eine Liste vom Typ Object mit den wichtigen Informationen zum 
     * Spielausgang
     */
    public List<Object> auswertung() {
        List<Object> ergebnis = new ArrayList<Object>();
        // Der Gewinner
        // Der aktuelle Spieler hat verloren
        if (spieler1.getFarbe() != spielfeld.getAktuellerSpieler()) {
            ergebnis.add(spieler1);
        } else {
            ergebnis.add(spieler2);
        }
        
        // Matt oder Patt
        if (spielfeld.isSchach()) {
            // True fuer Matt
            ergebnis.add(true);
        } else {
            // False fuer Patt
            ergebnis.add(false);
        }
        
        // Anzahl Zuege
        // AnzahlZuege von dem nicht aktiven Spieler, da dieser gewonnen hat
        ergebnis.add(spielfeld.getSpieldaten()
            .getAnzahlZuege(!spielfeld.getAktuellerSpieler()));
        
        return ergebnis;
    }
    
    /**
     * Gibt den Namen des Spiels zur&uuml;ck.
     * @return Der Name als String
     */
    public String getSpielname() {
        return spielname;
    }

    /**
     * Gibt den ersten Spieler zur&uuml;ck.
     * @return Der erste Spieler
     */
    public Spieler getSpieler1() {
        return spieler1;
    }
    
    /**
     * Gibt den zweiten Spieler zur&uuml;ck.
     * @return Der zweite Spieler
     */
    public Spieler getSpieler2() {
        return spieler2;
    }
    
    /**
     * Gibt das Spielfeld zur&uuml;ck.
     * @return Das zugeh&ouml;rige Spielfeld
     */
    public Spielfeld getSpielfeld() {
        return spielfeld;
    }
    
}