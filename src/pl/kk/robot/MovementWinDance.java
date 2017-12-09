package pl.kk.robot;

public class MovementWinDance implements Movement {

    private Kiler me;

    public MovementWinDance(Kiler me) {
        this.me = me;
    }

    @Override
    public void move() {
        for (int i = 0; i < 50; i++) {
            me.turnRight(30);
            me.turnLeft(30);
        }
    }

    @Override
    public void onWin() {
    }

}
