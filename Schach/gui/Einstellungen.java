package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class Einstellungen extends JPanel {
    // Anfang Attribute
    private static final long serialVersionUID = 6356424037842416199L;
    SpielGUI parent;
    
    // Ende Attribute
    
    // Konstruktor 
    public Einstellungen(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    private void init() {
        this.setLayout(new BorderLayout());
        
        // North 
    }
}
