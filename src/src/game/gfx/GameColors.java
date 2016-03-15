package src.game.gfx;

public class GameColors {

	public static int get(int color1, int color2, int color3, int color4) {
		return ((get(color4) << 24) + (get(color3) << 16) + (get(color2) << 8) + get(color1));
	}
	private static int get(int color) {
		if(color < 0) return 255;
		int r = color / 100 % 10;
		int g = color / 10 % 10;
		int b = color % 10;
		
		return r*36 + g*6 + b;
	}
}
