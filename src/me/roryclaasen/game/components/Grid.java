package me.roryclaasen.game.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.roryclaasen.util.Log;

public class Grid {
	public final int WIDTH = 4, HEIGHT = 4;

	private Random random;
	private Map<Integer, Tile> tileMap;
	private List<int[]> pulse;

	private int width, height;
	private int[] tiles;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public Grid() {
		random = new Random();
		tileMap = new HashMap<Integer, Tile>();
		pulse = new ArrayList<int[]>();
	}

	public void newGrid() {
		newGrid(WIDTH, HEIGHT);
	}

	public void newGrid(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = null;
		this.tiles = new int[width * height];
		newRandomTile(1);
		newRandomTile();
	}

	public void move(Direction direction) {
		pulse.clear();
		if (direction == Direction.UP) {
			for (int i = 0; i < height; i++) {
				boolean inc = false;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						inc = moveTile(x, y, 0, -1, inc);
					}
				}
			}
		}
		if (direction == Direction.DOWN) {
			for (int i = 0; i < height; i++) {
				boolean inc = false;
				for (int x = 0; x < width; x++) {
					for (int y = height; y >= 0; y--) {
						inc = moveTile(x, y, 0, 1, inc);
					}
				}
			}
		}
		if (direction == Direction.LEFT) {
			for (int i = 0; i < width; i++) {
				boolean inc = false;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						inc = moveTile(x, y, -1, 0, inc);
					}
				}
			}
		}
		if (direction == Direction.RIGHT) {
			for (int i = 0; i < width; i++) {
				boolean inc = false;
				for (int y = 0; y < height; y++) {
					for (int x = width; x >= 0; x--) {
						inc = moveTile(x, y, 1, 0, inc);
					}
				}
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
						pulse.add(new int[] { x + xOffset, y+yOffset });
					}
					inc = true;
					setTile(x, y, 0);
				}
			}
		}
		return inc;
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
		Log.info("Creating new Tile at x=" + x + ", y=" + y + ", id=" + id);
		tiles[x + y * width] = id;
		pulse.add(new int[] { x, y });
	}

	public void newRandomTile() {
		if (getHightestStage() >= 4) newRandomTile(random.nextInt(3) + 1);
		else newRandomTile(random.nextInt(2) + 1);
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public List<int[]> getTilesToPulse() {
		return pulse;
	}
}
