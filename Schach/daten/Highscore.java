package daten;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Stellt ein Ranking der Spieler zur Verf&uuml;gung.
 * @author Christian Ackermann
 */
public class Highscore {

    /**
     * Die sortierte Liste der Spieler.
     */
    private List<Spieler> ranking = new ArrayList<Spieler>();
    
    /**
     * Erzeugt eine neue Highscore-&Uuml;bersicht.<br>
     * Einziger Konstruktor dieser Klasse.
     */
    public Highscore() {
        
    }
    
    /**
     * Gibt die Liste der Spieler nach absteigendem Score sortiert wieder.
     * @return Eine Liste mit den gerankten Spielern
     */
    public List<Spieler> getRanking() {
        return sortiereListe(ranking);
    }
    
    /**
     * Eine statische Klasse, die zwei Spieler aufgrund ihres Scores vergleicht.
     * @author Christian Ackermann
     */
    public static class SpielerComparator implements Comparator<Spieler> {
        @Override
        public int compare(Spieler sp1, Spieler sp2) {
            // Wenn der Score des ersten Spielers kleiner ist
            if (sp1.getStatistik().getScore() 
                < sp2.getStatistik().getScore()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    
    /**
     * Sortiert die angegebene Liste von Spielern nach Score.
     * @param spieler Die zu sortierende Liste von Spielern
     * @return Die sortierte Liste
     */
    private List<Spieler> sortiereListe(List<Spieler> spieler) {
        Collections.sort(spieler, new SpielerComparator());
        return spieler;
    }
}
