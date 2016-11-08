package me.roryclaasen.game.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.roryclaasen.game.components.anim.Animation;
import me.roryclaasen.game.components.anim.GrowAnimation;
import me.roryclaasen.game.components.anim.PulseAnimation;
import me.roryclaasen.game.graphics.Panel;
import me.roryclaasen.util.Log;

public class Grid {
	public final int WIDTH = 4, HEIGHT = 4;

	private Panel panel;
	private Random random;
	private Map<Integer, Tile> tileMap;

	private int width, height;
	private int[] tiles;

	private List<int[]> skipRender;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public Grid(Panel panel) {
		this.panel = panel;
		this.random = new Random();
		this.tileMap = new HashMap<Integer, Tile>();
		this.skipRender = new ArrayList<int[]>();
	}

	public void newGrid() {
		newGrid(WIDTH, HEIGHT);
	}

	public void newGridBlank() {
		newGridBlank(WIDTH, HEIGHT);
	}

	public void newGridBlank(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = null;
		this.tiles = new int[width * height];
	}

	public void newGrid(int width, int height) {
		newGridBlank(width, height);
		newRandomTile(1);
		newRandomTile();
	}

	public void move(Direction direction) {
		switch (direction) {
			case UP: {
				for (int i = 0; i < height; i++) {
					boolean inc = false;
					for (int x = 0; x < width; x++) {
						for (int y = 0; y < height; y++) {
							inc = moveTile(x, y, 0, -1, inc);
						}
					}
				}
				break;
			}
			case DOWN: {
				for (int i = 0; i < height; i++) {
					boolean inc = false;
					for (int x = 0; x < width; x++) {
						for (int y = height; y >= 0; y--) {
							inc = moveTile(x, y, 0, 1, inc);
						}
					}
				}
				break;
			}
			case LEFT: {
				for (int i = 0; i < width; i++) {
					boolean inc = false;
					for (int y = 0; y < height; y++) {
						for (int x = 0; x < width; x++) {
							inc = moveTile(x, y, -1, 0, inc);
						}
					}
				}
				break;
			}
			case RIGHT: {
				for (int i = 0; i < width; i++) {
					boolean inc = false;
					for (int y = 0; y < height; y++) {
						for (int x = width; x >= 0; x--) {
							inc = moveTile(x, y, 1, 0, inc);
						}
					}
				}
				break;
			}
		}
	}

	private boolean moveTile(int x, int y, int xOffset, int yOffset, boolean inc) {
		Tile current = getTile(x, y);
		if (current != null) {
			Tile adj = getTile(x + xOffset, y + yOffset);
			if (adj == null) {
				setTile(x + xOffset, y + yOffset, current.getStage() + 1);
				setTile(x, y, 0);
			} else if (!adj.equals(Tile.edge)) {
				if (current.equals(adj)) {
					if (inc) setTile(x + xOffset, y + yOffset, current.getStage() + 1);
					else {
						setTile(x + xOffset, y + yOffset, current.getStage() + 2);
					}
					newAnim(PulseAnimation.class, x + xOffset, y + yOffset);
					inc = true;
					setTile(x, y, 0);
				}
			}
		}
		return inc;
	}

	private void newAnim(Class<? extends Animation> anim, int x, int y) {
		skipRender.add(new int[] { x, y });
		if (anim.getCanonicalName().equals(PulseAnimation.class.getCanonicalName())) {
			panel.addAnim(new PulseAnimation(panel, getTile(x, y), x, y));
		} else if (anim.getCanonicalName().equals(GrowAnimation.class.getCanonicalName())) {
			panel.addAnim(new GrowAnimation(panel, getTile(x, y), x, y));
		} else {
			Log.warn("Unknow animation type");
		}
	}

	public void newRandomTile(int id) {
		int x = 0, y = 0;
		boolean placed = false;
		while (placed == false) {
			x = random.nextInt(width);
			y = random.nextInt(height);
			if (x > 0 || x < width || y > 0 || y < height) {
				if (tiles[x + y * width] == 0) placed = true;
			}
		}
		// Log.info("Creating new Tile at x=" + x + ", y=" + y + ", id=" + id);
		tiles[x + y * width] = id;
		newAnim(GrowAnimation.class, x, y);
	}

	public void newRandomTile() {
		if (getHightestStage() >= 32) newRandomTile(random.nextInt(3) + 1);
		else newRandomTile(1);
	}

	public void setTile(int x, int y, int id) {
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		tiles[x + y * width] = id;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return Tile.edge;
		int id = tiles[x + y * width];
		if (id <= 0) return null;
		if (!tileMap.containsKey(id)) tileMap.put(id, new Tile(id - 1));
		return tileMap.get(id);
	}

	public int getHightestStage() {
		int max = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Tile tile = getTile(x, y);
				if (tile != null) {
					int stage = tile.getStage();
					if (stage > max) max = stage;
				}
			}
		}
		return max;
	}

	public List<int[]> getSkipRender() {
		return skipRender;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
