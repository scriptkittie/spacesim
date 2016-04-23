
package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import util.DoubleVector;
import util.Util;
import util.Vector;

public class Thruster implements Cloneable {

	private Color INNER_THRUSTER_COLOR = new Color(255, 255, 255);
	private Color MIDDLE_THRUSTER_COLOR = new Color(128, 179, 255);
	private Color OUTER_THRUSTER_COLOR = new Color(32, 64, 255);
	
	public enum ThrusterType {
		MANUVERING_MODE,
		MAIN_MODE;
		
		public static ThrusterType forName(String name){
			if (name.equalsIgnoreCase("manuver")){
				return MANUVERING_MODE;
			} else {
				return MAIN_MODE;
			}
		}
	}
	
	private static final TriangulatorSpace TRIANGLESSSS = new TriangulatorSpace();
	private Vector thrusterPosition;
	private Vector thrusterDirection;
	private float thrusterValue;
	private float thrusterSize;
	private ThrusterType thrusterType;
	
	public enum RotationDirection {
		LEFT,
		RIGHT
	}
	
	public Thruster(DoubleVector position, DoubleVector direction, float thrusterSize, ThrusterType thrusterType){
		this.thrusterSize = thrusterSize;
		this.thrusterPosition = new Vector(position);
		this.thrusterDirection = new Vector(direction);
		this.thrusterType = thrusterType;
	}
	
	public DoubleVector getDirection(){
		return thrusterDirection;
	}
	
	public DoubleVector getPosition(){
		return thrusterPosition;
	}
	
	public ThrusterType getThrusterType(){
		return thrusterType;
	}
	
	public void setValue(float value){
		this.thrusterValue = Util.lock(value, 0.0f, 1.0f);
	}
	
	public float getValue(){
		return thrusterValue;
	}
	
	private static final Vector tempVector = new Vector(0,0);
	
	public void draw(Graphics g){
		
		int back = (int)((Math.random()*6 + thrusterSize*8));
		tempVector.set(thrusterDirection.yPoint, -thrusterDirection.xPoint);
		TRIANGLESSSS.setPoint(0,thrusterPosition.xPoint - thrusterDirection.xPoint*thrusterValue*back, thrusterPosition.yPoint - thrusterDirection.yPoint*thrusterValue*back);
		TRIANGLESSSS.setPoint(2,thrusterPosition.xPoint - tempVector.xPoint*thrusterSize,thrusterPosition.yPoint - tempVector.yPoint*thrusterSize);
		TRIANGLESSSS.setPoint(1,thrusterPosition.xPoint + tempVector.xPoint*thrusterSize,thrusterPosition.yPoint + tempVector.yPoint*thrusterSize);
		g.setColor(OUTER_THRUSTER_COLOR);
		g.fill(TRIANGLESSSS);
		TRIANGLESSSS.setPoint(0,thrusterPosition.xPoint - thrusterDirection.xPoint*thrusterValue*back*0.6f, thrusterPosition.yPoint - thrusterDirection.yPoint*thrusterValue*back*0.6f);
		TRIANGLESSSS.setPoint(1,thrusterPosition.xPoint + tempVector.xPoint*thrusterSize*.6f,thrusterPosition.yPoint + tempVector.yPoint*thrusterSize*.6f);
		TRIANGLESSSS.setPoint(2,thrusterPosition.xPoint - tempVector.xPoint*thrusterSize*.6f,thrusterPosition.yPoint - tempVector.yPoint*thrusterSize*.6f);
		g.setColor(MIDDLE_THRUSTER_COLOR);
		g.fill(TRIANGLESSSS);
		TRIANGLESSSS.setPoint(0,thrusterPosition.xPoint - thrusterDirection.xPoint*thrusterValue*back*0.3f, thrusterPosition.yPoint - thrusterDirection.yPoint*thrusterValue*back*0.3f);
		TRIANGLESSSS.setPoint(1,thrusterPosition.xPoint + tempVector.xPoint*thrusterSize*.3f,thrusterPosition.yPoint + tempVector.yPoint*thrusterSize*.3f);
		TRIANGLESSSS.setPoint(2,thrusterPosition.xPoint - tempVector.xPoint*thrusterSize*.3f,thrusterPosition.yPoint - tempVector.yPoint*thrusterSize*.3f);
		g.setColor(INNER_THRUSTER_COLOR);
		g.fill(TRIANGLESSSS);
	}
	
	public DoubleVector getThrust(float heading){
		tempVector.set(thrusterDirection);
		tempVector.scale(thrusterValue*thrusterSize*100);
		tempVector.rotate(heading);
		return tempVector;
	}
	
	public float getTorque(){
		return thrusterPosition.intersectVector(thrusterDirection)*thrusterValue;
	}
	
	public RotationDirection getTorqueDir(){
		if (thrusterPosition.intersectVector(thrusterDirection) < 0){
			return RotationDirection.LEFT;
		} else {
			return RotationDirection.RIGHT;
		}
	}
	
	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

}
