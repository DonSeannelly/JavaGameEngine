package src.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private int width;
	private int height;
	
	public int[] pixels;
	
	public SpriteSheet(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(image == null) {
			return;
		}
		
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = (pixels[i] & 0xff)/64;
		}
		for(int i = 0; i < 8; i++) {
			System.out.println(pixels[i]);
		}
	}

	public int getWidth() {
		return width;
	}
	public int[] getPixels() {
		return pixels;
	}
	public int getPixels(int i) {
		return pixels[i];
	}
	public void setPixels(int pixelIndex, int newIndex) {
		pixels[pixelIndex] = newIndex;
	}

}
