package daten;

import figuren.Figur;
import gui.Feld;

public class Zug {

    private Feld startfeld;
    private Feld zielfeld;
    private Figur figur;
    private Spieler spieler;
    private int zugzeit;
    private static int zugZaehler;
    
    public Zug(Feld startfeld, Feld zielfeld, Figur figur, Spieler spieler,
        int zugzeit) {
        this.startfeld = startfeld;
        this.zielfeld = zielfeld;
        this.figur = figur;
        this.spieler = spieler;
        this.zugzeit = zugzeit;
        zugZaehler++;
    }
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