package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

/**
 * Der Bauplan für die Spielfigur Dame. <br>
 * Besitzt haupts&auml;chlich die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 * @author Christian Ackermann
 */
public class Dame extends Figur {

    /**
     * Erzeugt eine neue Dame.
     * Einziger Konstruktor dieser Klasse.
     * @param position : Das Feld auf dem die Figur stehen soll
     * @param farbe : Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */
    public Dame(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(900);
    }

    /**
     * {@inheritDoc}
     */
    protected List<Feld> getMoeglicheFelder() {
        List<Feld> moeglicheFelder = new ArrayList<Feld>();
        /*<Gehe alle horizontalen, vertikalen und diagonalen Felder durch und 
         * schaue ob sie existieren und nicht von eigenen Figuren besetzt sind.>
         */
        // Die Abweichung der Indizes der umliegenden Felder zum aktuellen Feld
        int[] indizes = {-9, -8, -7, -1, 1, 7, 8, 9};
        // Fuer alle Felder
        for (int i : indizes) {
            // Hilfsvariablen
            int zaehl = 1;
            int newIndex = i;
            boolean keinZeilenumbruch = true;
            // Solange das naechste Feld frei ist und wir nicht den Rand
            // erreicht haben
            while (super.istFrei(super.getFeldIndex() + (i * zaehl)) 
                && keinZeilenumbruch) {
                /* Wenn der Index zwischen 0 und 63 liegt und das Feld
                 * besetzt werden darf
                 */
                if (super.getFeldIndex() + newIndex >= 0 
                    && super.getFeldIndex() + newIndex < 64) {
                    boolean zulaessig = false;
                    if (i == -9 || i == -1 || i == 7) {
                        // Auf linken Rand pruefen
                        if (super.getPosition().getXK() > 0) {
                            zulaessig = true;
                        } else {
                            // Ende der Zeile erreicht
                            keinZeilenumbruch = false;
                        }
                    } else if (i == -7 || i == 1 || i == 9) {
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
            }
            
            // Wenn das naechste Feld nicht mehr frei ist
            /* Wenn der Index zwischen 0 und 63 liegt und das Feld
             * besetzt werden darf
             */
            if (super.getFeldIndex() + newIndex >= 0 
                && super.getFeldIndex() + newIndex < 64
                && super.istMoeglich(super.getFeldIndex() + newIndex)) {
                boolean zulaessig = false;
                if (i == -9 || i == -1 || i == 7) {
                    // Auf linken Rand pruefen
                    if (super.getPosition().getXK() > 0) {
                        zulaessig = true;
                    } else {
                        // Ende der Zeile erreicht
                        keinZeilenumbruch = false;
                    }
                } else if (i == -7 || i == 1 || i == 9) {
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
        }
        
        return moeglicheFelder;
    }

    /**
     * {@inheritDoc}
     */
    public List<Feld> getKorrekteFelder() {
        // TODO Auto-generated method stub
        return null;
    }

}
