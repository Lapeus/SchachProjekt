package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Spielerauswahl extends JPanel {
    // Anfang Attribute
    private static final long serialVersionUID = -6920443370361911344L;
    SpielGUI parent;
    JLabel Spielerauswahl = new JLabel("Spielerauswahl");
    JButton spielen = new JButton("Spiel starten");

    // Ende Attribute
    
    // Konstruktor 
    public Spielerauswahl(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    private void init() {
        this.setLayout(new BorderLayout());
        spielen.addActionListener(new SeitenwechselListener(parent));
        spielen.setActionCommand(spielen.getText());
        
        // North
        Container cNorth = new JPanel();
        cNorth.setLayout(new FlowLayout());
        cNorth.add(Spielerauswahl);
        this.add(cNorth, BorderLayout.NORTH);
        
        // West
        
        
        // East
        
        // South 
        Container cSouth = new Container();
        cSouth.setLayout(new FlowLayout());
        cSouth.add(spielen);
        this.add(cSouth);
    }
}
