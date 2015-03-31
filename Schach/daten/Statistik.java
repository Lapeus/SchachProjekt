package daten;

/**
 * Verwaltet alle statistischen Daten eines Spielers und berechnet den Score
 * des Spielers.
 * @author Christian Ackermann
 */
public class Statistik { 
    
    /**
     * Anzahl der bereits gespielten Spiele.
     */
    private int anzahlSpiele = 0;
    
    /**
     * Anzahl der Siege.
     */
    private int anzahlSiege = 0;
    
    /**
     * Anzahl der Patts.
     */
    private int anzahlPatt = 0;
    
    /**
     * Anzahl der Matts durch den Gegner.
     */
    private int anzahlMatt = 0;
    
    /**
     * Anzahl der Siege gegen den Computer.
     */
    private int anzahlSiegeC = 0;
    
    /**
     * Anzahl der Patts gegen den Computer.
     */
    private int anzahlPattC = 0;
    
    /**
     * Anzahl der Matts durch den Computer.
     */
    private int anzahlMattC = 0;
    
    /**
     * Zeit bis zum schnellsten Sieg.
     */
    private int schnellsterSieg = -1;
    
    /**
     * Anzahl Z&uuml;ge bis zum schnellsten Sieg.
     */
    private int kuerzesterSieg = -1;
    
    /**
     * Zeit bis zum Schnellsten Matt.
     */
    private int schnellstesMatt = -1;
    
    /**
     * Anzahl Z&uuml;ge bis zum schnellsten Matt.
     */
    private int kuerzestesMatt = -1;
    
    /**
     * Durchschnittlich ben&ouml;tigte Zeit f&uuml;r einen Sieg.
     */
    private int zeitSiegDurchschnitt = -1;
    
    /**
     * Durchschnittlich ben&ouml;tigte Zeit bis zum Matt.
     */
    private int zeitMattDurchschnitt = -1;
    
    /**
     * Durchschnittliche Anzahl an Z&uuml;gen bis zum Sieg.
     */
    private int zuegeSiegDurchschnitt = -1;
    
    /**
     * Durchschnittliche Anzahl an Z&uuml;gen bis zum Matt.
     */
    private int zuegeMattDurchschnitt = -1;
    
    /**
     * Durchschnittlicher Materialwert beim Sieg.
     */
    private int matWertSiegDurchschnitt = -1;
    
    /**
     * Durchschnittlicher Materialwert beim Matt.
     */
    private int matWertMattDurchschnitt = -1;
    
    /**
     * Der Score des Spielers.
     */
    private int score = 1;
    
    /**
     * Der Score des letzten Spiels.
     */
    private int scoreLastGame = 0;
    
    /**
     * Erstellt eine neue Statistik f&uuml;r den Spieler.<br>
     * &Uuml;blicher Konstruktor der Klasse. Aufruf in der Regel beim Erstellen
     * eines neuen Spielers.
     */
    public Statistik() {
        
    }
    
    /**
     * Erstellt eine neue Statistik f&uuml;r den Spieler anhand der 
     * &uuml;bergebenen Attribute.<br>
     * Aufruf in der Regel beim Laden der Statistik eines Spielers im 
     * Gesamtdatensatz.
     * @param stat Ein Integer-Array mit allen Statistik-Werten
     */
    public Statistik(int[] stat) {
        this.anzahlSiege = stat[0];
        this.score = stat[1];
        this.anzahlPatt = stat[2];
        this.anzahlMatt = stat[3];
        this.anzahlSiegeC = stat[4];
        this.anzahlPattC = stat[5];
        this.anzahlMattC = stat[6];
        this.schnellsterSieg = stat[7];
        this.kuerzesterSieg = stat[8];
        this.schnellstesMatt = stat[9];
        this.kuerzestesMatt = stat[10];
        this.zeitSiegDurchschnitt = stat[11];
        this.zeitMattDurchschnitt = stat[12];
        this.zuegeSiegDurchschnitt = stat[13];
        this.zuegeMattDurchschnitt = stat[14];
        this.matWertSiegDurchschnitt = stat[15];
        this.matWertMattDurchschnitt = stat[16];
    }
    
    /**
     * Berechnet den Score des Spielers anhand der gespeicherten Daten. <br>
     * @return Ganzzahliger Score zwischen 0 und 1000
     */
    public int getScore() {
        // Anzahl der gespielten Partien
        int anzahlSpiele = getAnzahlSpiele();
        if (scoreLastGame != 0) {
            int gesamtScore = score * (anzahlSpiele - 1) + scoreLastGame;
            score = gesamtScore / anzahlSpiele;
            scoreLastGame = 0;
        }
        return score;
    }
    
    /**
     * Liefert eine Zeichenkette mit allen wichtigen Daten zur&uuml;ck. <br>
     * Wird f&uuml;r das Speichern des Gesamtdatensatzes verwendet. Dabei 
     * wird jedes Attribut in eine eigene Zeile geschrieben.
     * @return Eine mehrzeilige Zeichenkette
     */
    public String toString() {
        String string;
        String lineSep = System.getProperty("line.separator");
        string = anzahlSiege + lineSep;
        string += score + lineSep;
        string += anzahlPatt + lineSep;
        string += anzahlMatt + lineSep;
        string += anzahlSiegeC + lineSep;
        string += anzahlPattC + lineSep;
        string += anzahlMattC + lineSep;
        string += schnellsterSieg + lineSep;
        string += kuerzesterSieg + lineSep;
        string += schnellstesMatt + lineSep;
        string += kuerzestesMatt + lineSep;
        string += zeitSiegDurchschnitt + lineSep;
        string += zeitMattDurchschnitt + lineSep;
        string += zuegeSiegDurchschnitt + lineSep;
        string += zuegeMattDurchschnitt + lineSep;
        string += matWertSiegDurchschnitt + lineSep;
        string += matWertMattDurchschnitt; 
        
        return string;
    }
    
    /**
     * Gibt die Anzahl der gesamten Spiele zur&uuml;ck.
     * @return Die Anzahl der gespielten Spiele
     */
    public int getAnzahlSpiele() {
        anzahlSpiele = anzahlSiege + anzahlPatt + anzahlMatt;
        return anzahlSpiele;
    }
    
    /**
     * Gibt die Anzahl der Siege zur&uuml;ck.
     * @return Die Anzahl der Siege
     */
    public int getAnzahlSiege() {
        return anzahlSiege;
    }
    
    /**
     * Setzt die Anzahl der Siege.
     * @param anzahlSiege Anzahl der Siege
     */
    public void setAnzahlSiege(int anzahlSiege) {
        this.anzahlSiege = anzahlSiege;
    }
    
    /**
     * Gibt die Anzahl der Patt-Situationen zur&uuml;ck.
     * @return Anzahl Patt
     */
    public int getAnzahlPatt() {
        return anzahlPatt;
    }
    
    /**
     * Setzt die Anzahl der Patt-Situationen.
     * @param anzahlPatt Anzahl Patt
     */
    public void setAnzahlPatt(int anzahlPatt) {
        this.anzahlPatt = anzahlPatt;
    }
    
    /**
     * Gibt die Anzahl der Matt-Setzungen durch den Gegner zur&uuml;ck.
     * @return Anzahl der Matts durch Gegner
     */
    public int getAnzahlMatt() {
        return anzahlMatt;
    }
    
    /**
     * Setzt die Anzahl der Matt-Setzungen durch den Gegner.
     * @param anzahlMatt Anzahl der Matts durch Gegner
     */
    public void setAnzahlMatt(int anzahlMatt) {
        this.anzahlMatt = anzahlMatt;
    }
    
    /**
     * Gibt die Anzahl der Siege gegen den Computer zur&uuml;ck.
     * @return Anzahl Siege gegen Computer
     */
    public int getAnzahlSiegeC() {
        return anzahlSiegeC;
    }
    
    /**
     * Setzt die Anzahl der Siege gegen den Computer.
     * @param anzahlSiegeC Anzahl Siege gegen Computer
     */
    public void setAnzahlSiegeC(int anzahlSiegeC) {
        this.anzahlSiegeC = anzahlSiegeC;
    }
    
    /**
     * Gibt die Anzahl der Patt-Situationen gegen den Computer zur&uuml;ck.
     * @return Anzahl Patt gegen Computer
     */
    public int getAnzahlPattC() {
        return anzahlPattC;
    }
    
    /**
     * Setzt die Anzahl der Patt-Situationen gegen den Computer.
     * @param anzahlPattC Anzahl Patt gegen Computer
     */
    public void setAnzahlPattC(int anzahlPattC) {
        this.anzahlPattC = anzahlPattC;
    }
    
    /**
     * Gibt die Anzahl der Matt-Setzungen durch den Computer zur&uuml;ck.
     * @return Anzahl Matt durch Computer
     */
    public int getAnzahlMattC() {
        return anzahlMattC;
    }
    
    /**
     * Setzt die Anzahl der Matt-Setzungen durch den Computer.
     * @param anzahlMattC Anzahl Matt durch Computer
     */
    public void setAnzahlMattC(int anzahlMattC) {
        this.anzahlMattC = anzahlMattC;
    }
    
    /**
     * Gibt die Zeit bis zum schnellsten Sieg zur&uuml;ck.
     * @return Zeit in Sekunden
     */
    public int getSchnellsterSieg() {
        return schnellsterSieg;
    }
    
    /**
     * Setzt die Zeit bis zum schnellsten Sieg.
     * @param schnellsterSieg Zeit in Sekunden
     */
    public void setSchnellsterSieg(int schnellsterSieg) {
        this.schnellsterSieg = schnellsterSieg;
    }
    
    /**
     * Gibt die Anzahl der Z&uuml;ge bis zum schnellsten Sieg zur&uuml;ck.
     * @return Anzahl Z&uuml;ge
     */
    public int getKuerzesterSieg() {
        return kuerzesterSieg;
    }
    
    /**
     * Setzt die Anzahl der Z&uuml;ge bis zum schnellsten Sieg.
     * @param kuerzesterSieg Anzahl Z&uuml;ge
     */
    public void setKuerzesterSieg(int kuerzesterSieg) {
        this.kuerzesterSieg = kuerzesterSieg;
    }
    
    /**
     * Gibt die Zeit bis zum schnellsten Matt durch den Gegner zur&uuml;ck.
     * @return Zeit in Sekunden
     */
    public int getSchnellstesMatt() {
        return schnellstesMatt;
    }
    
    /**
     * Setzt die Zeit bis zum schnellsten Matt durch den Gegner.
     * @param schnellstesMatt Zeit in Sekunden
     */
    public void setSchnellstesMatt(int schnellstesMatt) {
        this.schnellstesMatt = schnellstesMatt;
    }
    
    /**
     * Gibt die Anzahl der Z&uuml;ge bis zum schnellsten Matt durch den Gegner
     * zur&uuml;ck.
     * @return Anzahl Z&uuml;ge
     */
    public int getKuerzestesMatt() {
        return kuerzestesMatt;
    }
    
    /**
     * Setzt die Anzahl der Z&uuml;ge bis zum schnellsten Matt durch den Gegner.
     * @param kuerzestesMatt Anzahl Z&uuml;ge
     */
    public void setKuerzestesMatt(int kuerzestesMatt) {
        this.kuerzestesMatt = kuerzestesMatt;
    }
    
    /**
     * Gibt die durchschnittliche Zeit bis zum Sieg zur&uuml;ck.
     * @return Durchschnittliche Zeit in Sekunden
     */
    public int getZeitSiegDurchschnitt() {
        return zeitSiegDurchschnitt;
    }
    
    /**
     * Setzt die durchschnittliche Zeit bis zum Sieg.
     * @param zeitSiegDurchschnitt Durchschnittliche Zeit in Sekunden
     */
    public void setZeitSiegDurchschnitt(int zeitSiegDurchschnitt) {
        this.zeitSiegDurchschnitt = zeitSiegDurchschnitt;
    }
    
    /**
     * Gibt die durchschnittliche Zeit bis zum Matt durch den Gegner 
     * zur&uuml;ck.
     * @return Durchschnittliche Zeit in Sekunden
     */
    public int getZeitMattDurchschnitt() {
        return zeitMattDurchschnitt;
    }
    
    /**
     * Setzt die durchschnittliche Zeit bis zum Matt durch den Gegner.
     * @param zeitMattDurchschnitt Durchschnittliche Zeit in Sekunden
     */
    public void setZeitMattDurchschnitt(int zeitMattDurchschnitt) {
        this.zeitMattDurchschnitt = zeitMattDurchschnitt;
    }
    
    /**
     * Gibt die durchschnittliche Anzahl der Z&uuml;ge bis zum Sieg zur&uuml;ck.
     * @return zuegeSiegDurchschnitt Durchschnittliche Anzahl der Z&uuml;ge
     */
    public int getZuegeSiegDurchschnitt() {
        return zuegeSiegDurchschnitt;
    }
    
    /**
     * Setzt die durchschnittliche Anzahl der Z&uuml;ge bis zum Sieg.
     * @param zuegeSiegDurchschnitt Durchschnittliche Anzahl der Z&uuml;ge
     */
    public void setZuegeSiegDurchschnitt(int zuegeSiegDurchschnitt) {
        this.zuegeSiegDurchschnitt = zuegeSiegDurchschnitt;
    }
    
    /**
     * Gibt die durchschnittliche Anzahl der Z&uuml;ge bis zum Matt durch den
     * Gegner zur&uuml;ck.
     * @return zuegeMattDurchschnitt Durchschnittliche Anzahl der Z&uuml;ge
     */
    public int getZuegeMattDurchschnitt() {
        return zuegeMattDurchschnitt;
    }
    
    /**
     * Setzt die durschnittliche Anzahl der Z&uuml;ge bis zum Matt durch den 
     * Gegner.
     * @param zuegeMattDurchschnitt Durchschnittliche Anzahl der Z&uuml;ge
     */
    public void setZuegeMattDurchschnitt(int zuegeMattDurchschnitt) {
        this.zuegeMattDurchschnitt = zuegeMattDurchschnitt;
    }
    
    /**
     * Gibt den durchschnittlichen Materialwert bei Sieg zur&uuml;ck.
     * @return matWertSiegDurchschnitt Durchschnittlicher Materialwert
     */
    public int getMatWertSiegDurchschnitt() {
        return matWertSiegDurchschnitt;
    }
    
    /**
     * Setzt den durchschnittlichen Materialwert bei Sieg.
     * @param matWertSiegDurchschnitt Durchschnittlicher Materialwert
     */
    public void setMatWertSiegDurchschnitt(int matWertSiegDurchschnitt) {
        this.matWertSiegDurchschnitt = matWertSiegDurchschnitt;
    }
    
    
    /**
     * Gibt den durchschnittlichen Materialwert bei Matt durch den Gegner
     * zur&uuml;ck.
     * @return matWertMattDurchschnitt Durchschnittlicher Materialwert
     */
    public int getMatWertMattDurchschnitt() {
        return matWertMattDurchschnitt;
    }
    
    /**
     * Setzt den durchschnittlichen Materialwert bei Matt durch den Gegner.
     * @param matWertMattDurchschnitt Durchschnittlicher Materialwert
     */
    public void setMatWertMattDurchschnitt(int matWertMattDurchschnitt) {
        this.matWertMattDurchschnitt = matWertMattDurchschnitt;
    }

    /**
     * Setzt den Score dieses Spielers. Wird nur beim Erstellen von neuen
     * Spielern verwendet um nicht bei 0 starten zu m&uuml;ssen.
     * @param score Der Score der gesetzt werden soll
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * Setzt den Score des letzten Spieles.
     * @param scoreLastGame Punktzahl des letzten Spiels
     */
    public void setScoreLastGame(int scoreLastGame) {
        this.scoreLastGame = scoreLastGame;
    }
    
    
}
