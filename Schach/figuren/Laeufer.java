package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

/**
 * Der Bauplan für die Spielfigur L&auml;ufer. <br>
 * Besitzt haupts&auml;chlich die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 * @author Christian Ackermann
 */
public class Laeufer extends Figur {

    /**
     * Erzeugt einen neuen L&auml;ufer.
     * Einziger Konstruktor dieser Klasse.
     * @param position : Das Feld auf dem die Figur stehen soll
     * @param farbe : Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */

    public Laeufer(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(325);
    }

    /**
     * {@inheritDoc}
     */
    protected List<Feld> getMoeglicheFelder() {
        List<Feld> moeglicheFelder = new ArrayList<Feld>();
        /*<Gehe alle diagonalen Felder durch und schaue ob 
         *sie existieren und nicht von eigenen Figuren besetzt sind.>
         */
        // Die Abweichung der Indizes der umliegenden Felder zum aktuellen Feld
        int[] indizes = {-9, -7, 7, 9};
        // Fuer alle Felder
        for (int i : indizes) {
            // Hilfsvariablen
            int zaehl = 1;
            int newIndex = i;
            boolean keinZeilenumbruch = true;
            // Berechnung des Index' des neuen Feldes
            int index = super.getFeldIndex() + (i * zaehl);
            // 0 <= index <= 63 sowie die restlichen Bedingungen (s.While)
            boolean bedingung = (index >= 0 && index < 64) 
                && super.istFrei(index) && keinZeilenumbruch;
            /* Solange das naechste Feld frei ist und der Rand nicht erreicht
             * ist
             */ 
            while (bedingung) {
                /* Wenn der Index zwischen 0 und 63 liegt und das Feld
                 * besetzt werden darf
                 */
                if (super.getFeldIndex() + newIndex >= 0 
                    && super.getFeldIndex() + newIndex < 64) {
                    boolean zulaessig = false;
                    if (i == -9 || i == 7) {
                        // Auf linken Rand pruefen
                        if (super.getPosition().getXK() > 0) {
                            zulaessig = true;
                        } else {
                            // Ende der Zeile erreicht
                            keinZeilenumbruch = false;
                        }
                    } else if (i == -7 || i == 9) {
                        // Auf rechten Rand pruefen
                        if (super.getPosition().getXK() < 7) {
                            zulaessig = true;
                        } else {
                            // Ende der Zeile erreicht
                            keinZeilenumbruch = false;
                        }
                    }
                    
                    if (zulaessig) {
                        moeglicheFelder.add(super.getFeld(
                            super.getFeldIndex() + newIndex));
                    }
                }
                zaehl++;
                newIndex = i * zaehl;
                // Aktualisierung der Index- und While-Bedingung
                index = super.getFeldIndex() + (i * zaehl);
                bedingung = (index >= 0 && index < 64) 
                    && super.istFrei(index) && keinZeilenumbruch;
            }
            
            // Wenn das naechste Feld nicht mehr frei ist
            /* Wenn der Index zwischen 0 und 63 liegt und das Feld
             * besetzt werden darf
             */
            if (super.getFeldIndex() + newIndex >= 0 
                && super.getFeldIndex() + newIndex < 64
                && super.istMoeglich(super.getFeldIndex() + newIndex)) {
                boolean zulaessig = false;
                if (i == -9 || i == 7) {
                    // Auf linken Rand pruefen
                    if (super.getPosition().getXK() > 0) {
                        zulaessig = true;
                    } else {
                        // Ende der Zeile erreicht
                        keinZeilenumbruch = false;
                    }
                } else if (i == -7 || i == 9) {
                    // Auf rechten Rand pruefen
                    if (super.getPosition().getXK() < 7) {
                        zulaessig = true;
                    } else {
                        // Ende der Zeile erreicht
                        keinZeilenumbruch = false;
                    }
                }
                if (zulaessig) {
                    moeglicheFelder.add(super.getFeld(
                        super.getFeldIndex() + newIndex));
                }
            }
        }
        
        return moeglicheFelder;
    }


}
