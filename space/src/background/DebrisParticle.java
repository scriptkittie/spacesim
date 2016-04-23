package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import util.Vector;

public class DebrisParticle {
	
	private Vector position;
	private Image image;
	private int debrisSize;
	
	public DebrisParticle(Image image){
		position = new Vector(0,0);
		this.image = image;
		debrisSize = image.getWidth();
	}
	
	public boolean onScreen(float xPoint, float yPoint, float width1, float height){
		int width = image.getWidth();
		if (position.xPoint + width < xPoint || position.xPoint - width > xPoint + width1 ||
			position.yPoint + width < yPoint || position.yPoint - width > yPoint + height){
			return false;
		} 
		else 
		{
			return true;
		}
	}
	
	public void setPos(float xPoint, float yPoint){
		position.set(xPoint,yPoint);
	}
	
	public void draw(Graphics g){
		g.drawImage(image, position.xPoint - debrisSize/2, position.yPoint - debrisSize/2);
	}
	
}
