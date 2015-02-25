package gui;

import javax.swing.JLabel;

import figuren.Figur;

public class Feld extends JLabel {

    private int x;
    private int y;
    private Figur figur;
    
    public Feld(int x, int y) {
        this.x = x;
        this.y = y;
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