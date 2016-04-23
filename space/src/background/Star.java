package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import util.Vector;

public class Star {
	private Vector position;
	private Image image;
	
	public Star(float xPoint, float yPoint, Image image){
		position = new Vector(xPoint,yPoint);
		this.image = image;
	}
	
	public void draw(Graphics g){
		g.drawImage(image, position.xPoint, position.yPoint);
	}
}
