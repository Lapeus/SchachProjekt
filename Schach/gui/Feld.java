package gui;

import javax.swing.JLabel;

import figuren.Figur;

public class Feld extends JLabel {

    private static final long serialVersionUID = 5518342568474295458L;
    private int x;
    private int y;
    private Figur figur;
    
    public Feld(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }
    
    public Feld() {
        super();
    }
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Figur getFigur() {
        return figur;
    }
    
    public void setFigur(Figur figur) {
        this.figur = figur;
    }
}