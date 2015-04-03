package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import zuege.Zug;
import daten.Computerspieler;
import daten.Spiel;
import daten.Spieldaten;
import daten.Spieler;
import daten.Spielfeld;
import figuren.Bauer;
import figuren.Dame;
import figuren.Figur;
import figuren.Koenig;
import figuren.Laeufer;
import figuren.Springer;
import figuren.Turm;

/**
 * Klasse zum erstellen von Spielsituationen.
 * @author Marvin Wolf (GUI)
 * @author Christian Ackermann (Algorithmik)
 */
public class Editor extends JPanel implements MouseListener, ActionListener {
    
    /**
     * Serial Key zur sp&auml;teren Identifizierung.
     */
    private static final long serialVersionUID = -99864898290588657L;
    
    /**
     * ElternGUI.
     */
    private SpielGUI parent;
    
    /**
     * Konstante f&uuml;r den Farbton der "schwarzen" Felder.
     */
    private Color spielfeldSchwarz;
    
    /**
     * Konstante f&uuml;r den Farbton der "wei&szlig;en" Felder.
     */
    private Color spielfeldWeiss;
    
    /**
     * Konstante f&uuml;r den Farbton des Hintergrundes.
     */
    private Color hintergrund;
    
    /**
     * Konstante f&uuml;r den Farbton der Buttons.
     */
    private Color buttonFarbe;
    
    /**
     * Panel f&uuml;r das Spielfeld.
     */
    private JPanel cCenter = new JPanel();
    
    /**
     * Panel f&uuml;r die Auswahlelemete.
     */
    private JPanel cEast = new JPanel();
    
    /**
     * FelderListe.
     */
    private List<Feld> felderListe;
    
    /**
     * Spielfeld.
     */
    private Spielfeld spielfeld;
    
    /**
     * spieler1.
     */
    private Spieler spieler1;
    
    /**
     * spieler2.
     */
    private Spieler spieler2;
    
    /**
     * spiel.
     */
    private Spiel spiel;
    
    /**
     * Buttongrp Figuren.
     */
    private ButtonGroup figuren = new ButtonGroup();
    
    /**
     * Buttongrp Aktueller Spieler.
     */
    private ButtonGroup aktuellerSpieler = new ButtonGroup();
    /**
     * Button um das Spiel zu starten.
     */
    private JButton spielStarten;
    
    /**
     * Button um die Stellung zu speichern.
     */
    private JButton speichern;
    
    /**
     * Konstruktor der Klasse Editor.
     * @param parent ElternGUI
     * @param spielname spielname
     * @param spieler1 spieler1
     * @param spieler2 spieler2
     */
    public Editor(SpielGUI parent, String spielname,
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

        // Loescht die Startaufstellung
        spielfeld.getWeisseFiguren().clear();
        spielfeld.getSchwarzeFiguren().clear();
        
        // Setzt alle Felder zurueck
        for (Feld feld : felderListe) {
            feld.setFigur(null);
        }
        
        // Initialisiert die Farben
        hintergrund = parent.getFarben()[0];
        buttonFarbe = parent.getFarben()[1];
        spielfeldWeiss = parent.getFarben()[2];
        spielfeldSchwarz = parent.getFarben()[3];
        
        init();
        
        setVisible(true);
        
        
    }
    
    /**
     * init-Methode.
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
        parent.setTitle("Editor: " + spieler1.getName() + " vs. " 
            + spieler2.getName());
        
        // cCenter
        setLayout(new BorderLayout());
        cCenter.setBackground(new Color(0, 0, 0));
        cCenter.setLayout(new GridLayout(8, 8, 1, 1));
        
        // cEast
        cEast.setBackground(hintergrund);
        GridBagConstraints gbc = new GridBagConstraints();
        cEast.setLayout(new GridBagLayout());
        
        gbc.insets = new Insets(8, 10, 8, 10);
        
        radioButtonInit(gbc, cEast);
        
        gbc.insets = new Insets(30, 10, 10, 10);
        
        JRadioButton farbe;
        farbe = new JRadioButton("<html> Wei&szlig", true);
        aktuellerSpieler.add(farbe);
        gbc.gridx = 0;
        gbc.gridy = 6;
        farbe.setActionCommand("weiss");
        farbe.setBackground(hintergrund);
        farbe.setForeground(Color.WHITE);
        cEast.add(farbe, gbc);
        
        farbe = new JRadioButton("Schwarz");
        aktuellerSpieler.add(farbe);
        gbc.gridx = 1;
        gbc.gridy = 6;
        farbe.setActionCommand("schwarz");
        farbe.setBackground(hintergrund);
        farbe.setForeground(Color.BLACK);
        cEast.add(farbe, gbc);
        
        gbc.insets = new Insets(10, 10, 10, 10);
        
        speichern = new JButton("Speichern");
        speichern.addActionListener(this);
        speichern.setActionCommand("Speichern");
        speichern.setBackground(buttonFarbe);
        gbc.gridx = 0;
        gbc.gridy = 7;
        cEast.add(speichern, gbc);
        spielStarten = new JButton("Spiel starten");
        spielStarten.addActionListener(this);
        spielStarten.setActionCommand("Starten");
        spielStarten.setBackground(buttonFarbe);
        gbc.gridx = 1;
        gbc.gridy = 7;
        cEast.add(spielStarten, gbc);
        
        this.setLayout(new BorderLayout());
        this.add(cCenter, BorderLayout.CENTER);
        this.add(cEast, BorderLayout.EAST);
        spielfeldAufbau();
        
        
    }
    
    /**
     * Initialisiert die RadioButtons.
     * @param gbc GridBagConstraints
     * @param cEast Das entsprechende Panel
     */
    private void radioButtonInit(GridBagConstraints gbc, JPanel cEast) {

        JRadioButton figur;
        
        figur = new JRadioButton("Bauer", true);
        figuren.add(figur);
        figur.setActionCommand("WBauer");
        cEast.add(figur, gbc);
        
        figur = new JRadioButton("Springer");
        figuren.add(figur);
        gbc.gridy = 1;
        figur.setActionCommand("WSpringer");
        cEast.add(figur, gbc);
        
        figur = new JRadioButton("Läufer");
        figuren.add(figur);
        gbc.gridy = 2;
        figur.setActionCommand("WLaeufer");
        cEast.add(figur, gbc);
        
        figur = new JRadioButton("Turm");
        figuren.add(figur);
        gbc.gridy = 3;
        figur.setActionCommand("WTurm");
        cEast.add(figur, gbc);
        
        figur = new JRadioButton("Dame");
        figuren.add(figur);
        gbc.gridy = 4;
        figur.setActionCommand("WDame");
        cEast.add(figur, gbc);
        
        figur = new JRadioButton("König");
        figuren.add(figur);
        gbc.gridy = 5;
        figur.setActionCommand("WKoenig");
        cEast.add(figur, gbc);
        
        
        figur = new JRadioButton("Bauer", true);
        figuren.add(figur);
        gbc.gridx = 1;
        gbc.gridy = 0;
        figur.setActionCommand("Bauer");
        cEast.add(figur, gbc);
        
        figur = new JRadioButton("Springer");
        figuren.add(figur);
        gbc.gridx = 1;
        gbc.gridy = 1;
        figur.setActionCommand("Springer");
        cEast.add(figur, gbc);
        
        figur = new JRadioButton("Läufer");
        figuren.add(figur);
        gbc.gridx = 1;
        gbc.gridy = 2;
        figur.setActionCommand("Laeufer");
        cEast.add(figur, gbc);
        
        figur = new JRadioButton("Turm");
        figuren.add(figur);
        gbc.gridx = 1;
        gbc.gridy = 3;
        figur.setActionCommand("Turm");
        cEast.add(figur, gbc);
        
        figur = new JRadioButton("Dame");
        figuren.add(figur);
        gbc.gridx = 1;
        gbc.gridy = 4;
        figur.setActionCommand("Dame");
        cEast.add(figur, gbc);
        
        figur = new JRadioButton("König");
        figuren.add(figur);
        gbc.gridx = 1;
        gbc.gridy = 5;
        figur.setActionCommand("Koenig");
        cEast.add(figur, gbc);
        
        for (int i = 0; i <= 5; i++) {
            cEast.getComponent(i).setForeground(Color.WHITE);
            cEast.getComponent(i).setBackground(hintergrund);
        }
        for (int i = 6; i <= 11; i++) {
            cEast.getComponent(i).setForeground(Color.BLACK);
            cEast.getComponent(i).setBackground(hintergrund);
        }
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
     * Methode zum Aufbauen des Spielfelds.
     */
    private void spielfeldAufbau() {        
        // boolean fuer abwechselnd schwarz/spielfeldWeiss
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
                // Wenn die Farbe "schwarz" ist dann Feld schwarz machen
                if (!abwechslung) {
                    temp.setBackground(spielfeldSchwarz);
                    abwechslung = true;
                // Wenn die Farbe "weiss" ist dann Feld weiss machen    
                } else {
                    temp.setBackground(spielfeldWeiss);
                    abwechslung = false;
                }
                // Dem cCenter Panel das fertige Feld hinzufuegen
                cCenter.add(temp);
            }    
            counter -= 8;
        }
        spielfeldUIUpdate();
    }
    
    /**
     * Setzte die Figurenbilder.
     */
    private void spielfeldUIUpdate() {
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
        // - spielfeldWeisse Figurenbilder
        for (Figur spielfeldWeiss  : spielfeld.clone(
            spielfeld.getWeisseFiguren())) {
            // Feld der Figur abspeichern
            Feld momentan = spielfeldWeiss.getPosition();
            // Image in der Mitte zentrieren lassen
            momentan.setVerticalAlignment(SwingConstants.CENTER);
            momentan.setHorizontalAlignment(SwingConstants.CENTER);
            // Die Groesse an die Fenstergroesse angepasst
            int width = parent.getWidth() / 10;
            int height = parent.getHeight() / 9;
            // Das Bild aus dem Dateipfad geladen
            Image imageW = getImage(spielfeldWeiss);
            // rescaled
            ImageIcon iconW  = new ImageIcon(imageW
                .getScaledInstance(width, height, Image.SCALE_SMOOTH));
            // Als Icon des Feldes setzen
            momentan.setIcon(iconW);
        }
        revalidate();  
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
     * Unbenutzte MouseEventMethode.
     * @param e MouseEvent, erzeugt von den Feldern des Spielfelds
     */
    public void mouseClicked(MouseEvent e) {
        // Variablen Deklaration
        Feld feld = (Feld) e.getSource();
        Figur figur;
        List<Figur> figurenListe;
        List<Figur> andereListe;
        boolean farbe;
        // Wenn der ActionCommand mit einem W beginnt
        if (figuren.getSelection().getActionCommand().startsWith("W")) {
            // Ist es eine weisse Figur
            farbe = true;
            figuren.getSelection().setActionCommand(
                figuren.getSelection().getActionCommand().substring(1));
            figurenListe = spielfeld.getWeisseFiguren();
            andereListe = spielfeld.getSchwarzeFiguren();
        } else {
            farbe = false;
            figurenListe = spielfeld.getSchwarzeFiguren();
            andereListe = spielfeld.getWeisseFiguren();
        }
        // Die einzelnen Figuren
        if (figuren.getSelection().getActionCommand().equals("Bauer")) {
            figur = new Bauer(feld, farbe);
        } else if (figuren.getSelection().getActionCommand()
            .equals("Turm")) {
            figur = new Turm(feld, farbe);
        } else if (figuren.getSelection().getActionCommand()
            .equals("Springer")) {
            figur = new Springer(feld, farbe);
        } else if (figuren.getSelection().getActionCommand()
            .equals("Laeufer")) {
            figur = new Laeufer(feld, farbe);
        } else if (figuren.getSelection().getActionCommand()
            .equals("Dame")) {
            figur = new Dame(feld, farbe);
        } else {
            figur = new Koenig(feld, farbe);
        }
        
        // Bei weiss wieder ein W an den ActionCommand anhaengen
        if (farbe) {
            figuren.getSelection().setActionCommand(
                "W" + figuren.getSelection().getActionCommand());
        }
        
        figur.setSpielfeld(spielfeld);
        
        // Wenn es ein Koenig ist
        if (figur.getWert() == 0) {
            if ((figur.getFarbe() && feld.getIndex() != 4)
                || (!figur.getFarbe() && feld.getIndex() != 60)) {
                figur.setGezogen(true);
            }
        } else if (figur.getWert() == 100) {
            if (figur.getFarbe() && feld.getYK() != 1
                || !figur.getFarbe() && feld.getYK() != 6) {
                figur.setGezogen(true);
            }
        } else if (figur.getWert() == 465) {
            if ((figur.getFarbe() 
                && (feld.getIndex() != 0 && feld.getIndex() != 7))
                || (!figur.getFarbe() 
                    && (feld.getIndex() != 56 && feld.getIndex() != 63))) {
                System.out.println(feld.getIndex());
                figur.setGezogen(true);
            }
        }
        
        // Wenn auf dem Feld bereits eine Figur steht
        if (feld.getFigur() != null) {
            // Wenn es die gleiche Figur is
            if (feld.getFigur().getWert() == figur.getWert()
                && feld.getFigur().getFarbe() == figur.getFarbe()) {
                // Wird sie entfernt
                figurenListe.remove(feld.getFigur());
                andereListe.remove(feld.getFigur());
                feld.setFigur(null);
            } else {
                // Wenn es ein Koenig ist
                if (figurenListe.size() > 0 
                    && figurenListe.get(0).getWert() == 0 
                    && figur.getWert() == 0) {
                    figurenListe.get(0).getPosition().setFigur(null);
                    figurenListe.remove(0);
                }
                figurenListe.remove(feld.getFigur());
                andereListe.remove(feld.getFigur());
                feld.setFigur(figur);
                figurenListe.add(figur);
            }
        } else {
            if (figurenListe.size() > 0 && figurenListe.get(0).getWert() == 0 
                && figur.getWert() == 0) {
                figurenListe.get(0).getPosition().setFigur(null);
                figurenListe.remove(0);
            }
            feld.setFigur(figur);
            figurenListe.add(figur);
        }
        
        if (figur.getWert() == 100 
            && (feld.getYK() == 0 || feld.getYK() == 7)) {
            figurenListe.remove(figur);
            feld.setFigur(null);
        }
        
        spielfeldAufbau();
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param e MouseEvent, erzeugt von den Feldern des Spielfelds
     */
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param e MouseEvent, erzeugt von den Feldern des Spielfelds
     */
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
        
    /**
     * Unbenutzte MouseEventMethode.
     * @param e MouseEvent, erzeugt von den Feldern des Spielfelds
     */
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Unbenutzte MouseEventMethode.
     * @param e MouseEvent, erzeugt von den Feldern des Spielfelds
     */
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Action-Performed-Methode.
     * @param arg0 Ausgel&ouml;stes ActionEvent
     */
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals("Speichern")) {
            
        } else {
            spielfeld.getSpieldaten().getZugListe().add(new Zug());
            if (spielfeld.getWeisseFiguren().get(0).getWert() != 0) {
                parent.soundAbspielen("Hinweis.wav");
                JOptionPane.showMessageDialog(parent, "<html> Es fehlt ein "
                    + "wei&szlig;er K&ouml;nig.");
            } else if (spielfeld.getSchwarzeFiguren().get(0).getWert() != 0) {
                parent.soundAbspielen("Hinweis.wav");
                JOptionPane.showMessageDialog(parent, "<html> Es fehlt ein "
                    + "schwarzer K&ouml;nig.");
            } else {
                for (Feld feld : felderListe) {
                    feld.removeMouseListener(this);
                }
                if (aktuellerSpieler.getSelection().getActionCommand()
                    .equals("schwarz")) {
                    spielfeld.setAktuellerSpieler(false);
                }
                parent.setContentPane(new SpielfeldGUI(parent, spiel));
            }
        }
    
    }
}