package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Eroeffnungsseite extends Panel {
    
    // Anfang Attribute
    
    private static final long serialVersionUID = 7611895668957599156L;
    private SpielGUI parent;
    private JButton spielen = new JButton("Spielen");
    private JButton spielLaden = new JButton("Spiel laden");
    private JButton einstellungen = new JButton("Einstellungen");
    private JButton highscore = new JButton("Highscore");
    private JButton statistiken = new JButton("Statistiken");
    private JButton regelwerk = new JButton("Regelwerk");
    private JLabel menue = new JLabel("Menü");
    
    // Ende Attribute
    
    // Konstruktor
    
    public Eroeffnungsseite(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    // Anfang Methoden
    
    private void init() {
        this.setLayout(new BorderLayout(140, 20));
        
        // Button Action Listener
        ActionListener wechselListener = new SeitenwechselListener(parent);
        spielen.addActionListener(wechselListener);
        spielen.setActionCommand(spielen.getText());
        spielLaden.addActionListener(wechselListener);
        spielLaden.setActionCommand(spielLaden.getText());
        einstellungen.addActionListener(wechselListener);
        einstellungen.setActionCommand(einstellungen.getText());
        highscore.addActionListener(wechselListener);
        highscore.setActionCommand(highscore.getText());
        regelwerk.addActionListener(wechselListener);
        regelwerk.setActionCommand(regelwerk.getText());
        
        //North
        Container cNorth = new JPanel();
        cNorth.setLayout(new FlowLayout());
        cNorth.add(menue);
        this.add(cNorth, BorderLayout.NORTH);
        
        // Center
        Container cCenter = new JPanel();
        cCenter.setLayout(new GridLayout(0, 1));
        cCenter.add(spielen);
        cCenter.add(spielLaden);
        cCenter.add(einstellungen);
        cCenter.add(highscore);
        cCenter.add(statistiken);
        cCenter.add(regelwerk);
        this.add(cCenter, BorderLayout.CENTER);
        
        // East 
        JLabel platzhalter = new JLabel();
        this.add(platzhalter, BorderLayout.EAST);
        
        // West 
        JLabel platzhalter2 = new JLabel();
        this.add(platzhalter2, BorderLayout.WEST);
    }

}
