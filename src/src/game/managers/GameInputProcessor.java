package src.game.managers;

import src.game.main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameInputProcessor implements KeyListener {
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	
	public class Key {
		private boolean pressed = false;
		private int numTimesPressed = 0;
		
		public int getNumTimesPressed() {
			return numTimesPressed;
		}
		
		public boolean isPressed() {
			return pressed;
		}
		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if(pressed) numTimesPressed++;
		}
	}
	public GameInputProcessor(Game game) {
		game.addKeyListener(this);
	}

     public void keyPressed(KeyEvent e) {
 		toggleKey(e.getKeyCode(), true);

     }

	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	public void toggleKey(int keyCode, boolean isPressed) {
		if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP){up.toggle(isPressed); }
		if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN){down.toggle(isPressed); }
		if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT){left.toggle(isPressed); }
		if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT){right.toggle(isPressed); }

	}

}
		