package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action Listener, der das Wechseln zwischen den verschiedenene Seiten 
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
     * @param parent Eltern Fenster in dem die Seiten gewechselt werden sollen.
     */
    public SeitenwechselListener(SpielGUI parent) {
        this.parent = parent;
    }
    
    /**
     * Action Performed, welche das wechseln der Seite an die Eltern-SpielGUI 
     * Übergeben.
     * @param e Ausgeloestes ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == "Eroeffnungsseite") {
            parent.seitenAuswahl("Eroeffnungsseite");
        }
        if (command == "Spielen") {
            parent.seitenAuswahl("Spielerauswahl");
        }
        if (command == "Spiel laden") {
            parent.seitenAuswahl(command);
        }
        if (command == "Einstellungen") {
            parent.seitenAuswahl(command);
        }
        if (command == "Highscore") {
            parent.seitenAuswahl(command);
        }
        if (command == "Statistiken") {
            parent.seitenAuswahl(command);
        }
        if (command == "Spiel starten") {
            parent.seitenAuswahl("SpielfeldGUI");
        } 
        if (command == "spielende") {
            parent.autoSaveLoeschen();
            parent.seitenAuswahl("Eroeffnungsseite");
        }
    }

}
