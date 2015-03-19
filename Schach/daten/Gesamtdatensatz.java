package daten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import figuren.Dame;
import figuren.Figur;
import figuren.Laeufer;
import figuren.Springer;
import figuren.Turm;
import gui.Feld;
import zuege.Umwandlungszug;
import zuege.Zug;

/**
 * Verwaltet alle Daten, die beim Schlie&szlig;en des Programms gespeichert und
 * beim &Ouml;ffnen des Programms geladen werden m&uuml;ssen.<br>
 * Es werden dabei eine Liste mit den bisher angelegten Spielern, den 
 * gespeicherten Spielen und die aktuellen Einstellungen gespeichert.
 * Au&szlig;erdem stellt sie Methoden zum Speichern und Laden des 
 * Gesamtdatensatzes zur Verf&uuml;gung.
 * @author Christian Ackermann
 */
public class Gesamtdatensatz {
    
    /**
     * Eine Liste mit allen bisher angelegten Spielern.
     */
    private List<Spieler> spielerListe = new ArrayList<Spieler>();
    
    /**
     * Eine Liste mit den Namen aller gespeicherten Spiele inklusive 
     * Zeitstempel des letzten Speicherns.
     */
    private List<String> gespeicherteSpiele = new ArrayList<String>();
    
    /**
     * Die aktuellen Einstellungen des Spiels.
     */
    private Einstellungen einstellungen;
    
    /**
     * Erzeugt einen neuen Gesamtdatensatz der dann gespeichert werden kann.<br>
     * Einziger Konstruktor dieser Klasse. Ist leer und wird durch die Laden-
     * Methode mit Informationen gef&uuml;llt.
     */
    public Gesamtdatensatz() {
   
    }
    
    /**
     * L&auml;dt alle ben&ouml;tigten Daten aus dem ausf&uuml;hrenden Ordner.
     * <br> Sollten sich dort keine Daten befinden, werden diese neu erstellt.
     * @see #ladeDaten
     * @see #erzeugeNeueDaten
     */
    public void laden() {
        // Spieler Ordner
        File ordner = new File("settings" + System.getProperty(
            "file.separator") + "Spieler");
        // Wenn dieser gefuellt ist, muesste es einen intakten gespeicherten
        // Datensatz geben
        if (ordner.exists() && ordner.listFiles().length > 4) {         
            ladeDaten();
        } else {
            erzeugeNeueDaten();
        }
    }
    
    /**
     * L&auml;dt die vorhandenen Daten in den Gesamtdatensatz.<br>
     * Dabei wird zuerst die Datei settings.txt aus dem settings-Ordner gelesen.
     * Ein neuer Satz Einstellungen wird erzeugt und anschlie&szlig;end mit den
     * gelesenen Daten gef&uuml;llt. Sollte beim Laden etwas nicht 
     * funktionieren, wird automatisch ein neuer Satz mit Standardeinstellungen
     * erstellt. <br>
     * Danach werden die vorhandenen Spieler geladen. Der Dateiname gibt den
     * Spielernamen vor, in der Datei selbst stehen nur die Attribute der 
     * Statistik. <br>
     * Am Schluss werden noch die Namen der vorhanden Spiele geladen und um
     * ihren Zeitstempel erweitert, damit sie im Laden-Fenster entsprechend
     * angezeigt werden k&ouml;nnen.
     */
    private void ladeDaten() {
        // Die Einstellungen laden
        BufferedReader br1 = null;
        try {
            // Die Datei in der die Einstellungen liegen
            File setting = new File("settings" + System.getProperty(
                "file.separator") + "settings.txt");
            // Ein Reader um sie zeilenweise auslesen zu koennen
            br1 = new BufferedReader(new FileReader(setting));
            // Die ZugzeitBegrenzung
            int zzb = Integer.parseInt(br1.readLine());
            // Die sechs booleans
            boolean[] bool = new boolean[7];
            for (int i = 0; i <= 6; i++) {
                bool[i] = Boolean.parseBoolean(br1.readLine());
            }
            // Einen neuen Einstellungssatz mit den gelesenen Daten erstellen
            einstellungen = new Einstellungen();
            einstellungen.setZugZeitBegrenzung(zzb);
            einstellungen.setMoeglicheFelderAnzeigen(bool[0]);
            einstellungen.setBedrohteFigurenAnzeigen(bool[1]);
            einstellungen.setRochadeMoeglich(bool[2]);
            einstellungen.setEnPassantMoeglich(bool[3]);
            einstellungen.setSchachWarnung(bool[4]);
            einstellungen.setInStatistikEinbeziehen(bool[5]);
            einstellungen.setSpielfeldDrehen(bool[6]);
        } catch (IOException ioEx) {
            /* Wenn irgendwas beim Laden schief geht, werden die Standard-
             * Einstellungen wiederhergestellt.
             */
            einstellungen = new Einstellungen();
        } finally {
            try {
                br1.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
        
        // Die Spieler laden
        File spielerOrdner = new File("settings" + System.getProperty(
            "file.separator") + "Spieler");
        File[] files = spielerOrdner.listFiles();
        // Liste mit den Namen der Computerspieler
        List<String> computerNamen = new ArrayList<String>(
            Arrays.asList("Walter", "Karl Heinz", "Rosalinde", "Ursula"));
        for (File spielerFile : files) {
            // Der Name des Spielers (Name der Datei ohne .txt)
            String name = spielerFile.getName()
                .substring(0, spielerFile.getName().length() - 4);
            BufferedReader br2 = null;
            try {
                // Die Datei in der die Spielerdaten liegen
                File file = new File("settings" + System.getProperty(
                    "file.separator") + "Spieler" + System.getProperty(
                        "file.separator") + name + ".txt");
                // Ein Reader um sie zeilenweise auslesen zu koennen
                br2 = new BufferedReader(new FileReader(file));
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
                    stat[j] = Integer.parseInt(br2.readLine());
                }
                // Neue Statistik erstellen
                Statistik statistik = new Statistik(stat);
                // Die Statistik dem Spieler zuordnen
                spieler.setStatistik(statistik);
                spielerListe.add(spieler);
            } catch (IOException ioEx) {
                // Wenn das schief geht, wird der Spieler neu erzeugt
                spielerListe.add(new Spieler(name));
                // Und die fehlerhafte Datei geloescht
                spielerFile.delete();
            } finally {
                try {
                    br2.close();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        } 
        
        // Die Namen der vorhandenen Spiele laden
        File spieleOrdner = new File("settings" + System.getProperty(
            "file.separator") + "Spiele");
        files = spieleOrdner.listFiles();
        for (File file : files) {
            BufferedReader br3 = null;
            try {
                br3 = new BufferedReader(new FileReader(file));
                String datum = br3.readLine();
                // Den Namen (Name der Datei ohne das .txt)
                String name = file.getName().substring(
                    0, file.getName().length() - 4);
                name += " " + datum;
                gespeicherteSpiele.add(name);
            } catch (IOException ioEx) {
                gespeicherteSpiele.remove(file.getName());
            } finally {
                try {
                    br3.close();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }
        
    }
    
    /**
     * Legt den Standard-Einstellungssatz und neue Computerspieler an.
     */
    private void erzeugeNeueDaten() {
        /* Grundeinstellungen:
         * Zugzeit-Begrenzung: Nicht vorhanden (0)
         * Moegliche Felder anzeigen: True
         * Bedrohte Felder anzeigen: False
         * Rochade moeglich: True
         * En Passant moeglich: False
         * Schachwarnung: True
         * Statistik: True
         */
        einstellungen = new Einstellungen();
        spielerListe.add(new Computerspieler("Karl Heinz"));
        spielerListe.add(new Computerspieler("Rosalinde"));
        spielerListe.add(new Computerspieler("Ursula"));
        spielerListe.add(new Computerspieler("Walter"));
        
    }
    
    /**
     * Speichert den Gesamtdatensatz in einen Einstellungsordner um beim 
     * Neustart des Programms alle Daten wieder Laden zu k&ouml;nnen. <br>
     * Dabei wird zuerst getestet, ob eine intakte Ordnerstruktur vorhanden ist.
     * Wenn dem nicht so ist, wird sie entsprechend erstellt. Danach wird die
     * bisherige Einstellungsdatei zur Sicherheit gel&ouml;scht und eine neue
     * erstellt. Ebenso werden alle vorhandenen Spieler-Dateien gel&ouml;scht
     * und neu erstellt.
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
            // Schreibschutz
            sett.setReadOnly();
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
                // Schreibschutz
                file.setReadOnly();
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
        
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
     * Erstellt eine neue felderListe mit 64 Feldern(Index 0-7, 0-7). <br>
     * Sie ist eine Kopie der Methode aus der Klasse <b>SpielfeldGUI</b>.
     * @see gui.SpielfeldGUI#fuelleFelderListe
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
     * L&auml;dt das Spiel mit dem angegebenen Namen anhand der gespeicherten
     * Schachnotation.<br>
     * Dabei werden die Namen der Spieler mit ihren Farben, die Einstellungen,
     * mit denen das Spiel begonnen wurde und am Ende die Schachnotation 
     * geladen. Wenn die Schachnotation vollst&auml;ndig in Z&uuml;ge 
     * &uuml;bersetzt wurde, werden diese Z&uuml;ge aus der Startaufstellung
     * gezogen, sodass am Ende das Spielfeld genauso aussieht, wie es 
     * gespeichert wurde. Dies ist n&ouml;tig, da in der Notation keine Verweise
     * auf die Objekte der Figuren sondern nur auf die Felder vorhanden sind.
     * <br>Mit den ganzen Informationen wird ein neues Spiel erzeugt, 
     * welches anschlie&szlig;end zur&uuml;ckgegeben werden kann.
     * @param name Der Name des Spiels, wie er in dem Auswahlfenster angezeigt
     * wird
     * @return Das entsprechende Spiel
     * @see #ladeZugListe(BufferedReader, List)
     */
    public Spiel getSpiel(String name) {
        if (name.length() < 21) {
            // Die Liste aktualisieren
            gespeicherteSpiele.remove(name);
            return null;
        }
        String spielname = name.substring(0, name.length() - 20);
        // Die Quelldatei fuer das Spiel
        File file = new File("settings" + System.getProperty(
            "file.separator") + "Spiele" + System.getProperty(
                "file.separator") + spielname + ".txt");
        // Das Spiel anlegen
        Spiel spiel;
        // Wenn es ein Autosave Spiel war, muss der urspruengliche Name 
        // rausgefiltert werden
        if (spielname.contains("(autosave)")) {
            spielname = spielname.substring(0, spielname.length() - 11);
        }
        BufferedReader br = null;
        try {
            // Ein Reader um die Datei zeilenweise auslesen zu koennen
            br = new BufferedReader(new FileReader(file));
            // Hier muessen alle Zeilen ausgelesen und zugeordnet werden
            br.readLine(); // Zeit lesen lassen
            Spieler spieler1 = getSpieler(br.readLine());
            boolean farbe1 = Boolean.parseBoolean(br.readLine());
            Spieler spieler2 = getSpieler(br.readLine());
            boolean farbe2 = Boolean.parseBoolean(br.readLine());
            // Neue FelderListe wird erstellt
            List<Feld> felderListe = erstelleFelderListe();
            // Neues Spielfeld erzeugen
            Spielfeld spielfeld = new Spielfeld(felderListe, true);
            spielfeld.setSpieldaten(new Spieldaten());
            // Einem Computergegner muss das Spielfeld zugefuegt werden
            if (spieler2 instanceof Computerspieler) {
                ((Computerspieler) spieler2).setSpielfeld(spielfeld);
            }
            
            // Einstellungen laden
            // Die ZugzeitBegrenzung
            int zzb = Integer.parseInt(br.readLine());
            // Die sechs booleans
            boolean[] bool = new boolean[7];
            for (int i = 0; i <= 6; i++) {
                bool[i] = Boolean.parseBoolean(br.readLine());
            }
            einstellungen = new Einstellungen();
            einstellungen.setZugZeitBegrenzung(zzb);
            einstellungen.setMoeglicheFelderAnzeigen(bool[0]);
            einstellungen.setBedrohteFigurenAnzeigen(bool[1]);
            einstellungen.setRochadeMoeglich(bool[2]);
            einstellungen.setEnPassantMoeglich(bool[3]);
            einstellungen.setSchachWarnung(bool[4]);
            einstellungen.setInStatistikEinbeziehen(bool[5]);
            einstellungen.setSpielfeldDrehen(bool[6]);
            
            // Schachnotation wird zurueckuebersetzt
            List<Zug> zugliste = ladeZugListe(br, felderListe);
            for (Zug zug : zugliste) {
                // Ziehe jeden Zug
                spielfeld.ziehe(zug.getFigur(), zug.getZielfeld(), 
                    zug.getZugzeit());
                // Wenn es ein Umwandlungszug war
                if (zug instanceof Umwandlungszug) {
                    // Wandel die Figur entsprechend um
                    spielfeld.umwandeln(spielfeld.getSpieldaten()
                        .getLetzterZug().getFigur(), 
                        ((Umwandlungszug) zug).getNeueFigur().getWert());
                }
            }
            
            // Dem Spielfeld die Einstellungen zuf&uuml;gen
            spielfeld.setEinstellungen(einstellungen);
            
            // Den Spielern ihre Farben setzen
            spieler1.setFarbe(farbe1);
            spieler2.setFarbe(farbe2);
            
            // Das Spiel erstellen
            spiel = new Spiel(spielname, spieler1, spieler2, spielfeld);
        } catch (Exception ex) {
            // Wenn irgendwas schief geht
            spiel = null;
            // Wenn das Spiel null ist, gibt die GUI eine Fehlermeldung aus
        } finally {
            // Reader schliessen
            try {
                br.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
        
        // Die Quelldatei loeschen
        file.delete();
        // Die Liste aktualisieren
        gespeicherteSpiele.remove(name);
        
        return spiel;
    }
    
    /**
     * L&auml;dt mithilfe des &uuml;bergebenen BufferedReaders die Zugliste aus
     * der Textdatei. Dabei wird die Schachnotation in einen Zug umgewandelt.
     * @param br Ein BufferedReader der entsprechenden Textdatei
     * @param felderListe Die zugeh&ouml;hrige Liste der Felder 
     * @return Die vollst&auml;ndige Zugliste
     */
    private List<Zug> ladeZugListe(BufferedReader br, List<Feld> felderListe) {
        List<Zug> zugListe = new ArrayList<Zug>();
        // Die Farbe der aktuell ziehenden Figur (weiss beginnt)
        boolean aktuelleFarbe = true;
        try {
            // Schachnotation laden
            String line = br.readLine();
            Zug zug;
            // Solange noch eine Zeile zu laden ist
            while (line != null && !line.equals("")) {
                // Letztes Leerzeichen
                int hinteresLeerzeichen = line.lastIndexOf(" ");
                String vorne = line.substring(0, hinteresLeerzeichen);
                // Vorletztes Leerzeichen
                int vorderesLeerzeichen = vorne.lastIndexOf(" ");
                // Zugzeit steht dazwischen
                int zugzeit = Integer.parseInt(line.substring(
                    vorderesLeerzeichen + 1, hinteresLeerzeichen));
                
                Feld startfeld;
                Feld zielfeld;
                // Das erste Leerzeichen
                int stelleLeerzeichen = line.indexOf(" ");
                // Der reine Zug
                String vordererTeil = line.substring(0, stelleLeerzeichen);
                // Wenn es eine kleine Rochade ist
                if (vordererTeil.equals("0-0")) {
                    if (aktuelleFarbe) {
                        startfeld = felderListe.get(4);
                        zielfeld = felderListe.get(6);
                    } else {
                        startfeld = felderListe.get(60);
                        zielfeld = felderListe.get(62);
                    }
                    zug = new Zug(startfeld, zielfeld, false, zugzeit);
                // Wenn es eine grosse Rochade ist
                } else if (vordererTeil.equals("0-0-0")) {
                    if (aktuelleFarbe) {
                        startfeld = felderListe.get(4);
                        zielfeld = felderListe.get(2);
                    } else {
                        startfeld = felderListe.get(60);
                        zielfeld = felderListe.get(58);
                    }
                    zug = new Zug(startfeld, zielfeld, false, zugzeit);
                // Wenn es ein normaler Zug ist
                } else {
                    // Das Trennungszeichen (x oder -)
                    int stelleTrennung = line.indexOf("-");
                    // Es ist grundsaetzlich kein Schlagzug
                    boolean schlagzug = false;
                    // Wenn kein - vorhanden ist, ist es doch ein Schlagzug
                    if (stelleTrennung == -1) {
                        stelleTrennung = line.indexOf("x");
                        schlagzug = true;
                    }
                    // Spaltenbezeichnung
                    List<String> spalten = new ArrayList<String>(Arrays.asList(
                        "a", "b", "c", "d", "e", "f", "g", "h"));
                    // Das Startfeld
                    String figPos1 = vordererTeil.substring(
                        stelleTrennung - 2, stelleTrennung);
                    // Koordinaten des Startfeldes
                    int x = spalten.indexOf(figPos1.substring(0, 1));
                    int y = Integer.parseInt(figPos1.substring(1)) - 1;
                    // Das Startfeld
                    startfeld = felderListe.get(x + 8 * y);
                    // Das Zielfeld
                    String figPos2 = vordererTeil.substring(
                        stelleTrennung + 1, stelleTrennung + 3);
                    // Koordinaten des Zielfeldes
                    x = spalten.indexOf(figPos2.substring(0, 1));
                    y = Integer.parseInt(figPos2.substring(1)) - 1;
                    // Das Zielfeld
                    zielfeld = felderListe.get(x + 8 * y);
                    
                    /* Wenn hinter dem Trennungszeichen 3 Zeichen kommen, ist
                     * es ein Umwandlungszug
                     */
                    if (vordererTeil.substring(stelleTrennung + 1)
                        .length() == 3) {
                        String umgewandelteFigur = 
                            vordererTeil.substring(stelleTrennung + 3);
                        Figur neueFigur;
                        if (umgewandelteFigur.equals("S")) {
                            neueFigur = new Springer(zielfeld, aktuelleFarbe);
                        } else if (umgewandelteFigur.equals("L")) {
                            neueFigur = new Laeufer(zielfeld, aktuelleFarbe);
                        } else if (umgewandelteFigur.equals("T")) {
                            neueFigur = new Turm(zielfeld, aktuelleFarbe);
                        } else {
                            neueFigur = new Dame(zielfeld, aktuelleFarbe);
                        }
                        zug = new Umwandlungszug(startfeld, zielfeld, 
                            schlagzug, zugzeit, neueFigur);
                    } else {
                        // Normaler neuer Zug
                        zug = new Zug(startfeld, zielfeld, schlagzug, zugzeit);
                    }
                }
                // Den erzeugten Zug der Liste hinzufuegen
                zugListe.add(zug);
                // Naechste Zeile lesen
                line = br.readLine();
                aktuelleFarbe = !aktuelleFarbe;
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return zugListe;
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
            // Schreibschutz
            spielDatei.setReadOnly();
            // Die Namen der vorhandenen Spiele laden / aktualisieren
            gespeicherteSpiele.clear();
            File spieleOrdner = new File("settings" + System.getProperty(
                "file.separator") + "Spiele");
            File[] files = spieleOrdner.listFiles();
            for (File gamefile : files) {
                try {
                    BufferedReader br = new BufferedReader(
                        new FileReader(gamefile));
                    String datum = br.readLine();
                    br.close();
                    // Den Namen (Name der Datei ohne das .txt)
                    String name = gamefile.getName().substring(
                        0, gamefile.getName().length() - 4);
                    name += " " + datum;
                    gespeicherteSpiele.add(name);
                } catch (IOException ioEx) {
                    ioEx.printStackTrace();
                }
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        
        
        
        
        
    }
    
    /**
     * Speichert das angegebene Spiel f&uuml;r den Fall, dass der Computer
     * abst&uuml;rzt. <br>
     * Es kann immer nur ein Spiel gespeichert werden. Ruft man diese Methode
     * mehrfach auf, wird immer nur das letzte Spiel gespeichert.
     * @param spiel Das aktuelle Spiel was gespeichert werden soll
     */
    public void automatischesSpeichern(Spiel spiel) {
        // Die letzte automatische Speicherung loeschen, sofern vorhanden
        autosaveLoeschen();
        // Das Spiel speicher mit dem Zusatz (autosave)
        try {
            File spielDatei = new File("settings" + System.getProperty(
                "file.separator") + "Spiele" + System.getProperty(
                    "file.separator") + spiel.getSpielname() + " (autosave)" 
                        + ".txt");
            FileWriter fw = new FileWriter(spielDatei);
            fw.write(spiel.toString());
            fw.close();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        // Das Spiel der Liste zufuegen
        if (!gespeicherteSpiele.contains(spiel.getSpielname() 
            + " (autosave)")) {
            gespeicherteSpiele.add(spiel.getSpielname() + " (autosave)");
        }
        // Einstellungen und Spieler vorsorglich auch mit speichern
        speichern();
    }
    
    /**
     * L&ouml;scht automatisch gespeicherte Spiele.
     */
    public void autosaveLoeschen() {
        File ordner = new File("settings" + System.getProperty(
            "file.separator") + "Spiele");
        File[] files = ordner.listFiles();
        // Alle Dateien im Ordner durchgehen
        for (File file : files) {
            // Wenn eine Datei im Titel (autosave) enthaelt
            if (file.getName().contains("(autosave)")) {
                // Wird die Datei geloescht
                file.delete();
            }
        }
        // Neue Liste um keine CurrentModificationException zu bekommen
        List<String> zuEntfernendeSpiele = new ArrayList<String>();
        // Alle zu entfernenden Spiele bestimmen
        for (String spielname : gespeicherteSpiele) {
            if (spielname.contains("(autosave)")) {
                zuEntfernendeSpiele.add(spielname);
            }
        }
        // Alle entsprechenden Spiele aus der Liste loeschen
        gespeicherteSpiele.removeAll(zuEntfernendeSpiele);
    }
    
    /**
     * Gibt die Liste der Spieler nach absteigendem Score sortiert wieder.
     * @return Eine Liste mit den gerankten Spielern
     */
    public List<Spieler> getRanking() {
        List<Spieler> sortedList = new ArrayList<Spieler>();
        for (Spieler spieler : spielerListe) {
            sortedList.add(spieler);
        }
        return sortiereListe(sortedList);
    }
    
    /**
     * Sortiert die angegebene Liste von Spielern nach Score.<br>
     * Verwendet den rekursiven Bubble-Sort-Algorithmus.
     * @param spieler Die zu sortierende Liste von Spielern
     * @return Die sortierte Liste
     */
    private List<Spieler> sortiereListe(List<Spieler> spieler) {
        // Temporaerer Spieler
        Spieler temp;
        for (int i = 0; i < spieler.size() - 1; i++) {
            // Wenn zwei Spieler in der falschen Reihenfolge stehen
            if (spieler.get(i).getStatistik().getScore() 
                < spieler.get(i + 1).getStatistik().getScore()) {
                // Werden sie getauscht
                temp = spieler.get(i);
                spieler.set(i, spieler.get(i + 1));
                spieler.set(i + 1, temp);
                // Rekursiver Aufruf
                sortiereListe(spieler);
            }
        }
        return spieler;
    }
    
    /**
     * Gibt die Liste der Spieler zur&uuml;ck.
     * @return Liste der Spieler
     */
    public List<Spieler> getSpielerListe() {
        List<Spieler> spielerSorted = new ArrayList<Spieler>();
        // Alle Spieler kopieren
        spielerSorted.addAll(spielerListe);
        // Alle menschlichen Spieler entfernen
        spielerSorted.removeAll(getMenschlicheSpieler());
        // Und hinten wieder anhaengen
        spielerSorted.addAll(getMenschlicheSpieler());
        return spielerSorted;
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
     * F&uuml;gt einen Spieler hinzu.
     * @param spieler Der Spieler der zugef&uuml;gt werden soll.
     */
    public void addSpieler(Spieler spieler) {
        spielerListe.add(spieler);
    }
    
    /**
     * Gibt die Liste der Namen der Spiele zur&uuml;ck.
     * @return Liste der Namen der Spiele
     */
    public List<String> getSpieleListe() {
        return gespeicherteSpiele;
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
