

package objects;

import java.util.ArrayList;
import java.util.List;

import objects.WeaponSub.WeaponTypes;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import space.World;
import space.WorldObject;
import util.DoubleVector;
import util.Util;
import util.Vector;

public class Ship extends WorldObject {
	
	private Image shipImage;
	private Thrusters shipThrusterSet;
	private Vector direction;
	private List<WeaponSub> weaponsList;
	private Faction faction;
	private PlanetRadar radar;
	private ShipShield shield;
	private float shipArmor;
	
	private float MAX_ARMOR;
	
	public enum Faction {
		PLAYER,
		ENEMY;
	}
	
	public Ship(float shipMass, Faction faction){
		super(2f, 32);
		this.faction = faction;
		shipThrusterSet = new Thrusters(this);
		this.inertia = 30.0f;
		direction = new Vector(1,0);
		weaponsList = new ArrayList<WeaponSub>();
		radar = new PlanetRadar(this,10f);
		shield = new ShipShield(32);
		MAX_ARMOR = 10;
		shipArmor = MAX_ARMOR;
		try 
		{
			Image img = new Image("data/sprites/bullet.png");
			WeaponSub projectile = new ProjectileWeapon(img);
			projectile.mountProjectile(this, new Vector(0,-16));
			weaponsList.add(projectile);
			projectile = new ProjectileWeapon(img);
			projectile.mountProjectile(this, new Vector(0,16));
			weaponsList.add(projectile);
			projectile = new MissileLauncherWeapon();
			projectile.mountProjectile(this, new Vector(-20,0));

			weaponsList.add(projectile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PlanetRadar getRadar(){
		return radar;
	}
	
	public boolean isFaction(Ship ship){
		return faction != ship.faction;
	}
	
	public boolean isAlive(){
		return shipArmor > 0;
	}
	
	public void setImage(Image sprite){
		this.shipImage = sprite;
	}
	
	public Thrusters getThrusterList(){
		return shipThrusterSet;
	}
	
	public DoubleVector getDirection(){
		return direction;
	}
	
	public void update(World world, float step){
		radar.update(world);
		shield.update(step);
		addForce(shipThrusterSet.getNetForce());
		addToTorque(shipThrusterSet.getTorque());
		super.update(world, step);
		direction.set((float)Math.cos(objectRotation), (float)Math.sin(objectRotation));
		for(WeaponSub weapon : weaponsList){
			weapon.update(world, step);
		}
	}
	
	public void setIsFiring(WeaponTypes type, boolean firing){
		for(WeaponSub weapon : weaponsList){
			if (weapon.getType() == type){
				weapon.setIsFiring(firing);
			}
		}
	}
	
	public void draw(Graphics g){
		g.translate(position.xPoint, position.yPoint);
		g.rotate(0,0, objectRotation*Util.DEGREES_IN_RADIAN);
		g.drawImage(shipImage,-shipImage.getWidth()/2,-shipImage.getHeight()/2);
		shield.draw(g);
		shipThrusterSet.draw(g);
		g.rotate(0,0, -objectRotation*Util.DEGREES_IN_RADIAN);
		g.translate(-position.xPoint, -position.yPoint);
		
	}
	
	public void reset(Vector pos){
		this.velocity.set(0,0);
		this.position.set(pos);
		shipArmor = MAX_ARMOR;
		shield.reset();
	}
	
	public void giveDamage(float amount){
		if (shield.isShieldDown()){
			shipArmor -= amount;
		}
		else
		{
			shield.takeDamage(amount);
		}
		
	}

	public String getRadarIdent() {
		return "Ship";
	}

	public void OnCollision(WorldObject other) {
		shield.takeDamage(0.0f);
	}

}
