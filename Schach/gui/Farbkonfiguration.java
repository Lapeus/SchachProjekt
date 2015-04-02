package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Ein JPanel zur Konfiguration der verwendeten Farben.
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
     * Button um zur&uuml;ck zum Startfenster zu kommen.
     */
    private JButton zurueck;
    
    /**
     * Konstante f&uuml;r den Farbton des Hintergrundes.
     */
    private Color cHintergrund = new Color(164, 43, 24); 
    
    /**
     * Konstante f&uuml;r den Farbton der Buttons.
     */
    private Color cButtonFarbe = new Color(255, 248, 151);
    
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
        int zaehl = 0;
        for (Color color : parent.getFarben()) {
            farben[zaehl] = color;
            zaehl++;
        }
        setLayout(new BorderLayout());
        setBackground(cHintergrund);
        init();
    }
    
    /**
     * Baut die GUI auf.
     */
    private void init() {
        
        JPanel center = new JPanel();
        center.setBackground(Color.white);
        center.setLayout(new GridLayout(0, 1, 50, 15));
        
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
