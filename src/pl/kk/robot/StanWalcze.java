package pl.kk.robot;

public class StanWalcze implements Stan {

    private Kiler kiler;


    public StanWalcze(Kiler kiler) {
        this.kiler = kiler;
    }
    
    
    @Override
    public Stan chooseStrategiesState() {
        switch (kiler.getOthers()) {
        case 0:
            kiler.setDebugProperty("State Strategy:", "Wygrana");
            return new StanWygrana(kiler);
        case 1:
            kiler.setDebugProperty("State Strategy:", "Walcze");
            return this;
            //jak wiecej przeciwnikow... to to samo
            //potem mozna dodac nastepny zestaw strategii
        default:
            kiler.setDebugProperty("State Strategy:", "ManyOnOne-ale nie umie");
            return this;
        }
    }

    @Override
    public void updateStrategies() {
        kiler.movement = new MovementBasic(kiler);
        kiler.fireing = new FireingAdvanced(kiler);
    }

}
