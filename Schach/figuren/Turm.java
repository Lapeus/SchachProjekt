package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

/**
 * Der Bauplan für die Spielfigur Turm. <br>
 * Besitzt haupts&auml;chlich die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 * @author Christian Ackermann
 */
public class Turm extends Figur {

    /**
     * Erzeugt einen neuen Turm.
     * Einziger Konstruktor dieser Klasse.
     * @param position : Das Feld auf dem die Figur stehen soll
     * @param farbe : Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */
    public Turm(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(465);
        super.setGezogen(false);
    }

    /**
     * {@inheritDoc}
     */
    protected List<Feld> getMoeglicheFelder() {
        List<Feld> moeglicheFelder = new ArrayList<Feld>();
        /*<Gehe alle horizontalen und vertikalen Felder durch und schaue ob 
         *sie existieren und nicht von eigenen Figuren besetzt sind.>
         */
        // Die Abweichung der Indizes der umliegenden Felder zum aktuellen Feld
        int[] indizes = {-8, -1, 1, 8};
        // Fuer alle Felder
        for (int i : indizes) {
            // Hilfsvariablen
            int zaehl = 1;
            int newIndex = i;
            boolean keinZeilenumbruch = true;
            // Berechnung des Index' des neuen Feldes
            int index = super.getFeldIndex() + (i * zaehl);
            // Pruefung auf Rand sowie die restlichen Bedingungen (s.While)
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
                    if (i == -1) {
                        // Auf linken Rand pruefen
                        if (super.getPosition().getXK() > 0) {
                            zulaessig = true;
                        } else {
                            // Ende der Zeile erreicht
                            keinZeilenumbruch = false;
                        }
                    } else if (i == 1) {
                        // Auf rechten Rand pruefen
                        if (super.getPosition().getXK() < 7) {
                            zulaessig = true;
                        } else {
                            // Ende der Zeile erreicht
                            keinZeilenumbruch = false;
                        }
                    } else if (i == -8 || i == 8) {
                        // Vorne und hinten muss nicht geprueft werden
                        zulaessig = true;
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
                if (i == -1) {
                    // Auf linken Rand pruefen
                    if (super.getPosition().getXK() > 0) {
                        zulaessig = true;
                    }
                } else if (i == 1) {
                    // Auf rechten Rand pruefen
                    if (super.getPosition().getXK() < 7) {
                        zulaessig = true;
                    }
                } else if (i == -8 || i == 8) {
                    // Vorne und hinten muss nicht geprueft werden
                    zulaessig = true;
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
