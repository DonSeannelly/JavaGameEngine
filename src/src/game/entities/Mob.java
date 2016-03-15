package src.game.entities;

import src.game.world.Level;
import src.game.world.Tile;

public abstract class Mob extends Entity {
//MOB = Monster or Beast
	protected String name;
	protected int speed;
	protected int numSteps = 0;
	protected boolean moving;
	protected int direction = 1;//0-up,1-down,2-left,3-right
	protected int scale = 1;
	
	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public void move(int xa, int ya) {
		if(xa != 0 && ya != 0) {
			move(xa,0);
			move(0,ya);
			numSteps--;
			return;
		}
		numSteps++;
		if(!hasCollided(xa,ya)) {
			if(ya < 0) direction = 0;
			if(ya > 0) direction = 1;
			if(xa < 0) direction = 2;
			if(xa > 0) direction = 3;
			x += xa * speed;
			y += ya * speed;
		}
	}
	
	public abstract boolean hasCollided(int xa, int ya);
	
	public String getName() {
		return name;
	}
	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		if(level == null) { return false; }
		
		Tile lastTile = level.getTile((this.x+x) >> 3, (this.y+y) >> 3);
		Tile newTile = level.getTile((this.x + x + xa) >> 3, (this.y + y + ya) >> 3);
		if(!lastTile.equals(newTile) && newTile.isSolid()) {
			return true;
		}
		return false;
	}
}
