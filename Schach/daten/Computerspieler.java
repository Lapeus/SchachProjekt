package daten;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;

import figuren.Figur;
import gui.Feld;
import zuege.RochadenZug;
import zuege.Zug;
import zuege.Umwandlungszug;

/**
 * Stellt einen Computergegner dar. <br>
 * Erbt von Spieler und verh&auml;lt sich auch genauso wie dieser. In der GUI
 * wird nach einem Zug des menschlichen Gegners getestet, ob es sich beim 
 * anderen Spieler um einen Computerspieler handelt. Wenn ja, wird die ziehen-
 * Methode des Computerspielers aufgerufen, welche einen Zug generiert und 
 * anschlie&szlig;end auch selbstst&auml;ndig zieht.
 * @see Spieler
 * @see #ziehen()
 * @author Christian Ackermann
 */
public class Computerspieler extends Spieler {

    /**
     * Das Spielfeld, auf welchem der Computer ziehen kann. <br>
     * Ist hier als Attribut unabdingbar, da die &uuml;bliche ziehen-Methode
     * selbst in Spielfeld ist und hier daher sonst Zugriffsm&ouml;glichkeiten
     * fehlen w&uuml;rden.
     * @see Spielfeld#ziehe(Figur, Feld, int)
     */
    private Spielfeld spielfeld;
    
    /**
     * Die ProgressBar zum Anzeigen der Rechenzeit.
     */
    private JProgressBar progBar;
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
     * Generiert einen Zug und zieht ihn anschlie&szlig;end. <br>
     * Je nach Computergegner sieht die Ermittlung des Zuges anders aus.
     * @see #nachRegeln()
     * @see #rekursKI(int)
     */
    public void ziehen() {
        // Je nach Name / Stufe wird eine andere Methode aufgerufen
        if (getName().equals("Karl Heinz")) {
            // Einfache Zugregeln
            nachRegeln();
        } else if (getName().equals("Rosalinde")) {
            // Rekursionstiefe 2
            rekursKI(2);
        } else if (getName().equals("Ursula")) {
            // Rekursionstiefe 3
            rekursKI(3);
        } else if (getName().equals("Walter")) {
            // Rekursionstiefe 4
            rekursKI(4);
        } else if (getName().equals("Harald")) {
            // Rekursionstiefe 5
            rekursKI(5);
        } else if (getName().equals("Hilfe")) {
            // Hinweisgegner
            rekursKI(3);
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
     * {@inheritDoc}
     */
    public void ziehen(JProgressBar progBar) {
        this.progBar = progBar;
        progBar.setVisible(true);
        ziehen();
    }
    
    /**
     * F&uuml;hrt einen Computerzug nach relativ simplen Zug-Regeln durch.<br>
     * Dabei werden folgende Kriterien untersucht:
     * <ul>
     * <li> Kann eine gegnerische Figur geschlagen werden?</li>
     * <li> Ist eine eigene Figur bedroht? </li>
     * <li> Wenn beides zutrifft: <br>
     * Ist die wertvollste zu schlagende Figur wertvoller als die wertvollste
     * bedrohte Figur? </li>
     * </ul>
     * Folgende Regel gilt immer: <br>
     * Schlage die wertvollste Figur des Gegners mit der Figur mit dem
     * geringsten Wert, f&uuml;r den Fall, dass die gegnerische Figur gedeckt
     * ist.<br>
     * Entscheidet sich der Computer daf&uuml;r, eine Figur in Sicherheit zu
     * bringen, so zieht er sie einfach wahllos auf ein freies Feld. Ob dieses
     * bedroht ist oder dadurch andere Vorteile f&uuml;r den Gegner entstehen,
     * wird nicht getestet.
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
                && !feld.getFigur().getKorrekteFelder().isEmpty()) {
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
        // Wenn man niemanden schlagen kann oder der Verlust hoeher waere
        } else if (maxWertSchlag == 0 || maxWertSchlag < maxWertVerlust) {
            Figur verlust = maxFeldVerlust.getFigur();
            // Alle Felder auf die die bedrohte Figur ziehen koennte
            List<Feld> alternativen = verlust.getKorrekteFelder();
            // Zieht zufaellig weg
            // Erzeugt eine Zufallszahl zwischen 0 und alleFelder.size() - 1
            int zufall = (int) (Math.random() * alternativen.size());
            spielfeld.ziehe(verlust, alternativen.get(zufall), 0);
            
        // Wenn man keine Figur verlieren wuerde oder der Verlust geringer waere
        } else if (maxWertVerlust == 0 || maxWertSchlag >= maxWertVerlust) {
            // Suche eine Figur die das Feld schlagen kann
            List<Figur> eigeneFiguren;
            if (getFarbe()) {
                eigeneFiguren = spielfeld.clone(spielfeld.getWeisseFiguren());
            } else {
                eigeneFiguren = spielfeld.clone(spielfeld.getSchwarzeFiguren());
            }
            // Groesser als 900 (Dame)
            int minWert = 1000;
            Figur ziehendeFigur = null;
            // Fuer jede eigene Figur
            for (Figur figur : eigeneFiguren) {
                // Schaue ob sie das Feld erreichen kann und wenn ja, ob sie 
                // einen geringeren Wert hat als alternative Figuren
                if (figur.getKorrekteFelder().contains(maxFeldSchlag)
                    && figur.getWert() < minWert) {
                    ziehendeFigur = figur;
                    minWert = figur.getWert();
                }
            } 
            spielfeld.ziehe(ziehendeFigur, maxFeldSchlag, 0);
        }
        
    }
    
    /**
     * Ermittelt alle m&ouml;glichen Z&uuml;ge aller Figuren, w&auml;hlt 
     * zuf&auml;llig einen von ihnen aus und f&uuml;hrt ihn anschlie&szlig;end 
     * durch.
     */
    private void zufall() {
        // Eigene Figuren
        List<Figur> eigeneFiguren;
        if (getFarbe()) {
            eigeneFiguren = spielfeld.getWeisseFiguren();
        } else {
            eigeneFiguren = spielfeld.getSchwarzeFiguren();
        }
        // Alle Felder
        List<Feld> alleFelder = new ArrayList<Feld>();
        int zufall, zufall2;
        for (Figur figur : eigeneFiguren) {
            alleFelder.addAll(figur.getKorrekteFelder());
        }
        // Wenn der Computergegner ziehen kann
        if (!alleFelder.isEmpty()) {
            do {
                // Erzeugt eine Zufallszahl zwischen 0 und 
                // eigeneFiguren.size() - 1
                zufall = (int) (Math.random() * eigeneFiguren.size());
                // Alle moeglichen Felder
                alleFelder = eigeneFiguren.get(zufall).getKorrekteFelder();
                // Erzeugt eine Zufallszahl zwischen 0 und alleFelder.size() - 1
                zufall2 = (int) (Math.random() * alleFelder.size());
            } while (alleFelder.isEmpty());
            // Ziehe dieses ausgewaehlte Feld
            spielfeld.ziehe(eigeneFiguren.get(zufall), 
                alleFelder.get(zufall2), 1);   
        }
    }
    
    /**
     * Ein rekursiver Computergegner, der theoretisch beliebig viele Stufen 
     * untersuchen kann. <br> 
     * Ermittelt den besten Zug und zieht ihn.
     * Praktisch nur sinnvoll f&uuml;r die Stufen 2 - 4. <br>
     * Teile der verwendeten Methoden min und max basieren auf dem folgenden
     * Prinzip: {@link <a href="http://de.wikipedia.org/wiki/Alpha-Beta-Suche">
     * http://de.wikipedia.org/wiki/Alpha-Beta-Suche</a>}  
     * @param maxStufe Die maximale Suchtiefe (sinnvollerweise zwischen 2 und
     * 4)
     */
    private void rekursKI(int maxStufe) {
        /* Ein Zug eines Computerspielers laeuft grundsaetzlich folgendermassen
         * ab:
         * 1. Ermittle fuer alle eigenen Figuren alle moeglichen Felder.
         * 2. Ziehe nacheinander alle diese Felder und fuehre fuer jeden Zug
         *    wieder die ersten beiden Schritte aus, bis die gewuenschte 
         *    Rekursionstiefe erreicht ist.
         * 3. Bewerte die aktuelle Brettstellung (Materialwert und einiges
         *    Anderes) und gib den Wert in der Rekursion zurueck an die oberste
         *    Stufe.
         * 4. Jede Stufe entscheidet nun nach dem MiniMax-Algorithmus, welcher
         *    Zug der jeweils Beste ist und gibt den Wert weiter nach oben
         * 5. Die letzte Stufe weiss nun genau, welcher Zug bei bestmoeglichem
         *    Spiel beider Seiten in n Zuegen die beste Spielsituation hervor-
         *    ruft. Dieser Zug wird anschliessend gezogen.
         */
        List<Figur> alleFiguren;
        // Wenn momentan weiss dran ist
        if (getFarbe()) {
            alleFiguren = spielfeld.clone(spielfeld.getWeisseFiguren());
        } else {
            alleFiguren = spielfeld.clone(spielfeld.getSchwarzeFiguren());
        }
        // Maximale Bewertung; initialisiert mit niedrigstem Wert
        int maxbewertung = 6000;
        if (getFarbe()) {
            maxbewertung = -6000;
        }
        // Der Zug der durchgefuehrt werden soll
        List<Figur> besteFiguren = new ArrayList<Figur>();
        List<Feld> besteFelder = new ArrayList<Feld>();
        // Ziehe alle moeglichen Figuren auf alle moeglichen Felder
        Zugsortierer sort = new Zugsortierer(alleFiguren, false, false);
        if (progBar != null) {
            progBar.setMaximum(sort.getSize());
        }
        for (int i = 0; i < sort.getSize(); i++) {
            if (progBar != null) {
                progBar.setValue(i);
            }
            Figur figur = sort.get(i).getFigur();
            Feld feld = sort.get(i).getFeld();
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
                int bewertung = min(maxStufe - 1, -4500, 4500);
                bewertung += bewertungsAnpassung(letzterZug);
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
                int bewertung = max(maxStufe - 1, -4500, 4500);
                bewertung -= bewertungsAnpassung(letzterZug);
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
        zieheZug(besteFelder, besteFiguren);
        
    }
    
    /**
     * Berechnet Bonuspunkte f&uuml;r einen Zug, wie zum Beispiel Figuren raus
     * bringen am Anfang oder Rochaden, sofern sie sinnvoll sind.
     * @param letzterZug Der letzte Zug
     * @return Ganzzahliger positiver Bonus
     */
    private int bewertungsAnpassung(Zug letzterZug) {
        int bonus = 0;
        
        // Wenn es noch die ersten 12 Halbzuege sind und ein
        // Springer oder Laeufer gezogen wurde
        if (spielfeld.getSpieldaten().getZugListe().size() < 12
            && letzterZug.getFigur().getWert() > 100
            && letzterZug.getFigur().getWert() < 465
            && letzterZug.isErsterZug()) {
            // Gibt es Extrapunkte (Figuren raus bringen)
            bonus += 10;
        }
        // Wenn Bauern gezogen werden, sind die in der Mitte in der Regel besser
        // als die am Rand
        if (spielfeld.getSpieldaten().getZugListe().size() < 16
            && letzterZug.getFigur().getWert() == 100
            && !letzterZug.isSchlagzug()) {
            int reihe = letzterZug.getStartfeld().getXK();
            if (reihe >= 4) {
                reihe = 7 - reihe;
            }
            // Je weiter in der Mitte die Figur gezogen wurde, desto mehr Punkte
            // gibt es
            bonus += reihe;
        }
        // Doppelzug eines Bauern in den ersten 12 Halbzuegen
        if (spielfeld.getSpieldaten().getZugListe().size() < 12
            && letzterZug.getFigur().getWert() == 100
            && letzterZug.isErsterZug()
            && (letzterZug.getZielfeld().getYK() == 3
            || letzterZug.getZielfeld().getYK() == 4)) {
            bonus += 5;
        }
        // Wenn ein Springer an den Rand gezogen wird
        if (letzterZug.getFigur().getWert() == 275
            && (letzterZug.getZielfeld().getXK() == 0
            || letzterZug.getZielfeld().getXK() == 7)) {
            // Gibt es noch einen zusaetzlichen Strafpunkt
            bonus -= 1;
        }
        // Wenn es eine Rochade gewesen ist
        if (letzterZug instanceof RochadenZug) {
            if (letzterZug.getZielfeld().equals(spielfeld
                .getFelder().get(62))) {
                bonus += rochadeSinnvoll(false, true);
            } else {
                bonus += rochadeSinnvoll(false, false);
            }
        }
        // Damentausch / Damenschlag
        List<Figur> gegnerFiguren;
        if (getFarbe()) {
            gegnerFiguren = spielfeld.getGeschlagenSchwarz();
        } else {
            gegnerFiguren = spielfeld.getGeschlagenWeiss();
        }
        // Wenn es ein Schlagzug war und die letzte geschlagene Figur eine Dame
        // ist
        if (letzterZug.isSchlagzug() 
            && gegnerFiguren.get(gegnerFiguren.size() - 1).getWert() == 900) {
            bonus += 30;
        }
        return bonus;
    }
    /**
     * Ermittelt den besten Zug und zieht ihn.
     * @param besteFelder Die Liste der besten Felder
     * @param besteFiguren Die Liste der besten Figuren
     */
    private void zieheZug(List<Feld> besteFelder, List<Figur> besteFiguren) {
        // Wenn es noch einen Zug zu ziehen gibt
        if (!besteFiguren.isEmpty()) {
            // Ermittle eine der Alternativen
            // Ermittle die wertgeringste Figur abgesehen vom Koenig
            int min = 1000;
            for (Figur figur : besteFiguren) {
                if (min > figur.getWert() && figur.getWert() > 0) {
                    min = figur.getWert();
                }
            }
            // Wenn kein min-Wert gesetzt wurde, konnte nur noch der Koenig
            // ziehen und deswegen wird der entsprechende Wert 0 gesetzt
            if (min == 1000) {
                min = 0;
            }
            int zufallsIndex;
            // Ermittle so lange einen zufaelligen Index, wie die ausgewaehlte
            // Figur nicht die wertgeringste ist
            do {
                // Erzeugt einen zufaelligen Index
                zufallsIndex = (int) (Math.random() * besteFiguren.size());
            } while (besteFiguren.get(zufallsIndex).getWert() != min);
            /* Ist nicht ganz schoen, hilft aber manchmal und macht den
             * Algorithmus instabil, sodass der Computer nicht mit den gleichen
             * Schritten in jeder Partie Matt gesetzt werden kann.
             */
            
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
     * @return Die maximale Bewertung als ganze Zahl
     */
    private int max(int stufe, int alpha, int beta) {
        if (stufe == 0) {
            // return bewertungsfunktion();
            return ruhigeStellungMin(alpha, beta);
        }
        int maxWert = alpha;
        List<Figur> alleFiguren = new ArrayList<Figur>();
        // Wenn momentan weiss dran ist
        if (spielfeld.getAktuellerSpieler()) {
            alleFiguren = spielfeld.clone(spielfeld.getWeisseFiguren());
        } else {
            alleFiguren = spielfeld.clone(spielfeld.getSchwarzeFiguren());
        }
        // Testet, ob ueberhaupt ein Zug gemacht wurde
        int zaehl = 0;
        // Ziehe alle moeglichen Figuren auf alle moeglichen Felder
        Zugsortierer sort;
        sort = new Zugsortierer(alleFiguren, true, false);
        for (int i = 0; i < sort.getSize(); i++) {
            Figur figur = sort.get(i).getFigur();
            Feld feld = sort.get(i).getFeld();
            zaehl++;
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
        // Wenn keine Figur gezogen werden konnte, war es eine Matt oder Patt
        // Situation
        if (zaehl == 0) {
            // Wenn er jetzt im Schach steht, ist es Matt
            if (spielfeld.isSchach()) {
                // Schach-Marker, der bei isSchach() gesetzt wird, loeschen
                spielfeld.setSchach(false);
                maxWert = -5000 - stufe;
            } else {
                /* Es ist eine Patt-Situation eingetreten. Nun soll getestet 
                 * werden, ob der aktuelle Spieler deutlich fuehrt, denn wenn
                 * dem so ist, waere ein Patt aus seiner Sicht nicht 
                 * erstrebenswert.
                 */
                // Wenn der Spieler aktuell deutlich fuehrt
                // Bei Zug rueckgaengig wurde der aktuelle Spieler gewechselt
                if (bewertungsfunktion() > 500) {
                    // Wird der niedrigste moegliche Wert gesetzt
                    // Jedoch ist Patt immernoch besser als Matt gesetzt zu
                    // werden
                    maxWert = -4501;
                } else {
                    // Wenn wir nicht fuehren ist Patt sehr schlecht
                    maxWert = 4501;
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
     * @return Die minimale Bewertung als ganze Zahl
     */
    private int min(int stufe, int alpha, int beta) {
        if (stufe == 0) {
            // return bewertungsfunktion();
            return ruhigeStellungMax(alpha, beta);
        }
        int minWert = beta;
        List<Figur> alleFiguren = new ArrayList<Figur>();
        // Wenn momentan weiss dran ist
        if (spielfeld.getAktuellerSpieler()) {
            alleFiguren = spielfeld.clone(spielfeld.getWeisseFiguren());
        } else {
            alleFiguren = spielfeld.clone(spielfeld.getSchwarzeFiguren());
        }
        // Testet ob ueberhaupt eine Figur gezogen wurde
        int zaehl = 0;
        // Ziehe alle moeglichen Figuren auf alle moeglichen Felder
        Zugsortierer sort;
        sort = new Zugsortierer(alleFiguren, true, false);
        for (int i = 0; i < sort.getSize(); i++) {
            Figur figur = sort.get(i).getFigur();
            Feld feld = sort.get(i).getFeld();
            zaehl++;
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
        // Wenn keine Figur gezogen werden konnte, war es eine Matt oder Patt
        // Situation
        if (zaehl == 0) {
            // Wenn er jetzt im Schach steht, ist es Matt
            if (spielfeld.isSchach()) {
                // Schach-Marker, der bei isSchach() gesetzt wird, loeschen
                spielfeld.setSchach(false);
                // Schlechtester Wert
                minWert = 5000 + stufe;
            } else {
                /* Es ist eine Patt-Situation eingetreten. Nun soll getestet 
                 * werden, ob der aktuelle Spieler deutlich fuehrt, denn wenn
                 * dem so ist, waere ein Patt aus seiner Sicht nicht 
                 * erstrebenswert.
                 */
                // Wenn der Spieler aktuell deutlich fuehrt
                // Beim Zug rueckgaengig wurde der Spieler gewechselt
                if (bewertungsfunktion() < 500) {
                    /* Wird der hoechst moegliche Wert gesetzt, der beim 
                     * Minimieren natuerlich der schlechteste ist.
                     * Jedoch: Patt ist immernoch besser als Matt gesetzt zu
                     * werden
                     */
                    minWert = 4501;
                } else {
                    // Wenn wir nicht fuehren ist Patt sehr gut
                    minWert = -4501;
                }
            }
        }
        
        return minWert;
    } 
    
    /**
     * Berechnet den Maximalwert f&uuml;r die aktuelle Stufe und wird verwendet
     * wenn es ein Schachgebot gibt. Dieses wird dann bis zu Ende analysiert
     * und erst dann bewertet.
     * @param alpha Der Max-Wert
     * @param beta Der Min-Wert
     * @return Die maximale Bewertung als ganze Zahl
     */
    private int ruhigeStellungMax(int alpha, int beta) {
        int maxWert = alpha;
        if (spielfeld.isSchach()) {
            spielfeld.setSchach(false);
            maxWert = max(1, alpha, beta);
        } else {
            maxWert = bewertungsfunktion();
        }
        return maxWert;
    }
    
    /**
     * Berechnet den Minimalwert f&uuml;r die aktuelle Stufe und wird verwendet
     * wenn es ein Schachgebot gibt. Dieses wird dann bis zu Ende analysiert
     * und erst dann bewertet.
     * @param alpha Der Max-Wert
     * @param beta Der Min-Wert
     * @return Die minimale Bewertung als ganze Zahl
     */
    private int ruhigeStellungMin(int alpha, int beta) {
        int minWert = beta;
        if (spielfeld.isSchach()) {
            spielfeld.setSchach(false);
            minWert = min(1, alpha, beta);
        } else {
            minWert = bewertungsfunktion();
        }
        return minWert;
    } 
    
    /**
     * Bewertet das Spielfeld nach verschiedenen Kriterien.
     * <ul>
     * <li> Materialwert </li>
     * <li> Entfernung der Bauern von der gegnerischen Grundlinie </li>
     * <li> Schachgebot </li>
     * </ul>
     * @return Eine Bewertung in Form einer ganzen Zahl im Bereich von etwa
     * -4500 bis +4500<br> <b> Positiv </b> ist gut f&uuml;r wei&szlig;,
     * <b> negativ </b> ist gut f&uuml;r schwarz.
     */
    private int bewertungsfunktion() {
        int zugAnzahl = spielfeld.getSpieldaten().getZugListe().size();
        int bewertung;
        // Materialwert
        bewertung = spielfeld.getMaterialwert(true) 
            - spielfeld.getMaterialwert(false);
        
        // Wenn Weiss nicht mehr gewinnen kann
        int matWert = spielfeld.getMaterialwert(true);
        int anzahlFiguren = spielfeld.getWeisseFiguren().size();
        boolean bauer = false;
        if (anzahlFiguren > 1) {
            bauer = spielfeld.getWeisseFiguren().get(1).getWert() == 100;
        }
        if (matWert <= 600 && !bauer) {
            bewertung -= 2000;
        }
        
        // Wenn Schwarz nicht mehr gewinnen kann
        matWert = spielfeld.getMaterialwert(false);
        anzahlFiguren = spielfeld.getSchwarzeFiguren().size();
        bauer = false;
        if (anzahlFiguren > 1) {
            bauer = spielfeld.getSchwarzeFiguren().get(1).getWert() == 100;
        }
        if (matWert <= 600 && !bauer) {
            bewertung += 2000;
        }
        
        // Figurenbeweglichkeit
        int index = 0;
        // Der Bonus des Bauern wenn er auf einer entsprechenden Reihe steht
        int[] bauernBonus = {5, 20, 80};
        int[] bauernBonusEndspiel = {30, 100, 300};
        while (index < spielfeld.getWeisseFiguren().size()) {
            Figur figur = spielfeld.getWeisseFiguren().get(index);
            if (spielfeld.getSpieldaten().getZugListe().size() >= 12
                && spielfeld.getWeisseFiguren().size() >= 6 
                && spielfeld.getMaterialwert(true) > 1000) {
                bewertung -= figurenRadiusBewertung(figur);
            }
            // Wenn es Bauern sind
            if (figur.getWert() == 100) {
                // Zaehlt erst ab 8 Zuegen
                if (spielfeld.getSpieldaten().getZugListe().size() >= 16) {
                    int y = figur.getPosition().getYK();
                    // Je naeher die Bauern an der gegnerisches Grundlinie sind
                    int[] bauernwert;
                    if (spielfeld.getMaterialwert(true) <= 1000 
                        || spielfeld.getWeisseFiguren().size() <= 6) {
                        bauernBonus = bauernBonusEndspiel;
                    }
                    // Bonus
                    bauernwert = bauernBonus;
                    if (y >= 4) {
                        // Desto mehr Punkte zaehlen sie
                        // Drei Reihen vor der Umwandlung
                        bewertung += bauernwert[0];
                        if (y >= 5) {
                            // Zwei Reihen vor der Umwandlung
                            bewertung += bauernwert[1];
                            if (y == 6) {
                                // Letzte Reihe vor der Umwandlung
                                bewertung += bauernwert[2];
                            }
                        }
                    }
                }
            }
            index++;
        }
        // Schwarz analog
        index = 0;
        while (index < spielfeld.getSchwarzeFiguren().size()) {
            Figur figur = spielfeld.getSchwarzeFiguren().get(index);
            if (spielfeld.getSpieldaten().getZugListe().size() >= 12
                && spielfeld.getSchwarzeFiguren().size() >= 6 
                && spielfeld.getMaterialwert(false) > 1000) {
                bewertung += figurenRadiusBewertung(figur);
            }
            // Wenn es ein Bauer ist
            if (figur.getWert() == 100) {
                // Zaehlt erst nach 16 Zuegen
                if (spielfeld.getSpieldaten().getZugListe().size() >= 16) {
                    int y = figur.getPosition().getYK();
                    int[] bauernwert;
                    if (spielfeld.getMaterialwert(false) <= 1000 
                        || spielfeld.getSchwarzeFiguren().size() <= 6) {
                        bauernBonus = bauernBonusEndspiel;
                    }
                    // Bonus
                    bauernwert = bauernBonus;
                    if (y <= 3) {
                        bewertung -= bauernwert[0];
                        if (y <= 2) {
                            bewertung -= bauernwert[1];
                            if (y == 1) {
                                bewertung -= bauernwert[2];
                            }
                        }
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
            bonus = 8;
        } else {
            koenig = spielfeld.getWeisseFiguren().get(0).getPosition();
            bonus = -8;
        }
        Zug letzterZug = spielfeld.getSpieldaten().getLetzterZug();
        Figur figur;
        // Wenn es ein RochadenZug war
        if (letzterZug instanceof RochadenZug) {
            // Ist es der Turm, der entscheidend ist
            figur = ((RochadenZug) letzterZug).getTurm();
        } else {
            // Sonst die aktive Figur
            figur = letzterZug.getFigur();
        }
        // Wenn die zuletzt gezogene Figur dem Koenig Schach bietet
        if (figur.bietetSchach(koenig)) {
            bewertung += bonus;
        }
        
        // Alle durch ruhigeStellungErzeugen gezogene Zuege rueckgaengig machen
        while (spielfeld.getSpieldaten().getZugListe().size() != zugAnzahl) {
            spielfeld.zugRueckgaengig();
        }
        return bewertung;
    }
    
    /**
     * Ermittelt den Punktabzug f&uuml;r den eingeschr&auml;nkten Zugradius.
     * @param figur Die entsprechende Figur
     * @return Punktabzug als ganze Zahl, prozentualer Anteil am Figurenwert
     */
    private int figurenRadiusBewertung(Figur figur) {
        int abzug = 0;
        int[] gewichtungsArray = {10, 8, 7, 6};
        int[] eroeffnung = {4, 2, 1, 1};
        if (spielfeld.getSpieldaten().getZugListe().size() < 10) {
            gewichtungsArray = eroeffnung;
        }
        int anzahlFelder = figur.getMoeglicheFelderKI().size();
        // Koenig
        if (figur.getWert() == 0) {
            if (anzahlFelder > 4) {
                // Es ist gefaehrlich wenn der Koenig so frei steht
                abzug = 50;
            }
        // Bauer
        } else if (figur.getWert() == 100) {
            // Wenn er nicht mehr ziehen kann
            if (anzahlFelder == 0) {
                // Zaehlt er nur noch wenig
                abzug = 50;
            }
        // Springer
        } else if (figur.getWert() == 275) {
            abzug = (6 - anzahlFelder) * gewichtungsArray[0];
        // Laeufer
        } else if (figur.getWert() == 325) {
            abzug = (7 - anzahlFelder) * gewichtungsArray[1];
        // Turm
        } else if (figur.getWert() == 465) {
            if (spielfeld.getSpieldaten().getZugListe().size() > 14) {
                if (anzahlFelder <= 4) {
                    abzug = 4 * gewichtungsArray[2] - 5;
                } else {
                    abzug = (8 - anzahlFelder) * gewichtungsArray[2];
                }
            }
        // Dame
        } else if (figur.getWert() == 900) {
            if (spielfeld.getSpieldaten().getZugListe().size() > 10) {
                abzug = (12 - anzahlFelder) * gewichtungsArray[3];
            }
        }
        
        return abzug;
    }
   
    /**
     * Ermittelt, wie sinnvoll es ist, die vorgeschlagene Rochade
     * durchzuf&uuml;hren.
     * @param farbe Die Farbe des Spielers
     * @param kleineRochade Ob es eine kleine Rochade oder eine gro&szlig;e ist
     * @return Bonuspunkte, falls die Rochade durchgef&uuml;hrt wird
     */
    private int rochadeSinnvoll(boolean farbe, boolean kleineRochade) {
        int bonus = 0;
        int zaehler = 0;
        if (farbe && kleineRochade) {
            int[] feldIndizes = {13, 14, 15, 21, 22, 23, 31};
            for (int index : feldIndizes) {
                if (spielfeld.getFelder().get(index).getFigur() != null
                    && spielfeld.getFelder().get(index).getFigur().getWert() 
                    == 100) {
                    zaehler++;
                }
            }
        } else if (farbe && !kleineRochade) {
            int[] feldIndizes = {9, 10, 11, 17, 18, 19};
            for (int index : feldIndizes) {
                if (spielfeld.getFelder().get(index).getFigur() != null
                    && spielfeld.getFelder().get(index).getFigur().getWert() 
                    == 100) {
                    zaehler++;
                }
            }
        } else if (!farbe && kleineRochade) {
            int[] feldIndizes = {55, 54, 53, 47, 46, 45, 39};
            for (int index : feldIndizes) {
                if (spielfeld.getFelder().get(index).getFigur() != null
                    && spielfeld.getFelder().get(index).getFigur().getWert() 
                    == 100) {
                    zaehler++;
                }
            }  
        } else if (!farbe && !kleineRochade) {
            int[] feldIndizes = {49, 50, 51, 41, 42, 43};
            for (int index : feldIndizes) {
                if (spielfeld.getFelder().get(index).getFigur() != null
                    && spielfeld.getFelder().get(index).getFigur().getWert() 
                    == 100) {
                    zaehler++;
                }
            }
        }
        if (zaehler == 2) {
            bonus = 20;
        } else if (zaehler == 3) {
            bonus = 50;
        } else {
            bonus = -20;
        }
        return bonus;
    }
    /**
     * Entscheidet, ob ein Unentschieden-Angebot vom Gegner angenommen werden 
     * soll. <br>
     * Dabei wird getestet, ob der Computergegner vom Materialwert her deutlich
     * hinten liegt.
     * @return Wahrheitswert
     */
    public boolean unentschiedenAnnehmen() {
        boolean annehmen = false;
        // Materialwert aus Sicht von weiss
        int matWert = spielfeld.getMaterialwert(true) 
            - spielfeld.getMaterialwert(false);
        // Wenn der Computergegner mehr als 600 Punkte weniger hat
        if ((getFarbe() && matWert < -600) || (!getFarbe() && matWert > 600)) {
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
