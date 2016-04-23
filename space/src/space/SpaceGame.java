package space;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SpaceGame implements Game {
	
	private GameState gameState;
	private Level loadingLevel;
	private PlayerSub player;
	
	private enum GameState {
		SPLASH_SCREEN,
		LOADING_LEVEL,
		IN_GAME
	}
	
	public boolean closeRequested() {
		return true;
	}

	public String getTitle() {
		return "SPAAAAACEEEE";
	}

	public void init(GameContainer gc) throws SlickException {
		gc.setMinimumLogicUpdateInterval(1);
		gc.setMaximumLogicUpdateInterval(1);
		gameState = GameState.SPLASH_SCREEN;
		player = new PlayerSub("Goat");

	}
	

	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (gameState == GameState.SPLASH_SCREEN)
		{
			String loading = "Loading Game";
			int loadingWidth = gc.getDefaultFont().getWidth(loading);
			loadingWidth /= 2f;
			g.drawString(loading,gc.getWidth()/2 - loadingWidth, gc.getHeight()/2);
			gameState = GameState.LOADING_LEVEL;
		}
		else if (gameState == GameState.LOADING_LEVEL)
		{
			loadingLevel = new Level(player, gc);
			loadingLevel.load(gc);
			gameState = GameState.IN_GAME;
		} 
		else 
		{
			loadingLevel.render(gc, g);
		}
	}

	public void update(GameContainer gc, int timeintstep) throws SlickException {
		if (gameState == GameState.IN_GAME)
		{
			float secondStep = timeintstep/1000.0f;
			player.getShipControl().update(gc.getInput(), secondStep);
			loadingLevel.updateObjectInWorld(secondStep);
		}
	}
	
	public static void main(String[] args){
		try { 
		    AppGameContainer container = new AppGameContainer(new SpaceGame()); 
		    container.setDisplayMode(1920,1080,true); 
		    container.start(); 
		} catch (Exception e) { 
		    e.printStackTrace(); 
		}
	}

}
