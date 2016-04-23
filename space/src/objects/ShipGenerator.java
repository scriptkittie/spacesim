
package objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objects.Ship.Faction;
import objects.Thruster.ThrusterType;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.util.ResourceLoader;

import util.Vector;

public class ShipGenerator {

	private Map<String, ShipType> shipTypes;
	
	@SuppressWarnings("unchecked")
	public ShipGenerator(){
		shipTypes = new HashMap<String, ShipType>();
		
		try {
			Document document = new SAXBuilder().build(ResourceLoader.getResourceAsStream("data/ships.xml"));
			for(Element childElement : (List<Element>)document.getRootElement().getChildren())
			{
				ShipType type = new ShipType(Float.parseFloat(childElement.getChildText("mass")),new Image("data/sprites/" + childElement.getChildText("sprite")));
				type.setShipShield(generateShipShield(type.image));
				shipTypes.put(childElement.getAttributeValue("name"), type);
				for(Element element : (List<Element>)childElement.getChild("thrusters").getChildren()){
					Thruster thruster = new Thruster(loadVectorFromXML(element.getChild("pos")),
													 loadVectorFromXML(element.getChild("dir")),
													 Float.parseFloat(element.getAttributeValue("size")),
													 ThrusterType.forName(element.getAttributeValue("type")));
					type.thrustersList.add(thruster);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vector loadVectorFromXML(Element element){
		return new Vector(Float.parseFloat(element.getChildText("x")), Float.parseFloat(element.getChildText("y")));
	}
	
	public Ship generateShip(String shipType, Faction faction){
		ShipType type = shipTypes.get(shipType);
		Ship ship = new Ship(type.shipMAss, faction);
		ship.setImage(type.image);
		for(Thruster thruster : type.thrustersList)
		{
			ship.getThrusterList().addShipThruster((Thruster)thruster.clone());
		}
		return ship;
	}
	
	public Image generateShipShield(Image shieldSprite){
		ImageBuffer imageBuffer = new ImageBuffer(shieldSprite.getWidth(), shieldSprite.getHeight());
		for(int i = 0; i < imageBuffer.getWidth(); i++){
			for(int j = 0; j < imageBuffer.getHeight(); j++){
				float directionX = (2*i - imageBuffer.getWidth())/(float)imageBuffer.getWidth();
				float directionY = (2*j - imageBuffer.getHeight())/(float)imageBuffer.getHeight();
				float direction = directionX*directionX + directionY*directionY;
				direction *= direction;
				if (direction < 1.0f)
				{
					direction *= 0.8f;
					imageBuffer.setRGBA(i,j,255,255,255, (int)(direction*255));
				} 
				else 
				{
					imageBuffer.setRGBA(i,j,0,0,0,0);
				}
			}
		}
		return imageBuffer.getImage();
	}
	
}
