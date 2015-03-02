package gui;

import javax.imageio.ImageIO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

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
public class SpielfeldGUI extends JPanel implements MouseListener {
    
    /**
     * Serial Key zur Identifizierung.
     */
    private static final long serialVersionUID = -5131381120236108231L;
    
    /**
     * Das ElterGUI-Objekt von dem aus das jeweilige Spielfeld aufgerufen wurde.
     */
    private SpielGUI parent;
    
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
     * Kontainer für die Anzeigen und Button neben dem Spielfeld.
     */
    private Container cEast;
    
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
        Dimension size = new Dimension(800, 800);
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
        
        // SpielfeldGUI erstellen
        this.setLayout(new BorderLayout());
        cCenter.setBackground(new Color(0, 0, 0));
        cCenter.setLayout(new GridLayout(8, 8, 1, 1));
        spielfeldAufbau();
        
        // EAST
        this.add(cCenter, BorderLayout.CENTER);
        
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
     // - schwarze Figurenbilder
        for (Figur schwarz  : spielfeld.getSchwarzeFiguren()) {
            Feld momentan = schwarz.getPosition();
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
     * MouseEvent-Methode mouseClicked.
     * @param arg0 MouseEvent erzeugt von den Feldern des Spielfelds
     */
    public void mouseClicked(MouseEvent arg0) {
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
                ausgewaehlteFigur.getPosition().setIcon(null);
                spielfeld.ziehe(ausgewaehlteFigur, momentanesFeld);
                ausgewaehlteFigur = null;
                
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
    
}
