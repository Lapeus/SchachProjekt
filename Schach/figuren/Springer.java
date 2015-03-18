package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

/**
 * Der Bauplan fuer die Spielfigur Springer. <br>
 * Besitzt haupts&auml;chlich die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 * @author Christian Ackermann
 * @see Figur
 */
public class Springer extends Figur {

    /**
     * Erzeugt einen neuen Springer.
     * Einziger Konstruktor dieser Klasse.
     * @param position Das Feld auf dem die Figur stehen soll
     * @param farbe Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */
    public Springer(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(275);
    }

    /**
     * Ermittelt alle m&ouml;glichen Felder, auf die ein Springer ziehen darf.
     * Sofern keine Figuren auf den Zielfeldern stehen oder der Spielfeldrand
     * dabei erreicht wird, sind das acht verschiedene Felder. Diese werden
     * einer Liste zugef&uuml;gt und anschlie&szlig;end zur&uuml;ck gegeben.
     * @return Die Liste der m&ouml;glichen Felder
     */
    protected List<Feld> getMoeglicheFelder() {
        List<Feld> moeglicheFelder = new ArrayList<Feld>();
        /*<Gehe alle Felder durch und schaue ob sie existieren und
         *nicht von eigenen Figuren besetzt sind.>
         */
        // Die Abweichung der Indizes der umliegenden Felder zum aktuellen Feld
        int[] indizes = {-17, -15, -10, -6, 6, 10, 15, 17};
        // Fuer alle Felder
        for (int i : indizes) {
            /* Wenn der Index zwischen 0 und 63 liegt und das Feld
             * besetzt werden darf
             */
            if (super.getFeldIndex() + i >= 0 
                && super.getFeldIndex() + i < 64
                && super.istMoeglich(super.getFeldIndex() + i)) {
                boolean zulaessig = false;
                if (i == -10 || i == 6) {
                    // Wenn die Figur zwei Felder nach links geht
                    if (super.getPosition().getXK() > 1) {
                        zulaessig = true;
                    }
                } else if (i == -17 || i == 15) {
                    // Wenn die Figur ein Feld nach links geht
                    if (super.getPosition().getXK() > 0) {
                        zulaessig = true;
                    }
                } else if (i == -15 || i == 17) {
                    // Wenn die Figur ein Feld nach rechts geht
                    if (super.getPosition().getXK() < 7) {
                        zulaessig = true;
                    }
                } else if (i == -6 || i == 10) {
                    // Wenn die Figur zwei Felder nach rechts geht
                    if (super.getPosition().getXK() < 6) {
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


}
