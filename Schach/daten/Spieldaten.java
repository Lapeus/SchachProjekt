package daten;

import java.util.ArrayList;
import java.util.List;

import zuege.Zug;

/**
 * Verwaltet alle spielspezifischen Daten wie die durchgef&uuml;hrten 
 * Z&uuml;ge und die Zugzeit jeden Spielers.
 * @author Christian Ackermann
 */
public class Spieldaten {

    /**
     * Eine chronologisch geordnete Liste mit den durchgef&uuml;hrten 
     * Z&uuml;gen. 
     * Ein neuer Zug wird immer hinten angeh&auml;ngt.
     */
    private List<Zug> zugListe = new ArrayList<Zug>();
    
    /**
     * Die Zugzeit von wei&szlig;, sofern ein gespeichertes Spiel vorliegt.
     */
    private int geladenZeitWeiss = 0;
    
    /**
     * Die Zugzeit von schwarz, sofern ein gespeichertes Spiel vorliegt.
     */
    private int geladenZeitSchwarz = 0;
    
    /**
     * Die Zuganzahl von wei&szlig;, sofern ein gespeichertes Spiel vorliegt.
     */
    private int geladenZuegeWeiss = 0;
    
    /**
     * Die Zuganzahl von schwarz, sofern ein gespeichertes Spiel vorliegt.
     */
    private int geladenZuegeSchwarz = 0;
    
    /**
     * Die komplette Zugliste in Schachnotation, sofern ein gespeichertes Spiel
     * vorliegt.
     */
    private String geladenNotation = "";
    
    /**
     * Der (leere) Konstruktor dieser Klasse. <br>
     * Hierbei muss nichts passieren, da lediglich eine leere Zugliste 
     * ben&ouml;tigt wird und diese automatisch erzeugt wird.
     */
    public Spieldaten() {
        
    }
    
    /**
     * Gibt die Liste mit den durchgef&uuml;hrten Z&uuml;gen zur&uuml;ck.
     * @return Eine Liste mit allen Z&uuml;gen
     */
    public List<Zug> getZugListe() {
        return zugListe;
    }
    
    /**
     * Gibt die gesamte Zugzeit des angegebenen Spielers zur&uuml;ck.
     * @param spieler Die Spielfarbe des Spielers, f&uuml;r den die Zugzeit
     * berechnet werden soll. <b>True</b> f&uuml;r wei&szlig;, <b>false</b>
     * f&uuml;r schwarz
     * @return Die Zugzeit des Spielers in ganzen Sekunden
     */
    public int getZugzeit(boolean spieler) {
        int zugzeit = geladenZeitWeiss;
        // Startindex
        int start = 0;
        // Wenn der Spieler schwarz ist
        if (!spieler) {
            // Beginnt es erst bei 1
            start = 1;
            zugzeit = geladenZeitSchwarz;
        }
        // Fuer jeden zweiten Zug
        for (int i = start; i <= zugListe.size() - 1; i += 2) {
            zugzeit += zugListe.get(i).getZugzeit();
        }
        return zugzeit;
    }
    
    /**
     * Gibt die Anzahl der durchgef&uuml;hrten Z&uuml;ge des angegebenen
     * Spielers zur&uuml;ck.
     * @param spieler Die Spielfarbe des Spielers. <b>True</b> f&uuml;r 
     * wei&szlig;, <b>false</b> f&uuml;r schwarz
     * @return Die Anzahl der Z&uuml;ge des Spielers 
     */
    public int getAnzahlZuege(boolean spieler) {
        int anzahl;
        if (spieler) {
            // Aufgerundete Anzahl, weil weiss anfaengt
            anzahl = zugListe.size() / 2 + 1;
            anzahl += geladenZuegeWeiss;
        } else {
            // Abgerundete Anzahl, weil schwarz nach zieht
            anzahl = zugListe.size() / 2;
            anzahl += geladenZuegeSchwarz;
        }
        return anzahl;
    }
    
    /**
     * Gibt die gesamte Zugliste als Zeichenkette zur&uuml;ck.
     * @return Eine Zeichenkette mit allen Z&uuml;gen
     */
    public String toString() {
        String string = geladenNotation;
        String lineSep = System.getProperty("line.separator");
        for (Zug zug : zugListe) {
            string += zug.toSchachNotation() + lineSep;
        }
        return string;
    }

    /**
     * Setzt die bisherige Zeit von schwarz im Falle eines gespeicherten 
     * Spieles.
     * @param geladenZeitSchwarz Zeit in ganzen Sekunden;
     */
    public void setGeladenZeitSchwarz(int geladenZeitSchwarz) {
        this.geladenZeitSchwarz = geladenZeitSchwarz;
    }
    
    /**
     * Setzt die bisherige Zeit von wei&szlig; im Falle eines gespeicherten 
     * Spieles.
     * @param geladenZeitWeiss Zeit in ganzen Sekunden;
     */
    public void setGeladenZeitWeiss(int geladenZeitWeiss) {
        this.geladenZeitWeiss = geladenZeitWeiss;
    }
    
    /**
     * Setzt die bisherige Zuganzahl von schwarz im Falle eines gespeicherten 
     * Spieles.
     * @param geladenZuegeSchwarz Anzahl der Z&uuml;ge;
     */
    public void setGeladenZuegeSchwarz(int geladenZuegeSchwarz) {
        this.geladenZuegeSchwarz = geladenZuegeSchwarz;
    }

    /**
     * Setzt die bisherige Zuganzahl von wei&szlig; im Falle eines 
     * gespeicherten Spieles.
     * @param geladenZuegeWeiss Anzahl der Z&uuml;ge;
     */
    public void setGeladenZuegeWeiss(int geladenZuegeWeiss) {
        this.geladenZuegeWeiss = geladenZuegeWeiss;
    }
    
    /**
     * Setzt die bisherigen Z&uuml;ge in Schachnotation im Falle eines 
     * gespeicherten Spieles.
     * @param notation Eine mehrzeilige Zeichenkette mit der Schachnotation;
     */
    public void setGeladenNotation(String notation) {
        this.geladenNotation = notation;
    }
    
    
}
