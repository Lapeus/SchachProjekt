package daten;

import java.util.ArrayList;
import java.util.List;

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
            anzahl = (int) Math.ceil(zugListe.size() / 2);
        } else {
            // Abgerundete Anzahl, weil schwarz nach ziehts
            anzahl = (int) Math.floor(zugListe.size() / 2);
        }
        return anzahl;
    }
  
}
