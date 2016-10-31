package me.roryclaasen.game.components;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.roryclaasen.game.GameCanvas;
import me.roryclaasen.game.resource.ResourceManager;

public class Panel {
	private GameCanvas canvas;
	private Grid grid;

	public Panel(GameCanvas canvas) {
		this.canvas = canvas;
		this.grid = new Grid();
		this.grid.newGrid();
	}

	public void update() {

	}

	public void render(Graphics g) {
		int gridWidth = ((Tile.SIZE + 5) * grid.getWidth()) - 5;
		int gridHeight = ((Tile.SIZE + 5) * grid.getHeight()) - 5;
		int xOffset = (canvas.getWidth() / 2) - (gridWidth / 2);
		int yOffset = (canvas.getHeight() / 2) - (gridHeight / 2);
		for (int tX = 0; tX < grid.getWidth(); tX++) {
			for (int tY = 0; tY < grid.getHeight(); tY++) {
				Tile tile = grid.getTile(tX, tY);
				int rX = xOffset + ((Tile.SIZE + 5) * tX);
				int rY = yOffset + ((Tile.SIZE + 5) * tY);
				if (tile == null) {
					g.setColor(ResourceManager.Colors.TILE_BLANK.get());
					g.fillRoundRect(rX, rY, Tile.SIZE, Tile.SIZE, Tile.SIZE / 8, Tile.SIZE / 8);
				} else {
					g.setColor(tile.getColor());
					g.fillRoundRect(rX, rY, Tile.SIZE, Tile.SIZE, Tile.SIZE / 8, Tile.SIZE / 8);
					g.setColor(ResourceManager.Colors.TILE_TEXT.get());
					drawCenteredString(g, "" + tile.getNumber(), rX, rY, g.getFont());
				}
			}
		}
	}

	public void drawCenteredString(Graphics g, String text, int cX, int cY, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		Rectangle rect = new Rectangle(cX, cY, Tile.SIZE, Tile.SIZE);
		int x = (rect.width - metrics.stringWidth(text)) / 2;
		int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(font);
		g.drawString(text, rect.x + x, rect.y + y);
	}
}
