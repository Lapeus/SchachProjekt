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
        // Wenn an der jetzigen Stelle plus 8 (vorne) keine Figur steht
        if (super.getFigurAt(getFeldIndex() + 8) == null) {
            // Fuege das entsprechende Feld der Liste zu
            moeglicheFelder.add(super.getFeld(getFeldIndex() + 8));
        }
        
        // Wenn die Figur noch nicht bewegt wurde
        if (!super.getGezogen()) {
            // Wenn an der jetzigen Stelle plus 16 (2 vorne) keine Figur steht
            if (super.getFigurAt(getFeldIndex() + 16) == null) {
                // Fuege das entsprechende Feld der Liste zu
                moeglicheFelder.add(super.getFeld(getFeldIndex() + 16));
            }
        }
        
        // Wenn links noch ein Feld ist
        if (super.getPosition().getX() > 0) {
            // Wenn schraeg links vorne eine gegnerische Figur steht
            if (super.getFigurAt(getFeldIndex() + 7) != null 
                || super.getFigurAt(getFeldIndex() + 7).getFarbe() 
                != super.getFarbe()) {
                // Fuege das entsprechende Feld der Liste zu
                moeglicheFelder.add(super.getFeld(getFeldIndex() + 7));
            }
        }
        
        // Wenn rechts noch ein Feld ist
        if (super.getPosition().getX() < 7) {
            // Wenn schraeg rechts vorne eine gegnerische Figur steht
            if (super.getFigurAt(getFeldIndex() + 9) != null 
                || super.getFigurAt(getFeldIndex() + 9).getFarbe() 
                != super.getFarbe()) {
                // Fuege das entsprechende Feld der Liste zu
                moeglicheFelder.add(super.getFeld(getFeldIndex() + 9));
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
