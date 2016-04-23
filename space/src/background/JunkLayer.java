package background;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class JunkLayer extends LayerSuper {

	private DebrisParticle[] debrisParticles;
	private Image[] debrisImages;
	private boolean precache;
	
	public JunkLayer(float scrollScale, GameContainer gc) {
		super(scrollScale, gc);
		debrisParticles = new DebrisParticle[20];
		precache = true;
		try {
			debrisImages = new Image[]{
					new Image("data/sprites/debris1.png"),
					new Image("data/sprites/debris2.png")
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
		Random r = new Random();
		for(int i = 0; i < debrisParticles.length; i++){
			debrisParticles[i] = new DebrisParticle(debrisImages[r.nextInt(debrisImages.length)]);
		}
	}
	
	public void update(float x, float y, boolean precache){
		for(int i = 0; i < debrisParticles.length; i++){
			if (!debrisParticles[i].onScreen(x, y, width, height)){
				float px = (float)Math.random()*width;
				float py = (float)Math.random()*height;
				if (!precache){
					if (Math.abs(px) > Math.abs(py)){
						if (px < width/2){
							px = 0;
						} else {
							px = width;
						}
					} else {
						if (py < height/2){
							py = 0;
						} else {
							py = height;
						}
					}
				}
				px += x;
				py += y;
				debrisParticles[i].setPos(px,py);
			}
		}
	}

	public void draw(Graphics g, float x, float y) {
		update(x,y, precache);
		g.translate(-x - width/2, -y - height/2);
		for(int i = 0; i < debrisParticles.length; i++){
			debrisParticles[i].draw(g);
		}
		g.translate(x + width/2, y + height/2);
		precache = false;
	}

}
