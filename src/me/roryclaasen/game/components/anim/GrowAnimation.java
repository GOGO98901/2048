package me.roryclaasen.game.components.anim;

import me.roryclaasen.game.logic.Panel;
import me.roryclaasen.game.logic.grid.Tile;
import me.roryclaasen.game.logic.grid.TileAttributes;

public class GrowAnimation extends SimpleAnimation {

	private int tileSize;

	public GrowAnimation(Panel panel, Tile tile, int x, int y) {
		super(panel, tile, x, y);
		attribs = TileAttributes.DEFAULT.clone();
		tileSize = 0;
	}

	@Override
	public void update() {
		if (isRunning()) {
			tileSize += 7;
			int offset = -(Tile.SIZE - tileSize) / 2;
			attribs.setxOffset(offset);
			attribs.setyOffset(offset);
			attribs.setSize(tileSize);
			if (tileSize >= Tile.SIZE) {
				remove();
			}
		}
	}

	@Override
	public void onStop() {
		attribs.setSize(Tile.SIZE);
		attribs.setxOffset(0);
		attribs.setyOffset(0);
	}
}
