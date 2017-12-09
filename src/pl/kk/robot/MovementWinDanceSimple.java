package pl.kk.robot;

public class MovementWinDanceSimple implements Movement {

    private Kiler me;

    public MovementWinDanceSimple(Kiler me) {
        this.me = me;
    }

    @Override
    public void move() {
        // Victory dance
        me.turnRight(36000);
    }

    @Override
    public void onWin() {
    }

}
