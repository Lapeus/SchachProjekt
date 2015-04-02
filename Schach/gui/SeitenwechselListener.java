package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action Listener, der das Wechseln zwischen den verschiedenen Seiten 
 * verarbeitet.
 * @author Marvin Wolf
 */
public class SeitenwechselListener implements ActionListener {
    // Anfang Attribute
    
    /**
     * Eltern-Fenster in dem die Seiten gewechselt werden sollen.
     */
    private SpielGUI parent;
    
    // Ende Atribute
    
    /**
     * Erzeugt einen neuen Seitenwechsel-Listener.
     * @param parent Eltern-Fenster in dem die Seiten gewechselt werden sollen.
     */
    public SeitenwechselListener(SpielGUI parent) {
        this.parent = parent;
    }
    
    /**
     * Action Performed, welche das Wechseln der Seite an die 
     * Eltern-SpielGUI-Methode {@link SpielGUI#seitenAuswahl(String)} 
     * &uuml;bergibt.
     * @param e Ausgel&ouml;stes ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        /* Je nach Action Command wird die seitenAuswahl-Methode mit passendem
         *command aufgerufen
         */
        if (command == "Eroeffnungsseite") {
            parent.seitenAuswahl("Eroeffnungsseite");
        } else if (command == "Spielen") {
            parent.seitenAuswahl("Spielerauswahl");
        } else if (command == "Spiel laden") {
            parent.seitenAuswahl(command);
        } else if (command == "Einstellungen") {
            parent.seitenAuswahl(command);
        } else if (command == "Highscore") {
            parent.seitenAuswahl(command);
        } else if (command == "Statistiken") {
            parent.seitenAuswahl(command);
        } else if (command == "Spiel starten") {
            parent.seitenAuswahl("SpielfeldGUI");
        } 
    }

}
