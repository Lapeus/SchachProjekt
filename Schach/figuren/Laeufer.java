package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

/**
 * Der Bauplan f&uuml;r die Spielfigur L&auml;ufer. <br>
 * Enth&auml;lt nur die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 * @author Christian Ackermann
 * @see Figur
 */
public class Laeufer extends Figur {

    /**
     * Erzeugt einen neuen L&auml;ufer.
     * Einziger Konstruktor dieser Klasse.
     * @param position Das Feld auf dem die Figur stehen soll
     * @param farbe Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */

    public Laeufer(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(325);
    }

    /**
     * Ermittelt alle m&ouml;glichen Felder, auf die der L&auml;ufer nach den
     * normalen Zugregeln ziehen kann. Dabei darf er sich grunds&auml;tzlich 
     * so weit diagonal bewegen, bis er eine andere Figur oder den 
     * Spielfeldrand erreicht. Alle m&ouml;glichen Felder werden einer Liste
     * zugef&uuml;gt, die am Ende zur&uuml;ckgegeben wird.
     * @return Die Liste der m&ouml;glichen Felder
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
                int neuerFeldIndex = super.getFeldIndex() + newIndex;
                // Wenn der Index zwischen 0 und 63 liegt
                if (neuerFeldIndex >= 0 
                    && neuerFeldIndex < 64) {
                    boolean zulaessig = false;
                    if (i == -9 || i == 7) {
                        // Auf linken Rand pruefen
                        // Wenn Zeilenumbruch war, dann waere neuer Wert 7
                        if (super.getFeld(neuerFeldIndex).getXK() < 7) {
                            zulaessig = true;
                        } else {
                            // Ende der Zeile erreicht
                            keinZeilenumbruch = false;
                        }
                    } else if (i == -7 || i == 9) {
                        // Auf rechten Rand pruefen
                        // Wenn Zeilenumbruch war, dann waere neuer Wert 0
                        if (super.getFeld(neuerFeldIndex).getXK() > 0) {
                            zulaessig = true;
                        } else {
                            // Ende der Zeile erreicht
                            keinZeilenumbruch = false;
                        }
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
                    if (i == -9 || i == 7) {
                        // Auf linken Rand pruefen
                        // Wenn Zeilenumbruch war, dann waere neuer Wert 7
                        if (super.getFeld(neuerFeldIndex).getXK() < 7) {
                            zulaessig = true;
                        }
                    } else if (i == -7 || i == 9) {
                        // Auf rechten Rand pruefen
                        // Wenn Zeilenumbruch war, dann waere neuer Wert 0
                        if (super.getFeld(neuerFeldIndex).getXK() > 0) {
                            zulaessig = true;
                        }
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
