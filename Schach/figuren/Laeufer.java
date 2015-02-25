package figuren;

import gui.Feld;

import java.util.List;

public class Laeufer extends Figur {

    public Laeufer(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(325);
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
