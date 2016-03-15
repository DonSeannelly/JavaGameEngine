package src.game.world;

import src.game.entities.Entity;
import src.game.gfx.Screen;

import java.util.ArrayList;
import java.util.List;

public class Level {

	private byte[] tiles;
	private int width;
	private int height;
	
	public List<Entity> entities = new ArrayList<Entity>();
	
	public Level(int width, int height) {
		tiles = new byte[width * height];
		this.width = width;
		this.height = height;
		this.generateLevel();

	}
	public void generateLevel() {
		//Places the tiles into the tile array
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
					if(x * y % 10 < 7) {
						tiles[x + y * width] = Tile.GRASS.getId();
					} 
					else
						tiles[x + y * width] = Tile.STONE.getId();

				
			}
		}
	}
	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if(xOffset < 0) 
			xOffset = 0;
		if(xOffset > ((width << 3)-screen.getWidth()))
			xOffset = ((width << 3) - screen.getWidth());
		if(yOffset < 0) 
			yOffset = 0;
		if(yOffset > ((height << 3)-screen.getHeight())) 
			yOffset = ((height << 3) - screen.getHeight());
		
		screen.setOffset(xOffset,yOffset);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				getTile(x,y).render(screen, this,x<<3,y<<3);
			}
		}
	}
	public void renderEntities(Screen screen) {
		for(Entity e : entities) {
			e.render(screen);
		}
		
	}
	public void tick() {
		for(Entity e : entities) {
			e.tick();
		}
		
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public Tile getTile(int x, int y) {
		if(0 > x || x >= width || 0 > y || y >= height) 
			return Tile.VOID;
		return Tile.tiles[tiles[x+y*width]];
	}
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

}
