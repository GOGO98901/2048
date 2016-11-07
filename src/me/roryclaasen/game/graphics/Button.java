package me.roryclaasen.game.graphics;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Button extends GraphicsElement {
	public Button(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public Button(Rectangle bounds) {
		super(bounds);
	}

	@Override
	public void render(Graphics g) {}

	@Override
	public void update() {}
}
