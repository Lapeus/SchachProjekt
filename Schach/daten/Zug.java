package daten;

import figuren.Figur;
import gui.Feld;

public class Zug {

    private Feld startfeld;
    private Feld zielfeld;
    private Figur figur;
    private boolean schlagzug;
    private int zugzeit;
    private static int zugZaehler;
    
    public Zug(Feld startfeld, Feld zielfeld, Figur figur, boolean schlagzug
        , int zugzeit) {
        this.startfeld = startfeld;
        this.zielfeld = zielfeld;
        this.figur = figur;
        this.schlagzug = schlagzug;
        this.zugzeit = zugzeit;
        zugZaehler++;
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
    
    public int getZugZaehler() {
        return zugZaehler;
    }
    public boolean isSchlagzug() {
        return schlagzug;
    }
    public void setSchlagzug(boolean schlagzug) {
        this.schlagzug = schlagzug;
    }
}