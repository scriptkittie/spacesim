package background;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class TileLayer extends LayerSuper {
	
	private static final int RENDER_CALLS = 10;

	private Map<Integer, Tile> tileCache;
	private int lastRenderUpdate;
	
	public TileLayer(float scrollSize, GameContainer gc){
		super(scrollSize, gc);
		tileCache = new HashMap<Integer, Tile>();
		lastRenderUpdate = RENDER_CALLS;
	}
	
	private void update(float xPointx, float yPoint, boolean cachedCells){
		Iterator<Tile> iterator = tileCache.values().iterator();
		while(iterator.hasNext())
		{
			Tile cell = iterator.next();
			if (!cell.memoryCache(xPointx, yPoint, width, height)){
				cell.freeCellResources();
				iterator.remove();
				if (tileCache.containsKey(cell.hashCode())){
					throw new IllegalStateException();
				}
			}
		}
		int renderCallsPerFrame = 2;
		for(int cellXPoint = (int)(xPointx/Tile.TILE_CELL_SIZE)-2; cellXPoint <= (xPointx + width + 2)/Tile.TILE_CELL_SIZE; cellXPoint++){
			for(int cellYPoint = (int)(yPoint/Tile.TILE_CELL_SIZE)-2; cellYPoint <= (yPoint + height + 2)/Tile.TILE_CELL_SIZE; cellYPoint++){
				int hashCode = Tile.hashCode(cellXPoint, cellYPoint);
				if (!tileCache.containsKey(hashCode)){
					Tile tempCell = createTileCell(cellXPoint,cellYPoint);
					tileCache.put(hashCode, tempCell);
					if (!tempCell.memoryCache(xPointx, yPoint, width, height)){
						throw new IllegalStateException();
					}
					renderCallsPerFrame--;
					if (renderCallsPerFrame == 0 && !cachedCells){
						return;
					}
				}
			}
		}
	}
	
	public void draw(Graphics g, float xPoint, float yPoint){
		xPoint*=scrollScale;
		yPoint*=scrollScale;
		if (lastRenderUpdate == RENDER_CALLS){
			update(xPoint,yPoint, tileCache.size() < 1);
			lastRenderUpdate = 0;
		} 
		else 
		{
			lastRenderUpdate++;
		}
		
		xPoint += width/2;
		yPoint += height/2;
		g.translate(-xPoint, -yPoint);
		for(Tile tileCell : tileCache.values()){
			tileCell.draw(g);
		}
		g.translate(xPoint, yPoint);

	}
	
	public abstract Tile createTileCell(int xPoint, int yPoint);
}
