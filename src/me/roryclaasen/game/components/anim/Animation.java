package me.roryclaasen.game.components.anim;

import java.awt.Graphics;

import me.roryclaasen.game.graphics.Panel;
import me.roryclaasen.game.logic.Tile;

public abstract class Animation {

	protected Panel panel;

	protected int x, y;
	protected Tile tile;

	private boolean running = false, remove = false;

	public Animation(Panel panel, Tile tile, int x, int y) {
		this.panel = panel;
		this.x = x;
		this.y = y;
		this.tile = tile;
	}

	public final void start() {
		running = true;
		onStart();
	}

	public final void stop() {
		running = false;
		onStop();
	}

	protected void onStart() {}

	protected void onStop() {}

	public final void remove() {
		stop();
		remove = true;
	}

	public final boolean isRemoved() {
		return remove;
	}

	public final boolean isRunning() {
		return running;
	}

	public abstract void update();

	public abstract void render(Graphics g);

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
