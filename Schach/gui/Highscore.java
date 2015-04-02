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
 * Darstellung aller Spieler in einer Highscore-Liste.
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
     * JLabel f&uuml;r die Information "Highscore".
     */
    private JLabel lblHighscore = new JLabel("Highscore");
    
    /**
     * Textarea f&uuml;r die Highscore-Liste.
     */
    private JTextPane highscorePane = new JTextPane();
    
    /**
     * Konstante f&uuml;r den Farbton des Hintergrundes (Braun).
     */
    private Color hintergrund; 
    
    /**
     * Konstante f&uuml;r den Farbton der Buttons (Beige).
     */
    private Color buttonFarbe;
    
    // Konstruktor
    /**
     * Erzeugt ein neues JPanel, welches die Darstellung des Highscores 
     * &uuml;bernimmt.
     * @param parent Eltern-SpielGUI-Fenster
     */
    public Highscore(SpielGUI parent) {
        super();
        this.parent = parent;
        hintergrund = parent.getFarben()[0];
        buttonFarbe = parent.getFarben()[1];
        init();
    }
    
    /**
     * K&uuml;mmert sich um die Darstellung eines Highscores-Fensters.
     */
    public void init() {
        setLayout(new BorderLayout());
        setBackground(hintergrund);
        
        // North 
        Container cNorth = new JPanel();
        cNorth.setBackground(hintergrund);
        cNorth.setLayout(new FlowLayout());
        lblHighscore.setFont(new Font("Arial", Font.BOLD, 20));
        cNorth.add(lblHighscore);
        add(cNorth, BorderLayout.NORTH);
        
        // Center 
        JPanel cCenter = new JPanel();
        cCenter.setBackground(hintergrund);
        List<Spieler> ranking = parent.getRanking();
        String ergebnis = "";
        // Counter fuer die anzeige des Bestenlistenmplatz
        int counter = 1;
        // nach jedem Spieler gibt es einen Zeilenumbruch
        String lineSep =  System.getProperty("line.separator");
        // Fuer jeden Spieler
        for (Spieler spieler : ranking) {
            // "Platz" + "Name" + "Score" + "Punkte" + <br>
            ergebnis += counter + ". " + spieler.getName() + " " 
                + spieler.getStatistik().getScore() + " Punkte" + lineSep; 
            counter++;
        }
        // Ergebnis String den letzten LineSep abziehen(sonst Leerzeile)
        ergebnis = ergebnis.substring(0, ergebnis.length() - lineSep.length());
        // String in der Pane hinzufuegen
        highscorePane.setText(ergebnis);
        highscorePane.setBackground(buttonFarbe);
        highscorePane.setEditable(false);
        cCenter.add(highscorePane);
        add(cCenter, BorderLayout.CENTER);
        
        // South 
        JPanel cSouth = new JPanel();
        cSouth.setBackground(hintergrund);
        
        // Button zurueck
        JButton zurueck = new JButton("<html>Zur&uuml;ck");
        zurueck.addActionListener(new SeitenwechselListener(parent));
        zurueck.setActionCommand("Eroeffnungsseite");
        zurueck.setBackground(buttonFarbe);
        
        cSouth.add(zurueck);
        add(cSouth, BorderLayout.SOUTH);
        revalidate();
    }
}
