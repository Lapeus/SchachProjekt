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
     * Container f&uuml;r die Center-Objekte. 
     */
    private Container cCenter;
    
    /**
     * Label f&uuml;r die Information "Spiel laden".
     */
    private JLabel lblSpielLaden = new JLabel("Spiel laden");
    
    /**
     * Button um die laden() Methode der SpielGUI aufzurufen.
     */
    private JButton btnSpielLaden = new JButton("Spiel laden");
    
    /**
     * Button um auf das Startpanel zur&uuml;ckzukehren.
     */
    private JButton btnZurueck = new JButton("<html>Zur&uuml;ck");
    
    /**
     * Liste mit den zu ladenden Spielen.
     */
    private JList<String> jSpieleListe;
    
    /**
     * Konstante f&uuml;r den Farbton den Hintergrunds.
     */
    private Color hintergrund; 
    
    /**
     * Konstante f&uuml;r den Farbton der Auswahlfelder.
     */
    private Color buttonFarbe;
    
    // Ende Attribute
    
    // Konstruktor
    /**
     * Erstellt ein neues SpielLaden Objekt und ruft die init()-Methode auf.
     * Einziger Konstruktor dieser Klasse.
     * @param parent Eltern-SpielGUI-Fenster 
     */
    public SpielLaden(SpielGUI parent) {
        super();
        this.parent = parent;
        hintergrund = parent.getFarben()[0];
        buttonFarbe = parent.getFarben()[1];
        init();
    }
    
    /**
     * Gestaltet ein JPanel mit Auswahlfeldern f&uuml;r eine Spiel laden 
     * Operation.
     */
    private void init() {
        this.setLayout(new BorderLayout());
        this.setBackground(hintergrund);
        
        // North
        Container cNorth = new JPanel();
        cNorth.setBackground(hintergrund);
        cNorth.setLayout(new FlowLayout());
        cNorth.add(lblSpielLaden);
        this.add(cNorth, BorderLayout.NORTH);
        
        // Center
        cCenter = new JPanel();
        cCenter.setBackground(hintergrund);
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
        jSpieleListe.setBackground(buttonFarbe);
        cCenter.add(scrollPane, gbc);
 
        this.add(cCenter, BorderLayout.CENTER);
        
        // South
        JPanel cSouth = new JPanel();
        cSouth.setLayout(new FlowLayout());
        cSouth.setBackground(hintergrund);
        
        // Laden-Button
        btnSpielLaden.addActionListener(this);
        btnSpielLaden.setBackground(buttonFarbe);
        cSouth.add(btnSpielLaden);
        
        // Zurueck-Button
        btnZurueck.addActionListener(new SeitenwechselListener(parent));
        btnZurueck.setActionCommand("Eroeffnungsseite");
        btnZurueck.setBackground(buttonFarbe);
        cSouth.add(btnZurueck);
        
        add(cSouth, BorderLayout.SOUTH);
        
    }
    
    /**
     * ActionPerformed f&uuml;r den Laden-Button.
     * F&auml;ngt die ung&uuml;ltigen Eingaben/Auswahlen ab. 
     * Aktualisiert die Liste, wenn ein falscher (nicht zu ladendes Spiel) 
     * auftaucht.
     * Ruft die Laden-Methode von parent auf.
     * @param e ausgel&ouml;stes ActionEvent 
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
                    cCenter.removeAll();
                    validate();
                    repaint();
                    // Muss ein Fehler ausgegeben werden 
                    parent.soundAbspielen("FehlerBeimLaden.wav");
                    JOptionPane.showMessageDialog(parent, "<html>Das "
                        + "ausgew&auml;hlte Spiel kann nicht geladen werden");
                    /* und die Liste muss neu geladen werden, damit der falsche
                     * Name aus der Liste entfernt wird.
                     */
                    DefaultListModel<String> listModel 
                        = new DefaultListModel<String>();
                    List<String> spieleListe = parent.getSpieleListe();
                    if (spieleListe.size() == 0) {
                        parent.seitenAuswahl("Eroeffnungsseite");
                    } else {
                        jSpieleListe = new JList<String>();
                        for (int i = 0; i < spieleListe.size(); i++) {
                            listModel.addElement((spieleListe.get(i)));
                        }
                        jSpieleListe = new JList<String>(listModel);
                        jSpieleListe.setBackground(buttonFarbe);
                        JScrollPane scrollPane = new JScrollPane(jSpieleListe);
                        cCenter.add(scrollPane);
                    }
                    cCenter.revalidate();
                }
            // Wenn kein Spiel ausgewaehlt wurde
            } else {
                // Hinweis anzeigen
                parent.soundAbspielen("Hinweis.wav");
                JOptionPane.showMessageDialog(parent, "<html>W&auml;hlen Sie "
                    + "ein Spiel zum Laden aus");
            }
            this.revalidate();
        }
    }
}
