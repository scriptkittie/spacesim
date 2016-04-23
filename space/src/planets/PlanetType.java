package planets;

import misc.RandomSeedFunction;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import util.Util;

public class PlanetType {
	private int colorPallete[][];
	private RandomSeedFunction planetTexture;
	private Color planetAtmosphere;
	
	public PlanetType(Image image, RandomSeedFunction texture, Color atmosphere){
		this.planetTexture = texture;
		this.planetAtmosphere = atmosphere;
		colorPallete = new int[image.getWidth()][3];
		for(int i = 0; i < colorPallete.length; i++){
			Color c = image.getColor(i, 0);
			colorPallete[i][0] = c.getRedByte();
			colorPallete[i][1] = c.getGreenByte();
			colorPallete[i][2] = c.getBlueByte();
		}
	}
	
	public boolean hasAtmosphere(){
		return planetAtmosphere != null;
	}
	
	public Color getAtmosphere(){
		return planetAtmosphere;
	}
	
	public int[] getRGB(float positionX, float positionY){
		float val = (planetTexture.evaluateFractal(positionX, positionY) + 1.0f)/2.0f;
		val = Util.lock(val,0,1.0f);
		return colorPallete[(int)(val*255)];
	}
	
	public void setRandomSeed(int randomSeed){
		planetTexture.setRandomSeed(randomSeed);
	}
	
	
}
