package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import daten.Spiel;
import daten.Spieler;
import daten.Spielfeld;


public class SpielfeldGUI extends JPanel {

    private static final long serialVersionUID = -5131381120236108231L;
    private SpielGUI parent;
    private Spieler spieler1;
    private Spieler spieler2;
    private List<Feld> felderListe;
    private Spielfeld spielfeld;
    private Spiel spiel;
    
    
    public SpielfeldGUI(SpielGUI parent) {
        super();
        this.parent = parent;
        init();
    }
    
    private void init() {
        spieler1 = new Spieler("Spieler 1");
        spieler2 = new Spieler("Spieler 2");
        
        // Farbe einstellen
        spieler1.setFarbe(true);
        spieler2.setFarbe(false);   
        
        // FelderListe füllen
        felderListe = new ArrayList<Feld>();
        fuelleFelderListe();
        
        // Spielfeld
        spielfeld = new Spielfeld(felderListe);
        
        // Spiel 
        spiel = new Spiel("Test", spieler1, spieler2, spielfeld);
        
        // SpielfeldGUI erstellen 
        
        this.setLayout(new BorderLayout());
        Container cCenter = new JPanel();
        cCenter.setLayout(new GridLayout(0, 8));
        Color braun = new Color(181, 81, 16);
        Color weiss = new Color(255, 248, 151);
        boolean abwechslung = false;
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            abwechslung = !abwechslung;
            for (int j = 0; j < 8; j++) {
//                JLabel temp = felderListe.get(counter);
                Feld temp = felderListe.get(counter);
                temp.setOpaque(true);
                counter++;
                if (!abwechslung) {
                    temp.setBackground(braun);
                    abwechslung = true;
                    
                } else {
                    temp.setBackground(weiss);
                    abwechslung = false;
                }
                cCenter.add(temp);
            }    
        }
        this.add(cCenter, BorderLayout.CENTER);
        
        
        /*
        this.setLayout(new GridLayout(8, 8));
        int c = 0;
        for (int i = 0; i < 64; i++) {
            Color braun = new Color(205, 102, 29);
            JLabel temp = new JLabel();
            temp.setOpaque(true);
            temp.setBackground(braun);
            this.add(temp);
            c++;
        }
        */
  
    }
    
    private void fuelleFelderListe() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                felderListe.add(new Feld(i, j));
                
            }    
        }
    }
    
}
