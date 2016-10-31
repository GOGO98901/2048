package me.roryclaasen.game.components;

import java.awt.Graphics;

public class Panel {

	private int tileSize = 80;
	private Grid grid;

	public Panel() {
		grid = new Grid();
		grid.newGrid();
	}

	public void update() {
		
	}

	public void render(Graphics g) {
		if (grid.hasGrid()) {
			for (int tX = 0; tX < grid.getWidth(); tX++) {
				for (int tY = 0; tY < grid.getWidth(); tY++) {
					Tile tile = grid.getTile(tX, tY);
					if (tile == null) continue;
					// TODO render the tile
				}
			}
		}
	}
}
