
package space;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import background.StarTileGrid;

public class World {

	private StarTileGrid backgroundSpace;
	private List<WorldObject> worldObjectList;
	private List<WorldObject> newWorldObjectList;
	private Map<String, WorldObject> objectMap;
	private WorldObject worldTargetObject;

	public World(GameContainer gc){
		backgroundSpace = new StarTileGrid(gc);
		worldObjectList = new ArrayList<WorldObject>();
		newWorldObjectList = new ArrayList<WorldObject>();
		objectMap = new HashMap<String, WorldObject>();
		
	}
	
	public void addObjectToWorld(WorldObject object){
		newWorldObjectList.add(object);
		if (object.getName() != null){
			objectMap.put(object.getName(), object);
		}
	}
	
	public WorldObject getObjectInWorld(String name){
		if (objectMap.containsKey(name)){
			return objectMap.get(name);
		} else {
			return null;
		}
	}
	
	public void update(float interval){
		Iterator<WorldObject> it = worldObjectList.iterator(); 
		while(it.hasNext()){
			WorldObject object = it.next();
			object.update(this, interval);
			for(WorldObject worldObject : worldObjectList){
				if (object != worldObject && object.onCollision(worldObject) && worldObject.onCollision(object)){
					WorldObject.seperate(object, worldObject);
					object.OnCollision(worldObject);
					worldObject.OnCollision(object);
				}
			}
			if (!object.isAlive()){
				it.remove();
			}
		}
		for(WorldObject worldObject : newWorldObjectList){

			worldObjectList.add(worldObject);
		}
		newWorldObjectList.clear();
	}
	
	public void render(GameContainer gc, Graphics g){
		backgroundSpace.draw(g,worldTargetObject.getPosition().getXCoord(), worldTargetObject.getPosition().getYCoord());
		g.translate(-worldTargetObject.getPosition().getXCoord(),-worldTargetObject.getPosition().getYCoord());
		for(WorldObject worldObject : worldObjectList){
			worldObject.draw(g);
		}

	}
	
	
	public WorldObject getTargetObject(){
		return worldTargetObject;
	}
	
	public void setTargetObject(WorldObject target){
		this.worldTargetObject = target;
	}
	
	public Collection<WorldObject> getObjects(){
		return worldObjectList;
	}
}
