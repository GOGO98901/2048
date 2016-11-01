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

	private int tileCurve = Tile.SIZE / 8;
	private int panelCurve = tileCurve + 10;

	private int gridWidth;
	private int gridHeight;

	public Panel(GameCanvas canvas) {
		this.canvas = canvas;
		this.grid = new Grid();
		this.grid.newGrid();
	}

	public void update() {
		gridWidth = ((Tile.SIZE + 5) * grid.getWidth()) - 5;
		gridHeight = ((Tile.SIZE + 5) * grid.getHeight()) - 5;
	}

	public void render(Graphics g) {
		int xOffset = (canvas.getWidth() / 2) - (gridWidth / 2);
		int yOffset = (canvas.getHeight() / 2) - (gridHeight / 2);
		g.setColor(ResourceManager.colors.PANEL_BACKGROUND.get());
		g.fillRoundRect(xOffset - 10, yOffset - 10, gridWidth + 20, gridHeight + 20, panelCurve, panelCurve);
		for (int tX = 0; tX < grid.getWidth(); tX++) {
			for (int tY = 0; tY < grid.getHeight(); tY++) {
				Tile tile = grid.getTile(tX, tY);
				int rX = xOffset + ((Tile.SIZE + 5) * tX);
				int rY = yOffset + ((Tile.SIZE + 5) * tY);
				if (tile == null) {
					g.setColor(ResourceManager.colors.TILE_BLANK.get());
					g.fillRoundRect(rX, rY, Tile.SIZE, Tile.SIZE, tileCurve, tileCurve);
				} else {
					g.setColor(tile.getColor());
					g.fillRoundRect(rX, rY, Tile.SIZE, Tile.SIZE, tileCurve, tileCurve);
					g.setColor(ResourceManager.colors.TILE_TEXT.get());
					g.setFont(ResourceManager.roboto.deriveFont(32f));
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
