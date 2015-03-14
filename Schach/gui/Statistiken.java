//TODO Ruecksetzen Button

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
import javax.swing.JTextArea;

import daten.Spieler;
import daten.Statistik;

/**
 * Bietet ein Panel zur Dartsellung von Statistiken von auszuwaehlenden Spieler
 * des Schachspiels. 
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
     * JPanel fuer die Komponenten im NORDEN des Panels.
     */
    private JPanel cNorth = new JPanel();
    
    /**
     * JPanel fuer die Komponenten im NORDEN des Panels.
     */
    private JPanel cCenter = new JPanel();
    
    /**
     * JComboBox um Spieler auszuwaehlen von dem die Statistik angezeigt werden
     * soll.
     */
    private JComboBox<String> spielerAuswahl;
    
    /**
     * TextArea in der die Statistik von dem ausgewaehlten Spieler angezeigt 
     * wird.
     */
    private JTextArea daten = new JTextArea();
    
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
     * Erstellt ein neues Statistik-Panel, bei dem man einen Spieler auswaehlen
     * kann und dann dessen Statistik angezeigt bekommt.
     * @param parent Eltern-SpielGUI um auf Gesamtdaten zugreifen zu koennen
     */
    public Statistiken(SpielGUI parent) {
        this.parent = parent;
        this.setLayout(new BorderLayout());
        init();
    }
    
    /**
     * Initialisert eine Statistik-Seite, auf welcher man einen Spieler 
     * auswaehlen kann und von diesem die Statistik angezeigt bekommt.
     */
    private void init() {
        this.setBackground(cBraunRot);
        
        // NORTH
        JLabel statistiken = new JLabel("Statistiken");
        statistiken.setFont(new Font("Arial", Font.BOLD, 20));
        cNorth.setBackground(cBraunRot);
        cNorth.add(statistiken);
        this.add(cNorth, BorderLayout.NORTH);
        
        //Center
        daten.setEditable(false);
        daten.setBackground(cBraunRot);
        cCenter.setLayout(new BorderLayout());
        cCenter.setBackground(cBraunRot);
        List<Spieler> spielerListe = parent.getSpielerListe();
        String[] spielernamen = new String[spielerListe.size() + 1];
        spielernamen[0] = "Waehlen sie einen Spieler aus";
        for (int i = 0; i < spielernamen.length - 1; i++) {
            spielernamen[i + 1] = spielerListe.get(i).getName();
        }
        spielerAuswahl = new JComboBox<String>(spielernamen);
        spielerAuswahl.setBackground(cHellesBeige);
        spielerAuswahl.addActionListener(this);
        cCenter.add(spielerAuswahl, BorderLayout.NORTH);
        
        this.add(cCenter, BorderLayout.CENTER);
        
        // South
        JPanel cSouth = new JPanel();
        cSouth.setLayout(new FlowLayout());
        cSouth.setBackground(cBraunRot);
        
        // Zurueck-Button
        JButton zurueck = new JButton("zurueck");
        zurueck.addActionListener(new SeitenwechselListener(parent));
        zurueck.setActionCommand("Eroeffnungsseite");
        zurueck.setBackground(cHellesBeige);
        cSouth.add(zurueck);
        
        // Zuruecksetzen Button
        JButton btnzuruecksetzen 
            = new JButton("Spielerstatistik zuruecksetzen");
        btnzuruecksetzen.setActionCommand("zuruecksetzen");
        btnzuruecksetzen.addActionListener(this);
        btnzuruecksetzen.setBackground(cHellesBeige);
        cSouth.add(btnzuruecksetzen);
        
        this.add(cSouth, BorderLayout.SOUTH);
   
        
    }
    
    /**
     * Gibt SpielerObjekt des Spielers mit diesem namen zurueck.
     * @param name name des gesuchten Spielers
     * @return Spieler mit dem namen - null wenn es keinen gibt
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
    
    /**
     * ActionPeformed Methode fuer die auswahlCombobox.
     * @param e ausgeloestes ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String ausgewaehlt = (String) spielerAuswahl.getSelectedItem();
        Spieler spieler = istVorhanden(ausgewaehlt);
        if (spieler != null) {
            if (e.getActionCommand().equals("zuruecksetzen")) {
                spieler.setStatistik(new Statistik());
            } 
            daten.removeAll();
            Statistik statistik = spieler.getStatistik();
            String momentan;
            String lineSep = System.getProperty("line.separator");
            momentan = "Spiele: " 
                + statistik.getAnzahlSpiele() + lineSep;
            momentan += "Siege: " 
                + statistik.getAnzahlSiege() + lineSep;
            momentan += "Patt: " 
                + statistik.getAnzahlPatt() + lineSep;
            momentan += "Matt: " 
                + statistik.getAnzahlMatt() + lineSep;
            momentan += "Siege vs. Comp: "
                + statistik.getAnzahlSiegeC() + lineSep;
            momentan += "Patt vs. Comp: " 
                + statistik.getAnzahlPattC() + lineSep;
            momentan += "Matt vs. Comp: " 
                + statistik.getAnzahlMattC() + lineSep;
            if (statistik.getSchnellsterSieg() != -1) {
                momentan += "Schnellster Sieg: " 
                    + statistik.getSchnellsterSieg() + " Sekunden" + lineSep;
            }
            if (statistik.getKuerzesterSieg() != -1) {
                momentan += "Kuerzester Sieg: "
                    + statistik.getKuerzesterSieg() + " Zuege" + lineSep;
            }
            if (statistik.getSchnellstesMatt() != -1) {
                momentan += "Schnellstes Matt: " 
                    + statistik.getSchnellstesMatt() + " Sekunden" + lineSep;
            }
            if (statistik.getKuerzestesMatt() != -1) {
                momentan += "Kuerzestes Matt:  "
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
                momentan += "Durschnittliche Siegzuege: " 
                    + statistik.getZuegeSiegDurchschnitt() + " Zuege" + lineSep;
            }
            if (statistik.getZuegeMattDurchschnitt() != -1) {
                momentan += "Durschnittliche Mattzuege: " 
                    + statistik.getZuegeMattDurchschnitt() + " Zuege" + lineSep;
            }
            if (statistik.getMatWertSiegDurchschnitt() != -1) {
                momentan += "Durschnittlicher Materialwert Sieg: " 
                    + statistik.getMatWertSiegDurchschnitt() + lineSep;
            }
            if (statistik.getMatWertMattDurchschnitt() != -1) {
                momentan += "Durchschnittlicher Materialwert Matt: "
                    + statistik.getMatWertMattDurchschnitt();
            }
            daten.setText(momentan);
            cCenter.add(daten, BorderLayout.CENTER);
            this.revalidate();
        }
        
        
    }
  
    
}
