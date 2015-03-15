package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import daten.Spieler;

/**
 * Darstelung aller Spieler in einer Highscore-Liste.
 * @author Marvin Wolf
 */
public class Highscore extends JPanel {
    // Anfang Attribute
    /**
     * Serial-Key zur Identifizierung.
     */
    private static final long serialVersionUID = 8641478663933220199L;
    
    /**
     * Eltern-SpielGUI-Fenster.
     */
    private SpielGUI parent;
    
    /**
     * JLabel fuer die Information "Highscore".
     */
    private JLabel lblHighscore = new JLabel("Highscore");
    
    /**
     * Textarea fuer die HighscoreListe.
     */
    private JTextPane highscorePane = new JTextPane();
    
    /**
     * Konstante fuer den Farbton des Hintergrundes (Braun).
     */
    private final Color cBraunRot = new Color(164, 43, 24); 
    
    /**
     * Konstante fuer den Farbton der Buttons (Beige).
     */
    private final Color cHellesBeige = new Color(255, 248, 151);
    
    // Konstruktor
    /**
     * Erzeugt ein neues JPanel welches die Darstellung des Highscores 
     * uebernimmt.
     * @param parent Eltern-SpielGUI-Fenster
     */
    public Highscore(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    /**
     * Kuemmert sich um die Darstellung eines Highscores-Fensters.
     */
    public void init() {
        this.setLayout(new BorderLayout());
        this.setBackground(cBraunRot);
        
        // North 
        Container cNorth = new JPanel();
        cNorth.setBackground(cBraunRot);
        cNorth.setLayout(new FlowLayout());
        lblHighscore.setFont(new Font("Arial", Font.BOLD, 20));
        cNorth.add(lblHighscore);
        this.add(cNorth, BorderLayout.NORTH);
        
        // Center 
        JPanel cCenter = new JPanel();
        cCenter.setBackground(cBraunRot);
        List<Spieler> ranking = parent.getRanking();
        String ergebnis = "";
        int counter = 1;
        String lineSep =  System.getProperty("line.separator");
        for (Spieler spieler : ranking) {
            ergebnis += counter + ". " + spieler.getName() + " " 
                + spieler.getStatistik().getScore() + " Punkte" + lineSep; 
            counter++;
        }
        ergebnis = ergebnis.substring(0, ergebnis.length() - lineSep.length());
        highscorePane.setFont(new Font("Arial", Font.BOLD, 15));
        highscorePane.setText(ergebnis);
        highscorePane.setBackground(cHellesBeige);
        highscorePane.setEditable(false);
        cCenter.add(highscorePane);
        this.add(cCenter, BorderLayout.CENTER);
        
        // South 
        JPanel cSouth = new JPanel();
        cSouth.setBackground(cBraunRot);
        JButton zurueck = new JButton("<html>Zur&uuml;ck");
        zurueck.addActionListener(new SeitenwechselListener(parent));
        zurueck.setActionCommand("Eroeffnungsseite");
        zurueck.setBackground(cHellesBeige);
        cSouth.add(zurueck);
        this.add(cSouth, BorderLayout.SOUTH);
        
        this.revalidate();
    }
}
