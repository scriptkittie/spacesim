
package misc;

public class DeathAdder implements RandomSeedFunction {

	private RandomSeedFunction rand1, rand2;
	private float weightLevel, weightLevel2;
	
	public DeathAdder(RandomSeedFunction rand1, float weightLevel, RandomSeedFunction rand2, float weightLevel2){
		this.rand1 = rand1;
		this.rand2 = rand2;
		this.weightLevel = weightLevel;
		this.weightLevel2 = weightLevel2;
	}
	
	public float evaluateFractal(float xCoord, float yCoord) {
		return rand1.evaluateFractal(xCoord, yCoord)*weightLevel + rand2.evaluateFractal(xCoord, yCoord)*weightLevel2;
	}

	public void setRandomSeed(int randomSeed) {
		rand1.setRandomSeed(randomSeed);
		rand2.setRandomSeed(randomSeed);
	}

}
