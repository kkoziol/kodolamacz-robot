package pl.kk.robot;

public class FireingBasic implements Fireing {

    private Kiler kiler;

    public FireingBasic(Kiler kiler) {
        this.kiler = kiler;
    }

    @Override
    public void fire() {
      kiler.fire(1);        
    }

    
}
