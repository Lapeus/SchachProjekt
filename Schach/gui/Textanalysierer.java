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
        string = string.toUpperCase();
        List<String> woerter = new ArrayList<String>();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ' ') {
                woerter.add(string.substring(leerzeichen, i));
                leerzeichen = i + 1;
            }
        }
        woerter.add(string.substring(leerzeichen, string.length()));
        if (woerter.contains("BAUERN")) {
            woerter.set(woerter.indexOf("BAUERN"), "BAUER");
        }
        String feld1 = "";
        String feld2 = "";
        List<String> enthalteneFiguren = new ArrayList<String>();
        List<String> buchstaben 
            = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
        List<String> zahlen 
            = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        List<String> figuren
            = Arrays.asList("BAUER", "SPRINGER", "LÄUFER", "TURM", "DAME", 
                "KÖNIG");
        List<Integer> figurenWerte 
            = Arrays.asList(100, 275, 325, 465, 900, 0);
        for (String wort : woerter) {
            if (wort.length() == 2) {
                if (buchstaben.contains(wort.substring(0, 1))) {
                    if (zahlen.contains(wort.substring(1))) {
                        if (feld1.equals("")) {
                            feld1 = wort;
                        } else if (feld2.equals("")) {
                            feld2 = wort;
                        } else {
                            fail = true;
                        }
                    }
                }
            } else if (figuren.contains(wort)) {
                enthalteneFiguren.add(wort);  
            }
        }
        // Wir haben nur Figuren und keine Felder
        if (feld1.equals("") && enthalteneFiguren.size() == 2) {
            int wert1 = figurenWerte.get(figuren.indexOf(
                enthalteneFiguren.get(0)));
            int wert2 = figurenWerte.get(figuren.indexOf(
                enthalteneFiguren.get(1)));
            List<Feld> felder = getBeideFelderAusNamen(wert1, wert2);
            if (felder == null) {
                fail = true;
            } else if (felder.get(1).getFigur() != null) {
                if (felder.get(0).getFigur().getFarbe() 
                    == felder.get(1).getFigur().getFarbe()) {
                    fail = true;
                }
            }
            klick(fail, felder.get(0), felder.get(1));
        // Wenn nur ein Feld fehlt, wir aber mindestens eine Figur haben
        } else if (feld2.equals("") && enthalteneFiguren.size() > 0) {
            int indexDesFeldes = woerter.indexOf(feld1);
            int indexDesNamens = 0;
            Feld startfeld;
            Feld zielfeld;
            if (enthalteneFiguren.size() == 1) {
                indexDesNamens = woerter.indexOf(enthalteneFiguren.get(0));
            } else if (enthalteneFiguren.size() == 2) {
                indexDesNamens = woerter.lastIndexOf(enthalteneFiguren.get(1));
                // Wenn das Feld vor dem zweiten Namen kam
            } else {
                fail = true;
            }
            if (!fail) {
                if (indexDesNamens > indexDesFeldes) {
                    int index1 = buchstaben.indexOf(feld1.substring(0, 1));
                    int index2 = zahlen.indexOf(feld1.substring(1));
                    int feldIndex1 = index1 + 8 * index2;
                    startfeld = felderListe.get(feldIndex1);
                    // Muss der Name dem Zielfeld zugeordnet werden
                    zielfeld = getZielfeldAusNamen(figurenWerte.get(
                        figuren.indexOf(enthalteneFiguren.get(
                            enthalteneFiguren.size() - 1))), startfeld);
                    if (zielfeld == null) {
                        fail = true;
                    }
                } else {
                    feld2 = feld1;
                    int index1 = buchstaben.indexOf(feld2.substring(0, 1));
                    int index2 = zahlen.indexOf(feld2.substring(1));
                    int feldIndex2 = index1 + 8 * index2;
                    zielfeld = felderListe.get(feldIndex2);
                    // Muss der Name dem Startfeld zugeordnet werden
                    startfeld = getStartfeldAusNamen(figurenWerte.get(
                        figuren.indexOf(
                            enthalteneFiguren.get(
                                enthalteneFiguren.size() - 1))), zielfeld);
                    if (startfeld == null) {
                        fail = true;
                    }
                }
                if (zielfeld.getFigur() != null
                    && startfeld.getFigur().getFarbe() 
                    == zielfeld.getFigur().getFarbe()) {
                    fail = true;
                }
                klick(fail, startfeld, zielfeld);
            }
        // Wenn beide Felder gefunden wurden
        } else if (!feld2.equals("")) {
            int index1 = buchstaben.indexOf(feld1.substring(0, 1));
            int index2 = zahlen.indexOf(feld1.substring(1));
            int feldIndex1 = index1 + 8 * index2;
            index1 = buchstaben.indexOf(feld2.substring(0, 1));
            index2 = zahlen.indexOf(feld2.substring(1));
            int feldIndex2 = index1 + 8 * index2;
            Feld startfeld = felderListe.get(feldIndex1);
            Feld zielfeld = felderListe.get(feldIndex2);
            if (zielfeld.getFigur() != null
                && startfeld.getFigur().getFarbe() 
                == zielfeld.getFigur().getFarbe()) {
                fail = true;
            }
            klick(fail, startfeld, zielfeld);
        } else if (woerter.contains("ROCHADE")) {
            fail = rochade(woerter, fail);
        }
        if (fail) {
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
        if (spielfeld.getAktuellerSpieler()) {
            eigeneFiguren = spielfeld.getWeisseFiguren();
        } else {
            eigeneFiguren = spielfeld.getSchwarzeFiguren();
        }
        int zaehl = 0;
        do {
            if (eigeneFiguren.get(zaehl).getWert() == wert) {
                Figur figur = eigeneFiguren.get(zaehl);
                if (figur.getMoeglicheFelderKI().contains(zielfeld)) {
                    if (figur.isKorrektesFeld(zielfeld)) {
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
        for (Feld feldAktuell : startfeld.getFigur().getMoeglicheFelderKI()) {
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
        if (spielfeld.getAktuellerSpieler()) {
            eigeneFiguren = spielfeld.getWeisseFiguren();
        } else {
            eigeneFiguren = spielfeld.getSchwarzeFiguren();
        }
        int zaehl = 0;
        do {
            if (eigeneFiguren.get(zaehl).getWert() == wert1) {
                Figur figur = eigeneFiguren.get(zaehl);
                for (Feld feld : figur.getMoeglicheFelderKI()) {
                    if (feld.getFigur() != null
                        && feld.getFigur().getWert() == wert2
                        && figur.isKorrektesFeld(feld)) {
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
        } while (eigeneFiguren.get(zaehl).getWert() <= wert1);
        if (felder.isEmpty()) {
            felder = null;
        }
        return felder;
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
        } else {
            boolean gefunden = false;
            if (rochadeMoeglich(true, startfeld)) {
                if (spielfeld.getAktuellerSpieler()) {
                    zielfeld = felderListe.get(6);
                } else {
                    zielfeld = felderListe.get(62);
                }
                gefunden = true;
            }
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
        if (zielfeld.getFigur() != null
            && startfeld.getFigur().getFarbe() 
            == zielfeld.getFigur().getFarbe()) {
            fail = true;
        }
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
        if (!fail) {
            spielfeldGUI.mouseClicked(newMouseEvent(startfeld));
            spielfeldGUI.mouseClicked(newMouseEvent(zielfeld));
        }
    }
}
