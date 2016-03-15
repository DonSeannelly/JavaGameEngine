package src.game.world;

public class BasicSolidTile extends BasicTile {

	public BasicSolidTile(int id, int x, int y, int tileColor) {
		super(id, x, y, tileColor);
		this.solid = true;
	}

}
