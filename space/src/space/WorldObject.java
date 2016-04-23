
package space;

import org.newdawn.slick.Graphics;

import util.DoubleVector;
import util.Util;
import util.Vector;

public abstract class WorldObject {

	protected Vector position;
	protected Vector velocity;
	protected Vector netForce;
	protected float    mass;
	protected float    inertia;
	protected float    torque;
	protected float    objectRotation;
	protected float    rotationRate;
	protected float    radius;
	public abstract boolean isAlive();
	public abstract String getRadarIdent();
	public abstract void OnCollision(WorldObject other);
	public abstract void draw(Graphics g);
	private static final Vector temp = new Vector(0,0);
	private static final Vector velocity2 = new Vector(0,0);
	
	public WorldObject(float mass, float radius){
		this.mass = mass;
		this.radius = radius;
		inertia = 1.0f;
		position = new Vector(0,0);
		velocity = new Vector(0,0);
		netForce = new Vector(0,0);
	}
	
	public void update(World world, float step){
		position.addScaled(velocity, step);
		velocity.addScaled(netForce,step/mass);
		netForce.set(0,0);
		
		objectRotation += rotationRate*(step/inertia);
		rotationRate += torque/inertia;
		objectRotation = Util.normaliseAngle(objectRotation);
		torque  = 0;
	}
	
	public void moveObject(float dx, float dy){
		position.add(dx,dy);
	}
	
	public float getMass(){
		return mass;
	}
	
	public float getAngle(){
		return objectRotation;
	}
	
	public float getRotationRate(){
		return rotationRate;
	}
	
	public DoubleVector getPosition(){
		return position;
	}
	
	public DoubleVector getVelocity(){
		return velocity;
	}
	
	public void setAngle(float rotation){
		this.objectRotation = rotation;
	}
	
	public void setVelocity(DoubleVector vectorDel){
		this.velocity.set(vectorDel);
	}
	
	public void setPosition(float x, float y){
		position.set(x,y);
	}

	public void setPosition(DoubleVector vectorD){
		position.set(vectorD);
	}
	
	public void addForce(DoubleVector vectorDal){
		netForce.addVector(vectorDal);
	}
	
	public void addToTorque(float value){
		torque += value;
	}
	
	public float getSpeed(){
		return velocity.vectorLength();
	}
	
	public String getName(){
		return null;
	}
	
	public boolean onCollision(WorldObject other){
		float r = radius + other.radius;
		return (position.distanceTo2(other.position) < r*r);
	}

	public static void seperate(WorldObject worldObject1, WorldObject worldObject2){
		float radius = worldObject1.radius + worldObject2.radius;
		temp.set(worldObject1.position);
		temp.sub(worldObject2.position);
		float diameter = temp.vectorLength() - radius;
		if (diameter < 0.0f)
		{
			temp.normalizeScreen();

			velocity2.set(temp);
			velocity2.scale(0.1f);
			velocity2.addVector(worldObject1.getVelocity());
			worldObject1.setVelocity(velocity2);

			velocity2.set(temp);
			velocity2.scale(-0.1f);
			velocity2.addVector(worldObject2.getVelocity());
			worldObject2.setVelocity(velocity2);

			temp.scale(-diameter);
			float netMass = worldObject2.getMass()/(worldObject1.getMass() + worldObject2.getMass());
			worldObject1.moveObject(temp.xPoint*netMass, temp.yPoint*netMass);
			netMass = 1.0f - netMass;
			worldObject2.moveObject(-temp.xPoint*netMass, -temp.yPoint*netMass);
		}
	}
	
}
