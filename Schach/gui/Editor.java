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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import daten.Computerspieler;
import daten.Spiel;
import daten.Spieldaten;
import daten.Spieler;
import daten.Spielfeld;
import figuren.Figur;

/**
 * Klasse zum erstellen von Spielsituationen.
 */
public class Editor extends JPanel implements MouseListener {
    
    /**
     * Serial Key zur sp&auml;teren Identifizierung.
     */
    private static final long serialVersionUID = -99864898290588657L;
    
    /**
     * ElternGUI.
     */
    private SpielGUI parent;
    
    /**
     * Konstante f&uuml;r den Farbton der "schwarzen" Felder (braun).
     */
    private final Color braun = new Color(181, 81, 16);
    
    /**
     * Konstante f&uuml;r den Farbton der "wei&szlig;en" Felder (helles Beige).
     */
    private final Color weiss = new Color(255, 248, 151);
    
    /**
     * Konstante f&uuml;r den Farbton des Hintergrundes (Braun).
     */
    private final Color cBraunRot = new Color(164, 43, 24); 
    
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
     * Buttongrp farbe.
     */
    private ButtonGroup farben = new ButtonGroup();
    
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
        cEast.setBackground(cBraunRot);
        GridBagConstraints gbc = new GridBagConstraints();
        cEast.setLayout(new GridBagLayout());
        
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JRadioButton figur;
        
        figur = new JRadioButton("Bauer", true);
        figuren.add(figur);
        figur.setBackground(cBraunRot);
        cEast.add(figur, gbc);
        figur = new JRadioButton("Turm");
        figuren.add(figur);
        gbc.gridy = 1;
        figur.setBackground(cBraunRot);
        cEast.add(figur, gbc);
        figur = new JRadioButton("Springer");
        figuren.add(figur);
        gbc.gridy = 2;
        figur.setBackground(cBraunRot);
        cEast.add(figur, gbc);
        figur = new JRadioButton("Läufer");
        figuren.add(figur);
        gbc.gridy = 0;
        gbc.gridx = 1;
        figur.setBackground(cBraunRot);
        cEast.add(figur, gbc);
        figur = new JRadioButton("Dame");
        figuren.add(figur);
        gbc.gridx = 1;
        gbc.gridy = 1;
        figur.setBackground(cBraunRot);
        cEast.add(figur, gbc);
        figur = new JRadioButton("König");
        figuren.add(figur);
        gbc.gridx = 1;
        gbc.gridy = 2;
        figur.setBackground(cBraunRot);
        cEast.add(figur, gbc);
        
        gbc.insets = new Insets(30, 10, 30, 10);
        
        JRadioButton farbe;
        farbe = new JRadioButton("Weiss", true);
        farbe.setBackground(cBraunRot);
        farbe.setForeground(Color.WHITE);
        farbe.setActionCommand("Weiss");
        farben.add(farbe);
        gbc.gridx = 0;
        gbc.gridy = 3;
        cEast.add(farbe, gbc);
        
        farbe = new JRadioButton("Schwarz");
        farbe.setBackground(cBraunRot);
        farbe.setActionCommand("Schwarz");
        farben.add(farbe);
        gbc.gridx = 1;
        gbc.gridy = 3;
        cEast.add(farbe, gbc);
        
        
        this.setLayout(new BorderLayout());
        this.add(cCenter, BorderLayout.CENTER);
        this.add(cEast, BorderLayout.EAST);
        spielfeldAufbau();
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
        if (farben.getSelection().getActionCommand().equals("Weiss")) {
            if (figuren.getSelection().getActionCommand().equals("Bauern")) {
                // TODO
            } else if (figuren.getSelection().getActionCommand()
                .equals("Turm")) {
                // TODO
            } else if (figuren.getSelection().getActionCommand()
                .equals("Springer")) {
                // TODO
            } else if (figuren.getSelection().getActionCommand()
                .equals("Läufer")) {
                // TODO
            } else if (figuren.getSelection().getActionCommand()
                .equals("Dame")) {
                // TODO
            } else {
                // TODO
            }
        } else {
            if (figuren.getSelection().getActionCommand().equals("Bauern")) {
                // TODO
            } else if (figuren.getSelection().getActionCommand()
                .equals("Turm")) {
                // TODO
            } else if (figuren.getSelection().getActionCommand()
                .equals("Springer")) {
                // TODO
            } else if (figuren.getSelection().getActionCommand()
                .equals("Läufer")) {
                // TODO
            } else if (figuren.getSelection().getActionCommand()
                .equals("Dame")) {
                // TODO
            } else {
                // TODO
            }
        }
        
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
}