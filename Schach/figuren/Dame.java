package figuren;

import gui.Feld;

import java.util.List;

public class Dame extends Figur {

    public Dame(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(900);
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
