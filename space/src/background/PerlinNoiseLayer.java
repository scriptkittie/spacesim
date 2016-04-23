package background;

import misc.FractalGen;
import misc.Functor;
import misc.SimplexNoiseGenerator;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;

import util.Util;

public class PerlinNoiseLayer extends TileLayer {

	private Functor sourceFunctor;
	private ImageBuffer imageBuffer;
	private int colorPallete[][];
	
	public PerlinNoiseLayer(float scrollScale, GameContainer gc){
		super(scrollScale,gc);
		sourceFunctor = new FractalGen(new SimplexNoiseGenerator(1),8,0.6f);
		imageBuffer = new ImageBuffer(ImageTile.TILE_CELL_SIZE, ImageTile.TILE_CELL_SIZE);
		
		try 
		{
			Image imageSource = new Image("data/images/blue.png");
			colorPallete = new int[imageSource.getWidth()][3];
			for(int i = 0; i < colorPallete.length; i++){
				Color color = imageSource.getColor(i, 0);
				colorPallete[i][0] = color.getRedByte();
				colorPallete[i][1] = color.getGreenByte();
				colorPallete[i][2] = color.getBlueByte();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	public Tile createTileCell(int cellXPoint, int cellyPoinrt) {
		float x = cellXPoint * ImageTile.TILE_CELL_SIZE;
		float y = cellyPoinrt * ImageTile.TILE_CELL_SIZE;
		for(int i = 0; i < imageBuffer.getWidth(); i++){
			for (int j = 0; j < imageBuffer.getHeight(); j++){
				float val = (sourceFunctor.evaluateFractal((x + i)/2000.0f, (y + j)/2000.0f) + 1.0f)/2.5f;
				val = Util.lock(val, 0.0f, 1.0f);
				int imageIndex = (int)(val*255);
				imageBuffer.setRGBA(i, j, colorPallete[imageIndex][0], colorPallete[imageIndex][1], colorPallete[imageIndex][2], imageIndex);
			}
		}
		return new ImageTile(cellXPoint,cellyPoinrt,imageBuffer.getImage(Image.FILTER_NEAREST));
	}

}
