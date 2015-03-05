package figuren;

import gui.Feld;

import java.util.ArrayList;
import java.util.List;

import daten.Spielfeld;

/**
 * Eine Klasse die alle Spielfiguren verwaltet.
 * Die abstrakte Klasse <i>Figur</i> stellt den Bauplan 
 * für s&auml;mtliche Figuren bereit.
 * @author Christian Ackermann
 */
public abstract class Figur {

    /**
     * Das Spielbrett, zu dem die Figur geh&ouml;rt.
     */
    private Spielfeld spielfeld;
    
    /**
     * Das Feld, auf dem die Figur momentan steht. <br>
     * Wurde eine Figur geschlagen, wird das letzte Feld auf dem sie stand,
     * beibehalten, um diesen Zug r&uuml;ckg&auml;ngig machen zu k&ouml;nnen
     */
    private Feld position;
    
    /**
     * Die Farbe der Figur. <br>
     * <b>True</b> steht für wei&szlig;, <b>False</b> steht für schwarz.
     */
    private boolean farbe;
    
    /**
     * Der materielle Wert der Figur. <br>
     * Wird haupts&auml;chlich für die Bewertungsfunktion des Computergegners
     * ben&ouml;tigt und basiert auf stochastischen Erhebungen. 
     * Dient zum anderen der einfachen Identifikation der Figur ohne Verwendung
     * des <b>instanceof</b> Operators.
     * Die Werte sind aus den gegebenen Internetquellen &uuml;bernommen.
     */
    private int wert;
    
    /**
     * Gibt an, ob die Figur in diesem Spiel schon einmal gezogen wurde. <br>
     * Dies ist vorallem f&uuml;r die Rochade und f&uuml;r die jeweils ersten
     * Z&uuml;ge der Bauern wichtig.
     */
    private boolean bereitsGezogen;
    
    
    /**
     * Berechnet alle Felder, auf die die Figur nach den
     * Zugregeln ziehen kann.
     * @return Liste von m&ouml;glichen Feldern
     */
    protected abstract List<Feld> getMoeglicheFelder();
    /* Diese Methode sieht fuer Dame, Turm und Laeufer ziemlich aehnlich aus.
     * Es werden wie bei allen Figuren alle Richtungen durchgegangen und dann
     * mittels einer while-Schleife vervielfacht. Dabei ist die Abbruchbedingung
     * das Erreichen einer Figur oder des Spielfeldrandes.
     * 
     * Die Methoden fuer Springer und Koenig aehneln sich ebenfalls, da hier 
     * nur einmal in jede moegliche Richtung gezogen wird, sofern dieses Feld
     * existiert und frei ist.
     * 
     * Die Methode fuer den Bauern ist etwas anders, da hier wichtig ist, ob
     * es ein weisser oder ein schwarzer Bauer ist. Ausserdem muss unterschieden
     * werden, ob ein Feld frei ist, oder ob eine gegnerische Figur dort steht.
     * Bauern duerfen schliesslich nur schraeg gezogen werden, wenn sie dabei 
     * eine Figur schlagen koennen.
     */
    
    /**
     * Pr&uuml;ft alle Felder die die Methode <i>getMoeglicheFelder</i> 
     * vorschl&auml;gt, ob nach dem Zug der eigene K&ouml;nig im Schach stehen
     * w&uuml;rde.
     * @return Liste der tats&auml;chlich ziehbaren Felder
     */
    public List<Feld> getKorrektFelder() {
        // Uebergabe der zu pruefenden Felder
        List<Feld> korrekt = this.getMoeglicheFelder();
        // Das Feld auf dem der eigene Koenig steht
        Feld koenigsfeldEigen;
        // Wenn weiss dran ist
        if (farbe) {
            // Der Koenig ist immer das erste Element der Liste
            koenigsfeldEigen = spielfeld.getWeisseFiguren().get(0).position;
        // Wenn schwarz dran ist
        } else {
            // Der Koenig ist immer das erste Element der Liste
            koenigsfeldEigen = spielfeld.getSchwarzeFiguren().get(0).position;
        }
        /*<Simuliere jeden Zug und pruefe anschliessend erneut mit
         * getMoeglicheFelder, ob diesmal jemand auf das Koenigsfeld ziehen
         * kann.>
         */
        /* Man darf waehrend einer Listen-Iteration nicht die Liste veraendern.
         * Daher muss eine neue Liste erzeugt werden, mit den Feldern die 
         * spaeter entfernt werden sollen.
         */
        List<Feld> nichtKorrekt = new ArrayList<Feld>();
        // Wenn es moegliche Zuege gibt
        if (!korrekt.isEmpty()) {
            // Fuer jeden vorgeschlagenen Zug
            for (Feld feld : korrekt) {
                // Simuliere einen Zug (Zugzeit 0)
                spielfeld.ziehe(this, feld, 0);
                /* Wenn der Koenig gezogen hat, muss das Feld aktualisiert 
                 * werden.
                 */
                if (this.getWert() == 0) {
                    koenigsfeldEigen = feld;
                }
                //Pruefe alle moeglichen Felder
                // Fuer alle gegnerischen Figuren
                // Liste mit allen gegnerischen Figuren
                List<Figur> gegner;
                if (farbe) {
                    gegner = spielfeld.getSchwarzeFiguren();
                } else {
                    gegner = spielfeld.getWeisseFiguren();
                }
                // Fuer alle gegnerischen Figuren
                for (Figur figur : gegner) {
                    // Liste mit allen moeglichen Feldern der aktuellen Figur
                    List<Feld> felder = figur.getMoeglicheFelder();
                    // Wenn die Figur auf das Koenigsfeld ziehen koennte
                    if (felder.contains(koenigsfeldEigen)) {
                        /* Ist dieser Zug nicht zulaessig und er muss aus der 
                         * Liste geloescht werden.
                         */
                        nichtKorrekt.add(feld);
                    }
                }
                // Mache den Zug wieder rueckgaengig
                spielfeld.zugRueckgaengig();
            }
        }
        // Jetzt koennen die nicht zulaessigen Felder entfernt werden
        korrekt.removeAll(nichtKorrekt);
        
        /* Zusatz: Vorbereitung fuer die grafische Hilfestellung "Es werden
         * die Figuren gezeigt, die von dieser Figur geschlagen werden koennen."
         */
        // Liste mit den in diesem Zug zu schlagenden Feldern leeren
        spielfeld.getSchlagendeFelder().clear();
        // Wenn es moegliche Zuege gibt
        if (!korrekt.isEmpty()) {
            for (Feld feld : korrekt) {
                // Wenn das Feld nicht leer ist
                if (feld.getFigur() != null) {
                    // Fuege das Feld den zu schlagenden Feldern zu
                    spielfeld.getSchlagendeFelder().add(feld);
                }
            }
        }
        // Gib die korrigierte Liste zurueck
        return korrekt;
        
    }
    
    /**
     * Gibt an, ob das Feld am angegebenen Index leer ist.
     * @param index : Ganzzahliger Index (zwischen 0 und 63)
     * @return <b>true</b> wenn frei
     */
    protected boolean istFrei(int index) {
        boolean frei = false;
        if (getFigurAt(index) == null) {
            frei = true;
        }
        return frei;
    }
    
    /**
     * Gibt an, ob das Feld am angegebenen Index nicht von einer eigenen Figur
     * besetzt ist.
     * Wenn <b>true</b> zur&uuml;ck gegeben wird, bedeutet das, dass dieses
     * Feld f&uuml;r einen Zug zur Verf&uuml;gung steht.
     * @param index : Ganzzahliger Index (zwischen 0 und 63)
     * @return <b>true</b> f&uuml;r leer oder gegnerische Figur
     */
    protected boolean istMoeglich(int index) {
        boolean moeglich = false;
        if (istFrei(index) || getFigurAt(index).getFarbe() != farbe) {
            moeglich = true;
        }
        return moeglich;
    }
    
    /**
     * Gibt das Feld mit dem angegebenen Index zur&uuml;ck.
     * @param index : Der ganzzahlige Index (Zwischen 0 und 63)
     * @return Ein Objekt vom Typ Feld
     */
    protected Feld getFeld(int index) {
        return spielfeld.getFelder().get(index);
    }
    
    /**
     * Gibt den Index des Felder zur&uuml;ck, auf dem die Figur steht.
     * @return Der ganzzahlige Index (Zwischen 0 und 63)
     */
    protected int getFeldIndex() {
        return position.getXK() + position.getYK() * 8;
    }
    
    /**
     * Gibt die Figur auf dem Feld des angegebenen Index' zur&uuml;ck.
     * @param index : Der ganzzahlige Index (Zwischen 0 und 63)
     * @return Eine Figur 
     */
    protected Figur getFigurAt(int index) {
        return getFeld(index).getFigur();
    }
    
    /**
     * Gibt eine Zeichenkette mit allen wichtigen Daten zur&uuml;ck. <br>
     * Wird beim Speichern ben&ouml;tigt.
     * @return Eine mehrzeilige Zeichenkette
     */
    public String toString() {
        String string;
        string = position.getXK() + "" + position.getYK() + "\n";
        string += farbe + "\n";
        string += wert + "\n";
        string += bereitsGezogen;
        return string;
    }
    
    /**
     * Gibt das Spielfeld zur&uuml;ck, auf dem die Figur steht.
     * @return Das Spielfeld
     */
    public Spielfeld getSpielfeld() {
        return spielfeld;
    }
    
    /**
     * Setzt das Spielfeld, auf dem die Figur steht.
     * @param spielfeld : Das zu setzende Spielfeld
     */
    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }
    
    /**
     * Gibt das Feld zur&uuml;ck, auf dem die Figur steht.
     * @return Das Feld
     */
    public Feld getPosition() {
        return position;
    }
    
    /**
     * Setzt das Feld, auf dem die Figur steht.
     * @param position : Das Feld, auf dem die Figur steht
     */
    public void setPosition(Feld position) {
        this.position = position;
    }
    
    /**
     * Gibt an, ob die Figur wei&szlig; oder schwarz ist.
     * @return <b>true</b> f&uuml;r wei&szlig;, <b>false</b> f&uuml;r schwarz
     */
    public boolean getFarbe() {
        return farbe;
    }
    
    /**
     * Setzt die Farbe der Figur.
     * @param farbe : <b>true</b> f&uuml;r wei&szlig;, <b>false</b> 
     * f&uuml;r schwarz
     */
    public void setFarbe(boolean farbe) {
        this.farbe = farbe;
    }
    
    /**
     * Gibt den Wert der Figur zur&uuml;ck.
     * @return Ganzzahliger Wert (Zwischen 0 und 900)
     */
    public int getWert() {
        return wert;
    }
    
    /**
     * Setzt den Wert der Figur.
     * @param wert Der ganzzahlige Wert der Figur (zwischen 0 und 900)
     */
    public void setWert(int wert) {
        this.wert = wert;
    }
    
    /**
     * Gibt an, ob die Figur bereits gezogen wurde.
     * @return Wahrheitswert
     */
    public boolean getGezogen() {
        return bereitsGezogen;
    }
    
    /**
     * Setzt die Variable, ob die Figur schon gezogen wurde.
     * @param bereitsGezogen : Wahrheitswert
     */
    public void setGezogen(boolean bereitsGezogen) {
        this.bereitsGezogen = bereitsGezogen;
    }
 
}