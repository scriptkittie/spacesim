

package planets;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import space.World;
import space.WorldObject;
import util.DoubleVector;

public class Planet extends WorldObject {

	private Image worldImage;
	private String planetName;
	
	public Planet(String planetName, Image worldImage, DoubleVector position){
		super(worldImage.getWidth()*worldImage.getHeight(), worldImage.getWidth()/2.0f);
		this.worldImage = worldImage;
		this.position.set(position);
		this.planetName = planetName;
	}
	public void OnCollision(WorldObject other){}
	
	public String getName()
	{
		return planetName;
	}
	
	public boolean isAlive(){
		return true;
	}
	
	public void draw(Graphics g){
		g.drawImage(worldImage,position.xPoint-worldImage.getWidth()/2,position.yPoint-worldImage.getHeight()/2);
	}

	public String getRadarIdent() {
		return planetName;
	}
	
	public boolean onCollision(WorldObject other){
		return false;
	}
	
	public void update(World world, float timestep){
		
	}
}
