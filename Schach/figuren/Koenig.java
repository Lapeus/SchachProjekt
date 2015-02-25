package figuren;

import gui.Feld;

import java.util.List;

public class Koenig extends Figur {

    public Koenig(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(0); // Wert des Koenigs ist irrelevant
        super.setGezogen(false);
    }
    
    public void praePruefung() {
        // TODO Auto-generated method stub
        
    }

    public List<Feld> getMoeglicheFelder() {
        // TODO Auto-generated method stub
        return null;
    }

    public void postPruefung() {
        // TODO Auto-generated method stub
        
    }
    

}
