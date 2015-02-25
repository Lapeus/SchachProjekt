package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class SpielGUI extends JFrame {
    
    // Anfang Attribute
    private static final long serialVersionUID = -8895681303810255159L;
    // Ende Attribute

    // Konstruktor
    
    public SpielGUI() {
        super("Schachspiel");
        initEroeffnungsseite();
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    // Anfang Methoden
    
    public void initEroeffnungsseite() {
        Container eroeffnungsseite = new Eroeffnungsseite();
        this.setContentPane(eroeffnungsseite);
    }
    
    // Ende Methoden
    
    public static void main(String[] args) {
        new SpielGUI();
    }
}
