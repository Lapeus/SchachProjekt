package daten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import zuege.KIZug;
import figuren.Figur;
import gui.Feld;

/**
 * Stellt eine Hilfsklasse dar, die die Zugsortierung f&uuml;r die KI 
 * &uuml;bernimmt. 
 */
public class Zugsortierer {
    
    /**
     * Liste von m&ouml;glichen KIZ&uuml;gen.
     */
    private List<KIZug> figurenUndFelder = new ArrayList<KIZug>();
   
    /**
     * Erstellt einen neuen Zugsortierer.<br>
     * Einziger Konstruktor dieser Klasse.
     * @param figuren Liste von Figuren
     * @param sort Ob sortiert werden soll
     */
    public Zugsortierer(List<Figur> figuren, boolean sort) {
        for (Figur figur : figuren) {
            for (Feld feld : figur.getKorrekteFelder()) {
                figurenUndFelder.add(new KIZug(figur, feld));
            }
        }
        if (sort) {
            bewerten();
            quicksort(0, figurenUndFelder.size() - 1);  
        }
        
    }
    
    /**
     * Bewertet die verschiedenen Z&uuml;ge.
     */
    private void bewerten() {
        for (KIZug zug : figurenUndFelder) {
            // Wenn ein Bauer zieht
            if (zug.getFigur().getWert() == 100) {
                // Umwandlung
                if ((zug.getFigur().getFarbe() && zug.getFeld().getYK() == 7)
                    || (!zug.getFigur().getFarbe() && zug.getFeld().getYK() 
                        == 0)) {
                    // Eine Figur wird geschlagen
                    if (zug.getFeld().getFigur() != null) {
                        // 1000 plus Wert der Figur
                        zug.setBewertung(1000 
                            + zug.getFeld().getFigur().getWert());
                    } else {
                        // Nur 1000
                        zug.setBewertung(1000);
                    }
                // Keine Umwandlung
                } else {
                    // Wenn es ein Schlagzug ist
                    if (zug.getFeld().getFigur() != null) {
                        // Der Gewinn dieses Zuges
                        zug.setBewertung(
                            zug.getFeld().getFigur().getWert() - 100);
                    } else {
                        // Doppelschritt
                        if (!zug.getFigur().getGezogen()
                            && (zug.getFeld().getYK() == 3 
                            || zug.getFeld().getYK() == 4)) {
                            zug.setBewertung(-30);
                        // Normaler Schritt
                        } else {
                            zug.setBewertung(-35);
                        }
                    }
                }
            // Wenn ein Koenig zieht
            } else if (zug.getFigur().getWert() == 0) {
                // RochadenFelder
                List<Integer> rochadenFelder = Arrays.asList(2, 6, 58, 62);
                // Wenn er noch nicht gezogen wurde und es ein RochadenFeld ist
                if (!zug.getFigur().getGezogen()
                    && rochadenFelder.contains(zug.getFeld().getIndex())) {
                    zug.setBewertung(-10);
                // Koenigsschlagzuege
                } else if (zug.getFeld().getFigur() != null) {
                    zug.setBewertung(25);
                // Koenigszuege
                } else {
                    zug.setBewertung(-40);
                }
            // Wenn eine andere Figur zieht
            } else {
                // Wenn es ein Schlagzug ist
                if (zug.getFeld().getFigur() != null) {
                    zug.setBewertung(zug.getFigur().getWert()
                        - zug.getFeld().getFigur().getWert());
                } else {
                    zug.setBewertung(-20);
                }
            }
        }
    }
    
    /**
     * Implementierung des Quicksort-Algorithmus' zur Sortierung der Z&uuml;ge
     * nach Bewertung.
     * @param links Linke Grenze
     * @param rechts Rechte Grenze
     */
    private void quicksort(int links, int rechts) {
        if (links < rechts) {
            int teiler = teile(links, rechts);
            quicksort(links, teiler - 1);
            quicksort(teiler + 1, rechts);
        }
    }
    
    /**
     * Ermittelt den Index des Pivotelementes f&uuml;r die Quicksort-Methode.
     * @param links Linke Grenze
     * @param rechts Rechte Grenze
     * @return Index des Pivotelementes
     */
    private int teile(int links, int rechts) {
        int i = links;
        int j = rechts - 1;
        KIZug pivot = figurenUndFelder.get(rechts);
        do {
            while (figurenUndFelder.get(i).getBewertung() 
                <= pivot.getBewertung() && i < rechts) {
                i++;
            }
            while (figurenUndFelder.get(j).getBewertung() 
                >= pivot.getBewertung() && j > links) {
                j--;
            }
            if (i < j) {
                KIZug temp = figurenUndFelder.get(i);
                figurenUndFelder.set(i, figurenUndFelder.get(j));
                figurenUndFelder.set(j, temp);
            }
        } while (i < j);
        if (figurenUndFelder.get(i).getBewertung() > pivot.getBewertung()) {
            KIZug temp = figurenUndFelder.get(i);
            figurenUndFelder.set(i, figurenUndFelder.get(rechts));
            figurenUndFelder.set(rechts, temp);
        }
        
        return i;
    }
    
    /**
     * Gibt den KIZug an der entsprechenden Stelle zur&uuml;ck.
     * @param index Ganzzahliger Index
     * @return Der KIZug
     */
    public KIZug get(int index) {
        return figurenUndFelder.get(index);
    }
    
    /**
     * Gibt die L&auml;nge der Liste und damit die Anzahl der m&ouml;glichen
     * Z&uuml;ge zur&uuml;ck.
     * @return Anzahl der m&ouml;glichen Z&uuml;ge
     */
    public int getSize() {
        return figurenUndFelder.size();
    }
   
}
