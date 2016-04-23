
package space;

import objects.Ship;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import util.Util;

public class BulletObj extends WorldObject {

	private Image bulletSprite;
	private float bulletLife;
	private Ship bulletSource;
	
	public BulletObj(Image sprite, Ship bulletSource) {
		super(0.001f, 0.01f);
		bulletLife = 1.0f;
		this.bulletSource = bulletSource;
		this.bulletSprite = sprite;
	}

	public void update(World world, float step){
		bulletLife -= step;
		super.update(world, step);
	}
	
	public boolean isAlive(){
		return bulletLife > 0;
	}
	
	public void draw(Graphics g) {
		bulletSprite.setRotation(objectRotation* Util.DEGREES_IN_RADIAN);
		bulletSprite.drawCentered(position.xPoint, position.yPoint);
	}

	public String getRadarIdent() {
		return null;
	}
	
	public boolean onCollision(WorldObject worldObject){
		if (worldObject == bulletSource){
			return false;
		} else {
			return super.onCollision(worldObject);
		}
	}

	public void OnCollision(WorldObject worldObject) {
		if (worldObject instanceof Ship){
			((Ship)worldObject).giveDamage(5.0f);
		}
		bulletLife = 0;
	}

}
