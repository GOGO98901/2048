package me.roryclaasen.game.graphics;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.roryclaasen.game.GameCanvas;
import me.roryclaasen.game.components.anim.Animation;
import me.roryclaasen.game.events.ButtonEvent;
import me.roryclaasen.game.events.ButtonEventListener;
import me.roryclaasen.game.events.GraphicsElementEvent;
import me.roryclaasen.game.handler.GameHandler;
import me.roryclaasen.game.logic.Grid;
import me.roryclaasen.game.logic.Tile;
import me.roryclaasen.game.logic.TileAttributes;
import me.roryclaasen.game.resource.ResourceManager;
import me.roryclaasen.language.LangUtil;

public class Panel {
	private GameCanvas canvas;
	private List<Animation> anims;
	private Grid grid;

	private int gridWidth, gridHeight;
	private int xOffset, yOffset;

	private boolean animating = false, allowMove = true;

	private Button play;

	public Panel(GameCanvas canvas) {
		this.canvas = canvas;
		this.anims = new ArrayList<Animation>();
		this.grid = new Grid(this);
		this.grid.newGridBlank();
		play = new Button(canvas.getWidth() / 4, canvas.getHeight() - 100, canvas.getWidth() / 2, 75).setText(LangUtil.get("game.menu.play"));
		play.addListener(new ButtonEventListener() {

			@Override
			public void hover(GraphicsElementEvent evt) {}

			@Override
			public void buttonClick(ButtonEvent evt) {
				grid.newGrid();
				play.setVisible(false);
			}

			@Override
			public void buttonPress(ButtonEvent evt) {}
		});
	}

	public void update() {
		gridWidth = ((Tile.SIZE + 5) * grid.getWidth()) - 5;
		gridHeight = ((Tile.SIZE + 5) * grid.getHeight()) - 5;
		xOffset = (canvas.getWidth() / 2) - (gridWidth / 2);
		yOffset = (canvas.getHeight() / 2) - (gridHeight / 2);
		if (!animating && allowMove) {
			if (GameHandler.keys().up ^ GameHandler.keys().down) {
				animating = true;
				allowMove = false;
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
				animating = true;
				allowMove = false;
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
			if (!(GameHandler.keys().up || GameHandler.keys().down || GameHandler.keys().left || GameHandler.keys().right)) allowMove = true;
		}

		Iterator<Animation> itAnims = anims.listIterator();
		while (itAnims.hasNext()) {
			Animation anim = itAnims.next();
			if (grid.getTile(anim.getX(), anim.getY()) == null) anim.remove();
			else anim.update();

			if (anim.isRemoved()) {
				Iterator<int[]> itSkips = grid.getSkipRender().listIterator();
				while (itSkips.hasNext()) {
					int[] coord = itSkips.next();
					if (coord[0] == anim.getX() && coord[1] == anim.getY()) {
						itSkips.remove();
					}
				}
				itAnims.remove();
			}
		}
		if (anims.size() == 0) animating = false;
		play.update();
	}

	public void render(Graphics g) {
		g.setColor(ResourceManager.colors.PANEL_BACKGROUND.get());
		g.fillRoundRect(xOffset - 10, yOffset - 10, gridWidth + 20, gridHeight + 20, 25, 25);
		for (int tX = 0; tX < grid.getWidth(); tX++) {
			for (int tY = 0; tY < grid.getHeight(); tY++) {
				boolean skip = false;
				Iterator<int[]> itSkips = grid.getSkipRender().listIterator();
				while (itSkips.hasNext() && !skip) {
					int[] coord = itSkips.next();
					if (coord[0] == tX && coord[1] == tY) {
						skip = true;
					}
				}
				if (skip) continue;
				Tile tile = grid.getTile(tX, tY);
				int rX = xOffset + ((Tile.SIZE + 5) * tX);
				int rY = yOffset + ((Tile.SIZE + 5) * tY);
				if (tile == null) {
					g.setColor(ResourceManager.colors.TILE_BLANK.get());
					g.fillRoundRect(rX, rY, Tile.SIZE, Tile.SIZE, TileAttributes.DEFAULT.getTileCurve(), TileAttributes.DEFAULT.getTileCurve());
				} else {
					drawTile(g, tile, tX, tY);
				}
			}
		}
		Iterator<Animation> itAnims = anims.listIterator();
		while (itAnims.hasNext()) {
			itAnims.next().render(g);
		}
		play.render(g);
	}

	public void drawTile(Graphics g, Tile tile, int gX, int gY) {
		drawTile(g, tile, gX, gY, TileAttributes.DEFAULT);
	}

	public void drawTile(Graphics g, Tile tile, int gX, int gY, TileAttributes attribs) {
		int rX = xOffset + ((Tile.SIZE + 5) * gX) - attribs.getxOffset();
		int rY = yOffset + ((Tile.SIZE + 5) * gY) - attribs.getyOffset();
		g.setColor(tile.getColor());
		g.clipRect(rX, rY, attribs.getSize(), attribs.getSize());
		g.fillRoundRect(rX, rY, attribs.getSize(), attribs.getSize(), attribs.getTileCurve(), attribs.getTileCurve());
		g.setColor(ResourceManager.colors.TILE_TEXT.get());
		g.setFont(ResourceManager.roboto.deriveFont(32f));
		drawCenteredString(g, "" + tile.getNumber(), rX, rY, attribs);
		g.setClip(null);
	}

	private void drawCenteredString(Graphics g, String text, int cX, int cY, TileAttributes attribs) {
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		Rectangle rect = new Rectangle(cX, cY, attribs.getSize(), attribs.getSize());
		int x = (rect.width - metrics.stringWidth(text)) / 2;
		int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.drawString(text, rect.x + x, rect.y + y);
	}

	public void addAnim(Animation anim) {
		anims.add(anim);
		anim.start();
	}

	public Grid getGrid() {
		return grid;
	}
}
