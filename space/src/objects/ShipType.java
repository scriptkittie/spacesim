package objects;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;

public class ShipType {
	
	public float shipMAss;
	public Image image;
	public Image shieldImage;
	public List<Thruster> thrustersList;
	
	public ShipType(float mass, Image sprite){
		this.shipMAss = mass;
		this.image = sprite;
		thrustersList = new ArrayList<Thruster>();
	}
	
	public void setShipShield(Image shield){
		this.shieldImage = shield;
	}
	
	
}
