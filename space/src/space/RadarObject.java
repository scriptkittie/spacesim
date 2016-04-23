
package space;

import org.newdawn.slick.Graphics;

public abstract class RadarObject {
	
	protected float x,y;
	protected float width,height;
	
	public RadarObject(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public abstract void draw(Graphics g);
	
}
