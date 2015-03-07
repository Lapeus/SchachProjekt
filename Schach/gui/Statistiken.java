package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * Bietet ein Panel zur Dartsellung von Statistiken von auszuwaehlenden Spieler
 * des Schachspiels. 
 */
public class Statistiken extends JPanel {
    // Anfang Attribute
    /**
     * Serial-Key zur Identifizierung.
     */
    private static final long serialVersionUID = 7874483006424135726L;
    
    /**
     * Eltern-SpielGUI-Fenster, welches die Gesamtdaten verwaltet.
     */
    private SpielGUI parent;
    
    /**
     * JPanel fuer die Komponenten im NORDEN des Panels.
     */
    private JPanel cNorth;

    // Ende Attribute
    
    // Konstruktor
    /**
     * Erstellt ein neues Statistik-Panel, bei dem man einen Spieler auswaehlen
     * kann und dann dessen Statistik angezeigt bekommt.
     * @param parent Eltern-SpielGUI um auf Gesamtdaten zugreifen zu koennen
     */
    public Statistiken(SpielGUI parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout());
        init();
    }
    
    private void init() {
        
    }
  
    
}
