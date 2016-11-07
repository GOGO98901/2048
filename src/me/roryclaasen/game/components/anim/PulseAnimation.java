package me.roryclaasen.game.components.anim;

import java.awt.Graphics;

import me.roryclaasen.game.components.Panel;
import me.roryclaasen.game.components.Tile;
import me.roryclaasen.game.components.TileAttributes;

public class PulseAnimation extends Animation {

	private int time, timeMax, tileSize;

	private TileAttributes attribs;

	public PulseAnimation(Panel panel, Tile tile, int x, int y) {
		super(panel, tile, x, y);
		attribs = TileAttributes.DEFAULT.clone();
		time = 0;
		timeMax = 20;
		tileSize = Tile.SIZE;
	}

	@Override
	public void update() {
		if (isRunning()) {
			time++;
			tileSize +=  Math.sin(timeMax / time) + Math.cos(timeMax / time);
			int offset = -(Tile.SIZE - tileSize) / 2;
			attribs.setxOffset(offset);
			attribs.setyOffset(offset);
			attribs.setSize(tileSize);
			if (time > timeMax) {
				remove();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if (isRunning()) {
			panel.drawTile(g, tile, x, y, attribs);
		}
	}
}
