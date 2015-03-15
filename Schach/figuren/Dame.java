package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

/**
 * Der Bauplan fuer die Spielfigur Dame. <br>
 * Besitzt haupts&auml;chlich die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 * @author Christian Ackermann
 * @see Figur
 */
public class Dame extends Figur {

    /**
     * Erzeugt eine neue Dame.
     * Einziger Konstruktor dieser Klasse.
     * @param position Das Feld auf dem die Figur stehen soll
     * @param farbe Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */
    public Dame(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(900);
    }

    /**
     * Ermittelt alle m&ouml;glichen Felder, auf die die Dame nach den Zugregeln
     * ziehen k&ouml;nnte. Dabei kann sie sich grundsätzlich horizontal, 
     * vertikal und diagonal bewegen und zwar so lange, bis eine eigene Figur
     * im Weg steht, eine gegnerische Figur im Weg steht und geschlagen werden
     * kann oder das Spielfeld zu Ende ist. Alle m&ouml;glichen Felder werden
     * einer Liste zugef&uuml;gt die am Ende zur&uuml;ck gegeben wird.
     * @return Die Liste mit den m&ouml;glichen Feldern
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
            // Berechnung des Index' des neuen Feldes
            int index = super.getFeldIndex() + (i * zaehl);
            // 0 <= index <= 63 sowie die restlichen Bedingungen (s.While)
            boolean bedingung = (index >= 0 && index < 64) 
                && super.istFrei(index) && keinZeilenumbruch;
            /* Solange das naechste Feld frei ist und der Rand nicht erreicht
             * ist
             */ 
            while (bedingung) {
                int neuerFeldIndex = super.getFeldIndex() + newIndex;
                // Wenn der Index zwischen 0 und 63 liegt
                if (neuerFeldIndex >= 0 
                    && neuerFeldIndex < 64) {
                    boolean zulaessig = false;
                    if (i == -9 || i == -1 || i == 7) {
                        // Auf linken Rand pruefen
                        // Wenn Zeilenumbruch war, dann waere neuer Wert 7
                        if (super.getFeld(neuerFeldIndex).getXK() < 7) {
                            zulaessig = true;
                        } else {
                            // Ende der Zeile erreicht
                            keinZeilenumbruch = false;
                        }
                    } else if (i == -7 || i == 1 || i == 9) {
                        // Auf rechten Rand pruefen
                        // Wenn Zeilenumbruch war, dann waere neuer Wert 0
                        if (super.getFeld(neuerFeldIndex).getXK() > 0) {
                            zulaessig = true;
                        } else {
                            // Ende der Zeile erreicht
                            keinZeilenumbruch = false;
                        }
                    } else if (i == -8 || i == 8) {
                        // Nach oben und unten muss nicht getestet werden
                        zulaessig = true;
                    }
                    
                    if (zulaessig) {
                        moeglicheFelder.add(super.getFeld(neuerFeldIndex));
                    }
                }
                zaehl++;
                newIndex = i * zaehl;
                // Aktualisierung der Index- und While-Bedingung
                index = super.getFeldIndex() + (i * zaehl);
                bedingung = (index >= 0 && index < 64) 
                    && super.istFrei(index) && keinZeilenumbruch;
            }
           
            /* Wenn es nicht am Zeilenumbruch gescheitert ist, sondern daran,
             * dass eine gegnerische Figur im Weg stand, muss noch das naechste
             * Feld zugefuegt werden, sofern dabei kein Zeilenumbruch auftritt
             */
            
            // Wenn es nicht am Zeilenumbruch gescheitert ist
            if (keinZeilenumbruch) {
                int neuerFeldIndex = super.getFeldIndex() + newIndex;
                // Wenn der Index zwischen 0 und 63 liegt
                if (neuerFeldIndex >= 0 
                    && neuerFeldIndex < 64
                    && super.istMoeglich(neuerFeldIndex)) {
                    boolean zulaessig = false;
                    if (i == -9 || i == -1 || i == 7) {
                        // Auf linken Rand pruefen
                        // Wenn Zeilenumbruch war, dann waere neuer Wert 7
                        if (super.getFeld(neuerFeldIndex).getXK() < 7) {
                            zulaessig = true;
                        }
                    } else if (i == -7 || i == 1 || i == 9) {
                        // Auf rechten Rand pruefen
                        // Wenn Zeilenumbruch war, dann waere neuer Wert 0
                        if (super.getFeld(neuerFeldIndex).getXK() > 0) {
                            zulaessig = true;
                        }
                    } else if (i == -8 || i == 8) {
                        // Nach oben und unten muss nicht getestet werden
                        zulaessig = true;
                    }     
                    if (zulaessig) {
                        moeglicheFelder.add(super.getFeld(neuerFeldIndex));
                    }
                }
            
            }
        }
        
        return moeglicheFelder;
    }

  

}
