package me.roryclaasen.game.components.anim;

import me.roryclaasen.game.logic.Panel;
import me.roryclaasen.game.logic.grid.Tile;

public class PulseAnimation extends SimpleAnimation {

	private int time, timeMax, tileSize;

	public PulseAnimation(Panel panel, Tile tile, int x, int y) {
		super(panel, tile, x, y);
		time = 0;
		timeMax = 20;
		tileSize = Tile.SIZE;
	}

	@Override
	public void update() {
		if (isRunning()) {
			time++;
			tileSize += Math.sin(timeMax / time) + Math.cos(timeMax / time);
			int offset = -(Tile.SIZE - tileSize) / 2;
			attribs.setxOffset(offset);
			attribs.setyOffset(offset);
			attribs.setSize(tileSize);
			if (time > timeMax) {
				remove();
			}
		}
	}
}
