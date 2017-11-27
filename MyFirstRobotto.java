package mnguyen_lhuang;
import robocode.*;
import java.awt.*;
import robocode.util.*;
// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * MyFirstRobotto - a robot by Leyna Huang & Michael Nguyen
 */
public class MyFirstRobotto extends AdvancedRobot
{
	// Path Pattern
	private int moveDirection = 1;
	
	/**
	 * run: MyFirstRobot's default behavior
	 */
	public void run() {
		setBodyColor(Color.cyan);
		setGunColor(Color.black);
		setRadarColor(Color.white);
		setScanColor(Color.white);
		setBulletColor(Color.cyan);
		
		// Robot main loop
		while(true) {
			setAdjustRadarForRobotTurn(true);
			setAdjustGunForRobotTurn(true);
			setTurnRadarRight(360);
			ahead(200);
			setTurnRight(45);
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		
		setTurnRight(e.getBearing() + 90);
		setTurnRadarRight(Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getRadarHeading())); // Face radar twards the enemy
		setTurnGunRight(Utils.normalRelativeAngleDegrees(getHeading() + e.getBearing() - getGunHeading())); // Face gun towards enemy
		
		// Fires towards enemy if they are within specified distance
		if(e.getDistance() <= 150) {
			fire(3);
			// Crashes enemy if they are within specified distance
			if(e.getDistance() <= 50) {
			setTurnRight(getHeading() - getGunHeading() + e.getBearing());
			setAhead(50);
			fire(3);
			}
		}
		else{
			fire(1);
		}
		execute();
		if(getTime() % 20 == 0) {
			moveDirection *= -1;
			setAhead(200 * moveDirection);
		}
		
	}

	public void onHitByBullet(HitByBulletEvent e) {
		if(e.getBearing() > -10 && e.getBearing() < 10) {
			setTurnRadarRight(getHeading() + e.getBearing() - getRadarHeading()); // Face radar twards the enemy
			setTurnGunRight(getHeading() + e.getBearing() - getGunHeading()); // Face gun towards enemy
			fire(3);
		}
	}
	
	public void onHitWall(HitWallEvent e) {
		moveDirection *= -1;
	}	
}
