package background;

import org.newdawn.slick.Graphics;

public abstract class Tile {
	
	public static final int TILE_CELL_SIZE = 128;
	protected int xPoint, yPoint;
	
	public Tile(int xPoint, int yPoint){
		this.xPoint = xPoint;
		this.yPoint = yPoint;
	}

	public boolean memoryCache(float xCoord, float yCoord, float width, float height){
		int cx = this.xPoint*TILE_CELL_SIZE;
		int cy = this.yPoint*TILE_CELL_SIZE;
		
		float xDistance = Math.max(xCoord - (cx + TILE_CELL_SIZE),cx  - (xCoord + width));
		float yDistance = Math.max(yCoord - (cy + TILE_CELL_SIZE),cy  - (yCoord + height));
		
		return xDistance <= TILE_CELL_SIZE*2 && yDistance <= TILE_CELL_SIZE*2;
	}

	public static int hashCode(int x, int y){
		return x*100 + y;
	}
	
	public void freeCellResources(){}
	
	public abstract void draw(Graphics g);

}
