package gui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

import daten.Gesamtdatensatz;

/**
 * Das eigentliche Fenster des Schachspiels. Auf diesem Fenster werden die Panes
 * gewechselt. Erbt von JFrame
 * @author Marvin Wolf
 */
public class SpielGUI extends JFrame {
    
    // Anfang Attribute
    
    /**
     * Serial-Key zur Identifizierung.
     */
    private static final long serialVersionUID = -8895681303810255159L;
    
    /**
     * Gesamtdatensatz, welcher zu Beginn geladen/erstellt und beim beenden 
     * gespeichert werden muss.
     */
    private Gesamtdatensatz gesamtdatensatz;
    
    // Ende Attribute

    // Konstruktor
    
    /**
     * Erstellt ein neues Spiel-Fenster.
     * Ruft dabei init() auf.
     */
    public SpielGUI() {
        super("Schachspiel");
        seitenAuswahl("Eroeffnungsseite");
        //pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    // Anfang Methoden
    
    /**
     * Dient zum wechsel der Contentpane, welches dur den SeitenwechselnListener
     * aufgeruden wird.
     * @param auswahl Auf die zu wechselnde Seite.
     */
    public void seitenAuswahl(String auswahl) {
        Container seite;
        switch (auswahl) {
        case "Eroeffnungsseite":
            seite = new Eroeffnungsseite(this);
            this.setContentPane(seite);
            Dimension minimaleGroesse = new Dimension(500, 500);
            setMinimumSize(minimaleGroesse);
            setSize(minimaleGroesse);
            setLocationRelativeTo(null);
            break;
            
        case "Spielerauswahl":
            seite = new Spielerauswahl(this);
            this.setContentPane(seite);
            break;
            
        case "Spiel laden":
            seite = new SpielLaden(this);
            this.setContentPane(seite);
            break;
            
        case "Einstellungen":
            seite = new Einstellungen(this);
            this.setContentPane(seite);
            break;
            
        case "Highscore":
            seite = new Highscore(this);
            this.setContentPane(seite);
            
        case "Statistiken":
            seite = new Statistiken(this);
            this.setContentPane(seite);
            break;
            
        case "Regelwerk":
            seite = new Statistiken(this);
            this.setContentPane(seite);
            break;
            

        default:
            break;
        }
        this.revalidate();
        this.paint(getGraphics());
    }
    
    /**
     * Gibt den momentanten Gesamtdatensatz dieser Spielsession zurueck.
     * @return momentaner Gesamtdatensatz
     */
    public Gesamtdatensatz getGesamtdatensatz() {
        return gesamtdatensatz;
    }
    
    /**
     * Initiert das Laden des Gesamtdatensatzes durch die Datenklassen.
     */
    private void gesamtdatenLaden() {
        
    }
    
    /**
     * Initiiert das Speichern des Gesamtdatensatzes durch die Datenklassen.
     */
    private void gesamtdatenSpeichern() {
        
    }
    
    
    
    // Ende Methoden
    
    /**
     * Sorgt für die Erstellung einer SpielGUI beim start des Programms.
     * @param args Systemrelevantes Stringarray
     */
    public static void main(String[] args) {
        new SpielGUI();
    }
}
