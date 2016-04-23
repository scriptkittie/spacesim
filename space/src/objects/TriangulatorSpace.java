
package objects;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Triangulator;

public class TriangulatorSpace extends Shape implements Triangulator {

	private static final float[] centerPoint = new float[2];
	private static final float[] TRIANGLE_CENTER = new float[]{0.0f,0.0f};
	
	public TriangulatorSpace(){
		points = new float[6];
	}
	
	public void setPoint(int pointIndex, float x, float y){
		points[pointIndex*2] = x;
		points[pointIndex*2 + 1] = y;
	}
	
	public void setPoints(float xCoord1, float yCoord1, float xCoord2, float yCoord2, float xCoord3, float yCoord3){
		points[0] = xCoord1;
		points[1] = yCoord1;
		points[2] = xCoord2;
		points[3] = yCoord2;
		points[4] = xCoord3;
		points[5] = yCoord3;
	}
	
	protected void createPoints() {}

	public Shape transform(Transform arg0) {
		return null;
	}
	
	public Triangulator getTriangles(){
		return this;
	}
	
	public float[] getCenter(){
		return TRIANGLE_CENTER;
	}

	public void addPolyPoint(float x, float y) {}

	public int getTriangleCount() {
		return 1;
	}

	public float[] getTrianglePoint(int tri, int i) {
		centerPoint[0] = points[i*2];
		centerPoint[1] = points[i*2 + 1];
		return centerPoint;
	}

	public void startHole() {}

	public boolean triangulate() {
		return true;
	}
}
