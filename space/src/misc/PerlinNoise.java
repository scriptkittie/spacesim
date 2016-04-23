
package misc;

import java.util.Random;

public class PerlinNoise {
    
    private float[][][] baseGrid;
    private int octaveLevel;
    private float fadeLevel;
    
    public PerlinNoise(int gridSize, long seed, int octaveLevel, float fadeLevel){
        baseGrid = new float[gridSize][gridSize][2];
        this.fadeLevel = fadeLevel;
        this.octaveLevel = octaveLevel;
        Random rand = new Random(seed);
        for(int i = 0; i < gridSize;i++){
            for(int j = 0; j < gridSize; j++){
                double angle = rand.nextDouble()*Math.PI*2;
                baseGrid[i][j][0] = (float)Math.sin(angle);
                baseGrid[i][j][1] = (float)Math.cos(angle);
            }
        }
    }
    

    public float summation(float xPoint, float yPoint){
        float sum = 0;
        float currentWeight = 1.0f;
        for(int i = 0; i < octaveLevel;i++){
            sum += currentWeight*generateNoise(xPoint*(1<<i),yPoint*(1<<i));
            currentWeight *= fadeLevel;
        }
        return sum;
    }
    
    public float generateNoise(float gridX, float gridY){
        int i;
        int j;
        i =(int) Math.floor(gridX);
        j =(int) Math.floor(gridY);
        gridX -= i;
        gridY -= j;
        i %= (baseGrid.length - 1);
        j %= (baseGrid[0].length - 1);
        
        float noise1 = derpa(i,j,gridX,gridY); 
        float noise2 = derpa(i+1,j,gridX-1,gridY); 
        float noise3 = derpa(i,j+1,gridX,gridY-1); 
        float noise4 = derpa(i+1,j+1,gridX-1,gridY-1); 

        float noise12 = herpa(fractalFade(gridX),noise1,noise2);
        float noise34 = herpa(fractalFade(gridX),noise3,noise4);
        return herpa(fractalFade(gridY),noise12,noise34);
    }
    
    private float derpa(int gxPoint, int gyPoint,float xPoint, float yPoint){
        return baseGrid[gxPoint][gyPoint][0]*xPoint + baseGrid[gxPoint][gyPoint][1]*yPoint;
    }
    
    
	private static float herpa(float z, float u, float v) {
		return u + z * (v - u);
	}
    
    public static float fractalFade(float opac){
        return 3*opac*opac - 2*opac*opac*opac;
    }
    
}
