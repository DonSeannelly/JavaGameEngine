package src.game.entities;

import src.game.gfx.Screen;
import src.game.world.Level;

public abstract class Entity {

	public int x, y;
	protected Level level;
	
	public Entity(Level l) {
		init(l);
	}
	public final void init(Level l) {
		level = l;
	}
	public abstract void tick();
	public abstract void render(Screen screen);
}
