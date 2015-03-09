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
    
    public EnPassantZug(Feld startfeld, Feld zielfeld, int zugzeit) {
        this.startfeld = startfeld;
    }
    /**
     * {@inheritDoc}
     */
    public String toSchachNotation() {
        String string = "";
        Feld schlagfeld = geschlagenderBauer.getPosition();
        // Spaltenbezeichnung
        String[] spalten = {"a", "b", "c", "d", "e", "f", "g", "h"};
        // Das Startfeld
        string += spalten[startfeld.getXK()] + (startfeld.getYK() + 1);
        // Ein Bauer wird geschlagen
        string += "x";
        // Das Zielfeld
        // Wenn der geschlagene Bauer weiss ist
        if (geschlagenderBauer.getFarbe()) {
            // Ist das Zielfeld Y-1 plus die Index-Korrektur von 1
            string += spalten[schlagfeld.getXK()] + (schlagfeld.getYK());
        // Wenn der geschlagene Bauer schwarz ist
        } else {
            // Ist das Zielfeld Y+1 plus die Index-Korrektur von 1
            string += spalten[schlagfeld.getXK()] + (schlagfeld.getYK() + 2);
        }
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
        if (ausfuehrenderBauer == null) {
            return startfeld.getFigur();
        } else {
            return ausfuehrenderBauer;
        }
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
        if (geschlagenderBauer == null) {
            if (startfeld.getYK() == 4) {
                
            }
        }
        return geschlagenderBauer;
    }
  
}
