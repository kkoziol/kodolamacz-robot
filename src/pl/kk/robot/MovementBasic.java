package pl.kk.robot;

public class MovementBasic implements Movement {

    private Kiler kiler;

    public MovementBasic(Kiler robot) {
        this.kiler = robot;
    }

    @Override
    public void move() {
        // ustaw sie prostopadle jezeli mamy cel
        if (kiler.currentEnemy != null)
            kiler.setTurnLeft(90 - kiler.currentEnemy.getBearing());

        if (kiler.getDistanceRemaining() == 0) {
            kiler.setAhead(100 * kiler.kierunek);
            kiler.kierunek *= -1;
        }
    }

    @Override
    public void onWin() {
    }

}
