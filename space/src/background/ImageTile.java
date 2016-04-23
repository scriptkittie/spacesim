package background;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageTile extends Tile{

	private Image img;
	
	public ImageTile(int x, int y, Image img){
		super(x,y);
		this.img = img;
	}
	
	public void freeCellResources(){
		try {
			img.destroy();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		img = null;
	}

	
	public void draw(Graphics g){
		g.drawImage(img, xPoint*Tile.TILE_CELL_SIZE, yPoint*Tile.TILE_CELL_SIZE);
	}
	
}
