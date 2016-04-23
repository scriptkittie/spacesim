package background;

import java.util.Random;

import org.newdawn.slick.Graphics;

public class StarTile extends Tile {

	private static final Random rand1 = new Random();
	private Star[] starArray;
	
	public StarTile(int x, int y, StarGrid starLayer) {
		super(x, y);
		rand1.setSeed(hashCode(x,y) + starLayer.hashCode());
		starArray = new Star[rand1.nextInt(2) + 1];
		int types = starLayer.getStarCount();
		for(int i = 0; i < starArray.length; i++)
		{
			starArray[i] = new Star((x + rand1.nextFloat())*Tile.TILE_CELL_SIZE,
								(y + rand1.nextFloat())*Tile.TILE_CELL_SIZE,
								starLayer.getStarImage(rand1.nextInt(types)));
		}
	}

	public void draw(Graphics g) {
		for(Star starObj : starArray)
		{
			starObj.draw(g);
		}
	}
}
