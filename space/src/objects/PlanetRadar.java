package objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import space.World;
import space.WorldObject;

public class PlanetRadar {

	private Ship ship;
	private float resolution;
	private List<WorldObject> objectsInRadar;
	
	public PlanetRadar(Ship ship, float res){
		this.ship = ship;
		this.resolution = res;
		objectsInRadar = new ArrayList<WorldObject>();
	}
	
	public Ship getShip(){
		return ship;
	}
	
	public void update(World world){
		for(WorldObject obj: world.getObjects()){
			float worldObjectMass = obj.getMass();
			float distance = ship.getPosition().distanceToInMeters(obj.getPosition());
			if (obj.getRadarIdent() != null && obj != ship && distance > 100.0f){
				float massOfRadar = worldObjectMass/distance;
				if (massOfRadar > 1/(resolution*100)){
					if (!inRadar(obj)){
						objectsInRadar.add(obj);
					}
				}
				
			}
		}
		Iterator<WorldObject> it = objectsInRadar.iterator();
		while(it.hasNext()){
			WorldObject next = it.next();
			if (!next.isAlive()){
				it.remove();
			}
		}
	}
	
	public int getObjectCount(){
		return objectsInRadar.size();
	}
	
	public WorldObject getObject(int index){
		return objectsInRadar.get(index);
	}
	
	public boolean inRadar(WorldObject object){
		return objectsInRadar.contains(object);
	}
}
