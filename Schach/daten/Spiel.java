package daten;

/**
 * Verwaltet alle wichtigen Daten f&uuml;r ein Spiel. <br>
 * Ein Spiel besteht aus einem Namen, den beteiligten Spielern, dem 
 * zugehörigen Spielfeld, sowie s&auml;mtliche ben&ouml;tigte Daten und 
 * Einstellungen.
 */
public class Spiel {

    /**
     * Der Name des Spiels. Wird beim Laden eines Spiels ben&ouml;tigt.
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
     * Das zugeh&uml;rige Spielfeld. Stellt die Figurenanordnung auf dem Brett
     * zur Verf&uuml;gung.
     */
    private Spielfeld spielfeld;
    
    //private Einstellungen einstellungen;
    //private Spieldaten spieldaten;
    
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
        Spielfeld spielfeld/*, Einstellungen einstellungen,
        Spieldaten spieldaten*/) {
        this.spielname = spielname;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
        this.spielfeld = spielfeld;
        //this.einstellungen = einstellungen;
        //this.spieldaten = spieldaten;
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