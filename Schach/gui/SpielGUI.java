package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import daten.Gesamtdatensatz;
import daten.Spiel;
import daten.Spieler;
import daten.Einstellungen;

/**
 * Das eigentliche Fenster des Schachspiels. Auf diesem Fenster werden die Panes
 * gewechselt. Erbt von JFrame.
 * @author Marvin Wolf
 */
public class SpielGUI extends JFrame implements WindowListener {
    
    // Anfang Attribute
    
    /**
     * Serial-Key zur Identifizierung.
     */
    private static final long serialVersionUID = -8895681303810255159L;
    
    /**
     * Gesamtdatensatz, welcher zu Beginn geladen/erstellt und beim Beenden 
     * gespeichert werden muss.
     */
    private Gesamtdatensatz gesamtdatensatz;    
    
    // Ende Attribute

    // Konstruktor
    
    /**
     * Erstellt ein neues Spiel-Fenster.
     * Verwaltet den Gesamtdatensatz einer Spielsession. Dient als Grundfenster
     * auf dem nur die jeweiligen Panels aufgerufen werden. Jedem Panel wird 
     * dieses Fenster als parent-Fenster &uuml;bergeben, damit diese auf die 
     * hier lokalisierten Funktionen zugreifen können.
     * Ruft die init()-Methode auf.
     */
    public SpielGUI() {
        super("Schachspiel");
        setVisible(true);
        // Maximale Screengroesse auf Vollbild setzten;
        setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        // Default CloseOperation ausschalten
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        // Eigene CloseOperation einbinden
        addWindowListener(this);
        // Gesamtdaten laden
        gesamtdatenLaden();
        gesamtdatenSpeichern();
        // Eroeffnungsseite aufrufen
        seitenAuswahl("Eroeffnungsseite");
    }
    
    // Anfang Methoden
    
    /**
     * Dient zum Wechsel der Contentpane, welches durch den 
     * SeitenwechselListener aufgerufen wird. In Ausnahmef&auml;llen wird diese 
     * Methode auch von dem jeweiligen Panel aufgerufen.
     * @param auswahl Auf die zu wechselnde Seite.
     */
    public void seitenAuswahl(String auswahl) {
        Container seite;
        // Je nach command die passende Seite aufrufen
        switch (auswahl) {
        case "Eroeffnungsseite":
            seite = new Eroeffnungsseite(this);
            setContentPane(seite);
            setMinimumSize(new Dimension(500, 500));
            setSize(new Dimension(500, 500));
            setLocationRelativeTo(null);
            break;
            
        case "Spielerauswahl":
            setMinimumSize(new Dimension(500, 300));
            setSize(new Dimension(200, 200));
            seite = new Spielerauswahl(this);
            setContentPane(seite);
            setLocationRelativeTo(null);
            break;
            
        case "Spiel laden":
            seite = new SpielLaden(this);
            setContentPane(seite);
            setMinimumSize(new Dimension(300, 300));
            setSize(new Dimension(300, 300));
            setLocationRelativeTo(null);
            break;
            
        case "Einstellungen":
            seite = new EinstellungenGUI(this);
            setContentPane(seite);
            setLocationRelativeTo(null);
            break;
            
        case "Highscore":
            seite = new Highscore(this);
            setContentPane(seite);
            setLocationRelativeTo(null);
            break;
            
        case "Statistiken":
            seite = new Statistiken(this);
            setContentPane(seite);
            setLocationRelativeTo(null);
            break;
        
        case "KonfigFenster":
            seite = new Farbkonfiguration(this);
            setContentPane(seite);
            setLocationRelativeTo(null);
            break;

        default:
            break;
        }
        revalidate();
        paint(getGraphics());
    }
    
    /**
     * Gibt den momentanen Gesamtdatensatz dieser Spielsession zur&uuml;ck.
     * @return momentaner Gesamtdatensatz
     */
    public Gesamtdatensatz getGesamtdatensatz() {
        return gesamtdatensatz;
    }
    
    /**
     * Gibt die Farben zur&uuml;ck.
     * @return Ein Farb-Array
     */
    public Color[] getFarben() {
        return gesamtdatensatz.getFarben();
    }
    
    /**
     * Setzt die Farben.
     * @param farben Ein Farb-Array
     */
    public void setFarben(Color[] farben) {
        gesamtdatensatz.setFarben(farben);
    }
    
    /**
     * Methode zur R&uuml;ckgabe der Spiele-Liste.
     * @return Gibt die Spiele-Liste des Gesamtdatensatzes zur&uuml;ck
     */
    public List<String> getSpieleListe() {
        return gesamtdatensatz.getSpieleListe();
    }
    
    /**
     * Ruft die getSpiel-Methode des Gesamdatensatzes auf und gibt das Spiel mit
     * diesem Namen zur&uuml;ck.
     * @param spielname Name eines gespeicherten Spiels
     * @return Spiel zu dem eingegebenen Namen
     */
    public Spiel getSpiel(String spielname) {
        return gesamtdatensatz.getSpiel(spielname);
    }
    
    /**
     * Methode zur R&uuml;ckgabe der Spieler-Liste des Gesamtdatensatzes.
     * @return Spieler-Liste des Gesamtdatensatzes
     */
    public List<Spieler> getSpielerListe() {
        return gesamtdatensatz.getSpielerListe();
    }
    
    /**
     * Methode zur R&uuml;ckgabe der Menschlichen-Spieler-Liste des 
     * Gesamtdatensatzes.
     * @return Meschliche-Spieler-Liste des Gesamtdatensatzes
     */
    public List<Spieler> getMenschlicheSpielerListe() {
        return  gesamtdatensatz.getMenschlicheSpieler();
    }
    
    /**
     * Gibt den Einstellungssatz der momentanen Spielsession zur&uuml;ck.
     * @return momentaner Einstellungssatz
     */
    public Einstellungen getEinstellungen() {
        return gesamtdatensatz.getEinstellungen();
    }
    
    /**
     * Setzt die Grundeinstellung auf die eingegebenen Einstellungen.
     * @param einstellungen Neue Einstellungen
     */
    public void setEinstellungen(Einstellungen einstellungen) {
        gesamtdatensatz.setEinstellungen(einstellungen);
    }
    
    /**
     * F&uuml;gt einen Spieler zum Gesamtdatensatz hinzu.
     * @param spieler Spieler, welcher zum Gesamtdatensatz hinzugef&uuml;gt 
     * werden soll
     */
    public void addSpieler(Spieler spieler) {
        gesamtdatensatz.addSpieler(spieler);
    }
    
    /**
     * F&uuml;gt ein Spiel zur Spiele-Liste des Gesamtdatensatzes hinzu.
     * @param spiel Das hinzuzuf&uuml;gende Spieleobjekt
     */
    public void spielSpeichern(Spiel spiel) {
        gesamtdatensatz.spielSpeichern(spiel);
    }
    
    /**
     * Speichert ein beendetes Spiel.
     * @param spiel Das zu speichernde Spiel
     */
    public void endSpielSpeichern(Spiel spiel) {
        gesamtdatensatz.endSpielSpeichern(spiel);
    }
    
    /**
     * Gibt die HighscoreListe des Gesamtdatensatzes zur&uuml;ck.
     * @return nach Punkten gerankte Spielerliste 
     */
    public List<Spieler> getRanking() {
        return gesamtdatensatz.getRanking();
    }
    
    /**
     * Speichert das Spiel als autosave zwischen.
     * @param spiel Das zu speichernde Spiel
     */
    public void autoSave(Spiel spiel) {
        gesamtdatensatz.automatischesSpeichern(spiel);
    }
    
    /**
     * L&ouml;scht die automatische Speicherung des momentanen Spiels.
     */
    public void autoSaveLoeschen() {
        gesamtdatensatz.autosaveLoeschen();
    }
    
    /**
     * Initiiert das Laden des Gesamtdatensatzes durch die Datenklassen.
     */
    private void gesamtdatenLaden() {
        gesamtdatensatz = new Gesamtdatensatz();
        gesamtdatensatz.laden();
    }
    
    /**
     * Initiiert das Speichern des Gesamtdatensatzes durch die Datenklassen.
     */
    private void gesamtdatenSpeichern() {
        gesamtdatensatz.speichern();
    }
    
    /**
     * Methode zum Abspielen von Systemsounds.
     * @param filename Name des Sound der abgespielt werden soll
     */
    public void soundAbspielen(String filename) {
        if (getEinstellungen().isTon()) {
            try {
                String fileSep = System.getProperty(
                    "file.separator");
                AudioInputStream ais = AudioSystem
                    .getAudioInputStream(
                    new File("sounds" + fileSep 
                            + filename));
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
 
    // Ende Methoden
    
    /**
     * Sorgt f&uuml;r die Erstellung einer SpielGUI beim Start des Programms.
     * @param args Systemrelevantes String-Array
     */
    public static void main(String[] args) {
        new SpielGUI();
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowActivated(WindowEvent e) {
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowClosed(WindowEvent e) {
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowClosing(WindowEvent e) {
        soundAbspielen("Hinweis.wav");
        int auswahl = JOptionPane.showConfirmDialog(this, "Wollen Sie das Spiel"
            + " wirklich beenden", "Fenster schlie\u00dfen",
            JOptionPane.YES_NO_OPTION);
        // Wenn Ja angeklickt wird
        if (auswahl == 0) {
            // Gesamtdaten vor beenden abspeichern
            gesamtdatenSpeichern();
            // Beenden-Sound absspielen
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(
                    new File("sounds" + System.getProperty("file.separator") 
                        + "schliessen.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
                dispose();
                Thread.sleep(2000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // Fenster schliessen
            System.exit(0);
        }
        // Wenn Nein angeklickt wird passiert nichts
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowDeactivated(WindowEvent e) {
    }

    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */    
    public void windowDeiconified(WindowEvent e) {
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowIconified(WindowEvent e) {
    }
    
    /**
     * Automatisch generierte Methode zur Behandlung von WindowListenerEvents.
     * @param e WindowListenerEvent
     */
    public void windowOpened(WindowEvent e) {
    }

}
