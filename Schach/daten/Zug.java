package daten;

import figuren.Figur;
import gui.Feld;

public class Zug {

    private Feld startfeld;
    private Feld zielfeld;
    private Figur figur;
    private boolean schlagzug;
    private int zugzeit;
    private boolean ersterZug;
    private boolean umwandlung;
    
    /**
     * Leerer Konstrukor um Sonderz&uuml;ge anlegen zu k&ouml;nnen.
     */
    public Zug() {

    }
    
    public Zug(Feld startfeld, Feld zielfeld, Figur figur, boolean schlagzug
        , int zugzeit, boolean ersterZug, boolean umwandlung) {
        this.startfeld = startfeld;
        this.zielfeld = zielfeld;
        this.figur = figur;
        this.schlagzug = schlagzug;
        this.zugzeit = zugzeit;
        this.ersterZug = ersterZug;
        this.umwandlung = umwandlung;
    }
    
    public Feld getStartfeld() {
        return startfeld;
    }
    
    public Feld getZielfeld() {
        return zielfeld;
    }
    
    public Figur getFigur() {
        return figur;
    }
    
    public int getZugzeit() {
        return zugzeit;
    }
    
    public void setZugzeit(int zugzeit) {
        this.zugzeit = zugzeit;
    }
    
    public boolean isSchlagzug() {
        return schlagzug;
    }
    public void setSchlagzug(boolean schlagzug) {
        this.schlagzug = schlagzug;
    }

    public boolean isErsterZug() {
        return ersterZug;
    }

    public void setErsterZug(boolean ersterZug) {
        this.ersterZug = ersterZug;
    }

    public boolean isUmwandlung() {
        return umwandlung;
    }

    public void setUmwandlung(boolean umwandlung) {
        this.umwandlung = umwandlung;
    }
}