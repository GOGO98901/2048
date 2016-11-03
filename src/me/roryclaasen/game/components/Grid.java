package me.roryclaasen.game.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.roryclaasen.util.Log;

public class Grid {
	public final int WIDTH = 4, HEIGHT = 4;

	private Random random;
	private Map<Integer, Tile> tileMap;

	private int width, height;
	private int[] tiles;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public Grid() {
		random = new Random();
		tileMap = new HashMap<Integer, Tile>();
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
		newRandomTile(1);

	}

	public void move(Direction direction) {
		if (direction == Direction.UP) {
			for (int i = 0; i < height; i++) {
				boolean inc = false;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						Tile current = getTile(x, y);
						if (current != null) {
							Tile adj = getTile(x, y - 1);
							if (adj == null) {
								setTile(x, y - 1, current.getStage() + 1);
								setTile(x, y, 0);
							} else if (!adj.equals(Tile.edge)) {
								if (current.equals(adj) && !inc) {
									inc = true;
									setTile(x, y - 1, current.getStage() + 2);
									setTile(x, y, 0);
								}
							}
						}
					}
				}
			}
		}
		if (direction == Direction.DOWN) {
			for (int i = 0; i < height; i++) {
				boolean inc = false;
				for (int x = 0; x < width; x++) {
					for (int y = height; y >= 0; y--) {
						Tile current = getTile(x, y);
						if (current != null) {
							Tile adj = getTile(x, y + 1);
							if (adj == null) {
								setTile(x, y + 1, current.getStage() + 1);
								setTile(x, y, 0);
							} else if (!adj.equals(Tile.edge)) {
								if (current.equals(adj) && !inc) {
									inc = true;
									setTile(x, y + 1, current.getStage() + 2);
									setTile(x, y, 0);
								}
							}
						}
					}
				}
			}
		}
		if (direction == Direction.LEFT) {
			for (int i = 0; i < width; i++) {
				boolean inc = false;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Tile current = getTile(x, y);
						if (current != null) {
							Tile adj = getTile(x - 1, y);
							if (adj == null) {
								setTile(x - 1, y, current.getStage() + 1);
								setTile(x, y, 0);
							} else if (!adj.equals(Tile.edge)) {
								if (current.equals(adj) && !inc) {
									inc = true;
									setTile(x - 1, y, current.getStage() + 2);
									setTile(x, y, 0);
								}
							}
						}
					}
				}
			}
		}
		if (direction == Direction.RIGHT) {
			for (int i = 0; i < width; i++) {
				boolean inc = false;
				for (int y = 0; y < height; y++) {
					for (int x = width; x >= 0; x--) {
						Tile current = getTile(x, y);
						if (current != null) {
							Tile adj = getTile(x + 1, y);
							if (adj == null) {
								setTile(x + 1, y, current.getStage() + 1);
								setTile(x, y, 0);
							} else if (!adj.equals(Tile.edge)) {
								if (current.equals(adj) && !inc) {
									inc = true;
									setTile(x + 1, y, current.getStage() + 2);
									setTile(x, y, 0);
								}
							}
						}
					}
				}
			}
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
		Log.info("Creating new Tile at x=" + x + ", y=" + y + ", id=" + id);
		tiles[x + y * width] = id;
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
}
