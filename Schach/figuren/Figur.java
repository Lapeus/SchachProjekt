package figuren;

import gui.Feld;

import java.util.List;

import daten.Spielfeld;

public abstract class Figur {

    private Spielfeld spielfeld;
    private Feld position;
    private boolean farbe; //true für weiss, false für schwarz
    private int wert;
    
    public abstract void praePruefung();
    public abstract List<Feld> getMoeglicheFelder();
    public abstract void postPruefung();
    
    public void ziehe(Feld zielfeld) {
        // Figur wird von der aktuellen Position entfernt
        position.setFigur(null);
        // Wenn auf dem Zielfeld eine Figur steht
        if (zielfeld.getFigur() != null) {
            // Wenn wir selbst eine weiße Figur sind
            if (farbe == true) {
                // schlagen wir eine schwarze Figur
                spielfeld.getSchwarzeFiguren().remove(zielfeld.getFigur());
                spielfeld.getGeschlagenSchwarz().add(zielfeld.getFigur());
            // Wenn wir selbst eine schwarze Figur sind
            } else {
                // schlagen wir eine weiße Figur
                spielfeld.getWeisseFiguren().remove(zielfeld.getFigur());
                spielfeld.getGeschlagenWeiss().add(zielfeld.getFigur());
            }
            
        }
        // Figur wird auf das Zielfeld gesetzt
        zielfeld.setFigur(this);
        // Die aktuelle Position wird angepasst
        position = zielfeld;
        
        // Hier muss ein neuer Zug erstellt werden
    }
    public Spielfeld getSpielfeld() {
        return spielfeld;
    }
    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }
    public Feld getPosition() {
        return position;
    }
    public void setPosition(Feld position) {
        this.position = position;
    }
    public boolean getFarbe() {
        return farbe;
    }
    public void setFarbe(boolean farbe) {
        this.farbe = farbe;
    }
    public int getWert() {
        return wert;
    }
    public void setWert(int wert) {
        this.wert = wert;
    }
 
}