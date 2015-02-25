package gui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

public class SpielGUI extends JFrame {
    
    // Anfang Attribute
    private static final long serialVersionUID = -8895681303810255159L;
    private SpielfeldGUI spielfeld;
    // Ende Attribute

    // Konstruktor
    
    public SpielGUI() {
        super("Schachspiel");
        seitenAuswahl("Eroeffnungsseite");
        Dimension minimaleGroesse = new Dimension(500, 500);
        setMinimumSize(minimaleGroesse);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    // Anfang Methoden
    public void seitenAuswahl(String auswahl) {
        Container seite;
        switch (auswahl) {
        case "Eroeffnungsseite":
            seite = new Eroeffnungsseite(this);
            this.setContentPane(seite);
            break;
            
        case "Spielerauswahl":
            seite = new Spielerauswahl(this);
            this.setContentPane(seite);
            break;
            
        case "Spiel laden":
            seite = new SpielLaden(this);
            this.setContentPane(seite);
            break;
            
        case "Einstellungen":
            seite = new Einstellungen(this);
            this.setContentPane(seite);
            break;
            
        case "Highscore":
            seite = new Highscore(this);
            this.setContentPane(seite);
            
        case "Statistiken":
            seite = new Statistiken(this);
            this.setContentPane(seite);
            break;
            
        case "Regelwerk":
            seite = new Statistiken(this);
            this.setContentPane(seite);
            break;
            
        case "SpielfeldGUI":
            spielfeld = new SpielfeldGUI(this);
            this.setContentPane(spielfeld);
            break;
            

        default:
            break;
        }
        this.revalidate();
        this.paint(getGraphics());
    }
    
    
    
    // Ende Methoden
    
    public static void main(String[] args) {
        new SpielGUI();
    }
}
