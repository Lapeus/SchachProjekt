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

/**
 * Verwaltet die Figuren und ihre Position auf dem Brett.
 * Gibt zus&auml;tzlich an, ob das Brett aktuell aus wei&szlig;er oder aus
 * schwarzer Sicht gezeigt wird.
 */
public class Spielfeld {

    /**
     * Eine Liste mit allen 64 Felder des Spielbrettes. <br>
     * Index 0 entspricht dem Feld links unten, Index 63 dem Feld rechts oben.
     */
    private List<Feld> felder = new ArrayList<Feld>();
    
    /**
     * Eine Liste mit allen schwarzen Figuren, die sich noch im Spiel befinden,
     * nach aufsteigendem Wert sortiert.
     */
    private List<Figur> schwarzeFiguren = new ArrayList<Figur>();
    
    /**
     * Eine Liste mit allen wei&szlig;en Figuren, die sich noch im Spiel 
     * befinden, nach aufsteigendem Wert sortiert.
     */
    private List<Figur> weisseFiguren = new ArrayList<Figur>();
    
    /**
     * Eine Liste mit allen geschlagenen schwarzen Figuren, nach absteigendem
     * Wert sortiert.
     */
    private List<Figur> geschlagenSchwarz = new ArrayList<Figur>();
    
    /**
     * Eine Liste mit allen geschlagenen wei&szlig;en Figuren, nach absteigendem
     * Wert sortiert.
     */
    private List<Figur> geschlagenWeiss = new ArrayList<Figur>();
    
    /**
     * Gibt an, von welcher Person aus das Brett aufgebaut ist. <br>
     * <b>true</b> f&uuml;r wei&szlig;, <b>false</b> f&uuml;r schwarz
     */
    private boolean richtigAusgerichtet = true;
    
    /**
     * Erzeugt ein neues Spielfeld und stellt die Figuren an die richtige
     * Position.
     * @param felder : Die Liste mit den Feldern, auf die die Figuren gestellt
     * werden sollen
     */
    public Spielfeld(List<Feld> felder) {
        // Liste mit den Feldern wurde uebergeben
        this.felder = felder;
        
        // Acht schwarze Bauern
        for (int i = 1; i <= 8; i++) {
            // Erstellen
            Bauer bauer = new Bauer(felder.get(47 + i), false);
            // Der Liste hinzufuegen
            schwarzeFiguren.add(bauer);
            // Dem Feld die Figur hinzufuegen
            felder.get(47 + i).setFigur(bauer);
        }
        // Acht weisse Bauern
        for (int i = 1; i <= 8; i++) {
            // Erstellen
            Bauer bauer = new Bauer(felder.get(7 + i), true);
            // Der Liste hinzufuegen
            weisseFiguren.add(bauer);
            // Dem Feld die Figur hinzufuegen
            felder.get(7 + i).setFigur(bauer);
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
    
    /**
     * Gibt die Liste der Felder zur&uuml;ck.
     * @return Liste von Feldern
     */
    public List<Feld> getFelder() {
        return felder;
    }
    
    /**
     * Gibt die schwarzen noch im Spiel befindlichen Figuren zur&uuml;ck.
     * @return Liste von schwarzen Figuren
     */
    public List<Figur> getSchwarzeFiguren() {
        return schwarzeFiguren;
    }
    
    /**
     * Gibt die wei&szlig;en noch im Spiel befindlichen Figuren zur&uuml;ck.
     * @return Liste von wei&szlig;en Figuren
     */
    public List<Figur> getWeisseFiguren() {
        return weisseFiguren;
    }
    
    /**
     * Gibt die geschlagenen schwarzen Figuren zur&uuml;ck.
     * @return Liste von schwarzen Figuren
     */
    public List<Figur> getGeschlagenSchwarz() {
        return geschlagenSchwarz;
    }
    
    /**
     * Gibt die geschlagenen wei&szlig;en Figuren zur&uuml;ck.
     * @return Liste von wei&szlig;en Figuren
     */
    public List<Figur> getGeschlagenWeiss() {
        return geschlagenWeiss;
    }
    
    /**
     * Gibt an, ob das Brett momentan von Wei&szlig; oder von Schwarz aus 
     * gesehen wird, beziehungsweise wer am Zug ist.
     * @return <b>true</b> f&uuml;r wei&szlig;, <b>false</b> f&uuml;r schwarz
     */
    public boolean getAusrichtung() {
        return richtigAusgerichtet;
    }
    
    
}