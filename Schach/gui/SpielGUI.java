package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JFrame;

import daten.Gesamtdatensatz;
import daten.Spiel;
import daten.Spieler;
import daten.Einstellungen;

/**
 * Das eigentliche Fenster des Schachspiels. Auf diesem Fenster werden die Panes
 * gewechselt. Erbt von JFrame
 * @author Marvin Wolf
 */
public class SpielGUI extends JFrame implements WindowListener {
    
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
        //this.addWindowListener(this);
        
        gesamtdatenLaden();
        gesamtdatenSpeichern();
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
            seite = new EinstellungenGUI(this);
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
     * Methode zur Rueckgabe der SpieleListe.
     * @return Gibt die SpieleListe des Gesamtdatensatzes zurueck
     */
    public List<Spiel> getSpieleListe() {
        return gesamtdatensatz.getSpieleListe();
    }
    
    /**
     * Methode zur rueckgabe der SpielerListe des Gesamtdatensatzes.
     * @return SpielerListe des Gesamtdatensatzes
     */
    public List<Spieler> getSpielerListe() {
        return gesamtdatensatz.getSpielerListe();
    }
    
    /**
     * Gibt den Einstellungsatz der momentanten Spielsession zurueck.
     * @return momentaner Einstellungssatz
     */
    public Einstellungen getEinstellungen() {
        return gesamtdatensatz.getEinstellungen();
    }
    
    /**
     * Setzt die Grundeinstellung auf die neu Eingegebenen Einstellungen.
     * @param einstellungen neue Einstellungen
     */
    public void setEinstellungen(Einstellungen einstellungen) {
        gesamtdatensatz.setEinstellungen(einstellungen);
    }
    
    /**
     * Fuegt einen Spieler zum gesamtdatensatz hinzu.
     * @param spieler Spieler, welcher zum Gesamtdatensatz hinzugefuegt werden 
     * soll
     */
    public void addSpieler(Spieler spieler) {
        gesamtdatensatz.addSpieler(spieler);
    }
    
    /**
     * Fuegt ein Spiel zur SpieleListe des Gesamtdatensatzes hinzu.
     * @param spiel Das hinzuzufuegende Spieleobjekt
     */
    public void addSpiel(Spiel spiel) {
        gesamtdatensatz.addSpiel(spiel);
    }
    
    /**
     * Initiert das Laden des Gesamtdatensatzes durch die Datenklassen.
     */
    private void gesamtdatenLaden() {
        gesamtdatensatz = new Gesamtdatensatz();
        gesamtdatensatz.laden();
        // TODO Laden
        
    }
    
    /**
     * Initiiert das Speichern des Gesamtdatensatzes durch die Datenklassen.
     */
    private void gesamtdatenSpeichern() {
        gesamtdatensatz.speichern();
    }
    
    
    
    // Ende Methoden
    
    /**
     * Sorgt für die Erstellung einer SpielGUI beim start des Programms.
     * @param args Systemrelevantes Stringarray
     */
    public static void main(String[] args) {
        new SpielGUI();
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowClosing(WindowEvent e) {
        // Sicherheitsfenster Wirklich beenden?
        
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */    
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
    }
}
