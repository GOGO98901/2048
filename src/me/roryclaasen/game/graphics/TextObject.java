package me.roryclaasen.game.graphics;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.roryclaasen.game.resource.ResourceManager;

public class TextObject extends GraphicsElement {

	private String text;

	public TextObject(String text, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.text = text;
	}

	public TextObject(String text, Rectangle bounds) {
		super(bounds);
	}

	public TextObject setText(String text) {
		this.text = text;
		return this;
	}

	public String getText() {
		return text;
	}

	@Override
	protected void renderElement(Graphics g) {
		if (text != null) {
			if (text.length() > 0) {
				g.setFont(ResourceManager.roboto.deriveFont((float) bounds.height * 0.75f));
				FontMetrics metrics = g.getFontMetrics(g.getFont());
				int x = (bounds.width - metrics.stringWidth(text)) / 2;
				int y = ((bounds.height - metrics.getHeight()) / 2) + metrics.getAscent();
				g.setColor(ResourceManager.colors.TILE_TEXT.get());
				g.drawString(text, bounds.x + x, bounds.y + y);
			}
		}
	}
}
