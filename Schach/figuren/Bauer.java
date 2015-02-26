package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

/**
 * Der Bauplan für die Spielfigur Bauer. <br>
 * Besitzt haupts&auml;chlich die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 */
public class Bauer extends Figur {
    
    /**
     * Erzeugt einen neuen Bauern.
     * Einziger Konstruktor dieser Klasse.
     * @param position : Das Feld auf dem die Figur stehen soll
     * @param farbe : Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */
    public Bauer(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(100);
        super.setGezogen(false);
    }
    
    /**
     * {@inheritDoc}
     */
    public void praePruefung() {
        // TODO Auto-generated method stub
        
    }

    /**
     * {@inheritDoc}
     */
    public List<Feld> getMoeglicheFelder() {
        List<Feld> moeglicheFelder = new ArrayList<Feld>();
        // Indizes fuer weiss
        int[] indizes = {8, 16, 7, 9};
        // Abfrage ob weiss oder schwarz
        if (!super.getFarbe()) {
            // Indizes fuer schwarz
            indizes[0] = -8;
            indizes[1] = -16;
            indizes[2] = -9; // Getauscht, weil sie ja entgegengesetzt laufen
            indizes[3] = -7; //   ""           ""          ""           ""
        }
        // Wenn an der jetzigen Stelle plus 8 (vorne) keine Figur steht
        if (super.getFigurAt(getFeldIndex() + indizes[0]) == null) {
            // Fuege das entsprechende Feld der Liste zu
            moeglicheFelder.add(super.getFeld(getFeldIndex() + indizes[0]));
        }
        
        // Wenn die Figur noch nicht bewegt wurde
        if (!super.getGezogen()) {
            // Wenn an der jetzigen Stelle plus 16 (2 vorne) keine Figur steht
            if (super.getFigurAt(getFeldIndex() + indizes[1]) == null) {
                // Fuege das entsprechende Feld der Liste zu
                moeglicheFelder.add(super.getFeld(getFeldIndex() + indizes[1]));
            }
        }
        
        // Wenn links noch ein Feld ist
        if (super.getPosition().getX() > 0) {
            // Wenn schraeg links vorne eine gegnerische Figur steht
            if (super.getFigurAt(getFeldIndex() + indizes[2]) != null 
                && super.getFigurAt(getFeldIndex() + indizes[2]).getFarbe() 
                != super.getFarbe()) {
                // Fuege das entsprechende Feld der Liste zu
                moeglicheFelder.add(super.getFeld(getFeldIndex() + indizes[2]));
            }
        }
        
        // Wenn rechts noch ein Feld ist
        if (super.getPosition().getX() < 7) {
            // Wenn schraeg rechts vorne eine gegnerische Figur steht
            if (super.getFigurAt(getFeldIndex() + indizes[3]) != null 
                && super.getFigurAt(getFeldIndex() + indizes[3]).getFarbe() 
                != super.getFarbe()) {
                // Fuege das entsprechende Feld der Liste zu
                moeglicheFelder.add(super.getFeld(getFeldIndex() + indizes[3]));
            }
        }
        
        // Gibt die Liste der moeglichen Felder zurueck
        return moeglicheFelder;
    }

    /**
     * {@inheritDoc}
     */
    public void postPruefung(List<Feld> felder) {
        // TODO Auto-generated method stub
        
    }
   
    
    

}
