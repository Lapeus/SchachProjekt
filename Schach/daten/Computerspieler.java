package daten;

import java.util.List;

import figuren.Figur;
import gui.Feld;

/**
 * Stellt einen Computergegner dar. <br>
 * Erbt von Spieler und verh&auml;lt sich auch genauso wie dieser. In der GUI
 * wird nach einem Zug des menschlichen Gegners getestet, ob es sich beim 
 * anderen Spieler um einen Computerspieler handelt. Wenn ja, wird eine ziehen-
 * Methode des Computerspielers aufgerufen, welche einen Zug generiert und 
 * anschlie&szlig;end auch selbstst&auml;ndig zieht.
 * @see Spieler
 * @author Christian Ackermann
 */
public class Computerspieler extends Spieler {

    /**
     * Das Spielfeld, auf welchem der Computer ziehen kann. <br>
     * Ist hier als Attribut unabdingbar, da die &uuml;bliche ziehen-Methode
     * selbst in Spielfeld ist und hier daher sonst Zugriffsm&ouml;glichkeiten
     * fehlen w&uuml;rden.
     */
    private Spielfeld spielfeld;
    
    /**
     * Erstellt einen neuen Computerspieler. <br>
     * Einziger Konstruktor dieser Klasse. Ruft nur den Konstruktor der 
     * vererbenden Klasse auf.
     * @param name Der Name des Computerspielers
     */
    public Computerspieler(String name) {
        super(name);
    }
    
    /**
     * Generiert einen Zug und zieht ihn anschlie&szlig;end.
     */
    public void ziehen() {
        /* Ein Zug eines Computerspielers laeuft grundsaetzlich folgendermassen
         * ab:
         * 1. Ermittle fuer alle eigenen Figuren alle moeglichen Felder.
         * 2. Ziehe nacheinander alle diese Felder und fuehre fuer jeden Zug
         *    wieder die ersten beiden Schritte aus, bis die gewuenschte 
         *    Rekursionstiefe (angepeilt sind eigentlich 3) erreicht ist.
         * 3. Bewerte die aktuelle Brettstellung (Materialwert und eventuell
         *    Weiteres) und gib den Wert in der Rekursion zurueck an die oberste
         *    Stufe.
         * 4. Jede Stufe entscheidet nun nach dem MiniMax-Algorithmus, welcher
         *    Zug der jeweils Beste ist und gibt den Wert weiter nach oben
         * 5. Die letzte Stufe weiss nun genau, welcher Zug bei bestmoeglichem
         *    Spiel beider Seiten in n Zuegen die beste Spielsituation hervor-
         *    ruft. Dieser Zug wird anschliessend gezogen.
         */
       
        zufallsZug();
    }

    
    
    /**
     * F&uuml;hrt einen zuf&auml;lligen Zug aus.
     */
    private void zufallsZug() {
        // Eigene Figuren
        List<Figur> eigeneFiguren;
        if (getFarbe()) {
            eigeneFiguren = spielfeld.getWeisseFiguren();
        } else {
            eigeneFiguren = spielfeld.getSchwarzeFiguren();
        }
        List<Feld> alleFelder;
        int zufall, zufall2;
        do {
            // Erzeugt eine Zufallszahl zwischen 0 und eigeneFiguren.size() - 1
            zufall = (int) (Math.random() * eigeneFiguren.size());
            // Alle moeglichen Felder
            alleFelder = eigeneFiguren.get(zufall).getKorrektFelder();
            // Erzeugt eine Zufallszahl zwischen 0 und alleFelder.size() - 1
            zufall2 = (int) (Math.random() * alleFelder.size());
        } while (alleFelder.isEmpty());
        // Ziehe dieses ausgewaehlte Feld
        spielfeld.ziehe(eigeneFiguren.get(zufall), alleFelder.get(zufall2), 1);
    }
    
    /**
     * Setzt das Spielfeld des Computergegners.
     * @param spielfeld Das Spielfeld
     */
    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }

}
