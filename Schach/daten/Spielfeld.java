package daten;

import java.util.ArrayList;
import java.util.List;

import figuren.Bauer;
import figuren.Dame;
import figuren.Figur;
import figuren.Koenig;
import figuren.Laeufer;
import figuren.Springer;
import figuren.Turm;
import gui.Feld;

public class Spielfeld {

    private List<Feld> felder = new ArrayList<Feld>();
    private List<Figur> schwarzeFiguren = new ArrayList<Figur>();
    private List<Figur> weisseFiguren = new ArrayList<Figur>();
    private List<Figur> geschlagenWeiss = new ArrayList<Figur>();
    private List<Figur> geschlagenSchwarz = new ArrayList<Figur>();
    private boolean richtigAusgerichtet = true;
    
    public Spielfeld(List<Feld> felder) {
        // Liste mit den Feldern wurde uebergeben
        this.felder = felder;
        
        // Acht schwarze Bauern
        for (int i = 1; i <= 8; i++) {
            // Erstellen
            Bauer bauer = new Bauer(felder.get(47+i), false);
            // Der Liste hinzufuegen
            schwarzeFiguren.add(bauer);
            // Dem Feld die Figur hinzufuegen
            felder.get(47+i).setFigur(bauer);
        }
        // Acht weisse Bauern
        for (int i = 1; i <= 8; i++) {
            // Erstellen
            Bauer bauer = new Bauer(felder.get(7+i), true);
            // Der Liste hinzufuegen
            weisseFiguren.add(bauer);
            // Dem Feld die Figur hinzufuegen
            felder.get(7+i).setFigur(bauer);
        }
        // Zwei schwarze Springer erstellen
        Springer springer = new Springer(felder.get(57), false);
        schwarzeFiguren.add(springer);
        felder.get(57).setFigur(springer);
        springer = new Springer(felder.get(62), false);
        schwarzeFiguren.add(springer);
        felder.get(62).setFigur(springer);
        // Zwei weisse Springer erstellen
        springer = new Springer(felder.get(1), true);
        weisseFiguren.add(springer);
        felder.get(1).setFigur(springer);
        springer = new Springer(felder.get(6), true);
        weisseFiguren.add(springer);
        felder.get(6).setFigur(springer);
        // Zwei schwarze Laeufer erstellen
        Laeufer laeufer = new Laeufer(felder.get(58), false);
        schwarzeFiguren.add(laeufer);
        felder.get(58).setFigur(laeufer);
        laeufer = new Laeufer(felder.get(61), false);
        schwarzeFiguren.add(laeufer);
        felder.get(61).setFigur(laeufer);
        // Zwei weisse Laeufer erstellen
        laeufer = new Laeufer(felder.get(2), true);
        weisseFiguren.add(laeufer);
        felder.get(2).setFigur(laeufer);
        laeufer = new Laeufer(felder.get(5), true);
        weisseFiguren.add(laeufer);
        felder.get(5).setFigur(laeufer);
        // Zwei schwarze Tuerme erstellen
        Turm turm = new Turm(felder.get(56), false);
        schwarzeFiguren.add(turm);
        felder.get(56).setFigur(turm);
        turm = new Turm(felder.get(63), false);
        schwarzeFiguren.add(turm);
        felder.get(63).setFigur(turm);
        // Zwei weisse Tuerme erstellen
        turm = new Turm(felder.get(0), true);
        weisseFiguren.add(turm);
        felder.get(0).setFigur(turm);
        turm = new Turm(felder.get(7), true);
        weisseFiguren.add(turm);
        felder.get(7).setFigur(turm);
        // Eine schwarze Dame erstellen
        Dame dame = new Dame(felder.get(59), false);
        schwarzeFiguren.add(dame);
        felder.get(59).setFigur(dame);
        // Eine weisse Dame erstellen
        dame = new Dame(felder.get(3), true);
        weisseFiguren.add(dame);
        felder.get(3).setFigur(dame);
        // Einen schwarzen Koenig erstellen
        Koenig koenig = new Koenig(felder.get(60), false);
        schwarzeFiguren.add(koenig);
        felder.get(60).setFigur(koenig);
        // Einen weissen Koenig erstellen
        koenig = new Koenig(felder.get(4), false);
        weisseFiguren.add(koenig);
        felder.get(4).setFigur(koenig);
        
    }
    
    public List<Figur> getSchwarzeFiguren() {
        return schwarzeFiguren;
    }
    
    public List<Figur> getWeisseFiguren() {
        return weisseFiguren;
    }
    
    public List<Figur> getGeschlagenSchwarz() {
        return geschlagenSchwarz;
    }
    
    public List<Figur> getGeschlagenWeiss() {
        return geschlagenWeiss;
    }
    
    
    
    
}