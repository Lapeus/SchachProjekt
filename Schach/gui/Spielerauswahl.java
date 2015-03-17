package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import daten.Spieler;
/**
 * Klasse fuer ein Spielerauswahl-Panel. 
 * Implementiert einen eigenen ActionListener der "Spiel starten"-Button Klicks 
 * verarbeitet
 * @author Marvin Wolf
 */
public class Spielerauswahl extends JPanel implements ActionListener {
    // Anfang Attribute
    
    /**
     * SerialKey zur spaeteren Identifizierung.
     */
    private static final long serialVersionUID = -6920443370361911344L;
    
    /**
     * Konstante fuer den Farbton den Hintergrunds (Braun).
     */
    private static Color cBraunRot = new Color(164, 43, 24); 
    
    /**
     * Konstante fuer den Farbton der Auswahlfelder (Beige).
     */
    private static Color cHellesBeige = new Color(255, 248, 151);
    
    /**
     * Eltern SpielGUI-Fenster auf der das Spielerauswahl Panel dargestellt 
     * werden soll.
     */
    private SpielGUI parent;
    
    /**
     * Label fuer den Spielnamen.
     */
    private JLabel lSpielname = new JLabel("Spielname");
    
    /**
     * Button um eine Partie zu starten.
     */
    private JButton bSpielen = new JButton("Spiel starten");
    
    /**
     * Button um auf die Eroeffnungseite zu kommen.
     */
    private JButton btnZurueck = new JButton("<html>Zur&uuml;ck");
    
    /**
     * Textfeld in den der Spielname eingegeben werden muss.
     */
    private JTextPane tSpielname = new JTextPane();
    
    /**
     * Spieler der mit dem Namen aus dem Textfeld nameWest erstellt und 
     * spaeter an die SpielfeldGUI uebergeben wird.
     */
    private Spieler spieler1;
    
    /**
     * Spieler der mit dem Namen aus dem Textfeld nameEast erstellt und 
     * spaeter an die SpielfeldGUI uebergeben wird.
     */
    private Spieler spieler2;
    
    /**
     * Textfeld zur Eingabe des Namen fuer Spieler 1.
     */
    private JTextField nameWEST = new JTextField("");
    
    /**
     * Textfeld zur Eingabe des Namen fuer Spieler 2.
     */
    private JTextField nameEAST = new JTextField("");
    
    /**
     * Combobox zur Auswahl von bereits erstellten Spielern fuer Spieler 1.
     */
    private JComboBox<String> boxWEST;
    
    /**
     * Combobox zur Auswahl von bereits erstellten Spielern fuer Spieler 2.
     */
    private JComboBox<String> boxEAST;
    
    /**
     * ButtonGroup aus der im ActionListener die Farbauswahl fuer Spieler 1 
     * ausgelsen wird.
     */
    private ButtonGroup bGFarbauswahl; 

    // Ende Attribute
    
    // Konstruktor
    
    /**
     * Einziger Konstruktor von der Klasse Sielerauswahl. Erstellt ein neues 
     * Panel fuer die Auswahl von zwei Spielern fuer eine Partie Schach.
     * 
     * @param parent Die ElternGUI auf der das Panel angezeigt werden soll
     */
    public Spielerauswahl(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    //Methoden Anfang
    
    /**
     * Methode wird vom Konstruktor aufgerufen und modelliert das Panel fuer die
     * Spielerauswahl.
     */
    private void init() {
        parent.setMinimumSize(new Dimension(500, 300));
        parent.setSize(new Dimension(200, 200));
        this.setLayout(new BorderLayout());
        bSpielen.addActionListener(this);
        bSpielen.setActionCommand(bSpielen.getText());
        setBackground(cBraunRot);        
        // North (Spielname)
        Container cNorth = new JPanel();
        cNorth.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        cNorth.setBackground(cBraunRot);
        lSpielname.setMinimumSize(new Dimension(150, 50));
        cNorth.add(lSpielname, gbc);
        tSpielname.setBackground(cHellesBeige);
        tSpielname.setToolTipText("Bestehend aus 0..9/a..z/A..Z/Space ");
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cNorth.add(tSpielname, gbc);
        this.add(cNorth, BorderLayout.NORTH);
        
        // West (Spieler 1)
        this.add(auswahlPanel("West"), BorderLayout.WEST);
       
        
        // East (Spieler 2)
        this.add(auswahlPanel("East"), BorderLayout.EAST);
        
        // Center (Farbauswahl --> Radio Buttons)
        
        // Label Farbauswahl
        Container cCenter = new JPanel();
        cCenter.setBackground(cBraunRot);
        cCenter.setLayout(new GridLayout(5, 1));
        
        Container cCenterLabel = new Container();
        cCenterLabel.setLayout(new FlowLayout());
        JLabel lFarbwahl 
            = new JLabel("<html>Farbe f&uuml;r Spieler 1 w&auml;hlen");
        
        cCenterLabel.add(lFarbwahl);
        
        
        // Radio Buttons
        Container cCenterMenu = new Container();
        cCenterMenu.setLayout(new FlowLayout());
        
        JRadioButton schwarz = new JRadioButton("schwarz");
        schwarz.setActionCommand("schwarz");
        schwarz.setBackground(cBraunRot);
        schwarz.addActionListener(this);
        
        JRadioButton weiss = new JRadioButton("<html>wei&szlig");
        weiss.setActionCommand("weiss");
        weiss.setSelected(true);
        weiss.setBackground(cBraunRot);
        weiss.addActionListener(this);
        
        bGFarbauswahl = new ButtonGroup();
        bGFarbauswahl.add(weiss);
        bGFarbauswahl.add(schwarz);
        cCenterMenu.add(weiss);
        cCenterMenu.add(schwarz);
        
        // Center fuellen
        cCenter.add(cCenterLabel);
        cCenter.add(cCenterMenu);
        
        this.add(cCenter, BorderLayout.CENTER);
        
        // South 
        Container cSouth = new Container();
        cSouth.setLayout(new FlowLayout());
        
        // Spiel Starten Button
        bSpielen.setBackground(cHellesBeige);
        cSouth.add(bSpielen);
        
        // Zurueck-Button
        btnZurueck.setBackground(cHellesBeige);
        btnZurueck.addActionListener(new SeitenwechselListener(parent));
        btnZurueck.setActionCommand("Eroeffnungsseite");
        cSouth.add(btnZurueck);
        
        this.add(cSouth, BorderLayout.SOUTH);
    }
    
    /**
     * Gibt ein Panel mit den Feldern fuer die Eingabe eines Spielers aus.
     * Wird fuer jeden Spieler einmal aufgerufen.
     * 
     * @param seite Die Seite ("West" oder "East") muss uebergeben werden 
     * 
     * @return Gibt ein Panel zurueck das Felder zur Eingabe von Spielernamen 
     * enthaelt
     */
    private JPanel auswahlPanel(String seite) {
        JPanel eingabePanel = new JPanel();
        eingabePanel.setLayout(new GridLayout(6, 1, 5, 0));
        
        // ComboBox
        JComboBox<String> spielerMenu;
        // Array mit allen Spielern + Computerspieler (EAST)
        if (seite.equals("East")) {
            String[] spielerListe 
                = new String[parent.getSpielerListe().size() + 1];
            spielerListe[0] = "neuer Spieler";
            for (int i = 0; i < spielerListe.length - 1; i++) {
                spielerListe[i + 1] = parent.getSpielerListe().get(i).getName();
            }
            spielerMenu = new JComboBox<String>(spielerListe);
            spielerMenu.addActionListener(this);
            spielerMenu.setActionCommand("boxWEST");
            boxEAST = spielerMenu;
        } else {
        // Array mit allen Spielern ohne Computerspieler (West)
            String[] menschlicheSpielerListe 
                = new String[parent.getMenschlicheSpielerListe().size() + 1];
            menschlicheSpielerListe[0] = "neuer Spieler";
            for (int i = 0; i < menschlicheSpielerListe.length - 1; i++) {
                menschlicheSpielerListe[i + 1] 
                    = parent.getMenschlicheSpielerListe().get(i).getName();
            }
            spielerMenu 
                = new JComboBox<String>(menschlicheSpielerListe);
            spielerMenu.setActionCommand("boxEAST");
            spielerMenu.addActionListener(this);
            boxWEST = spielerMenu;
        }
        
        
        
        spielerMenu.setBackground(cHellesBeige);
        eingabePanel.add(spielerMenu);
        
        // Spielername
        JLabel lName = new JLabel("Spielername");
        eingabePanel.add(lName);
        
        if (seite.equals("West")) {
            nameWEST.setBackground(cHellesBeige);
            nameWEST.setToolTipText("Bestehend aus 0..9/a..z/A..Z/Space ");
            eingabePanel.add(nameWEST);
        } else {
            nameEAST.setBackground(cHellesBeige);
            nameEAST.setToolTipText("Bestehend aus 0..9/a..z/A..Z/Space ");
            eingabePanel.add(nameEAST);
        }
        eingabePanel.setBackground(cBraunRot);
        return eingabePanel;
    }
    
    /**
     * Gibt zurueck ob es schon einen Spieler mit dem neu Eingegebenen 
     * namen ist.
     * @param name name nach dem in der SpielerListe gesucht werden soll
     * @return true - Spielernamen gibt es schon. false - Spielernamen gibt es 
     * noch nicht 
     */
    private Spieler istBereitsVorhanden(String name) {
        Spieler spielerVorhanden = null;
        for (Spieler spieler : parent.getSpielerListe()) {
            if (spieler.getName().equals(name)) {
                spielerVorhanden = spieler;
            }
        }
        return spielerVorhanden;
    }
    
    /**
     * Gibt zurueck ob ein Spiel mit dem Namen schon existiert.
     * @param name String name nach welchem gesucht werden soll
     * @return true - spiel gibt es bereits false - spiel gibt es noch nicht
     */
    private boolean spielIstBereitsVorhanden(String name) {
        boolean vorhanden = false;
        for (String spielname : parent.getSpieleListe()) {
            if (spielname.equals(name)) {
                vorhanden = true;
            }
        }
        return vorhanden;    
    }
    
    // Methoden Ende
    
    // ActionListener

    /**
     * ActionPerformed fuer den Fall, dass der "Spiel starten"-Button gedrueckt 
     * wird.
     * Wenn der "Spiel starten"-Button gedrueckt wird muss geschaut werden, ob
     * ob die benoetigten Felder fuer die Erstellung eines neuen Spiels 
     * ausgefuellt sind.
     * 
     * @param arg0 Ausgeloestes Event
     */
    public void actionPerformed(ActionEvent arg0) {
        String nameWest = (String) boxWEST.getSelectedItem();
        String nameEast = (String) boxEAST.getSelectedItem();
        // Wenn der "Spiel starten"-Button gedrueckt wird
        if (arg0.getActionCommand().equals("Spiel starten")) {
            // Wenn alle Felder korrekt ausgefuellt sind
            if (wennFelderKorrekt(nameWest, nameEast)) {
                // Dann wird mit diesen ein neues Spiel erstellt
                spielErstellen(nameWest, nameEast);
            }
        } 
        // Wenn eine neue Spielerauswahl fuer den Spieler1 getroffen wurde
        if (arg0.getSource().equals(boxWEST)) {
            // Wenn es nicht der neue Spieler reiter ist
            if (!nameWest.equals("neuer Spieler")) {
                // Das Namensfeld auf diesen Spielernamen setzten 
                nameWEST.setText(nameWest);
                // Und verhindern, dass der name geaendert werden kann
                nameWEST.setEditable(false);
            // Wenn es der neue Spieler reiter ist 
            } else {
                // Dann jeden Text loeschen
                nameWEST.setText("");
                // dafuer sorgen, dass man einen namne eingeben kann
                nameWEST.setEditable(true);
            }
        }
        if (arg0.getSource().equals(boxEAST)) {
            if (!nameEast.equals("neuer Spieler")) {
                nameEAST.setText(nameEast);
                nameEAST.setEditable(false);
            } else {
                nameEAST.setText("");
                nameEAST.setEditable(true);
            }
        }
    }
    
    /**
     * Prueft ob alle Felder Korrekte eingaben enthalten.
     * @param nameWest name im westlichen Textfeld(Spieler 1)
     * @param nameEast name im oestlichen Textfeld(Spieler 2)
     * @return true wenn alle Felder Korrekt sind
     */
    private boolean wennFelderKorrekt(String nameWest, String nameEast) {
        boolean korrekt = true;
        String fehlermeldung = "";
        String spieler1 = nameWEST.getText();
        String spieler2 = nameEAST.getText();
        if (nameWest.equals("") || !enthaehltKorrekteZeichen(spieler1)) {
            fehlermeldung = "<html>Geben Sie einen korrekten Namen f&uuml;r "
                + "Spieler1 an!";
        } else if (nameEast.equals("") 
            || !enthaehltKorrekteZeichen(spieler2)) {
            fehlermeldung = "<html>Geben Sie einen korrekten Namen f&uuml;r "
                + "Spieler2 an!";
        } else if (nameWEST.getText().equals(nameEAST.getText())) {
            fehlermeldung = "Beide Spieler haben den gleichen Namen!";
        } else if (tSpielname.getText().equals("") 
            || !enthaehltKorrekteZeichen(tSpielname.getText())) {
            fehlermeldung = "Geben Sie einen korrekten Spielnamen ein!";
        } else if (spielIstBereitsVorhanden(tSpielname.getText())) {
            fehlermeldung = "Es existiert bereits ein Spiel mit diesem Namen!";
        }
        if (!(fehlermeldung.equals(""))) {
            korrekt = false;
            parent.soundAbspielen("FehlerhafteEingabe.wav");
            JOptionPane.showMessageDialog(parent, fehlermeldung);
        }
        return korrekt;
    }
    
    /**
     * Ueberprueft ob eine Zeichenkette nur die zeichen 0..9/a..z/A..Z enthaelt.
     * @param zeichenKette zu ueberpruefende Zeichenkette
     * @return true wenn alle Zeichen gueltig sind sonst false
     */
    private boolean enthaehltKorrekteZeichen(String zeichenKette) {
        // man geht von einer korrekten Zeichenkette aus
        boolean korrekt = true;
        // fuer jeden Charakter
        if (!zeichenKette.equals("")) {
            for (int i = 0; i < zeichenKette.length(); i++) {
                char zeichen = zeichenKette.charAt(i);
                int nummer = zeichen;
                // Wenn es kein Zeichen zwischen 0..9/a..z/A..Z ist
                if (!((nummer >= 48 && nummer <= 71) 
                    || (nummer >= 65 && nummer <= 90)
                    || (nummer >= 97 && nummer <= 122)
                    || nummer == 32)) {
                    // dann ist die Zeichenkette Fehlerhaft
                    korrekt = false;
                }
            }
        } else {
            korrekt = false;
        }
        return korrekt;
    }
    
    /**
     * Erstellt wenn alle Daten korrekt eingegeben wein neues Spiel mit den 
     * zwei gefiltertetn Spielern und der Spielerfarbe.
     * @param nameWest name im westlichen Textfeld(Spieler 1)
     * @param nameEast name im oestlichen Textfeld(Spieler 2)
     */
    private void spielErstellen(String nameWest, String nameEast) {
     // Wenn Spieler1 ein neuer Spieler ist
        if (nameWest.equals("neuer Spieler")) { 
            // Wenn der Spieler noch nicht vorhanden ist
            if (istBereitsVorhanden(nameWEST.getText()) == null) {
                // Wird der Erste Spieler als Spieler mit dem namen erstellt
                spieler1 = new Spieler(nameWEST.getText());
                // Und der SpielerListe zugefuegt
                parent.addSpieler(spieler1);
            } else {
                // Wird eine Fehlermeldung ausgegeben
                parent.soundAbspielen("FehlerhafteEingabe.wav");
                JOptionPane.showMessageDialog(parent, "Der Spieler " 
                    + nameWEST.getText() + " existiert bereits!", 
                    "Fehlerhafte Eingabe", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Wenn Spieler2 ein neuer Spieler ist
        if (nameEast.equals("neuer Spieler")) {
            // Wenn der Spieler noch nicht vorhanden ist
            if (istBereitsVorhanden(nameEAST.getText()) == null) {
                // Wird der Zweite Spieler als Spieler mit dem namen erstellt
                spieler2 = new Spieler(nameEAST.getText());
                // Und der SpielerListe zugefuegt
                parent.addSpieler(spieler2);
            // Wenn der Spieler schon vorhanden ist    
            } else {
                // Wird eine Fehlermeldung ausgegeben
                parent.soundAbspielen("FehlerhafteEingabe.wav");
                JOptionPane.showMessageDialog(parent, "Der Spieler " 
                    + nameEAST.getText() + " existiert bereits!", 
                    "Fehlerhafte Eingabe", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        // Wenn Spieler1 ein bereits vorhandener Spieler ist
        if (istBereitsVorhanden(nameWest) != null) {
            // Wird Spieler1 auf diesen gesetzt
            spieler1 = istBereitsVorhanden(nameWest);
        }
        // Wenn Spieler2 ein bereits vorhandener Spieler ist
        if (istBereitsVorhanden(nameEast) != null) {
            // Wird Spieler2 auf diesen gesetzt
            spieler2 = istBereitsVorhanden(nameEast);
        }
       
        
        // Wenn zwei Spieler vorhanden sind
        if (spieler1 != null && spieler2 != null) {
            // Wenn Spieler 1 die Farbe weiss ausgewaehlt hat
            if (bGFarbauswahl.getSelection().
                getActionCommand().equals("weiss")) {
                spieler1.setFarbe(true);
                spieler2.setFarbe(false);
            // Wenn Spieler 1 die Farbe schwarz ausgewaehlt hat
            } else {
                spieler1.setFarbe(false);
                spieler2.setFarbe(true);
            }
            /* Die Pane fuer das Hauptfenster auf eine neue SpielfeldGUI
             * setzten und die oben gefilterten Parameter fuer 
             * spielernamen und Spielnamen uebergben
            */
            parent.setContentPane(new SpielfeldGUI(parent,
                tSpielname.getText(), spieler1, spieler2));
            parent.revalidate();
        } 
    }
    
}
