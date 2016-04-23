package util;

public interface DoubleVector {

	public float center(DoubleVector vectorD);
	public float distanceTo2(DoubleVector vectorD);
	public float distanceToInMeters(DoubleVector vectorD);
	public void  addVector(DoubleVector vectorD);
	public float getXCoord();
	public float getYCoord();
	public float getRotationalAngle(DoubleVector vectorD);
	public float vectorLength();
	public float vectorLengthSquared();
	
}
