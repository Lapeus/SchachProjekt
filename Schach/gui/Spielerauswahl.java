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
import java.util.ArrayList;
import java.util.List;

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
 * Klasse f&uuml;r ein Spielerauswahl-Panel. 
 * Implementiert einen eigenen ActionListener der "Spiel starten"-Button-Klicks 
 * verarbeitet
 * @author Marvin Wolf
 */
public class Spielerauswahl extends JPanel implements ActionListener {
    // Anfang Attribute
    
    /**
     * SerialKey zur sp&auml;teren Identifizierung.
     */
    private static final long serialVersionUID = -6920443370361911344L;
    
    /**
     * Konstante f&uuml;r den Farbton den Hintergrundes.
     */
    private Color hintergrund; 
    
    /**
     * Konstante f&uuml;r den Farbton der Auswahlfelder.
     */
    private Color buttonFarbe;
    
    /**
     * Eltern-SpielGUI-Fenster auf der das Spielerauswahl Panel dargestellt 
     * werden soll.
     */
    private SpielGUI parent;
    
    /**
     * Label f&uuml;r den Spielnamen.
     */
    private JLabel lSpielname = new JLabel("Spielname");
    
    /**
     * Button um eine Partie zu starten.
     */
    private JButton bSpielen = new JButton("Spiel starten");
    
    /**
     * Button um den Editor aufzurufen.
     */
    private JButton bEditor = new JButton("Editor");
    
    /**
     * Button um auf die Er&ouml;ffnungsseite zu kommen.
     */
    private JButton btnZurueck = new JButton("<html>Zur&uuml;ck");
    
    /**
     * Textfeld in dem der Spielname eingegeben werden muss.
     */
    private JTextPane tSpielname = new JTextPane();
    
    /**
     * Spieler, der mit dem Namen aus dem Textfeld nameWest erstellt und 
     * sp&auml;ter an die SpielfeldGUI &uuml;bergeben wird.
     */
    private Spieler spieler1;
    
    /**
     * Spieler, der mit dem Namen aus dem Textfeld nameEast erstellt und 
     * sp&auml;ter an die SpielfeldGUI &uuml;bergeben wird.
     */
    private Spieler spieler2;
    
    /**
     * Textfeld zur Eingabe des Namen f&uuml;r Spieler 1.
     */
    private JTextField nameWEST = new JTextField("");
    
    /**
     * Textfeld zur Eingabe des Namen f&uuml;r Spieler 2.
     */
    private JTextField nameEAST = new JTextField("");
    
    /**
     * Combobox zur Auswahl von bereits erstellten Spielern f&uuml;r Spieler 1.
     */
    private JComboBox<String> boxWEST;
    
    /**
     * Combobox zur Auswahl von bereits erstellten Spielern f&uuml;r Spieler 2.
     */
    private JComboBox<String> boxEAST;
    
    /**
     * ButtonGroup aus der im ActionListener die Farbauswahl f&uuml;r Spieler 1 
     * ausgelesen wird.
     */
    private ButtonGroup bGFarbauswahl; 
    
    /**
     * Liste mit verbotenen Spielernamen.
     */
    private List<String> verboten = new ArrayList<String>();

    // Ende Attribute
    
    // Konstruktor
    
    /**
     * Einziger Konstruktor der Klasse Spielerauswahl. Erstellt ein neues 
     * Panel f&uuml;r die Auswahl von zwei Spielern f&uuml;r eine Partie Schach.
     * Die eingegebenen Daten werden, bevor sie ans Backend geschickt werden, 
     * auf ihre Korrektheit &uuml;berpr&uuml;ft.
     * Ruft die init()-Methode auf.
     * @param parent Die ElternGUI auf der das Panel angezeigt werden soll
     */
    public Spielerauswahl(SpielGUI parent) {
        super();
        this.parent = parent;
        hintergrund = parent.getFarben()[0];
        buttonFarbe = parent.getFarben()[1];
        init();
    }
    
    //Methoden Anfang
    
    /**
     * Methode wird vom Konstruktor aufgerufen und modelliert das Panel f&uuml;r
     * die Spielerauswahl.
     * Es gibt zwei Mal die Spielerauswahl, welche in der 
     * {@link #auswahlPanel(String)}-Methode erstellt wird. 
     * Zudem wird ein Eingabefeld f&uuml;r den Spielnamen erstellt und die 
     * Auswahl der Farbe f&uuml;r Spieler 1 wird im Center angezeigt.
     */
    private void init() {
        setLayout(new BorderLayout());
        bEditor.addActionListener(this);
        bSpielen.addActionListener(this);
        bSpielen.setActionCommand(bSpielen.getText());
        setBackground(hintergrund);        
        // North (Spielname)
        Container cNorth = new JPanel();
        cNorth.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        cNorth.setBackground(hintergrund);
        lSpielname.setMinimumSize(new Dimension(150, 50));
        cNorth.add(lSpielname, gbc);
        tSpielname.setBackground(buttonFarbe);
        tSpielname.setToolTipText("Bestehend aus 0..9/a..z/A..Z/Space");
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cNorth.add(tSpielname, gbc);
        
        add(cNorth, BorderLayout.NORTH);
        
        // Liste mit verbotenen Spielernamen fuellen
        verboten.add("C1 Karl Heinz");
        verboten.add("C2 Rosalinde");
        verboten.add("C3 Ursula");
        verboten.add("C4 Walter");
        verboten.add("C5 Harald");
        
        // West (Spieler 1)
        add(auswahlPanel("West"), BorderLayout.WEST);
       
        
        // East (Spieler 2)
        add(auswahlPanel("East"), BorderLayout.EAST);
        
        // Center (Farbauswahl --> Radio Buttons)
        
        // Label Farbauswahl
        Container cCenter = new JPanel();
        cCenter.setBackground(hintergrund);
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
        schwarz.setBackground(hintergrund);
        schwarz.addActionListener(this);
        
        JRadioButton weiss = new JRadioButton("<html>wei&szlig");
        weiss.setActionCommand("weiss");
        weiss.setSelected(true);
        weiss.setBackground(hintergrund);
        weiss.addActionListener(this);
        
        bGFarbauswahl = new ButtonGroup();
        bGFarbauswahl.add(weiss);
        bGFarbauswahl.add(schwarz);
        cCenterMenu.add(weiss);
        cCenterMenu.add(schwarz);
        
        // Center fuellen
        cCenter.add(cCenterLabel);
        cCenter.add(cCenterMenu);
        
        add(cCenter, BorderLayout.CENTER);
        
        // South 
        Container cSouth = new Container();
        cSouth.setLayout(new FlowLayout());
        
        // Spiel Starten Button
        bSpielen.setBackground(buttonFarbe);
        cSouth.add(bSpielen);
        
        // Editor Button
        bEditor.setBackground(buttonFarbe);
        cSouth.add(bEditor);
        
        // Zurueck-Button
        btnZurueck.setBackground(buttonFarbe);
        btnZurueck.addActionListener(new SeitenwechselListener(parent));
        btnZurueck.setActionCommand("Eroeffnungsseite");
        cSouth.add(btnZurueck);
        
        add(cSouth, BorderLayout.SOUTH);
    }
    
    /**
     * Gibt ein Panel mit den Feldern f&uuml;r die Eingabe eines Spielers aus.
     * Wird f&uuml;r jeden Spieler einmal aufgerufen.
     * @param seite Die Seite ("West" oder "East") muss &uuml;bergeben werden 
     * @return Gibt ein Panel zur&uuml;ck, das Felder zur Eingabe von 
     * Spielernamen enth&auml;lt
     */
    private JPanel auswahlPanel(String seite) {
        JPanel eingabePanel = new JPanel();
        eingabePanel.setLayout(new GridLayout(6, 1, 5, 0));
        
        // ComboBox
        JComboBox<String> spielerMenu;
        // Array mit allen Spielern + Computerspieler (EAST)
        if (seite.equals("East")) {
            // +1 da wir als erstes noch "neuer Spieler" einfuegen
            String[] spielerListe 
                = new String[parent.getSpielerListe().size() + 1];
            spielerListe[0] = "neuer Spieler";
            spielerListe[1] = "C1 " + parent.getSpielerListe().get(0).getName();
            spielerListe[2] = "C2 " + parent.getSpielerListe().get(1).getName();
            spielerListe[3] = "C3 " + parent.getSpielerListe().get(2).getName();
            spielerListe[4] = "C4 " + parent.getSpielerListe().get(3).getName();
            spielerListe[5] = "C5 " + parent.getSpielerListe().get(4).getName();
            // Jeden Spielernamen ins Array kopieren
            for (int i = 5; i < spielerListe.length - 1; i++) {
                // +1 da SpielerListe schon "neuer Spieler" enthaehlt
                spielerListe[i + 1] = parent.getSpielerListe().get(i).getName();
            }
            spielerMenu = new JComboBox<String>(spielerListe);
            spielerMenu.addActionListener(this);
            spielerMenu.setActionCommand("boxWEST");
            boxEAST = spielerMenu;
        // Array mit allen Spielern ohne Computerspieler (West)    
        } else {
            // +1 da wir als erstes noch "neuer Spieler" einfuegen
            String[] menschlicheSpielerListe 
                = new String[parent.getMenschlicheSpielerListe().size() + 1];
            menschlicheSpielerListe[0] = "neuer Spieler";
            // Jeden Spielernamen ins Array kopieren
            for (int i = 0; i < menschlicheSpielerListe.length - 1; i++) {
                // +1 da SpielerListe schon "neuer Spieler" enthaehlt
                menschlicheSpielerListe[i + 1] 
                    = parent.getMenschlicheSpielerListe().get(i).getName();
            }
            spielerMenu 
                = new JComboBox<String>(menschlicheSpielerListe);
            spielerMenu.setActionCommand("boxEAST");
            spielerMenu.addActionListener(this);
            boxWEST = spielerMenu;
        }
        
        spielerMenu.setBackground(buttonFarbe);
        eingabePanel.add(spielerMenu);
        
        // Spielername
        JLabel lName = new JLabel("Spielername");
        eingabePanel.add(lName);
        
        // Wenn es fuer die menschlische SpielerListe ist
        if (seite.equals("West")) {
            nameWEST.setBackground(buttonFarbe);
            nameWEST.setToolTipText("Bestehend aus 0..9/a..z/A..Z/Space");
            eingabePanel.add(nameWEST);
        // Wenn es fuer die komlette SpielerListe ist
        } else {
            nameEAST.setBackground(buttonFarbe);
            nameEAST.setToolTipText("Bestehend aus 0..9/a..z/A..Z/Space");
            eingabePanel.add(nameEAST);
        }
        eingabePanel.setBackground(hintergrund);
        return eingabePanel;
    }
    
    // ActionListener

    /**
     * ActionPerformed f&uumlr den Fall, dass der "Spiel starten"-Button 
     * gedr&uuml;ckt wird.
     * Wenn der "Spiel starten"-Button gedr&uuml;ckt wird muss geschaut werden, 
     * ob die ben&ouml;tigten Felder f&uuml;r die Erstellung eines neuen 
     * Spiels ausgef&uuml;llt sind.
     * @param arg0 Ausgel&ouml;stes Event
     */
    public void actionPerformed(ActionEvent arg0) {
        String nameWest = (String) boxWEST.getSelectedItem();
        String nameEast;
        if (((String) boxEAST.getSelectedItem()).equals("neuer Spieler")) {
            nameEast = (String) boxEAST.getSelectedItem();
        } else {
            nameEast = parent.getSpielerListe().get(boxEAST
                .getSelectedIndex() - 1).getName();
        }
        // Wenn der "Spiel starten"-Button gedrueckt wird
        if (arg0.getActionCommand().equals("Spiel starten")
            || arg0.getSource().equals(bEditor)) {
            // Wenn alle Felder korrekt ausgefuellt sind
            if (wennFelderKorrekt(nameWest, nameEast)) {
                // Dann wird mit diesen ein neues Spiel erstellt
                if (arg0.getSource().equals(bEditor)) {
                    spielErstellen(nameWest, nameEast, false);
                } else {
                    spielErstellen(nameWest, nameEast, true);
                }
                
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
     * Pr&uuml;ft ob alle Felder korrekte Eingaben enthalten.
     * Wenn nicht, dann wird die passende Fehlermeldung angezeigt.
     * @param nameWest name im westlichen Textfeld(Spieler 1)
     * @param nameEast name im &ouml;stlichen Textfeld(Spieler 2)
     * @return true wenn alle Felder Korrekt sind
     */
    private boolean wennFelderKorrekt(String nameWest, String nameEast) {
        // Wenn es keinen Fehler gibt wird true zurueckgegeben
        boolean korrekt = true;
        String fehlermeldung = "";
        String spieler1 = nameWEST.getText();
        String spieler2 = nameEAST.getText();
        /* Je nach dem welches(wenn) Feld einen Fehler enthaehlt wird die 
         * Fehlermeldung an dieses Feld angepasst 
         */
        // Spieler1 : Name ist leer oder enthaehlt unerlaubte Zeichen/Namen
        if (nameWest.equals("") || !enthaehltKorrekteZeichen(spieler1) 
            || verboten.contains(spieler1)) {
            fehlermeldung = "<html>Geben Sie einen g&uuml;ltigen Namen "
                + "f&uuml;r Spieler1 an!";
        // Spieler2 : Name ist leer oder enthaehlt unerlaubte Zeichen/Namen
        } else if (nameEast.equals("") || !enthaehltKorrekteZeichen(spieler2)
            || verboten.contains(spieler2)) {
            fehlermeldung = "<html>Geben Sie einen g&uuml;ltigen Namen "
                + "f&uuml;r Spieler2 an!";
        // Spieler 1 und Spieler 2 haben den gleichen Namen
        } else if (nameWEST.getText().equals(nameEAST.getText())) {
            fehlermeldung = "Beide Spieler haben den gleichen Namen!";
        // Spielname ist leer oder enthaehlt unerlaubte Zeichen
        } else if (tSpielname.getText().equals("") 
            || !enthaehltKorrekteZeichen(tSpielname.getText())) {
            fehlermeldung = "Geben Sie einen korrekten Spielnamen ein!";
        // Spielname ist bereits vorhanden
        } else if (spielIstBereitsVorhanden(tSpielname.getText())) {
            fehlermeldung = "Es existiert bereits ein Spiel mit diesem Namen!";
        }
        // Wenn es eine Fehlermeldung gibt
        if (!(fehlermeldung.equals(""))) {
            // Wird diese angezeigt und es wird false zurueckgegeben
            korrekt = false;
            parent.soundAbspielen("FehlerhafteEingabe.wav");
            JOptionPane.showMessageDialog(parent, fehlermeldung);
        }
        return korrekt;
    }
    
    /**
     * &Uuml;berpr&uuml;ft ob eine Zeichenkette nur die Zeichen 0..9/a..z/A..Z 
     * enth&auml;lt.
     * @param zeichenKette zu &uumlberpr&uuml;fende Zeichenkette
     * @return true wenn alle Zeichen g&uuml;ltig sind, sonst false
     */
    private boolean enthaehltKorrekteZeichen(String zeichenKette) {
        // man geht von einer korrekten Zeichenkette aus
        boolean korrekt = true;
        // fuer jeden Charakter
        if (!zeichenKette.equals("") && !(zeichenKette.charAt(0) == ' ')) {
            // -1 da das letzte Zeichen gesondert geprueft wird
            for (int i = 0; i < zeichenKette.length() - 1; i++) {
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
            char letzter = zeichenKette.charAt(zeichenKette.length() - 1);
            int nummer = letzter;
            /* Die letzte Stelle darf kein Leerzeichen sein, da diese beim 
             * speichern(Windows) einfach geloescht werden
             */
            if (!((nummer >= 48 && nummer <= 71) 
                || (nummer >= 65 && nummer <= 90)
                || (nummer >= 97 && nummer <= 122))) {
                korrekt = false;
            }
        } else {
            korrekt = false;
        }
        return korrekt;
    }
    
    /**
     * Erstellt, wenn alle Daten korrekt eingegeben wurden, ein neues Spiel mit
     * den zwei gefilterten Spielern und der Spielerfarbe.
     * @param nameWest Name im westlichen Textfeld(Spieler 1)
     * @param nameEast Name im &ouml;stlichen Textfeld(Spieler 2)
     * @param b welcher Button true - SpielStarten, false - Editor
     */
    private void spielErstellen(String nameWest, String nameEast, boolean b) {
     // Wenn Spieler1 ein neuer Spieler ist
        if (nameWest.equals("neuer Spieler")) { 
            // Wenn der Spieler noch nicht vorhanden ist
            if (wennBereitsVorhanden(nameWEST.getText()) == null) {
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
            if (wennBereitsVorhanden(nameEAST.getText()) == null) {
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
        if (wennBereitsVorhanden(nameWest) != null) {
            // Wird Spieler1 auf diesen gesetzt
            spieler1 = wennBereitsVorhanden(nameWest);
        }
        // Wenn Spieler2 ein bereits vorhandener Spieler ist
        if (wennBereitsVorhanden(nameEast) != null) {
            // Wird Spieler2 auf diesen gesetzt
            spieler2 = wennBereitsVorhanden(nameEast);
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
            if (b) {
                parent.setContentPane(new SpielfeldGUI(parent,
                    tSpielname.getText(), spieler1, spieler2)); 
            } else {
                parent.setContentPane(new Editor(parent,
                    tSpielname.getText(), spieler1, spieler2));
            }
            parent.revalidate();
        } 
    }
    
    /**
     * Gibt zur&uuml;ck ob es schon einen Spieler mit dem neu eingegebenen 
     * Namen gibt.
     * @param name Der Name, nach dem in der Spieler-Liste gesucht werden soll
     * @return <b>null</b> wenn es keinen Spieler gibt, sonst den Spieler 
     */
    private Spieler wennBereitsVorhanden(String name) {
        Spieler spielerVorhanden = null;
        // Spieler-Liste auf den eingegebenen namen pruefen
        for (Spieler spieler : parent.getSpielerListe()) {
            if (spieler.getName().equals(name)) {
                spielerVorhanden = spieler;
            }
        }
        return spielerVorhanden;
    }
    
    /**
     * Gibt zur&uuml;ck, ob ein Spiel mit dem Namen schon existiert.
     * @param name Der Spielname nach dem gesucht werden soll
     * @return true - Spiel gibt es bereits; false - Spiel gibt es noch nicht
     */
    private boolean spielIstBereitsVorhanden(String name) {
        boolean vorhanden = false;
        // Spieler-Liste auf den eingegebenen namen pruefen
        for (String spielname : parent.getSpieleListe()) {
            if (spielname.equals(name)) {
                vorhanden = true;
            }
        }
        return vorhanden;    
    }
    
}
