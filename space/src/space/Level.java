
package space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import objects.FactionAI;
import objects.Ship;
import objects.ShipGenerator;
import objects.Ship.Faction;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import planets.PLanetGenerator;
import planets.Planet;
import util.Vector;

public class Level {

	private List<RadarObject> navigationSystem;
	private World world;
	private PlayerSub player;
	private List<FactionAI> factionList;
	
	public Level(PlayerSub player, GameContainer gc){
		this.player = player;
		world = new World(gc);
		factionList = new ArrayList<FactionAI>();
		navigationSystem = new ArrayList<RadarObject>();
	}
	
	public void updateObjectInWorld(float intervalStep){
		for(FactionAI ai : factionList){
			ai.update(world);
		}
		world.update(intervalStep);
		
		if (!player.getShip().isAlive()){
			reset();
		}
	}
	
	public void load(GameContainer gc){
		ShipGenerator   shipGen = new ShipGenerator();
		world = new World(gc);
		generatePlanets();
		Ship ship = shipGen.generateShip("Ship1", Faction.PLAYER);
		world.addObjectToWorld(ship);
		player.setShip(ship);
		reset();
		world.setTargetObject(ship);
		DisplayRadar r = new DisplayRadar(gc);
		r.setRadar(ship.getRadar());
		player.setShip(ship);
		navigationSystem.add(r);
		addFactionShips();
	}
	
	private void addFactionShips(){
		ShipGenerator   shipGen = new ShipGenerator();
		Planet[] enemyPlanets = new Planet[]{(Planet)world.getObjectInWorld("Mercury"),
											(Planet)world.getObjectInWorld("Venus"),
											(Planet)world.getObjectInWorld("Mars"),
											(Planet)world.getObjectInWorld("Jupiter"),
											(Planet)world.getObjectInWorld("Io")};
		for(Planet planet : enemyPlanets){
			Ship factionShip = shipGen.generateShip("Ship2", Faction.ENEMY);
			FactionAI factionAI = new FactionAI(factionShip);
			factionShip.setPosition(planet.getPosition());
			factionShip.moveObject(100,100);
			factionList.add(factionAI);
			world.addObjectToWorld(factionShip);
		}
	}
	
	public void reset(){
		Vector startingPosition = new Vector(world.getObjectInWorld("Earth").getPosition());
		startingPosition.add(100,20);
		player.getShip().reset(startingPosition);
	}
	
	public void render(GameContainer gc, Graphics g){
		int width = gc.getWidth();
		int height = gc.getHeight();
		g.translate(width/2, height/2);
		g.scale(1.0f, -1.0f);
		world.render(gc, g);
		g.resetTransform();
		g.translate(width/2, height/2);
		g.scale(1.0f, -1.0f);
		player.getShipControl().draw(g);
		for(RadarObject hudObj : navigationSystem){
			g.resetTransform();
			hudObj.draw(g);
		}
	}
	
	
	private Vector getRandomPlanetPos(float radius, Random r){
		double angle = r.nextDouble()*Math.PI*2;
		return new Vector((float)Math.cos(angle)*radius, (float)Math.sin(angle)*radius);
	}

	
	public void generatePlanets(){
		PLanetGenerator planetGen = new PLanetGenerator();
		Random rand = new Random(5);
		world.addObjectToWorld(planetGen.generatePlanet("The Sun",  2048, "Sun",   0, new Vector(0.0f,0.0f)));
		world.addObjectToWorld(planetGen.generatePlanet("Mercury",  64, "Mercury", 0, getRandomPlanetPos(5000,rand)));
		world.addObjectToWorld(planetGen.generatePlanet("Venus",    128, "Venus",  0, getRandomPlanetPos(10000,rand)));
		world.addObjectToWorld(planetGen.generatePlanet("Earth",    128, "Earth",  1, new Vector(15000.0f,0.0f)));
		world.addObjectToWorld(planetGen.generatePlanet("The Moon", 32, "Moon",  0, new Vector(14900.0f,100.0f)));
		world.addObjectToWorld(planetGen.generatePlanet("Mars",     128, "Mars",  1, getRandomPlanetPos(20000,rand)));
		Vector jupiterPos = new Vector(40000,1000);
		world.addObjectToWorld(planetGen.generatePlanet("Jupiter",  512, "GasGiant",  1, jupiterPos));
		Vector moonPos = new Vector(jupiterPos);
		moonPos.add(100,20);
		world.addObjectToWorld(planetGen.generatePlanet("Io",  16, "Moon",  1, moonPos));
		moonPos.add(-300,-10);
		world.addObjectToWorld(planetGen.generatePlanet("Europa",  16, "Venus",  2, moonPos));
		moonPos.add(500,-10);
		world.addObjectToWorld(planetGen.generatePlanet("Ganymede",  32, "Mercury",  3, moonPos));
		moonPos.add(-300,10);
		world.addObjectToWorld(planetGen.generatePlanet("Callisto",  32, "Moon",  4, moonPos));
	}

	
}
