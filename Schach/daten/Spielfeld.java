package daten;

import java.util.ArrayList;
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

/**
 * Verwaltet die Figuren und ihre Position auf dem Brett.
 * Sie stellt die Methoden zum Ziehen bereit und gibt zus&auml;tzlich an, ob 
 * das Brett aktuell aus wei&szlig;er oder aus schwarzer Sicht gezeigt wird.
 * @author Christian Ackermann
 */
public class Spielfeld {

    /**
     * Alle spielbezogenen Daten wie die Auflistung der Z&uuml;ge.
     */
    private Spieldaten spieldaten;
    
    /**
     * Eine Liste mit allen 64 Felder des Spielbrettes. <br>
     * Index 0 entspricht dem Feld links unten, Index 63 dem Feld rechts oben.
     */
    private List<Feld> felder = new ArrayList<Feld>();
    
    /**
     * Eine Liste mit allen schwarzen Figuren, die sich noch im Spiel befinden,
     * nach aufsteigendem Wert sortiert.
     */
    private List<Figur> schwarzeFiguren = new ArrayList<Figur>();
    
    /**
     * Eine Liste mit allen wei&szlig;en Figuren, die sich noch im Spiel 
     * befinden, nach aufsteigendem Wert sortiert.
     */
    private List<Figur> weisseFiguren = new ArrayList<Figur>();
    
    /**
     * Eine Liste mit allen geschlagenen schwarzen Figuren, nach absteigendem
     * Wert sortiert.
     */
    private List<Figur> geschlagenSchwarz = new ArrayList<Figur>();
    
    /**
     * Eine Liste mit allen geschlagenen wei&szlig;en Figuren, nach absteigendem
     * Wert sortiert.
     */
    private List<Figur> geschlagenWeiss = new ArrayList<Figur>();
    
    /**
     * Eine Liste mit den Feldern der bedrohten Figuren.
     */
    private List<Feld> bedrohteFelder = new ArrayList<Feld>();
    
    /**
     * Eine Liste mit den Feldern der zu schlagenden Figuren.
     */
    private List<Feld> schlagendeFelder = new ArrayList<Feld>();
    
    /**
     * Gibt an, welcher Spieler am Zug ist und von welcher Person aus das Brett
     * aufgebaut ist. <br>
     * <b>true</b> f&uuml;r wei&szlig;, <b>false</b> f&uuml;r schwarz
     */
    private boolean aktuellerSpieler = true;
    
    /**
     * Gibt an, ob der aktuelle Spieler im Schach steht.
     */
    private boolean schach;
    
    /**
     * Erzeugt ein neues Spielfeld und stellt die Figuren an die richtige
     * Position.
     * @param felder Die Liste mit den Feldern, auf die die Figuren gestellt
     * werden sollen
     */
    public Spielfeld(List<Feld> felder) {
        // Liste mit den Feldern wurde uebergeben
        this.felder = felder;
        
        // Acht schwarze Bauern
        for (int i = 1; i <= 8; i++) {
            // Erstellen
            Bauer bauer = new Bauer(felder.get(47 + i), false);
            // Der Liste hinzufuegen
            schwarzeFiguren.add(bauer);
            // Dem Feld die Figur hinzufuegen
            felder.get(47 + i).setFigur(bauer);
        }
        // Acht weisse Bauern
        for (int i = 1; i <= 8; i++) {
            // Erstellen
            Bauer bauer = new Bauer(felder.get(7 + i), true);
            // Der Liste hinzufuegen
            weisseFiguren.add(bauer);
            // Dem Feld die Figur hinzufuegen
            felder.get(7 + i).setFigur(bauer);
        }
        // Zwei schwarze Springer erstellen
        Springer springer = new Springer(felder.get(57), false);
        schwarzeFiguren.add(springer);
        felder.get(57).setFigur(springer);
        springer = new Springer(felder.get(62), false);
        schwarzeFiguren.add(springer);
        felder.get(62).setFigur(springer);
        // Zwei weisse Springer erstellen
        springer = new Springer(felder.get(1), true);
        weisseFiguren.add(springer);
        felder.get(1).setFigur(springer);
        springer = new Springer(felder.get(6), true);
        weisseFiguren.add(springer);
        felder.get(6).setFigur(springer);
        // Zwei schwarze Laeufer erstellen
        Laeufer laeufer = new Laeufer(felder.get(58), false);
        schwarzeFiguren.add(laeufer);
        felder.get(58).setFigur(laeufer);
        laeufer = new Laeufer(felder.get(61), false);
        schwarzeFiguren.add(laeufer);
        felder.get(61).setFigur(laeufer);
        // Zwei weisse Laeufer erstellen
        laeufer = new Laeufer(felder.get(2), true);
        weisseFiguren.add(laeufer);
        felder.get(2).setFigur(laeufer);
        laeufer = new Laeufer(felder.get(5), true);
        weisseFiguren.add(laeufer);
        felder.get(5).setFigur(laeufer);
        // Zwei schwarze Tuerme erstellen
        Turm turm = new Turm(felder.get(56), false);
        schwarzeFiguren.add(turm);
        felder.get(56).setFigur(turm);
        turm = new Turm(felder.get(63), false);
        schwarzeFiguren.add(turm);
        felder.get(63).setFigur(turm);
        // Zwei weisse Tuerme erstellen
        turm = new Turm(felder.get(0), true);
        weisseFiguren.add(turm);
        felder.get(0).setFigur(turm);
        turm = new Turm(felder.get(7), true);
        weisseFiguren.add(turm);
        felder.get(7).setFigur(turm);
        // Eine schwarze Dame erstellen
        Dame dame = new Dame(felder.get(59), false);
        schwarzeFiguren.add(dame);
        felder.get(59).setFigur(dame);
        // Eine weisse Dame erstellen
        dame = new Dame(felder.get(3), true);
        weisseFiguren.add(dame);
        felder.get(3).setFigur(dame);
        // Einen schwarzen Koenig erstellen
        Koenig koenig = new Koenig(felder.get(60), false);
        schwarzeFiguren.add(koenig);
        felder.get(60).setFigur(koenig);
        // Einen weissen Koenig erstellen
        koenig = new Koenig(felder.get(4), true);
        weisseFiguren.add(koenig);
        felder.get(4).setFigur(koenig);
        
        // Allen Feldern das Spielfeld zufuegen
        for (Figur figur : schwarzeFiguren) {
            figur.setSpielfeld(this);
        }
        for (Figur figur : weisseFiguren) {
            figur.setSpielfeld(this);
        }
        
    }
    
    /**
     * F&uuml;hrt einen Zug durch und passt alle n&ouml;tigen Listen und Felder
     * an.
     * @param figur Die Figur, die gezogen werden soll
     * @param zielfeld Das Feld, auf das die Figur gezogen werden soll
     * @param zugzeit Die Dauer des Zuges in ganzen Sekunden
     */
    public void ziehe(Figur figur, Feld zielfeld, int zugzeit) {
        // Hier muss ein neuer Zug erstellt werden
        Zug zug;
        // Wird das ein Schlagzug
        boolean schlagzug = false;
        // Wenn auf dem Zielfeld eine Figur steht
        if (zielfeld.getFigur() != null) {
            // Ist es ein Schlagzug
            schlagzug = true;
        }
        // Wenn die Figur noch nicht gezogen wurde
        boolean ersterZug = false;
        if (!figur.getGezogen()) {
            // Ist es der erste Zug
            ersterZug = true;
        }
        // Wenn ein Bauer auf die gegnerische Grundlinie zieht
        boolean umwandlung = false;
        if (figur.getWert() == 100 
            && (zielfeld.getYK() == 0 || zielfeld.getYK() == 7)) {
            // Dann ist es ein Umwandlungszug
            umwandlung = true;
        }
        
        // Neuer Zug wird erstellt
        zug = new Zug(figur.getPosition(), zielfeld, figur, schlagzug,  zugzeit,
            ersterZug, umwandlung);
        spieldaten.getZugListe().add(zug);
        // Figur wird von der aktuellen Position entfernt
        figur.getPosition().setFigur(null);
        // Wenn auf dem Zielfeld eine Figur steht
        if (zielfeld.getFigur() != null) {
            // Wenn wir selbst eine weiße Figur sind
            if (figur.getFarbe()) {
                // schlagen wir eine schwarze Figur
                schwarzeFiguren.remove(zielfeld.getFigur());
                geschlagenSchwarz.add(zielfeld.getFigur());
            // Wenn wir selbst eine schwarze Figur sind
            } else {
                // schlagen wir eine weiße Figur
                weisseFiguren.remove(zielfeld.getFigur());
                geschlagenWeiss.add(zielfeld.getFigur());
            }   
        }
        
        /* Ueberpruefung auf Rochade aus praktischen und platztechnische
         * Gruenden ausgelagert.
         */
        rochadeZiehen(zug);
        
        /* Ueberpruefung auf enPassantZug aus praktischen und platztechnischen
         * Gruenden ausgelagert.
         */
        enPassantZiehen(zug);
        
        // Figur wird auf das Zielfeld gesetzt
        zielfeld.setFigur(figur);
        // Die aktuelle Position wird angepasst
        figur.setPosition(zielfeld);
        
        // Der erste Zug von Bauer, Turm und Koenig muessen erfasst werden.
        // Zur Vereinfachung wird dies einfach bei allen Figuren durchgefuerht.
        if (!figur.getGezogen()) {
            figur.setGezogen(true);
        }
        
        /* Der Zug ist nun vorbei. Daher aendert sich der aktive Spieler und
         * das Spielfeld muss gedreht werden.
         */
        aktuellerSpieler = !aktuellerSpieler;  
        
    }
    
    /**
     * Pr&uuml;ft, ob der Zug eine Rochade war und f&uuml;hrt ihn entsprechend
     * zu Ende.
     * @param zug Der letzte durchgef&uuml;hrte Zug
     */
    private void rochadeZiehen(Zug zug) {
        // Wenn es eine Rochade ist
        /* Wenn es eine Rochade ist, wurde der Koenig zwei Felder zur Seite
         * bewegt.
         */
        if (zug.getFigur().getWert() == 0 && zug.isErsterZug() 
            && (zug.getZielfeld().getXK() == 2 
            || zug.getZielfeld().getXK() == 6)) {
            // Index des Startfeldes
            int indexStart;
            // Index des Zielfeldes
            int indexZiel;
            // Grosse Rochade
            if (zug.getZielfeld().getXK() == 2) {
                // Weiss
                if (zug.getFigur().getFarbe()) {
                    // Der Turm links unten
                    indexStart = 0;
                // Schwarz
                } else {
                    // Der Turm links oben
                    indexStart = 56;
                }
                indexZiel = indexStart + 3;
            // Kleine Rochade
            } else {
                // Weiss
                if (zug.getFigur().getFarbe()) {
                    // Der Turm rechts unten
                    indexStart = 7;
                // Schwarz
                } else {
                    // Der Turm rechts oben
                    indexStart = 63;
                }
                indexZiel = indexStart - 2;
            }
            // Der beteiligte Turm
            Figur turm = felder.get(indexStart).getFigur();
            // Wird umgesetzt
            turm.setPosition(felder.get(indexZiel));
            // Die Felder werden aktualisiert
            felder.get(indexZiel).setFigur(turm);
            felder.get(indexStart).setFigur(null);
            // Der Turm wurde gezogen
            turm.setGezogen(true);
            /* Der Zug wird geandert um ihn einfacher rueckgaengig machen
             * zu koennen.
             */
            spieldaten.getZugListe().remove(zug);
            RochadenZug rochzug = new RochadenZug(zug.getFigur(), 
                zug.getStartfeld(), turm, felder.get(indexStart)
                , zug.getZugzeit());
            spieldaten.getZugListe().add(rochzug);   
        }
    }
    
    /**
     * Pr&uuml;ft, ob der Zug ein En-Passant-Schlag war und f&uuml;hrt ihn
     * entsprechend zu Ende.
     * @param zug Der letzte durchgef&uuml;hrte Zug
     */
    private void enPassantZiehen(Zug zug) {
        // Wenn es ein en-passant-Schlag ist
        /* Wenn es ein Bauer ist und er die Spalte wechselt ohne dabei 
         * eine Figur zu schlagen
         */
        if (zug.getFigur().getWert() == 100 
            && zug.getStartfeld().getXK() != zug.getZielfeld().getXK() 
            && zug.getZielfeld().getFigur() == null) {
            // Der andere beteiligte Bauer
            Figur andererBauer;
            // Wenn weiss gezogen hat
            if (zug.getFigur().getFarbe()) {
                // Der Bauer steht auf dem Feld darunter
                andererBauer = felder.get(felder.indexOf(zug.getZielfeld()) - 8)
                    .getFigur();
                // Listen werden aktualisiert
                geschlagenSchwarz.add(andererBauer);
                schwarzeFiguren.remove(andererBauer);
            // Wenn schwarz gezogen hat
            } else {
                // Der Bauer steht auf dem Feld darueber
                andererBauer = felder.get(felder.indexOf(zug.getZielfeld()) + 8)
                    .getFigur();
                // Listen werden aktualisiert
                geschlagenWeiss.add(andererBauer);
                weisseFiguren.remove(andererBauer);
            }
            // Das Feld wird aktualisiert
            andererBauer.getPosition().setFigur(null);
            
            /* Der Zug wird geandert um ihn einfacher rueckgaengig machen
             * zu koennen.
             */
            spieldaten.getZugListe().remove(zug);
            EnPassantZug enpasszug = new EnPassantZug(zug.getFigur(), 
                zug.getStartfeld(), andererBauer, zug.getZugzeit());
            spieldaten.getZugListe().add(enpasszug);
        }
    }
    
    /**
     * Macht den jeweils letzten Zug r&uuml;ckg&auml;ngig.
     */
    public void zugRueckgaengig() {
        // Rufe den letzten durchgefuehrten Zug auf
        Zug zug = spieldaten.getZugListe()
            .get(spieldaten.getZugListe().size() - 1);
        // Loesche ihn aus der Liste
        spieldaten.getZugListe().remove(zug);
        // Mache ihn rueckgaengig (ziehe Methode von hinten)
        // Die bedrohten Felder koennen ignoriert werden
        
        // Aktiver Spieler muss geaendert werden.
        aktuellerSpieler = !aktuellerSpieler;
        
        // Wenn es ein Rochadenzug war
        if (zug instanceof RochadenZug) {
            // Umwandlung des Zugs
            RochadenZug rochzug = (RochadenZug) zug;
            // Das Zielfeld des Koenigs leeren
            rochzug.getKoenig().getPosition().setFigur(null);
            // Die Position des Koenigs auf das Startfeld setzen
            rochzug.getKoenig().setPosition(rochzug.getStartfeldK());
            // Dem Startfeld den Koenig zuweisen
            rochzug.getStartfeldK().setFigur(rochzug.getKoenig());
            // Der Koenig wurde noch nicht gezogen
            rochzug.getKoenig().setGezogen(false);
            
            // Das Zielfeld des Turms leeren
            rochzug.getTurm().getPosition().setFigur(null);
            // Die Position des Turms auf das Startfeld setzen
            rochzug.getTurm().setPosition(rochzug.getStartfeldT());
            // Dem Startfeld den Turm zuweisen
            rochzug.getStartfeldT().setFigur(rochzug.getTurm());
            // Der Turm wurde noch nicht gezogen
            rochzug.getTurm().setGezogen(false);
            
        // Wenn es ein En-Passant-Schlag war   
        } else if (zug instanceof EnPassantZug) {
            // Umwandlung des Zugs
            EnPassantZug enpasszug = (EnPassantZug) zug;
            // Ausfuerender Bauer
            Figur bauer = enpasszug.getAusfuehrer();
            // Geschlagenener Bauer
            Figur geschlagBauer = enpasszug.getGeschlagenen();
            
            // Ausfuehrender Bauer zuruecksetzen
            bauer.getPosition().setFigur(null);
            // Die Position des ausfuehrenden Bauerns auf das Startfeld setzen
            bauer.setPosition(enpasszug.getStartfeld());
            // Dem Startfeld den Bauern wieder zuweisen
            enpasszug.getStartfeld().setFigur(bauer);
            
            // Den geschlagenen Bauern wieder herstellen
            // Wenn der schlagende Bauer weiss ist
            if (bauer.getFarbe()) {
                // Schwarze Listen aktualisieren
                geschlagenSchwarz.remove(geschlagBauer);
                schwarzeFiguren.add(geschlagBauer);
            // Wenn der schlagende Bauer schwarz ist
            } else {
                // Weisse Liste aktualisieren
                geschlagenWeiss.remove(geschlagBauer);
                weisseFiguren.add(geschlagBauer);
            }
            // Dem Feld die Figur zuweisen
            geschlagBauer.getPosition().setFigur(geschlagBauer);
            
        // Sonst war es ein normaler Zug
        } else {
            // Figur an die vorherige Stelle setzen
            zug.getFigur().setPosition(zug.getStartfeld());
            // Die Felder aktualisieren
            zug.getStartfeld().setFigur(zug.getFigur());
            zug.getZielfeld().setFigur(null);
            // Falls das der erste Zug dieser Figur war
            if (zug.isErsterZug()) {
                // Muss das rueckgaengig gemacht werden
                zug.getFigur().setGezogen(false);
            }
            // Falls im letzten Zug eine Figur geschlagen wurde
            if (zug.isSchlagzug()) {
                // Muss die wieder hergestellt werden
                Figur geschlageneFigur = null;
                // Wenn wir selbst weiss sind
                if (zug.getFigur().getFarbe()) {
                    // Muss eine schwarze Figur wieder hergestellt werden
                    // Letzte geschlagene Figur wieder herstellen
                    geschlageneFigur = geschlagenSchwarz
                        .get(geschlagenSchwarz.size() - 1);
                    /* Sie aus der Liste entfernen und dem Spielfeld wieder 
                     * zufuegen.
                     */
                    geschlagenSchwarz.remove(geschlageneFigur);
                    schwarzeFiguren.add(geschlageneFigur);
                } else if (!zug.getFigur().getFarbe()) {
                    // Muss eine weisse Figur wieder hergestellt werden
                    // Letzte geschlagene Figur wieder herstellen
                    geschlageneFigur = geschlagenWeiss
                        .get(geschlagenWeiss.size() - 1);
                    /* Sie aus der Liste entfernen und dem Spielfeld wieder 
                     * zufuegen.
                     */
                    geschlagenWeiss.remove(geschlageneFigur);
                    weisseFiguren.add(geschlageneFigur);
                }
                // Sie auf ihr vorheriges Feld setzen
                geschlageneFigur.getPosition().setFigur(geschlageneFigur);
            }
            
        } // Ende von Rochaden-if
        
    }
    
    /**
     * Wandelt den angegebenen Bauern in eine andere Figur um.
     * @param figur Der Bauer, der umgewandelt werden darf
     * @param wert Der Wert der Figur, in die er umgewandelt werden soll
     */
    public void umwandeln(Figur figur, int wert) {
        // Erzeugen der neuen Figur
        /* Cast wird benoetigt, damit die Variable auch ausserhalb des ifs 
         * gueltig ist.
         */
        Figur neueFigur;
        if (wert == 275) {
            neueFigur = new Springer(
                figur.getPosition(), figur.getFarbe());
        } else if (wert == 325) {    
            neueFigur = new Laeufer(
                figur.getPosition(), figur.getFarbe());
        } else if (wert == 465) {    
            neueFigur = new Turm(
                figur.getPosition(), figur.getFarbe());
        } else {    
            neueFigur = new Dame(
                figur.getPosition(), figur.getFarbe());
        }     
        // Sie auf das neue Feld stellen
        figur.getPosition().setFigur(figur);
        // WICHTIG um Pam-Krabbé-Rochade zu verhindern
        neueFigur.setGezogen(figur.getGezogen());
        // Die entsprechende Liste aktualisieren
        // Wenn es eine weisse Figur ist
        if (figur.getFarbe()) {
            weisseFiguren.remove(figur);
            weisseFiguren.add(neueFigur);
        // Wenn es eine schwarze Figur ist
        } else {
            schwarzeFiguren.remove(figur);
            schwarzeFiguren.add(neueFigur);
        }
    }
   
    /**
     * Eine statische Klasse, die zwei Figuren aufgrund ihres Wertes vergleicht.
     * @author Christian Ackermann
     */
    public static class FigurenComparator implements Comparator<Figur> {
        @Override
        public int compare(Figur fig1, Figur fig2) {
            // Wenn der Wert der ersten Figur nicht kleiner ist
            if (fig1.getWert() >= fig2.getWert()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    
    /**
     * Sortiert eine Liste von Figuren aufsteigend nach Wert.
     * @param figuren Eine Liste von Figuren die sortiert werden soll
     * @return Die sortierte Liste
     */
    private List<Figur> sortiereListe(List<Figur> figuren) {
        Collections.sort(figuren, new FigurenComparator());
        return figuren;
    }
    
    /**
     * Pr&uuml;ft ob der aktuelle Spieler noch ziehen kann. <br>
     * Die Pr&uuml;fung auf Matt oder Patt erfolgt an anderer Stelle.
     * @return <b>true</b> wenn er nicht mehr ziehen kann, <b>false</b> wenn er
     * noch ziehen kann.
     */
    public boolean schachMatt() {
        boolean matt = false;
        List<Figur> eigeneFiguren;
        List<Feld> alleFelder = new ArrayList<Feld>();
        // Wenn weiss dran ist
        if (aktuellerSpieler) {
            eigeneFiguren = weisseFiguren;
        // Wenn schwarz dran ist
        } else {
            eigeneFiguren = schwarzeFiguren;
        }
        // Fuer jede eigene Figur
        for (Figur figur : eigeneFiguren) {
            // Fuege der Liste alle moeglichen Felder zu
            alleFelder.addAll(figur.getKorrektFelder());
        }
        // Wenn es fuer keine Figur ein moegliches Feld gibt
        if (alleFelder.isEmpty()) {
            // Ist er matt oder patt
            matt = true;
        }
        return matt;
    }
    
    /**
     * Gibt den gesamten Materialwert des angegebenen Spielers zur&uuml;ck.
     * @param spieler Die Spielfarbe des Spielers
     * @return Der Materialwert als ganze Zahl zwischen 0 und 3830
     */
    public int getMaterialwert(boolean spieler) {
        int matWert = 0;
        List<Figur> eigeneFiguren;
        if (spieler) {
            eigeneFiguren = weisseFiguren;
        } else {
            eigeneFiguren = schwarzeFiguren;
        }
        for (Figur figur : eigeneFiguren) {
            matWert += figur.getWert();
        }
        return matWert;
    }
    
    /**
     * Gibt die Spieldaten zur&uuml;ck.
     * @return Die Spieldaten
     */
    public Spieldaten getSpieldaten() {
        return spieldaten;
    }

    /**
     * Setzt die Spieldaten.
     * @param spieldaten Die Spieldaten
     */
    public void setSpieldaten(Spieldaten spieldaten) {
        this.spieldaten = spieldaten;
    }
    
    /**
     * Gibt die Liste der Felder zur&uuml;ck.
     * @return Liste von Feldern
     */
    public List<Feld> getFelder() {
        return felder;
    }
    
    /**
     * Gibt die schwarzen noch im Spiel befindlichen Figuren zur&uuml;ck.
     * @return Liste von schwarzen Figuren
     */
    public List<Figur> getSchwarzeFiguren() {
        return sortiereListe(schwarzeFiguren);
    }
    
    /**
     * Gibt die wei&szlig;en noch im Spiel befindlichen Figuren zur&uuml;ck.
     * @return Liste von wei&szlig;en Figuren
     */
    public List<Figur> getWeisseFiguren() {
        return sortiereListe(weisseFiguren);
    }
    
    /**
     * Gibt die geschlagenen schwarzen Figuren zur&uuml;ck.
     * @return Liste von schwarzen Figuren
     */
    public List<Figur> getGeschlagenSchwarz() {
        return geschlagenSchwarz;
    }
    
    /**
     * Gibt die geschlagenen wei&szlig;en Figuren zur&uuml;ck.
     * @return Liste von wei&szlig;en Figuren
     */
    public List<Figur> getGeschlagenWeiss() {
        return geschlagenWeiss;
    }
    
    /**
     * Gibt die geschlagenen schwarzen Figuren nach aufsteigendem Wert 
     * zur&uuml;ck.
     * @return Liste von schwarzen Figuren
     */
    public List<Figur> getGeschlagenSchwarzSort() {
        return sortiereListe(geschlagenSchwarz);
    }
    
    /**
     * Gibt die geschlagenen wei&szlig;en Figuren nach aufsteigendem Wert
     * zur&uuml;ck.
     * @return Liste von wei&szlig;en Figuren
     */
    public List<Figur> getGeschlagenWeissSort() {
        return sortiereListe(geschlagenWeiss);
    }
    
    /**
     * Gibt eine Liste von den aktuell bedrohten Feldern auf denen eine Figur
     * steht zur&uuml;ck. <br>
     * Wenn die entsprechende Option aktiviert ist, werden diese dem Spieler als
     * Hilfestellung angezeigt.
     * @return Liste der besetzten, bedrohten Felder
     */
    public List<Feld> getBedrohteFelder() {
        /* Zusatz: Vorbereitung fuer die grafische Hilfestellung "Es werden
         * alle in diesem Zug bedrohten Figuren angezeigt."
         */
        // Liste mit den bedrohten Figuren leeren
        bedrohteFelder.clear();
        // Liste mit den gegnerischen Figuren
        List<Figur> gegnerFiguren;
        // Wenn weiss als naechstes dran ist
        if (aktuellerSpieler) {
            gegnerFiguren = schwarzeFiguren;
        // Wenn schwarz als naechstes dran ist
        } else {
            gegnerFiguren = weisseFiguren;
        }
        // Fuer alle gegnerischen Figuren
        for (Figur gegner : gegnerFiguren) {
            // Liste mit den korrekten Feldern dieser Figur
            List<Feld> felder = gegner.getKorrektFelder();
            // Fuer jedes dieser Felder
            for (Feld feld : felder) {
                // Wenn auf dem Feld eine Figur steht
                if (feld.getFigur() != null) {
                    // Ist das meine und somit bedroht
                    bedrohteFelder.add(feld);
                    
                    // Wenn es der Koenig ist, steht er nun im Schach
                    if (feld.getFigur().getWert() == 0) {
                        schach = true;
                    }
                }
            }
        }
      
        return bedrohteFelder;
    }
    
    /**
     * Gibt an, welcher Spieler am Zug ist und ob das Brett momentan von 
     * Wei&szlig; oder von Schwarz aus gesehen wird.
     * @return <b>true</b> f&uuml;r wei&szlig;, <b>false</b> f&uuml;r schwarz
     */
    public boolean getAktuellerSpieler() {
        return aktuellerSpieler;
    }

    /**
     * Gibt an, ob der aktuelle Spieler im Schach steht.<br>
     * Wird nur f&uuml;r die Konversation zwischen Logik und GUI verwendet.
     * @return Wahrheitswert
     */
    public boolean isSchach() {
        return schach;
    }

    /**
     * Setzt, ob der aktuelle Spieler im Schach steht.
     * @param schach Wahrheitswert
     */
    public void setSchach(boolean schach) {
        this.schach = schach;
    }

    /**
     * Gibt eine Liste mit den Feldern zur&uuml;ck, auf denen gegnerische 
     * Figuren stehen und welche in diesem Zug vom aktiven Spieler geschlagen
     * werden k&ouml;nnen.<br>
     * Wenn die entsprechende Option aktiviert ist, werden diese Felder dem
     * Spieler als grafische Hilfestellung angezeigt.
     * @return Eine Liste mit den Feldern auf denen zu schlagende Figuren stehen
     */
    public List<Feld> getSchlagendeFelder() {
        return schlagendeFelder;
    }
    
}