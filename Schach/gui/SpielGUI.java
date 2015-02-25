package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

public class SpielGUI extends JFrame {
    
    // Anfang Attribute
    private static final long serialVersionUID = -8895681303810255159L;
    // Ende Attribute

    // Konstruktor
    
    public SpielGUI() {
        super("Schachspiel");
        seitenAuswahl("Eroeffnungsseite");
        Dimension minimaleGroesse = new Dimension(500, 300);
        setMinimumSize(minimaleGroesse);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    // Anfang Methoden
    public void seitenAuswahl(String auswahl) {
        switch (auswahl) {
        case "Eroeffnungsseite":
            initEroeffnungsseite();
            break;
            
        case "Spielerauswahl":
            initSpielerauswahl();
            break;
            
        case "SpielLaden":
            initSpielLaden();
            break;
            
        case "Einstellungen":
            initEinstellungen();
            break;

        default:
            initEroeffnungsseite();
            break;
        }
        this.revalidate();
        
        
    }
    
    private void initEroeffnungsseite() {
        Container eroeffnungsseite = new Eroeffnungsseite(this);
        this.setContentPane(eroeffnungsseite);
    }
    
    private void initSpielerauswahl() {
        Container spielerauswahl = new Spielerauswahl(this);
        this.setContentPane(spielerauswahl);
    }
    
    private void initSpielLaden() {
        Container spielLaden = new SpielLaden(this);
        this.setContentPane(spielLaden);
    }
    
    private void initEinstellungen() {
        Container einstellungen = new Einstellungen(this);
        this.setContentPane(einstellungen);
    }
    
    // Ende Methoden
    
    public static void main(String[] args) {
        new SpielGUI();
    }
}
