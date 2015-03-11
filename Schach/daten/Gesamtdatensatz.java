package daten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import figuren.Bauer;
import figuren.Dame;
import figuren.Figur;
import figuren.Koenig;
import figuren.Laeufer;
import figuren.Springer;
import figuren.Turm;
import gui.Feld;
import zuege.Umwandlungszug;
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
     * L&auml;dt - sofern vorhanden - alle ben&ouml;tigten Daten aus dem
     * ausf&uuml;hrenden Ordner.
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
            /* Wenn irgendwas beim Laden schief geht, werden die Standard-
             * Einstellungen wiederhergestellt.
             */
            einstellungen = new Einstellungen(0, true, false, true, false, 
                true, true);
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
                // Wenn das schief geht, wird der Spieler neu erzeugt
                spielerListe.add(new Spieler(name));
                // Und die fehlerhafte Datei geloescht
                spielerFile.delete();
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
         * Zugzeit-Begrenzung: Nicht vorhanden (0)
         * Moegliche Felder anzeigen: True
         * Bedrohte Felder anzeigen: False
         * Rochade moeglich: True
         * En Passant moeglich: False
         * Schachwarnung: True
         * Statistik: True
         */
        einstellungen = new Einstellungen(0, true, false, true, false, 
            true, true);
        spielerListe.add(new Computerspieler("Walter"));
        spielerListe.add(new Computerspieler("Karl Heinz"));
        spielerListe.add(new Computerspieler("Rosalinde"));
        spielerListe.add(new Computerspieler("Ursula"));
        
        
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
        // Wenn es ein Autosave Spiel war, muss der urspruengliche Name 
        // rausgefiltert werden
        if (name.contains("(autosave)")) {
            name = name.substring(0, name.length() - 11);
        }
        try {
            // Ein Reader um die Datei zeilenweise auslesen zu koennen
            BufferedReader br = new BufferedReader(new FileReader(file));
            // Hier muessen alle Zeilen ausgelesen und zugeordnet werden
            String time = br.readLine();
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
            // Wenn irgendwas schief geht
            // TODO Fehlermeldung ausgeben
            System.out.println("Soll kein CheckstyleFehler kommen");
        }
        
        // Die Quelldatei loeschen
        file.delete();
        // Die Liste aktualisieren
        gespeicherteSpiele.remove(name);
        
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
        // Die Farbe der aktuell ziehenden Figur (weiss beginnt)
        boolean aktuelleFarbe = true;
        try {
            // Schachnotation laden
            String line = br.readLine();
            Zug zug;
            // Solange noch eine Zeile zu laden ist
            while (!line.equals("")) {
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
                // Wenn es eine kleine Rochade ist
                if (line.equals("0-0")) {
                    if (aktuelleFarbe) {
                        startfeld = felderListe.get(4);
                        zielfeld = felderListe.get(6);
                    } else {
                        startfeld = felderListe.get(60);
                        zielfeld = felderListe.get(62);
                    }
                    zug = new Zug(startfeld, zielfeld, false, zugzeit);
                // Wenn es eine grosse Rochade ist
                } else if (line.equals("0-0-0")) {
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
                    // Das erste Leerzeichen
                    int stelleLeerzeichen = line.indexOf(" ");
                    // Der reine Zug
                    String vordererTeil = line.substring(0, stelleLeerzeichen);
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
            // Schreibschutz
            spielDatei.setReadOnly();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        if (!gespeicherteSpiele.contains(spiel.getSpielname())) {
            gespeicherteSpiele.add(spiel.getSpielname());
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
        for (File file : files) {
            if (file.getName().contains("autosave")) {
                file.delete();
            }
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
