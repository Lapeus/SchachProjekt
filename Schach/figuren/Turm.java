package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

/**
 * Der Bauplan fuer die Spielfigur Turm. <br>
 * Besitzt haupts&auml;chlich die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 * @author Christian Ackermann
 * @see Figur
 */
public class Turm extends Figur {

    /**
     * Erzeugt einen neuen Turm.
     * Einziger Konstruktor dieser Klasse.
     * @param position Das Feld auf dem die Figur stehen soll
     * @param farbe Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */
    public Turm(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(465);
        super.setGezogen(false);
    }

    /**
     * Ermittelt alle Felder, auf die ein Turm ziehen darf. Ein Turm darf sich
     * grunds&auml;tzlich horizontal und vertikal so weit bewegen, bis er eine
     * andere Figur oder den Bildschirmrand erreicht. Das &Uuml;berspringen
     * des K&ouml;nigs bei der Rochade wird nicht einbezogen, da das beim 
     * Ziehen einer Rochade ber&uuml;cksichtigt wird. Alle m&ouml;glichen
     * Felder werden einer Liste zugef&uuml;gt und anschlie&szlig;end 
     * zur&uuml;ck gegeben.
     * @return Die Liste der m&ouml;glichen Felder
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
                    if (i == -1) {
                        // Auf linken Rand pruefen
                        // Wenn Zeilenumbruch war, dann waere neuer Wert 7
                        if (super.getFeld(neuerFeldIndex).getXK() < 7) {
                            zulaessig = true;
                        } else {
                            // Ende der Zeile erreicht
                            keinZeilenumbruch = false;
                        }
                    } else if (i == 1) {
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
                    if (i == -1) {
                        // Auf linken Rand pruefen
                        // Wenn Zeilenumbruch war, dann waere neuer Wert 7
                        if (super.getFeld(neuerFeldIndex).getXK() < 7) {
                            zulaessig = true;
                        }
                    } else if (i == 1) {
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
