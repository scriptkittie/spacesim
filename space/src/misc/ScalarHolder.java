
package misc;

public class ScalarHolder implements RandomSeedFunction {

	private float skewLevelX;
	private float skewLevelY;
	private RandomSeedFunction rand;
	
	public ScalarHolder(float skewLevelX, float skewLevelY, RandomSeedFunction rand){
		this.rand = rand;
		this.skewLevelX = skewLevelX;
		this.skewLevelY = skewLevelY;
	}
	
	public float evaluateFractal(float gridX, float gridY) {
		return rand.evaluateFractal(gridX*skewLevelX, gridY*skewLevelY);
	}

	public void setRandomSeed(int randomSeed) {
		rand.setRandomSeed(randomSeed);
	}

}
