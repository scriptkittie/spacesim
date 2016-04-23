package space;

import objects.Ship;
import objects.ShipDirectionalController;

public class PlayerSub {

	private String name;
	private Ship ship;
	private ShipDirectionalController controller;
	
	public PlayerSub(String name){
		this.name = name;
		controller = new ShipDirectionalController();
	}
	
	public void setShip(Ship ship){
		this.ship = ship;
		controller.setShip(ship);
	}
	
	public ShipDirectionalController getShipControl(){
		return controller;
	}
	
	public Ship getShip(){
		return ship;
	}
	
	public String getName(){
		return name;
	}
	
}
