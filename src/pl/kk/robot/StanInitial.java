package pl.kk.robot;

public class StanInitial implements Stan {

    private Kiler me;

    public StanInitial(Kiler kk) {
        this.me = kk;
    }

    @Override
    public Stan chooseStrategiesState() {
        switch (me.getOthers()) {
        case 0:
            me.setDebugProperty("State Strategy:", "Initial");
            return this;
        case 1:
            me.setDebugProperty("State Strategy:", "OneOnOne-walcze");
            return new StanWalcze(me);
            //jak wiecej przeciwnikow... to to samo
            //potem mozna dodac nastepny zestaw strategii
        default:
            me.setDebugProperty("State Strategy:", "ManyOnOne-jeszcze nie umie");
            return new StanWalcze(me);
        }
    }

    @Override
    public void updateStrategies() {
        me.movement = new MovementDoNothing(me);
        me.fireing = new FireingDoNothing(me);
    }

}
