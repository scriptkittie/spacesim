
package planets;

import java.util.HashMap;
import java.util.Map;

import misc.DeathAdder;
import misc.FractalGen;
import misc.RandomSeedFunction;
import misc.ScalarHolder;
import misc.SimplexNoiseGenerator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;

import util.Util;
import util.Vector;

public class PLanetGenerator {
	
	private Map<String, PlanetType> planetTypes;
	private static final float PLANET_LIGHT = 0.2f;
	private static final float PI_FLOAT = (float)Math.PI;

	public PLanetGenerator()
	{
		RandomSeedFunction planetFractal1 = new FractalGen(new SimplexNoiseGenerator(0),7, 0.5f);
		RandomSeedFunction planetFractal2 = new FractalGen(new SimplexNoiseGenerator(0),8, 0.7f);
		RandomSeedFunction planetFractal3 = new ScalarHolder(0.05f, 1.0f, new FractalGen(new SimplexNoiseGenerator(0),6, 0.5f));
		RandomSeedFunction simpleFractal = new ScalarHolder(2.0f, 2.0f, planetFractal2);
		RandomSeedFunction oddFractal = new ScalarHolder(0.5f, 1.0f, new FractalGen(new SimplexNoiseGenerator(0),5, 0.5f));
		RandomSeedFunction thickFractal = new ScalarHolder(20.0f, 20.0f, new FractalGen(new SimplexNoiseGenerator(0),4, 0.8f));
		RandomSeedFunction jupiterFractal = new DeathAdder(planetFractal3, 0.9f, simpleFractal, 0.1f);
		planetTypes = new HashMap<String, PlanetType>();
		try {
			planetTypes.put("Sun", new PlanetType(new Image("data/images/sun.png"),           thickFractal, new Color(255,192,128)));
			planetTypes.put("GasGiant",new PlanetType(new Image("data/images/gas.png"), jupiterFractal, null));
			planetTypes.put("Moon", new PlanetType(new Image("data/images/moon.png"),         planetFractal1, null));
			planetTypes.put("Earth", new PlanetType(new Image("data/images/earth.png"),   planetFractal1, new Color(64,128,255)));
			planetTypes.put("Mercury", new PlanetType(new Image("data/images/mercury.png"),   simpleFractal, null));
			planetTypes.put("Venus", new PlanetType(new Image("data/images/mercury.png"),   oddFractal, new Color(192,180,120)));
			planetTypes.put("Mars", new PlanetType(new Image("data/images/mars.png"),   planetFractal2, new Color(192,64,64)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void findPlanetPosition(float xPoint, float yPoint, Vector destinationVector) {
	  float zAxis;
	  float xAngle;
	  float yAngle;
	  float thetaAnglex;
	  float thetaAngley;
	  float ps = 0.5f;

	  zAxis = (float)Math.sqrt ((1 - xPoint * xPoint  - yPoint * yPoint ));

	  xAngle = (float)Math.acos(xPoint / Math.sqrt(xPoint * xPoint + zAxis * zAxis));
	  thetaAnglex = PI_FLOAT / 2 - xAngle;
	  thetaAngley = (float)Math.asin (Math.sin (thetaAnglex) * ps);
	  thetaAngley = PI_FLOAT / 2 - xAngle - thetaAngley;
	  destinationVector.xPoint = xPoint - (float)Math.tan (thetaAngley) * zAxis;

	  yAngle = (float)Math.acos (yPoint / Math.sqrt(yPoint * yPoint + zAxis * zAxis));
	  thetaAnglex = PI_FLOAT / 2 - yAngle;
	  thetaAngley = (float)Math.asin(Math.sin (thetaAnglex) * ps);
	  thetaAngley = PI_FLOAT / 2 - yAngle - thetaAngley;
	  destinationVector.yPoint = yPoint - (float)Math.tan (thetaAngley) * zAxis;
	  
	}
	
	public Planet generatePlanet(String planetName, int planetSize, String planetType, int randomSeed, Vector planetPosition){
		Vector positionVector = new Vector(0,0);
		PlanetType pType = planetTypes.get(planetType);
		pType.setRandomSeed(randomSeed);
		ImageBuffer imageBuffer = new ImageBuffer(planetSize,planetSize);
		int radiusZ = planetSize/2;
		boolean isEmersive = false;
		Vector lightPos = new Vector(planetPosition);
		if (planetPosition.vectorLength() < 0.01f)
		{
			isEmersive = true;
		} 
		else 
		{
			lightPos.normalizeScreen();
			lightPos.scale(-1.0f);
		}
		float atmosphereStart = isEmersive ? 0.7f:0.9f;
			
		Color ambientAtomosphere = pType.getAtmosphere();
		for(int i = 0; i < planetSize; i++){
			for(int j = 0; j < planetSize; j++){
				float directionX = (i - radiusZ)/(float)(radiusZ);
				float directionY = (j - radiusZ)/(float)(radiusZ);
				float planetLight = lightPos.xPoint*directionX + lightPos.yPoint*directionY;
				if (isEmersive){
					planetLight = 1.0f;
				}
				planetLight = Util.lock(planetLight, 0.0f, 1.0f - PLANET_LIGHT) + PLANET_LIGHT;
				float direction = (float)Math.sqrt(directionX*directionX + directionY*directionY);
				if (direction < atmosphereStart)
				{
					directionX /= atmosphereStart;
					directionY /= atmosphereStart;
					findPlanetPosition(directionX,directionY,positionVector);
					int[] rgbColor = pType.getRGB(positionVector.xPoint, positionVector.yPoint);
					imageBuffer.setRGBA(i, j, (int)(rgbColor[0]*planetLight), (int)(rgbColor[1]*planetLight), (int)(rgbColor[2]*planetLight), 255);
				} 
				else if (direction < 1.0f)
				{
					if (pType.hasAtmosphere()){
						direction = (1.0f - direction)/(1.1f - atmosphereStart);
						imageBuffer.setRGBA(i, j, ambientAtomosphere.getRedByte(), ambientAtomosphere.getGreenByte(), ambientAtomosphere.getBlueByte(),(int)(direction*planetLight*255));
					} 
					else
					{
						imageBuffer.setRGBA(i, j, 0, 0, 0,0);
					}
				} 
				else 
				{
					imageBuffer.setRGBA(i,j,0,0,0,0);
				}
			}
		}
		return new Planet(planetName,imageBuffer.getImage(),planetPosition);
	}
	
}
