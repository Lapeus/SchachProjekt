package daten;

/**
 * Verwaltet alle spielbezogenen Einstellungen. <br>
 * Unter anderem wird gespeichert, ob und wenn ja, welche Profiregeln 
 * zul&auml;ssig sind und welche grafischen Hilfestellungen es geben soll.
 * @author Christian Ackermann
 */
public class Einstellungen {

    /**
     * Erzeugt einen neuen Satz Einstellungen, der sp&auml;ter einem Spiel 
     * hinzugef&uuml;gt werden kann.<br>
     * Einziger Konstruktor dieser Klasse.
     */
    public Einstellungen() {
        // Zugzeitbegrenzung getter/setter           (int)
        // Mögliche Felder anzeigen getter/setter    (boolean)
        // Bedrohte Figuren anzeigen getter/setter   (boolean)
        // Rochade Benutzbar getter/setter           (boolean)
        // Schachwarnung getter/setter               (boolean)
        // En-Passant-Schlagen getter/setter         (boolean)
        // Statistik (einbezogen) getter/seter       (boolean)
    }
}
