package pl.kk.robot;

import java.awt.Color;
import java.awt.Graphics2D;

import robocode.AdvancedRobot;
import robocode.Condition;
import robocode.CustomEvent;
import robocode.HitByBulletEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import robocode.util.Utils;

public class Kiler extends AdvancedRobot {
    // Stan strategi robota
    Stan stan = new StanInitial(this);

    // aktualny zestaw strategi
    Fireing fireing = new FireingDoNothing(this);
    Movement movement = new MovementDoNothing(this);

    int trigger = 90;
    int kierunek = 1;
    double gunAngleToTurnInDegree;

    ScannedRobotEvent currentEnemy;

    public void run() {
        // Ustawienie niezaleznosci obrotów
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        setBodyColor(Color.CYAN);
        setGunColor(Color.CYAN);
        setRadarColor(Color.BLUE);

        setScanColor(Color.PINK);
        setBulletColor(Color.RED);

        addCustomEvent(new Condition("spadek-energi") {
            public boolean test() {
                return (getEnergy() <= trigger);
            }
        });

        while (true) {
            // dokonaj wyboru nowego zestawu strategi
            stan = stan.chooseStrategiesState();
            // pozmieniaj zawartosci zmiennych strategii
            stan.updateStrategies();

            // zakrec radarem, jezeli z jakiegos powodu stanol
            if (getRadarTurnRemaining() == 0.0)
                setTurnRadarRightRadians(Double.POSITIVE_INFINITY);

            // ruchy
            movement.move();

            // celowane
            if (getGunTurnRemaining() == 0.00)
                setTurnGunRight(gunAngleToTurnInDegree);

            // strzelanie
            fireing.fire();
            execute();
        }

    }

    public void onScannedRobot(ScannedRobotEvent e) {
        setDebugProperty("lastScannedRobot", e.getName() + " at " + e.getBearing() + " degrees at time " + getTime());

        currentEnemy = e;
        
        double angleToEnemy = getHeadingRadians() + e.getBearingRadians();

        // Subtract current radar heading to get the turn required to face the enemy, be
        // sure it is normalized
        double radarTurn = Utils.normalRelativeAngle(angleToEnemy - getRadarHeadingRadians());

        // Distance we want to scan from middle of enemy to either side
        // The 36.0 is how many units from the center of the enemy robot it scans.
        double extraTurn = Math.min(Math.atan(36.0 / e.getDistance()), Rules.RADAR_TURN_RATE_RADIANS);

        // Adjust the radar turn so it goes that much further in the direction it is
        // going to turn
        // Basically if we were going to turn it left, turn it even more left, if right,
        // turn more right.
        // This allows us to overshoot our enemy so that we get a good sweep that will
        // not slip.
        if (radarTurn < 0)
            radarTurn -= extraTurn;
        else
            radarTurn += extraTurn;

        // Turn the radar
        setTurnRadarRightRadians(radarTurn);

        gunAngleToTurnInDegree = Utils.normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

    }

    public void onHitByBullet(HitByBulletEvent e) {
        // demonstrate feature of debugging properties on RobotDialog
        setDebugProperty("lastHitBy", e.getName() + " with power of bullet " + e.getPower() + " at time " + getTime());

        // show how to remove debugging property
        setDebugProperty("lastScannedRobot", null);

        // gebugging by painting to battle view
        Graphics2D g = getGraphics();

        g.setColor(Color.orange);
        g.drawOval((int) (getX() - 55), (int) (getY() - 55), 110, 110);
        g.drawOval((int) (getX() - 56), (int) (getY() - 56), 112, 112);
        g.drawOval((int) (getX() - 59), (int) (getY() - 59), 118, 118);
        g.drawOval((int) (getX() - 60), (int) (getY() - 60), 120, 120);

    }

    public void onCustomEvent(CustomEvent e) {
        if (e.getCondition().getName().equals("spadek-energi")) {
            trigger = trigger - 20;

            out.println("Ouch, down to " + (int) (getEnergy() + .5) + " energy.");
            fireing = new FireingAdvanced(this);

             turnLeft(65);
        }
    }

    public void onWin(WinEvent e) {
        movement.onWin();
    }

    public void onPaint(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawOval((int) (getX() - 50), (int) (getY() - 50), 100, 100);
        g.setColor(new Color(0, 0xFF, 0, 30));
        g.fillOval((int) (getX() - 60), (int) (getY() - 60), 120, 120);
    }

}