package background;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class LayerSuper {
	
	protected float scrollScale;
	protected int width, height;
	
	public LayerSuper(float scrollLevel, GameContainer gc){
		this.scrollScale = scrollLevel;
		width = gc.getWidth();
		height = gc.getHeight();
	}

	public abstract void draw(Graphics g, float xPoint, float yPoint);
}
