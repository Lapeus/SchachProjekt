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
 * Dient zur Konfiguration der Er&ouml;ffnungsseite.
 * Von hier aus kann man zu allen anderen Fenstern wechseln und hier f&uuml;hrt 
 * der Zur&uuml;ck-Button aller anderen Panels hin.
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
     * JButton um in das Spielerauswahlmen&uuml; zu kommen (Spiel anfangen).
     */
    private JButton spielen = new JButton("Spielen");
    
    /**
     * JButton um in das Men&uuml; zum Spielladen zu kommen.
     */
    private JButton spielLaden = new JButton("Spiel laden");
    
    /**
     * JButton um in das Einstellungsmen&uuml; zu kommen.
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
     * JLabel um Fenster als Men&uuml; zu kennzeichnen.
     */
    private JLabel menue = new JLabel("<html>Men&uuml;</html>");
    
    /**
     * Konstante f&uuml;r den Farbton des Hintergrundes.
     */
    private Color hintergrund; 
    
    /**
     * Konstante f&uuml;r den Farbton der Buttons.
     */
    private Color buttonFarbe;
    
    // Ende Attribute
    
    // Konstruktor
    
    /**
     * Erstelllt eine Er&ouml;ffnungsseite.
     * @param parent Elter-Fenster &uuml;bergeben
     */
    public Eroeffnungsseite(SpielGUI parent) {
        super();
        this.parent = parent;
        hintergrund = parent.getFarben()[0];
        buttonFarbe = parent.getFarben()[1];
        init();
    }
    
    // Anfang Methoden
    
    /**
     * Definiert das Aussehen der Er&ouml;ffnungsseite.
     */
    private void init() {
        setLayout(new BorderLayout(140, 20));
        setBackground(hintergrund);
        
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
        cNorth.setBackground(hintergrund);
        add(cNorth, BorderLayout.NORTH);
        
        // Center
        Container cCenter = new JPanel();
        cCenter.setBackground(hintergrund);
        cCenter.setLayout(new GridLayout(0, 1, 0, 40));
        spielen.setBackground(buttonFarbe);
        spielLaden.setBackground(buttonFarbe);
        // Wenn es kein zu ladendes Spiel gibt
        if (parent.getSpieleListe().isEmpty()) {
            spielLaden.setEnabled(false);
            spielLaden.setToolTipText("Keine gespeicherten Spiele vorhanden!");
        }
        einstellungen.setBackground(buttonFarbe);
        highscore.setBackground(buttonFarbe);
        statistiken.setBackground(buttonFarbe);
        cCenter.add(spielen);
        cCenter.add(spielLaden);
        cCenter.add(einstellungen);
        cCenter.add(highscore);
        cCenter.add(statistiken);

        add(cCenter, BorderLayout.CENTER);
        
        // East 
        JLabel platzhalter = new JLabel();
        add(platzhalter, BorderLayout.EAST);
        
        // West 
        JLabel platzhalter2 = new JLabel();
        add(platzhalter2, BorderLayout.WEST);
    }

}
