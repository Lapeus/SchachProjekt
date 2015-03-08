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
    private int schnellsterSieg = 0;
    
    /**
     * Anzahl Z&uuml;ge bis zum schnellsten Sieg.
     */
    private int kuerzesterSieg = 0;
    
    /**
     * Zeit bis zum Schnellsten Matt.
     */
    private int schnellstesMatt = 0;
    
    /**
     * Anzahl Z&uuml;ge bis zum schnellsten Matt.
     */
    private int kuerzestesMatt = 0;
    
    /**
     * Durchschnittlich ben&ouml;tigte Zeit f&uuml;r einen Sieg.
     */
    private int zeitSiegDurchschnitt = 0;
    
    /**
     * Durchschnittlich ben&ouml;tigte Zeit bis zum Matt.
     */
    private int zeitMattDurchschnitt = 0;
    
    /**
     * Durchschnittliche Anzahl an Z&uuml;gen bis zum Sieg.
     */
    private int zuegeSiegDurchschnitt = 0;
    
    /**
     * Durchschnittliche Anzahl an Z&uuml;gen bis zum Matt.
     */
    private int zuegeMattDurchschnitt = 0;
    
    /**
     * Durchschnittlicher Materialwert beim Sieg.
     */
    private int matWertSiegDurchschnitt = 0;
    
    /**
     * Durchschnittlicher Materialwert beim Matt.
     */
    private int matWertMattDurchschnitt = 0;
    
    /**
     * Erstellt eine neue Statistik f&uuml;r den Spieler.<br>
     * &Uuml;blicher Konstruktor der Klasse. Aufruf in der Regel beim Erstellen
     * eines neuen Spielers.
     */
    public Statistik() {
        
    }
    
    /**
     * Erstellt eine neue Statistik f&uuml;r den Spieler.<br>
     * Aufruf in der Regel beim Laden der Statistik eines Spielers im 
     * Gesamtdatensatz.
     * @param stat Ein Integer-Array mit allen Statistik-Werten
     */
    public Statistik(int[] stat) {
        this.anzahlSiege = stat[0];
        this.anzahlPatt = stat[1];
        this.anzahlMatt = stat[2];
        this.anzahlSiegeC = stat[3];
        this.anzahlPattC = stat[4];
        this.anzahlMattC = stat[5];
        this.schnellsterSieg = stat[6];
        this.kuerzesterSieg = stat[7];
        this.schnellstesMatt = stat[8];
        this.kuerzestesMatt = stat[9];
        this.zeitSiegDurchschnitt = stat[10];
        this.zeitMattDurchschnitt = stat[11];
        this.zuegeSiegDurchschnitt = stat[12];
        this.zuegeMattDurchschnitt = stat[13];
        this.matWertSiegDurchschnitt = stat[14];
        this.matWertMattDurchschnitt = stat[15];
    }
    
    /**
     * Berechnet den Score des Spielers anhand einiger der gespeicherten Daten.
     * @return Ganzzahliger Score zwischen 0 und 1000
     */
    public int getScore() {
        // Berechnung beginnt bei 0
        double score = 0;
        // Anzahl der gespielten Partien
        int anzahlSpiele = getAnzahlSpiele();
        // Drei Punkte fuer Sieg
        score = 3 * anzahlSiege;
        // Ein Punkt fuer Patt
        score += anzahlPatt;
        // Durchschnitt pro Partie
        score /= anzahlSpiele;
        // Ganzzahliger hundertfacher Wert
        score = Math.round(score * 100);
        /* Einbeziehen von durchschnittlichen Zuegen und durchschnittlichem
         * Materialwert bei Gewinn und Verlust
         */
        
        // Versuch, den maxWert auf 1000 zu setzen
        /* Durchschnitt pro Partie sind 300
         * Maximaler Materialwert ist 3830
         * Minimaler Materialwert ist 0
         */
        return (int) score;
    }
    
    /**
     * Liefert eine Zeichenkette mit allen wichtigen Daten zur&uuml;ck. <br>
     * Wird f&uuml;r das Speichern des Gesamtdatensatzes verwendet.
     * @return Eine mehrzeilige Zeichenkette
     */
    public String toString() {
        String string;
        String lineSep = System.getProperty("line.separator");
        string = anzahlSiege + lineSep;
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
    
    
}
