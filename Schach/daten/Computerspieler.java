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
        int maxbewertung = 4500;
        if (getFarbe()) {
            maxbewertung = -4500;
        }
        // Der Zug der durchgefuehrt werden soll
        List<Figur> besteFiguren = new ArrayList<Figur>();
        List<Feld> besteFelder = new ArrayList<Feld>();
        // Ziehe alle moeglichen Figuren auf alle moeglichen Felder
        for (Figur figur : alleFiguren) {
            for (Feld feld : figur.getKorrekteFelder()) {
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
                    // Wenn es noch die ersten 16 Halbzuege sind und ein 
                    // Springer oder Laeufer gezogen wurde
                    if (spielfeld.getSpieldaten().getZugListe().size() < 16
                        && letzterZug.getFigur().getWert() > 100
                        && letzterZug.getFigur().getWert() < 465
                        && letzterZug.isErsterZug()) {
                        // Gibt es Extrapunkte (Figuren raus bringen)
                        bewertung += 20;
                    }
                    System.out.println(letzterZug.toSchachNotation() + " " + bewertung);
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
                    // Wenn es noch die ersten 16 Halbzuege sind und ein
                    // Springer oder Laeufer gezogen wurde
                    if (spielfeld.getSpieldaten().getZugListe().size() < 16
                        && letzterZug.getFigur().getWert() > 100
                        && letzterZug.getFigur().getWert() < 465
                        && letzterZug.isErsterZug()) {
                        // Gibt es Extrapunkte (Figuren raus bringen)
                        bewertung -= 20;
                    }
                    System.out.println(letzterZug.toSchachNotation() + " " + bewertung);
                    
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
        
        // Wenn es noch einen Zug zu ziehen gibt
        if (!besteFiguren.isEmpty()) {
            // Ermittle eine der Alternativen
            // Ermittle die wertgeringste Figur
            int min = 900;
            for (Figur figur : besteFiguren) {
                if (min > figur.getWert()) {
                    min = figur.getWert();
                }
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
        System.out.println("");
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
        // Testet, ob ueberhaupt ein Zug gemacht wurde
        int zaehl = 0;
        // Ziehe alle moeglichen Figuren auf alle moeglichen Felder
        for (Figur figur : alleFiguren) {
            for (Feld feld : figur.getKorrekteFelder()) {
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
        }
        // Wenn keine Figur gezogen werden konnte, war es eine Matt oder Patt
        // Situation
        if (zaehl == 0) {
            // Wenn er jetzt im Schach steht, ist es Matt
            if (spielfeld.isSchach()) {
                // Schach-Marker, der bei isSchach() gesetzt wird, loeschen
                spielfeld.setSchach(false);
            } else {
                /* Es ist eine Patt-Situation eingetreten. Nun soll getestet 
                 * werden, ob der aktuelle Spieler deutlich fuehrt, denn wenn
                 * dem so ist, waere ein Patt aus seiner Sicht nicht 
                 * erstrebenswert.
                 */
                // Wenn der Spieler aktuell deutlich fuehrt
                if (bewertungsfunktion() > 600) {
                    // Wird der niedrigste moegliche Wert gesetzt
                    maxWert = -4500;
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
        // Testet ob ueberhaupt eine Figur gezogen wurde
        int zaehl = 0;
        // Ziehe alle moeglichen Figuren auf alle moeglichen Felder
        for (Figur figur : alleFiguren) {
            for (Feld feld : figur.getKorrekteFelder()) {
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
        }
        // Wenn keine Figur gezogen werden konnte, war es eine Matt oder Patt
        // Situation
        if (zaehl == 0) {
            // Wenn er jetzt im Schach steht, ist es Matt
            if (spielfeld.isSchach()) {
                // Schach-Marker, der bei isSchach() gesetzt wird, loeschen
                spielfeld.setSchach(false);
            } else {
                /* Es ist eine Patt-Situation eingetreten. Nun soll getestet 
                 * werden, ob der aktuelle Spieler deutlich fuehrt, denn wenn
                 * dem so ist, waere ein Patt aus seiner Sicht nicht 
                 * erstrebenswert.
                 */
                // Wenn der Spieler aktuell deutlich fuehrt
                // (Minimieren fuer schwarz -> je kleiner desto besser)
                if (bewertungsfunktion() < 600) {
                    /* Wird der hoechst moegliche Wert gesetzt, der beim 
                     * Minimieren natuerlich der schlechteste ist
                     */
                    minWert = 4500;
                }
            }
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
        int bewertung;
        // Materialwert
        bewertung = spielfeld.getMaterialwert(true) 
            - spielfeld.getMaterialwert(false);
        // Bauern kurz vor der Umwandlung und Springer am Rand
        int index = 1;
        while (index < spielfeld.getWeisseFiguren().size() 
            && spielfeld.getWeisseFiguren().get(index).getWert() < 325) {
            // Wenn es Bauern sind
            if (spielfeld.getWeisseFiguren().get(index).getWert() == 100) {
                // Zaehlt erst ab 16 Zuegen
                if (spielfeld.getSpieldaten().getZugListe().size() >= 16) {
                    Figur bauer = spielfeld.getWeisseFiguren().get(index);
                    int y = bauer.getPosition().getYK();
                    // Je naeher die Bauern an der gegnerisches Grundlinie sind
                    if (y >= 4) {
                        // Desto mehr Punkte zaehlen sie
                        // Drei Reihen vor der Umwandlung +20
                        bewertung += 20;
                        if (y >= 5) {
                            // Zwei Reihen vor der Umwandlung +20 +40
                            bewertung += 40;
                            if (y == 6) {
                                // Letzte Reihe vor der Umwandlung +20 +40 +115
                                bewertung += 115;
                                // Damit sind sie direkt vor der Umwandlung so 
                                // viel wert wie ein Springer
                            }
                        }
                    }
                }
            } else {
                Figur springer = spielfeld.getWeisseFiguren().get(index);
                int x = springer.getPosition().getXK();
                // Springer am Rand sind hinderlich
                if (x == 0 || x == 7) {
                    // Strafpunkte
                    bewertung -= 40;
                }
            }
            index++;
        }
        // Schwarz analog
        index = 1;
        while (index < spielfeld.getSchwarzeFiguren().size() 
            && spielfeld.getSchwarzeFiguren().get(index).getWert() < 325) {
            // Wenn es ein Bauer ist
            if (spielfeld.getSchwarzeFiguren().get(index).getWert() 
                == 100) {
                // Zaehlt erst nach 16 Zuegen
                if (spielfeld.getSpieldaten().getZugListe().size() >= 16) {
                    Figur bauer = spielfeld.getSchwarzeFiguren().get(index);
                    int y = bauer.getPosition().getYK();
                    if (y <= 3) {
                        bewertung -= 20;
                        if (y <= 2) {
                            bewertung -= 40;
                            if (y == 1) {
                                bewertung -= 115;
                            }
                        }
                    }
                }
            } else {
                Figur springer = spielfeld.getSchwarzeFiguren().get(index);
                int x = springer.getPosition().getXK();
                // Springer am Rand sind hinderlich
                if (x == 0 || x == 7) {
                    // Strafpunkte
                    bewertung += 40;
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
        
        return bewertung;
    }
    
    /**
     * Entscheidet, ob ein Unentschieden-Angebot vom Gegner angenommen werden 
     * soll. <br>
     * Dabei wird getestet, ob der Computergegner vom Materialwert her deutlich
     * hinten liegt und ob in den letzten 16 Halbz&uuml;gen ein Bauer gezogen
     * oder eine Figur geschlagen wurde (Abwandlung der 50-Z&uuml;ge-Regel).
     * @see Spieldaten#fuenfzigZuegeRegel()
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
        
        // Test auf 8 Zuege Regel (Variante der offiziellen 50 Zuege Regel
        // Grundsaetzliche Annahme, die 8 Zuege Regel wuerde zutreffen
        boolean remis = true;
        int zaehl = 0; 
        // Wenn noch keine 16 Halbzuege gezogen wurden
        if (spielfeld.getSpieldaten().getZugListe().size() < 16) {
            remis = false;
        } else {
            /* Solange noch nicht alle letzten 16 Halbzuege getestet wurden und 
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
