package zuege;

import figuren.Figur;
import gui.Feld;

/**
 * Ist ein Sonderzug, der das En-Passant-Schlagen aufzeichnet. <br>
 * Erbt von Zug, muss dieser Klasse nur die Dauer des Zuges &uuml;bergeben.
 * @author Christian Ackermann
 */
public class EnPassantZug extends Zug {

    /**
     * Der ziehende Bauer.
     */
    private Figur ausfuehrenderBauer;
    
    /**
     * Das Startfeld des ziehenden Bauern.
     */
    private Feld startfeld;
    
    /**
     * Das Zielfeld des ziehenden Bauern.
     */
    private Feld zielfeld;
    
    /**
     * Der geschlagene Bauer.
     */
    private Figur geschlagenderBauer;
    
    /**
     * Erstellt einen neuen En-Passant-Zug. <br>
     * Einziger Konstruktor dieser Klasse.
     * @param ausfuehrenderBauer Der ziehende Bauer
     * @param startfeld Das Startfeld des ziehenden Bauern
     * @param zielfeld Das Zielfeld des ziehenden Bauern
     * @param geschlagenerBauer Der geschlagene Bauer
     * @param zugzeit Die Dauer des Zuges in ganzen Sekunden
     */
    public EnPassantZug(Figur ausfuehrenderBauer, Feld startfeld, 
        Feld zielfeld, Figur geschlagenerBauer, int zugzeit) {
        this.ausfuehrenderBauer = ausfuehrenderBauer;
        this.startfeld = startfeld;
        this.zielfeld = zielfeld;
        this.geschlagenderBauer = geschlagenerBauer;
        setZugzeit(zugzeit);
    }
    
    /**
     * {@inheritDoc}
     */
    public String toSchachNotation() {
        String string = "";
        // Spaltenbezeichnung
        String[] spalten = {"a", "b", "c", "d", "e", "f", "g", "h"};
        // Das Startfeld
        string += spalten[startfeld.getXK()] + (startfeld.getYK() + 1);
        // Ein Bauer wird geschlagen
        string += "x";
        // Das Zielfeld
        string += spalten[zielfeld.getXK()] + (zielfeld.getYK() + 1);
        string += " e.p.";
        // Die Zugzeit 
        string += " " + getZugzeit() + " sek";
        
        return string;
    }
    
    /**
     * Gibt den ziehenden Bauern zur&uuml;ck.
     * @return Der ziehende Bauer
     */
    public Figur getFigur() {
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
     * Gibt das Zielfeld des ziehenden Bauern zur&uuml;ck.
     * @return Das Zielfeld des ziehenden Bauern
     */
    public Feld getZielfeld() {
        return zielfeld;
    }

    /**
     * Gibt den geschlagenen Bauern zur&uuml;ck.
     * @return Der geschlagene Bauer
     */
    public Figur getGeschlagenen() {
        return geschlagenderBauer;
    }
  
}
