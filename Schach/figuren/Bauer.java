package figuren;

import gui.Feld;

import java.util.List;

public class Bauer extends Figur {

    private boolean bereitsGezogen;
    
    public Bauer() {
        
    }
    
    public Bauer(Feld position, boolean farbe) {
        super.setPosition(position);
        super.setFarbe(farbe);
        super.setWert(100);
        bereitsGezogen = false;
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
    
    public boolean getGezogen() {
        return bereitsGezogen;
    }
    
    public void setGezogen(boolean gezogen) {
        bereitsGezogen = gezogen;
    }

}
