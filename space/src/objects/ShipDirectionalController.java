
package objects;

import objects.WeaponSub.WeaponTypes;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import util.Util;
import util.Vector;

public class ShipDirectionalController {

	private static final float SHIP_ROTATIONAL_SPEED_FLOAT = 3.0f;
	public enum ControlType {
		AUTO_ACCELERATE,
		AUTO_BREAK,
		AUTO_DRIFT,
		ACCELLERATE,
		BREAK,
		DRIFT,
	}
	
	private Ship playerShip;
	private float rotationalAngle;
	private Vector targetDirection;
	private ControlType controlMode;
	private Image crosshairImage;
	private Vector targetVelocity;
	private float maxAutoSpeed;
	private float maxManualSpeed;
	
	
	public ShipDirectionalController(){
		targetDirection = new Vector(0,0);
		targetVelocity = new Vector(0,0);
		controlMode = ControlType.AUTO_BREAK;
		maxAutoSpeed = 500;
		maxManualSpeed = 1000;
		try {
			crosshairImage = new Image("data/images/target.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ControlType getControlMode(Input localInput){
		ControlType mode;
		if (localInput.isKeyDown(Input.KEY_LSHIFT) || localInput.isKeyDown(Input.KEY_RSHIFT)){
			if (localInput.isKeyDown(Input.KEY_UP)){
				mode = ControlType.ACCELLERATE;
			} else if (localInput.isKeyDown(Input.KEY_DOWN)){
				mode = ControlType.BREAK;
			} else{
				mode = ControlType.DRIFT;
			}
		} else {
			if (localInput.isKeyDown(Input.KEY_UP)){
				mode = ControlType.AUTO_ACCELERATE;
			} else if (localInput.isKeyDown(Input.KEY_DOWN)){
				mode = ControlType.AUTO_BREAK;
			} else{
				mode = ControlType.AUTO_DRIFT;
			}
		}
		return mode;
	}
	

	public void update(Input localInput, float timestep){
		if (localInput.isKeyDown(Input.KEY_LEFT)){
			rotationalAngle += timestep*SHIP_ROTATIONAL_SPEED_FLOAT;
		} else if (localInput.isKeyDown(Input.KEY_RIGHT)){
			rotationalAngle -= timestep*SHIP_ROTATIONAL_SPEED_FLOAT;
		}
		targetDirection.set((float)Math.cos(rotationalAngle), (float)Math.sin(rotationalAngle));
		rotationalAngle = Util.normaliseAngle(rotationalAngle);
		controlMode = getControlMode(localInput);
		if (playerShip == null){
			return;
		}
		Thrusters shipThrusterSet = playerShip.getThrusterList();
		switch(controlMode){
			case ACCELLERATE:
				targetVelocity.set(targetDirection);
				targetVelocity.scale(maxManualSpeed);
				shipThrusterSet.setThrusterControls(targetVelocity, rotationalAngle,false);
				break;
			case BREAK:
				targetVelocity.set(playerShip.getVelocity());
				targetVelocity.addScaled(targetDirection, -maxAutoSpeed/2.0f);
				shipThrusterSet.setThrusterControls(targetVelocity, rotationalAngle,true);
				break;
			case DRIFT:
				targetVelocity.set(playerShip.getVelocity());
				shipThrusterSet.setThrusterControls(targetVelocity, rotationalAngle,false);
				break;
			case AUTO_ACCELERATE:
				targetVelocity.set(targetDirection);
				targetVelocity.scale(maxAutoSpeed);
				targetDirection.set(targetVelocity);
				targetDirection.scale(1.5f);
				targetDirection.sub(playerShip.getVelocity());
				if (targetDirection.vectorLength() < maxAutoSpeed/2.0f){
					shipThrusterSet.setThrusterControls(targetVelocity, rotationalAngle,true);
				} else {
					shipThrusterSet.setThrusterControls(targetVelocity, (float)Math.atan2(targetDirection.yPoint, targetDirection.xPoint), false);
				}
				break;
			case AUTO_BREAK:
				targetVelocity.set(targetDirection);
				targetVelocity.scale(-maxAutoSpeed/2.0f);
				shipThrusterSet.setThrusterControls(targetVelocity, rotationalAngle,true);
				break;
			case AUTO_DRIFT:
				targetVelocity.set(0,0);
				shipThrusterSet.setThrusterControls(targetVelocity, rotationalAngle,true);
				break;
		}
		
		if (localInput.isKeyDown(Input.KEY_LCONTROL)){
			playerShip.setIsFiring(WeaponTypes.PRIMARY, true);
		} else {
			playerShip.setIsFiring(WeaponTypes.PRIMARY, false);
		}
		if (localInput.isKeyDown(Input.KEY_SPACE)){
			playerShip.setIsFiring(WeaponTypes.SECONDARY, true);
		} else {
			playerShip.setIsFiring(WeaponTypes.SECONDARY, false);
		}
	}
	
	public float getRotationalTargetAngle(){
		return rotationalAngle;
	}
	
	public void setShip(Ship ship){
		this.playerShip = ship;
	}
	
	public void draw(Graphics g){
		targetDirection.set((float)Math.cos(rotationalAngle), (float)Math.sin(rotationalAngle));
		targetDirection.scale(100.0f);
		g.drawImage(crosshairImage, targetDirection.xPoint - 16, targetDirection.yPoint - 16);
	}

	
}
