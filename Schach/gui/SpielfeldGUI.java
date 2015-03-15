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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
 * implementiert und fuer die Darstellung des Spielfeldfenster zustaednig ist.
 * @author Marvin Wolf 
 */
public class SpielfeldGUI extends JPanel implements MouseListener, 
    ActionListener, Runnable, ComponentListener {
    
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
    private JButton rueckgaengig 
        = new JButton("<html>Zug R&uuml;ckg&auml;ngig");
    
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
     * Button zum pausieren/weiterfuehren der Wiederholung.
     */
    private JButton btnStopp = new JButton("Stopp");
    
    /**
     * JLabel zum Anzeigen des momentanen Spielers.
     */
    private JLabel momentanerSpieler = new JLabel("<html>Wei&szlig;");
    
    /**
     * JLabel zum Anzeigen der Zugzeit des momentanen Spielers.
     */
    private JLabel zugzeit = new JLabel("  ");
    
    /**
     * JPanel fuer die Geschlagenen schwarzen Figuren.
     */
    private JPanel geschlageneSchwarze = new JPanel(); 
    
    /**
     * JPanel fuer die Geschlagenen schwarzen Figuren.
     */
    private JPanel geschlageneWeisse = new JPanel();
    
    /**
     * Spieler1, welcher von der <b>Spielerauswahl</b> Seite uebergeben wird.
     */
    private Spieler spieler1;
    
    /**
     * Spieler2, welcher von der <b>Spielerauswahl</b> Seite uebergeben wird.
     */
    private Spieler spieler2;
    
    /**
     * Liste die 64 Schachfelder enthaelt.
     */
    private List<Feld> felderListe;
    
    /**
     * Objekt der Klasse <b>Spielfeld</b>, welches fuer Figurenpositionen 
     * benoetigt wird.
     */
    private Spielfeld spielfeld;
    
    
    /**
     * Objekt der Klasse <b>Spiel</b>, welche das Spiel speichert zu dem dieses 
     * Spielfeld gehoert.
     */
    private Spiel spiel;
    
    /**
     * Enhaelt eventuell momentan ausgewaehlte Figur. <br>
     * Wird benoetigt um Zug auszufuehren, da man ja nur ziehen kann wenn man
     * vorher schon eine Figur ausgewaehlt hat.
     */
    private Figur ausgewaehlteFigur;
    
    /**
     * Zugliste fuer den Engamescreen.
     */
    private JList<String> zugListe;
    
    /**
     * JPanel fuer das Spielfeld. Wird in der <i>spielfeldUpdate</i> Methode 
     * benoetigt.  
     */
    private Container cCenter = new JPanel();
    
    /**
     * Kontainer fuer die Anzeigen und Button neben dem Spielfeld.
     */
    private JPanel cEast = new JPanel();
    
    /**
     * Panel welches nach Beendigung eines Spiels cEast ersetzt.
     */
    private JPanel cEnde = new JPanel();
    
    /**
     * Konstante fuer den Farbton der "schwarzen" Felder (braun).
     */
    private final Color braun = new Color(181, 81, 16);
    
    /**
     * Konstante fuer den Farbton der "weissen" Felder (helles Beige).
     */
    private final Color weiss = new Color(255, 248, 151);
    
    /**
     * Konstante fuer den Farbton der makierten Felder (rot).
     */
    private final Color rot = new Color(204, 0, 0);
    
    /**
     * Konstante fuer den Farbton der Letzten Zug Felder (gruen).
     */
    private final Color gruen = new Color(6, 148, 6);
    
    /**
     * Konstante fuer den Farbton des Hintergrundes (Braun).
     */
    private final Color cBraunRot = new Color(164, 43, 24); 
    
    /**
     * Konstante fuer den Farbton der Buttons (Beige).
     */
    private final Color cHellesBeige = new Color(255, 248, 151);
    
    /**
     * Action Command fuer den Rueckgaening-Button.
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
    private final String commandRemis = "remis";
    
    /**
     * Action Command fuer den Startmenue-Button.
     */
    private final String commandStartmenue = "spielende";
    
    /**
     * Startzeit fuer die Zugzeit-Stoppuhr (Ueber Systemzeit). 
     */
    private long sekundenStart;
    
    /**
     * Endzeit fuer die Zugzeit-Stoppuhr (Endezeit - Startzeit). 
     */
    private long sekundenStopp;
    
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
     * Wenn der der Benutzer das Spielvideo unterbrehcen will wird diese
     * auf false gesetzt.
     */
    private boolean wiederholen = true;
    
    /**
     * Thread fuer die Stoppuhr.
     */
    private Thread th = new Thread(this);
    
    
    /**
     * Erzeugt eine SpielfeldGUI.
     * Wird von der Spielerauswahl-Seite aufgerufen und erstellt eine Spielfeld
     * GUI fuer ein neues Spiel.
     * @param parent Das Objekt der dazugehoerigen <b>SpielGUI</b>
     * @param spieler1 Ein Objekt der Klasse <b>Spieler</b>
     * @param spieler2 Ein weiteres Objekt der Klasse <b>Spieler</b>
     * @param spielname Name des Spiels muss uebergeben werden
     */
    public SpielfeldGUI(SpielGUI parent, String spielname,
        Spieler spieler1, Spieler spieler2) {
        super();
        this.parent = parent;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
        
        // FelderListe fuellen
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
     * @param parent Das Objekt der dazugehoerigen <b>SpielGUI</b>
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
            momentanerSpieler.setText("<html>Wei&szlig;");
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
     * 1. Fuellung der felderListe <br>
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
        cCenter.addComponentListener(this);
        this.setLayout(new BorderLayout());
        cCenter.setBackground(new Color(0, 0, 0));
        cCenter.setLayout(new GridLayout(8, 8, 1, 1));
        
        // EAST
        cEast.setBackground(cBraunRot);
        GridBagConstraints gbc = new GridBagConstraints();
        cEast.setLayout(new GridBagLayout());
        
        
        // geschlageneLabelW
        JLabel lGeschlageneW 
            = new JLabel("<html>Geschlagene Wei&szlig;e Figuren:");
        lGeschlageneW.setForeground(new Color(0, 0, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(20, 10, 10, 20);
        cEast.add(lGeschlageneW, gbc); 
        
        //geschlageneLabelB
        JLabel lGeschlageneB = new JLabel("Geschlagene Schwarze Figuren:");
        lGeschlageneB.setForeground(new Color(0, 0, 0));
        gbc.gridy = 8;
        cEast.add(lGeschlageneB, gbc);
        
        // geschlagene Weiss
        geschlageneWeisse.setBackground(cBraunRot);
        geschlageneWeisse.setLayout(new GridLayout(3, 0));
        gbc.gridy = 1;
        gbc.gridheight = 2;
        cEast.add(geschlageneWeisse, gbc);
        
        // geschlagene Schwarz
        geschlageneSchwarze.setBackground(cBraunRot);
        geschlageneSchwarze.setLayout(new GridLayout(3, 0));
        gbc.gridy = 9;
        cEast.add(geschlageneSchwarze, gbc);
        
        // Label momentanerSpieler
        momentanerSpieler.setForeground(new Color(0, 0, 0));
        momentanerSpieler.setBackground(cHellesBeige);
        gbc.gridheight = 1;
        gbc.gridy = 3;
        cEast.add(momentanerSpieler, gbc);
        
        // Label Stoppuhr
        zugzeit.setBackground(cHellesBeige);
        gbc.gridy = 7;
        cEast.add(zugzeit, gbc);
        
        // Button rueck
        rueckgaengig.setForeground(new Color(0, 0, 0));
        rueckgaengig.setBackground(cHellesBeige);
        rueckgaengig.addActionListener(this);
        rueckgaengig.setActionCommand(commandRueck);
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        cEast.add(rueckgaengig, gbc);
        
        // Button speichern
        speichern.setBackground(cHellesBeige);
        speichern.addActionListener(this);
        speichern.setActionCommand(commandSpeichern);
        gbc.gridx = 2;
        gbc.gridy = 4;
        cEast.add(speichern, gbc);
        
        // Button unentschieden
        btnUnentschieden.setBackground(cHellesBeige);
        btnUnentschieden.addActionListener(this);
        btnUnentschieden.setActionCommand(commandRemis);
        gbc.gridx = 1;
        gbc.gridy = 6;
        cEast.add(btnUnentschieden, gbc);
        
        
        // Button aufgeben
        aufgeben.setBackground(cHellesBeige);
        aufgeben.addActionListener(this);
        aufgeben.setActionCommand(commandAufgeben);
        gbc.gridx = 2;
        gbc.gridy = 6;
        cEast.add(aufgeben, gbc);
        
        // Zu Panel hinzufuegen
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
     * Ansicht, welche nach Spielende aufgerufen wird.
     * Bietet einen Button zum zur&uuml;ckkehren auf die 
     * {@link Eroeffnungsseite}. Zudem wird eine Option zum Wiederholen des
     * gerade gespielten Spiels angeboten und dessem Zugliste wird angezeigt.
     * Es werden alle Autosavedaten des Spiels gel&ouml;scht. 
     */
    private void cEndeErstellen()  {
        // Alle Autosave Dateien des Spiels loeschen
        parent.autoSaveLoeschen();
        
        // cEnde
        cEnde.setLayout(new GridBagLayout());
        cEnde.setBackground(cBraunRot);
        GridBagConstraints gbc2 = new GridBagConstraints();
        
        // Startmenue Button
        JButton startmenue = new JButton("Zurueck zum Startmenue");
        startmenue.setActionCommand(commandStartmenue);
        startmenue.addActionListener(this);
        startmenue.setBackground(cHellesBeige);
        gbc2.insets = new Insets(15, 15, 15, 15);
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        cEnde.add(startmenue, gbc2);
        
        // Wiederholung-Anzeigen-Button
        btnWiederholung.setBackground(cHellesBeige);
        btnWiederholung.addActionListener(this);
        gbc2.gridy = 1;
        cEnde.add(btnWiederholung, gbc2);
        
        // Pause-Button fuer die Wiederholung
        btnStopp.setBackground(cHellesBeige);
        btnStopp.addActionListener(this);
        btnStopp.setVisible(false);
        gbc2.gridy = 2;
        cEnde.add(btnStopp, gbc2);
        
        // Zugliste
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        String[] zuege = spielfeld.getSpieldaten().toString()
            .split(System.getProperty("line.separator"));
        for (String string : zuege) {
            listModel.addElement(string);
        }
        zugListe = new JList<String>(listModel);
        zugListe.setBackground(cHellesBeige);
        JScrollPane sPane = new JScrollPane(zugListe);
        sPane.setBackground(cHellesBeige);
        gbc2.gridy = 3;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        cEnde.add(sPane, gbc2);
        cEnde.revalidate();
    }
    
    /**
     * Fuellt die felderListe mit 64 Feldern(Index 0-7, 0-7).
     * Fuegt zudem jedem Feld einen MouseListener hinzu (this)
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
     * Dient zum Erstellen der SpielfeldGUI, d.h. das Spielfeld wird mit den 
     * Feldern der {@link #felderListe} gefüllt und die Labels bekommen ihre
     * Farbe. Dann wird die Methode {@link #spielfeldUIUpdate()} aufgerufen, 
     * welche für die Darstellung der Figuren sorgt.
     */
    private void spielfeldAufbau() {        
        // boolean fuer abwechselnd schwarz/weiss
        boolean abwechslung = false;
        // zaehler fuer richtge Position in der Felderliste
        int counter = 56;
        // Fuer jede Zeile
        for (int i = 0; i < 8; i++) {
            // in der neuen Reihe kommt die gleiche Farbe wie ende letzer Reihe
            abwechslung = !abwechslung;
            // Fuer jede Spalte
            for (int j = 0; j < 8; j++) {
                /* passendes Feld aus Spielfeld lesen und Hintergrund sichtbar 
                 * machen. Dann den Zaehler fuer die Position in der Liste 
                 * vermindern
                 */ 
                Feld temp = spielfeld.getFelder().get(counter + j);
                temp.setOpaque(true);
                // Wenn die Farbe "schwarz"(Braun) ist dann Feld braun machen
                if (!abwechslung) {
                    temp.setBackground(braun);
                    abwechslung = true;
                // Wenn die Farbe "weiss"(Beige) ist dann Feld beige machen    
                } else {
                    temp.setBackground(weiss);
                    abwechslung = false;
                }
                // Dem cCenter Panel das fertige Feld hinzufuegen
                cCenter.add(temp);
            }    
            counter -= 8;
        }
        if (spielfeld.getEinstellungen().isSpielfeldDrehen() && !wiederholung 
            && !(spieler2 instanceof Computerspieler)) {
            spielfeldDrehen();
        }
        /* Spielfeld drehen wenn Spieler 1 schwarz ist und ein Computergegner
        dran ist */
        if (!(spieler1.getFarbe())
                && spieler2 instanceof Computerspieler) {
            spielfeldDrehen();
        }
        spielfeldUIUpdate();
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
     * Sorgt daf&uuml;r, dass jede Figur ihr passendes Figurenbild erh&auml;lt.
     * Zudem wird hier gesteuert ob der rueckg&auml;ngig-Button klickbar(Zug
     * vorhanden) oder nicht(kein Zug vorhande) ist. Die 
     * Figurenbildergr&ouml;&szlig;en an die Gr&ouml;&szlig;e des Fensters
     * angepasst werden. Dann wird noch die {@link #geschlageneFigureUpdate()} 
     * Methode aufgerufen.
     */
    private void spielfeldUIUpdate() {
        if (spielfeld.getSpieldaten().getZugListe().isEmpty()) {
            rueckgaengig.setEnabled(false);
        } else {
            rueckgaengig.setEnabled(true);
        }
        // Alle Bilder loeschen damit keine Bilder doppelt bleiben
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
                int width = parent.getWidth() / 10;
                int height = parent.getHeight() / 9;
                Image imageB = ImageIO.read(new File(name));
                ImageIcon iconB  = new ImageIcon(imageB
                    .getScaledInstance(width, height, Image.SCALE_SMOOTH));
                momentan.setIcon(iconB);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
        // - weisse Figurenbilder
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
                int width = parent.getWidth() / 10;
                int height = parent.getHeight() / 9;
                Image imageW = ImageIO.read(new File(name));
                ImageIcon iconW  = new ImageIcon(imageW
                    .getScaledInstance(width, height, Image.SCALE_SMOOTH));
                momentan.setIcon(iconW);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        geschlageneFigureUpdate();
        this.revalidate();  
    }
    
    /**
     * Updated die Anzeigen der geschlagenen Figuren.
     * Geht die geschlagenenFigurenListen der jeweiligen Farbe durch und 
     * zeigt diese in dem jewiligen Conatiner an.
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
     * Wenn der Computergegner dran ist werden die Verhhaltensm&ouml;glichkeiten
     * abgefragt und ausgef&uuml;hrt.
     * Wenn die 50-Z&uuml;ge Regel erf&uuml;llt ist dann wird der Computer ein
     * Unentschieden erzwingen.
     * Wenn der Computer ziehen soll dann wird eine neue Zugzeit gestartet der 
     * Zug ausgef&uuml;hrt und dann die Zeit gestoppt und dem Zug 
     * nachtr&auml;glich &uuml;bergeben. Dabei wird mit der 
     * {@link #mattOderSchach()}-Methode nach jedem Zug auf Schach oder Matt des
     * folgenden Spielers kontrolliert. 
     * Dann werden Start- und Zielfeld des letzten Zugs eines Computers 
     * gr&uuml;n makiert.
     * 
     */
    private void wennComputerDannZiehen() {
        // Wenn spieler 2 ein Computergegner ist und dran ist
        if (istComputerSpielerUndIstAmZug()) {
            // Testen ob die 50-Zuege-Regel verletzt wurde
            if (spielfeld.getSpieldaten().fuenfzigZuegeRegel()) {
                // Unentschieden einreichen
                spiel.unentschieden();
                parent.soundAbspielen("Hinweis.wav");
                JOptionPane.showMessageDialog(parent, "50 Zuege Regel wurde "
                    + "erfuellt. Das Spiel endet mit einem Unentschieden");
                this.remove(cEast);
                cEndeErstellen();
                this.add(cEnde, BorderLayout.EAST);
                this.validate();
                this.repaint();  
            } else {
                // Zugzeit neu starten
                start();
                // Zug ausfuehren
                ((Computerspieler) spieler2).ziehen();
                // zugzeit stoppen 
                sekundenStopp = (System.currentTimeMillis()
                    - sekundenStart) / 1000;
                // und nachtraeglcih uebergeben
                spielfeld.getSpieldaten().getLetzterZug().setZugzeit(
                    (int) sekundenStopp);
                // auf Matt und Schach Pruefen
                mattOderSchach();
                spielfeldAufbau();
                start();
                // Letzten Zug gruen makieren
                for (Feld feld : spielfeld.getLetzteFelder()) {
                    feld.setBackground(gruen);
                }
            }
        }
    }
    

    /**
     * Immer wenn ein Spieldfeld angeklickt wird wird diese Methode aufgerufen
     * und verarbeitet dann den Klick.
     * Wenn noch keins ausgew&auml;hlt wurde oder die Auswahl gewechselt wurde
     * wird ein neues ausgew&auml;hlt und wenn aktiviert die moeglichen Felder
     * angezeigt.
     * Wenn  
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mouseClicked(MouseEvent arg0) {
        spielfeldAufbau();
        // Felder Bewegen
        Feld momentanesFeld = (Feld) arg0.getSource();
        /* (Wenn eine korrekte Figur ausgewaehlt wird und es noch keine 
         * ausgewaehlte Figur gibt.)
         * ODER
         * (Wenn man dann auf eine seiner eigenen Figuren Klickt, wechselt 
         * die GUI  auf die moeglichen Felder dieser Figur.)
         */
        if ((momentanesFeld.getFigur() != null 
            && (momentanesFeld.getFigur().getFarbe() 
            == spielfeld.getAktuellerSpieler()) 
            && ausgewaehlteFigur == null) 
            || 
            (ausgewaehlteFigur != null && momentanesFeld.getFigur() != null 
            && momentanesFeld.getFigur().getFarbe() 
            == ausgewaehlteFigur.getFarbe()
            && !(momentanesFeld.getFigur().equals(ausgewaehlteFigur)))) {
            // Wird diese als neue Ausgewaehlte Figur gespeichert
            ausgewaehlteFigur = momentanesFeld.getFigur();
            /* Wenn der Spieler Weiss dran ist und dies angeklickte Figur 
             * eine weisse ist.
             */
            if (spielfeld.getAktuellerSpieler() 
                && spielfeld.getWeisseFiguren()
                    .contains(ausgewaehlteFigur)) {
                // Wird diese als neue Ausgewaehlte Figur gespeichert
                momentanesFeld.setBackground(rot);
                if (spielfeld.getEinstellungen().isMoeglicheFelderAnzeigen()) {
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
                if (spielfeld.getEinstellungen().isMoeglicheFelderAnzeigen()) {
                    for (Feld makieren : ausgewaehlteFigur.getKorrektFelder()) {
                        makieren.setBackground(rot);
                    }
                }
            }
        // Wenn es bereits eine ausgewaehlte Figur gibt 
        } else if (ausgewaehlteFigur != null 
            && ausgewaehlteFigur.getKorrektFelder().contains(momentanesFeld)) {
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
                    momentanerSpieler.setText("<html>Wei&szlig;");
                } else {
                    momentanerSpieler.setText("Schwarz");
                }
                // Wenn das Spiel nicht vorbei ist 
                if (!spielVorbei) {
                    // autosave initiieren
                    parent.autoSave(spiel);
                } 
            }
        // Wenn man die selbe Figur anklickt    
        } else if (momentanesFeld.getFigur() == null) {
            ausgewaehlteFigur = null;
        } else if (ausgewaehlteFigur != null
            && momentanesFeld.getFigur().equals(ausgewaehlteFigur)) {
            ausgewaehlteFigur = null; 
        } 
        
        // Faerbt die bedrohten Felder Grau
        if (spielfeld.getEinstellungen().isBedrohteFigurenAnzeigen()) {
            for (Feld bedroht : spielfeld.getBedrohteFelder()) {
                bedroht.setBackground(new Color(100, 100, 100));
            }
        }
        this.validate();
        this.repaint();  
    }
    

    /**
     * Hier werden die Zuege veranlasst und auf der Gui geupdated.
     * @param momentanesFeld Das momentan ausgewaehlte Feld
     */
    private void spielerzugGUI(Feld momentanesFeld) {
        // Hier ist der jetzige Zug beendet also auch die Zugzeit
        sekundenStopp = (System.currentTimeMillis()
            - sekundenStart) / 1000;
        // Ein Zug wird ausgefuehrt und die Zugzeit uebergeben
        spielfeld.ziehe(ausgewaehlteFigur, momentanesFeld,
            (int) sekundenStopp);
        spielfeldAufbau();
        revalidate();  
        // Neuer Spieler = keine Ausgewaehlte Figur
        ausgewaehlteFigur = null;
        spielfeld.getBedrohteFelder();
        Zug letzterZug = spielfeld.getSpieldaten().getLetzterZug();
        mattOderSchach();
        if (!spielVorbei && letzterZug instanceof Umwandlungszug)   {
            spielfeldAufbau();
            parent.soundAbspielen("Hinweis.wav");
            String[] moeglicheFiguren = {"Dame", "Turm", "Laeufer", 
                "Springer"};
            String s = (String) JOptionPane.showInputDialog(parent,
                "Waehlen Sie eine Figur aus, die Sie gegen den "
                + "Bauern tauschen wollen"
                , "Figurenwechsel", JOptionPane.
                PLAIN_MESSAGE, null, moeglicheFiguren, "Dame");
            int wert;
            if (s.equals("Dame")) {
                wert = 900;
            } else if (s.equals("Turm")) {
                wert = 465;
            } else if (s.equals("Laeufer")) {
                wert = 325;
            } else {
                wert = 275;
            }
            spielfeld.umwandeln(letzterZug.getFigur(), wert);
        }
        // Start der neuen Zugzeit
        start();
    }
    

    /**
     * Pr&uuml;ft nach einem Zug, ob der neue aktuelle Spieler Matt oder im 
     * Schach steht. Wenn der aktuelle Spieler im Matt steht dann wird die 
     * Spielauswertung ausgef&uuml;hrt und dem Benutzer/n wird ein JOptionPane
     * mit der Auswertung angezeigt. Hierbei wird zwischen Matt und Patt
     * unterschieden. Wenn der aktuelle Spieler im Schach steht dann wird die 
     * Methode {@link #schachWarnung()}
     */
    private void mattOderSchach() {
        // Wenn das Spiel vorbei ist
        if (spielfeld.schachMatt()) {
            spielfeldAufbau();
            // das Spiel ausgewertet
            List<Object> auswertung = spiel.auswertung();
            Spieler gewinner = (Spieler) auswertung.get(0);
            String ergebnis; 
            String zuege = auswertung.get(2).toString();
            if ((boolean) auswertung.get(1)) {
                ergebnis = gewinner.getName() 
                    + " gewinnt nach " + zuege + " Zuegen.";
            } else {
                ergebnis = "Das Spiel endet in einem Patt";
            }
            // Und Ein Dialogfenster fuer den Gewinner angezeigt
            parent.soundAbspielen("SchachMatt.wav");
            JOptionPane.showMessageDialog(parent
                , ergebnis);
            // Das spiel ist vorbei also keine Zuege mehr moeglich
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
     * Gibt eine Schachwarnung aus, wenn der aktuelle Spieler ein menschlicher 
     * Spieler ist. Computerspieler ben&ouml;tigen diese Warung aus 
     * offensichtilchen Gru&uuml;den nicht.
     */
    private void schachWarnung() {
        // Wenn kein Computerspieler dran ist und das Spiel nocht vorbei ist
        if (istComputerSpielerUndIstAmZug()
            && !spielVorbei) {
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
     * Action Performed fuer alle Buttons (R&uuml;ckg&auml;ngig, Remis, 
     * Aufgeben, Speichern, Wiederholung).
     * Wenn der Zur&uuml;ck-Button gedr&uuml;ckt wurde, dann m&uuml;ssen bei 
     * einem Computergegner immer 2 Z&uuml;ge r&uuml;ckg&auml;ngig gemacht 
     * werden, sonst nur einer. Zudem wird die zugzeit zur&uuml;ckgesetzt. 
     * Wenn der Remis-Button gedr&uuml;ckt wurde wird die 
     * {@link #remisAuswertung()}-Methode aufgerufen.
     * Wenn der Aufgeben-Button gedr&uuml;ckt wurde dann wird das Spiel beendet,
     * ausgewertet und eine Nachricht f&uuml;r den Gewinner wird angezeigt.
     * Wenn der Speichern-Button gedr&uuml;ckt wurde dann wird das Spiel 
     * gespeichert und ein JOptionPane zeigt die erfolgreiche Speicherung an.
     * Wenn der Wiederholungs-Button gedr&uuml;ckt wurde dann wird eine
     * Wiederholung des letzten Spiels gestartet.
     * @param e Ausgeloestes ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        // Wenn der momentante Spieler einen Zug rueckgaengig macht
        if (e.getActionCommand().equals(commandRueck)) {
            /* Wenn es einen Computerspieler gibt dann muss dessen Zug auch 
             * rueckgaenig gemacht werden.
            */
            if (spieler2 instanceof Computerspieler) {
                spielfeld.zugRueckgaengig();
            }
            spielfeld.zugRueckgaengig();
            // Labels wieder richtig setzen
            if (spielfeld.getAktuellerSpieler()) {
                momentanerSpieler.setText("<html>Wei&szlig;");
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
        if (e.getActionCommand().equals(commandRemis)) {
            remisAuswertung();
        }
        // Wenn der momentane Spieler aufgibt
        if (e.getActionCommand().equals(commandAufgeben)) {
            spielVorbei = true;
            List<Object> aufgeben = spiel
                .aufgeben(spielfeld.getAktuellerSpieler());
            Spieler verlierer = (Spieler) aufgeben.get(0);
            String zuege = aufgeben.get(1).toString();
            Spieler gewinner = (Spieler) aufgeben.get(2);
            parent.soundAbspielen("Aufgeben.wav");
            JOptionPane.showMessageDialog(parent, verlierer.getName() 
                + " gibt nach " + zuege + " Zuegen auf! " + gewinner.getName() 
                + " gewinnt!!!");
            // Das spiel ist vorbei also keine Zuege mehr moeglich
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
        // Wenn der Wiederholen-Button gedrueckt wird
        if (e.getSource().equals(btnWiederholung)) {
            // Wird das Steuerelement fuer Start/Stopp angezeigt
            btnStopp.setVisible(true);
            // Die Wiederholung startet
            wiederholung = true;
            start();
            // Wenn dies der erste Klick ist
            if (zaehler == -1) {
                // Muss die Liste aller zuege des Spiels geladen werden
                spielvideo = spiel.spielvideo();
                spielfeldAufbau();
            // Bei allen weiteren Klicks    
            } else {
                // Wird der naechste Zug aus der Zugliste ausgewaehlt
                Zug zug = spielvideo.get(zaehler);
                // Und ausgefuehrt
                spielfeld.ziehe(zug.getFigur(), zug.getZielfeld(), 
                    zug.getZugzeit());
                zugListe.setSelectedIndex(zaehler);
                // Wenn es ein Umwandlungszug war
                if (zug instanceof Umwandlungszug) {
                    // Muss nachtraeglich noch die figur umgewandelt werden
                    spielfeld.umwandeln(spielfeld.getSpieldaten()
                        .getLetzterZug().getFigur(), 
                        ((Umwandlungszug) zug).getNeueFigur().getWert());
                }
                spielfeldAufbau();
                // Start und Ziel feld werden a gruen makiert
                zug.getStartfeld().setBackground(gruen);
                zug.getZielfeld().setBackground(gruen);
            }
            // naechster Zug
            zaehler++;
            // Wenn alle Zuege ausgefuehrt wurden
            if (zaehler == spielvideo.size()) {
                // Wird der WiedeholungsButton ausgegraut
                btnWiederholung.setEnabled(false);
                // und Die Steuerelemente versteckt
                btnStopp.setVisible(false);
                // Die Wiederholung beendet
                wiederholung = false;
            }
        }
        // Wenn der Stopp-Button gedrueckt wurde
        if (e.getSource().equals(btnStopp)) {
            // boolean fuer Labelaenderung
            wiederholen = !(wiederholen);
            if (wiederholen) {
                // Wenn gerade widerholt wird kann man stoppen
                btnStopp.setText("Stopp");
                start();
            } else {
                // Wenn die Widerholung gestoppt ist kann man sie starten
                btnStopp.setText("Start");
            }
        }
        // Wenn das SpielGUI-Panel verlassen wird
        if (e.getActionCommand().equals(commandStartmenue)) {
            // Muss der Thread gestoppt werden
            uhrAktiv = false;
            // Und auf die Eroeffnungseite gewechselt werden
            parent.seitenAuswahl("Eroeffnungsseite");
        }
    }
    
    /**
     * Wenn der Remis-Button gedrueckt wird wird hier die Auswertung der Remis-
     * Anfrage bearbeitet.
     */
    private void remisAuswertung() {
     // Testen ob die 50-Zuege-Regel verletzt wurde
        if (spielfeld.getSpieldaten().fuenfzigZuegeRegel()) {
            // Unentschieden einreichen
            spielVorbei = true;
            spiel.unentschieden();
            parent.soundAbspielen("Hinweis.wav");
            JOptionPane.showMessageDialog(parent, "50 Zuege Regel wurde "
                + "erfuellt. Das Spiel endet in einem Unentschieden");
            this.remove(cEast);
            cEndeErstellen();
            this.add(cEnde, BorderLayout.EAST);
            this.validate();
            this.repaint();  
        } else {
            if (!(spieler2 instanceof Computerspieler)) {
                parent.soundAbspielen("Hinweis.wav");
                int eingabe = JOptionPane.showConfirmDialog(parent, 
                    "Moechten Sie sich auf ein Unentschieden einigen?");
                if (eingabe == 0) {
                    spielVorbei = true;
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
                        spieler = spieler2;
                    } else {
                        spieler = spieler1;
                    }
                    parent.soundAbspielen("Aufgeben.wav");
                    JOptionPane.showMessageDialog(parent, 
                        spieler.getName() + " hat das Remis abgelehnt");
                }
            } else {
                if ((boolean) ((Computerspieler) spieler2)
                    .unentschiedenAnnehmen()) {
                    parent.soundAbspielen("Hinweis.wav");
                    JOptionPane.showMessageDialog(parent, "Spiel endet "
                        + "unentschieden.");
                    spielVorbei = true;
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
    
    /**
     * Startet eine neue Zeitnahmesession fuer die Zugzeit der Spieler.
     */
    private void start() {
        // Neue Zeit anfangen
        uhrAktiv = true;
        // Thread anlegen 
        sekundenStart = System.currentTimeMillis();
    }
    
    /**
     * Runnable Methode zum erstellen und &uuml;berwachen der Zugzeit und zum
     * ausf&uuml;hren der Wiederholungsfunktion.
     * Solange der Thread aktiv ist (wird noch von einer Funktion gebraucht)
     * wird alle 10ms die Uhrzeit auf dem daf&uuml;r passendem Label 
     * aktualisiert und gepr&uuml;ft ob die maximale Zugzeit &uuml;berschritten
     * worden ist. 
     * Wenn der Wiederholungs-Boolean true ist dann wird alle 2s der 
     * Wiederholungs-Button geklickt um so ein Spielvideo zu erzeugen.   
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
            sekundenStopp = System.currentTimeMillis()
                    - sekundenStart;
            // Formatierungshilfe fuer Zeiten
            Calendar dauer;
            dauer = Calendar.getInstance();
            // Zeit in Millisekunden uebergeben
            dauer.setTimeInMillis(sekundenStopp);
            // und in Minuten/Sekunden/Millisekunden (0-9/0-9/0-99)
            int min;
            int sek;
            int ms;
            min = dauer.get(Calendar.MINUTE);
            sek = dauer.get(Calendar.SECOND);
            ms = dauer.get(Calendar.MILLISECOND);
            
            // An den AusgabeString anhaengen
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
            if (!spielVorbei) {
                int begrenzung = spielfeld.getEinstellungen()
                    .getZugZeitBegrenzung();
                // Wenn die Zugzeit > Maximale zugzeit --> Aufgeben Button
                if (begrenzung > 0 && sekundenStopp / 1000 >= begrenzung) {
                    aufgeben.doClick();
                }
            }
            // Wenn ein Spielvideo angeziegt werden soll
            if (btnWiederholung.isEnabled()) {
                /* Wenn wiederholt werden soll und nicht stopp ist und 2 oder 
                 * mehr Sekunden vergangen sind 
                */
                if (wiederholung && wiederholen && sekundenStopp >= 2000) {
                    // Dann soll der naechste zug ausgefuert werden
                    btnWiederholung.doClick();
                    start();
                }
            // Wenn die Wiederholung vorbei ist    
            } else {
                // Thread beenden
                uhrAktiv = false;
            }
            
        }
    }
    
    /**
     * Component Listener Methode.
     * @param e durch Component ausgeloestes Event
     */
    public void componentHidden(ComponentEvent e) {
    }
    
    /**
     * Component Listener Methode.
     * @param e durch Component ausgeloestes Event
     */
    public void componentMoved(ComponentEvent e) {
    }
    
    /**
     * Sorgt dafuer, dass nach einem resize die Spielfiguren in der richtigen 
     * Groesse angezeigt werden.
     * @param e durch resizen ausgeloestes Event
     */
    public void componentResized(ComponentEvent e) {
        spielfeldUIUpdate();
    }
    
    /**
     * Component Listener Methode.
     * @param e durch resizen ausgeloestes Event
     */
    public void componentShown(ComponentEvent e) {
    }
}
