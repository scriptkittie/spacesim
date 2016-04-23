package objects;

import java.util.ArrayList;
import java.util.List;

import objects.Thruster.RotationDirection;
import objects.Thruster.ThrusterType;

import org.newdawn.slick.Graphics;

import util.DoubleVector;
import util.Util;
import util.Vector;

public class Thrusters {

	private Vector netForce;
	private Ship ship;
	private List<Thruster> thrusters;
	
	public Thrusters(Ship ship){
		this.ship = ship;
		thrusters = new ArrayList<Thruster>();
		netForce = new Vector(0,0);
	}
	
	public void setShip(Ship ship){
		this.ship = ship;
	}
	
	public void addShipThruster(Thruster thruster){
		thrusters.add(thruster);
	}
	
	public DoubleVector getNetForce(){
		netForce.set(0,0);
		for(Thruster thruster : thrusters){
			if (thruster.getThrusterType() == ThrusterType.MAIN_MODE)
			{
				netForce.addVector(thruster.getThrust(ship.getAngle()));
			}
		}
		for(Thruster thruster : thrusters)
		{
			if (thruster.getThrusterType() == ThrusterType.MANUVERING_MODE)
			{
				netForce.addVector(thruster.getThrust(ship.getAngle()));
			}
		}
		return netForce;
	}
	
	public float getTorque(){
		float torqueValue = 0;
		for(Thruster thruster : thrusters)
		{
			if (thruster.getThrusterType() == ThrusterType.MANUVERING_MODE)
			{
				torqueValue += thruster.getTorque();
			}
		}
		return torqueValue;
	}
	
	private final Vector velocity1 = new Vector(0,0); 
	private final Vector velocity2 = new Vector(0,0);
	
	public void setThrusterControls(DoubleVector targetVelocity, float targetAngle, boolean useVelcityForMovement){
		
		velocity1.set(targetVelocity);
		velocity1.sub(ship.getVelocity());
		for(Thruster childthruster : thrusters)
		{
			if (childthruster.getThrusterType() == ThrusterType.MAIN_MODE){
				velocity2.set(childthruster.getDirection());
				velocity2.rotate(ship.getAngle());
				float value = velocity1.center(velocity2);
				childthruster.setValue(Util.lock(value, 0, 1.0f));
			}
		}
		for(Thruster childThruster : thrusters)
		{
			if (childThruster.getThrusterType() == ThrusterType.MANUVERING_MODE){
				if (useVelcityForMovement){
					velocity2.set(childThruster.getDirection());
					velocity2.rotate(ship.getAngle());
					float value = velocity1.center(velocity2);
					velocity1.set(targetVelocity);
					velocity1.sub(ship.getVelocity());
					childThruster.setValue(Util.lock(value*velocity1.vectorLength(), 0, 1.0f)); 
				} else {
					childThruster.setValue(0);
				}
			}
		}
		float moveMentNeeded = Util.getSmallestAngle(targetAngle,ship.getAngle())/(float)Math.PI;
		moveMentNeeded *= 50.0f;
		moveMentNeeded += ship.getRotationRate()*0.1f;
		moveMentNeeded = Util.lock(moveMentNeeded, -1.0f, 1.0f);
		for(Thruster childthruster : thrusters){
			if (childthruster.getThrusterType() == ThrusterType.MANUVERING_MODE)
			{
				float currentValue = childthruster.getValue();
				if (childthruster.getTorqueDir() == RotationDirection.LEFT && moveMentNeeded > 0.0f){
					childthruster.setValue(Math.abs(moveMentNeeded) + currentValue);
				} else if (childthruster.getTorqueDir() == RotationDirection.RIGHT && moveMentNeeded < 0.0f){
					childthruster.setValue(Math.abs(moveMentNeeded) + currentValue);
				}
			}
		}
	}
	
	public void draw(Graphics g)
	{
		for(Thruster thruster : thrusters){
			thruster.draw(g);
		}
	}
	
}
