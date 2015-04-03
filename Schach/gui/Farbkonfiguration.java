package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Ein JPanel zur Konfiguration der verwendeten Farben.
 * @author Christian Ackermann
 */
public class Farbkonfiguration extends JPanel implements ActionListener {

    /**
     * Automatisch generierte UID.
     */
    private static final long serialVersionUID = -6760446452920582196L;

    /**
     * Das zugeh&ouml;rige JFrame.
     */
    private SpielGUI parent;
    
    /**
     * Button f&uuml;r die Speicherung der Spieleinstellungen.
     */
    private JButton speichern;
    
    /**
     * Button um die Standardwerte wiederherzustellen.
     */
    private JButton standard;
    /**
     * Button um zur&uuml;ck zum Startfenster zu kommen.
     */
    private JButton zurueck;
    
    /**
     * Konstante f&uuml;r den Farbton des Hintergrundes.
     */
    private Color cHintergrund; 
    
    /**
     * Konstante f&uuml;r den Farbton der Buttons.
     */
    private Color cButtonFarbe;
    
    /**
     * Die Farben.
     */
    private Color[] farben = new Color[11];
    /**
     * Konstruktor.
     * @param parent Das zugeh&ouml;rige JFrame
     */
    public Farbkonfiguration(SpielGUI parent) {
        this.parent = parent;
        cHintergrund = parent.getFarben()[0];
        cButtonFarbe = parent.getFarben()[1];
        int zaehl = 0;
        for (Color color : parent.getFarben()) {
            farben[zaehl] = color;
            zaehl++;
        }
        setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        setBackground(cHintergrund);
        init();
    }
    
    /**
     * Baut die GUI auf.
     */
    private void init() {
        
        JPanel center = new JPanel();
        center.setBackground(Color.white);
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        center.setPreferredSize(new Dimension(240, 400));
        
        JButton hintergrund = new JButton("Hintergrund");
        center.add(hintergrund);
        JButton buttonFarbe = new JButton("Button-Farbe");
        center.add(buttonFarbe);
        JButton schachfeldWeiss = new JButton("<html>Schachfeld Wei&szlig");
        center.add(schachfeldWeiss);
        JButton schachfeldSchwarz = new JButton("Schachfeld Schwarz");
        center.add(schachfeldSchwarz);
        JButton moeglicheFelder = new JButton("<html>M&ouml;gliche Felder");
        center.add(moeglicheFelder);
        JButton schlagendeFelder = new JButton("Schlagende Felder");
        center.add(schlagendeFelder);
        JButton bedrohteFelder = new JButton("Bedrohte Felder");
        center.add(bedrohteFelder);
        JButton letzterZug = new JButton("Letzter Zug");
        center.add(letzterZug);
        JButton schach = new JButton("Schach");
        center.add(schach);
        JButton matt = new JButton("Matt");
        center.add(matt);
        JButton beteiligteFiguren = new JButton("Am Matt beteiligte Figuren");
        center.add(beteiligteFiguren);
        
        // Fuegt allen Buttons einen ActionListener und einen ActionCommand zu
        int zaehl = 0;
        for (Component comp : center.getComponents()) {
            ((JButton) comp).addActionListener(this);
            ((JButton) comp).setActionCommand("" + zaehl);
            ((JButton) comp).setBackground(farben[zaehl]);
            comp.setPreferredSize(new Dimension(200, 25));
            zaehl++;
        }
        
        add(center, BorderLayout.CENTER);
        
        // Buttons unten
        JPanel south = new JPanel();
        south.setBackground(cHintergrund);
        speichern = new JButton("Farben speichern");
        speichern.setBackground(cButtonFarbe);
        speichern.setActionCommand("Speichern");
        speichern.addActionListener(this);
        south.add(speichern);       
        standard = new JButton("Standardwerte");
        standard.setBackground(cButtonFarbe);
        standard.setActionCommand("Standard");
        standard.addActionListener(this);
        south.add(standard);
        zurueck = new JButton("<html>Zur&uuml;ck");
        zurueck.setBackground(cButtonFarbe);
        zurueck.setActionCommand("Zurueck");
        zurueck.addActionListener(this);
        south.add(zurueck); 
        add(south, BorderLayout.SOUTH);
    }

    /**
     * Action-Performed-Methode der Buttons.
     * @param arg0 Das ausgel&ouml;ste ActionEvent
     */
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals("Speichern")) {
            parent.setFarben(farben);
            parent.soundAbspielen("Hinweis.wav");
            JOptionPane.showMessageDialog(parent,
                "Farben gespeichert!");
            parent.seitenAuswahl("Einstellungen");
        } else if (arg0.getActionCommand().equals("Standard")) {
            // Setzt auf Standardwerte zurueck
            parent.setFarben(null);
            farben = parent.getFarben();
            this.removeAll();
            init();
            revalidate();
        } else if (arg0.getActionCommand().equals("Zurueck")) {
            parent.seitenAuswahl("Einstellungen");
        } else {
            Color color = JColorChooser.showDialog(null, "Farbauswahl", null);
            if (color != null) {
                int index = Integer.parseInt(arg0.getActionCommand());
                farben[index] = color;
            }
            this.removeAll();
            init();
            revalidate();
        }
    }
}
