package me.roryclaasen.game.components;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.roryclaasen.game.GameCanvas;
import me.roryclaasen.game.handler.GameHandler;
import me.roryclaasen.game.resource.ResourceManager;

public class Panel {
	private GameCanvas canvas;
	private Grid grid;

	private int tileCurve = Tile.SIZE / 8;
	private int panelCurve = tileCurve + 10;

	private int gridWidth;
	private int gridHeight;

	private int pulseTime = 0;

	private boolean animating = false, moving = false, pulse = false;

	public Panel(GameCanvas canvas) {
		this.canvas = canvas;
		this.grid = new Grid();
		this.grid.newGrid();
	}

	public void update() {
		gridWidth = ((Tile.SIZE + 5) * grid.getWidth()) - 5;
		gridHeight = ((Tile.SIZE + 5) * grid.getHeight()) - 5;

		if (moving) animating = true;
		if (!animating) {
			if (GameHandler.keys().up ^ GameHandler.keys().down) {
				moving = animating = true;
				if (GameHandler.keys().up) {
					grid.move(Grid.Direction.UP);
					grid.newRandomTile();
				}
				if (GameHandler.keys().down) {
					grid.move(Grid.Direction.DOWN);
					grid.newRandomTile();
				}
			}
			if (GameHandler.keys().left ^ GameHandler.keys().right) {
				moving = animating = true;
				if (GameHandler.keys().left) {
					grid.move(Grid.Direction.LEFT);
					grid.newRandomTile();
				}
				if (GameHandler.keys().right) {
					grid.move(Grid.Direction.RIGHT);
					grid.newRandomTile();
				}
			}
		} else {
			if (!(GameHandler.keys().up || GameHandler.keys().down || GameHandler.keys().left || GameHandler.keys().right)) moving = false;
		}

		if (grid.getTilesToPulse().size() == 0) {
			if (!moving) animating = false;
		} else pulse = true;

		if (pulse) {
			pulseTime++;
			if (pulseTime > 15) {
				pulseTime = 0;
				pulse = false;
				grid.getTilesToPulse().clear();
			}
		}
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
				int tS = Tile.SIZE;
				if (tile == null) {
					g.setColor(ResourceManager.colors.TILE_BLANK.get());
					g.fillRoundRect(rX, rY, tS, tS, tileCurve, tileCurve);
				} else {
					g.setColor(tile.getColor());
					g.fillRoundRect(rX, rY, tS, tS, tileCurve, tileCurve);
					g.setColor(ResourceManager.colors.TILE_TEXT.get());
					g.setFont(ResourceManager.roboto.deriveFont(32f));
					drawCenteredString(g, "" + tile.getNumber(), rX, rY, g.getFont());
				}
			}
		}
		for (int[] tilePulse : grid.getTilesToPulse()) {
			int tX = tilePulse[0];
			int tY = tilePulse[1];
			Tile tile = grid.getTile(tX, tY);
			if (tile != null) {
				int offset = (int) (Math.sin(pulseTime / 2) * 10) / 2;
				int rX = xOffset + ((Tile.SIZE + 5) * tX) - (offset / 2);
				int rY = yOffset + ((Tile.SIZE + 5) * tY) - (offset / 2);
				int tS = Tile.SIZE + offset;
				g.setColor(tile.getColor());
				g.fillRoundRect(rX, rY, tS, tS, tileCurve, tileCurve);
				g.setColor(ResourceManager.colors.TILE_TEXT.get());
				g.setFont(ResourceManager.roboto.deriveFont(32f));
				drawCenteredString(g, "" + tile.getNumber(), rX, rY, g.getFont());
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
