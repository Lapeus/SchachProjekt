package gui;

import javax.imageio.ImageIO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import daten.Computerspieler;
import daten.Spiel;
import daten.Spieldaten;
import daten.Spieler;
import daten.Spielfeld;
import daten.Zug;
import figuren.Figur;

/**
 * Eine Klasse die von <b>JPanel</b> und einen eigenen <b>MouseListener</b>
 * implementiert und für die Darstellung des Spielfeldfenster zustädnig ist.
 * @author Marvin Wolf 
 */
public class SpielfeldGUI extends JPanel implements MouseListener, 
    ActionListener, Runnable {
    
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
     * Button um aufzugeben.
     */
    private JButton aufgeben = new JButton("Spiel Aufgeben");
    
    /**
     * JLabel zum Anzeigen des momentanen Spielers.
     */
    private JLabel momentanerSpieler = new JLabel("Weiß");
    
    /**
     * JLabel zum Anzeigen der Zugzeit des momentanen Spielers.
     */
    private JLabel zugzeit = new JLabel("  ");
    
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
     * Spielname welcher von der <b>Spielerauswahl</b> Seite übergeben wird.
     */
    private String spielname;
    
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
     * Panel welches nach Beendigung eines Spiels cEast ersetzt.
     */
    private JPanel cEnde = new JPanel();
    
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
     * Action Command fuer den Aufgeben-Button.
     */
    private final String commandAufgeben = "aufgeben";
    
    /**
     * Action Command fuer den Startmenue-Button.
     */
    private final String commandStartmenue = "Eroeffnungsseite";
    
    /**
     * Startzeit für die Zugzeit-Stoppuhr (Ueber Systemzeit). 
     */
    private int sekundenStart;
    
    /**
     * Endzeit für die Zugzeit-Stoppuhr (Endezeit - Startzeit). 
     */
    private int sekundenStopp;
    
    /**
     * Gibt an ob die Uhr aktiv ist.
     */
    private boolean uhrAktiv = false; 
    
    
    /**
     * Erzeugt eine SpielfeldGUI.
     * @param parent Das Objekt der dazugehörigen <b>SpielGUI</b>
     * @param spieler1 Ein Objekt der Klasse <b>Spieler</b>
     * @param spieler2 Ein weiteres Objekt der Klasse <b>Spieler</b>
     * @param spielname Name des Spiels muss übergeben werden
     */
    public SpielfeldGUI(SpielGUI parent, String spielname,
        Spieler spieler1, Spieler spieler2) {
        super();
        this.parent = parent;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
        this.spielname = spielname;
        
        // FelderListe füllen
        felderListe = new ArrayList<Feld>();
        fuelleFelderListe();
        
        // Spielfeld
        spielfeld = new Spielfeld(felderListe);
        spielfeld.setEinstellungen(parent.getEinstellungen());
        spielfeld.setSpieldaten(new Spieldaten());
        
        // Spiel 
        spiel = new Spiel(spielname, spieler1, spieler2, spielfeld);
        
        init();
    }
    
    /**
     * 
     */
    public SpielfeldGUI(SpielGUI parent, Spiel spiel) {
        this.parent = parent;
        this.spieler1 = spiel.getSpieler1();
        this.spieler2 = spiel.getSpieler2();
        this.spielname = spiel.getSpielname();
        this.spielfeld = spiel.getSpielfeld();
        this.felderListe = spielfeld.getFelder();
        init();
    }
    
    /**
     * Initialisierungmethode eines Spielfelds.
     * Wird vom Konstruktor aufgerufen. <br>
     * Ruft auf: <br>
     * 1. Füllung der felderListe <br>
     * 2. Erzeugt ein <b>Spielfeld</b> mit dieser felderListe <br>
     * 3. Erzeugt ein <b>Spiel</b> mit Spielname, spieler1, spieler2 und
     *  dem spielfeld
     * 4. Erstellt das Aussehen des Spielfelds  
     */
    private void init() { 
        Dimension size = new Dimension(1200, 800);
        parent.setMinimumSize(size);
        parent.setSize(size);
        parent.setLocationRelativeTo(null);
        
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
        gbc.insets = new Insets(20, 10, 10, 20);
        cEast.add(lGeschlageneW, gbc); 
        
        //geschlageneLabelB
        JLabel lGeschlageneB = new JLabel("Geschlagene Schwarze Figuren:");
        gbc.gridy = 8;
        cEast.add(lGeschlageneB, gbc);
        
        // geschlagene Weiss
        geschlageneWeisse.setLayout(new GridLayout(2, 8));
        gbc.gridy = 1;
        gbc.gridheight = 2;
        cEast.add(geschlageneWeisse, gbc);
        
        // geschlagene Schwarz
        geschlageneSchwarze.setLayout(new GridLayout(2, 8));
        gbc.gridy = 9;
        cEast.add(geschlageneSchwarze, gbc);
        
        // Label momentanerSpieler
        gbc.gridheight = 1;
        gbc.gridy = 3;
        cEast.add(momentanerSpieler, gbc);
        
        // Label Stoppuhr
        gbc.gridy = 7;
        cEast.add(zugzeit, gbc);
        
        // Button rueck
        rueckgaengig.addActionListener(this);
        rueckgaengig.setActionCommand(commandRueck);
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        cEast.add(rueckgaengig, gbc);
        
        // Button speichern
        speichern.addActionListener(this);
        speichern.setActionCommand(commandSpeichern);
        gbc.gridx = 2;
        gbc.gridy = 4;
        cEast.add(speichern, gbc);
        
        // Button aufgeben
        aufgeben.addActionListener(this);
        aufgeben.setActionCommand(commandAufgeben);
        gbc.gridx = 2;
        gbc.gridy = 6;
        cEast.add(aufgeben, gbc);
        
        // cEnde
        JButton startmenue = new JButton("Zurück zum Startmenü");
        startmenue.setActionCommand(commandStartmenue);
        startmenue.addActionListener(new SeitenwechselListener(parent));
        cEnde.add(startmenue);
        
        
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
        // Alle Bilder löschen damit keine Bilder doppelt bleiben
        for (Feld feld : felderListe) {
            feld.setIcon(null);
        }
        // - schwarze Figurenbilder
        for (Figur schwarz  : spielfeld.getSchwarzeFiguren()) {
            Feld momentan = schwarz.getPosition();
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            String name = "";
            if (schwarz.getWert() == 900) {
                name = "queenb.gif";
            }
            if (schwarz.getWert() == 100) {
                name = "pawnb.gif";
            }
            if (schwarz.getWert() == 0) {
                name = "kingb.gif";
            }
            if (schwarz.getWert() == 325) {
                name = "bishopb.gif";
            }
            if (schwarz.getWert() == 275) {
                name = "knightb.gif";
            }
            if (schwarz.getWert() == 465) {
                name = "rookb.gif";
            }
            try {
                Image test = ImageIO.read(new File(name));
                ImageIcon test2  = new ImageIcon(test);
                momentan.setIcon(test2);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
        // - weiße Figurenbilder
        for (Figur weiss  : spielfeld.getWeisseFiguren()) {
            Feld momentan = weiss.getPosition();
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            String name = "";
            if (weiss.getWert() == 900) {
                name = "queenw.gif";
            }
            if (weiss.getWert() == 100) {
                name = "pawnw.gif";
            }
            if (weiss.getWert() == 0) {
                name = "kingw.gif";
            }
            if (weiss.getWert() == 325) {
                name = "bishopw.gif";
            }
            if (weiss.getWert() == 275) {
                name = "knightw.gif";
            }
            if (weiss.getWert() == 465) {
                name = "rookw.gif";
            }
            try {
                Image test = ImageIO.read(new File(name));
                ImageIcon test2  = new ImageIcon(test);
                momentan.setIcon(test2);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
        geschlageneFigureUpdate();
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
        for (Figur schwarz : spielfeld.getGeschlagenSchwarzSort()) {
            JLabel momentan = new JLabel();
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            String name = "";
            if (schwarz.getWert() == 900) {
                name = "queenb.gif";
            }
            if (schwarz.getWert() == 100) {
                name = "pawnb.gif";
            }
            if (schwarz.getWert() == 0) {
                name = "kingb.gif";
            }
            if (schwarz.getWert() == 325) {
                name = "bishopb.gif";
            }
            if (schwarz.getWert() == 275) {
                name = "knightb.gif";
            }
            if (schwarz.getWert() == 465) {
                name = "rookb.gif";
            }
            try {
                Image imageB = ImageIO.read(new File(name));
                ImageIcon iconB  = new ImageIcon(
                    imageB.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                momentan.setIcon(iconB);
                geschlageneSchwarze.add(momentan);
            } catch (IOException exc) {
                exc.printStackTrace();
            }  
        }
               
        geschlageneWeisse.removeAll();
        for (Figur weiss : spielfeld.getGeschlagenWeissSort()) {
            JLabel momentan = new JLabel();
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            String name = "";
            if (weiss.getWert() == 900) {
                name = "queenw.gif";
            }
            if (weiss.getWert() == 100) {
                name = "pawnw.gif";
            }
            if (weiss.getWert() == 0) {
                name = "kingw.gif";
            }
            if (weiss.getWert() == 325) {
                name = "bishopw.gif";
            }
            if (weiss.getWert() == 275) {
                name = "knightw.gif";
            }
            if (weiss.getWert() == 465) {
                name = "rookw.gif";
            }
            try {
                Image imageW = ImageIO.read(new File(name));
                ImageIcon iconW  = new ImageIcon(
                    imageW.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
                momentan.setIcon(iconW);
                geschlageneWeisse.add(momentan);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
    
    /**
     * Hier werden die Züge veranlasst und auf der Gui geupdated.
     * @param momentanesFeld Das momentan ausgewählte Feld
     */
    private void zugGUI(Feld momentanesFeld) {
        // HIER WIRD GEZOGEN
        spielfeld.ziehe(ausgewaehlteFigur, momentanesFeld,
            sekundenStopp);
        // Start der zugzeit
        start();
        // Neuer Spieler = keine Ausgewählte Figur
        ausgewaehlteFigur = null;
        
        spielfeld.getBedrohteFelder();
        // Je nach aktuellem Spieler wird das Label gesetzt
        if (spielfeld.getAktuellerSpieler()) {
            momentanerSpieler.setText("Weiß");
        } else {
            momentanerSpieler.setText("Schwarz");
        }
        List<Zug> zugliste 
            = spielfeld.getSpieldaten().getZugListe();
        Zug letzterZug = zugliste.get(zugliste.size() - 1);
        // Wenn das Spiel vorbei ist
        if (spielfeld.schachMatt()) {
            spielfeldAufbau();
            // wird die Stoppuhr angehalten
            uhrAktiv = false;
            // das Spiel ausgewertet
            List<Object> auswertung = spiel.auswertung();
            Spieler gewinner = (Spieler) auswertung.get(0);
            String ergebnis; 
            String zuege = auswertung.get(2).toString();
            if ((boolean) auswertung.get(1)) {
                ergebnis = gewinner.getName() 
                    + " gewinnt nach " + zuege + " Zügen.";
            } else {
                ergebnis = "Das Spiel endet mit einem Patt";
            }
            // Und Ein Dialogfenster für den Gewinner angezeigt
            JOptionPane.showMessageDialog(parent
                , ergebnis);
            // Das spiel ist vorbei also keine Züge mehr möglich
            for (Feld feld : felderListe) {
                feld.removeMouseListener(this);
            }
            this.remove(cEast);
            this.add(cEnde, BorderLayout.EAST);
            this.revalidate();
        // Wenn der momentane Spieler im Schach steht
        } else if (spielfeld.isSchach()) {
            spielfeldAufbau();
            JOptionPane.showMessageDialog(parent, 
                "Sie stehen im Schach!", "Schachwarnung!",
                JOptionPane.WARNING_MESSAGE);
            spielfeld.setSchach(false);
        } else if (letzterZug.isUmwandlung())   {
            spielfeldAufbau();
            String[] moeglicheFiguren = {"Dame", "Turm", "Läufer", 
                "Springer"};
            String s = (String) JOptionPane.showInputDialog(parent,
                "Wählen sie eine Figur aus die sie gegen den "
                + "Bauern tauschen Wollen"
                , "Figurenwechsel", JOptionPane.
                PLAIN_MESSAGE, null, moeglicheFiguren, "Dame");
            int wert;
            if (s.equals("Dame")) {
                wert = 900;
            } else if (s.equals("Turm")) {
                wert = 465;
            } else if (s.equals("Läufer")) {
                wert = 325;
            } else {
                wert = 275;
            }
            spielfeld.umwandeln(letzterZug.getFigur(), wert); 
        }
    }
     
    /**
     * MouseEvent-Methode mouseClicked.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mouseClicked(MouseEvent arg0) {
        spielfeldAufbau();
        
        // Wenn spieler 1 ein Computergegner ist und dran ist
        if ((spieler1 instanceof Computerspieler 
            && spieler1.getFarbe() == spielfeld.getAktuellerSpieler())) {
            ((Computerspieler) spieler1).setSpielfeld(spielfeld);
            ((Computerspieler) spieler1).ziehen();
            spielfeldAufbau();
        // Wenn spieler 2 ein Computergegner ist und dran ist
        } else if (spieler2 instanceof Computerspieler 
            && spieler2.getFarbe() == spielfeld.getAktuellerSpieler()) {
            ((Computerspieler) spieler2).setSpielfeld(spielfeld);
            ((Computerspieler) spieler2).ziehen();
            spielfeldAufbau();
        } else {
        // Felder Bewegen
            Feld momentanesFeld = (Feld) arg0.getSource();
            /* (Wenn eine korrekte Figur ausgewählt wird und es noch keine 
             * ausgewaehlte Figur gibt.)
             * ODER
             * (Wenn man dann auf eine seiner eigenen Figuren Klickt, wechselt 
             * die GUI  auf die möglichen Felder dieser Figur.)
             */
            if ((momentanesFeld.getFigur() != null 
                && (momentanesFeld.getFigur().getFarbe() 
                == spielfeld.getAktuellerSpieler()) 
                && ausgewaehlteFigur == null) 
                || 
                (ausgewaehlteFigur != null && momentanesFeld.getFigur() != null 
                && momentanesFeld.getFigur().getFarbe() 
                == ausgewaehlteFigur.getFarbe())) {
                // Wird diese als neue Ausgewählte Figur gespeichert
                ausgewaehlteFigur = momentanesFeld.getFigur();
                /* Wenn der Spieler Weiß dran ist und dies angeklickte Figur 
                 * eine weiße ist.
                 */
                if (spielfeld.getAktuellerSpieler() 
                    && spielfeld.getWeisseFiguren()
                        .contains(ausgewaehlteFigur)) {
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
                    && spielfeld.getSchwarzeFiguren()
                        .contains(ausgewaehlteFigur)) {
                    momentanesFeld.setBackground(rot);
                    for (Feld makieren : ausgewaehlteFigur.getKorrektFelder()) {
                        makieren.setBackground(rot);
                    }
                    
                }
            // Wenn es bereits eine ausgewaehlte Figur gibt
            } else if (ausgewaehlteFigur != null) {
                sekundenStopp = (int) System.currentTimeMillis()
                    - sekundenStart;
                // TODO Wenn die Zugzeit > Maximale zugzeit --> Aufgeben Button 
                /* und das neue ausgewaehlte Feld unter den moeglichen Feldern 
                 dieser ist */
                if (ausgewaehlteFigur.getKorrektFelder()
                    .contains(momentanesFeld)) {
                    zugGUI(momentanesFeld);
                    spielfeldAufbau();
                /* Wenn nochmal auf das gleiche Feld geklickt wird, wird die
                 * Auswahl aufgehoben.
                 */
                } else if (ausgewaehlteFigur.getPosition()
                    .equals(momentanesFeld)) {
                    ausgewaehlteFigur = null;
                    spielfeldAufbau();
                }
            }
            // Wenn man ein leeres Feld anklickt
            if (momentanesFeld.getFigur() == null) {
                ausgewaehlteFigur = null;
            }
            
        }
        // Färbt die bedrohten Felder Grau
        for (Feld bedroht : spielfeld.getBedrohteFelder()) {
            bedroht.setBackground(new Color(100, 100, 100));
        }
        this.revalidate();   
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
        // Wenn der momentante Spieler einen Zug rückgängig macht
        if (e.getActionCommand().equals(commandRueck)) {
            spielfeld.zugRueckgaengig();
            if (spielfeld.getAktuellerSpieler()) {
                momentanerSpieler.setText("Weiß");
            } else {
                momentanerSpieler.setText("Schwarz");  
            }
            start();
            spielfeldAufbau();
            this.revalidate();
        }
        // Wenn der momentane Spieler aufgibt
        if (e.getActionCommand().equals(commandAufgeben)) {
            uhrAktiv = false;
            List<Object> aufgeben = spiel
                .aufgeben(spielfeld.getAktuellerSpieler());
            Spieler verlierer = (Spieler) aufgeben.get(0);
            String zuege = aufgeben.get(1).toString();
            Spieler gewinner = (Spieler) aufgeben.get(2);
            
            JOptionPane.showMessageDialog(parent, verlierer.getName() 
                + " gibt nach " + zuege + " Zügen auf! " + gewinner.getName() 
                + " gewinnt!!!");
            // Das spiel ist vorbei also keine Züge mehr möglich
            for (Feld feld : felderListe) {
                feld.removeMouseListener(this);
            }
            this.remove(cEast);
            this.add(cEnde, BorderLayout.EAST);
            this.revalidate();
        }
        // Wenn das Spiel gespeichert werden soll
        if (e.getActionCommand().equals(commandSpeichern)) {
            for (Spiel spiel : parent.getSpieleListe()) {
                if (spiel.equals(this.spiel)) {
                    parent.getSpieleListe().remove(spiel);
                }
            }
            parent.spielSpeichern(spiel);
        }
    }
    
    /**
     * Startet eine neue Zeitnahmesession für die Zugzeit der Spieler.
     */
    private void start() {
        // Alte Zeit beenden
        uhrAktiv = false;
        // Neue Zeit anfangen
        uhrAktiv = true;
        // Thread anlegen
        Thread th = new Thread(this); 
        sekundenStart = (int) System.currentTimeMillis();
        // Thread starten
        th.start(); 
    }
    
    /**
     * Runnable Methode zum erstellen der Zugzeit des momentanen Spielers.
     */
    public void run() {
        StringBuffer ausgabe;
        while (uhrAktiv) {
            zugzeit.setForeground(Color.BLACK);
            ausgabe = new StringBuffer();
            // Alle 10 millisekunden wird geupdated
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Vergangene Ziet in Millisekuden
            sekundenStopp = (int) System.currentTimeMillis()
                    - sekundenStart;
            // Formatierungshilfe für Zeiten
            Calendar dauer;
            dauer = Calendar.getInstance();
            // Zeit in Millisekunden übergeben
            dauer.setTimeInMillis(sekundenStopp);
            // und in Minuten/Sekunden/Millisekunden (0-9/0-9/0-99)
            int min;
            int sek;
            int ms;
            min = dauer.get(Calendar.MINUTE);
            sek = dauer.get(Calendar.SECOND);
            ms = dauer.get(Calendar.MILLISECOND);
            
            // An den AusgabeString anhängen
            if (min <= 9) {
                ausgabe.append("0" + min + ":");
            } else {
                ausgabe.append(min + ":");
            }
            if (sek <= 9) {
                ausgabe.append("0" + sek + ":");
            } else {
                ausgabe.append(sek + ":");
            }
            if (ms <= 9) {
                ausgabe.append("00" + ms);
            } else if (ms <= 99) {
                ausgabe.append("0" + ms);
            } else {
                ausgabe.append(ms);
            }
            // Auf das Zugzeit-Label schreiben
            zugzeit.setText(ausgabe.toString());
        }
    }
    
}
