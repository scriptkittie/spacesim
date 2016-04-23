
package misc;

import java.util.Random;

public class SimplexNoiseGenerator implements RandomSeedFunction {

	private static final int BASEGRID[][] = {{1,1},{-1,1},{1,-1},{-1,-1},
										{1,0},{-1,0},{1,0},{-1,0},
										{0,1},{0,-1},{0,1},{0,-1}};
	private static int optimizedFloor(float value) {
	  return value> 0 ? (int)value : (int)value-1;
	}
	 
	private static float coordinate(int g[], float xPoint, float yPoint) {
	  return g[0]*xPoint + g[1]*yPoint;
	}

	private static final float float2 = (float)(0.5*(Math.sqrt(3.0)-1.0)); 
	private static final float grav2 = (float)((3.0-Math.sqrt(3.0))/6.0);
	
	private int[] gridArray = new int[512];
	
	public SimplexNoiseGenerator(int randomSeed){
		setRandomSeed(randomSeed);
	}
	 
	public void setRandomSeed(int randomSeed){
		Random r = new Random(randomSeed);
		for(int i = 0; i < 256; i++){
			gridArray[i] = r.nextInt(256);
			gridArray[i+256] = gridArray[i];
		}
	}
	
	
	public float evaluateFractal(float gridX, float gridY) {
	  float a1;
	  float a2;
	  float a3; 
	  
	  float z = (gridX+gridY)*float2; 
	  int n = optimizedFloor(gridX+z);
	  int m = optimizedFloor(gridY+z);
	  float p = (n+m)*grav2;
	  float xinput0 = n-p; 
	  float yinput0 = m-p;
	  float Xinput0 = gridX-xinput0; 
	  float Yinput0 = gridY-yinput0;

	  int indexW;
	  int indexJ; 
	  if(Xinput0>Yinput0) 
	  {
		  indexW=1; indexJ=0;
	  } 
	  else 
	  {
		  indexW=0; indexJ=1;
		  
	  }         
	  float   xCoord1  = Xinput0 - indexW + grav2; 
	  float   ycoord1  = Yinput0 - indexJ + grav2;
	  float   xCoord2  = Xinput0 - 1.0f + 2.0f * grav2; 
	  float   yCoord2  = Yinput0 - 1.0f + 2.0f * grav2;
	  int nn = n & 255;
	  int mm = m & 255;
	  
	  
	  
	  int gi0 = gridArray[nn+gridArray[mm]] % 12;
	  int gi1 = gridArray[nn+indexW+gridArray[mm+indexJ]] % 12;
	  int gi2 = gridArray[nn+1+gridArray[mm+1]] % 12;
	  float r0 = 0.5f - Xinput0*Xinput0-Yinput0*Yinput0;
	  if (r0<0)
	  {
		  a1 = 0.0f;
	  } 
	  else 
	  {
	    r0 *= r0;
	    a1 = r0 * r0 * coordinate(BASEGRID[gi0], Xinput0, Yinput0); 
	  }
	  
	  float r2 = 0.5f - xCoord1*xCoord1-ycoord1*ycoord1;
	  
	  if (r2<0) {
		  a2 = 0.0f;
	  } else {
	    r2 *= r2;
	    a2 = r2 * r2 * coordinate(BASEGRID[gi1], xCoord1, ycoord1);
	  }
	  
	  float t2 = 0.5f - xCoord2*xCoord2-yCoord2*yCoord2;
	  
	  if(t2<0)
	  {
		  a3 = 0.0f;
	  }
	  else
	  {
	    t2 *= t2;
	    a3 = t2 * t2 * coordinate(BASEGRID[gi2], xCoord2, yCoord2);
	  }
	  
	  
	  return 70.0f * (a1 + a2 + a3);
	}
}
