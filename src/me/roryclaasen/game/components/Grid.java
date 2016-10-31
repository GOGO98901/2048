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
		newRandomTile();

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
		newRandomTile(random.nextInt(2) + 1);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return null;
		int id = tiles[x + y * width];
		if (id <= 0) return null;
		if (!tileMap.containsKey(id)) tileMap.put(id, new Tile(id));
		return tileMap.get(id);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
