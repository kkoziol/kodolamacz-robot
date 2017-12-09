package pl.kk.robot;

public class FireingDoNothing implements Fireing {

    private Kiler kiler;

    public FireingDoNothing(Kiler kiler) {
        this.kiler = kiler;
    }

    @Override
    public void fire() {
    }

    
}
