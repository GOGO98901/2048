package me.roryclaasen.game.graphics;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.roryclaasen.game.GameCanvas;
import me.roryclaasen.game.components.anim.Animation;
import me.roryclaasen.game.events.ButtonEvent;
import me.roryclaasen.game.events.ButtonEventListener;
import me.roryclaasen.game.events.DropboxEvent;
import me.roryclaasen.game.events.DropboxEventListener;
import me.roryclaasen.game.events.GraphicsElementEvent;
import me.roryclaasen.game.handler.GameHandler;
import me.roryclaasen.game.logic.Grid;
import me.roryclaasen.game.logic.Tile;
import me.roryclaasen.game.logic.TileAttributes;
import me.roryclaasen.game.resource.ResourceManager;
import me.roryclaasen.language.LangUtil;
import me.roryclaasen.util.Log;

public class Panel {
	private GameCanvas canvas;
	private List<Animation> anims;
	private Map<String, GraphicsElement> graphics;
	private Grid grid;

	private int gridWidth, gridHeight, buttonWidth;
	private int xOffset, yOffset;

	private boolean started, animating = false, allowMove = true;

	public Panel(GameCanvas canvas) {
		this.canvas = canvas;
		this.anims = new ArrayList<Animation>();
		this.graphics = new HashMap<String, GraphicsElement>();
		this.grid = new Grid(this);
		this.grid.newGridBlank();

		this.updateGridVars();
		this.initButtons();
	}

	private void initButtons() {
		buttonWidth = canvas.getWidth() / 6;
		int buttonHeight = 40;
		int buttonY = canvas.getHeight() - 61;

		Button play = new Button(xOffset, buttonY, buttonWidth, buttonHeight).setText(LangUtil.get("game.menu.play"));
		play.addListener(new ButtonEventListener() {

			@Override
			public void hover(GraphicsElementEvent evt) {
			}

			@Override
			public void buttonClick(ButtonEvent evt) {
				Log.info("Game starting for the first time");
				grid.newGrid();
				graphics.get("play").setVisible(false);
				graphics.get("restart").setVisible(true);
				started = true;
			}

			@Override
			public void buttonPress(ButtonEvent evt) {
			}
		});

		Button restart = new Button(play.getBounds()).setText(LangUtil.get("game.menu.restart"));
		restart.setVisible(false);
		restart.addListener(new ButtonEventListener() {

			@Override
			public void hover(GraphicsElementEvent evt) {
			}

			@Override
			public void buttonPress(ButtonEvent evt) {
			}

			@Override
			public void buttonClick(ButtonEvent evt) {
				Log.info("Game restating");
				graphics.get("play").setVisible(true);
				graphics.get("restart").setVisible(false);
				started = true;
			}
		});

		Button exit = new Button(xOffset + gridWidth - buttonWidth, buttonY, buttonWidth, buttonHeight).setText(LangUtil.get("game.menu.exit"));
		exit.addListener(new ButtonEventListener() {
			@Override
			public void hover(GraphicsElementEvent evt) {
			}

			@Override
			public void buttonPress(ButtonEvent evt) {
			}

			@Override
			public void buttonClick(ButtonEvent evt) {
				Log.info("Game in shutdown");
				canvas.getThread().stop();
			}
		});

		Dropbox mode = new Dropbox(20, 20, buttonWidth, buttonHeight);
		mode.addItem("4 x 4");
		mode.addItem("5 x 5");
		mode.addItem("6 x 6");
		mode.addItem("10 x 10");
		mode.addListener(new DropboxEventListener() {

			@Override
			public void hover(GraphicsElementEvent evt) {
			}

			@Override
			public void dropboxSelect(DropboxEvent evt) {
				int w, h;
				String[] wh = evt.getCurrentItem().toLowerCase().replaceAll(" ", "").split("x");
				w = Integer.parseInt(wh[0]);
				h = Integer.parseInt(wh[1]);
				grid.setSizeForStart(w, h);
				grid.newGridBlank();
				started = false;
				graphics.get("play").setVisible(true);
				graphics.get("restart").setVisible(false);
			}

			@Override
			public void dropboxOpen(DropboxEvent evt) {
			}
		});

		graphics.put("play", play);
		graphics.put("restart", restart);
		graphics.put("exit", exit);

		graphics.put("mode", mode);
	}

	private void updateGridVars() {
		gridWidth = ((Tile.SIZE + 5) * grid.getWidth()) - 5;
		gridHeight = ((Tile.SIZE + 5) * grid.getHeight()) - 5;
		xOffset = (canvas.getWidth() / 2) - (gridWidth / 2);
		yOffset = (canvas.getHeight() / 2) - (gridHeight / 2);
	}

	public void update() {
		updateGridVars();
		if (!animating && allowMove && started) {
			if (grid.canMove()) {
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
				Log.info("Game over");
				// TODO game over
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
						boolean remove = true;
						for (Animation animCheck : anims) {
							if (animCheck.equals(anim)) continue;
							if (animCheck.getX() == anim.getX() && animCheck.getY() == anim.getY()) {
								remove = false;
								animCheck.start();
							}
						}
						if (remove) itSkips.remove();
					}
				}
				itAnims.remove();
			}
		}
		if (anims.size() == 0) animating = false;

		{
			int buttonY = canvas.getHeight() - 61;
			int grids = ((Tile.SIZE + 4) * 4) - 5;
			int buttonX = (canvas.getWidth() / 2) - (grids  / 2);

			graphics.get("play").bounds.setLocation(buttonX, buttonY);
			graphics.get("restart").bounds.setLocation(buttonX, buttonY);
			graphics.get("exit").bounds.setLocation(buttonX + grids - buttonWidth, buttonY);
		}
		for (GraphicsElement element : graphics.values()) {
			element.update();
		}
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

		for (GraphicsElement element : graphics.values()) {
			element.render(g);
		}
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
