package pl.kk.robot;

import java.awt.Color;
import java.awt.Graphics2D;

import robocode.*;
import robocode.util.Utils;

public class Kiler extends AdvancedRobot {

    int trigger = 90;
    int kierunek = 1;
    double angleInDegree;

    Fireing fireing = new FireingBasic(this);

    Stan stan;
    
    
    public void run() {

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
            
         // Turn the radar if we have no more turn, starts it if it stops and at the start of round
//            if ( getRadarTurnRemaining() == 0.0 )
//                setTurnRadarRightRadians(Double.POSITIVE_INFINITY );
            
            //ruchy
            stan.move();
        

            //celowane
//            if(getGunTurnRemaining() <= 0.01)
//               setTurnGunRight(angleInDegree);
            
            //strzelanie
            fireing.fire();
            stan = stan.determineNextStan();
            execute();
        }

    }

    public void onScannedRobot(ScannedRobotEvent e) {
        setDebugProperty("lastScannedRobot", e.getName() + " at " + e.getBearing() + " degrees at time " + getTime());

        //        fire(1);

        // Calculate exact location of the robot
        double absoluteBearing = getHeading() + e.getBearing();
        double bearingFromGun = Utils.normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

        // If it's close enough, fire!
        if (Math.abs(bearingFromGun) <= 3) {
            turnGunRight(bearingFromGun);
            // We check gun heat here, because calling fire()
            // uses a turn, which could cause us to lose track
            // of the other robot.
            if (getGunHeat() == 0) {
                fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
            }
        } // otherwise just set the gun to turn.
          // Note:  This will have no effect until we call scan()
        else {
            turnGunRight(bearingFromGun);
        }
        // Generates another scan event if we see a robot.
        // We only need to call this if the gun (and therefore radar)
        // are not turning.  Otherwise, scan is called automatically.
        if (bearingFromGun == 0) {
            scan();
        }
        /*
        // Absolute angle towards target
        double angleToEnemy = getHeadingRadians() + e.getBearingRadians();
     
        // Subtract current radar heading to get the turn required to face the enemy, be sure it is normalized
        double radarTurn = Utils.normalRelativeAngle( angleToEnemy - getRadarHeadingRadians() );
     
        // Distance we want to scan from middle of enemy to either side
        // The 36.0 is how many units from the center of the enemy robot it scans.
        double extraTurn = Math.min( Math.atan( 36.0 / e.getDistance() ), Rules.RADAR_TURN_RATE_RADIANS );
     
        // Adjust the radar turn so it goes that much further in the direction it is going to turn
        // Basically if we were going to turn it left, turn it even more left, if right, turn more right.
        // This allows us to overshoot our enemy so that we get a good sweep that will not slip.
        if (radarTurn < 0)
            radarTurn -= extraTurn;
        else
            radarTurn += extraTurn;
     
        //Turn the radar
        setTurnRadarRightRadians(radarTurn);

        angleInDegree = Utils.normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());
        */
        

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

        turnLeft(90 - e.getBearing());
  }
    
    public void onCustomEvent(CustomEvent e) {
        if (e.getCondition().getName().equals("spadek-energi")) {
            trigger = trigger - 20;

            out.println("Ouch, down to " + (int) (getEnergy() + .5) + " energy.");
            fireing = new FireingAdvanced(this);
            
            turnLeft(65);
            ahead(100);
        }
    }

    public void onWin(WinEvent e) {
        stan.onWin();
    }

    public void onPaint(Graphics2D g) {
        g.setColor(Color.RED);
        g.drawOval((int) (getX() - 50), (int) (getY() - 50), 100, 100);
        g.setColor(new Color(0, 0xFF, 0, 30));
        g.fillOval((int) (getX() - 60), (int) (getY() - 60), 120, 120);
    }



}