package pl.kk.robot;

public class StanWygrana implements Stan {

    private Kiler kiler;

    public StanWygrana(Kiler kiler) {
        this.kiler = kiler;
    }

    @Override
    public Stan chooseStrategiesState() {
        //stan koncowy nic juz sienie zmieni
        return this;
    }

    @Override
    public void updateStrategies() {
        kiler.movement = new MovementWinDance(kiler);
        kiler.fireing = new FireingDoNothing(kiler);
    }

}
