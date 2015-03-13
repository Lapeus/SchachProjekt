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
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import daten.Computerspieler;
import daten.Spiel;
import daten.Spieldaten;
import daten.Spieler;
import daten.Spielfeld;
import figuren.Figur;
import zuege.Zug;
import zuege.Umwandlungszug;

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
     * Button um ein unetschieden anzubieten.
     */
    private JButton btnUnentschieden = new JButton("Remis anbieten");
    
    /**
     * Button um aufzugeben.
     */
    private JButton aufgeben = new JButton("Spiel Aufgeben");
    
    /**
     * Button zum Spielwiederholung ansehen.
     */
    private JButton btnWiederholung = new JButton("Wiederholung ansehen");
    
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
     * Konstante fuer den Farbton der Letzten Zug Felder (gruen).
     */
    private final Color gruen = new Color(6, 148, 6);
    
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
     * Action Command fuer Remi-Button.
     */
    private final String commandRemi = "remis";
    
    /**
     * Action Command fuer den Startmenue-Button.
     */
    private final String commandStartmenue = "spielende";
    
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
     * Gibt an ob Spiel vorbei ist.
     */
    private boolean spielVorbei = false;
    
    /**
     * Zug der Wiederholung.
     */
    private int zaehler = -1;
    
    /**
     * Spielvideo Zugliste.
     */
    private List<Zug> spielvideo = null;
    
    /**
     * Wenn das Spiel zu ende ist dann muss der Timer das wissen.
     */
    private boolean wiederholung = false;
    
    /**
     * Thread fuer die Stoppuhr.
     */
    private Thread th = new Thread(this);
    
    
    /**
     * Erzeugt eine SpielfeldGUI.
     * Wird von der Spielerauswahl-Seite aufgerufen und erstellt eine Spielfeld
     * GUI fuer ein neues Spiel.
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
        
        // FelderListe füllen
        felderListe = new ArrayList<Feld>();
        fuelleFelderListe();
        
        // Spielfeld
        spielfeld = new Spielfeld(felderListe);
        spielfeld.setEinstellungen(parent.getEinstellungen());
        spielfeld.setSpieldaten(new Spieldaten());
        
        // Einem Computergegner das Spielfeld uebergeben
        if (spieler2 instanceof Computerspieler) {
            ((Computerspieler) spieler2).setSpielfeld(spielfeld);
        }
        
        // Spiel 
        spiel = new Spiel(spielname, spieler1, spieler2, spielfeld);
        
        init();
    }
    
    /**
     * Erzeugt eine neue SpielfeldGUI.
     * Wird von der SpielLaden-Seite Aufgerufen und erzuegt eine SpielfeldGUI 
     * fuer ein bereits gespeichertes Spiel.
     * @param parent Das Objekt der dazugehörigen <b>SpielGUI</b>
     * @param spiel Ein Objekt von typ Spiel, welches bereits Paramenter hat
     */
    public SpielfeldGUI(SpielGUI parent, Spiel spiel) {
        this.parent = parent;
        this.spieler1 = spiel.getSpieler1();
        this.spieler2 = spiel.getSpieler2();
        this.spielfeld = spiel.getSpielfeld();
        this.felderListe = spielfeld.getFelder();
        this.spiel = spiel;
        for (Feld feld : felderListe) {
            feld.addMouseListener(this);
        }
        if (spielfeld.getAktuellerSpieler()) {
            momentanerSpieler.setText("Weiß");
        } else {
            momentanerSpieler.setText("Schwarz");  
        }
        if (parent.getEinstellungen().isBedrohteFigurenAnzeigen()) {
            for (Feld bedroht : spielfeld.getBedrohteFelder()) {
                bedroht.setBackground(new Color(100, 100, 100));
            }
        }
        revalidate();
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
        geschlageneWeisse.setLayout(new GridLayout(3, 0));
        gbc.gridy = 1;
        gbc.gridheight = 2;
        cEast.add(geschlageneWeisse, gbc);
        
        // geschlagene Schwarz
        geschlageneSchwarze.setLayout(new GridLayout(3, 0));
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
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        cEast.add(rueckgaengig, gbc);
        
        // Button speichern
        speichern.addActionListener(this);
        speichern.setActionCommand(commandSpeichern);
        gbc.gridx = 2;
        gbc.gridy = 4;
        cEast.add(speichern, gbc);
        
        // Button unentschieden
        btnUnentschieden.addActionListener(this);
        btnUnentschieden.setActionCommand(commandRemi);
        gbc.gridx = 1;
        gbc.gridy = 6;
        cEast.add(btnUnentschieden, gbc);
        
        
        // Button aufgeben
        aufgeben.addActionListener(this);
        aufgeben.setActionCommand(commandAufgeben);
        gbc.gridx = 2;
        gbc.gridy = 6;
        cEast.add(aufgeben, gbc);
        
        // Zu Panel hinzufügen
        this.add(cCenter, BorderLayout.CENTER);
        this.add(cEast, BorderLayout.EAST);
        
        // Zugzeit fuer den ersten Zug starten
        start();
        // Thread starten
        th.start(); 

        // SpielfeldGUI erstellen
        spielfeldAufbau();
        
        /* Wenn ein Computerspieler mitspielt und anfangen soll muss er hier 
         * den ersten Zug machen
         */
        wennComputerDannZiehen();
        // Den Zug des Computers makieren
        if (istComputerSpielerUndIstAmZug()) {
            for (Feld feld : spielfeld.getLetzteFelder()) {
                feld.setBackground(gruen);
            }
        }
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
        if (parent.getEinstellungen().isSpielfeldDrehen()) {
            spielfeldDrehen();
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
            String lineSep = System.getProperty("file.separator");
            String name = "";
            if (schwarz.getWert() == 900) {
                name = "pictures" + lineSep + "queenb.gif";
            }
            if (schwarz.getWert() == 100) {
                name = "pictures" + lineSep + "pawnb.gif";
            }
            if (schwarz.getWert() == 0) {
                name = "pictures" + lineSep + "kingb.gif";
            }
            if (schwarz.getWert() == 325) {
                name = "pictures" + lineSep + "bishopb.gif";
            }
            if (schwarz.getWert() == 275) {
                name = "pictures" + lineSep + "knightb.gif";
            }
            if (schwarz.getWert() == 465) {
                name = "pictures" + lineSep + "rookb.gif";
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
            String lineSep = System.getProperty("file.separator");
            String name = "";
            if (weiss.getWert() == 900) {
                name = "pictures" + lineSep + "queenw.gif";
            }
            if (weiss.getWert() == 100) {
                name = "pictures" + lineSep +  "pawnw.gif";
            }
            if (weiss.getWert() == 0) {
                name = "pictures" + lineSep + "kingw.gif";
            }
            if (weiss.getWert() == 325) {
                name = "pictures" + lineSep + "bishopw.gif";
            }
            if (weiss.getWert() == 275) {
                name = "pictures" + lineSep + "knightw.gif";
            }
            if (weiss.getWert() == 465) {
                name = "pictures" + lineSep + "rookw.gif";
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
        this.validate();
        this.repaint();  
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
            String lineSep = System.getProperty("file.separator");
            String name = "";
            if (schwarz.getWert() == 900) {
                name = "pictures" + lineSep + "queenb.gif";
            }
            if (schwarz.getWert() == 100) {
                name = "pictures" + lineSep + "pawnb.gif";
            }
            if (schwarz.getWert() == 0) {
                name = "pictures" + lineSep + "kingb.gif";
            }
            if (schwarz.getWert() == 325) {
                name = "pictures" + lineSep + "bishopb.gif";
            }
            if (schwarz.getWert() == 275) {
                name = "pictures" + lineSep + "knightb.gif";
            }
            if (schwarz.getWert() == 465) {
                name = "pictures" + lineSep + "rookb.gif";
            }
            try {
                Image imageB = ImageIO.read(new File(name));
                ImageIcon iconB  = new ImageIcon(
                    imageB.getScaledInstance(45, 45, Image.SCALE_DEFAULT));
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
            String lineSep = System.getProperty("file.separator");
            String name = "";
            if (weiss.getWert() == 900) {
                name = "pictures" + lineSep + "queenw.gif";
            }
            if (weiss.getWert() == 100) {
                name = "pictures" + lineSep + "pawnw.gif";
            }
            if (weiss.getWert() == 0) {
                name = "pictures" + lineSep + "kingw.gif";
            }
            if (weiss.getWert() == 325) {
                name = "pictures" + lineSep + "bishopw.gif";
            }
            if (weiss.getWert() == 275) {
                name = "pictures" + lineSep + "knightw.gif";
            }
            if (weiss.getWert() == 465) {
                name = "pictures" + lineSep + "rookw.gif";
            }
            try {
                Image imageW = ImageIO.read(new File(name));
                ImageIcon iconW  = new ImageIcon(
                    imageW.getScaledInstance(45, 45, Image.SCALE_DEFAULT));
                momentan.setIcon(iconW);
                geschlageneWeisse.add(momentan);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
    
    /**
     * Wertet aus, ob der Spieler ein Computerspieler ist und ob dieser 
     * momentan am Zug ist.
     * @return true - ist ein Computerspieler false - ist kein Computerspieler
     */
    private boolean istComputerSpielerUndIstAmZug() {
        boolean istCompSpielerUndDran = false;
        if (spieler2 instanceof Computerspieler
            && spieler2.getFarbe() == spielfeld.getAktuellerSpieler() 
            && !spielVorbei) {
            istCompSpielerUndDran = true;
        }
        return istCompSpielerUndDran;
        
    }
    
    /**
     * Ermittelt ob ein Computerspieler dran ist und laesst ihn wenn dies so ist
     * ziehen.
     */
    private void wennComputerDannZiehen() {
        // Wenn spieler 2 ein Computergegner ist und dran ist
        if (istComputerSpielerUndIstAmZug()) {
            spielfeldAufbau();
            start();
            // Testen ob die 50-Züge-Regel verletzt wurde
            if (spielfeld.getSpieldaten().fuenfzigZuegeRegel()) {
                // Unentschieden einreichen
                spiel.unentschieden();
                parent.soundAbspielen("Hinweis.wav");
                JOptionPane.showMessageDialog(parent, "50 Züge Regel wurde "
                    + "erfüllt. Das Spiel endet in einem Unentschieden");
                this.remove(cEast);
                cEndeErstellen();
                this.add(cEnde, BorderLayout.EAST);
                this.validate();
                this.repaint();  
            } else {
                ((Computerspieler) spieler2).ziehen();
                sekundenStopp = ((int) System.currentTimeMillis()
                    - sekundenStart) / 1000;
                spielfeld.getSpieldaten().getLetzterZug().setZugzeit(
                    sekundenStopp);
                mattOderSchach();
                spielfeldAufbau();
                start();
            }
            for (Feld feld : spielfeld.getLetzteFelder()) {
                feld.setBackground(gruen);
            }
        }
    }
    
    /**
     * Gibt eine Schachwarnung aus, wenn der aktuelle Spieler ein menschlicher 
     * Spieler ist. Computerspieler benoetigen diese Warung aus offensichtilchen
     * Gruenden nicht.
     */
    private void schachWarnung() {
        // Wenn kein Computerspieler dran ist und das Spiel nocht vorbei ist
        if (!((spieler1 instanceof Computerspieler && spieler1.getFarbe() 
            == spielfeld.getAktuellerSpieler() && !spielVorbei)
            ||
            spieler2 instanceof Computerspieler 
            && spieler2.getFarbe() == spielfeld.getAktuellerSpieler()
            && !spielVorbei)) {
            spielfeldAufbau();
            parent.soundAbspielen("FehlerhafteEingabe.wav");
            // Schachmeldung ausgeben
            for (Feld feld : spielfeld.getLetzteFelder()) {
                feld.setBackground(gruen);
            }
            JOptionPane.showMessageDialog(parent, 
                "Sie stehen im Schach!", "Schachwarnung!",
                JOptionPane.WARNING_MESSAGE);
        }
        // Schach wieder aufheben, da nun ein Zug aus dem Schach gezwungen ist
        spielfeld.setSchach(false);
    }
    
    /**
     * Prueft nach einem Zug, ob der neue aktuelle Spieler matt ist oder im 
     * schach steht.
     */
    private void mattOderSchach() {
        // Wenn das Spiel vorbei ist
        if (spielfeld.schachMatt()) {
            spielfeldAufbau();
            // wird die Stoppuhr angehalten
            // uhrAktiv = false;
            // das Spiel ausgewertet
            List<Object> auswertung = spiel.auswertung();
            Spieler gewinner = (Spieler) auswertung.get(0);
            String ergebnis; 
            String zuege = auswertung.get(2).toString();
            if ((boolean) auswertung.get(1)) {
                ergebnis = gewinner.getName() 
                    + " gewinnt nach " + zuege + " Zügen.";
            } else {
                ergebnis = "Das Spiel endet in einem Patt";
            }
            // Und Ein Dialogfenster für den Gewinner angezeigt
            parent.soundAbspielen("SchachMatt.wav");
            JOptionPane.showMessageDialog(parent
                , ergebnis);
            // Das spiel ist vorbei also keine Züge mehr möglich
            for (Feld feld : felderListe) {
                feld.removeMouseListener(this);
            }
            // Spielende Screen
            this.remove(cEast);
            cEndeErstellen();
            this.add(cEnde, BorderLayout.EAST);
            this.validate();
            this.repaint();  
            spielVorbei = true;
        // Wenn der momentane Spieler im Schach steht
        } else if (spielfeld.isSchach()) {
            schachWarnung();
        }
    }
    
    
    /**
     * Hier werden die Züge veranlasst und auf der Gui geupdated.
     * @param momentanesFeld Das momentan ausgewählte Feld
     */
    private void spielerzugGUI(Feld momentanesFeld) {
        // Hier ist der jetzige Zug beendet also auch die Zugzeit
        sekundenStopp = ((int) System.currentTimeMillis()
            - sekundenStart) / 1000;
        // Ein Zug wird ausgeführt und die Zugzeit uebergeben
        spielfeld.ziehe(ausgewaehlteFigur, momentanesFeld,
            sekundenStopp);
        spielfeldAufbau();
        this.validate();
        this.repaint();  
        // Start der neuen Zugzeit
        start();
        // Neuer Spieler = keine Ausgewählte Figur
        ausgewaehlteFigur = null;
        spielfeld.getBedrohteFelder();
        Zug letzterZug = spielfeld.getSpieldaten().getLetzterZug();
        mattOderSchach();
        if (!spielVorbei && letzterZug instanceof Umwandlungszug)   {
            spielfeldAufbau();
            parent.soundAbspielen("Hinweis.wav");
            String[] moeglicheFiguren = {"Dame", "Turm", "Läufer", 
                "Springer"};
            String s = (String) JOptionPane.showInputDialog(parent,
                "Wählen Sie eine Figur aus, die Sie gegen den "
                + "Bauern tauschen wollen"
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
                if (parent.getEinstellungen().isMoeglicheFelderAnzeigen()) {
                    for (Feld makieren : ausgewaehlteFigur.getKorrektFelder()) {
                        makieren.setBackground(rot);
                    }
                }
            }
            /* Wenn der Spieler Schwarz dran ist und dies angeklickte Figur 
             * eine schwarze ist.
             */
            if (!spielfeld.getAktuellerSpieler() 
                && spielfeld.getSchwarzeFiguren()
                    .contains(ausgewaehlteFigur)) {
                momentanesFeld.setBackground(rot);
                if (parent.getEinstellungen().isMoeglicheFelderAnzeigen()) {
                    for (Feld makieren : ausgewaehlteFigur.getKorrektFelder()) {
                        makieren.setBackground(rot);
                    }
                }
            }
        // Wenn es bereits eine ausgewaehlte Figur gibt
        } else if (ausgewaehlteFigur != null) {
            /* und das neue ausgewaehlte Feld unter den moeglichen Feldern 
             dieser ist */
            if (ausgewaehlteFigur.getKorrektFelder()
                .contains(momentanesFeld)) {
                // Hier zieht ein menschlicher Spieler
                spielerzugGUI(momentanesFeld);
                /* Wenn ein Computerspieler mitspielt muss er hier den  Zug
                 * machen
                 */
                wennComputerDannZiehen();
                // Je nach aktuellem Spieler wird das Label gesetzt
                if (spielfeld.getAktuellerSpieler()) {
                    momentanerSpieler.setText("Weiß");
                } else {
                    momentanerSpieler.setText("Schwarz");
                }
                // Wenn das Spiel nicht vorbei ist 
                if (!spielVorbei) {
                    // autosave initiieren
                    parent.autoSave(spiel);
                } 
            } 
        }
        // Wenn man ein leeres Feld anklickt
        if (momentanesFeld.getFigur() == null) {
            ausgewaehlteFigur = null;
        }
        // Färbt die bedrohten Felder Grau
        if (parent.getEinstellungen().isBedrohteFigurenAnzeigen()) {
            for (Feld bedroht : spielfeld.getBedrohteFelder()) {
                bedroht.setBackground(new Color(100, 100, 100));
            }
        }
        this.validate();
        this.repaint();  
    }
   
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mouseEntered(MouseEvent arg0) {
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mouseExited(MouseEvent arg0) {
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mousePressed(MouseEvent arg0) {
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mouseReleased(MouseEvent arg0) {
    }
    
    /**
     * Action Performed fuer alle Buttons.
     * 
     * @param e Ausgeloestes ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        // Wenn der momentante Spieler einen Zug rückgängig macht
        if (e.getActionCommand().equals(commandRueck)) {
            /* Wenn es einen Computerspieler gibt dann muss dessen Zug auch 
             * rueckgaenig gemacht werden.
            */
            if (spieler1 instanceof Computerspieler 
                || spieler2 instanceof Computerspieler) {
                spielfeld.zugRueckgaengig();
            }
            spielfeld.zugRueckgaengig();
            // Labels wieder richtig setzen
            if (spielfeld.getAktuellerSpieler()) {
                momentanerSpieler.setText("Weiß");
            } else {
                momentanerSpieler.setText("Schwarz");  
            }
            // Zugzeit neu starten
            start();
            spielfeldAufbau();
            this.validate();
            this.repaint();  
        }
        // Wenn ein Spieler ein Remis anbietet
        if (e.getActionCommand().equals(commandRemi)) {
            // Testen ob die 50-Züge-Regel verletzt wurde
            if (spielfeld.getSpieldaten().fuenfzigZuegeRegel()) {
                // Unentschieden einreichen
                spiel.unentschieden();
                parent.soundAbspielen("Hinweis.wav");
                JOptionPane.showMessageDialog(parent, "50 Züge Regel wurde "
                    + "erfüllt. Das Spiel endet in einem Unentschieden");
                this.remove(cEast);
                cEndeErstellen();
                this.add(cEnde, BorderLayout.EAST);
                this.validate();
                this.repaint();  
            } else {
                if (!(spieler2 instanceof Computerspieler)) {
                    parent.soundAbspielen("Hinweis.wav");
                    int eingabe = JOptionPane.showConfirmDialog(parent, 
                        "Möchten Sie sich auf ein Unentschieden einigen?");
                    if (eingabe == 0) {
                        
                        spiel.unentschieden();
                        for (Feld feld : felderListe) {
                            feld.removeMouseListener(this);
                        }
                        this.remove(cEast);
                        cEndeErstellen();
                        this.add(cEnde, BorderLayout.EAST);
                        this.validate();
                        this.repaint();  
                    } else {
                        Spieler spieler;
                        if (spielfeld.getAktuellerSpieler() == spieler1
                            .getFarbe()) {
                            spieler = spieler1;
                        } else {
                            spieler = spieler2;
                        }
                        parent.soundAbspielen("Aufgeben.wav");
                        JOptionPane.showMessageDialog(parent, 
                            spieler.getName() + "hat das Remis abgelehnt");
                    }
                } else {
                    if ((boolean) ((Computerspieler) spieler2)
                        .unentschiedenAnnehmen()) {
                        parent.soundAbspielen("Hinweis.wav");
                        JOptionPane.showMessageDialog(parent, "Spiel endet "
                            + "unentschieden.");
                        spiel.unentschieden();
                        for (Feld feld : felderListe) {
                            feld.removeMouseListener(this);
                        }
                        this.remove(cEast);
                        cEndeErstellen();
                        this.add(cEnde, BorderLayout.EAST);
                        this.validate();
                        this.repaint();
                    } else {
                        parent.soundAbspielen("Aufgeben.wav");
                        JOptionPane.showMessageDialog(parent, 
                            spieler2.getName() + " hat das Remis abgelehnt");
                    }
                }
            }
        }
        // Wenn der momentane Spieler aufgibt
        if (e.getActionCommand().equals(commandAufgeben)) {
            //uhrAktiv = false;
            List<Object> aufgeben = spiel
                .aufgeben(spielfeld.getAktuellerSpieler());
            Spieler verlierer = (Spieler) aufgeben.get(0);
            String zuege = aufgeben.get(1).toString();
            Spieler gewinner = (Spieler) aufgeben.get(2);
            parent.soundAbspielen("Aufgeben.wav");
            JOptionPane.showMessageDialog(parent, verlierer.getName() 
                + " gibt nach " + zuege + " Zügen auf! " + gewinner.getName() 
                + " gewinnt!!!");
            // Das spiel ist vorbei also keine Züge mehr möglich
            for (Feld feld : felderListe) {
                feld.removeMouseListener(this);
            }
            this.remove(cEast);
            cEndeErstellen();
            this.add(cEnde, BorderLayout.EAST);
            this.validate();
            this.repaint();  
        }
        // Wenn das Spiel gespeichert werden soll
        if (e.getActionCommand().equals(commandSpeichern)) {
            parent.spielSpeichern(spiel);
            parent.soundAbspielen("Hinweis.wav");
            JOptionPane.showMessageDialog(parent, "Spiel gespeichert",
                "Speichern", JOptionPane.INFORMATION_MESSAGE);
        }
        if (e.getSource().equals(btnWiederholung)) {
            wiederholung = true;
            start();
            if (zaehler == -1) {
                spielvideo = spiel.spielvideo();
            } else {
                Zug zug = spielvideo.get(zaehler);
                // Ziehe jeden Zug
                spielfeld.ziehe(zug.getFigur(), zug.getZielfeld(), 
                    zug.getZugzeit());
                // Wenn es ein Umwandlungszug war
                if (zug instanceof Umwandlungszug) {
                    // Wandel die Figur entsprechend um
                    spielfeld.umwandeln(spielfeld.getSpieldaten()
                        .getLetzterZug().getFigur(), 
                        ((Umwandlungszug) zug).getNeueFigur().getWert());
                }
            }
            spielfeldAufbau();
            zaehler++;
            if (zaehler == spielvideo.size()) {
                btnWiederholung.setEnabled(false);
                wiederholung = false;
            }
        }
    }
    
    /**
     * Startet eine neue Zeitnahmesession für die Zugzeit der Spieler.
     */
    private void start() {
        System.out.println(Thread.activeCount());
        // Alte Zeit beenden
        uhrAktiv = false;
        System.out.println(Thread.activeCount());
        // Neue Zeit anfangen
        uhrAktiv = true;
        // Thread anlegen 
        sekundenStart = (int) System.currentTimeMillis();
    }
    
    /**
     * Runnable Methode zum erstellen der Zugzeit des momentanen Spielers.
     */
    public void run() {
        StringBuffer ausgabe;
        while (uhrAktiv) {
            zugzeit.setForeground(Color.BLACK);
            ausgabe = new StringBuffer();
            try {
                Thread.sleep(10);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            // Vergangene Zeit in Millisekuden
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
            // Wenn die Zugzeit > Maximale zugzeit --> Aufgeben Button
            int begrenzung = parent.getEinstellungen().getZugZeitBegrenzung();
            if (begrenzung > 0 && sekundenStopp / 1000 >= begrenzung) {
                aufgeben.doClick();
            }
            if (btnWiederholung.isEnabled()) {
                if (wiederholung && sekundenStopp >= 2000) {
                    btnWiederholung.doClick();
                    start();
                }
            } else {
                uhrAktiv = false;
            }
        }
    }
    
    /**
     * Dreht das Spielfeld, sodass es immer vom aktuellen Spieler aus gesehen 
     * wird.
     */
    private void spielfeldDrehen() {
        // Entfernt alle Felder
        cCenter.removeAll();
        // Wenn weiss dran ist
        if (spielfeld.getAktuellerSpieler()) {
            for (int i = 7; i >= 0; i--) {
                for (int j = 0; j < 8; j++) {
                    cCenter.add(felderListe.get(j + i * 8));
                }
            }
        // Wenn schwarz dran ist
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    cCenter.add(felderListe.get(j + i * 8));
                }
            }
        }
        this.validate();
        this.repaint();  
    }
    
    /**
     * Spielende Ansicht.
     */
    private void cEndeErstellen()  {
        System.out.println(Thread.activeCount());
        // Alle Autosave Dateien des Spiels löschen
        parent.autoSaveLoeschen();
        // cEnde
        System.out.println(spielfeld.getSpieldaten().toString());
        cEnde.setLayout(new GridBagLayout());
        JButton startmenue = new JButton("Zurück zum Startmenü");
        startmenue.setActionCommand(commandStartmenue);
        startmenue.addActionListener(new SeitenwechselListener(parent));
        GridBagConstraints gbc2 = new GridBagConstraints();
        // Startmenue Button
        gbc2.insets = new Insets(15, 15, 15, 15);
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        cEnde.add(startmenue, gbc2);
        // Wiederholung-Anzeigen-Button
        btnWiederholung.addActionListener(this);
        gbc2.gridy = 1;
        cEnde.add(btnWiederholung, gbc2);
        // Zugliste
        JTextPane zugliste = new JTextPane();
        zugliste.setEditable(false);
        zugliste.setBackground(cEast.getBackground());
        zugliste.setText(spielfeld.getSpieldaten().toString());
        JScrollPane sPane = new JScrollPane(zugliste);
        gbc2.gridy = 2;
        cEnde.add(sPane, gbc2);
    }
}
