package daten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import figuren.Bauer;
import figuren.Dame;
import figuren.Figur;
import figuren.Koenig;
import figuren.Laeufer;
import figuren.Springer;
import figuren.Turm;
import gui.Feld;
import zuege.Zug;

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
     * Eine Liste mit den Namen aller gespeicherten Spiele.
     */
    private List<String> gespeicherteSpiele;
    
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
        this.gespeicherteSpiele  = new ArrayList<String>();
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
            "file.separator") + "Spiele");
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
        
        // Die Einstellungen speichern
        try {
            File sett = new File("settings" + System.getProperty(
                "file.separator") + "settings.txt");
            FileWriter fw1 = new FileWriter(sett);
            fw1.write(einstellungen.toString());
            fw1.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
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
        
    }
    
    /**
     * L&auml;dt - sofern vorhanden - alle ben&ouml;tigten Daten aus dem
     * ausf&uuml;hrenden Ordner.
     */
    public void laden() {
        // Spieler Ordner
        File ordner = new File("settings" + System.getProperty(
            "file.separator") + "Spieler");
        // Wenn dieser gefuellt ist, muesste es einen intakten gespeicherten
        // Datensatz geben
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
        // Die Einstellungen laden
        try {
            // Die Datei in der die Einstellungen liegen
            File sett = new File("settings" + System.getProperty(
                "file.separator") + "settings.txt");
            // Ein Reader um sie zeilenweise auslesen zu koennen
            BufferedReader br = new BufferedReader(new FileReader(sett));
            // Die ZugzeitBegrenzung
            int zzb = Integer.parseInt(br.readLine());
            // Die sechs booleans
            boolean[] bool = new boolean[6];
            for (int i = 0; i <= 5; i++) {
                bool[i] = Boolean.parseBoolean(br.readLine());
            }
            br.close();
            // Einen neuen Einstellungssatz mit den gelesenen Daten erstellen
            einstellungen = new Einstellungen(zzb, bool[0], bool[1], bool[2], 
                bool[3], bool[4], bool[5]);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        
        // Die Spieler laden
        File spielerOrdner = new File("settings" + System.getProperty(
            "file.separator") + "Spieler");
        File[] files = spielerOrdner.listFiles();
        // Liste mit den Namen der Computerspieler
        List<String> computerNamen = new ArrayList<String>(
            Arrays.asList("Comp1", "Comp2", "Comp3", "Comp4"));        
        for (int i = 0; i < files.length; i++) {
            // Der Name des Spielers (Name der Datei ohne .txt)
            String name = files[i].getName()
                .substring(0, files[i].getName().length() - 4);
            try {
                // Die Datei in der die Spielerdaten liegen
                File file = new File("settings" + System.getProperty(
                    "file.separator") + "Spieler" + System.getProperty(
                        "file.separator") + name + ".txt");
                // Ein Reader um sie zeilenweise auslesen zu koennen
                BufferedReader br = new BufferedReader(new FileReader(file));
                // Hier muessen alle Zeilen ausgelesen und zugeordnet werden
                Spieler spieler;
                // Wenn es ein Computerspieler ist
                if (computerNamen.contains(name)) {
                    spieler = new Computerspieler(name);
                // Wenn es ein normaler Spieler ist
                } else {
                    spieler = new Spieler(name);
                }
                // Die Statistik des Spielers
                int[] stat = new int[16];
                for (int j = 0; j <= 15; j++) {
                    stat[j] = Integer.parseInt(br.readLine());
                }
                br.close();
                // Neue Statistik erstellen
                Statistik statistik = new Statistik(stat);
                // Die Statistik dem Spieler zuordnen
                spieler.setStatistik(statistik);
                spielerListe.add(spieler);
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        } 
        
        // Die Namen der vorhandenen Spiele laden
        File spieleOrdner = new File("settings" + System.getProperty(
            "file.separator") + "Spiele");
        files = spieleOrdner.listFiles();
        for (File file : files) {
            // Den Namen (Name der Datei ohne das .txt)
            gespeicherteSpiele.add(file.getName().substring(
                0, file.getName().length() - 4));
        }
        
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
     * Gibt den Spieler mit dem angegebenen Namen zur&uuml;ck.
     * @param name Der Name des gesuchten Spielers
     * @return  Der Spieler mit dem angegebenen Namen
     */
    private Spieler getSpieler(String name) {
        Spieler gesuchterSpieler = null;
        for (Spieler spieler : spielerListe) {
            if (spieler.getName().equals(name)) {
                gesuchterSpieler = spieler;
            }
        }
        return gesuchterSpieler;
    }
    
    /**
     * L&auml;dt das angegebene Spiel und gibt es zur&uuml;ck.
     * @param name Der Name des Spiels
     * @return Das Spiel mit dem angegebenen Namen
     */
    public Spiel getSpiel(String name) {
        // Die Quelldatei fuer das Spiel
        File file = new File("settings" + System.getProperty(
            "file.separator") + "Spiele" + System.getProperty(
                "file.separator") + name + ".txt");
       
        // Das Spiel anlegen
        Spiel spiel = null;
        
        try {
            // Ein Reader um die Datei zeilenweise auslesen zu koennen
            BufferedReader br = new BufferedReader(new FileReader(file));
            // Hier muessen alle Zeilen ausgelesen und zugeordnet werden
            Spieler spieler1 = getSpieler(br.readLine());
            boolean farbe1 = Boolean.parseBoolean(br.readLine());
            Spieler spieler2 = getSpieler(br.readLine());
            boolean farbe2 = Boolean.parseBoolean(br.readLine());
            // Neue FelderListe wird erstellt
            List<Feld> felderListe = erstelleFelderListe();
            
            // Neues Spielfeld erzeugen
            boolean aktuellerSpieler = Boolean.parseBoolean(br.readLine());
            Spielfeld spielfeld = new Spielfeld(felderListe, aktuellerSpieler);
            spielfeld.setSpieldaten(new Spieldaten());
            // Nacheinander werden jetzt die Listen ausgelesen
            // Die weisse Figuren-Liste
            List<Figur> weisseFiguren = fuelleFigurenListe(br, felderListe);
            // Die schwarze Figuren-Liste
            List<Figur> schwarzeFiguren = fuelleFigurenListe(br, felderListe);
            // Die geschlagenen weissen Figuren
            List<Figur> geschlagenWeiss = fuelleFigurenListe(br, felderListe);
            // Die geschlagenen schwarzen Figuren
            List<Figur> geschlagenSchwarz = fuelleFigurenListe(br, felderListe);
            
            // Listen dem Spielfeld uebergeben
            spielfeld.setWeisseFiguren(weisseFiguren);
            spielfeld.setSchwarzeFiguren(schwarzeFiguren);
            spielfeld.setGeschlagenWeiss(geschlagenWeiss);
            spielfeld.setGeschlagenSchwarz(geschlagenSchwarz);
            
            // Den Feldern die Figuren zuweisen
            for (Figur figur : weisseFiguren) {
                figur.getPosition().setFigur(figur);
            }
            for (Figur figur : schwarzeFiguren) {
                figur.getPosition().setFigur(figur);
            }
            
            // Den Figuren das fertige Spielfeld uebergeben
            for (Figur figur : weisseFiguren) {
                figur.setSpielfeld(spielfeld);
            }
            for (Figur figur : schwarzeFiguren) {
                figur.setSpielfeld(spielfeld);
            }
            for (Figur figur : geschlagenWeiss) {
                figur.setSpielfeld(spielfeld);
            }
            for (Figur figur : geschlagenSchwarz) {
                figur.setSpielfeld(spielfeld);
            }
            // Einstellungen laden
            // Die ZugzeitBegrenzung
            int zzb = Integer.parseInt(br.readLine());
            // Die sechs booleans
            boolean[] bool = new boolean[6];
            for (int i = 0; i <= 5; i++) {
                bool[i] = Boolean.parseBoolean(br.readLine());
            }
            einstellungen = new Einstellungen(zzb, bool[0], bool[1], bool[2], 
                bool[3], bool[4], bool[5]);
            
            // Schachnotation laden
            String notation = "";
            // Zugzeiten und Anzahl der Zuege fuer beide Spieler
            int zugZeitWeiss = 0;
            int zugZeitSchwarz = 0;
            int zuegeWeiss = 0;
            int zuegeSchwarz = 0;
            // Der aktuell zu behandelne Spieler (weiss beginnt)
            boolean aktuell = true;
            String line = br.readLine();
            // Solange es noch neue Zuege gibt
            while (!(line == null || line.equals(""))) {
                // Ganze Zeile speichern
                notation += line + System.getProperty("line.separator");
                /* Es gibt verschiedene Arten von Schachnotation:
                 * Klassisch: Db3-c4 23 sek
                 * Schlag: Db3xc4 23 sek
                 * Bauer: b3-b4 23 sek
                 * Rochade: 0-0 bzw. 0-0-0 23 sek
                 * en Passant: f5xg6 e.p. 23 sek
                 * Die Zugzeit kann natuerlich auch verschieden viele Stellen
                 * haben.
                 * Daher: Die Zugzeit steht immer zwischen den letzten beiden 
                 * Leerzeichen
                 */
                // Letztes Leerzeichen
                int hinteresLeerzeichen = line.lastIndexOf(" ");
                String vordererTeil = line.substring(0, hinteresLeerzeichen);
                // Vorletztes Leerzeichen
                int vorderesLeerzeichen = vordererTeil.lastIndexOf(" ");
                // Zugzeit steht dazwischen
                int zugzeit = Integer.parseInt(line.substring(
                    vorderesLeerzeichen + 1, hinteresLeerzeichen));
                // Wenn das ein weisser Zug ist
                if (aktuell) {
                    // Addiere die Zugzeit auf weiss und erhoehe die Zuganzahl
                    zugZeitWeiss += zugzeit;
                    zuegeWeiss++;
                } else {
                    // Addiere die Zugzeit auf schwarz und erhoehe die Zuganzahl
                    zugZeitSchwarz += zugzeit;
                    zuegeSchwarz++;
                }
                // Betreffenden Spieler aendern
                aktuell = !aktuell;
                // Naechste Zeile lesen
                line = br.readLine();
            }
            // Die gelesenen Daten in die Spieldaten schreiben
            spielfeld.getSpieldaten().setGeladenZeitWeiss(zugZeitWeiss);
            spielfeld.getSpieldaten().setGeladenZeitSchwarz(zugZeitSchwarz);
            spielfeld.getSpieldaten().setGeladenZuegeWeiss(zuegeWeiss);
            spielfeld.getSpieldaten().setGeladenZuegeSchwarz(zuegeSchwarz);
            spielfeld.getSpieldaten().setGeladenNotation(notation);
            // Den Reader schliessen
            br.close();
            
            // Dem Spielfeld die Einstellungen zuf&uuml;gen
            spielfeld.setEinstellungen(einstellungen);
            
            // Den Spielern ihre Farben setzen
            spieler1.setFarbe(farbe1);
            spieler2.setFarbe(farbe2);
            
            // Das Spiel erstellen
            spiel = new Spiel(name, spieler1, spieler2, spielfeld);
            
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        
        // Die Quelldatei loeschen
        file.delete();
        return spiel;
    }
    
    /**
     * L&auml;dt mithilfe des &uuml;bergebenen BufferedReaders die Zugliste aus
     * der Textdatei. Dabei wird Schachnotation in einen Zug umgewandelt.
     * @param br Ein BufferedReader der entsprechenden Textdatei
     * @param felderListe Die zugeh&ouml;hrige Liste der Felder 
     * @return Die vollst&auml;ndige Zugliste
     */
    private List<Zug> ladeZugListe(BufferedReader br, List<Feld> felderListe) {
        List<Zug> zugListe = new ArrayList<Zug>();
        try {
            // Schachnotation laden
            String line = br.readLine();
            while (!line.equals("")) {
                if (line.equals("0-0")) {
                
                } else if (line.equals("0-0-0")) {
                
                } else {
                    int stelleLeerzeichen = line.indexOf(" ");
                    String vordererTeil = line.substring(0, stelleLeerzeichen);
                    int stelleTrennung = line.indexOf("-");
                    if (stelleTrennung == -1) {
                        stelleTrennung = line.indexOf("x");
                    }
                    String figPos = vordererTeil.substring(
                        stelleTrennung - 2, stelleTrennung);
                    // Spaltenbezeichnung
                    List<String> spalten = new ArrayList<String>(Arrays.asList(
                        "a", "b", "c", "d", "e", "f", "g", "h"));
                    int x = spalten.indexOf(figPos.substring(0, 1));
                    int y = Integer.parseInt(figPos.substring(1)) - 1;
                    
                }
                // Naechste Zeile lesen
                line = br.readLine();
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return zugListe;
    }
    /**
     * Erstellt eine neue felderListe mit 64 Feldern(Index 0-7, 0-7).
     * @return Die neu erstellte Felder-Liste
     */
    
    private List<Feld> erstelleFelderListe() {
        List<Feld> felderListe = new ArrayList<Feld>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Feld temp = new Feld(j, i);
                felderListe.add(temp);
            }    
        }
        return felderListe;
    }
    
    /**
     * Wandelt die gespeicherte zweistellige Position in das entsprechende
     * Feld um und gibt es zur&uuml;ck.
     * @param position Die zweistelligen Koordinaten des Feldes
     * @param felderListe Die Liste der Felder auf die zugegriffen werden soll
     * @return Das gesuchte Feld
     */
    private Feld positionToFeld(String position, List<Feld> felderListe) {
        int x = Integer.parseInt(position.substring(0, 1));
        int y = Integer.parseInt(position.substring(1));
        return felderListe.get(x + 8 * y);
    }
    
    /**
     * Liest mithilfe des angegebenen BufferedReaders eine Liste von Figuren
     * aus der Textdatei.
     * @param br Ein BufferedReader der entsprechenden Textdatei
     * @param felderListe Die Felder-Liste um die Figuren richtig setzen zu
     * k&ouml;nnen
     * @return Eine Liste mit Figuren
     */
    private List<Figur> fuelleFigurenListe(BufferedReader br, 
        List<Feld> felderListe) {
        List<Figur> figuren = new ArrayList<Figur>();
        // Solange keine Leerzeile erreicht ist
        try {
            // Die Ueberschrift lesen aber nicht speichern
            br.readLine();
            // Die erste korrekte Zeile
            String line = br.readLine();
            // Solange die erste Zeile nicht leer ist
            while (!line.equals("")) {
                // Die Position
                String position = line;
                // Aus der Position das Feld bestimmen
                Feld feld = positionToFeld(position, felderListe); 
                // Farbe der Figur
                boolean farbe = Boolean.parseBoolean(br.readLine());
                // Wert der Figur
                int wert = Integer.parseInt(br.readLine());
                // Ob sie schon gezogen wurde
                boolean gezogen = Boolean.parseBoolean(br.readLine());
                // Neue Figur anlegen
                Figur figur;
                // Je nach Wert die Figur entsprechend erstellen
                if (wert == 0) {
                    figur = new Koenig(feld, farbe);
                } else if (wert == 100) {
                    figur = new Bauer(feld, farbe);
                } else if (wert == 275) {
                    figur = new Springer(feld, farbe);
                } else if (wert == 325) {
                    figur = new Laeufer(feld, farbe);
                } else if (wert == 465) {
                    figur = new Turm(feld, farbe);
                } else {
                    figur = new Dame(feld, farbe);
                }
             
                figur.setGezogen(gezogen);
                // Die Figur der Liste zufuegen
                figuren.add(figur);
                // Naechste Zeile einlesen
                /* Entweder ist das die erste Zeile der neuen Figur oder die 
                 * Leerzeile die der while-Schleife signalisiert, dass die Liste
                 * zu Ende ist und abgebrochen werden soll.
                 */
                line = br.readLine();
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        
        return figuren;
        
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
     * Gibt eine Liste der menschlichen Spieler zur&uuml;ck.
     * Wird ben&ouml;tigt, damit keine zwei Computerspieler gegen einander 
     * spielen k&ouml;nnen.
     * @return Eine Liste der menschlichen Spieler
     */
    public List<Spieler> getMenschlicheSpieler() {
        List<Spieler> menschSpieler = new ArrayList<Spieler>();
        for (Spieler spieler : spielerListe) {
            if (!(spieler instanceof Computerspieler)) {
                menschSpieler.add(spieler);
            }
        }
        return menschSpieler;
    }
    
    /**
     * Gibt die Liste der Namen der Spiele zur&uuml;ck.
     * @return Liste der Namen der Spiele
     */
    public List<String> getSpieleListe() {
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
     * Speichert das angegebene Spiel in den Text-Dateien.
     * @param spiel Das Spiel, welches gespeichert werden soll
     */
    public void spielSpeichern(Spiel spiel) {
        // Die Spieldatei - sofern vorhanden - loeschen
        File file = new File("settings" + System.getProperty(
            "file.separator") + "Spiele" + System.getProperty(
                "file.separator") + spiel.getSpielname() + ".txt");
        if (file.exists()) {
            file.delete();
        }
        try {
            File spielDatei = new File("settings" + System.getProperty(
                "file.separator") + "Spiele" + System.getProperty(
                    "file.separator") + spiel.getSpielname() + ".txt");
            FileWriter fw = new FileWriter(spielDatei);
            fw.write(spiel.toString());
            fw.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        if (!gespeicherteSpiele.contains(spiel.getSpielname())) {
            gespeicherteSpiele.add(spiel.getSpielname());
        }
        
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
