package objects;

import space.World;
import util.DoubleVector;
import util.Vector;

public abstract class WeaponSub {
	
	public enum WeaponTypes {
		PRIMARY,
		SECONDARY;
	
	}
	
	protected boolean isFiring;
	protected float rateOfFire;
	protected Ship playerShip;
	protected float fireDelta;
	protected Vector mountPoint;
	protected WeaponTypes weaponType;
	
	public WeaponSub(float fireRate, WeaponTypes type){
		this.rateOfFire = fireRate;
		fireDelta = 0;
		mountPoint = new Vector(0,0);
		this.weaponType = type;
	}
	
	public WeaponTypes getType(){
		return weaponType;
	}
	
	public void mountProjectile(Ship ship, DoubleVector point){
		this.playerShip = ship;
		mountPoint.set(point);
	}
	
	public void update(World world, float step){
		if (isFiring && fireDelta >= 1/rateOfFire){
			fireMissile(world);
			fireDelta = 0;
		}
		fireDelta += step;
	}
	
	protected abstract void fireMissile(World world);
	
	public void setIsFiring(boolean firing){
		this.isFiring = firing;
	}
	


}
