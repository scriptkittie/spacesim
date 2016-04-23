package util;

public class Vector implements DoubleVector {

	public float xPoint;
	public float yPoint;
	
	public Vector(float x, float y){
		this.xPoint = x;
		this.yPoint = y;
	}
	
	public Vector(DoubleVector vectorD){
		this.xPoint = vectorD.getXCoord();
		this.yPoint = vectorD.getYCoord();
	}
	
	public void set(float x, float y){
		this.xPoint = x;
		this.yPoint = y;
	}
	
	public void set(DoubleVector vectorD){
		this.xPoint = vectorD.getXCoord();
		this.yPoint = vectorD.getYCoord();
	}
	
	public float intersectVector(DoubleVector vectorD){
		return xPoint*vectorD.getYCoord() - yPoint*vectorD.getXCoord();
	}
	
	public float getRotationalAngle(DoubleVector vectorD){
		return Util.getSmallestAngle((float)Math.atan2(yPoint, xPoint),(float)Math.atan2(vectorD.getYCoord(), vectorD.getXCoord()));
	}
	
	public float getXCoord() {
		return xPoint;
	}

	public float getYCoord() {
		return yPoint;
	}
	
	public void rotate(float angle){
		float cosAngle = (float)Math.cos(angle);
		float sinAngle = (float)Math.sin(angle);
		float oldX = xPoint;
		xPoint = cosAngle*xPoint - sinAngle*yPoint;
		yPoint = sinAngle*oldX + cosAngle*yPoint;
	}
	
	public float center(DoubleVector vectorD){
		return xPoint*vectorD.getXCoord() + yPoint*vectorD.getYCoord();
	}
	
	public void add(float x, float y){
		this.xPoint += x;
		this.yPoint += y;
	}
	
	public void subtract(float x, float y){
		this.xPoint -= x;
		this.yPoint -= y;
	}
	
	public void addVector(DoubleVector vectorD){
		xPoint += vectorD.getXCoord();
		yPoint += vectorD.getYCoord();
	}
	
	public void sub(DoubleVector vectorD){
		this.xPoint -= vectorD.getXCoord();
		this.yPoint -= vectorD.getYCoord();
	}
	
	public void scale(float value){
		xPoint*=value;
		yPoint*=value;
	}
	
	public void addScaled(DoubleVector vectorDector, float vectorScale){
		xPoint += vectorDector.getXCoord()*vectorScale;
		yPoint += vectorDector.getYCoord()*vectorScale;
	}
	
	public void normalizeScreen(){
		scale(1/vectorLength());
	}
	
	public float vectorLength(){
		return (float)Math.sqrt(xPoint*xPoint + yPoint*yPoint);
	}
	
	public float vectorLengthSquared(){
		return xPoint*xPoint + yPoint*yPoint;
	}
	
	public float distanceToInMeters(DoubleVector vectorD){
		return (float)Math.sqrt((vectorD.getXCoord() - xPoint)*(vectorD.getXCoord() - xPoint) + (vectorD.getYCoord() - yPoint)*(vectorD.getYCoord() - yPoint));
	}
	
	public float distanceTo2(DoubleVector vectorD){
		return (vectorD.getXCoord() - xPoint)*(vectorD.getXCoord() - xPoint) + (vectorD.getYCoord() - yPoint)*(vectorD.getYCoord() - yPoint);
	}
	
	public float distanceTo(float px, float py){
		return (float)Math.sqrt((xPoint - px)*(xPoint - px) + (yPoint - py)*(yPoint - py));
	}
	
	public String toString(){
		return "(" + xPoint + ", " + yPoint + ")";
	}

}
