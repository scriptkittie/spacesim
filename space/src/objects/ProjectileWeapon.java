package objects;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import space.BulletObj;
import space.World;
import util.DoubleVector;
import util.Vector;

public class ProjectileWeapon extends WeaponSub {

	private static final float VELOCITY = 1000.0f;
	private static final float RATE_OF_FIRE = 5.0f;
	
	private Ship ship;
	private Vector mountPosition;
	private Image bulletSprite;
	
	public ProjectileWeapon(Image bulletImage){
		super(RATE_OF_FIRE, WeaponTypes.PRIMARY);
		mountPosition = new Vector(0,0);
		this.bulletSprite = bulletImage;
	}
	
	public void mountProjectile(Ship ship, DoubleVector position){
		this.ship = ship;
		mountPosition.set(position);
	}

	protected void fireMissile(World world){
		BulletObj bullet = new BulletObj(bulletSprite,ship);
		Vector positionVector = new Vector(mountPosition);
		positionVector.rotate(ship.getAngle());
		positionVector.addVector(ship.getPosition());
		bullet.setPosition(positionVector.xPoint, positionVector.yPoint);
		bullet.setAngle(ship.getAngle());
		positionVector.set(ship.getVelocity());
		positionVector.add(VELOCITY*(float)Math.cos(ship.getAngle()), VELOCITY*(float)Math.sin(ship.getAngle()));
		bullet.setVelocity(positionVector);
		world.addObjectToWorld(bullet);
	}
	
	public void draw(Graphics g){
		
	}
	
}
