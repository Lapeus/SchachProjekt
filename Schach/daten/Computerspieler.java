package daten;

import java.util.ArrayList;
import java.util.List;

import figuren.Figur;
import gui.Feld;
import zuege.EnPassantZug;
import zuege.RochadenZug;
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
       
        if (getName().equals("Karl Heinz")) {
            nachRegeln();
        } else if (getName().equals("Rosalinde")) {
            rekursKI(2);
        } else if (getName().equals("Ursula")) {
            rekursKI(3);
        } else if (getName().equals("Walter")) {
            rekursKI(4);
        }
        
        
        // Wenn ein Bauer umgewandelt wird
        Zug letzterZug = spielfeld.getSpieldaten().getLetzterZug();
        if (letzterZug instanceof Umwandlungszug) {
            spielfeld.umwandeln(letzterZug.getFigur(), 900);
        }
        
        /* Fordert den Garbage-Collector auf, unbenutzte Objekte zu entfernen
         * um fuer den noetigen freien Speicherplatz zu sorgen
         */
        Runtime.getRuntime().gc();
    }
    
    /**
     * Ein rekursiver Computergegner, der theoretisch beliebig viele Stufen 
     * untersuchen kann. <br> Ermittelt den besten Zug und zieht ihn.
     * Praktisch nur sinnvoll f&uuml;r die Stufen 2 - 4. <br>
     * Teile der verwendeten Methoden min und max basieren auf dem folgenden
     * Prinzip {@link de.wikipedia.org/wiki/Alpha-Beta-Suche} 
     * @param maxStufe Die maximale Suchtiefe (sinnvollerweise zwischen 2 und
     * 4)
     */
    private void rekursKI(int maxStufe) {
        List<Figur> alleFiguren;
        // Wenn momentan weiss dran ist
        if (getFarbe()) {
            alleFiguren = spielfeld.clone(spielfeld.getWeisseFiguren());
        } else {
            alleFiguren = spielfeld.clone(spielfeld.getSchwarzeFiguren());
        }
        // Maximale Bewertung; initialisiert mit niedrigstem Wert
        int maxbewertung = 4000;
        if (getFarbe()) {
            maxbewertung = -4000;
        }
        // Der Zug der durchgefuehrt werden soll
        List<Figur> besteFiguren = new ArrayList<Figur>();
        List<Feld> besteFelder = new ArrayList<Feld>();
        // Ziehe alle moeglichen Figuren auf alle moeglichen Felder
        for (Figur figur : alleFiguren) {
            for (Feld feld : figur.getKorrektFelder()) {
                // Mache den Zug
                spielfeld.ziehe(figur, feld, 0);
                // Wenn ein Bauer umgewandelt wird
                Zug letzterZug = spielfeld.getSpieldaten().getLetzterZug();
                if (letzterZug instanceof Umwandlungszug) {
                    spielfeld.umwandeln(letzterZug.getFigur(), 900);
                }
                
                // In Abhaengigkeit der Farbe
                if (getFarbe()) {
                    // Bekomme die Bewertung dafuer
                    int bewertung = min(maxStufe - 1, -4000, 4000);
                    // Wenn ein neuer MaxWert entsteht (weiss)
                    if (bewertung > maxbewertung) {
                        // Loesche bisherige Figuren und Felder
                        besteFiguren.clear();
                        besteFelder.clear();
                        besteFiguren.add(figur);
                        besteFelder.add(feld);
                        maxbewertung = bewertung;
                    // Wenn es eine Alternative mit gleicher Bewertung gibt
                    } else if (bewertung == maxbewertung) {
                        // Wird diese hinzugefuegt
                        besteFiguren.add(figur);
                        besteFelder.add(feld);
                    }
                } else {
                    // Bekomme die Bewertung dafuer
                    int bewertung = max(maxStufe - 1, -4000, 4000);
                    // Wenn ein neuer MinWert entsteht (schwarz)
                    if (bewertung < maxbewertung) {
                        // Loesche bisherige Figuren und Felder
                        besteFiguren.clear();
                        besteFelder.clear();
                        besteFiguren.add(figur);
                        besteFelder.add(feld);
                        maxbewertung = bewertung;
                    // Wenn es eine Alternative mit gleicher Bewertung gibt
                    } else if (bewertung == maxbewertung) {
                        // Wird diese hinzugefuegt
                        besteFiguren.add(figur);
                        besteFelder.add(feld);
                    }
                }
                // Mache den Zug rueckgaengig
                spielfeld.zugRueckgaengig();
            }
        }
        
        // Ermittle eine der Alternativen
        // Erzeugt eine Zufallszahl zwischen 0 und besteFiguren.size() - 1
        int zufallsIndex = (int) (Math.random() * besteFiguren.size());
        
        /* Ist nicht ganz schoen, hilft aber an einigen Stellen und macht den
         * Algorithmus instabil, sodass der Computer nicht mit den gleichen
         * Schritten in jeder Partie Matt gesetzt werden kann.
         */
        // Wenn es noch einen Zug zu ziehen gibt
        if (!besteFiguren.isEmpty()) {
            // Ziehe den besten Zug
            spielfeld.ziehe(besteFiguren.get(zufallsIndex), 
                besteFelder.get(zufallsIndex), 0);
        }
    }
    
    /**
     * Berechnet den Maximalwert f&uuml;r die aktuelle Stufe.
     * @param stufe Die aktuelle Stufe
     * @param alpha Der Max-Wert
     * @param beta Der Min-Wert
     * @return Maximale Bewertung
     */
    private int max(int stufe, int alpha, int beta) {
        if (stufe == 0) {
            return bewertungsfunktion();
        }
        int maxWert = alpha;
        List<Figur> alleFiguren = new ArrayList<Figur>();
        // Wenn momentan weiss dran ist
        if (spielfeld.getAktuellerSpieler()) {
            alleFiguren = spielfeld.clone(spielfeld.getWeisseFiguren());
        } else {
            alleFiguren = spielfeld.clone(spielfeld.getSchwarzeFiguren());
        }
        // Ziehe alle moeglichen Figuren auf alle moeglichen Felder
        for (Figur figur : alleFiguren) {
            for (Feld feld : figur.getKorrektFelder()) {
                spielfeld.ziehe(figur, feld, 0);
                // Wenn ein Bauer umgewandelt wird
                Zug letzterZug = spielfeld.getSpieldaten().getLetzterZug();
                if (letzterZug instanceof Umwandlungszug) {
                    spielfeld.umwandeln(letzterZug.getFigur(), 900);
                }
                int wert = min(stufe - 1, maxWert, beta);
                spielfeld.zugRueckgaengig();
                if (wert > maxWert) {
                    maxWert = wert;
                    if (maxWert >= beta) {
                        return maxWert;
                    }
                }
            }
        }
        return maxWert;
    }
    
    /**
     * Berechnet den Minimalwert f&uuml;r die aktuelle Stufe.
     * @param stufe Die aktuelle Stufe
     * @param alpha Der Max-Wert
     * @param beta Der Min-Wert
     * @return Minimale Bewertung
     */
    private int min(int stufe, int alpha, int beta) {
        if (stufe == 0) {
            return bewertungsfunktion();
        }
        int minWert = beta;
        List<Figur> alleFiguren = new ArrayList<Figur>();
        // Wenn momentan weiss dran ist
        if (spielfeld.getAktuellerSpieler()) {
            alleFiguren = spielfeld.clone(spielfeld.getWeisseFiguren());
        } else {
            alleFiguren = spielfeld.clone(spielfeld.getSchwarzeFiguren());
        }
        // Ziehe alle moeglichen Figuren auf alle moeglichen Felder
        for (Figur figur : alleFiguren) {
            for (Feld feld : figur.getKorrektFelder()) {
                spielfeld.ziehe(figur, feld, 0);
                // Wenn ein Bauer umgewandelt wird
                Zug letzterZug = spielfeld.getSpieldaten().getLetzterZug();
                if (letzterZug instanceof Umwandlungszug) {
                    spielfeld.umwandeln(letzterZug.getFigur(), 900);
                }
                int wert = max(stufe - 1, alpha, minWert);
                spielfeld.zugRueckgaengig();
                if (wert < minWert) {
                    minWert = wert;
                    if (minWert <= alpha) {
                        return minWert;
                    }
                }
            }
        }
        return minWert;
    } 
    
    /**
     * Bewertet das Spielfeld nach verschiedenen Kriterien.
     * @return Eine Bewertung in Form einer ganzen Zahl im Bereich von etwa
     * -4000 bis +4000; <b> positiv </b> ist gut f&uuml;r wei&szlig;,
     * <b> negativ </b> ist gut f&uuml;r schwarz.
     */
    private int bewertungsfunktion() {
        int bewertung;
        // Materialwert
        bewertung = spielfeld.getMaterialwert(true) 
            - spielfeld.getMaterialwert(false);
        // Bauern kurz vor der Umwandlung
        int index = 1;
        while (spielfeld.getSchwarzeFiguren().get(index).getWert() == 100) {
            Figur bauer = spielfeld.getSchwarzeFiguren().get(index);
            int y = bauer.getPosition().getYK();
            if (y <= 3) {
                bewertung -= 20;
                if (y <= 2) {
                    bewertung -= 40;
                    if (y == 1) {
                        bewertung -= 60;
                    }
                }
            }
            index++;
        }
        index = 1;
        while (spielfeld.getWeisseFiguren().get(index).getWert() == 100) {
            Figur bauer = spielfeld.getWeisseFiguren().get(index);
            int y = bauer.getPosition().getYK();
            if (y <= 3) {
                bewertung += 20;
                if (y <= 2) {
                    bewertung += 40;
                    if (y == 1) {
                        bewertung += 60;
                    }
                }
            }
            index++;
        }
        // Das Feld des gegnerischen Koenigs
        Feld koenig;
        // Der Einfluss auf die Spielbewertung
        int bonus;
        if (getFarbe()) {
            koenig = spielfeld.getSchwarzeFiguren().get(0).getPosition();
            bonus = 1;
        } else {
            koenig = spielfeld.getWeisseFiguren().get(0).getPosition();
            bonus = -1;
        }
        Zug letzterZug = spielfeld.getSpieldaten().getLetzterZug();
        Figur figur;
        if (letzterZug instanceof RochadenZug) {
            figur = ((RochadenZug) letzterZug).getTurm();
        } else if (letzterZug instanceof EnPassantZug) {
            figur = ((EnPassantZug) letzterZug).getAusfuehrer();
        } else {
            figur = letzterZug.getFigur();
        }
        // Wenn die zuletzt gezogene Figur dem Koenig Schach bietet
        if (figur.bietetSchach(koenig)) {
            bewertung += bonus;
        }
        return bewertung;
    }
    
    /**
     * F&uuml;hrt einen Computerzug nach relativ simplen Zug-Regeln durch.
     */
    private void nachRegeln() {
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
            zufall();
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
    private void zufall() {
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
     * Entscheidet, ob ein Unentschieden-Angebot vom Gegner angenommen werden 
     * soll.
     * @return Wahrheitswert
     */
    public boolean unentschiedenAnnehmen() {
        boolean annehmen = false;
        // Materialwert aus Sicht von weiss
        int matWert = spielfeld.getMaterialwert(true) 
            - spielfeld.getMaterialwert(false);
        // Wenn der Computergegner mehr als 500 Punkte weniger hat
        if ((getFarbe() && matWert < -400) || (!getFarbe() && matWert > 400)) {
            annehmen = true;
        }
        
        // Test auf 8 Zuege Regel (Variante der offiziellen 50 Zuege Regel
        // Grundsaetzliche Annahme, die 8 Zuege Regel wuerde zutreffen
        boolean remis = true;
        int zaehl = 0; 
        // Wenn noch keine 16 Halbzuege gezogen wurden
        if (spielfeld.getSpieldaten().getZugListe().size() < 16) {
            remis = false;
        } else {
            /* Solange noch nicht alle letzten 20 Halbzuege getestet wurden und 
             * noch keine Anforderung nicht erfuellt wurde
             */
            while (remis && zaehl < 16) {
                // Der zaehlte Zug von hinten
                Zug zug = spielfeld.getSpieldaten().getZugListe().get(
                    spielfeld.getSpieldaten().getZugListe().size() - zaehl - 1);
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
        // Wenn die Regel zutrifft
        if (remis) {
            annehmen = true;
        }
        return annehmen;
    }
    
    /**
     * Setzt das Spielfeld des Computergegners.
     * @param spielfeld Das Spielfeld
     */
    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }

}
