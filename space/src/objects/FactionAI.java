package objects;

import objects.WeaponSub.WeaponTypes;
import space.World;
import space.WorldObject;
import util.Util;
import util.Vector;

public class FactionAI {

	private Ship ship;
	private Ship targetShip;
	Vector targetVelocity = new Vector(0,0);
	private static final Vector velocity1 = new Vector(0,0);
	private static final Vector velocity2 = new Vector(0,0);
	
	public FactionAI(Ship ship){
		this.ship = ship;
	}
	
	public void update(World world){
		if (targetShip == null){
			targetVelocity.set(0,0);
			ship.getThrusterList().setThrusterControls(targetVelocity, 0, true);
			for(int i = 0; i < ship.getRadar().getObjectCount(); i++){
				WorldObject object = ship.getRadar().getObject(i);
				if (object instanceof Ship && object != ship && ((Ship)object).isFaction(ship)){
					targetShip = (Ship)object;
					break;
				}
			}
		} 
		else 
		{
			targetVelocity.set(0,0);
			velocity1.set(targetShip.getPosition());
			velocity1.sub(ship.getPosition());
			float targetAngle = Util.getSmallestAngle((float)Math.atan2(velocity1.yPoint,velocity1.xPoint), ship.getAngle());
			float distance = velocity1.vectorLength();
			ship.setIsFiring(WeaponTypes.PRIMARY, Math.abs(targetAngle) < 0.2f);
			velocity2.set(velocity1.yPoint, -velocity1.xPoint);
			velocity2.normalizeScreen();
			boolean canManuver = false;
			if (distance < 250)
			{
				velocity2.scale((float)(Math.signum(-targetAngle))*400.0f);
				canManuver = true;
			} 
			else 
			{
				velocity2.scale(-ship.getVelocity().center(velocity2));
			}
			targetVelocity.addScaled(velocity1,0.5f);
			targetVelocity.addScaled(velocity2,1f);
			targetVelocity.addScaled(targetShip.getVelocity(),0.5f);
			
			float rotationAngle = (float)Math.atan2(targetVelocity.yPoint, targetVelocity.xPoint);
			ship.getThrusterList().setThrusterControls(targetVelocity, rotationAngle, canManuver);
		}
	}
	
	
	
}
