package zuege;

import figuren.Figur;
import gui.Feld;

/**
 * Ist ein Sonderzug, um den Fall zu behandeln, dass ein Bauern durch Erreichen
 * der gegnerischen Grundlinie in eine andere Figur umgewandelt wird. <br>
 * Erbt von Zug und &uuml;bergibt dem Konstruktor alle wichtigen Daten. 
 * @author Christian Ackermann
 */
public class Umwandlungszug extends Zug {

    /**
     * Die neue Figur, gegen die der Bauer ausgetauscht wird.
     */
    private Figur neueFigur;
    
    /**
     * Erstellt einen neuen Umwandlungszug mit nahezu den gleichen Attributen
     * wie seine Elternklasse. <br>
     * Er &uuml;bergibt auch dem super-Konstruktor alle ben&ouml;tigten Daten
     * Der boolean ersterZug wird standardm&auml;&szlig;ig auf <b>false</b> 
     * gesetzt, da der Bauer bereits gezogen worden sein muss, damit er die 
     * gegnerische Grundlinie erreichen konnte.
     * @param startfeld Das Feld auf dem der Bauer vor dem Zug stand
     * @param zielfeld Das Feld auf dem der Bauer nach dem Zug steht
     * @param bauer Der Bauer der gezogen wird
     * @param schlagzug Ob bei dem Zug eine andere Figur geschlagen wurde
     * @param zugzeit Die Dauer des Zuges in ganzen Sekunden
     */
    public Umwandlungszug(Feld startfeld, Feld zielfeld, Figur bauer
        , boolean schlagzug, int zugzeit) {
        super(startfeld, zielfeld, bauer, schlagzug, zugzeit, false);
    }

    /**
     * {@inheritDoc}
     */
    public String toSchachNotation() {
        String string;
        // Normale Notation
        string = super.toSchachNotation();
        int wert = neueFigur.getWert();
        // Figurensymbol anhaengen
        if (wert == 275) {
            string += "S";
        } else if (wert == 325) {
            string += "L";
        } else if (wert == 465) {
            string += "T";
        } else {
            string += "D";
        }
        return string;
        
    }
    
    /**
     * Gibt die umgewandelte Figur zur&uuml;ck.
     * @return Die neue Figur
     */
    public Figur getNeueFigur() {
        return neueFigur;
    }

    /**
     * Setzt die umgewandelte Figur.
     * @param neueFigur Die neue Figur
     */
    public void setNeueFigur(Figur neueFigur) {
        this.neueFigur = neueFigur;
    }
}