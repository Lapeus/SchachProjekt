package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

import zuege.EnPassantZug;
import zuege.RochadenZug;
import zuege.Zug;

/**
 * Der Bauplan f&uuml;r die Spielfigur Bauer. <br>
 * Enth&auml;lt haupts&auml;chlich die spezifizierte Methode zur Ermittlung der 
 * m&ouml;glichen Z&uuml;ge.
 * @author Christian Ackermann
 * @see Figur
 */
public class Bauer extends Figur {
    
    /**
     * Erzeugt einen neuen Bauern. 
     * Einziger Konstruktor dieser Klasse.
     * @param position Das Feld auf dem die Figur stehen soll
     * @param farbe Die Spielfarbe der Figur (<b>true</b> f&uuml;r wei&szlig;
     * , <b>false</b> f&uuml;r schwarz)
     */
    public Bauer(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(100);
        super.setGezogen(false);
    }

    /**
     * Berechnet alle m&ouml;glichen Felder, auf die der Bauer nach den 
     * momentan eingestellten Zugregeln ziehen kann. Daf&uuml;r sind 
     * grunds&auml;tzlich vier verschiedene M&ouml;glichkeiten zu pr&uuml;fen:
     * <ul>
     * <li> Ist das Feld direkt vor dem Bauern frei?</li>
     * <li> Sind die zwei Felder vor dem Bauern frei und hat dieser noch
     * nicht gezogen? </li>
     * <li> Steht diagonal vorne eine gegnerische Figur? </li>
     * <li> Ist das Schlagen en-passant aktiviert und in diesem Fall 
     * m&ouml;glich? </li>
     * </ul>
     * Ist die jeweilige Bedingung erf&uuml;llt, wird das entsprechende Feld
     * der Liste zugef&uuml;gt die am Ende zur&uuml;ck gegeben wird.
     * @return Die Liste der m&ouml;glichen Felder
     */
    protected List<Feld> getMoeglicheFelder() {
        List<Feld> moeglicheFelder = new ArrayList<Feld>();
        // Indizes fuer weiss
        int[] indizes = {8, 16, 7, 9};
        // Abfrage ob weiss oder schwarz
        if (!super.getFarbe()) {
            // Indizes fuer schwarz
            indizes[0] = -8;
            indizes[1] = -16;
            indizes[2] = -9; // Getauscht, weil sie ja entgegengesetzt laufen
            indizes[3] = -7; //   ""           ""          ""           ""
        }
        
        // IndexBounds-Pruefung
        int newIndex = getFeldIndex() + indizes[0];
        // Wenn an der jetzigen Stelle plus 8 (vorne) keine Figur steht  
        if (newIndex >= 0 && newIndex < 64 
            && getFigurAt(getFeldIndex() + indizes[0]) == null) {
            // Fuege das entsprechende Feld der Liste zu
            moeglicheFelder.add(super.getFeld(getFeldIndex() + indizes[0]));
        }
        // Wenn die Figur noch nicht bewegt wurde
        if (!super.getGezogen()) {
            // IndexBounds-Pruefung
            newIndex = getFeldIndex() + indizes[0];
            // Wenn an der jetzigen Stelle plus 16 (2 vorne) keine Figur steht
            if (newIndex >= 0 && newIndex < 64 
                && super.getFigurAt(getFeldIndex() + indizes[0]) == null
                && super.getFigurAt(getFeldIndex() + indizes[1]) == null) {
                // Fuege das entsprechende Feld der Liste zu
                moeglicheFelder.add(super.getFeld(getFeldIndex() + indizes[1]));
            }
        }
        
        // IndexBounds-Pruefung
        newIndex = getFeldIndex() + indizes[2];
        // Wenn links noch ein Feld ist
        if (newIndex >= 0 && newIndex < 64 
            && super.getPosition().getXK() > 0) {
            // Wenn schraeg links vorne eine gegnerische Figur steht
            if (super.getFigurAt(getFeldIndex() + indizes[2]) != null 
                && super.getFigurAt(getFeldIndex() + indizes[2]).getFarbe() 
                != super.getFarbe()) {
                // Fuege das entsprechende Feld der Liste zu
                moeglicheFelder.add(super.getFeld(getFeldIndex() + indizes[2]));
            }
        }
        
        // IndexBounds-Pruefung
        newIndex = getFeldIndex() + indizes[3];
        // Wenn rechts noch ein Feld ist
        if (newIndex >= 0 && newIndex < 64 
            && super.getPosition().getXK() < 7) {
            // Wenn schraeg rechts vorne eine gegnerische Figur steht
            if (super.getFigurAt(getFeldIndex() + indizes[3]) != null 
                && super.getFigurAt(getFeldIndex() + indizes[3]).getFarbe() 
                != super.getFarbe()) {
                // Fuege das entsprechende Feld der Liste zu
                moeglicheFelder.add(super.getFeld(getFeldIndex() + indizes[3]));
            }
        }
        
        // En-passant-Schlagen
        if (getSpielfeld().getEinstellungen().isEnPassantMoeglich()
            && getSpielfeld().getSpieldaten().getLetzterZug() != null
            && getSpielfeld().getSpieldaten().getLetzterZug().getStartfeld() 
            != null) {
            moeglicheFelder.addAll(enPassant());
        }
        
        // Gibt die Liste der moeglichen Felder zurueck
        return moeglicheFelder;
    }
   
    /**
     * Gibt alle zus&auml;tzlichen m&ouml;glichen Felder zur&uuml;ck, wenn das
     * Schlagen En-Passant m&ouml;glich ist.
     * @return Eine Liste mit den zus&auml;tzlich m&ouml;glichen Feldern
     */
    private List<Feld> enPassant() {
        /* Das Schlagen en-passant ist erlaubt, wenn der Gegner im vorherigen
         * Zug mit seinem Bauern zwei Felder gezogen ist und nun neben einem
         * von unseren Bauern steht.
         * Dieser darf jetzt so tun, als waere der Gegner nur ein Feld nach 
         * vorne gezogen und schlaegt den Bauern neben sich.
         */
        
        /* Bedingungen:
         * 1. Im vorherigen Zug muss ein Bauer zwei Felder gezogen worden sein
         * (steht jetzt auf Reihe 3 bzw 4)
         * 2. Der eigene Bauer steht direkt daneben
         */
        List<Feld> moeglicheFelder = new ArrayList<Feld>();
        // Wenn das nicht der erste Zug ist
        if (!getSpielfeld().getSpieldaten().getZugListe().isEmpty()) {
            // Letzter durchgefuehrter Zug
            Zug letzterZug = getSpielfeld().getSpieldaten()
                .getLetzterZug();
            // Wenn der letzte Zug ein Sonderzug war, ist en-passant nicht mogl.
            /* Durch die erweiterte Methode getBedrohteFelder wurde es moeglich
             * dass ein Spieler in der Theorie doppelt zieht. Darauf muss hier
             * geachtet werden.
             */
            if (!(letzterZug instanceof RochadenZug 
                || letzterZug instanceof EnPassantZug)
                && letzterZug.getFigur().getFarbe() != super.getFarbe()) {
                // Wenn im letzten Zug ein Bauer ein Doppelzug gemacht hat
                if (letzterZug.getFigur().getWert() == 100 
                    && letzterZug.isErsterZug()
                    && (letzterZug.getZielfeld().getYK() == 4 
                    || letzterZug.getZielfeld().getYK() == 3)) {
                    Figur gegner = letzterZug.getFigur();
                    // Der eigene Bauer steht in der gleichen Reihe
                    if (gegner.getPosition().getYK() 
                        == getPosition().getYK()) {
                        // Der eigene Bauer steht direkt daneben
                        if (Math.abs(gegner.getPosition().getXK() 
                            - getPosition().getXK()) == 1) {
                            // Dann ist das Schlagen moeglich
                            if (super.getFarbe()) {
                                // Das Feld ueber dem Gegner
                                moeglicheFelder.add(getFeld(
                                    gegner.getFeldIndex() + 8));
                            } else {
                                // Das Feld unter dem Gegner
                                moeglicheFelder.add(getFeld(
                                    gegner.getFeldIndex() - 8));
                            }
                        }
                    }
                }
            }
        }
        
        return moeglicheFelder;
    }
    
    
    

}
