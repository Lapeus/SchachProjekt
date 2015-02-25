package daten;

import gui.Feld;

public class Zug {

    private Feld startfeld;
    private Feld zielfeld;
    private Spieler spieler;
    private int zugzeit;
    private static int zugZaehler;
    
    
    public Feld getStartfeld() {
        return startfeld;
    }
    
    public Feld getZielfeld() {
        return zielfeld;
    }
    
    public Spieler getSpieler() {
        return spieler;
    }
    
    public int getZugzeit() {
        return zugzeit;
    }
    
    public int getZugZaehler() {
        return zugZaehler;
    }
}