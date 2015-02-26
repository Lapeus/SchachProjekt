package figuren;

import gui.Feld;

import java.util.List;

import daten.Spielfeld;

/**
 * Eine Klasse die alle Spielfiguren verwaltet.
 * Die abstrakte Klasse <i>Figur</i> stellt den Bauplan 
 * für s&auml;mtliche Figuren bereit.
 */
public abstract class Figur {

    /**
     * Das Spielbrett, zu dem die Figur geh&ouml;rt.
     */
    private Spielfeld spielfeld;
    
    /**
     * Das Feld, auf dem die Figur momentan steht. <br>
     * Wurde eine Figur geschlagen, wird als Feld <b>null</b> angegeben.
     */
    private Feld position;
    
    /**
     * Die Farbe der Figur. <br>
     * <b>True</b> steht für wei&szlig;, <b>False</b> steht für schwarz.
     */
    private boolean farbe;
    
    /**
     * Der materielle Wert der Figur. <br>
     * Wird ausschlie&szlig;lich für die Bewertungsfunktion des Computergegners
     * ben&ouml;tigt und basiert auf stochastischen Erhebungen. Die Werte sind
     * von den gegebenen Internetquellen &uuml;bernommen.
     */
    private int wert;
    
    /**
     * Gibt an, ob die Figur in diesem Spiel schon einmal gezogen wurde.
     * Dies ist vorallem f&uuml;r die Rochade und f&uuml;r die jeweils ersten
     * Z&uuml;ge der Bauern wichtig.
     */
    private boolean bereitsGezogen;
    
    /**
     * 
     */
    public abstract void praePruefung();
    
    /**
     * Berechnet alle Felder, auf die die Figur nach den
     * eingestellten Regeln ziehen kann.
     * Dabei werden alle nach den Zugregeln zul&auml;ssige Felder ermittelt
     * und anschlie&szlig;end durch eine weitere Methode darauf getestet, ob
     * der K&ouml;nig nun im Schach steht.
     * @return Liste von zul&auml;ssigen Feldern
     */
    public abstract List<Feld> getMoeglicheFelder();
    
    /**
     * Methode, die die &uuml;bergebenen Zielfeld-M&ouml;glichkeiten
     * dahin gehend pr&uuml;ft, ob der eigene K&ouml;nig danach im Schach steht.
     * @param felder : Eine Liste von vorher berechneten m&ouml;glichen 
     * Zielfeldern des Zuges
     */
    public abstract void postPruefung(List<Feld> felder);
    
    /**
     * F&uuml;hrt einen Zug durch und passt alle n&ouml;tigen Listen und Felder
     * an.
     * @param zielfeld : Das Feld, auf das die Figur gezogen werden soll
     */
    public void ziehe(Feld zielfeld) {
        // Hier muss ein neuer Zug erstellt werden
        
        
        
        // Figur wird von der aktuellen Position entfernt
        position.setFigur(null);
        // Wenn auf dem Zielfeld eine Figur steht
        if (zielfeld.getFigur() != null) {
            // Wenn wir selbst eine weiße Figur sind
            if (farbe) {
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
        // Figurs wird auf das Zielfeld gesetzt
        zielfeld.setFigur(this);
        // Die aktuelle Position wird angepasst
        position = zielfeld;
        
        // Der erste Zug von Bauer, Turm und Koenig muessen erfasst werden.
        // Zur Vereinfachung wird dies einfach bei allen Figuren durchgefuerht.
        if (!this.getGezogen()) {
            this.setGezogen(true);
        }
        
    }
    
    /**
     * Gibt an, ob das Feld am angegebenen Index leer ist.
     * @param index : Ganzzahliger Index (zwischen 0 und 63)
     * @return <b>true</b> wenn frei
     */
    protected boolean istFrei(int index) {
        boolean frei = false;
        if (getFigurAt(index) == null) {
            frei = true;
        }
        return frei;
    }
    
    /**
     * Gibt an, ob das Feld am angegebenen Index nicht von einer eigenen Figur
     * besetzt ist.
     * Wenn <b>true</b> zur&uuml;ck gegeben wird, bedeutet das, dass dieses
     * Feld f&uuml;r einen Zug zur Verf&uuml;gung steht.
     * @param index : Ganzzahliger Index (zwischen 0 und 63)
     * @return <b>true</b> f&uuml;r leer oder gegnerische Figur
     */
    protected boolean istMoeglich(int index) {
        boolean moeglich = false;
        if (istFrei(index) || getFigurAt(index).getFarbe() != farbe) {
            moeglich = true;
        }
        return moeglich;
    }
    
    /**
     * Gibt das Feld zur&uuml;ck, auf dem die Figur steht.
     * @return Ein Objekt vom Typ Feld
     */
    public Feld getFeld() {
        return spielfeld.getFelder().get(getFeldIndex());
    }
    
    /**
     * Gibt das Feld mit dem angegebenen Index zur&uuml;ck.
     * @param index : Der ganzzahlige Index (Zwischen 0 und 63)
     * @return Ein Objekt vom Typ Feld
     */
    protected Feld getFeld(int index) {
        return spielfeld.getFelder().get(index);
    }
    
    /**
     * Gibt den Index des Felder zur&uuml;ck, auf dem die Figur steht.
     * @return Der ganzzahlige Index (Zwischen 0 und 63)
     */
    protected int getFeldIndex() {
        return position.getX() + position.getY() * 8;
    }
    
    /**
     * Gibt die Figur auf dem Feld des angegebenen Index' zur&uuml;ck.
     * @param index : Der ganzzahlige Index (Zwischen 0 und 63)
     * @return Eine Figur 
     */
    protected Figur getFigurAt(int index) {
        return getFeld().getFigur();
    }
    
    /**
     * Gibt das Spielfeld zur&uuml;ck, auf dem die Figur steht.
     * @return Das Spielfeld
     */
    public Spielfeld getSpielfeld() {
        return spielfeld;
    }
    
    /**
     * Setzt das Spielfeld, auf dem die Figur steht.
     * @param spielfeld : Das zu setzende Spielfeld
     */
    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }
    
    /**
     * Gibt das Feld zur&uuml;ck, auf dem die Figur steht.
     * @return Das Feld
     */
    public Feld getPosition() {
        return position;
    }
    
    /**
     * Setzt das Feld, auf dem die Figur steht.
     * @param position : Das Feld, auf dem die Figur steht
     */
    public void setPosition(Feld position) {
        this.position = position;
    }
    
    /**
     * Gibt an, ob die Figur wei&szlig; oder schwarz ist.
     * @return <b>true</b> f&uuml;r wei&szlig;, <b>false</b> f&uuml;r schwarz
     */
    public boolean getFarbe() {
        return farbe;
    }
    
    /**
     * Setzt die Farbe der Figur.
     * @param farbe : <b>true</b> f&uuml;r wei&szlig;, <b>false</b> 
     * f&uuml;r schwarz
     */
    public void setFarbe(boolean farbe) {
        this.farbe = farbe;
    }
    
    /**
     * Gibt den Wert der Figur zur&uuml;ck.
     * @return Ganzzahliger Wert (Zwischen 0 und 900)
     */
    public int getWert() {
        return wert;
    }
    
    /**
     * Setzt den Wert der Figur.
     * @param wert Der ganzzahlige Wert der Figur (zwischen 0 und 900)
     */
    public void setWert(int wert) {
        this.wert = wert;
    }
    
    /**
     * Gibt an, ob die Figur bereits gezogen wurde.
     * @return Wahrheitswert
     */
    protected boolean getGezogen() {
        return bereitsGezogen;
    }
    
    /**
     * Setzt die Variable, ob die Figur schon gezogen wurde.
     * @param bereitsGezogen : Wahrheitswert
     */
    protected void setGezogen(boolean bereitsGezogen) {
        this.bereitsGezogen = bereitsGezogen;
    }
 
}