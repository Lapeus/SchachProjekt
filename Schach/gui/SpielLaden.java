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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
     * 
     */
    private static final long serialVersionUID = 2145670587480648629L;
    SpielGUI parent;
    JScrollPane gespeicherteSpiele = new JScrollPane();
    JLabel lblSpielLaden = new JLabel("Spiel Laden");
    JButton btnSpielLaden = new JButton("SpielLaden");
    JComboBox<String> spielAuswahl;
    /**
     * Konstante für den Farbton den Hintergrunds (Braun).
     */
    private static Color cBraunRot = new Color(172, 59, 32); 
    
    /**
     * Konstante für den Farbton der Auswahlfelder (Beige).
     */
    private static Color cHellesBeige = new Color(255, 248, 151);
    
    // Ende Attribute
    
    // Konstruktor
    public SpielLaden(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
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
        Container cCenter = new JPanel();
        cCenter.setBackground(cBraunRot);
        cCenter.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        List<String> spieleListe = parent.getSpieleListe();
        String[] spieleArray = new String[spieleListe.size()];
        for (int i = 0; i < spieleArray.length; i++) {
            spieleArray[i] = spieleListe.get(i);
        }
        spielAuswahl = new JComboBox<String>(spieleArray);
        
        btnSpielLaden.addActionListener(this);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        cCenter.add(spielAuswahl, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        cCenter.add(btnSpielLaden, gbc);
        this.add(cCenter, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnSpielLaden)) {
            String name = (String) spielAuswahl.getSelectedItem();
            parent.setContentPane(new SpielfeldGUI(parent, 
                parent.getSpiel(name)));
        }
    }
    
    /* TODO private List<> gespeicherteSpieleLaden() {
        
    } */
}
