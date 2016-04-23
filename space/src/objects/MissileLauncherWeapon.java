
package objects;

import org.newdawn.slick.Image;

import space.World;
import util.Vector;

public class MissileLauncherWeapon extends WeaponSub {

	private Image missileImage;
	
	public MissileLauncherWeapon() {
		super(1.0f, WeaponTypes.SECONDARY);
		try {
			missileImage = new Image("data/sprites/missile.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void fireMissile(World world) {
		for(int i = 0; i < 5; i++){
			MissileObject missile = new MissileObject(missileImage, playerShip);
			Vector v = new Vector(mountPoint);
			v.add(0,i*5 - 10);
			v.rotate(playerShip.getAngle() + (i - 2.0f)/10.0f);
			v.addVector(playerShip.getPosition());
			missile.setPosition(v.xPoint, v.yPoint);
			missile.setAngle(playerShip.getAngle() + (i - 2.0f)/10.0f);
			v.set(playerShip.getVelocity());
			missile.setVelocity(v);
			world.addObjectToWorld(missile);
		}
	}

	
	
}
