package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * Panel um die Einstellungen zu verwalten.
 * Moegliche Eintellungen:/n 
 * 
 */
public class Einstellungen extends JPanel implements ActionListener {
    // Anfang Attribute
    
    /**
     * Serial-Key zur Identifizierung.
     */
    private static final long serialVersionUID = 6356424037842416199L;
    
    /**
     * Eltern-Fenster Eltern-Fenster in Form einer SpielGUI.
     */
    private SpielGUI parent;
    
    /**
     * Konstante fuer den Farbton des Hintergrundes (Braun).
     */
    private final Color cBraunRot = new Color(172, 59, 32); 
    
    /**
     * Konstante fuer den Farbton der Buttons (Beige).
     */
    private final Color cHellesBeige = new Color(255, 248, 151);
    
    // Ende Attribute
    
    // Konstruktor
    
    /**
     * Erstellt eine neue Einstellungspane.
     * @param parent Eltern-SpielGui auf der die Pane dragestellt werden soll
     */
    public Einstellungen(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    /**
     * Erstellt das Aussehen der Einstellungsseite.
     */
    private void init() {
        this.setLayout(new BorderLayout());
        this.setBackground(cBraunRot);
        
        // North
        Container cNorth = new Container(); 
        cNorth.setLayout(new FlowLayout());
        cNorth.setBackground(cBraunRot);
        JLabel einstellungen = new JLabel("Einstellungen");
        einstellungen.setFont(new Font("Arial", Font.BOLD, 20));
        cNorth.add(einstellungen);
        add(cNorth, BorderLayout.NORTH);
        
        // Center
        Container cCenter = new JPanel();
        cCenter.setLayout(new GridBagLayout());
        cCenter.setBackground(cBraunRot);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JRadioButton ja;
        JRadioButton nein;
        ButtonGroup bGAuswahl;
        
        // Label Zugzeitbegrenzung
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblZugzeitbegrenzung = new JLabel("Zugzeit: ");
        cCenter.add(lblZugzeitbegrenzung, gbc);
        
        // TextPane Zugzeitbegrenzung
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        JTextPane txtZugzeitbegrenzung = new JTextPane();
        txtZugzeitbegrenzung.setBackground(cHellesBeige);
        // TODO Getter auf null überprüfen sonst 0 (infinty) 
        cCenter.add(txtZugzeitbegrenzung, gbc);
        
        // Label Mögliche Felder anzeigen
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblMoeglicheFelder = new JLabel("Mögliche Felder anzeigen? ");
        cCenter.add(lblMoeglicheFelder, gbc);
        
        // RadioButtons Mögliche Felder anzeigen
        gbc.gridx = 1;
        ja = new JRadioButton("Ja");
        ja.setActionCommand("moeglicheFelderJa");
        ja.setBackground(cBraunRot);
        nein = new JRadioButton("Nein");
        nein.setActionCommand("moeglicheFelderNein");
        nein.setBackground(cBraunRot);
        bGAuswahl = new ButtonGroup();
        bGAuswahl.add(ja);
        bGAuswahl.add(nein);
        // TODO Wenn es schon eine Einstellung gibt einstellen sonst nein
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);
        
        // Label bedrohte Figuren anzeigen
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lblBedrohtefelder  
            = new JLabel("Bedrohte Felder anzeigen? ");
        cCenter.add(lblBedrohtefelder, gbc);
        
        // RadioButtons bedrohte Felder anzeigen
        gbc.gridx = 1;
        ja = new JRadioButton("Ja");
        ja.setActionCommand("bedrohteFelderJa");
        ja.setBackground(cBraunRot);
        nein = new JRadioButton("Nein");
        nein.setActionCommand("bedrohteFelderNein");
        nein.setBackground(cBraunRot);
        bGAuswahl = new ButtonGroup();
        bGAuswahl.add(ja);
        bGAuswahl.add(nein);
        // TODO Wenn es schon eine Einstellung gibt einstellen sonst nein
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);
        
        // Label Rochade benutzbar
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel lblRochadeBenutzbar  
            = new JLabel("Rochade nutzbar? ");
        cCenter.add(lblRochadeBenutzbar, gbc);
        
        // Radio Buttons Rochade Benutzbar
        gbc.gridx = 1;
        ja = new JRadioButton("Ja");
        ja.setActionCommand("rochadeBenutzbarJa");
        ja.setBackground(cBraunRot);
        nein = new JRadioButton("Nein");
        nein.setActionCommand("rochadeBenutzbarNein");
        nein.setBackground(cBraunRot);
        bGAuswahl = new ButtonGroup();
        bGAuswahl.add(ja);
        bGAuswahl.add(nein);
        // TODO Wenn es schon eine Einstellung gibt einstellen sonst nein
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);
        
        // Label Schachwarnung
        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel lblSchachwarnung  
            = new JLabel("Schachwarnung anzeigen? ");
        cCenter.add(lblSchachwarnung, gbc);
        
        // Radio Buttons Schachwarnung
        gbc.gridx = 1;
        ja = new JRadioButton("Ja");
        ja.setActionCommand("schachWarnungJa");
        ja.setBackground(cBraunRot);
        nein = new JRadioButton("Nein");
        nein.setActionCommand("schachWarnungNein");
        nein.setBackground(cBraunRot);
        bGAuswahl = new ButtonGroup();
        bGAuswahl.add(ja);
        bGAuswahl.add(nein);
        // TODO Wenn es schon eine Einstellung gibt einstellen sonst nein
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);
        
        
        // Label En-passant-Schlagen
        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel lblEnPassantSchlagen  
            = new JLabel("En-Passant-Schlagen nutzbar? ");
        cCenter.add(lblEnPassantSchlagen, gbc);
        
        // Radio Buttons En-Passant-Schlagen
        gbc.gridx = 1;
        ja = new JRadioButton("Ja");
        ja.setActionCommand("enPassantSchlagenJa");
        ja.setBackground(cBraunRot);
        nein = new JRadioButton("Nein");
        nein.setActionCommand("enPassantSchlagenNein");
        nein.setBackground(cBraunRot);
        bGAuswahl = new ButtonGroup();
        bGAuswahl.add(ja);
        bGAuswahl.add(nein);
        // TODO Wenn es schon eine Einstellung gibt einstellen sonst nein
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);
        
        // Label Statistik
        gbc.gridy = 6;
        gbc.gridx = 0;
        JLabel lblStatistik  
            = new JLabel("Soll das Spiel in die Statistik eingehen? ");
        cCenter.add(lblStatistik, gbc);
        
        // Radio Buttons Statistik
        gbc.gridx = 1;
        ja = new JRadioButton("Ja");
        ja.setActionCommand("statistikJa");
        ja.setBackground(cBraunRot);
        nein = new JRadioButton("Nein");
        nein.setActionCommand("statistikNein");
        nein.setBackground(cBraunRot);
        bGAuswahl = new ButtonGroup();
        bGAuswahl.add(ja);
        bGAuswahl.add(nein);
        // TODO Wenn es schon eine Einstellung gibt einstellen sonst nein
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);
        
        // South
        JButton speichern = new JButton("Einstellungen Speichern");
        speichern.addActionListener(this);
        
        this.add(cCenter, BorderLayout.CENTER);
    }
    
    /**
     * Action Performed für den Speichern Button.
     * @param e ausgeloestes ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        //TODO Auswahl abfragen und setten
        
    }
}
