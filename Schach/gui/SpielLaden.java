package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import daten.Spiel;

/**
 * Bietet ein JPanel zur Darstellung und Anwendung von zu ladenden Spielen.
 * @author Marvin Wolf
 */
public class SpielLaden extends JPanel implements ActionListener {
    // Angfnag Attribute
    /**
     * Serial-Key zur Identifizierung.
     */
    private static final long serialVersionUID = 2145670587480648629L;
    
    /**
     * Eltern-SpielGUI-Fenster.
     */
    private SpielGUI parent;
    
    /**
     * Container fuer die Center-Objekte. 
     */
    private Container cCenter;
    
    /**
     * Label fuer die Information "Spiel laden".
     */
    private JLabel lblSpielLaden = new JLabel("Spiel laden");
    
    /**
     * Button um die laden() Methode der SpielGUI aufzurufen.
     */
    private JButton btnSpielLaden = new JButton("Spiel laden");
    
    /**
     * Button um auf das Startpanel zurueckzukehren.
     */
    private JButton btnZurueck = new JButton("<html>Zur&uuml;ck");
    
    /**
     * Liste mit den zu ladenden Spielen.
     */
    private JList<String> jSpieleListe;
    
    /**
     * Konstante fuer den Farbton den Hintergrunds (Braun).
     */
    private static Color cBraunRot = new Color(164, 43, 24); 
    
    /**
     * Konstante fuer den Farbton der Auswahlfelder (Beige).
     */
    private static Color cHellesBeige = new Color(255, 248, 151);
    
    // Ende Attribute
    
    // Konstruktor
    /**
     * Ertsellt ein neue SpielLaden Objekt und ruft die init()-Methode auf.
     * Einziger Konstruktor dieser Klasse.
     * @param parent Eltern-SpielGUI-Fenster 
     */
    public SpielLaden(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    /**
     * Gestaltet ein JPanel mit Auswahlfeldern fuer eine Spiel laden Operation.
     */
    private void init() {
        this.setLayout(new BorderLayout());
        this.setBackground(cBraunRot);
        
        // North
        Container cNorth = new JPanel();
        cNorth.setBackground(cBraunRot);
        cNorth.setLayout(new FlowLayout());
        cNorth.add(lblSpielLaden);
        this.add(cNorth, BorderLayout.NORTH);
        
        // Center
        cCenter = new JPanel();
        cCenter.setBackground(cBraunRot);
        cCenter.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // AuswahlListe im JScrollPane eingebettet
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        List<String> spieleListe = parent.getSpieleListe();
        jSpieleListe = new JList<String>();
        // Jedes Spiel dem list Model hinzufuegen
        for (int i = 0; i < spieleListe.size(); i++) {
            listModel.addElement((spieleListe.get(i)));
        }
        // Liste mit dem list Model erstellen
        jSpieleListe = new JList<String>(listModel);
        // und in eine Scrollpane packen
        JScrollPane scrollPane = new JScrollPane(jSpieleListe);
        
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        jSpieleListe.setBackground(cHellesBeige);
        cCenter.add(scrollPane, gbc);
 
        this.add(cCenter, BorderLayout.CENTER);
        
        // South
        JPanel cSouth = new JPanel();
        cSouth.setLayout(new FlowLayout());
        cSouth.setBackground(cBraunRot);
        
        // Laden-Button
        btnSpielLaden.addActionListener(this);
        btnSpielLaden.setBackground(cHellesBeige);
        cSouth.add(btnSpielLaden);
        
        // Zurueck-Button
        btnZurueck.addActionListener(new SeitenwechselListener(parent));
        btnZurueck.setActionCommand("Eroeffnungsseite");
        btnZurueck.setBackground(cHellesBeige);
        cSouth.add(btnZurueck);
        
        add(cSouth, BorderLayout.SOUTH);
        
    }
    
    /**
     * Actionperformed fuer den laden Button.
     * F&auml;ngt die ung&uuml;ltigen Eingaben/Auswahlen ab. 
     * Aktualisiert die Liste, wenn ein falscher(nicht zu ladendes Spiel) 
     * auftaucht.
     * Ruft die laden Methode von parent auf.
     * @param e ausgeloestes ActionEvent 
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnSpielLaden)) {
            String name = jSpieleListe.getSelectedValue();
            // Wenn ein Spiel ausgewaehlt wurde
            if (name != null) {
                Spiel spiel = parent.getSpiel(name);
                // Wenn es ein zu ladendes Spiel zu dieser Auswahl gibt
                if (spiel != null) {
                    parent.setContentPane(new SpielfeldGUI(parent, 
                        spiel));
                // Wenn es keins gibt
                } else {
                    // Muss ein Fehler ausgegeben werden 
                    parent.soundAbspielen("FehlerBeimLaden.wav");
                    JOptionPane.showMessageDialog(parent, "Das ausgewaehlte "
                        + "Spiel kann nicht geladen werden");
                    /* und die Liste muss neu geladen werden, damit der falsche
                     * Name aus der Liste entfernt wird.
                     */
                    DefaultListModel<String> listModel 
                        = new DefaultListModel<String>();
                    List<String> spieleListe = parent.getSpieleListe();
                    jSpieleListe = new JList<String>();
                    for (int i = 0; i < spieleListe.size(); i++) {
                        listModel.addElement((spieleListe.get(i)));
                    }
                    jSpieleListe = new JList<String>(listModel);
                    jSpieleListe.setBackground(cHellesBeige);
                    JScrollPane scrollPane = new JScrollPane(jSpieleListe);
                    cCenter.removeAll();
                    cCenter.add(scrollPane);
                    revalidate();
                }
            // Wenn kein Spiel ausgewaehlt wurde
            } else {
                // Hinweis anzeigen
                parent.soundAbspielen("Hinweis.wav");
                JOptionPane.showMessageDialog(parent, "<html>W&auml;hlen Sie "
                    + "ein Spiel zum Laden aus");
            }
            
            
        }
    }
}
