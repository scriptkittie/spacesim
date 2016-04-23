package background;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class StarGrid extends TileLayer {

	private Image[] starImageArray;

	public StarGrid(float scrollLevel, GameContainer gc) {
		super(scrollLevel, gc);
		starImageArray = new Image[4];
		try {
			starImageArray[0] = new Image("data/sprites/star_1.png");
			starImageArray[1] = new Image("data/sprites/star_2.png");
			starImageArray[2] = new Image("data/sprites/star_3.png");
			starImageArray[3] = new Image("data/sprites/star_4.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getStarCount(){
		return starImageArray.length;
	}
	
	public Image getStarImage(int index){
		return starImageArray[index];
	}


	public Tile createTileCell(int xPoint, int yPoint) {
		return new StarTile(xPoint,yPoint,this);
	}
}
