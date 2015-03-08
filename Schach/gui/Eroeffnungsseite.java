package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Dient zur Konfiguration der Eroeffnungsseite.
 * Von hier aus kann man zu allen anderen Fenstern wechseln und hier fuehrt der
 * Zurueck-Button aller anderen Panels hin.
 * @author Marvin Wolf
 */
public class Eroeffnungsseite extends Panel {
    
    // Anfang Attribute
    
    /**
     * Serial-Key zur Identifizierung.
     */
    private static final long serialVersionUID = 7611895668957599156L;
    
    /**
     * Eltern-Fenster in Form einer SpielGUI.
     */
    private SpielGUI parent;
    
    /**
     * JButton um in das Spielerauswahlmenue zu kommen (Spiel anfangen).
     */
    private JButton spielen = new JButton("Spielen");
    
    /**
     * JButton um in das Menue zum Spielladen zu kommen.
     */
    private JButton spielLaden = new JButton("Spiel laden");
    
    /**
     * JButton um in das Einstellungsmenue zu kommen.
     */
    private JButton einstellungen = new JButton("Einstellungen");
    
    /**
     * JButton um auf die Highscore-Seite zu kommen.
     */
    private JButton highscore = new JButton("Highscore");
    
    /**
     * JButton um sich die Statistik anzusehen.
     */
    private JButton statistiken = new JButton("Statistiken");
    
    
    /**
     * JLabel um Fenster als Menue zu kennzeichen.
     */
    private JLabel menue = new JLabel("Menü");
    
    /**
     * Konstante fuer den Farbton des Hintergrundes (Braun).
     */
    private final Color cBraunRot = new Color(164, 43, 24); 
    
    /**
     * Konstante fuer den Farbton der Buttons (Beige).
     */
    private final Color cHellesBeige = new Color(255, 248, 151);
    
    // Ende Attribute
    
    // Konstruktor
    
    /**
     * Erstelllt eine eroeffnungsseite.
     * @param parent Elter-Fenster uebergeben
     */
    public Eroeffnungsseite(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    // Anfang Methoden
    
    /**
     * Definiert das Aussehen der Eroeffnungsseite.
     */
    private void init() {
        this.setLayout(new BorderLayout(140, 20));
        this.setBackground(cBraunRot);
        
        // Button ActionListener
        ActionListener wechselListener = new SeitenwechselListener(parent);
        spielen.addActionListener(wechselListener);
        spielen.setActionCommand(spielen.getText());
        spielLaden.addActionListener(wechselListener);
        spielLaden.setActionCommand(spielLaden.getText());
        einstellungen.addActionListener(wechselListener);
        einstellungen.setActionCommand(einstellungen.getText());
        statistiken.addActionListener(wechselListener);
        statistiken.setActionCommand(statistiken.getText());
        highscore.addActionListener(wechselListener);
        highscore.setActionCommand(highscore.getText());
        
        //North
        Container cNorth = new JPanel();
        cNorth.setLayout(new FlowLayout());
        menue.setFont(new Font("Arial", Font.BOLD, 20));
        cNorth.add(menue);
        cNorth.setBackground(cBraunRot);
        this.add(cNorth, BorderLayout.NORTH);
        
        // Center
        Container cCenter = new JPanel();
        cCenter.setBackground(cBraunRot);
        cCenter.setLayout(new GridLayout(0, 1, 0, 40));
        spielen.setBackground(cHellesBeige);
        spielLaden.setBackground(cHellesBeige);
        einstellungen.setBackground(cHellesBeige);
        highscore.setBackground(cHellesBeige);
        statistiken.setBackground(cHellesBeige);
        cCenter.add(spielen);
        cCenter.add(spielLaden);
        cCenter.add(einstellungen);
        cCenter.add(highscore);
        cCenter.add(statistiken);
        this.add(cCenter, BorderLayout.CENTER);
        
        // East 
        JLabel platzhalter = new JLabel();
        this.add(platzhalter, BorderLayout.EAST);
        
        // West 
        JLabel platzhalter2 = new JLabel();
        this.add(platzhalter2, BorderLayout.WEST);
    }

}
