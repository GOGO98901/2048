package me.roryclaasen.game.handler;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[65536];

	public boolean esc, w, s, a, d;
	public boolean arrowUp, arrowDown, arrowLeft, arrowRight;

	public boolean up, down, right, left;

	protected Keyboard(Canvas canvas) {
		canvas.addKeyListener(this);
	}

	protected void update() {
		esc = keys[KeyEvent.VK_ESCAPE];

		w = keys[KeyEvent.VK_W];
		s = keys[KeyEvent.VK_S];
		a = keys[KeyEvent.VK_A];
		d = keys[KeyEvent.VK_D];
		
		arrowUp = keys[KeyEvent.VK_UP];
		arrowDown = keys[KeyEvent.VK_DOWN];
		arrowLeft = keys[KeyEvent.VK_LEFT];
		arrowRight = keys[KeyEvent.VK_RIGHT];
		
		up = (w || arrowUp);
		down = (s || arrowDown);
		left = (a || arrowLeft);
		right = (d || arrowRight);
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	protected boolean setKey(int key, boolean state) {
		return keys[key] = state;
	}

	protected boolean[] getkeys() {
		return keys;
	}
}
