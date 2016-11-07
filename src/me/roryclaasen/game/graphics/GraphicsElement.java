package me.roryclaasen.game.graphics;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GraphicsElement {

	protected Rectangle bounds;

	public GraphicsElement(int x, int y, int width, int height) {
		this.bounds = new Rectangle(x, y, width, height);
	}

	public GraphicsElement(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public abstract void render(Graphics g);

	public void update() {}
}
