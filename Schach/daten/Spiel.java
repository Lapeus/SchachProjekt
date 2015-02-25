package daten;

public class Spiel {

    private String spielname;
    private Spieler spieler1;
    private Spieler spieler2;
    private Spielfeld spielfeld;
    private Einstellungen einstellungen;
    private Spieldaten spieldaten;
    
    public Spiel(String spielname, Spieler spieler1, Spieler spieler2,
        Spielfeld spielfeld, Einstellungen einstellungen,
        Spieldaten spieldaten) {
        this.spielname = spielname;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
        this.spielfeld = spielfeld;
        this.einstellungen = einstellungen;
        this.spieldaten = spieldaten;
    }
}