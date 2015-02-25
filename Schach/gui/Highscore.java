package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Highscore extends JPanel {
    // Anfang Attribute
    private static final long serialVersionUID = 8641478663933220199L;
    SpielGUI parent;
    JLabel highscore = new JLabel("Highscore");
    
    // Konstruktor
    public Highscore(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    public void init() {
        this.setLayout(new BorderLayout());
        
        // North 
        Container cNorth = new JPanel();
        cNorth.setLayout(new FlowLayout());
        cNorth.add(highscore);
        this.add(cNorth, BorderLayout.NORTH);
        
        // Center 
        // TODO Scroll Pane mit Statistiken
    }
}
