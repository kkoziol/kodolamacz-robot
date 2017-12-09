package pl.kk.robot;

public class StanWygrana implements Stan {

    private Kiler kiler;


    public StanWygrana(Kiler kiler) {
        this.kiler = kiler;
    }
    
    
    @Override
    public void onWin() {
        // Victory dance
        kiler.turnRight(36000);
    }


    @Override
    public void move() {
    }

}
