
package misc;

public class FractalGen implements RandomSeedFunction {
	
	private int octaves;
	private float fadeLevel;
	private RandomSeedFunction rand;
	
	public FractalGen(RandomSeedFunction source, int octaves, float fade){
		this.rand = source;
		this.octaves = octaves;
		this.fadeLevel = fade;
	}

	public float evaluateFractal(float xCoord, float yCoord) {
        float sum = 0;
        float currentWeight = 1.0f;
        for(int i = 0; i < octaves;i++){
            sum += currentWeight*rand.evaluateFractal(xCoord*(1<<i),yCoord*(1<<i));
            currentWeight *= fadeLevel;
        }
        return sum;
	}
	
	public void setRandomSeed(int randomSeed){
		rand.setRandomSeed(randomSeed);
	}

}
