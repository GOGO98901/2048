package me.roryclaasen.game.components;

import java.util.HashMap;
import java.util.Map;

public class Grid {
	public final int WIDTH = 4, HEIGHT = 4;
	private Map<Integer, Tile> tileMap;

	private int width, height;
	private int[] tiles;

	public Grid() {
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
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x > width || y < 0 || y > height) return null;
		int id = tiles[x + y * width];
		if (!tileMap.containsKey(id)) tileMap.put(id, new Tile(id));
		return tileMap.get(id);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean hasGrid() {
		return tiles != null;
	}
}
