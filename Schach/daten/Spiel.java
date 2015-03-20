package daten;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import zuege.Zug;

/**
 * Verwaltet alle wichtigen Daten f&uuml;r ein Spiel. <br>
 * Ein Spiel besteht aus einem Namen, den beteiligten Spielern, dem 
 * zugehoerigen Spielfeld, sowie s&auml;mtlichen ben&ouml;tigten Daten und 
 * Einstellungen.
 * @author Christian Ackermann
 */
public class Spiel {

    /**
     * Der Name des Spiels. <br>
     * Wird beim Laden eines Spiels ben&ouml;tigt.
     */
    private String spielname;
    
    /**
     * Der erste Spieler.
     */
    private Spieler spieler1;
    
    /**
     * Der zweite Spieler.
     */
    private Spieler spieler2;
    
    /**
     * Das zugeh&ouml;rige Spielfeld. <br>
     * Stellt die Figurenanordnung auf dem Brett
     * zur Verf&uuml;gung.
     */
    private Spielfeld spielfeld;
    
    /**
     * Legt ein neues Spiel an, welches gespielt und sp&auml;ter gespeichert
     * werden kann. <br>
     * Einziger Konstruktor dieser Klasse.
     * @param spielname Name des Spiels. Ben&ouml;tigt f&uuml;r die eindeutige
     * Zuordnung beim Laden
     * @param spieler1 Der eine teilnehmende Spieler
     * @param spieler2 Der andere Spieler
     * @param spielfeld Das zugeh&ouml;rige Spielfeld
     */
    public Spiel(String spielname, Spieler spieler1, Spieler spieler2,
        Spielfeld spielfeld) {
        this.spielname = spielname;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
        this.spielfeld = spielfeld;
    }
    
    /**
     * Wertet das Spiel aus und ermittelt das Ergebnis und den Sieger und 
     * aktualisiert die Statistik.<br>
     * Wird aus der GUI aufgerufen und &uuml;bergibt dieser eine Liste mit den
     * ermittelten Daten.
     * @return Eine Liste vom Typ Object mit den wichtigen Informationen zum 
     * Spielausgang
     * <ul>
     * <li>Der Gewinner des Spiels vom Typ Spieler</li>
     * <li>Ob es Matt oder Patt ausgegangen ist (<b>true</b> f&uuml;r Matt)</li>
     * <li>Die Anzahl der Z&uuml;ge nach denen dieses Spiel beendet wurde</li>
     * </ul>
     * 
     */
    public List<Object> auswertung() {
        List<Object> ergebnis = new ArrayList<Object>();
        Spieler gewinner;
        Spieler verlierer;
        // Der Gewinner
        // Der aktuelle Spieler hat verloren
        if (spieler1.getFarbe() != spielfeld.getAktuellerSpieler()) {
            gewinner = spieler1;
            verlierer = spieler2;
        } else {
            gewinner = spieler2;
            verlierer = spieler1;
        }
        ergebnis.add(gewinner);
        
        // Matt oder Patt
        ergebnis.add(spielfeld.isSchach());
        
        // Anzahl Zuege
        // AnzahlZuege von dem nicht aktiven Spieler, da dieser gewonnen hat
        ergebnis.add(spielfeld.getSpieldaten()
            .getAnzahlZuege(!spielfeld.getAktuellerSpieler()));
        
        // Statistik aktualisieren
        statistik(spielfeld.isSchach(), gewinner, verlierer);
        
        return ergebnis;
    }
    
    /**
     * Verarbeitet das Aufgeben eines Spielers, aktualisiert die Statistik
     * und meldet alle wichtigen Daten an die GUI. <br>
     * Wenn die entsprechende Zusatzoption aktiviert ist, wird diese Methode
     * ebenfalls bei Ablauf der Bedenkzeit aufgerufen. Ein Aufgeben wird wie
     * ein Matt gewertet.
     * @param spieler Die Spielfarbe des aufgebenden Spielers bzw. die 
     * Spielfarbe des Spielers, dessen Bedenkzeit &uuml;berschritten wurde
     * @return Eine Liste vom Typ Object mit den wichtigen Informationen zum
     * Aufgeben
     */
    public List<Object> aufgeben(boolean spieler) {
        // ErgebnisListe erstellen
        List<Object> ergebnis = new ArrayList<Object>();
        Spieler gewinner;
        Spieler verlierer;
        // Der Verlierer
        if (spieler1.getFarbe() == spieler) {
            verlierer = spieler1;
            gewinner = spieler2;
        } else {
            verlierer = spieler2;
            gewinner = spieler1;
        }
        ergebnis.add(verlierer);
        
        // Anzahl Zuege
        ergebnis.add(spielfeld.getSpieldaten().getAnzahlZuege(spieler));
        
        // Der Gewinner
        ergebnis.add(gewinner);
        
        // Statistik-Auswertung aufrufen
        if (spielfeld.getEinstellungen().isInStatistikEinbeziehen()) {
            statistik(true, gewinner, verlierer);
        }
        
        return ergebnis;
    }
    
    /**
     * Wertet eine Einigung auf Unentschieden zwischen den Spielern aus.<br>
     * Dabei wird Ausschlie&szlig;lich die Statistik aktualisiert.
     */
    public void unentschieden() {
        if (spielfeld.getEinstellungen().isInStatistikEinbeziehen()) {
            statistik(false, spieler1, spieler2);
        }
    }
    
    /**
     * Aktualisiert die Statistiken der beiden Spieler.
     * @param matt Ob es einen Sieger gibt
     * @param gewinner Der Spieler der den letzten Zug gemacht hat
     * @param verlierer Der Spieler der nicht mehr ziehen konnte
     */
    private void statistik(boolean matt, Spieler gewinner, Spieler verlierer) {
        // Wenn es einen Sieger gibt
        if (matt) {
            // Wenn es einen Computergegner gab
            if (verlierer instanceof Computerspieler) {
                gewinner.getStatistik().setAnzahlSiegeC(
                    gewinner.getStatistik().getAnzahlSiegeC() + 1);
            }
            statistikSieg(gewinner);
            
            // Wenn es einen Computergegner gab
            if (gewinner instanceof Computerspieler) {
                verlierer.getStatistik().setAnzahlMattC(
                    verlierer.getStatistik().getAnzahlMattC() + 1);
            }
            statistikMatt(verlierer);
        // Wenn es ein Patt ist
        } else {     
            // Die Statistik des Patt setzenden
            Statistik stat = gewinner.getStatistik();
            stat.setAnzahlPatt(stat.getAnzahlPatt() + 1);
            
            // Wenn es einen Computergegner gab
            if (verlierer instanceof Computerspieler) {
                stat.setAnzahlPattC(stat.getAnzahlPattC() + 1);
            }
            
            // Die Statistik des Patt gesetzten 
            stat = verlierer.getStatistik();
            stat.setAnzahlPatt(stat.getAnzahlPatt() + 1);
            
            // Wenn es einen Computergegner gab
            if (gewinner instanceof Computerspieler) {
                stat.setAnzahlPattC(stat.getAnzahlPattC() + 1);
            }
        }
    }
    
    /**
     * Aus Platz-Gr&uuml;nden ausgelagerte Methode zum Aktualisieren der 
     * Gewinner-Statistik.
     * @param gewinner Der Spieler der gewonnen hat
     */
    private void statistikSieg(Spieler gewinner) {
     // Die Statistik des Gewinners
        Statistik stat = gewinner.getStatistik();
        int zuege = spielfeld.getSpieldaten().getAnzahlZuege(
            gewinner.getFarbe());
        int zugzeit = spielfeld.getSpieldaten().getZugzeit(
            gewinner.getFarbe());
        // Erhoehe die Anzahl der Siege um 1
        stat.setAnzahlSiege(stat.getAnzahlSiege() + 1);
        
        // Wenn es weniger Zuege waren als beim Rekord
        if (zuege < stat.getKuerzesterSieg()) {
            // Muss das geaendert werden
            stat.setKuerzesterSieg(zuege);
        }
        
        // Wenn es noch gar keinen Rekord gibt
        if (stat.getKuerzesterSieg() == -1) {
            stat.setKuerzesterSieg(zuege);
        }
        
        // Wenn es weniger Zeit war als beim Rekord
        if (zugzeit < stat.getSchnellsterSieg()) {
            // Muss das geaendert werden
            stat.setSchnellsterSieg(zugzeit);
        }
        
        // Wenn es noch gar keinen Rekord gibt
        if (stat.getSchnellsterSieg() == -1) {
            stat.setSchnellsterSieg(zugzeit);
        }
        
        // Durchschnittliche Anzahl Zuege bis zum Sieg aktualisieren
        // durchschnittliche Anzahl bisher
        int zuegeDurchschnitt = stat.getZuegeSiegDurchschnitt();
        // Absoluter Wert (multipliziert mit Anzahl bisheriger Siege)
        zuegeDurchschnitt *= stat.getAnzahlSiege() - 1;
        // Neuer absoluter Wert
        zuegeDurchschnitt += zuege;
        // Neuer Durchschnittswert
        zuegeDurchschnitt /= stat.getAnzahlSiege();
        // In der Statistik aendern
        stat.setZuegeSiegDurchschnitt(zuegeDurchschnitt);
        
        // Durchschnittliche Zeit bis zum Sieg aktualisieren
        // durchschnittliche Anzahl bisher
        int zeitDurchschnitt = stat.getZeitSiegDurchschnitt();
        // Absoluter Wert (multipliziert mit Anzahl bisheriger Siege)
        zeitDurchschnitt *= stat.getAnzahlSiege() - 1;
        // Neuer absoluter Wert
        zeitDurchschnitt += zugzeit;
        // Neuer Durchschnittswert
        zeitDurchschnitt /= stat.getAnzahlSiege();
        // In der Statistik aendern
        stat.setZeitSiegDurchschnitt(zeitDurchschnitt);
        
        // Durchschnittlichen Materialwert aktualisieren
        // durchschnittlicher Wert bisher
        int matWertDurchschnitt = stat.getMatWertSiegDurchschnitt();
        // Absoluter Wert (multipliziert mit Anzahl bisheriger Siege)
        matWertDurchschnitt *= stat.getAnzahlSiege() - 1;
        // Neuer absoluter Wert
        matWertDurchschnitt += spielfeld.getMaterialwert(
            gewinner.getFarbe());
        // Neuer Durchschnittswert
        matWertDurchschnitt /= stat.getAnzahlSiege();
        // In der Statistik aendern
        stat.setMatWertSiegDurchschnitt(matWertDurchschnitt);
        
    }
    
    /**
     * Aus Platz-Gr&uuml;nden ausgelagerte Methode zum Aktualisieren der 
     * Verlierer-Statistik.
     * @param verlierer Der Spieler der verloren hat
     */
    private void statistikMatt(Spieler verlierer) {
        // Die Statistik des Verlierers
        Statistik stat = verlierer.getStatistik();
        int zuege = spielfeld.getSpieldaten().getAnzahlZuege(
            verlierer.getFarbe());
        int zugzeit = spielfeld.getSpieldaten().getZugzeit(
            verlierer.getFarbe());
        // Erhoehe die Anzahl der Matts um 1
        stat.setAnzahlMatt(stat.getAnzahlMatt() + 1);
        
        // Wenn es weniger Zuege waren als beim Rekord
        if (zuege < stat.getKuerzestesMatt()) {
            // Muss das geaendert werden
            stat.setKuerzestesMatt(zuege);
        }
        
        // Wenn es noch gar keinen Rekord gibt
        if (stat.getKuerzestesMatt() == -1) {
            stat.setKuerzestesMatt(zuege);
        }
        
        // Wenn es weniger Zeit war als beim Rekord
        if (zugzeit < stat.getSchnellstesMatt()) {
            // Muss das geaendert werden
            stat.setSchnellstesMatt(zugzeit);
        }
        
        // Wenn es noch gar keinen Rekord gibt
        if (stat.getSchnellstesMatt() == -1) {
            stat.setSchnellstesMatt(zugzeit);
        }

        // Durchschnittliche Anzahl Zuege bis zum Matt aktualisieren
        // durchschnittliche Anzahl bisher
        int zuegeDurchschnitt = stat.getZuegeMattDurchschnitt();
        // Absoluter Wert (multipliziert mit Anzahl bisheriger Matts)
        zuegeDurchschnitt *= stat.getAnzahlMatt() - 1;
        // Neuer absoluter Wert
        zuegeDurchschnitt += zuege;
        // Neuer Durchschnittswert
        zuegeDurchschnitt /= stat.getAnzahlMatt();
        // In der Statistik aendern
        stat.setZuegeMattDurchschnitt(zuegeDurchschnitt);
        
        // Durchschnittliche Zeit bis zum Matt aktualisieren
        // durchschnittliche Anzahl bisher
        int zeitDurchschnitt = stat.getZeitMattDurchschnitt();
        // Absoluter Wert (multipliziert mit Anzahl bisheriger Matts)
        zeitDurchschnitt *= stat.getAnzahlMatt() - 1;
        // Neuer absoluter Wert
        zeitDurchschnitt += zugzeit;
        // Neuer Durchschnittswert
        zeitDurchschnitt /= stat.getAnzahlMatt();
        // In der Statistik aendern
        stat.setZeitMattDurchschnitt(zeitDurchschnitt);
        
        // Durchschnittlichen Materialwert aktualisieren
        // durchschnittlicher Wert bisher
        int matWertDurchschnitt = stat.getMatWertMattDurchschnitt();
        // Absoluter Wert (multipliziert mit Anzahl bisheriger Matts)
        matWertDurchschnitt *= stat.getAnzahlMatt() - 1;
        // Neuer absoluter Wert
        matWertDurchschnitt += spielfeld.getMaterialwert(
            verlierer.getFarbe());
        // Neuer Durchschnittswert
        matWertDurchschnitt /= stat.getAnzahlMatt();
        // In der Statistik aendern
        stat.setMatWertMattDurchschnitt(matWertDurchschnitt);
    }
    
    /**
     * Bereitet die Spielwiederholung vor. Macht alle bisherigen Z&uuml;ge 
     * r&uuml;ckg&auml;ngig und gibt der aufrufenden Methode die ZugListe
     * zur&uuml;ck, damit diese jeden Zug in einem gewissen Zeitintervall
     * ziehen kann.
     * @return Die Liste aller Z&uuml;ge
     */
    public List<Zug> spielvideo() {
        List<Zug> zugListe = new ArrayList<Zug>();
        zugListe.addAll(spielfeld.getSpieldaten().getZugListe());
        for (int i = 0; i < zugListe.size(); i++) {
            spielfeld.zugRueckgaengig();
        }
        return zugListe;
    }
    
    /**
     * Gibt eine mehrzeilige Zeichenkette mit allen wichtigen Daten des Spiels 
     * zur&uuml;ck. Diese wird zum Speichern ben&ouml;tigt.
     * @return Eine mehrzeilige Zeichenkette mit allen wichtigen Daten
     */
    public String toString() {
        String string;
        String lineSep = System.getProperty("line.separator");
        // Aktuelles Datum und Uhrzeit
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        string = sdf.format(time) + lineSep;
        string += spieler1.getName() + lineSep;
        string += spieler1.getFarbe() + lineSep;
        string += spieler2.getName() + lineSep;
        string += spieler2.getFarbe() + lineSep;
        string += spielfeld.toString();
        return string;
    }
    
    /**
     * Gibt den Namen des Spiels zur&uuml;ck.
     * @return Der Name als String
     */
    public String getSpielname() {
        return spielname;
    }

    /**
     * Gibt den ersten Spieler zur&uuml;ck.
     * @return Der erste Spieler
     */
    public Spieler getSpieler1() {
        return spieler1;
    }
    
    /**
     * Gibt den zweiten Spieler zur&uuml;ck.
     * @return Der zweite Spieler
     */
    public Spieler getSpieler2() {
        return spieler2;
    }
    
    /**
     * Gibt das Spielfeld zur&uuml;ck.
     * @return Das zugeh&ouml;rige Spielfeld
     */
    public Spielfeld getSpielfeld() {
        return spielfeld;
    }
    
}
