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
        if (Math.abs(kiler.gunAngleToTurnInDegree) <= 1) {
            if (kiler.getGunHeat() == 0) {
                kiler.setFire(Math.min(3 - Math.abs(kiler.gunAngleToTurnInDegree), kiler.getEnergy() - .1));
            }
        }

    }

}
