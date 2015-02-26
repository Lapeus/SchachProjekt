package figuren;

import gui.Feld;

import java.util.List;

/**
 * Der Bauplan für die Spielfigur K&ouml;nig. <br>
 * Besitzt haupts&auml;chlich die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 */
public class Koenig extends Figur {

    /**
     * Erzeugt einen neuen K&ouml;nig.
     * Einziger Konstruktor dieser Klasse.
     * @param position : Das Feld auf dem die Figur stehen soll
     * @param farbe : Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */
    public Koenig(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(0); // Wert des Koenigs ist irrelevant
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void postPruefung(List<Feld> felder) {
        // TODO Auto-generated method stub
        
    }
    

}
