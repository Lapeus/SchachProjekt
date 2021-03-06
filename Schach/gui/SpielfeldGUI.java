package gui;

import javax.imageio.ImageIO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import daten.Computerspieler;
import daten.Spiel;
import daten.Spieldaten;
import daten.Spieler;
import daten.Spielfeld;
import figuren.Figur;
import zuege.Zug;
import zuege.Umwandlungszug;

/**
 * Eine Klasse die von <b>JPanel</b> erbt und einen eigenen <b>MouseListener</b>
 * implementiert und f&uuml;r die Darstellung des Spielfeld-Fensters 
 * zust&auml;ndig ist.
 * @author Marvin Wolf 
 */
public class SpielfeldGUI extends JPanel implements MouseListener, 
    ActionListener, Runnable, ComponentListener {
    
    /**
     * Serial Key zur Identifizierung.
     */
    private static final long serialVersionUID = -5131381120236108231L;
    
    /**
     * Das ElternGUI-Objekt von dem aus das jeweilige Spielfeld aufgerufen 
     * wurde.
     */
    private SpielGUI parent;
    
    /**
     * Button um das Spiel zu speichern.
     */
    private JButton speichern = new JButton("Spiel speichern");
    
    /**
     * Button um einen Zug r&uuml;ckg&auml;ngig zu machen.
     */
    private JButton rueckgaengig 
        = new JButton("<html>Zug r&uuml;ckg&auml;ngig");
    
    /**
     * Button um ein Unentschieden anzubieten.
     */
    private JButton btnUnentschieden = new JButton("Remis anbieten");
    
    /**
     * Button um aufzugeben.
     */
    private JButton aufgeben = new JButton("Spiel aufgeben");
    
    /**
     * Button zum Spielwiederholung ansehen.
     */
    private JButton btnWiederholung = new JButton("Wiederholung ansehen");
    
    /**
     * Button zum Pausieren / Weiterf&uuml;hren der Wiederholung.
     */
    private JButton btnStopp = new JButton("Stopp");
    
    /**
     * JLabel zum Anzeigen des momentanen Spielers.
     */
    private JLabel momentanerSpieler = new JLabel();
    
    /**
     * JLabel zum Anzeigen der Zugzeit des momentanen Spielers.
     */
    private JLabel zugzeit;
    
    /**
     * JLabel zum Anzeigen des letzten Zugs.
     */
    private JLabel letzterZug;
    
    /**
     * JPanel f&uuml;r die geschlagenen schwarzen Figuren.
     */
    private JPanel geschlageneSchwarze = new JPanel(); 
    
    /**
     * JPanel f&uuml;r die geschlagenen wei&szlig;en Figuren.
     */
    private JPanel geschlageneWeisse = new JPanel();
    
    /**
     * Spieler1, welcher von der <b>Spielerauswahl</b>-Seite &uuml;bergeben 
     * wird.
     */
    private Spieler spieler1;
    
    /**
     * Spieler2, welcher von der <b>Spielerauswahl</b>-Seite &uuml;bergeben 
     * wird.
     */
    private Spieler spieler2;
    
    /**
     * Liste, die 64 Schachfelder enth&auml;lt.
     */
    private List<Feld> felderListe;
    
    /**
     * Objekt der Klasse <b>Spielfeld</b>, welches f&uuml;r Figurenpositionen 
     * ben&ouml;tigt wird.
     */
    private Spielfeld spielfeld;
    
    
    /**
     * Objekt der Klasse <b>Spiel</b>, welche das Spiel speichert zu dem dieses 
     * Spielfeld geh&ouml;rt.
     */
    private Spiel spiel;
    
    /**
     * Enth&auml;lt momentan ausgew&auml;hlte Figur. <br>
     * Wird ben&ouml;tigt um Zug auszuf&uuml;hren, da man nur ziehen kann 
     * wenn man vorher schon eine Figur ausgew&auml;hlt hat.
     */
    private Figur ausgewaehlteFigur;
    
    /**
     * Zugliste f&uuml;r den Engamescreen.
     */
    private JList<String> zugListe;
    
    /**
     * JPanel f&uuml;r das Spielfeld. Wird in der <i>spielfeldUpdate</i> 
     * Methode ben&ouml;tigt.  
     */
    private Container cCenter = new JPanel();
    
    /**
     * Kontainer f&uuml;r die Anzeigen und Buttons neben dem Spielfeld.
     */
    private JPanel cEast = new JPanel();
    
    /**
     * Panel welches nach Beendigung eines Spiels cEast ersetzt.
     */
    private JPanel cEnde = new JPanel();
    
    /**
     * Konstante f&uuml;r den Farbton des Hintergrundes.
     */
    private Color hintergrund; 
    
    /**
     * Konstante f&uuml;r den Farbton der Buttons.
     */
    private Color buttonFarbe;
    
    /**
     * Konstante f&uuml;r den Farbton der "wei&szlig;en" Felder.
     */
    private Color spielfeldWeiss;
    
    /**
     * Konstante f&uuml;r den Farbton der "schwarzen" Felder.
     */
    private Color spielfeldSchwarz;
    
    /**
     * Konstante f&uuml;r den Farbton der markierten Felder.
     */
    private Color markierteFelder;
    
    /**
     * Konstante f&uuml;r den Farbton der schlagenden Felder.
     */
    private Color schlagendeFelder;
    
    /**
     * Konstante f&uuml;r den Farbton der bedrohten Felder.
     */
    private Color bedrohteFelderFarbe;
    
    /**
     * Konstante f&uuml;r den Farbton der Letzten-Zug-Felder.
     */
    private Color letzterZugFarbe;
    
    /**
     * Konstante f&uuml;r den Farbton des im Schach stehenden K&ouml;nigs.
     */
    private Color schachFarbe;
    /**
     * Konstante f&uuml;r den Farbton des Matt gesetzten K&ouml;nigs.
     */
    private Color koenigMatt;
    
    /**
     * Konstante f&uuml;r den Farbton der am Matt beteiligten Figuren.
     */
    private Color beteiligteFigurenFarbe;
    /**
     * Action Command f&uuml;r den R&uuml;ckg&auml;ngig-Button.
     */
    private final String commandRueck = "rueck";
    
    /**
     * Action Command f&uuml;r den Speichern-Button.
     */
    private final String commandSpeichern = "speichern";
    
    /**
     * Action Command f&uuml;r den Aufgeben-Button.
     */
    private final String commandAufgeben = "aufgeben";
    
    /**
     * Action Command f&uuml;r Remis-Button.
     */
    private final String commandRemis = "remis";
    
    /**
     * Action Command f&uuml;r den Startmen&uuml;-Button.
     */
    private final String commandStartmenue = "spielende";
    
    /**
     * Startzeit f&uuml;r die Zugzeit-Stoppuhr (&Uuml;ber Systemzeit). 
     */
    private long sekundenStart;
    
    /**
     * Endzeit f&uuml;r die Zugzeit-Stoppuhr (Endzeit - Startzeit). 
     */
    private long sekundenStopp;
    
    /**
     * Gibt an, ob die Uhr aktiv ist.
     */
    private boolean uhrAktiv = false; 
    
    /**
     * Gibt an, ob Spiel vorbei ist.
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
     * Wenn das Spiel zu Ende ist dann muss der Timer das wissen.
     */
    private boolean wiederholung = false;
    
    /**
     * Wenn der Benutzer das Spielvideo unterbrechen will, wird dieser Boolean
     * auf <b>false</b> gesetzt.
     */
    private boolean wiederholen = true;
    
    /**
     * Thread f&uuml;r die Stoppuhr.
     */
    private Thread th = new Thread(this);
    
    /**
     * Zeigt dem Computergegner, dass er jetzt ziehen soll.
     */
    private boolean[] jetztIstComputerDran = {false, false};
    
    /**
     * Die Progressbar zum Anzeigen der Rechenzeit.
     */
    private JProgressBar progBar;
    
    /**
     * Die Zugzeit von wei&szlig.
     */
    private int zugZeitWeiss;
    
    /**
     * Die Zugzeit von schwarz.
     */
    private int zugZeitSchwarz;
    
    /**
     * Erzeugt eine SpielfeldGUI.
     * Wird von der Spielerauswahl-Seite aufgerufen und erstellt eine 
     * SpielfeldGUI f&uuml;r ein neues Spiel.
     * @param parent Das Objekt der dazugeh&ouml;rigen <b>SpielGUI</b>
     * @param spieler1 Ein Objekt der Klasse <b>Spieler</b>
     * @param spieler2 Ein weiteres Objekt der Klasse <b>Spieler</b>
     * @param spielname Name des Spiels muss &uuml;bergeben werden
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
        spielfeld.setEinstellungen(parent.getEinstellungenCopy());
        spielfeld.setSpieldaten(new Spieldaten());
        
        // Einem Computergegner das Spielfeld uebergeben
        if (spieler2 instanceof Computerspieler) {
            ((Computerspieler) spieler2).setSpielfeld(spielfeld);
        }
        
        // Spiel 
        spiel = new Spiel(spielname, spieler1, spieler2, spielfeld);
        
        farbInit();
        init();
    }
    
    /**
     * Erzeugt eine neue SpielfeldGUI.
     * Wird von der SpielLaden-Seite aufgerufen und erzeugt eine SpielfeldGUI 
     * f&uuml;r ein bereits gespeichertes Spiel.
     * @param parent Das Objekt der dazugeh&ouml;rigen <b>SpielGUI</b>
     * @param spiel Ein Objekt von Typ Spiel, welches bereits Paramenter hat
     * @param beendet Ob das Spiel beendet ist
     */
    public SpielfeldGUI(SpielGUI parent, Spiel spiel, boolean beendet) {
        this.parent = parent;
        this.spieler1 = spiel.getSpieler1();
        this.spieler2 = spiel.getSpieler2();
        this.spielfeld = spiel.getSpielfeld();
        this.felderListe = spielfeld.getFelder();
        this.spiel = spiel;
        
        for (Feld feld : felderListe) {
            feld.addMouseListener(this);
        }
        
        spielVorbei = beendet;
        
        parent.revalidate();
        farbInit();
        init();
        
        if (beendet) {
            remove(cEast);
            cEndeErstellen();
            add(cEnde, BorderLayout.EAST);
        } else {
            // Je nach aktuellem Spieler wird das Label gesetzt
            momentanerSpielerUpdate();
            if (parent.getEinstellungen().isBedrohteFigurenAnzeigen()) {
                for (Feld bedroht : spielfeld.getBedrohteFelder()) {
                    bedroht.setBackground(bedrohteFelderFarbe);
                }
            }
        }

    }
    
    /**
     * Initialisierungsmethode eines Spielfelds.
     * Wird vom Konstruktor aufgerufen. <br>
     * Ruft auf: <br>
     * 1. F&uuml;llung der felderListe <br>
     * 2. Erzeugt ein <b>Spielfeld</b> mit dieser felderListe <br>
     * 3. Erzeugt ein <b>Spiel</b> mit Spielname, spieler1, spieler2 und
     *  dem spielfeld <br>
     * 4. Erstellt das Aussehen des Spielfelds  
     */
    private void init() { 
        Dimension maxsize = new Dimension(GraphicsEnvironment
            .getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
        
        Dimension minsize = new Dimension(1200, 800);
        if (minsize.width > maxsize.width) {
            minsize.width = maxsize.width;
        }
        if (minsize.height > maxsize.height) {
            minsize.height = (int) (maxsize.height);
        }
        parent.setMinimumSize(minsize);
        parent.setMaximumSize(maxsize);
        parent.setSize(minsize);
        parent.setLocationRelativeTo(null);
        parent.setTitle(spieler1.getName() + " vs. " + spieler2.getName());
        
        // CENTER
        cCenter.addComponentListener(this);
        setLayout(new BorderLayout());
        cCenter.setBackground(new Color(0, 0, 0));
        cCenter.setLayout(new GridLayout(8, 8, 1, 1));
        
        // EAST
        cEast.setBackground(hintergrund);
        GridBagConstraints gbc = new GridBagConstraints();
        cEast.setLayout(new GridBagLayout());
        
        // geschlageneLabelW
        JLabel lGeschlageneW 
            = new JLabel("<html>Geschlagene Wei&szlig;e Figuren:");
        lGeschlageneW.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(20, 10, 10, 20);
        cEast.add(lGeschlageneW, gbc); 
        
        //geschlageneLabelB
        JLabel lGeschlageneB = new JLabel("Geschlagene Schwarze Figuren:");
        lGeschlageneB.setForeground(Color.BLACK);
        gbc.gridy = 10;
        cEast.add(lGeschlageneB, gbc);
        
        // geschlagene Weiss
        geschlageneWeisse.setBackground(hintergrund);
        geschlageneWeisse.setLayout(new GridLayout(0, 5));
        gbc.gridy = 1;
        gbc.gridheight = 2;
        cEast.add(geschlageneWeisse, gbc);
        
        // geschlagene Schwarz
        geschlageneSchwarze.setBackground(hintergrund);
        geschlageneSchwarze.setLayout(new GridLayout(0, 5));
        gbc.gridy = 11;
        cEast.add(geschlageneSchwarze, gbc);
        
        // Label momentanerSpieler
        // Je nach aktuellem Spieler wird das Label gesetzt
        momentanerSpielerUpdate();
        momentanerSpieler.setBackground(buttonFarbe);
        gbc.gridheight = 1;
        gbc.gridy = 3;
        cEast.add(momentanerSpieler, gbc);
        
        // Label letzterZug
        letzterZug = new JLabel();
        letzterZug.setForeground(Color.BLACK);
        letzterZug.setBackground(buttonFarbe);
        if (null != spielfeld.getSpieldaten().getLetzterZug()) {
            letzterZug.setText(spielfeld.getSpieldaten().getLetzterZug()
                .toSchachNotation());
        }
        gbc.gridy = 4;
        cEast.add(letzterZug, gbc);
        
        // Label Stoppuhr
        zugzeit = new JLabel();
        zugzeit.setBackground(buttonFarbe);
        gbc.gridy = 8;
        cEast.add(zugzeit, gbc);
        
        // Progressbar
        progBar = new JProgressBar();
        progBar.setForeground(Color.GREEN);
        progBar.setVisible(false);
        progBar.setPreferredSize(new Dimension(140, 14));
        progBar.setMinimumSize(progBar.getSize());
        progBar.setMaximumSize(progBar.getSize());
        progBar.setStringPainted(true);
        gbc.gridy = 9;
        cEast.add(progBar, gbc);
        
        // Button rueck
        rueckgaengig.setForeground(Color.BLACK);
        rueckgaengig.setBackground(buttonFarbe);
        rueckgaengig.addActionListener(this);
        rueckgaengig.setActionCommand(commandRueck);
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        cEast.add(rueckgaengig, gbc);
        
        // Button speichern
        speichern.setBackground(buttonFarbe);
        speichern.addActionListener(this);
        speichern.setActionCommand(commandSpeichern);
        gbc.gridx = 2;
        gbc.gridy = 5;
        cEast.add(speichern, gbc);
        
        // Button unentschieden
        btnUnentschieden.setBackground(buttonFarbe);
        btnUnentschieden.addActionListener(this);
        btnUnentschieden.setActionCommand(commandRemis);
        gbc.gridx = 1;
        gbc.gridy = 7;
        cEast.add(btnUnentschieden, gbc);
        
        
        // Button aufgeben
        aufgeben.setBackground(buttonFarbe);
        aufgeben.addActionListener(this);
        aufgeben.setActionCommand(commandAufgeben);
        gbc.gridx = 2;
        gbc.gridy = 7;
        cEast.add(aufgeben, gbc);
        
        // Zu Panel hinzufuegen
        add(cCenter, BorderLayout.CENTER);
        add(cEast, BorderLayout.EAST);
        
        // Zugzeit fuer den ersten Zug starten
        start();
        // Thread starten
        th.start(); 
        
        // SpielfeldGUI erstellen
        spielfeldAufbau();
 
        /* Wenn ein Computerspieler mitspielt und anfangen soll muss er hier 
         * den ersten Zug machen
         */
        jetztIstComputerDran[0] = true;
    }
    
    /**
     * Initialisiert die Farben.
     */
    private void farbInit() {
        hintergrund = parent.getFarben()[0];
        buttonFarbe = parent.getFarben()[1];
        spielfeldWeiss = parent.getFarben()[2];
        spielfeldSchwarz = parent.getFarben()[3];
        markierteFelder = parent.getFarben()[4];
        schlagendeFelder = parent.getFarben()[5];
        bedrohteFelderFarbe = parent.getFarben()[6];
        letzterZugFarbe = parent.getFarben()[7];
        schachFarbe = parent.getFarben()[8];
        koenigMatt = parent.getFarben()[9];
        beteiligteFigurenFarbe = parent.getFarben()[10];
    }
    
    /**
     * Ansicht, welche nach Spielende aufgerufen wird.
     * Bietet einen Button zum Zur&uuml;ckkehren auf die 
     * {@link Eroeffnungsseite}. Zudem wird eine Option zum Wiederholen des
     * gerade gespielten Spiels angeboten und dessen Zugliste wird angezeigt.
     * Es werden alle Autosavedaten des Spiels gel&ouml;scht. 
     */
    private void cEndeErstellen()  {
        // Listener von den Feldern entfernen(keine Zuege mehr moeglich)
        for (Feld feld : felderListe) {
            feld.removeMouseListener(this);
        }
        // Alle Autosave Dateien des Spiels loeschen
        parent.autoSaveLoeschen();
        
        // cEnde
        cEnde.setLayout(new GridBagLayout());
        cEnde.setBackground(hintergrund);
        GridBagConstraints gbc2 = new GridBagConstraints();
        
        // Startmenue Button
        JButton startmenue = new JButton("<html>Zur&uuml;ck zum Startmen&uuml");
        startmenue.setActionCommand(commandStartmenue);
        startmenue.addActionListener(this);
        startmenue.setBackground(buttonFarbe);
        gbc2.insets = new Insets(15, 15, 15, 15);
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        cEnde.add(startmenue, gbc2);
        
        // Wiederholung-Anzeigen-Button
        btnWiederholung.setBackground(buttonFarbe);
        btnWiederholung.addActionListener(this);
        gbc2.gridy = 1;
        cEnde.add(btnWiederholung, gbc2);
        if (spiel.getSpielname().contains("(edit)")) {
            btnWiederholung.setEnabled(false);
        }
        
        // Pause-Button fuer die Wiederholung
        btnStopp.setBackground(buttonFarbe);
        btnStopp.addActionListener(this);
        btnStopp.setVisible(false);
        gbc2.gridy = 2;
        cEnde.add(btnStopp, gbc2);
        
        // Zugliste
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        String[] zuege = spielfeld.getSpieldaten().toString()
            .split(System.getProperty("line.separator"));
        for (String string : zuege) {
            if (!string.equals("")) {
                listModel.addElement(string);
            }
        }
        zugListe = new JList<String>(listModel);
        zugListe.setBackground(buttonFarbe);
        JScrollPane sPane = new JScrollPane(zugListe);
        sPane.setBackground(buttonFarbe);
        gbc2.gridy = 3;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        cEnde.add(sPane, gbc2);
        cEnde.revalidate();
    }
    
    /**
     * F&uuml;llt die felderListe mit 64 Feldern(Index 0-7, 0-7).
     * F&uuml;gt zudem jedem Feld einen MouseListener hinzu (this)
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
     * Feldern der {@link #felderListe} gef&uuml;llt und die Labels bekommen 
     * ihre Farbe. Dann wird die Methode {@link #spielfeldUIUpdate()} 
     * aufgerufen, welche f&uuml;r die Darstellung der Figuren sorgt.
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
                    temp.setBackground(spielfeldSchwarz);
                    abwechslung = true;
                // Wenn die Farbe "weiss"(Beige) ist dann Feld beige machen    
                } else {
                    temp.setBackground(spielfeldWeiss);
                    abwechslung = false;
                }
                // Dem cCenter Panel das fertige Feld hinzufuegen
                cCenter.add(temp);
            }    
            counter -= 8;
        }
        // Faerbt die bedrohten Felder
        if (spielfeld.getEinstellungen().isBedrohteFigurenAnzeigen() 
            && !spielVorbei) {
            for (Feld bedroht : spielfeld.getBedrohteFelder()) {
                if (bedroht.getFigur().getWert() == 0) {
                    bedroht.setBackground(schachFarbe);
                } else {
                    bedroht.setBackground(bedrohteFelderFarbe);
                }
            }
        }
        if (spielfeld.getEinstellungen().isSpielfeldDrehen() && !wiederholung 
            && !(spieler2 instanceof Computerspieler)) {
            spielfeldDrehen();
        }
        /* Spielfeld drehen wenn Spieler 1 schwarz ist und ein Computergegner
        dran ist */
        if (!(spieler1.getFarbe())
                && spieler2 instanceof Computerspieler
                && spielfeld.getEinstellungen().isSpielfeldDrehen()
                && !spielVorbei) {
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
                for (int j = 7; j >= 0; j--) {
                    cCenter.add(felderListe.get(j + i * 8));
                }
            }
        }
        revalidate();  
    }
    
    /**
     * Sorgt daf&uuml;r, dass jede Figur ihr passendes Figurenbild erh&auml;lt.
     * Zudem wird hier gesteuert ob der r&uuml;ckg&auml;ngig-Button klickbar(Zug
     * vorhanden) oder nicht(kein Zug vorhanden) ist. Die 
     * Figurenbildergr&ouml;&szlig;en werden an die Gr&ouml;&szlig;e des 
     * Fensters angepasst. Dann wird noch die {@link #geschlageneFigureUpdate()}
     * Methode aufgerufen.
     */
    private void spielfeldUIUpdate() {
        if (spielfeld.getSpieldaten().getZugListe().isEmpty()
            || (spieler2 instanceof Computerspieler 
                && spielfeld.getSpieldaten().getZugListe().size() < 2)) {
            rueckgaengig.setEnabled(false);
        } else {
            rueckgaengig.setEnabled(true);
        }
        // Alle Bilder loeschen damit keine Bilder doppelt bleiben
        for (Feld feld : felderListe) {
            feld.setIcon(null);
        }
        // - schwarze Figurenbilder
        for (Figur schwarz  : spielfeld.clone(spielfeld.getSchwarzeFiguren())) {
            // Feld der Figur abspeichern
            Feld momentan = schwarz.getPosition();
            // Image in der Mitte zentrieren lassen
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            // Die Groesse an die Fenstergroesse angepasst
            int width = parent.getWidth() / 10;
            int height = parent.getHeight() / 9;
            // Das Bild aus dem Dateipfad geladen
            Image imageB = getImage(schwarz);
            // rescaled
            ImageIcon iconB  = new ImageIcon(imageB
                .getScaledInstance(width, height, Image.SCALE_SMOOTH));
            // Als Icon des Feldes setzen
            momentan.setIcon(iconB);
        }
        // - weisse Figurenbilder
        for (Figur weiss  : spielfeld.clone(spielfeld.getWeisseFiguren())) {
            // Feld der Figur abspeichern
            Feld momentan = weiss.getPosition();
            // Image in der Mitte zentrieren lassen
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            // Die Groesse an die Fenstergroesse angepasst
            int width = parent.getWidth() / 10;
            int height = parent.getHeight() / 9;
            // Das Bild aus dem Dateipfad geladen
            Image imageW = getImage(weiss);
            // rescaled
            ImageIcon iconW  = new ImageIcon(imageW
                .getScaledInstance(width, height, Image.SCALE_SMOOTH));
            // Als Icon des Feldes setzen
            momentan.setIcon(iconW);
        }
        geschlageneFigureUpdate();
        revalidate();  
    }
    
    /**
     * Updated die Anzeigen der geschlagenen Figuren.
     * Geht die geschlageneFiguren-Liste der jeweiligen Farbe durch und 
     * zeigt diese in dem jeweiligen Container an.
     */
    private void geschlageneFigureUpdate() {
        // Alle Labels entfernen
        geschlageneSchwarze.removeAll();
        // Fuer alle geschlagenen schwarzen Figuren
        for (Figur schwarz : spielfeld.getGeschlagenSchwarzSort()) {
            // neues Label fuer das Icon erstellen
            JLabel momentan = new JLabel();
            // Icons zentrieren
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            // Bild aus Dateipfad laden
            Image imageB = getImage(schwarz);
            // rescalen
            ImageIcon iconB  = new ImageIcon(
                imageB.getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            // Label Icon hinzufuegen
            momentan.setIcon(iconB);
            // Container Label hinzufuegen
            geschlageneSchwarze.add(momentan); 
        }
        // Alle Label entfernen       
        geschlageneWeisse.removeAll();
        // Fuer alle geschlagenen weissen Figuren
        for (Figur weiss : spielfeld.getGeschlagenWeissSort()) {
            // neues Label fuer das Icon erstellen
            JLabel momentan = new JLabel();
            // Icons zentrieren
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            // Bild aus Dateipfad laden
            Image imageW = getImage(weiss);
            // rescalen
            ImageIcon iconW  = new ImageIcon(
                imageW.getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            // Label Icon hinzufuegen
            momentan.setIcon(iconW);
            // Container Label hinzufuegen
            geschlageneWeisse.add(momentan);
            
        }
    }
     /**
      * Gibt f&uuml;r eine &uuml;bergebene Figur das passende Image zur&uuml;ck.
      * @param figur Die Figur, f&uuml;r die ein Bild geladen werden soll
      * @return Das passende Bild f&uuml;r die &uuml;bergebene Figur
      */
    private Image getImage(Figur figur) {
        String fileSep = System.getProperty("file.separator");
        String name = "";
        Image image = null;
        // Wenn die Figur Weiss ist
        if (figur.getFarbe()) {
            // Je nach Wert der Figur das den passenden Bildpfad speichern
            if (figur.getWert() == 900) {
                name = "pictures" + fileSep + "queenw.gif";
            }
            if (figur.getWert() == 100) {
                name = "pictures" + fileSep + "pawnw.gif";
            }
            if (figur.getWert() == 0) {
                name = "pictures" + fileSep + "kingw.gif";
            }
            if (figur.getWert() == 325) {
                name = "pictures" + fileSep + "bishopw.gif";
            }
            if (figur.getWert() == 275) {
                name = "pictures" + fileSep + "knightw.gif";
            }
            if (figur.getWert() == 465) {
                name = "pictures" + fileSep + "rookw.gif";
            }
            try {
                // Bild aus Dateipfad laden
                image = ImageIO.read(getClass().getResource("/" + name));
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        // Wenn die Figur schwarz ist
        } else {
            // Je nach Wert der Figur das den passenden Bildpfad speichern
            if (figur.getWert() == 900) {
                name = "pictures" + fileSep + "queenb.gif";
            }
            if (figur.getWert() == 100) {
                name = "pictures" + fileSep + "pawnb.gif";
            }
            if (figur.getWert() == 0) {
                name = "pictures" + fileSep + "kingb.gif";
            }
            if (figur.getWert() == 325) {
                name = "pictures" + fileSep + "bishopb.gif";
            }
            if (figur.getWert() == 275) {
                name = "pictures" + fileSep + "knightb.gif";
            }
            if (figur.getWert() == 465) {
                name = "pictures" + fileSep + "rookb.gif";
            }
            try {
                // Bild aus Dateipfad laden
                image = ImageIO.read(getClass().getResource("/" + name));
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
        return image;
    }
    
    /**
     * Updated den das momentanterSpieler Label.
     */
    private void momentanerSpielerUpdate() {
        int zugNr = (spielfeld.getSpieldaten().getZugListe().size() + 1) / 2;
        String name;
        // Wenn Spieler1 dran ist
        if (spielfeld.getAktuellerSpieler() == spieler1.getFarbe()) {
            // Wird der Name von Spieler1 angezeigt
            name = spieler1.getName();
        // Wenn Spieler2 dran ist
        } else {
            // Wird der Name von Spieler2 angezeigt
            name = spieler2.getName();
        }
        if (spielfeld.getAktuellerSpieler()) {
            // Wenn es ein Edit-Spiel ist und schwarz beginnt
            if (!spielfeld.getSpieldaten().getZugListe().isEmpty()
                && spielfeld.getSpieldaten().getZugListe()
                .get(0).getZielfeld() == null) {
                zugNr -= 1;
            } 
            momentanerSpieler.setText(zugNr + 1 + ". " + name);
            momentanerSpieler.setForeground(Color.WHITE);
        } else {
            momentanerSpieler.setText(zugNr + ". " + name);
            momentanerSpieler.setForeground(Color.BLACK);
        }
    }
    
    /**
     * Wertet aus, ob der Spieler2 ein Computerspieler ist und ob dieser 
     * momentan am Zug ist.
     * @return true - ist ein Computerspieler und am Zug<br>
     * false - ist kein Computerspieler / ist ein Computerspieler, aber nicht
     * am Zug
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
     * Wenn der Computergegner dran ist, werden die Verhaltensm&ouml;glichkeiten
     * abgefragt und ausgef&uuml;hrt.
     * Wenn die 50-Z&uuml;ge Regel erf&uuml;llt ist, dann wird der Computer ein
     * Unentschieden erzwingen.
     * Wenn der Computer ziehen soll, dann wird eine neue Zugzeit gestartet, der
     * Zug ausgef&uuml;hrt und dann die Zeit gestoppt und dem Zug 
     * nachtr&auml;glich &uuml;bergeben. Dabei wird mit der 
     * {@link #mattOderSchach()}-Methode nach jedem Zug auf Schach oder Matt des
     * folgenden Spielers kontrolliert. 
     * Dann werden Start- und Zielfeld des letzten Zugs eines Computers 
     * gr&uuml;n markiert.
     */
    private void wennComputerDannZiehen() {
        // Wenn spieler 2 ein Computergegner ist und dran ist
        if (istComputerSpielerUndIstAmZug()) {
            // Testen ob die 50-Zuege-Regel verletzt wurde
            if (spielfeld.getSpieldaten().fuenfzigZuegeRegel()) {
                // Unentschieden einreichen
                spiel.unentschieden();
                parent.soundAbspielen("Hinweis.wav");
                JOptionPane.showMessageDialog(parent, "<html>50 Z&uuml;ge Regel"
                    + " wurde erf&uuml;llt. Das Spiel endet mit einem "
                    + "Unentschieden. <br>" + zugZeitAnzeige());
                remove(cEast);
                cEndeErstellen();
                spielSpeichern();
                add(cEnde, BorderLayout.EAST);
                revalidate(); 
            } else {
                // Zugzeit neu starten
                start();
                // Zug ausfuehren
                SwingWorker<Void, Void> cpS = new SwingWorker<Void, Void>() {
                    
                    protected void done() {
                        parent.setEnabled(true);
                        // auf Matt und Schach Pruefen
                        mattOderSchach();
                        spielfeldAufbau();
                        // Zugzeit stoppen
                        sekundenStopp = (System.currentTimeMillis()
                            - sekundenStart);
                        // und nachtraeglich uebergeben
                        spielfeld.getSpieldaten().getLetzterZug().setZugzeit(
                            (int) (sekundenStopp / 1000));
                        zugZeitAktualisierung();
                        // Wenn das Spiel nicht vorbei ist und es kein Editor-
                        // Spiel ist
                        if (!spielVorbei 
                            && !spiel.getSpielname().contains("(edit)")) {
                            // autosave initiieren
                            parent.autoSave(spiel);
                        }
                        // Je nach aktuellem Spieler wird das Label gesetzt
                        momentanerSpielerUpdate();
                        // Wenn es einen letzen Zug gibt wird dieser angezeigt
                        if (spielfeld.getSpieldaten().getLetzterZug() != null) {
                            letzterZug.setText(spielfeld.getSpieldaten()
                                .getLetzterZug().toSchachNotation());
                        }
                        start();
                        // Letzten Zug gruen makieren
                        for (Feld feld : spielfeld.getLetzteFelder()) {
                            feld.setBackground(letzterZugFarbe);
                        }
                        // Die Berechnung des Computers ist jetzt vorbei
                        jetztIstComputerDran[1] = false;
                    }
                   
                    protected Void doInBackground() throws Exception {
                        parent.setEnabled(false);
                        jetztIstComputerDran[1] = true;
                        ((Computerspieler) spieler2).ziehen(progBar);
                        progBar.setValue(0);
                        progBar.setVisible(false);
                        return null;
                    }
                };
                cpS.execute();
                     
            }
        }
    }
    
    /**
     * Die durch einen Mausklick aufgerufene Methode. Verarbeitet diesen 
     * Mausklick.
     * @param arg0 Das ausgel&ouml;ste MouseEvent
     */
    public void mouseClicked(MouseEvent arg0) {
        mouseClickedMethode(arg0);
    }
    
    /**
     * Zeigt den Hinweis des Computergegners an.
     */
    public void hinweisZug() {
        if (!jetztIstComputerDran[0] && !jetztIstComputerDran[1]) {
            for (Feld feld : spielfeld.getHinweisZug()) {
                feld.setBackground(letzterZugFarbe);
            }
        }
    }
    /**
     * &Ouml;ffnet ein Eingabefenster und l&ouml;st bei ENTER-Druck die Analyse
     * aus.
     */
    public void textEingabe() {
        if (!jetztIstComputerDran[0] && !jetztIstComputerDran[1]) {
            final JDialog dialog = new JDialog();
            dialog.setPreferredSize(new Dimension(250, 65));
            dialog.setLocationRelativeTo(null);
            dialog.setTitle("Zug");
            dialog.setVisible(true);
            
            final JTextField textfeld = new JTextField();
            dialog.add(textfeld);
            dialog.pack();
            textfeld.addKeyListener(new KeyListener() {
                public void keyPressed(KeyEvent arg0) {
                    if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                        dialog.dispose();
                        textAnalysieren(textfeld.getText());
                    }
                }
                public void keyReleased(KeyEvent arg0) { 
                }
                public void keyTyped(KeyEvent arg0) {
                }
            });
        }
    }
    
    /**
     * Analysiert den &uuml;bergebenen Text mit einem Textanalysierer.
     * @param string Der zu analysierende Text
     * @see Textanalysierer
     */
    private void textAnalysieren(String string) {
        Textanalysierer textAna = new Textanalysierer(this, spielfeld);
        textAna.textAnalysieren(string);
    }
    /**
     * Immer wenn ein Spieldfeld angeklickt wird, wird diese Methode aufgerufen
     * und verarbeitet dann den Klick.<br>
     * Wenn noch keins ausgew&auml;hlt wurde oder die Auswahl gewechselt wurde,
     * wird ein neues ausgew&auml;hlt und - wenn aktiviert - die m&ouml;glichen 
     * Felder angezeigt.
     * @param arg0 MouseEvent, erzeugt von den Feldern des Spielfelds
     */
    private void mouseClickedMethode(MouseEvent arg0) {
        spielfeldAufbau();
        // Felder Bewegen
        Feld momentanesFeld = (Feld) arg0.getSource();
        /* (Wenn eine korrekte Figur ausgewaehlt wird und es noch keine 
         * ausgewaehlte Figur gibt.)
         * ODER
         * (Wenn man dann auf eine seiner eigenen Figuren Klickt, wechselt 
         * die GUI  auf die moeglichen Felder dieser Figur.)
         */
        // Faerbt die bedrohten Felder
        if (spielfeld.getEinstellungen().isBedrohteFigurenAnzeigen() 
            && !spielVorbei) {
            for (Feld bedroht : spielfeld.getBedrohteFelder()) {
                if (bedroht.getFigur().getWert() == 0) {
                    bedroht.setBackground(schachFarbe);
                } else {
                    bedroht.setBackground(bedrohteFelderFarbe);
                }
            }
        }
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
                momentanesFeld.setBackground(markierteFelder);
                if (spielfeld.getEinstellungen().isMoeglicheFelderAnzeigen()) {
                    for (Feld makieren : ausgewaehlteFigur
                        .getKorrekteFelder()) {
                        if (makieren.getFigur() == null) {
                            makieren.setBackground(markierteFelder);
                        } else {
                            makieren.setBackground(schlagendeFelder);
                        }
                    }
                }
            }
            /* Wenn der Spieler Schwarz dran ist und dies angeklickte Figur 
             * eine schwarze ist.
             */
            if (!spielfeld.getAktuellerSpieler() 
                && spielfeld.getSchwarzeFiguren()
                    .contains(ausgewaehlteFigur)) {
                momentanesFeld.setBackground(markierteFelder);
                if (spielfeld.getEinstellungen().isMoeglicheFelderAnzeigen()) {
                    for (Feld makieren : ausgewaehlteFigur
                        .getKorrekteFelder()) {
                        if (makieren.getFigur() == null) {
                            makieren.setBackground(markierteFelder);
                        } else {
                            makieren.setBackground(schlagendeFelder);
                        }
                    }
                }
            }
        // Wenn es bereits eine ausgewaehlte Figur gibt 
        } else if (ausgewaehlteFigur != null 
            && ausgewaehlteFigur.getKorrekteFelder().contains(momentanesFeld)) {
            /* und das neue ausgewaehlte Feld unter den moeglichen Feldern 
             dieser ist */
            if (ausgewaehlteFigur.getKorrekteFelder()
                .contains(momentanesFeld)) {
                // Hier zieht ein menschlicher Spieler
                spielerzugGUI(momentanesFeld);
                // Wenn das Spiel nicht vorbei ist 
                if (!spielVorbei && !spiel.getSpielname().contains("(edit)")) {
                    // autosave initiieren
                    parent.autoSave(spiel);
                } 
                // Je nach aktuellem Spieler wird das Label gesetzt
                momentanerSpielerUpdate();
                // Wenn es einen letzen Zug gibt wird dieser angezeigt
                if (spielfeld.getSpieldaten().getLetzterZug() != null) {
                    letzterZug.setText(spielfeld.getSpieldaten().getLetzterZug()
                        .toSchachNotation());
                }
                /* Wenn ein Computerspieler mitspielt muss er hier den  Zug
                 * machen
                 */
                jetztIstComputerDran[0] = true;
            }
        // Wenn man auf die selbe Figur / ein leeres Feld / Fremde Figur klickt
        } else {
            // Figur abwaehlen
            ausgewaehlteFigur = null;
            // Faerbt die bedrohten Felder
            if (spielfeld.getEinstellungen().isBedrohteFigurenAnzeigen() 
                && !spielVorbei) {
                for (Feld bedroht : spielfeld.getBedrohteFelder()) {
                    if (bedroht.getFigur().getWert() == 0) {
                        bedroht.setBackground(schachFarbe);
                    } else {
                        bedroht.setBackground(bedrohteFelderFarbe);
                    }
                }
            }
        }        
        revalidate();  
    }
    /**
     * Hier werden die Z&uuml;ge veranlasst und auf der Gui geupdated.
     * Die Zugzeit wird gestoppt und dem Zug werden diese sowie das Start-
     * und Zielfeld &uuml;bergeben.
     * @param momentanesFeld Das momentan ausgew&auml;hlte Feld
     */
    private void spielerzugGUI(Feld momentanesFeld) {
        // Hier ist der jetzige Zug beendet also auch die Zugzeit
        sekundenStopp = (System.currentTimeMillis()
            - sekundenStart);
        // Ein Zug wird ausgefuehrt und die Zugzeit uebergeben
        spielfeld.ziehe(ausgewaehlteFigur, momentanesFeld,
            (int) (sekundenStopp / 1000));
        zugZeitAktualisierung();
        // Neuer Spieler = keine Ausgewaehlte Figur
        ausgewaehlteFigur = null;
        // Bedrohte Felder muessen geladen werden
        spielfeld.getBedrohteFelder();
        // Der letzte Zug (gerade uebergeben)
        Zug letzterZug = spielfeld.getSpieldaten().getLetzterZug();
        // Wenn der letzte Zug ein Umwandlungszug war
        if (!spielVorbei && letzterZug instanceof Umwandlungszug)   {
            spielfeldAufbau();
            // Wird gefragt in welche Figur umgewandelt werden soll
            parent.soundAbspielen("Hinweis.wav");
            String[] moeglicheFiguren = {"Dame", "Turm", "Laeufer", 
                "Springer"};
            String s = (String) JOptionPane.showInputDialog(parent,
                "<html>W&auml;hlen Sie eine Figur aus, die Sie gegen den "
                + "Bauern tauschen wollen"
                , "Figurenwechsel", JOptionPane.
                PLAIN_MESSAGE, null, moeglicheFiguren, "Dame");
            int wert;
            // Je nach ausgelsesenem Wert (wenn "x" oder "cancel" - Dame)
            if (s == null || s.equals("Dame")) {
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
        spielfeldAufbau();
        revalidate();  
        mattOderSchach();
        // Start der neuen Zugzeit
        start();
    }
    

    /**
     * Pr&uuml;ft nach einem Zug, ob der neue aktuelle Spieler Matt ist oder im 
     * Schach steht. Wenn der aktuelle Spieler Matt gesetzt ist, dann wird die 
     * Spielauswertung ausgef&uuml;hrt und dem Benutzer wird ein JOptionPane
     * mit der Auswertung angezeigt. Hierbei wird zwischen Matt und Patt
     * unterschieden. Wenn der aktuelle Spieler im Schach steht dann wird die 
     * Methode {@link #schachWarnung()} aufgerufen.
     */
    private void mattOderSchach() {
        // Wenn das Spiel vorbei ist
        if (spielfeld.schachMatt()) {
            // Spiel ist vorbei
            spielVorbei = true;
            spielfeldAufbau();
            // das Spiel ausgewertet
            List<Object> auswertung = spiel.auswertung();
            Spieler gewinner = (Spieler) auswertung.get(0);
            String ergebnis; 
            String zuege = auswertung.get(2).toString();
            if ((boolean) auswertung.get(1)) {
                ergebnis = "<html>" + gewinner.getName() 
                    + " gewinnt nach " + zuege + " Z&uuml;gen.";
                // Den Koenig pink makieren
                if (spielfeld.getAktuellerSpieler()) {
                    spielfeld.getWeisseFiguren().get(0).getPosition()
                    .setBackground(koenigMatt);
                } else {
                    spielfeld.getSchwarzeFiguren().get(0).getPosition()
                    .setBackground(koenigMatt);
                }
            } else {
                ergebnis = "<html>Das Spiel endet in einem Patt";
            }
            // letzen Zug gruen makieren
            for (Feld feld : spielfeld.getLetzteFelder()) {
                feld.setBackground(letzterZugFarbe);
            }
            // beteiligte Figuren blau markieren
            for (Feld feld : spielfeld.amMattBeteiligteFelder()) {
                feld.setBackground(beteiligteFigurenFarbe);
            }
            revalidate(); 
            // Und Ein Dialogfenster fuer den Gewinner angezeigt
            parent.soundAbspielen("SchachMatt.wav");
            JOptionPane.showMessageDialog(parent, ergebnis 
                + "<br>" + zugZeitAnzeige());
            // Endscreen aufrufen
            remove(cEast);
            spielSpeichern();
            cEndeErstellen();
            revalidate(); 
            add(cEnde, BorderLayout.EAST); 
        // Wenn der momentane Spieler im Schach steht
        } else if (spielfeld.isSchach()) {
            schachWarnung();
        }
    }
    
    /**
     * Gibt eine Schachwarnung aus, wenn der aktuelle Spieler ein menschlicher 
     * Spieler ist. Computerspieler ben&ouml;tigen diese Warnung nicht, da sie
     * selbst&auml;ndig erkennen, wenn sie im Schach stehen.
     */
    private void schachWarnung() {
        // Wenn kein Computerspieler dran ist und das Spiel nocht vorbei ist
        if (!istComputerSpielerUndIstAmZug()
            && !spielVorbei && spielfeld.getEinstellungen().isSchachWarnung()) {
            spielfeldAufbau();
            parent.soundAbspielen("FehlerhafteEingabe.wav");
            // letzen Zug gruen makieren
            for (Feld feld : spielfeld.getLetzteFelder()) {
                feld.setBackground(letzterZugFarbe);
            }
            revalidate();
            // Schachmeldung ausgeben
            JOptionPane.showMessageDialog(parent, 
                "Sie stehen im Schach!", "Schachwarnung!",
                JOptionPane.WARNING_MESSAGE);
        }
        // Schach wieder aufheben, da nun ein Zug aus dem Schach gezwungen ist
        spielfeld.setSchach(false);
    }
   
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent, erzeugt von den Feldern des Spielfelds
     */
    public void mouseEntered(MouseEvent arg0) {
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent, erzeugt von den Feldern des Spielfelds
     */
    public void mouseExited(MouseEvent arg0) {
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent, erzeugt von den Feldern des Spielfelds
     */
    public void mousePressed(MouseEvent arg0) {
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param arg0 MouseEvent, erzeugt von den Feldern des Spielfelds
     */
    public void mouseReleased(MouseEvent arg0) {
    }
    
    /**
     * Action Performed f&uuml;r alle Buttons (R&uuml;ckg&auml;ngig, Remis, 
     * Aufgeben, Speichern, Wiederholung). <br>
     * Wenn der Zur&uuml;ck-Button gedr&uuml;ckt wurde, dann m&uuml;ssen bei 
     * einem Computergegner immer 2 Z&uuml;ge r&uuml;ckg&auml;ngig gemacht 
     * werden, sonst nur einer. Zudem wird die Zugzeit zur&uuml;ckgesetzt. 
     * Wenn der Remis-Button gedr&uuml;ckt wurde, wird die 
     * {@link #remisAuswertung()}-Methode aufgerufen. <br>
     * Wenn der Aufgeben-Button gedr&uuml;ckt wurde, wird das Spiel beendet,
     * ausgewertet und eine Nachricht f&uuml;r den Gewinner wird angezeigt.
     * Wenn der Speichern-Button gedr&uuml;ckt wurde dann wird das Spiel 
     * gespeichert und ein JOptionPane zeigt die erfolgreiche Speicherung an.
     * Wenn der Wiederholungs-Button gedr&uuml;ckt wurde, dann wird eine
     * Wiederholung des letzten Spiels gestartet.
     * @param e Ausgel&ouml;stes ActionEvent
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
            // Je nach aktuellem Spieler wird das Label gesetzt
            momentanerSpielerUpdate();
            // Wenn es einen letzten zug gibt wird dieser angezeigt
            if (spielfeld.getSpieldaten().getLetzterZug() != null) {
                letzterZug.setText(spielfeld.getSpieldaten().getLetzterZug()
                    .toSchachNotation());
            }
            // Zugzeit neu starten
            start();
            spielfeldAufbau();
            revalidate();  
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
            JOptionPane.showMessageDialog(parent, "<html>" 
                + verlierer.getName() + " gibt nach " + zuege 
                + " Z&uuml;gen auf! " + gewinner.getName() + " gewinnt!!!"
                + "<br>" + zugZeitAnzeige());
            // Endscreen aufrufen
            remove(cEast);
            spielSpeichern();
            cEndeErstellen();
            add(cEnde, BorderLayout.EAST);
            revalidate();  
        }
        // Wenn das Spiel gespeichert werden soll
        if (e.getActionCommand().equals(commandSpeichern)) {
            parent.spielSpeichern(spiel);
            parent.autoSaveLoeschen();
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
                spielfeld.ziehe(zug.getStartfeld().getFigur(), 
                    zug.getZielfeld(), zug.getZugzeit());
                zugListe.setSelectedIndex(zaehler);
                zugListe.ensureIndexIsVisible(zaehler);
                zugListe.revalidate();
                // Wenn es ein Umwandlungszug war
                if (zug instanceof Umwandlungszug) {
                    // Muss nachtraeglich noch die figur umgewandelt werden
                    spielfeld.umwandeln(spielfeld.getSpieldaten()
                        .getLetzterZug().getFigur(), 
                        ((Umwandlungszug) zug).getNeueFigur().getWert());
                }
                spielfeldAufbau();
                // Start und Ziel feld werden a gruen makiert
                for (Feld feld : spielfeld.getLetzteFelder()) {
                    feld.setBackground(letzterZugFarbe);
                }
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
            // Titel wieder umsetzen
            parent.setTitle("Schachspiel");
            // Und auf die Eroeffnungseite gewechselt werden
            parent.seitenAuswahl("Eroeffnungsseite");
        }
    }
    
    /**
     * Wenn der Remis-Button gedr&uuml;ckt wird, wird hier die Auswertung der 
     * Remis-Anfrage bearbeitet.
     */
    private void remisAuswertung() {
        // Testen ob die 50-Zuege-Regel verletzt wurde
        if (spielfeld.getSpieldaten().fuenfzigZuegeRegel()) {
            // Spiel ist vorbei
            spielVorbei = true;
            // Unentschieden einreichen
            spiel.unentschieden();
            parent.soundAbspielen("Hinweis.wav");
            JOptionPane.showMessageDialog(parent, "<html>50 Z&uuml;ge Regel "
                + "wurde erf&uuml;llt. Das Spiel endet in einem Unentschieden");
            // Endescreen wird aufgebaut
            remove(cEast);
            spielSpeichern();
            cEndeErstellen();
            add(cEnde, BorderLayout.EAST);
            revalidate();
        // Wenn Spieler 2 kein Computerspieler ist und keine 50-Zuge-Regel
        } else {
            if (!(spieler2 instanceof Computerspieler)) {
                parent.soundAbspielen("Hinweis.wav");
                // Wird gefragt ob ein Unentschieden angenommen wird
                int eingabe = JOptionPane.showConfirmDialog(parent, 
                    "<html>M&ouml;chten Sie sich auf ein Unentschieden "
                    + "einigen?");
                // Wenn "Ja" ausgewaehlt wurde
                if (eingabe == 0) {
                    // ist das Spiel vorbei
                    spielVorbei = true;
                    // Unentschieden einreichen
                    spiel.unentschieden();
                    remove(cEast);
                    spielSpeichern();
                    cEndeErstellen();
                    add(cEnde, BorderLayout.EAST);
                    revalidate();
                // Wenn "Nein" ausgewaehlt wurde    
                } else {
                    Spieler spieler;
                    // Wenn Spieler1 das Unentschieden angeboten hat
                    if (spielfeld.getAktuellerSpieler() == spieler1
                        .getFarbe()) {
                        // lehnt Spieler 2 somit ab
                        spieler = spieler2;
                    // sonst hat Spieler2 das Unentschieden angeboten
                    } else {
                        // sonst lehnt Spieler 1 ab
                        spieler = spieler1;
                    }
                    parent.soundAbspielen("Aufgeben.wav");
                    JOptionPane.showMessageDialog(parent, 
                        spieler.getName() + " hat das Remis abgelehnt");
                }
            // Wenn Spieler2 ein Computerspieler ist 
            } else {
                // Wenn ein Unentschieden aktzeptabel ist
                if ((boolean) ((Computerspieler) spieler2)
                    .unentschiedenAnnehmen()) {
                    // Nimmt der Computerspieler das Unentschieden an
                    parent.soundAbspielen("Hinweis.wav");
                    JOptionPane.showMessageDialog(parent, "Spiel endet "
                        + "unentschieden.");
                    spielVorbei = true;
                    spiel.unentschieden();
                    // Endscreen aufrufen
                    remove(cEast);
                    spielSpeichern();
                    cEndeErstellen();
                    add(cEnde, BorderLayout.EAST);
                    revalidate();
                // Wenn es nicht aktzeptabel ist 
                } else {
                    // lehnt der Computerspieler ab
                    parent.soundAbspielen("Aufgeben.wav");
                    JOptionPane.showMessageDialog(parent, 
                        spieler2.getName() + " hat das Remis abgelehnt");
                }
            }
        }
    }
    
    /**
     * Startet eine neue Zeitnahmesession f&uuml;r die Zugzeit der Spieler.
     */
    private void start() {
        uhrAktiv = true;
        // Neue Zeit anfangen
        sekundenStart = System.currentTimeMillis();
    }
    
    /**
     * Runnable Methode zum Erstellen und &Uuml;berwachen der Zugzeit und zum
     * Ausf&uuml;hren der Wiederholungsfunktion.
     * Solange der Thread aktiv ist (wird noch von einer Funktion gebraucht)
     * wird alle 25ms die Uhrzeit auf dem daf&uuml;r passendem Label 
     * aktualisiert und gepr&uuml;ft ob die maximale Zugzeit &uuml;berschritten
     * worden ist. 
     * Wenn der Wiederholungs-Boolean true ist dann wird alle 2.5s der 
     * Wiederholungs-Button geklickt um so ein Spielvideo zu erzeugen.   
     */
    public void run() {
        StringBuffer ausgabe;
        parent.requestFocus();
        while (uhrAktiv) {
            if (jetztIstComputerDran[0]) {
                jetztIstComputerDran[0] = false;
                wennComputerDannZiehen();
            }
            zugzeit.setForeground(Color.BLACK);
            ausgabe = new StringBuffer();
            try {
                Thread.sleep(25);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            progBar.revalidate();
            // Vergangene Zeit in Millisekuden
            sekundenStopp = System.currentTimeMillis()
                    - sekundenStart;
            int begrenzung = spielfeld.getEinstellungen()
                .getZugZeitBegrenzung();
            int zugzeitBisher;
            boolean aktuellerSpieler;
            if (jetztIstComputerDran[1]) {
                aktuellerSpieler = spieler2.getFarbe();
            } else {
                aktuellerSpieler = spielfeld.getAktuellerSpieler();
            }
            if (aktuellerSpieler) {
                zugzeitBisher = zugZeitWeiss;
            } else {
                zugzeitBisher = zugZeitSchwarz;
            }
            int gesamtZugzeit = zugzeitBisher 
                + Integer.parseInt(sekundenStopp / 1000 + "");
            // Formatierungshilfe fuer Zeiten
            Calendar dauer;
            dauer = Calendar.getInstance();
            // Zeit in Millisekunden uebergeben
            dauer.setTimeInMillis(zugzeitBisher * 1000 + sekundenStopp);
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
                // Wenn die Zugzeit > Maximale zugzeit --> Aufgeben Button
                if (begrenzung > 0 && gesamtZugzeit >= begrenzung) {
                    // Spiel ist vorbei
                    spielVorbei = true;
                    spielfeldAufbau();
                    // das Spiel ausgewertet
                    List<Object> auswertung = spiel.auswertung();
                    Spieler gewinner = (Spieler) auswertung.get(0);
                    String ergebnis; 
                    String zuege = auswertung.get(2).toString();
                    ergebnis = "<html>Bedenkzeit &uuml;berschritten. " 
                        + gewinner.getName() + " gewinnt nach " + zuege 
                        + " Z&uuml;gen.";
                    // Und Ein Dialogfenster fuer den Gewinner angezeigt
                    parent.soundAbspielen("SchachMatt.wav");
                    JOptionPane.showMessageDialog(parent, ergebnis);
                    // Endscreen aufrufen
                    remove(cEast);
                    cEndeErstellen();
                    add(cEnde, BorderLayout.EAST);
                    revalidate();  
                }
            }
            // Wenn ein Spielvideo angezeigt werden soll
            if (btnWiederholung.isEnabled()) {
                /* Wenn wiederholt werden soll und nicht stopp ist und 2.5 oder 
                 * mehr Sekunden vergangen sind 
                */
                if (wiederholung && wiederholen && sekundenStopp >= 2500) {
                    // Dann soll der naechste zug ausgefuert werden
                    btnWiederholung.doClick();
                }
            // Wenn die Wiederholung vorbei ist    
            } else {
                // Thread beenden
                uhrAktiv = false;
            }
            
        }
    }
    
    /**
     * Aktualisiert die Zugzeit.
     */
    private void zugZeitAktualisierung() {
        zugZeitWeiss = spielfeld.getSpieldaten().getZugzeit(true);
        zugZeitSchwarz = spielfeld.getSpieldaten().getZugzeit(false);
    }
    
    /**
     * Gibt einen mehrzeiligen String zur Ausgabe der Zugzeit und der Zuganzahl
     * der beiden Spieler bei Spielende zur&uuml;ck.
     * @return Mehrzeiliger String
     */
    private String zugZeitAnzeige() {
        String string = "";
        string += spieler1.getName() + ": ";
        boolean farbe = spieler1.getFarbe();
        string += spielfeld.getSpieldaten().getAnzahlZuege(farbe) + " ";
        string += "Z&uuml;ge in " + spielfeld.getSpieldaten().getZugzeit(farbe);
        string += " Sekunden <br>";
        string += spieler2.getName() + ": ";
        farbe = spieler2.getFarbe();
        string += spielfeld.getSpieldaten().getAnzahlZuege(farbe) + " ";
        string += "Z&uuml;ge in " + spielfeld.getSpieldaten().getZugzeit(farbe);
        string += " Sekunden <br>";
        
        return string;
    }
    
    /**
     * &Ouml;ffnet ein Dialog-Fenster, ob das Spiel als beendetes Spiel
     * gespeichert werden soll.
     */
    private void spielSpeichern() {
        if (!spiel.getSpielname().contains("(edit)")) {
            parent.soundAbspielen("Hinweis.wav");
            int auswahl = JOptionPane.showConfirmDialog(this,
                "Wollen Sie das Spiel speichern?", 
                "Spiel speichern", JOptionPane.YES_NO_OPTION);
            if (auswahl == 0) {
                parent.endSpielSpeichern(spiel);
            }
        }
    }
    
    /**
     * Unbenutzte Component Listener Methode.
     * @param e durch Component ausgel&ouml;stes Event
     */
    public void componentHidden(ComponentEvent e) {
    }
    
    /**
     * Unbenutzte Component Listener Methode.
     * @param e durch Component ausgel&ouml;stes Event
     */
    public void componentMoved(ComponentEvent e) {
    }
    
    /**
     * Sorgt daf&uuml;r, dass nach einem Resize die Spielfiguren in der 
     * richtigen Gr&ouml;&szlig;e angezeigt werden.
     * @param e durch resizen ausgel&ouml;stes Event
     */
    public void componentResized(ComponentEvent e) {
        spielfeldUIUpdate();
    }
    
    /**
     * Unbenutzte Component Listener Methode.
     * @param e durch resizen ausgel&ouml;stes Event
     */
    public void componentShown(ComponentEvent e) {
    }

    /**
     * Getter f&uuml;r die SpielGUI.
     * @return SpielGUI
     */
    public SpielGUI getSpielGUI() {
        return parent;
    }
}
