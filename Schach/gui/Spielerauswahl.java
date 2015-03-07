package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

import daten.Spieler;
/**
 * Klasse für ein Spielerauswahl-Panel. 
 * Implementiert einen eigenen ActionListener der "Spiel starten"-Button Klicks 
 * verarbeitet
 * @author Marvin Wolf
 */
public class Spielerauswahl extends JPanel implements ActionListener {
    // Anfang Attribute
    
    /**
     * SerialKey zur späteren Identifizierung.
     */
    private static final long serialVersionUID = -6920443370361911344L;
    
    /**
     * Konstante für den Farbton den Hintergrunds (Braun).
     */
    private static Color cBraunRot = new Color(172, 59, 32); 
    
    /**
     * Konstante für den Farbton der Auswahlfelder (Beige).
     */
    private static Color cHellesBeige = new Color(255, 248, 151);
    
    /**
     * Eltern SpielGUI-Fenster auf der das Spielerauswahl Panel dargestellt 
     * werden soll.
     */
    private SpielGUI parent;
    
    /**
     * Label für den Spielnamen.
     */
    private JLabel lSpielname = new JLabel("Spielname: ");
    
    /**
     * Button um eine Partie zu starten.
     */
    private JButton bSpielen = new JButton("Spiel starten");
    
    /**
     * Textfeld in den der Spielname eingegeben werden muss.
     */
    private JTextField tSpielname = new JTextField("                   ");
    
    /**
     * Spieler der mit dem Namen aus dem Textfeld nameWest erstellt und 
     * später an die SpielfeldGUI übergeben wird.
     */
    private Spieler spieler1;
    
    /**
     * Spieler der mit dem Namen aus dem Textfeld nameEast erstellt und 
     * später an die SpielfeldGUI übergeben wird.
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
     * Combobox zur Auswahl von bereits erstellten Spielern für Spieler 1.
     */
    private JComboBox<String> boxWEST;
    
    /**
     * Combobox zur Auswahl von bereits erstellten Spielern für Spieler 2.
     */
    private JComboBox<String> boxEAST;
    
    /**
     * ButtonGroup aus der im ActionListener die Farbauswahl für Spieler 1 
     * ausgelsen wird.
     */
    private ButtonGroup bGFarbauswahl;

    // Ende Attribute
    
    // Konstruktor
    
    /**
     * Einziger Konstruktor von der Klasse Sielerauswahl. Erstellt ein neues 
     * Panel für die Auswahl von zwei Spielern für eine Partie Schach.
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
     * Methode wird vom Konstruktor aufgerufen und modelliert das Panel für die
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
        cNorth.setLayout(new FlowLayout());
        cNorth.setBackground(cBraunRot);
        lSpielname.setMinimumSize(new Dimension(150, 50));
        cNorth.add(lSpielname);
        tSpielname.setBackground(cHellesBeige);
        cNorth.add(tSpielname);
        this.add(cNorth, BorderLayout.NORTH);
        
        // West (Spieler 1)
        this.add(auswahlPanel("West"), BorderLayout.WEST);
       
        
        // East (Spieler 2)
        this.add(auswahlPanel("East"), BorderLayout.EAST);
        
        
        // South ("Spiel starten"-Button)
        Container cSouth = new Container();
        cSouth.setLayout(new FlowLayout());
        bSpielen.setBackground(cHellesBeige);
        cSouth.add(bSpielen);
        this.add(cSouth, BorderLayout.SOUTH);
        
        // Center (Farbauswahl --> Radio Buttons)
        
        // Label Farbauswahl
        Container cCenter = new JPanel();
        cCenter.setBackground(cBraunRot);
        cCenter.setLayout(new GridLayout(5, 1));
        
        Container cCenterLabel = new Container();
        cCenterLabel.setLayout(new FlowLayout());
        JLabel lFarbwahl = new JLabel("Farbe für Spieler 1 wählen");
        
        cCenterLabel.add(lFarbwahl);
        
        
        // Radio Buttons
        Container cCenterMenu = new Container();
        cCenterMenu.setLayout(new FlowLayout());
        
        JRadioButton schwarz = new JRadioButton("schwarz");
        schwarz.setActionCommand("schwarz");
        schwarz.setBackground(cBraunRot);
        schwarz.addActionListener(this);
        
        JRadioButton weiss = new JRadioButton("weiss");
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
        
    }
    
    /**
     * Gibt ein Panel mit den Feldern für die Eingabe eines Spielers aus.
     * Wird fuer jeden Spieler einmal aufgerufen.
     * 
     * @param seite Die Seite ("West" oder "East") muss übergeben werden 
     * 
     * @return Gibt ein Panel zurück das Felder zur Eingabe von Spielernamen 
     * enthaelt
     */
    private JPanel auswahlPanel(String seite) {
        JPanel eingabePanel = new JPanel();
        eingabePanel.setLayout(new GridLayout(6, 1, 5, 0));
        
        // ComboBox
        // TODO Array mit den Spielern (spielerArray)
        String[] spielerListe = new String[parent.getSpielerListe().size() + 1];
        spielerListe[0] = "neuer Spieler";
        for (int i = 0; i < spielerListe.length - 1; i++) {
            spielerListe[i + 1] = parent.getSpielerListe().get(i).getName();
        }
        JComboBox<String> spielerMenu = new JComboBox<String>(spielerListe);
        spielerMenu.addActionListener(this);
        if (seite.equals("West")) {
            spielerMenu.setActionCommand("boxWEST");
            boxWEST = spielerMenu;
        } else {
            spielerMenu.setActionCommand("boxEAST");
            boxEAST = spielerMenu;
        }
        spielerMenu.setBackground(cHellesBeige);
        eingabePanel.add(spielerMenu);
        
        // Spielername
        JLabel lName = new JLabel("Spielername");
        eingabePanel.add(lName);
        
        if (seite.equals("West")) {
            nameWEST.setBackground(cHellesBeige);
            eingabePanel.add(nameWEST);
        } else {
            nameEAST.setBackground(cHellesBeige);
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
    
    // Methoden Ende
    
    // ActionListener

    /**
     * ActionPerformed für den Fall, dass der "Spiel starten"-Button gedrückt 
     * wird.
     * Wenn der "Spiel starten"-Button gedrückt wird muss geschaut werden, ob
     * ob die benötigten Felder für die Erstellung eines neuen Spiels 
     * ausgefuellt sind.
     * 
     * @param arg0 Ausgeloestes Event
     */
    public void actionPerformed(ActionEvent arg0) {
        String nameWest = (String) boxWEST.getSelectedItem();
        String nameEast = (String) boxEAST.getSelectedItem();
        // Wenn der "Spiel starten"-Button gedrueckt wird
        if (arg0.getActionCommand().equals("Spiel starten")) {
            // Wenn zwei Spielernamen und ein Spiename vorhanden sind
            if (!((nameWEST.getText().equals("")) 
                || nameEAST.getText().equals("") 
                || tSpielname.getText().equals("                   "))) {
                
                // Wenn Spieler1 ein neuer Spieler ist
                if (nameWest.equals("neuer Spieler") 
                    && !(istBereitsVorhanden(nameWest) == null)) {
                    spieler1 = new Spieler(nameWEST.getText());
                    parent.addSpieler(spieler1);
                }
                // Wenn Spieler2 ein neuer Spieler ist
                if (nameEast.equals("neuer Spieler")) {
                    spieler2 = new Spieler(nameEAST.getText());
                    parent.addSpieler(spieler2);
                }
                
                // Wenn Spieler1 ein bereits vorhandener Spieler ist
                if (istBereitsVorhanden(nameWest) != null) {
                    spieler1 = istBereitsVorhanden(nameWest);
                }
                // Wenn Spieler2 ein bereits vorhandener Spieler ist
                if (istBereitsVorhanden(nameEast) != null) {
                    spieler2 = istBereitsVorhanden(nameEast);
                }
                
                // Wenn Spieler 1 die Farbe weiss ausgewählt hat
                if (bGFarbauswahl.getSelection().
                    getActionCommand().equals("weiss")) {
                    spieler1.setFarbe(true);
                    spieler2.setFarbe(false);
                // Wenn Spieler 1 die Farbe schwarz ausgewählt hat
                } else {
                    spieler1.setFarbe(false);
                    spieler2.setFarbe(true);
                }
                /* Die Pane für das Hauptfenster auf eine neue SpielfeldGUI
                 * setzten und die oben gefilterten Parameter für spielernamen
                 * und Spielnamen übergben
                */
                parent.setContentPane(new SpielfeldGUI(parent,
                    tSpielname.getText(), spieler1, spieler2));
                parent.revalidate();
            } else {
                JOptionPane.showMessageDialog(parent, "Geben sie bitte gültige"
                    + " Namen für die das Spiel und die Spieler ein bzw. wählen"
                    + " sie bereits vorhandenen Spielerprofile aus",
                    "Fehlerhafte Eingabe", JOptionPane.WARNING_MESSAGE);
            }
        } 
        if (arg0.getActionCommand().equals("boxWEST")) {
            if (!nameWest.equals("neuer Spieler")) {
                nameWEST.setText(nameWest);
                nameWEST.setEditable(false);
            }
        } 
        if (arg0.getActionCommand().equals("boxEAST")) {           
            if (!nameEast.equals("neuer Spieler")) {
                nameEAST.setText(nameEast);
                nameEAST.setEditable(false);
            }
        }
    }

}
