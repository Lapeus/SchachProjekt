package daten;

import java.util.ArrayList;
import java.util.List;

import figuren.Figur;
import gui.Feld;
import zuege.Zug;
import zuege.Umwandlungszug;

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
       
        stufe1();
        
        // Wenn ein Bauer umgewandelt wird
        int letzterZugIndex = spielfeld.getSpieldaten().getZugListe()
            .size() - 1;
        Zug letzterZug = spielfeld.getSpieldaten().getZugListe()
            .get(letzterZugIndex);
        if (letzterZug instanceof Umwandlungszug) {
            spielfeld.umwandeln(letzterZug.getFigur(), 900);
        }
    }
    
    private int zugGenerator(int stufe) {
        List<Figur> alleFiguren;
        if (spielfeld.getAktuellerSpieler()) {
            alleFiguren = spielfeld.clone(spielfeld.getWeisseFiguren());
        } else {
            alleFiguren = spielfeld.clone(spielfeld.getSchwarzeFiguren());
        }
        List<Integer> bewertung = new ArrayList<Integer>();
        for (Figur figur : alleFiguren) {
            for (Feld feld : figur.getKorrektFelder()) {
                spielfeld.ziehe(figur, feld, 0);
                if (stufe < 3) {
                    zugGenerator(stufe + 1);
                } else {
                    bewertung.add(bewertungsfunktion());
                }
            }
        }
        return getMaxWert(bewertung);
    }
    
    private int getMaxWert(List<Integer> liste) {
        int max = liste.get(0);
        for (int aktuell : liste) {
            if (aktuell > max) {
                max = aktuell;
            }
        }
        return max;
    }
    
    private int getMinWert(List<Integer> liste) {
        int min = liste.get(0);
        for (int aktuell : liste) {
            if (aktuell < min) {
                min = aktuell;
            }
        }
        return min;
    }
    
    private int bewertungsfunktion() {
        return spielfeld.getMaterialwert(true) 
            - spielfeld.getMaterialwert(false);
    }
    /**
     * F&uuml;hrt einen Computerzug nach relativ simplen Zug-Regeln durch.
     */
    private void stufe1() {
        // Liste mit den zu schlagenden Feldern
        List<Feld> schlagend = spielfeld.getSchlagendeFelder();
        int maxWertSchlag = 0;
        Feld maxFeldSchlag = null;
        // Fuer jedes der zu schlagenden Felder
        for (Feld feld : schlagend) {
            // Wenn die Figur darauf einen hoeheren Wert hat
            if (feld.getFigur().getWert() > maxWertSchlag) {
                // Wird das der neue Zug
                maxWertSchlag = feld.getFigur().getWert();
                maxFeldSchlag = feld;
            }
        }
        // Liste mit den zu schlagenden Feldern
        List<Feld> bedroht = spielfeld.getBedrohteFelder();
        int maxWertVerlust = 0;
        Feld maxFeldVerlust = null;
        // Fuer jedes der zu schlagenden Felder
        for (Feld feld : bedroht) {
            // Wenn die Figur darauf einen hoeheren Wert hat und wegziehen kann
            if (feld.getFigur().getWert() > maxWertVerlust 
                && !feld.getFigur().getKorrektFelder().isEmpty()) {
                // Wird das der neue Zug
                maxWertVerlust = feld.getFigur().getWert();
                maxFeldVerlust = feld;
            }
        }
        
        /* Jetzt wird geschaut ob man den Materialwert des Feldes aus seiner
         * Sicht erhoehen kann.
         */
        // Wenn man niemanden schlagen kann, aber auch nichts verliert
        if (maxWertSchlag == 0 && maxWertVerlust == 0) {
            // Soll er per Zufall ziehen
            stufe0();
        // Wenn man niemanden schlagen kann, oder der Verlust hoeher waere
        } else if (maxWertSchlag == 0 || maxWertSchlag < maxWertVerlust) {
            Figur verlust = maxFeldVerlust.getFigur();
            // Alle Felder auf die die bedrohte Figur ziehen koennte
            List<Feld> alternativen = verlust.getKorrektFelder();
            // Zieht voruebergehend zufaellig weg
            // Erzeugt eine Zufallszahl zwischen 0 und alleFelder.size() - 1
            int zufall = (int) (Math.random() * alternativen.size());
            spielfeld.ziehe(verlust, alternativen.get(zufall), 0);
            
        // Wenn man niemanden verlieren wuerde, oder der Verlust geringer waere
        } else if (maxWertVerlust == 0 || maxWertSchlag >= maxWertVerlust) {
            // Suche eine Figur die das Feld schlagen kann
            List<Figur> eigeneFiguren;
            if (getFarbe()) {
                eigeneFiguren = spielfeld.clone(spielfeld.getWeisseFiguren());
            } else {
                eigeneFiguren = spielfeld.clone(spielfeld.getSchwarzeFiguren());
            }
            // Groesser als 900
            int minWert = 1000;
            Figur ziehendeFigur = null;
            // Fuer jede eigene Figur
            for (Figur figur : eigeneFiguren) {
                // Schaue ob sie das Feld erreichen kann und wenn ja, ob sie 
                // einen geringeren Wert hat als AlternativFiguren
                if (figur.getKorrektFelder().contains(maxFeldSchlag)
                    && figur.getWert() < minWert) {
                    ziehendeFigur = figur;
                    minWert = figur.getWert();
                }
            }
            spielfeld.ziehe(ziehendeFigur, maxFeldSchlag, 0);
        }
        
    }
    
    /**
     * F&uuml;hrt einen zuf&auml;lligen Zug aus.
     */
    private void stufe0() {
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
