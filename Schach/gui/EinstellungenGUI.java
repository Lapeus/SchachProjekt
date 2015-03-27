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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import daten.Einstellungen;

/**
 * Panel um die Einstellungen zu verwalten.
 */
public class EinstellungenGUI extends JPanel implements ActionListener {
    // Anfang Attribute
    
    /**
     * Serial-Key zur Identifizierung.
     */
    private static final long serialVersionUID = 6356424037842416199L;
    
    /**
     * Eltern-Fenster in Form einer SpielGUI.
     */
    private SpielGUI parent;
    
    /**
     * Einstellungen, welche sp&auml;ter aus der ElternGUI ausgelesen werden. 
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
     * ButtonGroup f&uuml;r ja- und nein- Buttons, damit immer nur einer der 
     * Buttons ausw&auml;hlbar ist.
     */
    private ButtonGroup bGAuswahl;
    
    /**
     * Button f&uuml;r die Speicherung der Spieleinstellungen.
     */
    private JButton speichern;
    
    /**
     * Button um zur&uuml;ck zum Startfenster zu kommen.
     */
    private JButton zurueck;
    
    /**
     * Textpain.
     */
    private JTextPane txtZugzeitbegrenzung;
    
    /**
     * Konstante f&uuml;r den Farbton des Hintergrundes (Braun).
     */
    private final Color cBraunRot = new Color(164, 43, 24); 
    
    /**
     * Konstante f&uuml;r den Farbton der Buttons (Beige).
     */
    private final Color cHellesBeige = new Color(255, 248, 151);
    
    // Ende Attribute
    
    // Konstruktor
    
    /**
     * Erstellt eine neue Einstellungspane.
     * @param parent Eltern-SpielGui auf der die Pane dargestellt werden soll
     */
    public EinstellungenGUI(SpielGUI parent) {
        super();
        this.parent = parent;
        this.einstellungen = parent.getEinstellungen();
        setLayout(new BorderLayout());
        setBackground(cBraunRot);
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
        initCenter1();
        
        // South
        JPanel cSouth = new JPanel();
        cSouth.setBackground(cBraunRot);
        speichern = new JButton("Einstellungen speichern");
        speichern.setBackground(cHellesBeige);
        speichern.addActionListener(this);
        cSouth.add(speichern);        
        zurueck = new JButton("<html>Zur&uuml;ck");
        zurueck.setBackground(cHellesBeige);
        zurueck.setActionCommand("Eroeffnungsseite");
        zurueck.addActionListener(new SeitenwechselListener(parent));
        cSouth.add(zurueck); 
        add(cSouth, BorderLayout.SOUTH);
        
    }
    
    /**
     * Erstellt neue Buttons und gibt ihnen die &uuml;bergebenen ActionCommands.
     * @param commmandJa ActionCommand f&uuml;r den Ja-RadioButton
     * @param commandNein ActionCommand f&uuml;r den Nein-RadioButton
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
     * Erstellt das Auswahlmen&uuml; f&uuml;r die Einstellungen.
     */
    private void initCenter1() {
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
        JLabel lblZugzeitbegrenzung = new JLabel("Zugzeit in Minuten: ");
        cCenter.add(lblZugzeitbegrenzung, gbc);
        // TextPane Zugzeitbegrenzung
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        String zugzeit = (int) (einstellungen.getZugZeitBegrenzung() / 60) + "";
        txtZugzeitbegrenzung = new JTextPane();
        txtZugzeitbegrenzung.setText(zugzeit);
        txtZugzeitbegrenzung.setBackground(cHellesBeige);
        txtZugzeitbegrenzung.setToolTipText("0 = keine Begrenzung, sonst "
            + "(1..9) + (0..9)*");
        cCenter.add(txtZugzeitbegrenzung, gbc);        
        // Label Moegliche Felder anzeigen
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblMoeglicheFelder 
            = new JLabel("<html>M&ouml;gliche Felder anzeigen? ");
        cCenter.add(lblMoeglicheFelder, gbc);
        // RadioButtons Moegliche Felder anzeigen
        auswahlJaNein("moeglicheFelderJa", "moeglicheFelderNein");
        if (einstellungen.isMoeglicheFelderAnzeigen()) {
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
        if (einstellungen.isBedrohteFigurenAnzeigen()) {
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
        if (einstellungen.isRochadeMoeglich()) {
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
        initCenter2(gbc, cCenter);
        
        add(cCenter, BorderLayout.CENTER);
    }
    
    /**
     * Erstellt das Auswahlmen&uuml; f&uuml;r die Einstellungen.
     * @param gbc Entsprechendes GridBagConstraints
     * @param cCenter Entsprechender Container
     */
    private void initCenter2(GridBagConstraints gbc, Container cCenter) {
     // Radio Buttons En-Passant-Schlagen
        auswahlJaNein("enPassantSchlagenJa", "enPassantSchlagenNein");
        if (einstellungen.isEnPassantMoeglich()) {
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
        if (einstellungen.isSchachWarnung()) {
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
        if (einstellungen.isInStatistikEinbeziehen()) {
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
        // Radio Buttons Spielfeld drehen
        auswahlJaNein("drehenJa", "drehenNein");
        if (einstellungen.isSpielfeldDrehen()) {
            ja.setSelected(true);
        } else {
            nein.setSelected(true);
        }
        gbc.gridx = 1;
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);
        // Label Ton
        gbc.gridy = 8;
        gbc.gridx = 0;
        JLabel lblTon  
            = new JLabel("Ton an? ");
        cCenter.add(lblTon, gbc);
        // Radio Buttons Ton
        auswahlJaNein("tonJa", "TonNein");
        if (einstellungen.isTon()) {
            ja.setSelected(true);
        } else {
            nein.setSelected(true);
        }
        gbc.gridx = 1;
        cCenter.add(ja, gbc);
        gbc.gridx = 3;
        cCenter.add(nein, gbc);
    }
    
    /**
     * Gibt zur&uuml;ck ob eine eingegebene Zahl im Zugzeitbegrenzungsfenster 
     * einer korrekten Notation entspricht.
     * @return true wenn ja false wenn nein
     */
    private boolean korrekteZahl() {
        boolean korrekt = true;
        String zahl = txtZugzeitbegrenzung.getText();
        // Wenn die Zahl nicht 0 ist
        if (!zahl.equals("0")) {
            // Und die erste Ziffer keine 0 ist
            if (!(zahl.equals("")) && (zahl.charAt(0) >= 49 && zahl
                .charAt(0) <= 57)) {
                // fuer jede Ziffer pruefen 
                for (int i = 1; i < zahl.length(); i++) {
                    // Wenn nicht zwischen 0..9
                    if (!(zahl.charAt(i) >= 48 && zahl.charAt(i) <= 57)) {
                        // Dann ist die Zahl falsch
                        korrekt = false;
                    }
                }
            // Wenn die erste Ziffer keine Ziffer zwischen 1..9 ist
            } else {
                korrekt = false;
            }
        }
        return korrekt;
        
    }
    
    /**
     * Action Performed f&uuml;r den Speichern Button.
     * @param e ausgel&ouml;stes ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (e.getSource().equals(speichern)) {
            // Wenn die Zugzeitzahl korrekt ist
            if (korrekteZahl()) {
                /* Werden die parent Einstellungen auf die neuen Einstellungen
                 * gesetzt
                 */
                einstellungen.setZugZeitBegrenzung(
                    Integer.parseInt(txtZugzeitbegrenzung.getText()) * 60);
                parent.setEinstellungen(einstellungen);
                parent.soundAbspielen("Hinweis.wav");
                JOptionPane.showMessageDialog(parent,
                    "Einstellungen gespeichert!");
                parent.seitenAuswahl("Eroeffnungsseite");
            // Wenn die Zugzeitzahl nicht korrekt ist
            } else {
                // Wird eine Warnung ausgegeben
                JOptionPane.showMessageDialog(parent, "Geben Sie eine gueltige "
                    + "Zugzeitbegrenzung ein");
            }
        /* Wenn bei einem der Radio-Button-Groups die Auwahl geaendert wird dann
         * wird die Einstellung dieses Fenster auf diese Veraenderung angepasst
         */
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
        } else if (command.equals("tonJa")) {
            einstellungen.setTon(true);
        } else if (command.equals("tonNein")) {
            einstellungen.setTon(false);
        }
    }
}
