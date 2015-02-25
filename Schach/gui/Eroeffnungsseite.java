package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Eroeffnungsseite extends Panel {
    
    // Anfang Attribute
    
    private static final long serialVersionUID = 7611895668957599156L;
    private JButton einSpieler = new JButton("Ein Spieler");
    private JButton zweiSpieler = new JButton("Zwei Spieler");
    private JButton spielLaden = new JButton("Spiel laden");
    private JButton einstellungen = new JButton("Einstellungen");
    private JButton highscore = new JButton("Highscore");
    private JButton statistiken = new JButton("Statistiken");
    private JButton regelwerk = new JButton("Regelwerk");
    private JLabel menue = new JLabel("Menü");
    
    // Ende Attribute
    
    // Konstruktor
    
    public Eroeffnungsseite() {
        super();
        init();
    }
    
    // Anfang Methoden
    
    public void init() {
        this.setLayout(new BorderLayout());
        
        //North
        Container cNorth = new JPanel();
        cNorth.setLayout(new FlowLayout());
        cNorth.add(menue);
        this.add(cNorth, BorderLayout.NORTH);
        
        // Center
        Container cCenter = new JPanel();
        cCenter.setLayout(new GridLayout(0, 1));
        cCenter.add(einSpieler);
        cCenter.add(zweiSpieler);
        cCenter.add(spielLaden);
        cCenter.add(einstellungen);
        cCenter.add(highscore);
        cCenter.add(statistiken);
        cCenter.add(regelwerk);
        this.add(cCenter, BorderLayout.CENTER);
    }
}
