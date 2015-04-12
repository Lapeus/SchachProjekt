package gui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import daten.Spielfeld;
import figuren.Figur;
import figuren.Koenig;

/**
 * Eine Klasse, die Methoden zur Analysierung eines Textes bietet, aus ihm einen
 * Zug errechnet und diesen durch Mausklicks initiiert.
 * @author Christian Ackermann
 */
public class Textanalysierer {

    /**
     * Die SpielfeldGUI von der die Klasse aufgerufen wurde.
     */
    private SpielfeldGUI spielfeldGUI;
    
    /**
     * Das zugeh&ouml;rige Spielfeld.
     */
    private Spielfeld spielfeld;
    
    /**
     * Die Liste der Felder.
     */
    private List<Feld> felderListe;
    
    /**
     * Die SpielGUI (Das zugrundeliegende JFrame).
     */
    private SpielGUI parent;
    
    /**
     * Erstellt einen neuen Textanalysierer.<br>
     * Einziger Konstruktor dieser Klasse.
     * @param spielfeldGUI Die SpielfeldGUI von der die Klasse erstellt wurde
     * @param spielfeld Das zugeh&ouml;rige Spielfeld
     */
    public Textanalysierer(SpielfeldGUI spielfeldGUI, Spielfeld spielfeld) {
        this.spielfeldGUI = spielfeldGUI;
        this.spielfeld = spielfeld;
        this.felderListe = spielfeld.getFelder();
        this.parent = spielfeldGUI.getSpielGUI();
    }
    
    /**
     * Analysiert die Eingabe in das Textfeld.
     * @param string Der eingegebene Text
     */
    public void textAnalysieren(String string) {
        boolean fail = false;
        int leerzeichen = 0;
        // Grossbuchstaben
        string = string.toUpperCase();
        List<String> woerter = new ArrayList<String>();
        // Jedes Zeichen nach Leerzeichen durchsuchen
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                // Ganze Woerter in das Woerter-Array kopieren
                woerter.add(string.substring(leerzeichen, i));
                leerzeichen = i + 1;
            }
        }
        // Auch das letzte Wort noch
        woerter.add(string.substring(leerzeichen, string.length()));
        // Wenn der Akkusativ drin ist, wird der ersetzt
        // vgl.: Springer schlägt seinen Bauern
        if (woerter.contains("BAUERN")) {
            woerter.set(woerter.indexOf("BAUERN"), "BAUER");
        }
        String feld1 = "";
        String feld2 = "";
        List<String> enthalteneFiguren = new ArrayList<String>();
        // Moegliche Buchstaben fuer die Feldbezeichnung
        List<String> buchstaben 
            = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
        // Moegliche Zahlen fuer die Feldbezeichnung
        List<String> zahlen 
            = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        // Moegliche Figuren
        List<String> figuren
            = Arrays.asList("BAUER", "SPRINGER", "LÄUFER", "TURM", "DAME", 
                "KÖNIG");
        // Werte der moeglichen Figuren
        List<Integer> figurenWerte 
            = Arrays.asList(100, 275, 325, 465, 900, 0);
        // Fuer jedes Wort
        for (String wort : woerter) {
            // Wenn es zwei Zeichen lang ist
            if (wort.length() == 2) {
                // An der ersten Stelle einen zulaessigen Buchstaben
                if (buchstaben.contains(wort.substring(0, 1))) {
                    // Und an der zweiten eine zulaessige Zahl hat
                    if (zahlen.contains(wort.substring(1))) {
                        // Wenn es noch kein 1.Feld gibt
                        if (feld1.equals("")) {
                            feld1 = wort;
                        // Wenn es noch kein 2.Feld gibt
                        } else if (feld2.equals("")) {
                            feld2 = wort;
                        // Wenn es schon zwei Felder gibt
                        } else {
                            // Ist es nicht eindeutig
                            fail = true;
                        }
                    }
                }
            // Wenn es eine Figur ist
            } else if (figuren.contains(wort)) {
                enthalteneFiguren.add(wort);  
            }
        }
        // Wenn es kein Feld und zwei Figuren gibt
        if (feld1.equals("") && enthalteneFiguren.size() == 2) {
            // Wert der ersten Figur
            int wert1 = figurenWerte.get(figuren.indexOf(
                enthalteneFiguren.get(0)));
            // Wert der zweiten Figur
            int wert2 = figurenWerte.get(figuren.indexOf(
                enthalteneFiguren.get(1)));
            // Gibt beide Felder mit den Werten zurueck
            List<Feld> felder = getBeideFelderAusNamen(wert1, wert2);
            // Wenn das nicht ging
            if (felder == null) {
                // Ist es nicht moeglich oder nicht eindeutig
                fail = true;
            // Wenn auf dem Zielfeld eine Figur steht (eigentlich zwingend)
            } else if (felder.get(1).getFigur() != null) {
                // Und es die gleiche Farbe ist, wie die auf dem Startfeld
                if (felder.get(0).getFigur().getFarbe() 
                    == felder.get(1).getFigur().getFarbe()) {
                    // Ist der Zug nicht moeglich
                    fail = true;
                }
            }
            // Klick simulieren auf die beiden Felder
            klick(fail, felder.get(0), felder.get(1));
        // Wenn nur ein Feld fehlt, es aber mindestens eine Figur gibt
        } else if (feld2.equals("") && enthalteneFiguren.size() > 0) {
            fail = einFeldEineFigur(feld1, feld2, woerter, enthalteneFiguren, 
                fail);
        // Wenn beide Felder gefunden wurden
        } else if (!feld2.equals("")) {
            // Spalte des Startfeldes
            int index1 = buchstaben.indexOf(feld1.substring(0, 1));
            // Zeile des Startfeldes
            int index2 = zahlen.indexOf(feld1.substring(1));
            // Berechnung des Feldindex'
            int feldIndex1 = index1 + 8 * index2;
            // Analog fuer Zielfeld
            index1 = buchstaben.indexOf(feld2.substring(0, 1));
            index2 = zahlen.indexOf(feld2.substring(1));
            int feldIndex2 = index1 + 8 * index2;
            Feld startfeld = felderListe.get(feldIndex1);
            Feld zielfeld = felderListe.get(feldIndex2);
            // Wenn auf dem Zielfeld eine eigene Figur steht
            if (zielfeld.getFigur() != null
                && startfeld.getFigur().getFarbe() 
                == zielfeld.getFigur().getFarbe()) {
                // Ist der Zug nicht moeglich
                fail = true;
            }
            // Initiiere den Klick auf die beiden Felder
            klick(fail, startfeld, zielfeld);
        // Wenn das Wort Rochade drin ist
        } else if (woerter.contains("ROCHADE")) {
            // Soll er eine Rochade analysieren 
            fail = rochade(woerter, fail);
        }
        // Wenn es einen Fehler gab
        if (fail) {
            // Wird das angezeigt
            parent.soundAbspielen("Hinweis.wav");
            JOptionPane.showMessageDialog(parent, 
                "<html>Zug nicht m&ouml;glich oder nicht eindeutig!",
                "Warnung!", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Berechnet das Startfeld aus dem angegebenem Wert und dem Zielfeld.
     * Wird bei der textuellen Eingabe des Zuges verwendet.
     * @param wert Der Wert der Figur auf dem Startfeld
     * @param zielfeld Das Zielfeld das erreichbar sein muss
     * @return Das gesuchte Startfeld
     */
    private Feld getStartfeldAusNamen(int wert, Feld zielfeld) {
        Feld feld = null;
        boolean gefunden = false;
        List<Figur> eigeneFiguren;
        // Eigene Figuren
        if (spielfeld.getAktuellerSpieler()) {
            eigeneFiguren = spielfeld.getWeisseFiguren();
        } else {
            eigeneFiguren = spielfeld.getSchwarzeFiguren();
        }
        int zaehl = 0;
        do {
            // Wenn die aktuell betrachtete Figur den angegebenen Wert hat
            if (eigeneFiguren.get(zaehl).getWert() == wert) {
                Figur figur = eigeneFiguren.get(zaehl);
                // Wenn sie auf das Zielfeld ziehen kann
                if (figur.getMoeglicheFelderKI().contains(zielfeld)) {
                    if (figur.isKorrektesFeld(zielfeld)) {
                        // Und es noch keine andere Moeglichkeit gab
                        if (!gefunden) {
                            feld = figur.getPosition();
                            gefunden = true;
                        } else {
                            return null;
                        }
                    }
                }
            }
            zaehl++;
            // Solange wir noch die zulaessige Figur betrachten
        } while (eigeneFiguren.get(zaehl).getWert() <= wert);
        
        return feld;
    }
    
    /**
     * Berechnet das Zielfeld aus dem angegebenem Wert und dem Startfeld.
     * Wird bei der textuellen Eingabe des Zuges verwendet.
     * @param wert Der Wert der Figur auf dem Zielfeld
     * @param startfeld Das Startfeld
     * @return Das gesuchte Zielfeld
     */
    private Feld getZielfeldAusNamen(int wert, Feld startfeld) {
        Feld feld = null;
        boolean gefunden = false;
        // Fuer jedes vom Startfeld aus zu erreichendes Feld
        for (Feld feldAktuell : startfeld.getFigur().getMoeglicheFelderKI()) {
            // Wenn die Figur darauf den angegebenen Wert hat
            if (feldAktuell.getFigur() != null
                && feldAktuell.getFigur().getWert() == wert) {
                if (!gefunden) {
                    feld = feldAktuell;
                    gefunden = true;
                } else {
                    return null;
                }
            }
        }
        
        return feld;
    }
    
    /**
     * Berechnet das Startfeld und das Zielfeld aus den angegebenen Werten.
     * Wird bei der textuellen Eingabe des Zuges verwendet.
     * @param wert1 Der Wert der Figur auf dem Startfeld
     * @param wert2 Der Wert der Figur auf dem Zielfeld
     * @return Eine Liste mit dem Start- und dem Zielfeld
     */
    private List<Feld> getBeideFelderAusNamen(int wert1, int wert2) {
        List<Feld> felder = new ArrayList<Feld>();
        boolean gefunden = false;
        List<Figur> eigeneFiguren;
        // Eigene Figuren
        if (spielfeld.getAktuellerSpieler()) {
            eigeneFiguren = spielfeld.getWeisseFiguren();
        } else {
            eigeneFiguren = spielfeld.getSchwarzeFiguren();
        }
        int zaehl = 0;
        do {
            // Wenn die aktuelle Figur den angegebenen Wert hat
            if (eigeneFiguren.get(zaehl).getWert() == wert1) {
                Figur figur = eigeneFiguren.get(zaehl);
                // Fuer jedes von dieser Figur zu erreichenden Feldes
                for (Feld feld : figur.getMoeglicheFelderKI()) {
                    if (feld.getFigur() != null
                        && feld.getFigur().getWert() == wert2
                        && figur.isKorrektesFeld(feld)) {
                        // Wenn es noch keine andere Moeglichkeit gab
                        if (!gefunden) {
                            felder.add(figur.getPosition());
                            felder.add(feld);
                            gefunden = true;
                        } else {
                            return null;
                        }
                    }
                }
            }
            zaehl++;
            // Solange noch zulaessige Figuren betrachtet werden
        } while (eigeneFiguren.get(zaehl).getWert() <= wert1);
        if (felder.isEmpty()) {
            felder = null;
        }
        return felder;
    }
    
    /**
     * Ausgelagerte Methode f&uuml;r den Fall: Ein Feld und mindestens eine 
     * Figur bekannt.
     * @param feld1 Das erste Feld
     * @param feld2 Das zweite Feld
     * @param woerter Liste der W&ouml;rter
     * @param enthalteneFiguren Die enthaltenen Figuren
     * @param fail Die Pr&uuml;fvariable
     * @return Die Pr&uuml;fvariable
     */
    private boolean einFeldEineFigur(String feld1, String feld2, 
        List<String> woerter, List<String> enthalteneFiguren, boolean fail) {
        // Moegliche Buchstaben fuer die Feldbezeichnung
        List<String> buchstaben 
            = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
        // Moegliche Zahlen fuer die Feldbezeichnung
        List<String> zahlen 
            = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        // Moegliche Figuren
        List<String> figuren
            = Arrays.asList("BAUER", "SPRINGER", "LÄUFER", "TURM", "DAME", 
                "KÖNIG");
        // Werte der moeglichen Figuren
        List<Integer> figurenWerte 
            = Arrays.asList(100, 275, 325, 465, 900, 0);
        // Stelle des Feldes im Text
        int indexDesFeldes = woerter.indexOf(feld1);
        int indexDesNamens = 0;
        Feld startfeld;
        Feld zielfeld;
        // Wenn es nur eine Figur gibt
        if (enthalteneFiguren.size() == 1) {
            // Stelle des Namens im Text
            indexDesNamens = woerter.indexOf(enthalteneFiguren.get(0));
        // Wenn es zwei Figuren gibt
        } else if (enthalteneFiguren.size() == 2) {
            // Stelle des zweiten Wortes
            indexDesNamens = woerter.lastIndexOf(enthalteneFiguren.get(1));
        // Wenn es mehr als zwei Figuren gibt
        } else {
            // Ist es nicht eindeutig
            fail = true;
        }
        // Wenn nichts schief gegangen ist
        if (!fail) {
            // Wenn das Feld vor der Figur kam
            if (indexDesNamens > indexDesFeldes) {
                // Index der Spalte
                int index1 = buchstaben.indexOf(feld1.substring(0, 1));
                // Index der Zeile
                int index2 = zahlen.indexOf(feld1.substring(1));
                // Berechnung des Feldindex'
                int feldIndex1 = index1 + 8 * index2;
                startfeld = felderListe.get(feldIndex1);
                // Der Name dem Zielfeld zuordnen
                zielfeld = getZielfeldAusNamen(figurenWerte.get(
                    figuren.indexOf(enthalteneFiguren.get(
                        enthalteneFiguren.size() - 1))), startfeld);
                // Wenn es kein Zielfeld gab
                if (zielfeld == null) {
                    // Ist es nicht moeglich oder nicht eindeutig
                    fail = true;
                }
            // Wenn das Feld nach der Figur kam
            } else {
                // Muss das Feld getauscht werden
                feld2 = feld1;
                // Index der Spalte
                int index1 = buchstaben.indexOf(feld2.substring(0, 1));
                // Index der Zeile
                int index2 = zahlen.indexOf(feld2.substring(1));
                // Berechnung des Feldindex'
                int feldIndex2 = index1 + 8 * index2;
                zielfeld = felderListe.get(feldIndex2);
                // Der Name dem Startfeld zuordnen
                startfeld = getStartfeldAusNamen(figurenWerte.get(
                    figuren.indexOf(
                        enthalteneFiguren.get(
                            enthalteneFiguren.size() - 1))), zielfeld);
                // Wenn es kein Startfeld gibt
                if (startfeld == null) {
                    // Ist es nicht moeglich oder nicht eindeutig
                    fail = true;
                }
            }
            // Wenn auf dem Zielfeld eine Figur steht und diese die gleiche
            // Farbe hat wie die auf dem Startfeld
            if (zielfeld.getFigur() != null
                && startfeld.getFigur().getFarbe() 
                == zielfeld.getFigur().getFarbe()) {
                // Ist der Zug nicht moeglich
                fail = true;
            }
            // Initiiere den Klick auf die beiden Felder
            klick(fail, startfeld, zielfeld);
        }
        return fail;
    }
    
    /**
     * Behandelt die Eingabe des Wortes Rochade.
     * @param woerter Die Liste der W&ouml;rter
     * @param fail Ob es schon gescheitert ist
     * @return Gibt fail zur&uuml;ck
     */
    private boolean rochade(List<String> woerter, boolean fail) {
        Feld startfeld = null;
        Feld zielfeld = null;
        // Das Startfeld
        if (spielfeld.getAktuellerSpieler()) {
            startfeld = felderListe.get(4);
        } else {
            startfeld = felderListe.get(60);
        }
        // Kleine Rochade
        if (woerter.contains("KLEINE")) {
            if (spielfeld.getAktuellerSpieler()) {
                zielfeld = felderListe.get(6);
            } else {
                zielfeld = felderListe.get(62);
            }
            if (!rochadeMoeglich(true, startfeld)) {
                fail = true;
            }
        // Grosse Rochade
        } else if (woerter.contains(("große").toUpperCase())) {
            if (spielfeld.getAktuellerSpieler()) {
                zielfeld = felderListe.get(2);
            } else {
                zielfeld = felderListe.get(58);
            }
            if (!rochadeMoeglich(false, startfeld)) {
                fail = true;
            }
        // Nur Rochade
        } else {
            boolean gefunden = false;
            // Kleine Rochade
            if (rochadeMoeglich(true, startfeld)) {
                if (spielfeld.getAktuellerSpieler()) {
                    zielfeld = felderListe.get(6);
                } else {
                    zielfeld = felderListe.get(62);
                }
                gefunden = true;
            }
            // Grosse Rochade
            if (rochadeMoeglich(false, startfeld) && !gefunden) {
                if (spielfeld.getAktuellerSpieler()) {
                    zielfeld = felderListe.get(2);
                } else {
                    zielfeld = felderListe.get(58);
                }
                gefunden = true;
            } else {
                fail = true;
            }
        }
        // Wenn auf dem Zielfeld eine eigene Figur steht
        if (zielfeld.getFigur() != null
            && startfeld.getFigur().getFarbe() 
            == zielfeld.getFigur().getFarbe()) {
            fail = true;
        }
        // Klick initiieren
        klick(fail, startfeld, zielfeld);
        return fail;
    }
    
    /**
     * Testet ob eine Rochade m&ouml;glich ist.
     * @param kleineRochade Ob es eine kleine oder eine gro&szlig;e Rochade ist
     * @param startfeld Das Startfeld, das den K&ouml;nig identifiziert
     * @return Wahrheitswert
     */ 
    private boolean rochadeMoeglich(boolean kleineRochade, Feld startfeld) {
        boolean moeglich = false;
        // Wenn auf dem Feld ein Koenig steht
        if (startfeld.getFigur() != null 
            && startfeld.getFigur().getWert() == 0) {
            Koenig koenig = (Koenig) startfeld.getFigur();
            if (koenig.rochadeMoeglich(kleineRochade)) {
                moeglich = true;
            } else {
                moeglich = false;
            }
        }
        return moeglich;
    }
    
    /**
     * Erzeugt ein neues MouseEvent, ausgel&ouml;st von dem angegebenen Objekt.
     * @param o Das entsprechende Objekt
     * @return Ein neues MouseEvent
     */
    private MouseEvent newMouseEvent(Component o) {
        return new MouseEvent(o, 0, 0L, 0, 0, 0, 0, false);
    }
    
    /**
     * Initiiert den Mausklick auf die beiden ermittelten Felder.
     * @param fail Ob die Berechnung gescheitert ist
     * @param startfeld Das Startfeld
     * @param zielfeld Das Zielfeld
     */
    private void klick(boolean fail, Feld startfeld, Feld zielfeld) {
        // Sofern nichts schief gegangen ist
        if (!fail) {
            // Klicke auf die beiden Felder
            spielfeldGUI.mouseClicked(newMouseEvent(startfeld));
            spielfeldGUI.mouseClicked(newMouseEvent(zielfeld));
        }
    }
}
