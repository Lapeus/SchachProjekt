package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import daten.Einstellungen;

/**
 * Panel um die Einstellungen zu verwalten.
 * Moegliche Eintellungen:/n 
 * 
 */
public class EinstellungenGUI extends JPanel implements ActionListener {
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
     * Einstellungen welche spaeter aus der ElternGUI ausgelesen werden. 
     */
    private Einstellungen einstellungen;
    
    /**
     * RadioButton zur positiven Auswahl einer Option.
     */
    private JRadioButton ja;
    
    /**
     * RadioButton zur negativen Auswahl einer Option.
     */
    private JRadioButton nein;
    
    /**
     * ButtonGroup fuer ja- und nein- Buttons, damit immer nur einer der Buttons
     * auswaehlbar ist.
     */
    private ButtonGroup bGAuswahl;
    
    /**
     * Button fuer die Speicherung der Spieleinstellungen.
     */
    private JButton speichern;
    
    /**
     * Button um zurueck zum Startfenster zu kommen.
     */
    private JButton zurueck;
    
    /**
     * Textpain.
     */
    private JTextPane txtZugzeitbegrenzung;
    
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
     * Erstellt eine neue Einstellungspane.
     * @param parent Eltern-SpielGui auf der die Pane dragestellt werden soll
     */
    public EinstellungenGUI(SpielGUI parent) {
        super();
        this.parent = parent;
        this.einstellungen = parent.getEinstellungen();
        this.setLayout(new BorderLayout());
        this.setBackground(cBraunRot);
        init();
    }
    
    /**
     * Erstellt das Aussehen der Einstellungsseite.
     */
    private void init() {
        // North
        Container cNorth = new Container(); 
        cNorth.setLayout(new FlowLayout());
        cNorth.setBackground(cBraunRot);
        JLabel einstellungen = new JLabel("Einstellungen");
        einstellungen.setFont(new Font("Arial", Font.BOLD, 20));
        cNorth.add(einstellungen);
        add(cNorth, BorderLayout.NORTH);  
       
        // Center
        initCenter();
        
        // South
        JPanel cSouth = new JPanel();
        cSouth.setBackground(cBraunRot);
        speichern = new JButton("Einstellungen Speichern");
        speichern.setBackground(cHellesBeige);
        speichern.addActionListener(this);
        cSouth.add(speichern);        
        zurueck = new JButton("Zurück");
        zurueck.setBackground(cHellesBeige);
        zurueck.setActionCommand("Eroeffnungsseite");
        zurueck.addActionListener(new SeitenwechselListener(parent));
        cSouth.add(zurueck); 
        this.add(cSouth, BorderLayout.SOUTH);
        
    }
    
    /**
     * Erstellt neue Buttons und gibt ihnen die uebergebenen ActionCommands.
     * @param commmandJa ActionCommand fuer den Ja-RadioButton
     * @param commandNein ActionCommand fuer den Nein-RadioButton
     */
    private void auswahlJaNein(String commmandJa,
        String commandNein) {
        List<JRadioButton> radioButtons = new ArrayList<JRadioButton>();
        ja = new JRadioButton("Ja");
        ja.addActionListener(this);
        ja.setActionCommand(commmandJa);
        ja.setBackground(cBraunRot);
        nein = new JRadioButton("Nein");
        nein.addActionListener(this);
        nein.setActionCommand(commandNein);
        nein.setBackground(cBraunRot);
        bGAuswahl = new ButtonGroup();
        bGAuswahl.add(ja);
        bGAuswahl.add(nein);
        radioButtons.add(ja);
        radioButtons.add(nein);
    }
    
    /**
     * Erstellt das Auswahlmenü fuer die Einstellungen.
     */
    private void initCenter() {
        // Center
        Container cCenter = new JPanel();
        cCenter.setLayout(new GridBagLayout());
        cCenter.setBackground(cBraunRot);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        // Label Zugzeitbegrenzung
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblZugzeitbegrenzung = new JLabel("Zugzeit: ");
        cCenter.add(lblZugzeitbegrenzung, gbc);
        // TextPane Zugzeitbegrenzung
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        String zugzeit = this.einstellungen.getZugZeitBegrenzung() + "";
        txtZugzeitbegrenzung = new JTextPane();
        txtZugzeitbegrenzung.setText(zugzeit);
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
        auswahlJaNein("moeglicheFelderJa", "moeglicheFelderNein");
        if (this.einstellungen.isMoeglicheFelderAnzeigen()) {
            ja.setSelected(true);
        } else {
            nein.setSelected(true);
        }
        gbc.gridx = 1;
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
        auswahlJaNein("bedrohteFelderJa", "bedrohteFelderNein");
        if (this.einstellungen.isBedrohteFigurenAnzeigen()) {
            ja.setSelected(true);
        } else {
            nein.setSelected(true);
        }
        gbc.gridx = 1;
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
        auswahlJaNein("rochadeBenutzbarJa", "rochadeBenutzbarNein");
        if (this.einstellungen.isRochadeMoeglich()) {
            ja.setSelected(true);
        } else {
            nein.setSelected(true);
        }
        gbc.gridx = 1;
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);        
        // Label En-passant-Schlagen
        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel lblEnPassantSchlagen  
            = new JLabel("En-Passant-Schlagen nutzbar? ");
        cCenter.add(lblEnPassantSchlagen, gbc);
        // Radio Buttons En-Passant-Schlagen
        auswahlJaNein("enPassantSchlagenJa", "enPassantSchlagenNein");
        if (this.einstellungen.isEnPassantMoeglich()) {
            ja.setSelected(true);
        } else {
            nein.setSelected(true);
        }
        gbc.gridx = 1;
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);       
        // Label Schachwarnung
        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel lblSchachwarnung  
            = new JLabel("Schachwarnung anzeigen? ");
        cCenter.add(lblSchachwarnung, gbc);
        // Radio Buttons Schachwarnung
        auswahlJaNein("schachwarnungJa", "schachwarnungNein");
        if (this.einstellungen.isSchachWarnung()) {
            ja.setSelected(true);
        } else {
            nein.setSelected(true);
        }
        gbc.gridx = 1;
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
        auswahlJaNein("statistikJa", "statistikNein");
        if (this.einstellungen.isInStatistikEinbeziehen()) {
            ja.setSelected(true);
        } else {
            nein.setSelected(true);
        }
        gbc.gridx = 1;
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);
        
        // Label Spielfeld drehen
        gbc.gridy = 7;
        gbc.gridx = 0;
        JLabel lblSpielfeldDrehen  
            = new JLabel("Soll das Spielfeld gedreht werden ?");
        cCenter.add(lblSpielfeldDrehen, gbc);
        // Radio Buttons Statistik
        auswahlJaNein("drehenJa", "derehenNein");
        if (this.einstellungen.isSpielfeldDrehen()) {
            ja.setSelected(true);
        } else {
            nein.setSelected(true);
        }
        gbc.gridx = 1;
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);
        
        this.add(cCenter, BorderLayout.CENTER);
    }
    
    /**
     * Action Performed für den Speichern Button.
     * @param e ausgeloestes ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (e.getSource().equals(speichern)) {
            einstellungen.setZugZeitBegrenzung(
                Integer.parseInt(txtZugzeitbegrenzung.getText()));
            parent.setEinstellungen(einstellungen);
        } else if (command.equals("drehenJa")) {
            einstellungen.setSpielfeldDrehen(true);
        } else if (command.equals("drehenNein")) {    
            einstellungen.setSpielfeldDrehen(false);
        } else if (command.equals("moeglicheFelderJa")) {
            einstellungen.setMoeglicheFelderAnzeigen(true);
        } else if (command.equals("moeglicheFelderNein")) {
            einstellungen.setMoeglicheFelderAnzeigen(false);
        } else if (command.equals("bedrohteFelderJa")) {
            einstellungen.setBedrohteFigurenAnzeigen(true);
        } else if (command.equals("bedrohteFelderNein")) {
            einstellungen.setBedrohteFigurenAnzeigen(false);
        } else if (command.equals("rochadeBenutzbarJa")) {
            einstellungen.setRochadeMoeglich(true);
        } else if (command.equals("rochadeBenutzbarNein")) {
            einstellungen.setRochadeMoeglich(false);
        } else if (command.equals("enPassantSchlagenJa")) {
            einstellungen.setEnPassantMoeglich(true);
        } else if (command.equals("enPassantSchlagenNein")) {
            einstellungen.setEnPassantMoeglich(false);
        } else if (command.equals("schachwarnungJa")) {
            einstellungen.setSchachWarnung(true);
        } else if (command.equals("schachwarnungNein")) {
            einstellungen.setSchachWarnung(false);
        } else if (command.equals("statistikJa")) {
            einstellungen.setInStatistikEinbeziehen(true);
        } else if (command.equals("statistikNein")) {
            einstellungen.setInStatistikEinbeziehen(false);
        } 
    }
}
