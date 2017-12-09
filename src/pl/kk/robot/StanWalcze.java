package pl.kk.robot;

public class StanWalcze implements Stan {

    private Kiler kiler;


    public StanWalcze(Kiler kiler) {
        this.kiler = kiler;
    }
    
    
    @Override
    public void onWin() {
    }


    @Override
    public void move() {
        kiler.ahead(100 * kiler.kierunek);
        kiler.turnGunRight(360 * kiler.kierunek);
        kiler.kierunek *= -1;
    }

}
