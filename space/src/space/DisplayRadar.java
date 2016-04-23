
package space;

import objects.PlanetRadar;
import objects.Ship;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import util.Util;
import util.Vector;

public class DisplayRadar extends RadarObject {
	
	private static final Color PLANET_COLOR_RADAR = new Color(64,128,255);
	private static final Color ENEMY_COLOR_RADAR = new Color(255,0,0);
	
	private static final Vector screenPosition = new Vector(0,0);

	private static final float angle = (float)Math.PI/16;
	private Image pointer;
	private Image[] imageList;
	private Color pointerColor;
	private PlanetRadar radar;
	
	public enum Direction {
		NORTH(0,1),
		SOUTH(0,-1),
		EAST(1,0),
		WEST(-1,0);
		
		public final int directionX;
		public final int directionY;
		
		private Direction(int directionX, int directionY){
			this.directionX = directionX;
			this.directionY = directionY;
		}
		
		public static Direction getDirection(float directionX, float directionY){
			if (Math.abs(directionX) > Math.abs(directionY)){
				return directionX < 0 ? WEST : EAST;
			} else {
				return directionY < 0 ? SOUTH : NORTH;
			}
		}
		
	}
	
	public void setRadar(PlanetRadar radar){
		this.radar = radar;
	}
	
	public DisplayRadar(GameContainer gc) {
		super(0,0,gc.getWidth(),gc.getHeight());
		try {
			pointer = new Image("data/images/arrow.png");
			imageList = new Image[]{
				new Image("data/images/radar.png",false,Image.FILTER_NEAREST),	
				new Image("data/images/radar_center.png",false,Image.FILTER_NEAREST),	
				new Image("data/images/radar_lower.png",false,Image.FILTER_NEAREST),	
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
		pointerColor = new Color(255,255,255,0);
	}

	
	public void screenLock(Vector v){
		float ratio = width/height;
		Direction quadrant = Direction.getDirection(v.xPoint, v.yPoint*ratio); 
		switch(quadrant){
			case NORTH:
				v.scale((height/2)/v.yPoint);
				break;
			case EAST:
				v.scale((width/2)/v.xPoint);
				break;
			case SOUTH:
				v.scale(-(height/2)/v.yPoint);
				break;
			case WEST:
				v.scale(-(width/2)/v.xPoint);
				break;
		}
	}
	
	private static final Vector tempObj = new Vector(0,0);
	
	public void draw(Graphics g) {
		g.translate(width/2, height/2);
		g.scale(1.0f, -1.0f);
		WorldObject worldSource = radar.getShip();
		float worldSourceangle = worldSource.getAngle();
		WorldObject worldTarget = null;
		float targetAngle = angle;
		
		for(int i = 0; i < radar.getObjectCount(); i++)
		{
			WorldObject worldObject = radar.getObject(i);
			renderObj(g, worldObject);
			tempObj.set(worldObject.getPosition());
			tempObj.sub(worldSource.getPosition());
			float delta = Math.abs(Util.getSmallestAngle((float) Math.atan2(tempObj.yPoint,tempObj.xPoint), worldSourceangle)); 
			if (delta  <  targetAngle)
			{
				worldTarget = worldObject;
				targetAngle = delta;
			}
		}
		if (worldTarget != null){
			int dist = (int)worldTarget.getPosition().distanceToInMeters(worldSource.getPosition());
			tempObj.set((float)Math.cos(worldSource.getAngle()), (float)Math.sin(worldSource.getAngle()));
			screenLock(tempObj);
			pointerColor.a = 1.0f - targetAngle/angle;
			pointer.setRotation(worldSource.getAngle() * Util.DEGREES_IN_RADIAN);
			tempObj.scale(0.9f);
			g.drawImage(pointer, tempObj.xPoint - pointer.getWidth()/2, tempObj.yPoint - pointer.getHeight()/2, pointerColor);
			tempObj.scale(0.9f);
			g.translate(tempObj.xPoint, tempObj.yPoint);
			g.scale(1.0f, -1.0f);
			String objectName = worldTarget.getRadarIdent();
			g.drawString(objectName,-g.getFont().getWidth(objectName)/2,-8);
			objectName = dist + " meters";
			g.drawString(objectName,-g.getFont().getWidth(objectName)/2, 8);
		}
	}
	
	public void renderObj(Graphics g, WorldObject worldObject){
		int rotation = 0;
		screenPosition.set(worldObject.getPosition());
		screenPosition.sub(radar.getShip().getPosition());
		if (Math.abs(screenPosition.xPoint) < width/2 && Math.abs(screenPosition.yPoint) < height/2){
			return;
		}
		screenPosition.normalizeScreen();
		float ratio = width/height;
		Direction direction = Direction.getDirection(screenPosition.xPoint, screenPosition.yPoint*ratio); 
		switch (direction){
		case NORTH:
			rotation = 90;
			screenPosition.scale((height/2)/screenPosition.yPoint);
			screenPosition.yPoint -= 4;
			break;
		case EAST:
			screenPosition.scale((width/2)/screenPosition.xPoint);
			screenPosition.xPoint -= 4;
			rotation = 0;
			break;
		case SOUTH:
			screenPosition.scale(-(height/2)/screenPosition.yPoint);
			screenPosition.yPoint += 4;
			rotation = 270;
			break;
		case WEST:
			screenPosition.scale(-(width/2)/screenPosition.xPoint);
			screenPosition.xPoint += 4;
			rotation = 180;
			break;
		}
		imageList[0].setRotation(rotation);
		imageList[1].setRotation(rotation);
		imageList[2].setRotation(rotation);
		int directionX = direction.directionY*8;
		int directionY = -direction.directionX*8;
		Color color = worldObject instanceof Ship ? ENEMY_COLOR_RADAR : PLANET_COLOR_RADAR;
		imageList[0].draw(screenPosition.xPoint + directionX - 4, screenPosition.yPoint + directionY - 4, color);
		imageList[1].draw(screenPosition.xPoint - 4, screenPosition.yPoint - 4,           color);
		imageList[2].draw(screenPosition.xPoint - directionX - 4, screenPosition.yPoint - directionY - 4, color);

	}

	
}
