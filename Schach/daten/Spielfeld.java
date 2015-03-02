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
 * Gibt zus&auml;tzlich an, ob das Brett aktuell aus wei&szlig;er oder aus
 * schwarzer Sicht gezeigt wird.
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
     * @param felder : Die Liste mit den Feldern, auf die die Figuren gestellt
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
     * @param figur : Die Figur, die gezogen werden soll
     * @param zielfeld : Das Feld, auf das die Figur gezogen werden soll
     */
    public void ziehe(Figur figur, Feld zielfeld) {
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
        if (!figur.getGezogen()) {
            // Zug bekommt uebergeben, dass es der erste Zug dieser Figur ist
            zug = new Zug(figur.getPosition(), zielfeld, figur, schlagzug,  0,
                true);
        } else {
            // Normale Zugerstellung
            zug = new Zug(figur.getPosition(), zielfeld, figur, schlagzug,  0);
        }
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
     * Macht den jeweils letzten Zug r&uuml;ckg&auml;ngig.
     */
    public void zugRueckgaengig() {
        // Rufe den letzten durchgefuehrten Zug auf
        Zug zug = spieldaten.getZugListe()
            .get(spieldaten.getZugListe().size() - 1);
        // Loesche ihn aus der Liste
        spieldaten.getZugListe().remove(zug);
        // Mache ihn rueckgaengig (ziehe Methode von hinten)
        
        // Aktiver Spieler muss geaendert werden.
        aktuellerSpieler = !aktuellerSpieler;
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
            // Sie auf das Zielfeld des letzten Zugs setzen
            zug.getZielfeld().setFigur(geschlageneFigur);
        }
        
    }
    
    /**
     * Dreht das Spielfeld, sodass es nun vom aktiven Spieler aus gesehen wird.
     */
    private void dreheSpielfeld() {
        
    }
    
    public Feld getAktuellenKoenig() {
        Feld feld;
        // Wenn weiß dran ist
        if (aktuellerSpieler) {
            // Das Feld der letzten Figur (Koenig immer ganz hinten)
            feld = weisseFiguren.get(weisseFiguren.size() - 1)
                .getPosition();
        // Wenn schwarz dran ist
        } else {
            // Das Feld der letzten Figur (Koenig immer ganz hinten)
            feld = schwarzeFiguren.get(schwarzeFiguren.size() - 1)
                .getPosition();
        }
        return feld;
    }
    
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
    
    private List<Figur> sortiereListe(List<Figur> figuren) {
        Collections.sort(figuren, new FigurenComparator());
        return figuren;
    }
    
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
            // Ist er matt gesetzt
            matt = true;
            // PRUEFUNG AUF PATT
        }
        return matt;
    }
    public Spieldaten getSpieldaten() {
        return spieldaten;
    }

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
        return sortiereListe(geschlagenSchwarz);
    }
    
    /**
     * Gibt die geschlagenen wei&szlig;en Figuren zur&uuml;ck.
     * @return Liste von wei&szlig;en Figuren
     */
    public List<Figur> getGeschlagenWeiss() {
        return sortiereListe(geschlagenWeiss);
    }
    
    
    public List<Feld> getBedrohteFelder() {
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
     * Setzt den aktiven Spieler und dreht das Spielfeld.
     * @param aktuellerSpieler : <b>true</b> f&uuml;r wei&szlig;, <b>false</b> 
     * f&uuml;r schwarz
     */
    public void setAktuellerSpieler(boolean aktuellerSpieler) {
        this.aktuellerSpieler = aktuellerSpieler;
        dreheSpielfeld();
    }

    public boolean isSchach() {
        return schach;
    }

    public void setSchach(boolean schach) {
        this.schach = schach;
    }

    
}