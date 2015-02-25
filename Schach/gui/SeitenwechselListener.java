package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeitenwechselListener implements ActionListener {
    SpielGUI parent;
    
    public SeitenwechselListener(SpielGUI parent) {
        this.parent = parent;
    }
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); 
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
        if (command == "Regelwerk") {
            parent.seitenAuswahl(command);
        }
        if (command == "Spiel starten") {
            parent.seitenAuswahl("SpielfeldGUI");
        } 
    }

}
