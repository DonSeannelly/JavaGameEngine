package src.game.main;

import src.game.entities.Player;
import src.game.gfx.GameColors;
import src.game.gfx.Screen;
import src.game.gfx.SpriteSheet;
import src.game.managers.GameInputProcessor;
import src.game.world.Level;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 160;
	private static final int HEIGHT = WIDTH/12*9;
	private static final int SCALE = 5;
	
	private boolean running;
	private JFrame frame;
	private int tickCount = 0;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int[] colors = new int[6*6*6];
	
	private Screen screen;
	private GameInputProcessor gip;
	
	private Level level;
	
	public Player player;
	
	public Game() {
		
		setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		frame = new JFrame("Minimal Game Engine");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);//Adds Game to the frame in the center
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	public synchronized void start() {
		running = true;
		run();
	}
	public synchronized void stop() {
		running = false;
	}
	@SuppressWarnings("unused")
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		init();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now-lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;//init @ false & remove try/catch to limit to 60 fps
			while(delta >= 1) {
				ticks++;
				tick();//updates game logic
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			
			if(shouldRender) {
				frames++;
				render();			
			}


			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				//System.out.println("FPS: " + frames + " " + "TPS: " + ticks);
				frames = 0;
				ticks = 0;
			}
		}
	}
	public void tick() {
		tickCount++;

		level.tick();
	}
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);//uses triple buffering
			return;
		}
		
		int xOffset = player.x - (screen.getWidth() / 2);
		int yOffset = player.y - (screen.getHeight() / 2);
		
		level.renderTiles(screen, xOffset, yOffset);
		
		for(int x = 0; x < level.getWidth(); x++) {
			int color = GameColors.get(-1,-1,-1,000);
			if(x%10==0 && x!= 0)
				color = GameColors.get(-1,-1,-1,500);
		}
		
		level.renderEntities(screen);

		for(int y = 0; y < screen.getHeight(); y++) {
			for(int x = 0; x < screen.getWidth(); x++) {
				int colorCode = screen.pixels[x+y*screen.getWidth()];
				if(colorCode < 255) pixels[x+y*WIDTH] = colors[colorCode];
			}
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}
	public void init() {
		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255/5);
					int gg = (g * 255/5);
					int bb = (b * 255/5);
					
					colors[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}
		
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/TestSheet.png"));
		gip = new GameInputProcessor(this);
		level = new Level(64, 64);
		player = new Player(level,0,0,gip);
		level.addEntity(player);
	}
	public static void main(String[] args) {
		new Game().start();
	}

}
