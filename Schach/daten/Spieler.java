package daten;

/**
 * Verwaltet alle wichtigen Informationen eines Spielers.<br>
 * Neben dem Namen und der aktuellen Spielfarbe wird auch eine Statistik
 * f&uuml;r jeden Spieler gespeichert.
 * @author Christian Ackermann
 */
public class Spieler {

    /**
     * Der Name des Spielers.
     */
    private String name;
    
    /**
     * Die aktuelle Spielfarbe des Spielers.
     * <b>True</b> f&uuml;r wei&szlig;, <b>false</b> f&uuml;r schwarz
     */
    private boolean farbe;
    
    /**
     * Die Statistik des Spielers.
     */
    private Statistik statistik;
    
    /**
     * Erstellt einen neuen Spieler.<br>
     * Einziger Konstruktor dieser Klasse. 
     * @param name Der Name des Spielers
     */
    public Spieler(String name) {
        this.name = name;
        this.statistik = new Statistik();
    }
    
    /**
     * Liefert eine Zeichenkette mit allen wichtigen Daten zur&uuml;ck. <br>
     * Wird f&uuml;r das Speichern des Gesamtdatensatzes verwendet. Dabei ist
     * nur die Statistik des Spielers bedeutend, da der Name aus dem Dateinamen
     * abzuleiten ist und die aktuelle Farbe unerheblich ist, f&uuml;r den Fall,
     * dass die Spiele nicht in chronologischer Reihenfolge geladen werden.
     * @return Eine mehrzeilige Zeichenkette
     */
    public String toString() {
        String string = statistik.toString();
        return string;
    }
    
    /**
     * Gibt den Namen des Spielers zur&uuml;ck.
     * @return Name des Spielers
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gibt die aktuelle Spielfarbe des Spielers zur&uuml;ck.
     * @return Die Farbe des Spielers. <b>True</b> f&uuml;r wei&szlig;,
     * <b>false</b> f&uuml;r schwarz
     */
    public boolean getFarbe() {
        return farbe;
    }
    
    /**
     * Setzt die aktuelle Spielfarbe des Spielers.
     * @param farbe Die Farbe des Spielers. <b>True</b> f&uuml;r wei&szlig;,
     * <b>false</b> f&uuml;r schwarz
     */
    public void setFarbe(boolean farbe) {
        this.farbe = farbe;
    }
    
    /**
     * Gibt die Statistik des Spielers zur&uuml;ck.
     * @return Die Statistik
     */
    public Statistik getStatistik() {
        return statistik;
    }
    
    /**
     * Setzt die Statistik des Spielers. <br>
     * Wird von der Laden-Methode des Gesamtdatensatzes aufgerufen.
     * @param statistik Die Statistik
     */
    public void setStatistik(Statistik statistik) {
        this.statistik = statistik;
    }
    
}