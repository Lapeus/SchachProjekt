package daten;

import java.util.ArrayList;
import java.util.List;

import zuege.EnPassantZug;
import zuege.RochadenZug;
import zuege.Umwandlungszug;
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
     * Pr&uuml;ft, ob laut der 50-Z&uuml;ge-Regel die Partie Remis ausgeht.<br>
     * Voraussetzungen: In den letzten 50 Z&uuml;gen durfte kein Bauer gezogen
     * und keine Figur geschlagen worden sein.
     * @return <b>true</b> wenn das Spiel nach der Regel beendet werden kann
     */
    public boolean fuenfzigZuegeRegel() {
        // Grundsaetzliche Annahme, die 50 Zuege Regel wuerde zutreffen
        boolean remis = true;
        int zaehl = 0; 
        /* Solange noch nicht alle letzten 100 Halbzuege getestet wurden und 
         * noch keine Anforderung nicht erfuellt wurde
         */
        while (remis && zaehl < 100) {
            // Der zaehlte Zug von hinten
            Zug zug = zugListe.get(zugListe.size() - zaehl - 1);
            // Wenn es ein EnPassantZug oder ein Umwandlungszug war
            if (zug instanceof EnPassantZug || zug instanceof Umwandlungszug) {
                // Wurde ein Bauer bewegt
                remis = false;
            // Wenn es kein RochadenZug war (also ein normaler)
            } else if (!(zug instanceof RochadenZug)) {
                // Wenn die Figur ein Bauer war oder eine Figur geschlagen wurde
                if (zug.getFigur().getWert() == 100 || zug.isSchlagzug()) {
                    remis = false;
                }
            }
            zaehl++;
        }
        return remis;
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
            if (zugListe.get(i) != null) {
                zugzeit += zugListe.get(i).getZugzeit();
            }
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
     * Gibt die Liste mit den durchgef&uuml;hrten Z&uuml;gen zur&uuml;ck.
     * @return Eine Liste mit allen Z&uuml;gen
     */
    public List<Zug> getZugListe() {
        return zugListe;
    }
    
    public void setZugListe(List<Zug> zugListe) {
        this.zugListe = zugListe;
    }
    
    /**
     * Gibt den letzten Zug zur&uuml;ck.
     * @return Der letzte Zug
     */
    public Zug getLetzterZug() {
        return zugListe.get(zugListe.size() - 1);
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
