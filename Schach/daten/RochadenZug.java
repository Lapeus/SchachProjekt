package daten;

import figuren.Figur;
import gui.Feld;

/**
 * Ist ein Sonderzug, der die Rochade aufzeichnet.<br>
 * Erbt von Zug, muss dieser Klasse nur die Dauer des Zuges &uuml;bergeben.
 * @author Christian Ackermann
 */
public class RochadenZug extends Zug {
    
    /**
     * Der an der Rochade beteiligte K&ouml;nig.
     */
    private Figur koenig;
    
    /**
     * Das Startfeld des K&ouml;nigs.
     */
    private Feld startfeldK;
    
    /**
     * Der an der Rochade beteiligte Turm.
     */
    private Figur turm;
    
    /**
     * Das Startfeld des Turms.
     */
    private Feld startfeldT;

    /**
     * Erstellt einen neuen Rochaden-Zug.<br>
     * Einziger Konstruktor dieser Klasse.
     * @param koenig Der beteiligte K&ouml;nig
     * @param startfeldK Das Startfeld des K&ouml;nigs
     * @param turm Der beteiligte Turm
     * @param startfeldT Das Startfeld des Turms
     * @param zugzeit Die Dauer des Zuges in ganzen Sekunden
     */
    public RochadenZug(Figur koenig, Feld startfeldK, Figur turm, 
        Feld startfeldT, int zugzeit) {
        this.koenig = koenig;
        this.startfeldK = startfeldK;
        this.turm = turm;
        this.startfeldT = startfeldT;
        setZugzeit(zugzeit);
    }
    
    /**
     * Gibt den beteiligten K&ouml;nig zur&uuml;ck.
     * @return Der beteiligte K&ouml;nig
     */
    public Figur getKoenig() {
        return koenig;
    }
    
    /**
     * Gibt das Startfeld des K&ouml;nigs zur&uuml;ck.
     * @return Das Startfeld des K&ouml;nigs
     */
    public Feld getStartfeldK() {
        return startfeldK;
    }
    
    /**
     * Gibt den beteiligten Turm zur&uuml;ck.
     * @return Der beteiligte Turm
     */
    public Figur getTurm() {
        return turm;
    }
    
    /**
     * Gibt das Startfeld des Turms zur&uuml;ck.
     * @return Das Startfeld des Turms
     */
    public Feld getStartfeldT() {
        return startfeldT;
    }
    
    
}
