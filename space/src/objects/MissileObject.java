
package objects;

import objects.Thruster.ThrusterType;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import space.World;
import space.WorldObject;
import util.Util;
import util.Vector;

public class MissileObject extends WorldObject {

	private Thruster thruster;
	private Image missileImage;
	private Ship ship;
	private float fuel;
	
	public MissileObject(Image sprite, Ship ship) {
		super(0.5f,2.0f);
		this.missileImage = sprite;
		this.ship = ship;
		fuel = 5.0f;
		thruster = new Thruster(new Vector(-4,0), new Vector(1,0), 2.0f, ThrusterType.MAIN_MODE);
	}

	public boolean isAlive() {
		return fuel > 0.0f;
	}

	public void draw(Graphics g) {
		missileImage.setRotation(objectRotation*Util.DEGREES_IN_RADIAN);
		missileImage.drawCentered(position.xPoint, position.yPoint);
		g.translate(position.xPoint, position.yPoint);
		g.rotate(0,0,objectRotation*Util.DEGREES_IN_RADIAN);
		thruster.draw(g);
		g.rotate(0,0,-objectRotation*Util.DEGREES_IN_RADIAN);
		g.translate(-position.xPoint, -position.yPoint);
	}
	
	public void update(World world, float step){
		thruster.setValue(1.0f);
		netForce.addVector(thruster.getThrust(objectRotation));
		fuel -= step;
		super.update(world, step);
	}

	public String getRadarIdent() {
		return null;
	}

	public boolean onCollision(WorldObject other){
		if (other == ship){
			return false;
		} else {
			return super.onCollision(other);
		}
	}
	
	public void OnCollision(WorldObject other) {
		
	}

}
