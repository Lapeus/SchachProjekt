package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeitenwechselListener implements ActionListener {
    SpielGUI parent;
    
    public SeitenwechselListener(SpielGUI parent) {
        this.parent = parent;
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Spielen") {
            parent.seitenAuswahl("Spielerauswahl");
        }
        if (e.getActionCommand() == "Spiel laden") {
            parent.seitenAuswahl("SpielLaden");
        }
        if (e.getActionCommand() == "Einstellungen") {
            parent.seitenAuswahl("Einstellungen");
        }
        if (e.getActionCommand() == "Highscore") {
            parent.seitenAuswahl("Highscore");
        }
        if (e.getActionCommand() == "Statistiken") {
            parent.seitenAuswahl("Statistiken");
        }
        if (e.getActionCommand() == "Regelwerk") {
            parent.seitenAuswahl("Regelwerk");
        }
    }

}
