package daten;

import java.util.ArrayList;
import java.util.List;

import zuege.EnPassantZug;
import zuege.RochadenZug;
import zuege.Umwandlungszug;
import zuege.Zug;

/**
 * Verwaltet alle spielspezifischen Daten, wie die durchgef&uuml;hrten 
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
     * @return <b>true</b> wenn das Spiel laut der Regel beendet werden kann
     */
    public boolean fuenfzigZuegeRegel() {
        // Grundsaetzliche Annahme, die 50 Zuege Regel wuerde zutreffen
        boolean remis = true;
        int zaehl = 0; 
        // Wenn noch keine 100 Halbzuege gezogen wurden
        if (zugListe.size() < 100) {
            remis = false;
        } else {
            /* Solange noch nicht alle letzten 100 Halbzuege getestet wurden 
             * und noch keine Anforderung nicht erfuellt wurde
             */
            while (remis && zaehl < 100) {
                // Der zaehlte Zug von hinten
                Zug zug = zugListe.get(zugListe.size() - zaehl - 1);
                // Wenn es ein EnPassantZug oder ein Umwandlungszug war
                if (zug instanceof EnPassantZug 
                    || zug instanceof Umwandlungszug) {
                    // Wurde ein Bauer bewegt
                    remis = false;
                // Wenn es kein RochadenZug war (also ein normaler)
                } else if (!(zug instanceof RochadenZug)) {
                    // Wenn die Figur ein Bauer war oder eine Figur geschlagen 
                    // wurde
                    if (zug.getFigur().getWert() == 100 || zug.isSchlagzug()) {
                        remis = false;
                    }
                }
                zaehl++;
            }
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
        int zugzeit = 0;
        // Startindex
        int start = 0;
        // Wenn der Spieler schwarz ist
        if (!spieler) {
            // Beginnt es erst bei 1
            start = 1;
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
            anzahl = (int) Math.ceil((double) zugListe.size() / 2);
        } else {
            // Abgerundete Anzahl, weil schwarz nach zieht
            anzahl = zugListe.size() / 2;
        }
        return anzahl;
    }
    
    /**
     * Gibt die gesamte Zugliste als Zeichenkette zur&uuml;ck. Dabei steht in
     * jeder Zeile ein Zug.
     * @return Eine Zeichenkette mit allen Z&uuml;gen
     * @see zuege.Zug#toSchachNotation()
     */
    public String toString() {
        String string = "";
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
    
    /**
     * Setzt die Liste mit den durchgef&uuml;hrten Z&uuml;gen. Wird nur beim
     * Laden von <b>Gesamtdatensatz</b> verwendet.
     * @param zugListe Die Liste der bisherigen Z&uuml;ge
     */
    public void setZugListe(List<Zug> zugListe) {
        this.zugListe = zugListe;
    }
    
    /**
     * Gibt den letzten Zug zur&uuml;ck.
     * @return Der letzte Zug
     */
    public Zug getLetzterZug() {
        if (zugListe.size() > 0) {
            return zugListe.get(zugListe.size() - 1);
        } else {
            return null;
        }
    }
    
}
