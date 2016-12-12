package me.roryclaasen.game.components.anim;

import java.awt.Graphics;

import me.roryclaasen.game.components.GamePanel;
import me.roryclaasen.game.logic.Tile;
import me.roryclaasen.game.logic.TileAttributes;

public abstract class SimpleAnimation extends Animation {

	protected TileAttributes attribs;

	public SimpleAnimation(GamePanel panel, Tile tile, int x, int y) {
		super(panel, tile, x, y);
		attribs = TileAttributes.DEFAULT.clone();
	}

	@Override
	public void render(Graphics g) {
		if (isRunning()) {
			panel.drawTile(g, tile, x, y, attribs);
		}
	}

	public TileAttributes getAttribs() {
		return attribs;
	}

	public void setAttribs(TileAttributes attribs) {
		this.attribs = attribs;
	}
}
