package daten;

import figuren.Figur;
import gui.Feld;

/**
 * Ist ein Sonderzug, den das En-Passant-Schlagen aufzeichnet. <br>
 * Erbt von Zug, muss dieser Klasse nur die Dauer des Zuges &uuml;bergeben.
 * @author Christian Ackermann
 */
public class EnPassantZug extends Zug {

    /**
     * Der ziehende Bauer.
     */
    private Figur ausfuehrenderBauer;
    
    /**
     * Das Startfeld des ziehenden Bauers.
     */
    private Feld startfeld;
    
    /**
     * Der geschlagene Bauer.
     */
    private Figur geschlagenderBauer;
    
    /**
     * Erstellt einen neuen En-Passant-Zug. <br>
     * Einziger Konstruktor dieser Klasse.
     * @param ausfuehrenderBauer Der ziehende Bauer
     * @param startfeld Das Startfeld des ziehenden Bauers
     * @param geschlagenerBauer Der geschlagene Bauer
     * @param zugzeit Die Dauer des Zuges in ganzen Sekunden
     */
    public EnPassantZug(Figur ausfuehrenderBauer, Feld startfeld
        , Figur geschlagenerBauer, int zugzeit) {
        this.ausfuehrenderBauer = ausfuehrenderBauer;
        this.startfeld = startfeld;
        this.geschlagenderBauer = geschlagenerBauer;
        setZugzeit(zugzeit);
    }
    
    /**
     * {@inheritDoc}
     */
    public String toString() {
        String string = "";
        Feld schlagfeld = geschlagenderBauer.getPosition();
        // Spaltenbezeichnung
        String[] spalten = {"a", "b", "c", "d", "e", "f", "g", "h"};
        // Das Startfeld
        string += spalten[startfeld.getXK()] + startfeld.getYK();
        // Ein Bauer wird geschlagen
        string += "x";
        // Das Zielfeld
        string += spalten[schlagfeld.getXK()] + (schlagfeld.getYK() + 1);
        string += " e.p.";
        // Die Zugzeit 
        string += " " + getZugzeit() + " sek";
        
        return string;
    }
    
    /**
     * Gibt den ziehenden Bauern zur&uuml;ck.
     * @return Der ziehende Bauer
     */
    public Figur getAusfuehrer() {
        return ausfuehrenderBauer;
    }
    
    /**
     * Gibt das Startfeld des ziehenden Bauern zur&uuml;ck.
     * @return Das Startfeld des ziehenden Bauern
     */
    public Feld getStartfeld() {
        return startfeld;
    }
    
    /**
     * Gibt den geschlagenen Bauern zur&uuml;ck.
     * @return Der geschlagene Bauer
     */
    public Figur getGeschlagenen() {
        return geschlagenderBauer;
    }
  
}
