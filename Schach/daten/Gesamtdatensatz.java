package daten;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Verwaltet alle Daten, die beim Schlie&szlig;en des Programms gespeichert und
 * beim &Ouml;ffnen des Programms geladen werden m&uuml;ssen.<br>
 * Unter anderem wird eine Liste mit den bisher angelegten Spielern, eine Liste
 * mit den gespeicherten Spielen und das Ranking der Spieler gespeichert.
 * Stellt die Methoden zum Speichern und Laden des Gesamtdatensatzes zur
 * Verf&uuml;gung.
 * @author Christian Ackermann
 */
public class Gesamtdatensatz {
    
    /**
     * Eine Liste mit allen bisher angelegten Spielern.
     */
    private List<Spieler> spielerListe;
    
    /**
     * Eine Liste mit allen gespeicherten Spielen.
     */
    private List<Spiel> gespeicherteSpiele;
    
    /**
     * Die Standardeinstellungen des Spiels.
     */
    private Einstellungen einstellungen;
    
    /**
     * Erzeugt einen neuen Gesamtdatensatz der dann gespeichert werden kann.<br>
     * Einziger Konstruktor dieser Klasse. Ist leer und wird durch die Laden-
     * Methode mit Informationen gef&uuml;llt.
     */
    public Gesamtdatensatz() {
        this.spielerListe = new ArrayList<Spieler>();
        this.gespeicherteSpiele  = new ArrayList<Spiel>();
    }
    
    /**
     * Speichert den Gesamtdatensatz in einen Einstellungsordner um beim 
     * Neustart des Programms alle Daten wieder Laden zu k&ouml;nnen.
     */
    public void speichern() {
        // Ordnerstruktur abfragen oder erstellen
        File settingsOrdner = new File("settings");
        // Wenn es den settings Ordner nicht gibt
        if (!settingsOrdner.exists()) {
            // Wird er erstellt
            settingsOrdner.mkdir();
        }
        File spielerOrdner = new File("settings" + System.getProperty(
            "file.separator") + "Spieler");
        // Wenn es den Spieler Ordner nicht gibt
        if (!spielerOrdner.exists()) {
            // Wird er erstellt
            spielerOrdner.mkdir();
        }
        File spielOrdner = new File("settings" + System.getProperty(
            "file.separator") + "Spieler");
        // Wenn es den Spiele Ordner nicht gibt
        if (!spielOrdner.exists()) {
            // Wird er erstellt
            spielOrdner.mkdir();
        }
        
        // Die Einstellungsdatei - sofern vorhanden - loeschen
        File settings = new File("settings" + System.getProperty(
            "file.separator") + "settings.txt");
        if (settings.exists()) {
            settings.delete();
        }
        
        // Den Inhalt des Spieler-Ordners - sofern vorhanden - loeschen
        File ordner = new File("settings" + System.getProperty(
            "file.separator") + "Spieler");         
        File[] listFiles = ordner.listFiles();
        for (File file : listFiles) {            
            try {
                file.delete();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
     
        // Spieler speichern
        for (Spieler spieler : spielerListe) {
            try {
                File file = new File("settings" + System.getProperty(
                    "file.separator") + "Spieler" + System.getProperty(
                        "file.separator") + spieler.getName() + ".txt");
                FileWriter fw = new FileWriter(file);
                fw.write(spieler.toString());
                fw.close();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
        
        // Den Inhalt des Spiele-Ordners - sofern vorhanden - loeschen
        ordner = new File("settings" + System.getProperty(
            "file.separator") + "Spiele");      
        listFiles = ordner.listFiles();
        for (File file : listFiles) { 
            try {
                file.delete();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Spiele speichern
        for (Spiel spiel : gespeicherteSpiele) {
            try {
                File file = new File("settings" + System.getProperty(
                    "file.separator") + "Spiele" + System.getProperty(
                        "file.separator") + spiel.getSpielname() + ".txt");
                FileWriter fw = new FileWriter(file);
                fw.write(spiel.toString());
                fw.close();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
    }
    
    /**
     * L&auml;dt - sofern vorhanden - alle ben&ouml;tigten Daten aus dem
     * ausf&uuml;hrenden Ordner.
     */
    public void laden() {
        File ordner = new File("settings" + System.getProperty(
            "file.separator") + "Spieler");
        if (ordner.exists() && ordner.listFiles().length != 0) {         
            ladeDaten();
        } else {
            erzeugeNeueDaten();
        }
    }
    
    /**
     * L&auml;dt die vorhandenen Daten in den Gesamtdatensatz.
     */
    private void ladeDaten() {
        
    }
    
    /**
     * Legt neue Computerspieler und den Standard-Einstellungssatz an.
     */
    private void erzeugeNeueDaten() {
        /* Grundeinstellungen:
         * Zugzeit-Begrenzung: Nicht vorhanden (6000)
         * Moegliche Felder anzeigen: True
         * Bedrohte Felder anzeigen: False
         * Rochade moeglich: True
         * En Passant moeglich: False
         * Schachwarnung: True
         * Statistik: True
         */
        einstellungen = new Einstellungen(6000, true, false, true, false, 
            true, true);
        spielerListe.add(new Computerspieler("Comp1"));
        spielerListe.add(new Computerspieler("Comp2"));
        spielerListe.add(new Computerspieler("Comp3"));
        spielerListe.add(new Computerspieler("Comp4"));
    }
    
    /**
     * Gibt die Liste der Spieler nach absteigendem Score sortiert wieder.
     * @return Eine Liste mit den gerankten Spielern
     */
    public List<Spieler> getRanking() {
        return sortiereListe(spielerListe);
    }
    
    /**
     * Eine statische Klasse, die zwei Spieler aufgrund ihres Scores vergleicht.
     * @author Christian Ackermann
     */
    public static class SpielerComparator implements Comparator<Spieler> {
        @Override
        public int compare(Spieler sp1, Spieler sp2) {
            // Wenn der Score des ersten Spielers kleiner ist
            if (sp1.getStatistik().getScore() 
                < sp2.getStatistik().getScore()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    
    /**
     * Sortiert die angegebene Liste von Spielern nach Score.
     * @param spieler Die zu sortierende Liste von Spielern
     * @return Die sortierte Liste
     */
    private List<Spieler> sortiereListe(List<Spieler> spieler) {
        Collections.sort(spieler, new SpielerComparator());
        return spieler;
    }
    
    /**
     * Gibt die Liste der Spieler zur&uuml;ck.
     * @return Liste der Spieler
     */
    public List<Spieler> getSpielerListe() {
        return spielerListe;
    }
    
    /**
     * Gibt die Liste der Spiele zur&uuml;ck.
     * @return Liste der Spiele
     */
    public List<Spiel> getSpieleListe() {
        return gespeicherteSpiele;
    }
    
    /**
     * F&uuml;gt einen Spieler hinzu.
     * @param spieler Der Spieler der zugef&uuml;gt werden soll.
     */
    public void addSpieler(Spieler spieler) {
        spielerListe.add(spieler);
    }
    
    /**
     * F&uuml;gt ein Spiel hinzu.
     * @param spiel Das Spiel, welches zugef&uuml;gt werden soll.
     */
    public void addSpiel(Spiel spiel) {
        gespeicherteSpiele.add(spiel);
        
    }
    /**
     * Gibt die Standardeinstellungen zur&uum;ck.
     * @return Der Standardeinstellungssatz
     */
    public Einstellungen getEinstellungen() {
        return einstellungen;
    }
    
    /**
     * Setzt die Standardeinstellungen.
     * @param einstellungen Der neue Einstellungssatz
     */
    public void setEinstellungen(Einstellungen einstellungen) {
        this.einstellungen = einstellungen;
    }
}
