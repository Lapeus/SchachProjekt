package zuege;

import figuren.Figur;
import gui.Feld;

/**
 * Stellt f&uuml;r den Zugsortierer eine Datenstruktur Zug zur Verf&uuml;gung.
 */
public class KIZug {

    /**
     * Die Figur die gezogen wird.
     */
    private Figur figur;
    
    /**
     * Das Zielfeld des Zuges.
     */
    private Feld feld;
    
    /**
     * Die Bewertung dieses Zuges.
     */
    private int bewertung;
    
    /**
     * Erstellt einen neuen KIZug mit den angegebenen Werten.
     * @param figur Die Figur
     * @param feld Das Feld
     */
    public KIZug(Figur figur, Feld feld) {
        this.figur = figur;
        this.feld = feld;
        this.bewertung = 0;
    }

    /**
     * Gibt die ziehende Figur zur&uuml;ck.
     * @return Die ziehende Figur
     */
    public Figur getFigur() {
        return figur;
    }

    /**
     * Gibt das Zielfeld zur&uuml;ck.
     * @return Das Zielfeld
     */
    public Feld getFeld() {
        return feld;
    }

    /**
     * Gibt die Bewertung des Zuges zur&uuml;ck.
     * @return Die Bewertung
     */
    public int getBewertung() {
        return bewertung;
    }
    
    /**
     * Setzt die Bewertung des Zuges.
     * @param bewertung Die Bewertung des Zuges
     */
    public void setBewertung(int bewertung) {
        this.bewertung = bewertung;
    }
    
}
