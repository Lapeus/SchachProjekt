package daten;

public class Spieler {

    private String name;
    private boolean farbe; //true für weiss, false für schwarz
    private int spielzeit;
    private int anzahlZuege;
    private int anzahlSpiele;
    
    public Spieler(String name) {
        this.name = name;
        this.spielzeit = 0;
        this.anzahlZuege = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean getFarbe() {
        return farbe;
    }
    
    public void setFarbe(boolean farbe) {
        this.farbe = farbe;
    }
    
    public int getSpielzeit() {
        return spielzeit;
    }
    
    public int getAnzahlZuege() {
        return anzahlZuege;
    }
    
    public int getAnzahlSpiele() {
        return anzahlSpiele;
    }
    
}