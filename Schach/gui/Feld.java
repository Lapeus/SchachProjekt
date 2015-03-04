package gui;

import javax.swing.JLabel;

import figuren.Figur;

/**
 * Stellt ein Feld als Teil des Schachbrettes dar. <br>
 * Erbt von JLabel und hat zus&auml;tzlich die Koordinaten des Felder auf dem
 * Brett und die Figur auf dem Feld gespeichert.
 * @author Christian Ackermann
 */
public class Feld extends JLabel {

    /**
     * Automatisch generierte UID.
     */
    private static final long serialVersionUID = 5518342568474295458L;
    
    /**
     * Die x-Koordinate des Feldes auf dem Brett (A-H). <br>
     * Ganze Zahl zwischen 0 und 7.
     */
    private int x;
    
    /**
     * Die y-Koordinate des Feldes auf dem Brett (1-8). <br>
     * Ganze Zahl zwischen 0 und 7.
     */
    private int y;
    
    /**
     * Die Figur, die auf dem Feld steht. <br>
     * Ist das Feld leer, so ist dieses Attribut <b><i>null</i></b>.
     */
    private Figur figur;
    
    /**
     * Erzeugt ein neues Feld.
     * Einziger Konstruktor der Klasse Feld.
     * @param x : Die x-Koordinate (zwischen 0 und 7)
     * @param y : Die y-Koordinate (zwischen 0 und 7)
     */
    public Feld(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gibt die x-Koordinate des Feldes zur&uuml;ck.
     * @return Die ganzzahlige Koordinate (zwischen 0 und 7)
     */
    public int getXK() {
        return x;
    }
    
    /**
     * Gibt die y-Koordinate des Feldes zur&uuml;ck.
     * @return Die ganzzahlige Koordinate (zwischen 0 und 7)
     */
    public int getYK() {
        return y;
    }
    
    /**
     * Gibt die Figur die auf dem Feld steht zur&uuml;ck.
     * @return Die Figur
     */
    public Figur getFigur() {
        return figur;
    }
    
    /**
     * Setzt die Figur, welche auf dem Feld stehen soll.
     * @param figur : Eine Figur die auf diesem Feld stehen soll
     */
    public void setFigur(Figur figur) {
        this.figur = figur;
    }
    
}