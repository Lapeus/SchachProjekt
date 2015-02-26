package figuren;

import gui.Feld;

import java.util.ArrayList;
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
        List<Feld> moeglicheFelder = new ArrayList<Feld>();
        /*<Gehe alle Felder ringsherum durch und schaue ob sie existieren und
         *nicht von eigenen Figuren besetzt sind.>
         */
        // Die Abweichung der Indizes der umliegenden Felder zum aktuellen Feld
        int[] indizes = {-9, -8, -7, -1, 1, 7, 8, 9};
        // Fuer alle umliegenden Felder
        for (int i : indizes) {
            /* Wenn der Index zwischen 0 und 63 liegt und das Feld
             * besetzt werden darf
             */
            if (super.getFeldIndex() + i >= 0 
                && super.getFeldIndex() + i < 64
                && super.istMoeglich(super.getFeldIndex() + i)) {
                boolean zulaessig = false;
                if (i == -8 || i == 8) {
                    // Nach vorne und hinten klappt ohne weitere Pruefung
                    zulaessig = true;
                } else if (i == -9 || i == -1 || i == 7) {
                    // Nach links muss auf Rand getestet werden
                    if (super.getPosition().getX() > 0) {
                        zulaessig = true;
                    }
                } else if (i == -7 || i == 1 || i == 9) {
                    // Nach rechts muss auf Rand getestet werden
                    if (super.getPosition().getX() < 7) {
                        zulaessig = true;
                    }
                }
                if (zulaessig) {
                    moeglicheFelder.add(super.getFeld(
                        super.getFeldIndex() + i));
                }
             
            }
        }
        return moeglicheFelder;
    }

    /**
     * {@inheritDoc}
     */
    public void postPruefung(List<Feld> felder) {
        // TODO Auto-generated method stub
        
    }
    

}
