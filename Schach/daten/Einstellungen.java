package daten;

/**
 * Verwaltet alle spielbezogenen Einstellungen. <br>
 * Unter anderem wird gespeichert, ob und wenn ja, welche Profiregeln 
 * zul&auml;ssig sind und welche grafischen Hilfestellungen es geben soll.
 * @author Christian Ackermann
 */
public class Einstellungen {

    /**
     * Gibt die pro Zug maximal zur Verf&uuml;gung stehende Zeit in ganzen 
     * Sekunden an.
     */
    private int zugZeitBegrenzung;
    
    /**
     * Gibt an, ob m&ouml;gliche Felder angezeigt werden sollen..
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
     * Erzeugt einen neuen Satz Einstellungen, der sp&auml;ter einem Spiel 
     * hinzugef&uuml;gt werden kann.<br>
     * Einziger Konstruktor dieser Klasse.
     * @param zzB ZugZeitBegrenzung in ganzen Sekunden
     * @param moeglFelder Ob m&ouml;gliche Felder angezeigt werden sollen
     * @param bedroFelder Ob vom Gegner bedrohte Felder angezeigt werden sollen
     * @param rochMoegl Ob eine Rochade m&ouml;glich ist
     * @param enPassMoegl Ob En-Passant-Schlagen m&ouml;glich ist
     * @param schachWarn Ob eine Warnung angezeigt werden soll, wenn man im
     * Schach steht
     * @param inStat Ob das Spiel mit in die Statistik einbezogen werden soll
     */
    public Einstellungen(int zzB, boolean moeglFelder, boolean bedroFelder, 
        boolean rochMoegl, boolean enPassMoegl, boolean schachWarn, 
        boolean inStat) {
        this.zugZeitBegrenzung = zzB;
        this.moeglicheFelderAnzeigen = moeglFelder;
        this.bedrohteFigurenAnzeigen = bedroFelder;
        this.rochadeMoeglich = rochMoegl;
        this.enPassantMoeglich = enPassMoegl;
        this.schachWarnung = schachWarn;
        this.inStatistikEinbeziehen = inStat;
    }


    public int getZugZeitBegrenzung() {
        return zugZeitBegrenzung;
    }


    public void setZugZeitBegrenzung(int zugZeitBegrenzung) {
        this.zugZeitBegrenzung = zugZeitBegrenzung;
    }


    public boolean isMoeglicheFelderAnzeigen() {
        return moeglicheFelderAnzeigen;
    }


    public void setMoeglicheFelderAnzeigen(boolean moeglicheFelderAnzeigen) {
        this.moeglicheFelderAnzeigen = moeglicheFelderAnzeigen;
    }


    public boolean isBedrohteFigurenAnzeigen() {
        return bedrohteFigurenAnzeigen;
    }


    public void setBedrohteFigurenAnzeigen(boolean bedrohteFigurenAnzeigen) {
        this.bedrohteFigurenAnzeigen = bedrohteFigurenAnzeigen;
    }


    public boolean isRochadeMoeglich() {
        return rochadeMoeglich;
    }


    public void setRochadeMoeglich(boolean rochadeMoeglich) {
        this.rochadeMoeglich = rochadeMoeglich;
    }


    public boolean isEnPassantMoeglich() {
        return enPassantMoeglich;
    }


    public void setEnPassantMoeglich(boolean enPassantMoeglich) {
        this.enPassantMoeglich = enPassantMoeglich;
    }


    public boolean isSchachWarnung() {
        return schachWarnung;
    }


    public void setSchachWarnung(boolean schachWarnung) {
        this.schachWarnung = schachWarnung;
    }


    public boolean isInStatistikEinbeziehen() {
        return inStatistikEinbeziehen;
    }


    public void setInStatistikEinbeziehen(boolean inStatistikEinbeziehen) {
        this.inStatistikEinbeziehen = inStatistikEinbeziehen;
    }
}
