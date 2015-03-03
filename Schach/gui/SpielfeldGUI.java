package gui;

import javax.imageio.ImageIO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagLayoutInfo;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import daten.Spiel;
import daten.Spieldaten;
import daten.Spieler;
import daten.Spielfeld;
import figuren.Figur;

/**
 * Eine Klasse die von <b>JPanel</b> und einen eigenen <b>MouseListener</b>
 * implementiert und für die Darstellung des Spielfeldfenster zustädnig ist.
 * @author Marvin Wolf 
 */
public class SpielfeldGUI extends JPanel implements MouseListener, 
    ActionListener {
    
    /**
     * Serial Key zur Identifizierung.
     */
    private static final long serialVersionUID = -5131381120236108231L;
    
    /**
     * Das ElterGUI-Objekt von dem aus das jeweilige Spielfeld aufgerufen wurde.
     */
    private SpielGUI parent;
    
    /**
     * Button um das Spiel zu speichern.
     */
    private JButton speichern = new JButton("Spiel speichern");
    
    /**
     * Button um einen Zug Rueckgaenig zu machen.
     */
    private JButton rueckgaengig = new JButton("Zug Rückgängig");
    
    /**
     * JPanel für die Geschlagenen schwarzen Figuren.
     */
    private JPanel geschlageneSchwarze = new JPanel(); 
    
    /**
     * JPanel für die Geschlagenen schwarzen Figuren.
     */
    private JPanel geschlageneWeisse = new JPanel();
    
    /**
     * Spieler1, welcher von der <b>Spielerauswahl</b> Seite übergeben wird.
     */
    private Spieler spieler1;
    
    /**
     * Spieler2, welcher von der <b>Spielerauswahl</b> Seite übergeben wird.
     */
    private Spieler spieler2;
    
    /**
     * Liste die 64 Schachfelder enthält.
     */
    private List<Feld> felderListe;
    
    /**
     * Objekt der Klasse <b>Spielfeld</b>, welches für Figurenpositionen 
     * benötigt wird.
     */
    private Spielfeld spielfeld;
    
    
    /**
     * Objekt der Klasse <b>Spiel</b>, welche das Spiel speichert zu dem dieses 
     * Spielfeld gehört.
     */
    private Spiel spiel;
    
    /**
     * Enhält eventuell momentan ausgewählte Figur. <br>
     * Wird benötigt um Zug auszuführen, da man ja nur ziehen kann wenn man
     * vorher schon eine Figur ausgewählt hat.
     */
    private Figur ausgewaehlteFigur;
    
    /**
     * JPanel für das Spielfeld. Wird in der <i>spielfeldUpdate</i> Methode 
     * benötigt.  
     */
    private Container cCenter = new JPanel();
    
    /**
     * Kontainer für die Anzeigen und Button neben dem Spielfeld.
     */
    private JPanel cEast = new JPanel();
    
    /**
     * Konstante für den Farbton der "schwarzen" Felder (braun).
     */
    private final Color braun = new Color(181, 81, 16);
    
    /**
     * Konstante für den Farbton der "weißen" Felder (helles Beige).
     */
    private final Color weiss = new Color(255, 248, 151);
    
    /**
     * Konstante für den Farbton der makierten Felder (rot).
     */
    private final Color rot = new Color(204, 0, 0);
    
    /**
     * Action Command für den Rueckgaening-Button.
     */
    private final String commandRueck = "rueck";
    
    /**
     * Action Commmand fuer den Speichern-Button.
     */
    private final String commandSpeichern = "speichern";
    
    
    /**
     * Erzeugt eine SpielfeldGUI.
     * Einziger Konstruktor dieser Klasse!
     * @param parent Das Objekt der dazugehörigen <b>SpielGUI</b>
     * @param spieler1 Ein Objekt der Klasse <b>Spieler</b>
     * @param spieler2 Ein weiteres Objekt der Klasse <b>Spieler</b>
     * @param spielname Name des Spiels muss übergeben werden
     */
    public SpielfeldGUI(SpielGUI parent, String spielname,
        Spieler spieler1, Spieler spieler2) {
        super();
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
        this.parent = parent;
        init();
    }
    
    /**
     * Initialisierungmethode eines Spielfelds.
     * Wird vom Konstruktor aufgerufen. <br>
     * Ruft auf: <br>
     * 1. Füllung der felderListe <br>
     * 2. Erzeugt ein <b>Spielfelds</b> mit dieser felderListe <br>
     * 3. Erzeugt ein <b>Spiel</b> mit Spielname, spieler1, spieler2 und
     *  dem spielfeld
     * 4. Erstellt das Aussehen des Spielfelds  
     */
    private void init() { 
        Dimension size = new Dimension(1200, 800);
        parent.setMinimumSize(size);
        parent.setSize(size);
        parent.setLocationRelativeTo(null);
        
        // FelderListe füllen
        felderListe = new ArrayList<Feld>();
        fuelleFelderListe();
        
        // Spielfeld
        spielfeld = new Spielfeld(felderListe);
        spielfeld.setSpieldaten(new Spieldaten());
        
        // Spiel 
        spiel = new Spiel("Test", spieler1, spieler2, spielfeld);
        
        // CENTER
        this.setLayout(new BorderLayout());
        cCenter.setBackground(new Color(0, 0, 0));
        cCenter.setLayout(new GridLayout(8, 8, 1, 1));
        
        // EAST
        GridBagConstraints gbc = new GridBagConstraints();
        cEast.setLayout(new GridBagLayout());
        
        
        // geschlageneLabelW
        JLabel lGeschlageneW = new JLabel("Geschlagene Weiße Figuren:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.fill = GridBagConstraints.VERTICAL;
        cEast.add(lGeschlageneW, gbc);
        
        //geschlageneLabelB
        JLabel lGeschlageneB = new JLabel("Geschlagene Schwarze Figuren:");
        gbc.gridy = 4;
        cEast.add(lGeschlageneB, gbc);
        
        // geschlagene Weiss
        geschlageneWeisse.setLayout(new GridLayout(2, 8));
        gbc.gridy = 1;
        gbc.gridheight = 2;
        cEast.add(geschlageneWeisse, gbc);
        
        // geschlagene Schwarz
        geschlageneSchwarze.setLayout(new GridLayout(2, 8));
        gbc.gridy = 5;
        cEast.add(geschlageneSchwarze, gbc);
        
        // Button rueck
        rueckgaengig.addActionListener(this);
        rueckgaengig.setActionCommand(commandRueck);
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        cEast.add(rueckgaengig, gbc);
        
        // Button speichern
        speichern.addActionListener(this);
        speichern.setActionCommand(commandSpeichern);
        gbc.gridx = 2;
        gbc.gridy = 3;
        cEast.add(speichern, gbc);
        
        
        
        
        /*
        cEast.setLayout(new BorderLayout());
        JLabel groesse = new JLabel("                                      "
            + "                                                            ");
        
        Container eastCenter = new JPanel();
        eastCenter.setLayout(new GridLayout(8, 1));
        rueckgaengig.addActionListener(this);
        rueckgaengig.setActionCommand(commandRueck);
        eastCenter.add(rueckgaengig);
        
        geschlageneSchwarze.setLayout(new GridLayout(2, 8));
        geschlageneWeisse.setLayout(new GridLayout(2, 8));
        
        cEast.add(groesse, BorderLayout.EAST);
        cEast.add(geschlageneWeisse, BorderLayout.NORTH);
        cEast.add(eastCenter, BorderLayout.CENTER);
        cEast.add(geschlageneSchwarze, BorderLayout.SOUTH);
//        cEast.add(speichern, BorderLayout.CENTER);
*/        
        // Zu Panel hinzufügen
        this.add(cCenter, BorderLayout.CENTER);
        this.add(cEast, BorderLayout.EAST);
        
        // SpielfeldGUI erstellen
        spielfeldAufbau();
        

    }
    /**
     * Dient zum Updaten der SpielfeldGUI nach jeder Veränderung. 
     */
    private void spielfeldAufbau() {
        // boolean für abwechselnd schwarz/weiß
        boolean abwechslung = false;
        // zähler für richtge Position in der Felderliste
        int counter = 56;
        // Für jede Zeile
        for (int i = 0; i < 8; i++) {
            // in der neuen Reihe kommt die gleiche Farbe wie ende letzer Reihe
            abwechslung = !abwechslung;
            // Für jede Spalte
            for (int j = 0; j < 8; j++) {
                /* passendes Feld aus Spielfeld lesen und Hintergrund sichtbar 
                 * machen. Dann den Zähler für die Position in der Liste 
                 * vermindern
                 */ 
                Feld temp = spielfeld.getFelder().get(counter + j);
                temp.setOpaque(true);
                // Wenn die Farbe "schwarz"(Braun) ist dann Feld braun machen
                if (!abwechslung) {
                    temp.setBackground(braun);
                    abwechslung = true;
                // Wenn die Farbe "weiß"(Beige) ist dann Feld beige machen    
                } else {
                    temp.setBackground(weiss);
                    abwechslung = false;
                }
                // Dem cCenter Panel das fertige Feld hinzufügen
                cCenter.add(temp);
            }    
            counter -= 8;
        }
        spielfeldUIUpdate();
    }
    /**
     * Updaten der Spielfeldoberfläche.
     */
    private void spielfeldUIUpdate() {
        if (spielfeld.getSpieldaten().getZugListe().isEmpty()) {
            rueckgaengig.setEnabled(false);
        } else {
            rueckgaengig.setEnabled(true);
        }
        geschlageneFigureUpdate();
        // Alle Bilder löschen damit keine Bilder doppelt bleiben
        for (Feld feld : felderListe) {
            feld.setIcon(null);
        }
        // - schwarze Figurenbilder
        for (Figur schwarz  : spielfeld.getSchwarzeFiguren()) {
            Feld momentan = schwarz.getPosition();
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            if (schwarz.getWert() == 900) {
                try {
                    Image test = ImageIO.read(new File("queenb.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
                
            }
            if (schwarz.getWert() == 100) {
                try {
                    Image test = ImageIO.read(new File("pawnb.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (schwarz.getWert() == 0) {
                try {
                    Image test = ImageIO.read(new File("kingb.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (schwarz.getWert() == 325) {
                try {
                    Image test = ImageIO.read(new File("bishopb.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (schwarz.getWert() == 275) {
                try {
                    Image test = ImageIO.read(new File("knightb.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (schwarz.getWert() == 465) {
                try {
                    Image test = ImageIO.read(new File("rookb.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }
        // - weiße Figurenbilder
        for (Figur weiss  : spielfeld.getWeisseFiguren()) {
            Feld momentan = weiss.getPosition();
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            if (weiss.getWert() == 900) {
                try {
                    Image test = ImageIO.read(new File("queenw.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
                
            }
            if (weiss.getWert() == 100) {
                try {
                    Image test = ImageIO.read(new File("pawnw.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (weiss.getWert() == 0) {
                try {
                    Image test = ImageIO.read(new File("kingw.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (weiss.getWert() == 325) {
                try {
                    Image test = ImageIO.read(new File("bishopw.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (weiss.getWert() == 275) {
                try {
                    Image test = ImageIO.read(new File("knightw.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (weiss.getWert() == 465) {
                try {
                    Image test = ImageIO.read(new File("rookw.gif"));
                    ImageIcon test2  = new ImageIcon(test);
                    momentan.setIcon(test2);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }
        this.revalidate();
    }
    
    /**
     * Füllt die felderListe mit 64 Feldern(Index 0-7, 0-7).
     * Fügt zudem jedem Feld einen MouseListener hinzu (this)
     */
    private void fuelleFelderListe() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Feld temp = new Feld(j, i);
                temp.addMouseListener(this);
                felderListe.add(temp);
            }    
        }
    }
    
    /**
     * Updated die Anzeigen der geschlagenen Figuren.
     */
    private void geschlageneFigureUpdate() {
        geschlageneSchwarze.removeAll();
        geschlageneWeisse.removeAll();
        for (Figur schwarz : spielfeld.getGeschlagenSchwarz()) {
            JLabel momentan = new JLabel();
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            if (schwarz.getWert() == 900) {
                try {
                    Image test = ImageIO.read(new File("queenb.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneSchwarze.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
               
            }
            if (schwarz.getWert() == 100) {
                try {
                    Image test = ImageIO.read(new File("pawnb.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneSchwarze.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (schwarz.getWert() == 0) {
                try {
                    Image test = ImageIO.read(new File("kingb.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneSchwarze.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (schwarz.getWert() == 325) {
                try {
                    Image test = ImageIO.read(new File("bishopb.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneSchwarze.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (schwarz.getWert() == 275) {
                try {
                    Image test = ImageIO.read(new File("knightb.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneSchwarze.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (schwarz.getWert() == 465) {
                try {
                    Image test = ImageIO.read(new File("rookb.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneSchwarze.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            } 
        }
        for (Figur weiss : spielfeld.getGeschlagenWeiss()) {
            JLabel momentan = new JLabel();
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            if (weiss.getWert() == 900) {
                try {
                    Image test = ImageIO.read(new File("queenw.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneWeisse.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
               
            }
            if (weiss.getWert() == 100) {
                try {
                    Image test = ImageIO.read(new File("pawnw.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneWeisse.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (weiss.getWert() == 0) {
                try {
                    Image test = ImageIO.read(new File("kingw.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneWeisse.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (weiss.getWert() == 325) {
                try {
                    Image test = ImageIO.read(new File("bishopw.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneWeisse.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (weiss.getWert() == 275) {
                try {
                    Image test = ImageIO.read(new File("knightw.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneWeisse.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            if (weiss.getWert() == 465) {
                try {
                    Image test = ImageIO.read(new File("rookw.gif"));
                    ImageIcon test2  = new ImageIcon(
                        test.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                    momentan.setIcon(test2);
                    geschlageneWeisse.add(momentan);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
            
        }
        
    }
     
    /**
     * MouseEvent-Methode mouseClicked.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mouseClicked(MouseEvent arg0) {
        // Felder Bewegen
        Feld momentanesFeld = (Feld) arg0.getSource();
        spielfeldAufbau();
        /* (Wenn eine korrekte Figur ausgewählt wird und es noch keine 
         * ausgewaehlte Figur gibt.)
         * ODER
         * (Wenn man dann auf eine seiner eigenen Figuren Klickt, wechselt 
         * die GUI  auf die möglichen Felder dieser Figur.)
         */
        if ((momentanesFeld.getFigur() != null 
            && (momentanesFeld.getFigur().getFarbe() 
            == spielfeld.getAktuellerSpieler()) && ausgewaehlteFigur == null) 
            || 
            (ausgewaehlteFigur != null && momentanesFeld.getFigur() != null 
            && momentanesFeld.getFigur().getFarbe() 
            == ausgewaehlteFigur.getFarbe())) {
            // Wird diese als neue Ausgewählte Figur gespeichert
            ausgewaehlteFigur = momentanesFeld.getFigur();
            /* Wenn der Spieler Weiß dran ist und dies angeklickte Figur eine 
             * weiße ist.
             */
            if (spielfeld.getAktuellerSpieler() 
                && spielfeld.getWeisseFiguren().contains(ausgewaehlteFigur)) {
                // Wird diese als neue Ausgewählte Figur gespeichert
                momentanesFeld.setBackground(rot);
                for (Feld makieren : ausgewaehlteFigur.getKorrektFelder()) {
                    makieren.setBackground(rot);
                }
            }
            /* Wenn der Spieler Schwarz dran ist und dies angeklickte Figur 
             * eine schwarze ist.
             */
            if (!spielfeld.getAktuellerSpieler() 
                && spielfeld.getSchwarzeFiguren().contains(ausgewaehlteFigur)) {
                momentanesFeld.setBackground(rot);
                for (Feld makieren : ausgewaehlteFigur.getKorrektFelder()) {
                    makieren.setBackground(rot);
                }
            }
        // Wenn es bereits eine ausgewaehlte Figur gibt
        } else if (ausgewaehlteFigur != null) {
            /* und das neue ausgewaehlte Feld unter den moeglichen Feldern 
             dieser ist */
            if (ausgewaehlteFigur.getKorrektFelder().contains(momentanesFeld)) {
                spielfeld.ziehe(ausgewaehlteFigur, momentanesFeld);
                ausgewaehlteFigur = null;
                if (spielfeld.isSchach()) {
                    System.out.println("Schach");
                }
                // Wenn das Spiel vorbei ist
                if (spielfeld.schachMatt()) {
                    // TODO Popup Fenster mit SIEG
                    System.out.println("gewonnen");
                }
                spielfeldAufbau();
            /* Wenn nochmal auf das gleiche Feld geklickt wird, wird die
             * Auswahl aufgehoben.
             */
            } else if (ausgewaehlteFigur.getPosition().equals(momentanesFeld)) {
                ausgewaehlteFigur = null;
            }
        }
        // Wenn man ein leeres Feld anklickt
        if (momentanesFeld.getFigur() == null) {
            ausgewaehlteFigur = null;
        }
          
    }
   
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Action Performed fuer alle Buttons.
     * 
     * @param e Ausgeloestes ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(commandRueck)) {
            spielfeld.zugRueckgaengig();
            spielfeldAufbau();
        }
    }
    
}
