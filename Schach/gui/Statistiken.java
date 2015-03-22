package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;

import daten.Computerspieler;
import daten.Spieler;
import daten.Statistik;

/**
 * Bietet ein Panel zur Darstellung von Statistiken vom auszuw&auml;hlenden 
 * Spieler des Schachspiels. 
 * @author Marvin Wolf
 */
public class Statistiken extends JPanel implements ActionListener {
    // Anfang Attribute
    /**
     * Serial-Key zur Identifizierung.
     */
    private static final long serialVersionUID = 7874483006424135726L;
    
    /**
     * Eltern-SpielGUI-Fenster, welches die Gesamtdaten verwaltet.
     */
    private SpielGUI parent;
    
    /**
     * JPanel f&uuml;r die Komponenten im NORDEN des Panels.
     */
    private JPanel cNorth = new JPanel();
    
    /**
     * JPanel f&uuml;r die Komponenten in der Mitte des Panels.
     */
    private JPanel cCenter = new JPanel();
    
    /**
     * JComboBox um Spieler auszuw&auml;hlen von dem die Statistik angezeigt 
     * werden soll.
     */
    private JComboBox<String> spielerAuswahl;
    
    /**
     * TextArea in der die Statistik von dem ausgew&auml;hlten Spieler 
     * angezeigt wird.
     */
    private JTextPane daten = new JTextPane();
    
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
     * Erstellt ein neues Statistik-Panel, bei dem man einen Spieler 
     * ausw&auml;hlen kann und dann dessen Statistik angezeigt bekommt.
     * @param parent Eltern-SpielGUI um auf Gesamtdaten zugreifen zu k&ouml;nnen
     */
    public Statistiken(SpielGUI parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout());
        init();
    }
    
    /**
     * Initialisiert eine Statistik-Seite, auf welcher man einen Spieler 
     * ausw&auml;hlen kann und von diesem die Statistik angezeigt bekommt.
     */
    private void init() {
        setBackground(cBraunRot);
        
        // NORTH
        cNorth.setLayout(new BorderLayout());
        cNorth.setBackground(cBraunRot);
        
        // Label Statistik
        JPanel uNorth = new JPanel();
        uNorth.setLayout(new FlowLayout());
        JLabel statistiken = new JLabel("Statistiken: ");
        statistiken.setFont(new Font("Arial", Font.BOLD, 20));
        uNorth.add(statistiken);
        uNorth.setBackground(cBraunRot);
        cNorth.add(uNorth, BorderLayout.NORTH);
        
        // Spielerauswahlmenue
        daten.setEditable(false);
        daten.setBackground(cBraunRot);
        cCenter.setLayout(new BorderLayout());
        cCenter.setBackground(cBraunRot);
        // Spieler Liste des Backends
        List<Spieler> spielerListe = parent.getSpielerListe();
        // +1 wegen "Waehlen sie einen Spieler aus"
        String[] spielernamen = new String[spielerListe.size() + 1];
        spielernamen[0] = "<html>W&auml;hlen Sie einen Spieler aus";
        // Jeden Spieler in das Array packen
        for (int i = 0; i < spielernamen.length - 1; i++) {
            // +1 wegen "Waehlen sie einen Spieler aus"
            spielernamen[i + 1] = spielerListe.get(i).getName();
        }
        // ComboBox mit dem Array als Inhalt erzeugen 
        spielerAuswahl = new JComboBox<String>(spielernamen);
        spielerAuswahl.setBackground(cHellesBeige);
        spielerAuswahl.addActionListener(this);
        cNorth.add(spielerAuswahl, BorderLayout.CENTER);
        
        add(cNorth, BorderLayout.NORTH);
        
        // Center
        HTMLEditorKit hEdi = new HTMLEditorKit();
        // daten kann jetzt HTML-Code &uuml;bersetzen
        daten.setEditorKit(hEdi);
        
        // South
        JPanel cSouth = new JPanel();
        cSouth.setLayout(new FlowLayout());
        cSouth.setBackground(cBraunRot);
        
        // Zur&uuml;cksetzen Button
        JButton btnzuruecksetzen 
            = new JButton("<html>Spielerstatistik zur&uuml;cksetzen");
        btnzuruecksetzen.setActionCommand("zuruecksetzen");
        btnzuruecksetzen.addActionListener(this);
        btnzuruecksetzen.setBackground(cHellesBeige);
        cSouth.add(btnzuruecksetzen);
        
        // Zurueck-Button
        JButton zurueck = new JButton("<html>Zur&uuml;ck");
        zurueck.addActionListener(new SeitenwechselListener(parent));
        zurueck.setActionCommand("Eroeffnungsseite");
        zurueck.setBackground(cHellesBeige);
        cSouth.add(zurueck);
        
        add(cSouth, BorderLayout.SOUTH);
   
        
    }

    /**
     * ActionPerformed-Methode f&uuml;r die auswahlCombobox.
     * Erstellt, wenn ein korrekter Spieler ausgew&auml;hlt wurde, einen String,
     * welcher die Spielerstatistik auf der Textpane darstellt.
     * @param e ausgel&ouml;stes ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String ausgewaehlt = (String) spielerAuswahl.getSelectedItem();
        Spieler spieler = istVorhanden(ausgewaehlt);
        // Wenn es einen Spieler mit diesem Namen gibt
        if (spieler != null) {
            // Wenn man auf zuruecksetzen drueckt wird die Statistik geloescht
            if (e.getActionCommand().equals("zuruecksetzen")) {
                spieler.setStatistik(new Statistik());
            } 
            // Statistik aus dem Backend holen
            Statistik statistik = spieler.getStatistik();
            String momentan;
            // Zeilenumbruch HTML
            String lineSep = "<br>";
            // Folgende Werte werden immer angezeigt
            momentan = "Spiele: " 
                + statistik.getAnzahlSpiele() + lineSep;
            momentan += "Siege: " 
                + statistik.getAnzahlSiege() + lineSep;
            momentan += "Patt: " 
                + statistik.getAnzahlPatt() + lineSep;
            momentan += "Matt: " 
                + statistik.getAnzahlMatt() + lineSep;
            // Nur wenn es kein Computerspieler ist
            if (!(spieler instanceof Computerspieler)) {
                momentan += "Siege vs. Comp: "
                    + statistik.getAnzahlSiegeC() + lineSep;
                momentan += "Patt vs. Comp: " 
                    + statistik.getAnzahlPattC() + lineSep;
                momentan += "Matt vs. Comp: " 
                    + statistik.getAnzahlMattC() + lineSep;
            }
            /* Bei allen Werten die ab hier folgen wird zunaecht ueberprueft ob
             * diese ueberhaupt schon einen Wert haben. Nur dies zutrifft wird 
             * der jeweilige Wert in der Statistik angezeigt
             */
            if (statistik.getSchnellsterSieg() != -1) {
                momentan += "Schnellster Sieg: " 
                    + statistik.getSchnellsterSieg() + " Sekunden" + lineSep;
            }
            if (statistik.getKuerzesterSieg() != -1) {
                momentan += "K&uuml;rzester Sieg: "
                    + statistik.getKuerzesterSieg() + " Z&uuml;ge" + lineSep;
            }
            if (statistik.getSchnellstesMatt() != -1) {
                momentan += "Schnellstes Matt: " 
                    + statistik.getSchnellstesMatt() + " Sekunden" + lineSep;
            }
            if (statistik.getKuerzestesMatt() != -1) {
                momentan += "K&uuml;rzestes Matt:  "
                    + statistik.getKuerzestesMatt() + " Zuege" + lineSep; 
            }
            if (statistik.getZeitSiegDurchschnitt() != -1) {
                momentan += "Durschnittliche Siegzeit: " 
                    + statistik.getZeitSiegDurchschnitt() + " Sekunden" 
                    + lineSep;
            }
            if (statistik.getZeitMattDurchschnitt() != -1) {
                momentan += "Durschnittliche Mattzeit: " 
                    + statistik.getZeitMattDurchschnitt() + " Sekunden" 
                    + lineSep;
            }
            if (statistik.getZuegeSiegDurchschnitt() != -1) {
                momentan += "Durschnittliche Siegz&uuml;ge: " 
                    + statistik.getZuegeSiegDurchschnitt() + " Z&uuml;ge" 
                    + lineSep;
            }
            if (statistik.getZuegeMattDurchschnitt() != -1) {
                momentan += "Durschnittliche Mattz&uuml;ge: " 
                    + statistik.getZuegeMattDurchschnitt() + " Z&uuml;ge" 
                    + lineSep;
            }
            if (statistik.getMatWertSiegDurchschnitt() != -1) {
                momentan += "Durschnittlicher Materialwert Sieg: " 
                    + statistik.getMatWertSiegDurchschnitt() + lineSep;
            }
            if (statistik.getMatWertMattDurchschnitt() != -1) {
                momentan += "Durchschnittlicher Materialwert Matt: "
                    + statistik.getMatWertMattDurchschnitt();
            }
            daten.setBackground(cHellesBeige);
            daten.setText(momentan);
            cCenter.add(daten, BorderLayout.NORTH);
            add(cCenter, BorderLayout.CENTER);
            revalidate();
        }  
    }
    
    /**
     * Gibt SpielerObjekt des Spielers mit diesem Namen zur&uuml;ck.
     * @param name Name des gesuchten Spielers
     * @return Spieler mit dem Namen - <b>null</b> wenn es keinen gibt
     */
    private Spieler istVorhanden(String name) {
        Spieler vorhanden = null;
        for (Spieler spieler : parent.getSpielerListe()) {
            if (spieler.getName().equals(name)) {
                vorhanden = spieler;
            }
        }
        return vorhanden;
    }
}
