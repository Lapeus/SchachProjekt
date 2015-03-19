package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

/**
 * Der Bauplan fuer die Spielfigur K&ouml;nig. <br>
 * Besitzt haupts&auml;chlich die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 * @author Christian Ackermann
 * @see Figur
 */
public class Koenig extends Figur {

    /**
     * Erzeugt einen neuen K&ouml;nig.
     * Einziger Konstruktor dieser Klasse.
     * @param position Das Feld auf dem die Figur stehen soll
     * @param farbe Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */
    public Koenig(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(0); // Wert des Koenigs ist irrelevant
        super.setGezogen(false);
    }

    /**
     * Ermittelt alle Felder, auf die der K&ouml;nig nach den eingestellten 
     * Zugregeln ziehen kann und f&uuml;gt sie einer Liste zu, die am Ende
     * zur&uuml;ckgegeben wird. <br>
     * Ein K&ouml;nig kann grunds&auml;tzlich auf jedes benachbarte Feld ziehen,
     * sofern es von keiner eigenen Figur besetzt wird. Ist die Option 
     * "Rochade m&ouml;glich" aktiviert, muss noch getestet werden, ob er eine
     * Rochade durchf&uuml;hren kann. Dies geschieht jedoch in einer anderen
     * Methode.
     * @return Die Liste der m&ouml;glichen Felder
     */
    protected List<Feld> getMoeglicheFelder() {
        /* Hier muss eine Auslagerung vorgenommen werden. Die normalen
         * Koenigszuege werden durch die Methode normaleZuege() ermittelt.
         * Die Rochade wird durch die Methode rochade() implementiert.
         * Wuerde man das nicht trennen, entstaende in der Methode
         * rochadeMoeglich(boolean kleineRochade) eine endlose Rekursion, weil
         * abgeprueft werden muss, ob eines der ueberzogenen Felder bedroht ist.
         * Das wird umgesetzt, indem man jede gegnerische Figur auf jedes Feld
         * ziehen laesst und schaut, ob das zu pruefende dabei ist. Dabei wird
         * jedoch unter anderem die Methode getMoeglicheFelder() des
         * gegnerischen Koenigs aufgerufen. Der wuerde dann ebenfalls pruefen,
         * ob er eine Rochade durchfuehren kann und dabei entstaende eine 
         * endlose Rekursion aus der man nicht wieder raus kommt.
         * Fuer diesen Fall wird die Methode getMoeglicheFelder() des Koenigs
         * eingeteilt in normaleZuege() und rochade().
         */
        
        List<Feld> moeglicheFelder = new ArrayList<Feld>();
        // Normale Zuege
        moeglicheFelder.addAll(normaleZuege());
        
        // Rochade
        if (getSpielfeld().getEinstellungen().isRochadeMoeglich()) {
            moeglicheFelder.addAll(rochade());
        }
        
        return moeglicheFelder;
    }
    
    /**
     * Ermittelt die normalen Z&uuml;ge des K&ouml;nigs mit Ausnahme der
     * Rochade.
     * @return Liste der m&ouml;glichen Felder, auf die der K&ouml;nig ziehen
     * kann
     */
    private List<Feld> normaleZuege() {
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
                    if (super.getPosition().getXK() > 0) {
                        zulaessig = true;
                    }
                } else if (i == -7 || i == 1 || i == 9) {
                    // Nach rechts muss auf Rand getestet werden
                    if (super.getPosition().getXK() < 7) {
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
     * Ermittelt die zus&auml;tzlich m&ouml;glichen Felder des K&ouml;nigs, 
     * wenn er auch rochieren darf.
     * @return Liste mit den m&ouml;glichen Rochade-Feldern auf die der 
     * K&ouml;nig ziehen darf
     */
    private List<Feld> rochade() {
        List<Feld> moeglicheFelder = new ArrayList<Feld>();
        // Rochade
        
        // kleine Rochade
        if (!getGezogen() && rochadeMoeglich(true)) {
            // Fuege das Feld zwei rechts vom Koenig hinzu
            moeglicheFelder.add(getFeld(getFeldIndex() + 2));
        }
        
        // grosse Rochade
        if (!getGezogen() && rochadeMoeglich(false)) {
            // Fuege das Feld zwei links vom Koenig hinzu
            moeglicheFelder.add(getFeld(getFeldIndex() - 2));
        }
        
        return moeglicheFelder;
    }

    /**
     * Pr&uuml;ft, ob alle Bedingungen f&uuml;r eine Rochade erf&uuml;llt sind.
     * <br> Dabei m&uuml;ssen folgende Kriterien erf&uuml;llt sein:
     * <ul>
     * <li> Weder der K&ouml;nig noch der beteiligte Turm d&uuml;rfen bisher
     * gezogen worden sein </li>
     * <li> Der K&ouml;nig darf momentan nicht im Schach stehen </li>
     * <li> Der K&ouml;nig darf nach der Rochade nicht im Schach stehen </li>
     * <li> Der K&ouml;nig darf &uuml;ber kein Feld gezogen werden, das bedroht
     * ist. <br>In der Praxis hei&szlig;t das: Der Turm darf nach dem Zug nicht 
     * bedroht sein </li>
     * </ul>
     * @param kleineRochade Ob es eine kleine oder gro&szlig;e Rochade ist.
     * <b>True</b> f&uuml;r kleine, <b>false</b> f&uuml;r gro&szlig;e Rochade
     * @return Wahrheitswert, ob Rochade durchgef&uuml;hrt werden darf
     */
    private boolean rochadeMoeglich(boolean kleineRochade) {
        boolean moeglich = false;
        // Der beteiligte Turm
        Figur turm = null;
        // Wenn: Es die kleine Rochade ist und es eine Figur 3 Felder rechts 
        // vom Koenig gibt und diese Figur ein Turm ist
        // und dieser Turm die gleiche Farbe hat wie dieser Koenig
        if (kleineRochade && getFigurAt(getFeldIndex() + 3) != null 
            && getFigurAt(getFeldIndex() + 3).getWert() == 465
            && getFigurAt(getFeldIndex() + 3).getFarbe() == getFarbe()) {
            // Dann ist das der beteiligte Turm
            turm = getFigurAt(getFeldIndex() + 3);
         // Wenn: Es die grosse Rochade ist und es eine Figur 4 Felder links 
         // vom Koenig gibt und diese Figur ein Turm ist
         // und dieser Turm die gleiche Farbe hat wie dieser Koenig
        } else if (!kleineRochade && getFigurAt(getFeldIndex() - 4) != null 
            && getFigurAt(getFeldIndex() - 4).getWert() == 465
            && getFigurAt(getFeldIndex() - 4).getFarbe() == getFarbe()) {
            // Dann ist das der beteiligte Turm
            turm = getFigurAt(getFeldIndex() - 4);
        }
        
        // Felder, auf denen der Koenig steht oder die er ueberzieht
        List<Feld> pruefFelder = new ArrayList<Feld>();
        // Das aktuelle Feld
        pruefFelder.add(getPosition());
        if (kleineRochade) {
            // Bei der kleinen Rochade die beiden rechts davon
            pruefFelder.add(getFeld(getFeldIndex() + 1));
            pruefFelder.add(getFeld(getFeldIndex() + 2));
        } else {
            // Bei der grossen Rochade die beiden links davon
            pruefFelder.add(getFeld(getFeldIndex() - 1));
            pruefFelder.add(getFeld(getFeldIndex() - 2));
        }
        
        // Wenn es einen beteiligten Turm gibt
        if (turm != null) {
            moeglich = true;
            // Wenn der Turm bereits gezogen wurde
            if (turm.getGezogen()) {
                // Unzulaessig
                moeglich = false;
            // Wenn zwischen dem Turm und dem Koenig eine andere Figur steht
            // -> Wenn der Turm nicht auf das Zielfeld des Turms ziehen kann
            } else if (!turm.getMoeglicheFelder()
                .contains(pruefFelder.get(1))) {
                moeglich = false;
            } else {
                // Berechnung der bedrohten Felder
                List<Feld> bedrohteFelder = alleBedrohtenFelder(); 
                // Wenn der Koenig momentan bedroht ist
                if (bedrohteFelder.contains(pruefFelder.get(0))) {
                    moeglich = false;
                // Wenn das Zielfeld des Turms bedroht ist
                } else if (bedrohteFelder.contains(pruefFelder.get(1))) {
                    moeglich = false;
                // Wenn das Zielfeld des Koenigs bedroht ist
                } else if (bedrohteFelder.contains(pruefFelder.get(2))) {
                    moeglich = false;
                }
            }
        }
        return moeglich;
    }
    
    /**
     * Ermittelt alle vom Gegner bedrohten Felder. <br>
     * Wird verwendet, um bei der Rochade pr&uuml;fen zu k&ouml;nnen, ob eines
     * der Felder, &uuml;ber die der K&ouml;nig ziehen muss, bedroht ist. <br>
     * <i>Hinweis: Die Methode ist fast identisch mit der Methode 
     * {@link daten.Spielfeld#getBedrohteFelder()} in der Klasse Spielfeld, 
     * jedoch wird in dieser Variante nicht getestet, ob der K&ouml;nig eine 
     * Rochade ziehen d&uuml;rfte, da es sonst zu einer endlosen Rekursion 
     * k&auml;me.</i>
     * @return Liste mit allen bedrohten Feldern
     */
    private List<Feld> alleBedrohtenFelder() {
        // Berechnung der bedrohten Felder
        List<Feld> bedrohteFelder = new ArrayList<Feld>();
        // Liste mit den gegnerischen Figuren
        List<Figur> gegnerFiguren;
        // Wenn der Koenig weiss ist
        if (getFarbe()) {
            gegnerFiguren = getSpielfeld().getSchwarzeFiguren();
        // Wenn der Koenig schwarz ist
        } else {
            gegnerFiguren = getSpielfeld().getWeisseFiguren();
        }
        // Fuer alle gegnerischen Figuren
        for (Figur gegner : gegnerFiguren) {
            List<Feld> felder;
            // Wenn es nicht der Koenig ist
            if (gegner.getWert() != 0) {
                // Liste mit den moeglichen Feldern dieser Figur
                felder = gegner.getMoeglicheFelder();
            } else {
                /* Wenn es der Koenig ist, darf nicht getMoeglicheFelder()
                 * aufgerufen werden, sondern normaleFelder()
                 * Erklaerung: Siehe Kommentar getMoeglicheFelder()
                 */
                // Es ist definitiv ein Koenig
                Koenig koenig = (Koenig) gegner;
                // Rufe die normalen Zuege des Koenigs ab
                felder = koenig.normaleZuege();
            }
            // Fuer jedes dieser Felder
            for (Feld feld : felder) {
                // Fuege es der Liste zu
                bedrohteFelder.add(feld);
            }
        }
        return bedrohteFelder;
    }

}
