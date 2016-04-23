package background;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class StarTileGrid {

	private LayerSuper[] backgroundLayers;
	
	public StarTileGrid(GameContainer gc){
		backgroundLayers = new LayerSuper[]{
		new StarGrid(0.01f, gc),
		new StarGrid(0.02f, gc),
		new PerlinNoiseLayer(0.04f, gc),
		new StarGrid(0.09f, gc),
		new StarGrid(0.12f, gc),
		new JunkLayer(1.0f, gc),
		};
	}
	
	public void draw(Graphics g, float xPoint, float yPoint){
		for(LayerSuper layer : backgroundLayers){
			layer.draw(g,xPoint,yPoint);
		}
	}
	
	
}
