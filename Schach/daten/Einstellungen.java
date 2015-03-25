package daten;

/**
 * Verwaltet alle spielbezogenen Einstellungen. <br>
 * Unter anderem wird gespeichert, ob und wenn ja, welche Profiregeln 
 * zul&auml;ssig sind und welche grafischen Hilfestellungen angezeigt werden 
 * sollen.
 * @author Christian Ackermann
 */
public class Einstellungen {

    /**
     * Gibt die pro Zug maximal zur Verf&uuml;gung stehende Zeit in ganzen 
     * Sekunden an.
     */
    private int zugZeitBegrenzung;
    
    /**
     * Gibt an, ob m&ouml;gliche Felder angezeigt werden sollen.
     */
    private boolean moeglicheFelderAnzeigen;
    
    /**
     * Gibt an, ob bedrohte Figuren angezeigt werden sollen.
     */
    private boolean bedrohteFigurenAnzeigen;
    
    /**
     * Gibt an, ob man eine Rochade ziehen darf.
     */
    private boolean rochadeMoeglich;
    
    /**
     * Gibt an, ob das En-Passant-Schlagen m&ouml;glich ist.
     */
    private boolean enPassantMoeglich;
    
    /**
     * Gibt an, ob angezeigt werden soll, wenn man im Schach steht.
     */
    private boolean schachWarnung;
    
    /**
     * Gibt an, ob das Spiel mit in die Statistik einbezogen werden soll.
     */
    private boolean inStatistikEinbeziehen;
    
    /**
     * Gibt an, ob das Spielfeld bei jedem Zug gedreht werden soll, sodass die
     * eigenen Figuren immer am unteren Bildrand stehen.
     */
    private boolean spielfeldDrehen;
    
    /**
     * Gibt an, ob der Ton an sein soll.
     */
    private boolean ton;
    
    /**
     * Erzeugt einen neuen Satz Einstellungen, der sp&auml;ter einem Spiel 
     * hinzugef&uuml;gt werden kann.<br>
     * Einziger Konstruktor dieser Klasse.
     * Attribute werden mit Standardwerten initialisiert und k&ouml;nnen bei
     * Bedarf &uuml;berschrieben werden.
     */
    public Einstellungen() {
        this.zugZeitBegrenzung = 0;
        this.moeglicheFelderAnzeigen = true;
        this.bedrohteFigurenAnzeigen = false;
        this.rochadeMoeglich = true;
        this.enPassantMoeglich = false;
        this.schachWarnung = true;
        this.inStatistikEinbeziehen = true;
        this.spielfeldDrehen = false;
        this.ton = true;
    }

    /**
     * Gibt die Einstellungen als mehrzeilige Zeichenkette zur&uuml;ck. <br>
     * Dabei wird jedes Attribut in der angegebenen Reihenfolge in eine eigene
     * Zeile geschrieben.
     * @return Eine mehrzeilige Zeichenkette
     */
    public String toString() {
        String string;
        String lineSep = System.getProperty("line.separator");
        string = zugZeitBegrenzung + lineSep;
        string += moeglicheFelderAnzeigen + lineSep;
        string += bedrohteFigurenAnzeigen + lineSep;
        string += rochadeMoeglich + lineSep;
        string += enPassantMoeglich + lineSep;
        string += schachWarnung + lineSep;
        string += inStatistikEinbeziehen + lineSep;
        string += spielfeldDrehen + lineSep;
        string += ton;
        return string;
    }

    /**
     * Gibt die maximale Bedenkzeit pro Zug an.
     * @return Maximale Zugzeit in Sekunden
     */
    public int getZugZeitBegrenzung() {
        return zugZeitBegrenzung;
    }

    /**
     * Setzt die maximale Bedenkzeit pro Zug.
     * @param zugZeitBegrenzung Die maximale Bedenkzeit in ganzen Sekunden
     */
    public void setZugZeitBegrenzung(int zugZeitBegrenzung) {
        this.zugZeitBegrenzung = zugZeitBegrenzung;
    }

    /**
     * Gibt an, ob die GUI m&ouml;gliche Felder anzeigen soll.
     * @return Wahrheitswert
     */
    public boolean isMoeglicheFelderAnzeigen() {
        return moeglicheFelderAnzeigen;
    }

    /**
     * Setzt, ob die GUI m&ouml;gliche Felder anzeigen soll.
     * @param moeglicheFelderAnzeigen Wahrheitswert
     */
    public void setMoeglicheFelderAnzeigen(boolean moeglicheFelderAnzeigen) {
        this.moeglicheFelderAnzeigen = moeglicheFelderAnzeigen;
    }

    /**
     * Gibt an, ob die GUI bedrohte Figuren kenntlich machen soll.
     * @return Wahrheitswert
     */
    public boolean isBedrohteFigurenAnzeigen() {
        return bedrohteFigurenAnzeigen;
    }

    /**
     * Setzt, ob die GUI bedrohte Figuren kenntlich machen soll.
     * @param bedrohteFigurenAnzeigen Wahrheitswert
     */
    public void setBedrohteFigurenAnzeigen(boolean bedrohteFigurenAnzeigen) {
        this.bedrohteFigurenAnzeigen = bedrohteFigurenAnzeigen;
    }

    /**
     * Gibt an, ob eine Rochade erlaubt ist.
     * @return Wahrheitswert
     */
    public boolean isRochadeMoeglich() {
        return rochadeMoeglich;
    }

    /**
     * Setzt, ob eine Rochade erlaubt sein soll.
     * @param rochadeMoeglich Wahrheitswert
     */
    public void setRochadeMoeglich(boolean rochadeMoeglich) {
        this.rochadeMoeglich = rochadeMoeglich;
    }

    /**
     * Gibt an, ob En-Passant-Schlagen erlaubt ist.
     * @return Wahrheitswert
     */
    public boolean isEnPassantMoeglich() {
        return enPassantMoeglich;
    }

    /**
     * Setzt, ob En-Passant-Schlagen erlaubt sein soll.
     * @param enPassantMoeglich Wahrheitswert
     */
    public void setEnPassantMoeglich(boolean enPassantMoeglich) {
        this.enPassantMoeglich = enPassantMoeglich;
    }

    /**
     * Gibt an, ob die GUI eine Warnung ausgeben soll, wenn der K&ouml;nig im
     * Schach steht.
     * @return Wahrheitswert
     */
    public boolean isSchachWarnung() {
        return schachWarnung;
    }

    /**
     * Setzt, ob die GUI eine Warnung ausgeben soll, wenn der K&ouml;nig im
     * Schach steht.
     * @param schachWarnung Wahrheitswert
     */
    public void setSchachWarnung(boolean schachWarnung) {
        this.schachWarnung = schachWarnung;
    }

    /**
     * Gibt an, ob das Spiel in die Statistik mit einbezogen wird.
     * @return Wahrheitswert
     */
    public boolean isInStatistikEinbeziehen() {
        return inStatistikEinbeziehen;
    }

    /**
     * Setzt, ob das Spiel in die Statistik mit einbezogen werden soll.
     * @param inStatistikEinbeziehen Wahrheitswert
     */
    public void setInStatistikEinbeziehen(boolean inStatistikEinbeziehen) {
        this.inStatistikEinbeziehen = inStatistikEinbeziehen;
    }

    /**
     * Gibt an, ob das Spielfeld bei jedem Zug gedreht werden soll.
     * @return Wahrheitswert
     */
    public boolean isSpielfeldDrehen() {
        return spielfeldDrehen;
    }

    /**
     * Setzt, ob das Spielfeld bei jedem Zug gedreht werden soll.
     * @param spielfeldDrehen Wahrheitswert
     */
    public void setSpielfeldDrehen(boolean spielfeldDrehen) {
        this.spielfeldDrehen = spielfeldDrehen;
    }

    /**
     * Gibt an, ob der Ton aktiviert ist.
     * @return Wahrheitswert
     */
    public boolean isTon() {
        return ton;
    }

    /**
     * Setzt, ob der Ton aktiviert sein soll.
     * @param ton Wahrheitswert
     */
    public void setTon(boolean ton) {
        this.ton = ton;
    }
}
