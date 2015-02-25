package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SpielLaden extends JPanel {
    // Angfnag Attribute
    private static final long serialVersionUID = 2145670587480648629L;
    SpielGUI parent;
    JScrollPane gespeicherteSpiele = new JScrollPane();
    JLabel spielLaden = new JLabel("Spiel Laden");
    
    // Ende Attribute
    
    // Konstruktor
    public SpielLaden(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    private void init() {
        this.setLayout(new BorderLayout());
        
        // North
        Container cNorth = new JPanel();
        cNorth.setLayout(new FlowLayout());
        cNorth.add(spielLaden);
        this.add(cNorth, BorderLayout.NORTH);
        
    }
    
    /* TODO private List<> gespeicherteSpieleLaden() {
        
    } */
}
