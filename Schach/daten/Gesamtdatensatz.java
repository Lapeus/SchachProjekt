package daten;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    private List<Spieler> spielerListe = new ArrayList<Spieler>();
    
    /**
     * Eine Liste mit allen gespeicherten Spielen.
     */
    private List<Spiel> gespeicherteSpiele = new ArrayList<Spiel>();
    
    /**
     * Erzeugt einen neuen Gesamtdatensatz der dann gespeichert werden kann.<br>
     * Einziger Konstruktor dieser Klasse.
     * @param spielerListe Eine Liste mit allen angelegten Spielern
     * @param spielListe Eine Liste mit allen gespeicherten Spielen
     */
    public Gesamtdatensatz(List<Spieler> spielerListe, List<Spiel> spielListe) {
        this.spielerListe = spielerListe;
        this.gespeicherteSpiele = spielListe;
    }
    
    /**
     * Speichert den Gesamtdatensatz in einen Einstellungsordner um beim 
     * Neustart des Programms alle Daten wieder Laden zu k&ouml;nnen.
     */
    public void speichern() {
        // Den Inhalt des Spieler-Ordners - sofern vorhanden - loeschen
        File ordner = new File("settings" + System.getProperty(
            "file.separator") + "Spieler");
        if (ordner.exists()) {         
            File[] listFiles = ordner.listFiles();
            for (File file : listFiles) {            
                file.delete();
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
            "file.separator") + "Spieler");
        if (ordner.exists()) {         
            File[] listFiles = ordner.listFiles();
            for (File file : listFiles) {            
                file.delete();
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
}
