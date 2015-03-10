package zuege;

import figuren.Figur;
import gui.Feld;

/**
 * Verwaltet alle Informationen die f&uuml;r einen Zug wichtig sind. <br>
 * Neben den beteiligten Feldern und der Figur wird unter anderem auch die Dauer
 * des Zuges gespeichert.
 * @author Christian Ackermann
 */
public class Zug {

    /**
     * Das Feld auf dem die gezogene Figur vor dem Zug stand.
     */
    private Feld startfeld;
    
    /**
     * Das Feld auf dem die gezogene Figur nach dem Zug steht.
     */
    private Feld zielfeld;
    
    /**
     * Die gezogene Figur.
     */
    private Figur figur;
    
    /**
     * Gibt an, ob bei diesem Zug eine Figur geschlagen wurde. <br>
     * Ist wichig, wenn der Zug r&uuml;ckg&auml;ngig gemacht werden soll.
     */
    private boolean schlagzug;
    
    /**
     * Die Dauer des Zuges in Sekunden.
     */
    private int zugzeit;
    
    /**
     * Gibt an, ob es der erste Zug der gezogenen Figur war. <br>
     * Ist wichtig, wenn der Zug r&uuml;ckg&auml;ngig gemacht werden soll, oder
     * wenn es ein Bauer war, der nun en-passant geschlagen werden kann.
     */
    private boolean ersterZug;
    
    /**
     * Leerer Konstrukor um Sonderz&uuml;ge mit anderen Attributen anlegen zu
     * k&ouml;nnen. <br>
     * @see RochadenZug
     * @see EnPassantZug
     */
    public Zug() {

    }
    
    /**
     * Erstellt einen neuen Zug.<br>
     * Hauptkonstruktor dieser Klasse.
     * @param startfeld Das Feld auf dem die Figur vor dem Zug stand
     * @param zielfeld Das Feld auf dem die Figur nach dem Zug steht
     * @param figur Die Figur die gezogen wird
     * @param schlagzug Ob bei diesem Zug eine andere Figur geschlagen wird
     * @param zugzeit Die Dauer des Zuges in Sekunden
     * @param ersterZug Ob es der erste Zug dieser Figur ist
     */
    public Zug(Feld startfeld, Feld zielfeld, Figur figur, boolean schlagzug
        , int zugzeit, boolean ersterZug) {
        this.startfeld = startfeld;
        this.zielfeld = zielfeld;
        this.figur = figur;
        this.schlagzug = schlagzug;
        this.zugzeit = zugzeit;
        this.ersterZug = ersterZug;
    }
    
    /**
     * Erstellt einen neuen Zug, ohne zu wissen, welche Figur gezogen wird.
     * Beim Laden wird dieser Konstruktor verwendet, da in der Schachnotation
     * zwar die Felder angegeben sind, aber kein Verweis auf das Objekt der 
     * schlagenden Figur.
     * @param startfeld Das Startfeld der ziehenden Figur
     * @param zielfeld Das Zielfeld der ziehenden Figur
     * @param schlagzug Ob bei diesem Zug eine andere Figur geschlagen wird
     * @param zugzeit Die Dauer des Zuges in Sekunden
     */
    public Zug(Feld startfeld, Feld zielfeld, boolean schlagzug, int zugzeit) {
        this.startfeld = startfeld;
        this.zielfeld = zielfeld;
        this.schlagzug = schlagzug;
        this.zugzeit = zugzeit;
    }
    
    /**
     * Gibt den Zug in einer stark vereinfachten Schachnotation samt Zugzeit 
     * wieder.
     * @return Darstellung des Zugs als Zeichenkette
     */
    public String toSchachNotation() {
        String string = "";
        int wert = getFigur().getWert();
        // Figurensymbol
        if (wert == 275) {
            string += "S";
        } else if (wert == 325) {
            string += "L";
        } else if (wert == 465) {
            string += "T";
        } else if (wert == 900) {
            string += "D";
        } else if (wert == 0) {
            string += "K";
        }
        // Spaltenbezeichnung
        String[] spalten = {"a", "b", "c", "d", "e", "f", "g", "h"};
        // Das Startfeld
        string += spalten[startfeld.getXK()] + (startfeld.getYK() + 1);
        // Wenn es ein Schlagzug ist
        if (schlagzug) {
            string += "x";
        // Wenn es ein normaler Zug ist
        } else {
            string += "-";
        }
        // Das Zielfeld
        string += spalten[zielfeld.getXK()] + (zielfeld.getYK() + 1);
        // Die Zugzeit 
        string += " " + zugzeit + " sek";
        
        return string;
    }
    
    /**
     * Gibt das Startfeld zur&uuml;ck.
     * @return Das Startfeld
     */
    public Feld getStartfeld() {
        return startfeld;
    }
    
    /**
     * Gibt das Zielfeld zur&uuml;ck.
     * @return Das Zielfeld
     */
    public Feld getZielfeld() {
        return zielfeld;
    }
    
    /**
     * Gibt die gezogene Figur zur&uuml;ck.
     * @return Die Figur
     */
    public Figur getFigur() {
        if (figur == null) {
            return startfeld.getFigur();
        } else {
            return figur;
        }
    }
    
    /**
     * Gibt die Dauer des Zuges zur&uuml;ck.
     * @return Dauer des Zuges als ganze Zahl in Sekunden
     */
    public int getZugzeit() {
        return zugzeit;
    }
    
    /**
     * Setzt die Dauer des Zuges.
     * @param zugzeit Die Dauer des Zuges in ganzen Sekunden
     */
    public void setZugzeit(int zugzeit) {
        this.zugzeit = zugzeit;
    }
    
    /**
     * Gibt an, ob bei dem Zug eine Figur geschlagen wurde.
     * @return Wahrheitswert, ob eine Figur geschlagen wurde
     */
    public boolean isSchlagzug() {
        return schlagzug;
    }
    
    /**
     * Setzt, ob bei dem Zug eine Figur geschlagen wurde.
     * @param schlagzug Wahrheitswert, ob eine Figur geschlagen wird
     */
    public void setSchlagzug(boolean schlagzug) {
        this.schlagzug = schlagzug;
    }
    
    /**
     * Gibt an, ob es der erste Zug dieser Figur ist.
     * @return Wahrheitswert, ob es der erste Zug ist
     */
    public boolean isErsterZug() {
        return ersterZug;
    }

    /**
     * Setzt, ob es der erste Zug dieser Figur ist.
     * @param ersterZug Wahrheitswert, ob es der erste Zug ist
     */
    public void setErsterZug(boolean ersterZug) {
        this.ersterZug = ersterZug;
    }

  
}