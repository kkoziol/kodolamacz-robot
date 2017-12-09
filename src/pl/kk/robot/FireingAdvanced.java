package pl.kk.robot;

import java.awt.Color;

public class FireingAdvanced implements Fireing {

    private Kiler kiler;

    public FireingAdvanced(Kiler kiler) {
        this.kiler = kiler;
    }

    @Override
    public void fire() {
        kiler.setBulletColor(Color.GREEN);
        if (Math.abs(kiler.angleInDegree) <= 3) {
            if (kiler.getGunHeat() == 0) {
                kiler.fire(Math.min(3 - Math.abs(kiler.angleInDegree), kiler.getEnergy() - .1));
            }
        }

    }

}
