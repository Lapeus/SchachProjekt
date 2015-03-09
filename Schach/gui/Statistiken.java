package gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
        String[] spielernamen = new String[spielerListe.size()];
        for (int i = 0; i < spielernamen.length; i++) {
            spielernamen[i] = spielerListe.get(i).getName();
        }
        spielerAuswahl = new JComboBox<String>(spielernamen);
        spielerAuswahl.setBackground(cHellesBeige);
        spielerAuswahl.addActionListener(this);
        cCenter.add(spielerAuswahl, BorderLayout.NORTH);
        
        // South
        JPanel cSouth = new JPanel();
        cSouth.setBackground(cBraunRot);
        JButton zurueck = new JButton("zurück");
        zurueck.addActionListener(new SeitenwechselListener(parent));
        zurueck.setActionCommand("Eroeffnungsseite");
        zurueck.setBackground(cHellesBeige);
        cSouth.add(zurueck);
        this.add(cSouth, BorderLayout.SOUTH);
        
        
        this.add(cCenter, BorderLayout.CENTER);
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
        daten.removeAll();
        String ausgewaehlt = (String) spielerAuswahl.getSelectedItem();
        Spieler spieler = istVorhanden(ausgewaehlt);
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
        momentan += "Schnellster Sieg: " 
            + statistik.getSchnellsterSieg() + " Sekunden" + lineSep;
        momentan += "Kürzester Sieg: "
            + statistik.getKuerzesterSieg() + " Zuege" + lineSep;
        momentan += "Schnellstes Matt: " 
            + statistik.getSchnellstesMatt() + " Sekunden" + lineSep;
        momentan += "Kürzestes Matt:  "
            + statistik.getKuerzestesMatt() + " Zuege" + lineSep;
        momentan += "Durschnittliche Siegzeit: " 
            + statistik.getZeitSiegDurchschnitt() + " Sekunden" + lineSep;
        momentan += "Durschnittliche Mattzeit: " 
            + statistik.getZeitMattDurchschnitt() + " Sekunden" + lineSep;
        momentan += "Durschnittliche Siegzüge: " 
            + statistik.getZuegeSiegDurchschnitt() + " Zuege" + lineSep;
        momentan += "Durschnittliche Mattzüge: " 
            + statistik.getZuegeMattDurchschnitt() + " Zuege" + lineSep;
        momentan += "Durschnittlicher Materialwert Sieg: " 
            + statistik.getMatWertSiegDurchschnitt() + lineSep;
        momentan += "Durchschnittlicher Materialwert Matt: "
            + statistik.getMatWertMattDurchschnitt();
        daten.setText(momentan);
        cCenter.add(daten, BorderLayout.CENTER);
        this.revalidate();
    }
  
    
}
