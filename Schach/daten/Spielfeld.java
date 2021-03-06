package daten;

import java.util.ArrayList;
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
import zuege.RochadenZug;
import zuege.EnPassantZug;
import zuege.Umwandlungszug;

/**
 * Verwaltet die Figuren, ihre Position auf dem Brett, eine Auflistung der 
 * durchgef&uuml;hrten Z&uuml;ge und die Einstellungen, mit denen das Spiel
 * gestartet wurde. <br>
 * Sie stellt die Methoden zum Ziehen bereit und gibt zus&auml;tzlich an, 
 * welcher Spieler aktuell am Zug ist.
 * @author Christian Ackermann
 */
public class Spielfeld {

    /**
     * Alle spielbezogenen Daten wie die Auflistung der Z&uuml;ge.
     */
    private Spieldaten spieldaten;
    
    /**
     * Alle spielbezogenen Einstellungen, die beim Laden &uuml;bernommen werden
     * m&uuml;ssen.
     */
    private Einstellungen einstellungen;
    
    /**
     * Eine Liste mit allen 64 Felder des Spielbrettes. <br>
     * Index 0 entspricht dem Feld links unten, Index 63 dem Feld rechts oben.
     */
    private List<Feld> felder = new ArrayList<Feld>();
    
    /**
     * Eine Liste mit allen wei&szlig;en Figuren, die sich noch im Spiel 
     * befinden, nach aufsteigendem Wert sortiert.
     */
    private List<Figur> weisseFiguren = new ArrayList<Figur>();
    
    /**
     * Eine Liste mit allen schwarzen Figuren, die sich noch im Spiel befinden,
     * nach aufsteigendem Wert sortiert.
     */
    private List<Figur> schwarzeFiguren = new ArrayList<Figur>();
    
    /**
     * Eine Liste mit allen geschlagenen wei&szlig;en Figuren, chronologisch
     * nach Zeitpunkt des Schlagens sortiert.
     */
    private List<Figur> geschlagenWeiss = new ArrayList<Figur>();
    
    /**
     * Eine Liste mit allen geschlagenen schwarzen Figuren, chronologisch
     * nach Zeitpunkt des Schlagens sortiert.
     */
    private List<Figur> geschlagenSchwarz = new ArrayList<Figur>();
    
    /**
     * Gibt an, welcher Spieler am Zug ist.<br>
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
        // Figuren erzeugen und sie an die richtige Stelle setzen
        init();
        
    }
    
    /**
     * Zweiter Konstruktor, um ein Spielfeld laden zu k&ouml;nnen, ohne dass 
     * dabei alle Figuren neu erzeugt werden m&uuml;ssen.
     * @param felder Die Felder-Liste
     * @param aktuellerSpieler Setzt den aktuellen Spieler: <b>True</b> f&uuml;r
     * wei&szlig;, <b>false</b> f&uuml;r schwarz
     */
    public Spielfeld(List<Feld> felder, boolean aktuellerSpieler) {
        this.felder = felder;
        this.aktuellerSpieler = aktuellerSpieler;
        init();
    }
    
    /**
     * Erzeugt neue Figuren und stellt sie in die Startaufstellung.<br>
     * Ist <b>public</b>, weil sie f&uuml;r die Spielwiederholung aus 
     * {@link Gesamtdatensatz} aufgerufen werden k&ouml;nnen muss.
     */
    public void init() {
        // Acht weisse Bauern
        for (int i = 1; i <= 8; i++) {
            // Erstellen
            Bauer bauer = new Bauer(felder.get(7 + i), true);
            // Der Liste hinzufuegen
            weisseFiguren.add(bauer);
            // Dem Feld die Figur hinzufuegen
            felder.get(7 + i).setFigur(bauer);
        }
        // Acht schwarze Bauern
        for (int i = 1; i <= 8; i++) {
            // Erstellen
            Bauer bauer = new Bauer(felder.get(47 + i), false);
            // Der Liste hinzufuegen
            schwarzeFiguren.add(bauer);
            // Dem Feld die Figur hinzufuegen
            felder.get(47 + i).setFigur(bauer);
        }
        // Zwei weisse Springer erstellen
        Springer springer = new Springer(felder.get(1), true);
        weisseFiguren.add(springer);
        felder.get(1).setFigur(springer);
        springer = new Springer(felder.get(6), true);
        weisseFiguren.add(springer);
        felder.get(6).setFigur(springer);
        // Zwei schwarze Springer erstellen
        springer = new Springer(felder.get(57), false);
        schwarzeFiguren.add(springer);
        felder.get(57).setFigur(springer);
        springer = new Springer(felder.get(62), false);
        schwarzeFiguren.add(springer);
        felder.get(62).setFigur(springer);
        
        // Zwei weisse Laeufer erstellen
        Laeufer laeufer = new Laeufer(felder.get(2), true);
        weisseFiguren.add(laeufer);
        felder.get(2).setFigur(laeufer);
        laeufer = new Laeufer(felder.get(5), true);
        weisseFiguren.add(laeufer);
        felder.get(5).setFigur(laeufer);
        // Zwei schwarze Laeufer erstellen
        laeufer = new Laeufer(felder.get(58), false);
        schwarzeFiguren.add(laeufer);
        felder.get(58).setFigur(laeufer);
        laeufer = new Laeufer(felder.get(61), false);
        schwarzeFiguren.add(laeufer);
        felder.get(61).setFigur(laeufer);
        
        // Zwei weisse Tuerme erstellen
        Turm turm = new Turm(felder.get(0), true);
        weisseFiguren.add(turm);
        felder.get(0).setFigur(turm);
        turm = new Turm(felder.get(7), true);
        weisseFiguren.add(turm);
        felder.get(7).setFigur(turm);
        // Zwei schwarze Tuerme erstellen
        turm = new Turm(felder.get(56), false);
        schwarzeFiguren.add(turm);
        felder.get(56).setFigur(turm);
        turm = new Turm(felder.get(63), false);
        schwarzeFiguren.add(turm);
        felder.get(63).setFigur(turm);
        
        // Eine weisse Dame erstellen
        Dame dame = new Dame(felder.get(3), true);
        weisseFiguren.add(dame);
        felder.get(3).setFigur(dame);
        // Eine schwarze Dame erstellen
        dame = new Dame(felder.get(59), false);
        schwarzeFiguren.add(dame);
        felder.get(59).setFigur(dame);
        
        // Einen weissen Koenig erstellen
        Koenig koenig = new Koenig(felder.get(4), true);
        weisseFiguren.add(koenig);
        felder.get(4).setFigur(koenig);
        // Einen schwarzen Koenig erstellen
        koenig = new Koenig(felder.get(60), false);
        schwarzeFiguren.add(koenig);
        felder.get(60).setFigur(koenig);
        
        
        // Allen Feldern das Spielfeld zufuegen
        for (Figur figur : weisseFiguren) {
            figur.setSpielfeld(this);
        }
        for (Figur figur : schwarzeFiguren) {
            figur.setSpielfeld(this);
        }
        
    }
    
    /**
     * F&uuml;hrt einen Zug durch und passt alle n&ouml;tigen Listen und Felder
     * an.<br>
     * <ul>
     * <li> Test auf Schlagzug </li>
     * <li> Test auf ersten Zug einer Figur </li>
     * <li> Unterscheidung zwischen normalem Zug und dem Umwandeln eines Bauern
     * </li> <li> Ziehen der Figur </li>
     * <li> Bei entsprechend aktivierter Option: Test auf Rochade und EnPassant
     * und gegebenenfalls Anpassung des Zuges </li>
     * <li> Aktuellen Spieler &auml;ndern </li>
     * </ul>
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
            figur.setGezogen(true);
        }
        
        // Neuer Zug wird erstellt
        // Wenn es ein Umwandlungszug wird
        if (figur.getWert() == 100 
            && (zielfeld.getYK() == 0 || zielfeld.getYK() == 7)) {
            zug = new Umwandlungszug(figur.getPosition(), zielfeld, figur, 
                schlagzug, zugzeit);
        // Wenn es ein normaler Zug wird
        } else {
            zug = new Zug(figur.getPosition(), zielfeld, figur, schlagzug,  
                zugzeit, ersterZug);
        }
        spieldaten.getZugListe().add(zug);
        // Figur wird von der aktuellen Position entfernt
        figur.getPosition().setFigur(null);
        // Wenn es ein Schlagzug ist
        if (schlagzug) {
            // Wenn wir selbst eine weisse Figur sind
            if (figur.getFarbe()) {
                // schlagen wir eine schwarze Figur
                schwarzeFiguren.remove(zielfeld.getFigur());
                geschlagenSchwarz.add(zielfeld.getFigur());
            // Wenn wir selbst eine schwarze Figur sind
            } else {
                // schlagen wir eine weisse Figur
                weisseFiguren.remove(zielfeld.getFigur());
                geschlagenWeiss.add(zielfeld.getFigur());
            }   
        }
        
        /* Ueberpruefung auf Rochade aus praktischen und platztechnische
         * Gruenden ausgelagert.
         * Aus rechenzeittechnisches Gruenden nur wenn die Option aktiviert ist
         */
        if (einstellungen.isRochadeMoeglich()) {
            rochadeZiehen(zug);
        }
        
        /* Ueberpruefung auf enPassantZug aus praktischen und platztechnischen
         * Gruenden ausgelagert.
         * Aus rechenzeittechnisches Gruenden nur wenn die Option aktiviert ist
         */
        if (einstellungen.isEnPassantMoeglich()) {
            enPassantZiehen(zug);
        }
        
        // Figur wird auf das Zielfeld gesetzt
        zielfeld.setFigur(figur);
        // Die aktuelle Position wird angepasst
        figur.setPosition(zielfeld);
        
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
            /* Der Zug wird geaendert um ihn einfacher rueckgaengig machen
             * zu koennen.
             */
            spieldaten.getZugListe().remove(zug);
            RochadenZug rochzug = new RochadenZug(zug.getFigur(), 
                zug.getStartfeld(), zug.getZielfeld(), turm, 
                felder.get(indexStart), zug.getZugzeit());
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
            
            /* Der Zug wird geaendert um ihn einfacher rueckgaengig machen
             * zu koennen.
             */
            spieldaten.getZugListe().remove(zug);
            EnPassantZug enpasszug = new EnPassantZug(zug.getFigur(), 
                zug.getStartfeld(), zug.getZielfeld(), andererBauer, 
                zug.getZugzeit());
            spieldaten.getZugListe().add(enpasszug);
        }
    }
    
    /**
     * Macht den jeweils letzten Zug r&uuml;ckg&auml;ngig. <br>
     * Dabei wird grunds&auml;tzlich nur die Methode 
     * {@link #ziehe(Figur, Feld, int)} r&uuml;ckw&auml;rts ausgef&uuml;hrt. 
     * Sonderz&uuml;ge werden gesondert behandelt.
     */
    public void zugRueckgaengig() {
        // Rufe den letzten durchgefuehrten Zug auf
        Zug zug = spieldaten.getLetzterZug();
        // Loesche ihn aus der Liste
        spieldaten.getZugListe().remove(zug);
        // Mache ihn rueckgaengig (ziehe Methode von hinten)
        
        // Aktiver Spieler muss geaendert werden.
        aktuellerSpieler = !aktuellerSpieler;
        // Wenn es ein Rochadenzug war
        if (zug instanceof RochadenZug) {
            // Umwandlung des Zugs
            RochadenZug rochzug = (RochadenZug) zug;
            // Das Zielfeld des Koenigs leeren
            rochzug.getZielfeld().setFigur(null);
            // Die Position des Koenigs auf das Startfeld setzen
            rochzug.getFigur().setPosition(rochzug.getStartfeld());
            // Dem Startfeld den Koenig zuweisen
            rochzug.getStartfeld().setFigur(rochzug.getFigur());
            // Der Koenig wurde noch nicht gezogen
            rochzug.getFigur().setGezogen(false);
            
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
            Figur bauer = enpasszug.getFigur();
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
                // Weisse Listen aktualisieren
                geschlagenWeiss.remove(geschlagBauer);
                weisseFiguren.add(geschlagBauer);
            }
            // Dem Feld die Figur zuweisen
            geschlagBauer.getPosition().setFigur(geschlagBauer);
        // Sonst war es ein normaler Zug oder ein Umwandlungszug
        } else {
            Figur gezogeneFigur = zug.getFigur();
            // Wenn es ein Umwandlungszug war
            if (zug instanceof Umwandlungszug) {
                Umwandlungszug umwandlZug = (Umwandlungszug) zug;
                // Und wirklich eine Figur umgewandelt wurde
                if (umwandlZug.getNeueFigur() != null) {
                    // Die Listen muessen aktualisiert werden
                    if (gezogeneFigur.getFarbe()) {
                        // Die umgewandelte Figur muss weg
                        weisseFiguren.remove(umwandlZug.getNeueFigur());
                        weisseFiguren.add(gezogeneFigur);
                    } else {
                        // Die umgewandelte Figur muss weg
                        schwarzeFiguren.remove(umwandlZug.getNeueFigur());
                        schwarzeFiguren.add(gezogeneFigur);
                    }
                }
            }
            // Figur an die vorherige Stelle setzen
            gezogeneFigur.setPosition(zug.getStartfeld());
            // Die Felder aktualisieren
            zug.getStartfeld().setFigur(gezogeneFigur);
            zug.getZielfeld().setFigur(null);
            // Falls das der erste Zug dieser Figur war
            if (zug.isErsterZug()) {
                // Muss das rueckgaengig gemacht werden
                gezogeneFigur.setGezogen(false);
            }
            // Falls im letzten Zug eine Figur geschlagen wurde
            if (zug.isSchlagzug()) {
                // Muss die wieder hergestellt werden
                Figur geschlageneFigur = null;
                // Wenn wir selbst weiss sind
                if (gezogeneFigur.getFarbe()) {
                    // Muss eine schwarze Figur wieder hergestellt werden
                    // Letzte geschlagene Figur wieder herstellen
                    geschlageneFigur = geschlagenSchwarz
                        .get(geschlagenSchwarz.size() - 1);
                    /* Sie aus der Liste entfernen und dem Spielfeld wieder 
                     * zufuegen.
                     */
                    geschlagenSchwarz.remove(geschlageneFigur);
                    schwarzeFiguren.add(geschlageneFigur);
                } else if (!gezogeneFigur.getFarbe()) {
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
     * @param figur Der Bauer, der umgewandelt werden soll
     * @param wert Der Wert der Figur, in die er umgewandelt werden soll
     */
    public void umwandeln(Figur figur, int wert) {
        // Erzeugen der neuen Figur
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
        Umwandlungszug umwandlZug = (Umwandlungszug) spieldaten.getLetzterZug();
        umwandlZug.setNeueFigur(neueFigur);
        // Sie auf das neue Feld stellen
        neueFigur.getPosition().setFigur(neueFigur);
        // Das Spielfeld zuweisen
        neueFigur.setSpielfeld(this);
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
     * Pr&uuml;ft, ob der aktuelle Spieler noch ziehen kann. <br>
     * Die Pr&uuml;fung auf Matt oder Patt erfolgt an anderer Stelle.
     * @return <b>true</b> wenn er nicht mehr ziehen kann, <b>false</b> wenn er
     * noch ziehen kann.
     * @see Spiel#auswertung()
     */
    public boolean schachMatt() {
        boolean matt = false;
        List<Figur> eigeneFiguren;
        List<Feld> alleFelder = new ArrayList<Feld>();
        // Wenn weiss dran ist
        if (aktuellerSpieler) {
            eigeneFiguren = clone(weisseFiguren);
        // Wenn schwarz dran ist
        } else {
            eigeneFiguren = clone(schwarzeFiguren);
        }
        // Fuer jede eigene Figur
        for (Figur figur : eigeneFiguren) {
            // Fuege der Liste alle moeglichen Felder zu
            alleFelder.addAll(figur.getKorrekteFelder());
        }
        // Wenn es fuer keine Figur ein moegliches Feld gibt
        if (alleFelder.isEmpty()) {
            // Ist er matt oder patt
            matt = true;
        }
        return matt;
    }
    
    /**
     * Gibt die Felder der am Matt oder Patt beteiligten Figuren zur&uuml;ck.
     * @return Liste von Feldern
     */
    public List<Feld> amMattBeteiligteFelder() {
        List<Feld> moeglicheKoenigsfelder = new ArrayList<Feld>();
        List<Figur> gegnerFiguren;
        List<Figur> beteiligteFiguren = new ArrayList<Figur>();
        List<Feld> felderDerBeteiligtenFiguren = new ArrayList<Feld>();
        Figur koenig;
        if (aktuellerSpieler) {
            koenig = weisseFiguren.get(0);
            gegnerFiguren = clone(schwarzeFiguren);
        } else {
            koenig = schwarzeFiguren.get(0);
            gegnerFiguren = clone(weisseFiguren);
        }
        moeglicheKoenigsfelder = koenig.getMoeglicheFelderKI();
        for (Feld feld : moeglicheKoenigsfelder) {
            ziehe(koenig, feld, 0);
            for (Figur gegner : gegnerFiguren) {
                if (gegner.bietetSchach(feld) 
                    && !beteiligteFiguren.contains(gegner)) {
                    beteiligteFiguren.add(gegner);
                }
            }
            zugRueckgaengig();
        }
        for (Figur figur : beteiligteFiguren) {
            felderDerBeteiligtenFiguren.add(figur.getPosition());
        }
        return felderDerBeteiligtenFiguren;
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
     * Gibt das Startfeld und das Zielfeld der zuletzt gezogenen Figur in einer
     * Liste zur&uuml;ck um sie auf dem Spielfeld kenntlich machen zu 
     * k&ouml;nnen.
     * @return Eine Liste mit zwei Feldern: erst das Startfeld, dann das 
     * Zielfeld<br>
     * Im Fall von Rochade werden alle vier beteiligten Felder zugef&uuml;gt
     */
    public List<Feld> getLetzteFelder() {
        List<Feld> letzteFelder = new ArrayList<Feld>();
        Zug zug = spieldaten.getLetzterZug();
        if (zug instanceof RochadenZug) {
            // Das Startfeld des Koenigs
            letzteFelder.add(zug.getStartfeld());
            // Das Zielfeld des Koenigs
            letzteFelder.add(zug.getZielfeld());
            // Das Startfeld des Turms
            letzteFelder.add(((RochadenZug) zug).getStartfeldT());
            // Das Zielfeld des Turms
            letzteFelder.add(((RochadenZug) zug).getTurm().getPosition());
        } else {
            letzteFelder.add(zug.getStartfeld());
            letzteFelder.add(zug.getZielfeld());
        }
        return letzteFelder;
    }
    
    /**
     * Gibt das Startfeld und das Zielfeld des Zuges zur&uuml;ck, den ein
     * Computergegner ziehen w&uuml;rde.
     * @return Eine Liste mit Start- und Zielfeld eines sinnvollen Zuges
     */
    public List<Feld> getHinweisZug() {
        List<Feld> hinweisZug = new ArrayList<Feld>();
        Computerspieler hilfe = new Computerspieler("Hilfe");
        hilfe.setFarbe(aktuellerSpieler);
        hilfe.setSpielfeld(this);
        hilfe.ziehen();
        hinweisZug.add(spieldaten.getLetzterZug().getStartfeld());
        hinweisZug.add(spieldaten.getLetzterZug().getZielfeld());
        zugRueckgaengig();
        return hinweisZug;
    }
    
    /**
     * Gibt eine Liste mit den aktuell bedrohten Feldern auf denen eine Figur
     * steht zur&uuml;ck. <br>
     * Wenn die entsprechende Option aktiviert ist, werden diese dem Spieler als
     * Hilfestellung angezeigt. Getestet wird, ob eine gegnerische Figur im
     * n&auml;chsten Zug auf das entsprechende Feld ziehen kann.
     * @return Liste der besetzten, bedrohten Felder
     */
    public List<Feld> getBedrohteFelder() {
        /* Zusatz: Vorbereitung fuer die grafische Hilfestellung "Es werden
         * alle in diesem Zug bedrohten Figuren angezeigt."
         */
        // Liste mit den bedrohten Figuren anlegen
        List<Feld> bedrohteFelder = new ArrayList<Feld>();
        // Liste mit den gegnerischen Figuren
        List<Figur> gegnerFiguren;
        // Wenn weiss als naechstes dran ist
        if (aktuellerSpieler) {
            gegnerFiguren = clone(schwarzeFiguren);
        // Wenn schwarz als naechstes dran ist
        } else {
            gegnerFiguren = clone(weisseFiguren);
        }
        // Fuer alle gegnerischen Figuren
        for (Figur gegner : gegnerFiguren) {
            // Liste mit den korrekten Feldern dieser Figur
            List<Feld> felder = gegner.getMoeglicheFelderKI();
            // Fuer jedes dieser Felder
            for (Feld feld : felder) {
                // Wenn auf dem Feld eine Figur steht
                if (feld.getFigur() != null) {
                    // Ist das meine und somit bedroht
                    if (gegner.isKorrektesFeld(feld)) {
                        bedrohteFelder.add(feld);
                    }
                }
            }
        }
      
        return bedrohteFelder;
    }
    
    /**
     * Gibt eine Liste mit den Feldern zur&uuml;ck, auf denen gegnerische 
     * Figuren stehen und in diesem Zug vom aktiven Spieler geschlagen
     * werden k&ouml;nnen.<br>
     * Verwendet wird diese Methode ausschlie&szlig;lich vom Computergegner
     * Karl Heinz, um zu ermitteln, welche Figuren er schlagen kann. Es besteht
     * jedoch die M&ouml;glichkeit, das Programm so zu erweitern, dass auch dem 
     * menschlichen Spieler diese Felder als Hilfestellung angezeigt werden.
     * @return Eine Liste mit den Feldern auf denen zu schlagende Figuren stehen
     */
    public List<Feld> getSchlagendeFelder() {
        // Liste mit zu schlagenden Figuren erstellen
        List<Feld> schlagendeFelder = new ArrayList<Feld>();
        // Liste mit den eigenen Figuren
        List<Figur> eigeneFiguren;
        // Wenn weiss als naechstes dran ist
        if (aktuellerSpieler) {
            eigeneFiguren = clone(weisseFiguren);
        // Wenn schwarz als naechstes dran ist
        } else {
            eigeneFiguren = clone(schwarzeFiguren);
        }
        // Fuer alle eigenen Figuren
        for (Figur eigen : eigeneFiguren) {
            // Liste mit den korrekten Feldern dieser Figur
            List<Feld> felder = eigen.getMoeglicheFelderKI();
            // Fuer jedes dieser Felder
            for (Feld feld : felder) {
                // Wenn auf dem Feld eine Figur steht
                if (feld.getFigur() != null && feld.getFigur().getWert() != 0) {
                    // Ist das eine gegnerische und somit schlagbar
                    if (eigen.isKorrektesFeld(feld)) {
                        schlagendeFelder.add(feld);
                    }
                }
            }
        }
        return schlagendeFelder;
    }
    
    /**
     * Gibt eine mehrzeilige Zeichenkette mit allen wichtigen Daten zur&uuml;ck.
     * <br> Wird beim Speichern ben&ouml;tigt. Dabei wird jedes 
     * Einstellungsattribut und jeder Zug in einer Zeile gespeichert.
     * @return Eine mehrzeilige Zeichenkette
     * @see Einstellungen#toString()
     * @see Spieldaten#toString()
     */
    public String toString() {
        String string = "";
        String lineSep = System.getProperty("line.separator");
        string += einstellungen.toString() + lineSep;
        string += spieldaten.toString();
        return string;
    }
    
    /**
     * Klont die angegebene Liste damit keine CurrentModificationException bei
     * Schleifendurchl&auml;ufen auftritt.
     * @param figuren Die zu klonende Figuren-Liste
     * @return Der Klon der Figuren-Liste
     */
    public List<Figur> clone(List<Figur> figuren) {
        List<Figur> figurenCopy = new ArrayList<Figur>();
        figurenCopy.addAll(figuren);
        return figurenCopy;
    }
    
    /**
     * Sortiert eine Liste von Figuren aufsteigend nach Wert. <br>
     * Verwendet den rekursiven Bubble-Sort-Algorithmus.
     * @param figuren Eine Liste von Figuren die sortiert werden soll
     * @return Die sortierte Liste
     */
    private List<Figur> sortiereListe(List<Figur> figuren) {
        // Temporaere Figur
        Figur temp;
        for (int i = 0; i < figuren.size() - 1; i++) {
            // Wenn zwei Figuren in der falschen Reihenfolge sind
            if (figuren.get(i).getWert() > figuren.get(i + 1).getWert()) {
                // Werden sie getauscht
                temp = figuren.get(i);
                figuren.set(i, figuren.get(i + 1));
                figuren.set(i + 1, temp);
                // Rekursiver Aufruf
                sortiereListe(figuren);
            }
        }
        return figuren;
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
     * Gibt die Einstellungen des Spiels zur&uuml;ck.
     * @return Die Einstellungen
     */
    public Einstellungen getEinstellungen() {
        return einstellungen;
    }

    /**
     * Setzt die Einstellungen des Spiels.
     * @param einstellungen Die Einstellungen
     */
    public void setEinstellungen(Einstellungen einstellungen) {
        this.einstellungen = einstellungen;
    }
    
    /**
     * Gibt die Liste der Felder zur&uuml;ck.
     * @return Liste von Feldern
     */
    public List<Feld> getFelder() {
        return felder;
    }
    
    /**
     * Gibt die wei&szlig;en noch im Spiel befindlichen Figuren zur&uuml;ck.
     * @return Liste von wei&szlig;en Figuren
     */
    public List<Figur> getWeisseFiguren() {
        return sortiereListe(weisseFiguren);
    }
    
    /**
     * Gibt die schwarzen noch im Spiel befindlichen Figuren zur&uuml;ck.
     * @return Liste von schwarzen Figuren
     */
    public List<Figur> getSchwarzeFiguren() {
        return sortiereListe(schwarzeFiguren);
    }
    
    /**
     * Gibt die geschlagenen wei&szlig;en Figuren zur&uuml;ck.
     * @return Liste von wei&szlig;en Figuren
     */
    public List<Figur> getGeschlagenWeiss() {
        return geschlagenWeiss;
    }
    
    /**
     * Gibt die geschlagenen schwarzen Figuren zur&uuml;ck.
     * @return Liste von schwarzen Figuren
     */
    public List<Figur> getGeschlagenSchwarz() {
        return geschlagenSchwarz;
    }
    
    /**
     * Gibt die geschlagenen wei&szlig;en Figuren nach aufsteigendem Wert
     * zur&uuml;ck.
     * @return Liste von wei&szlig;en Figuren
     */
    public List<Figur> getGeschlagenWeissSort() {
        return sortiereListe(clone(geschlagenWeiss));
    }
    
    /**
     * Gibt die geschlagenen schwarzen Figuren nach aufsteigendem Wert 
     * zur&uuml;ck.
     * @return Liste von schwarzen Figuren
     */
    public List<Figur> getGeschlagenSchwarzSort() {
        return sortiereListe(clone(geschlagenSchwarz));
    }
    
    /**
     * Gibt an, welcher Spieler am Zug ist.
     * @return <b>true</b> f&uuml;r wei&szlig;, <b>false</b> f&uuml;r schwarz
     */
    public boolean getAktuellerSpieler() {
        return aktuellerSpieler;
    }
    
    /**
     * Wird beim Editieren verwendet.
     * @param aktuellerSpieler Der aktuelle Spieler
     */
    public void setAktuellerSpieler(boolean aktuellerSpieler) {
        this.aktuellerSpieler = aktuellerSpieler;
    }

    /**
     * Gibt an, ob der aktuelle Spieler im Schach steht.<br>
     * Wird nur f&uuml;r die Konversation zwischen Algorithmik und GUI 
     * verwendet.
     * @return Wahrheitswert
     */
    public boolean isSchach() {
        List<Feld> bedrohteFelder = getBedrohteFelder();
        for (Feld bedroht : bedrohteFelder) {
            // Wenn es der Koenig ist, steht er nun im Schach
            if (bedroht.getFigur().getWert() == 0) {
                schach = true;
            }
        }
        return schach;
    }

    /**
     * Setzt, ob der aktuelle Spieler im Schach steht. <br>
     * Wird ausschlie&szlig;lich von der GUI und vom Computerspieler aufgerufen
     * um das Attribut wieder auf <b>false</b> zu setzen, wenn die 
     * Schachwarnung verarbeitet wurde.
     * @param schach Wahrheitswert
     * @see gui.SpielfeldGUI#schachWarnung()
     */
    public void setSchach(boolean schach) {
        this.schach = schach;
    }
    
}
