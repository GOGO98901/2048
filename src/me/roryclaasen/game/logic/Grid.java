package me.roryclaasen.game.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.roryclaasen.game.GamePanel;
import me.roryclaasen.game.components.anim.Animation;
import me.roryclaasen.game.components.anim.GrowAnimation;
import me.roryclaasen.game.components.anim.PulseAnimation;
import me.roryclaasen.util.Log;

public class Grid {
	public final int WIDTH = 4, HEIGHT = 4;

	private GamePanel panel;
	private Random random;
	private Map<Integer, Tile> tileMap;

	private int width, height;
	private int nextWidth, nextHeight;
	private int[] tiles;

	private List<int[]> skipRender;

	private int score;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public Grid(GamePanel panel) {
		this.panel = panel;
		this.random = new Random();
		this.tileMap = new HashMap<Integer, Tile>();
		this.skipRender = new ArrayList<int[]>();
		this.nextWidth = this.WIDTH;
		this.nextHeight = this.WIDTH;
	}

	public void newGrid() {
		newGrid(nextWidth, nextHeight);
	}

	public void newGridBlank() {
		newGridBlank(nextWidth, nextHeight);
	}

	public void newGridBlank(int width, int height) {
		this.width = this.nextWidth = width;
		this.height = this.nextHeight = height;
		this.tiles = null;
		this.tiles = new int[width * height];
		this.score = 0;
	}

	public void newGrid(int width, int height) {
		newGridBlank(width, height);
		newRandomTile(1);
		newRandomTile();
	}

	public void setSizeForStart(int width, int height) {
		nextWidth = width;
		nextHeight = height;
	}

	public void move(Direction direction) {
		switch (direction) {
		case UP: {
			for (int i = 0; i < height; i++) {
				boolean increment = false;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						increment = moveTile(x, y, 0, -1, increment);
					}
				}
			}
			break;
		}
		case DOWN: {
			for (int i = 0; i < height; i++) {
				boolean increment = false;
				for (int x = 0; x < width; x++) {
					for (int y = height; y >= 0; y--) {
						increment = moveTile(x, y, 0, 1, increment);
					}
				}
			}
			break;
		}
		case LEFT: {
			for (int i = 0; i < width; i++) {
				boolean increment = false;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						increment = moveTile(x, y, -1, 0, increment);
					}
				}
			}
			break;
		}
		case RIGHT: {
			for (int i = 0; i < width; i++) {
				boolean increment = false;
				for (int y = 0; y < height; y++) {
					for (int x = width; x >= 0; x--) {
						increment = moveTile(x, y, 1, 0, increment);
					}
				}
			}
			break;
		}
		}
	}

	private boolean moveTile(int x, int y, int xOffset, int yOffset, boolean increment) {
		Tile current = getTile(x, y);
		if (current != null) {
			Tile adj = getTile(x + xOffset, y + yOffset);
			if (adj == null) {
				setTile(x + xOffset, y + yOffset, current.getStage() + 1);
				setTile(x, y, 0);
			} else if (!adj.equals(Tile.edge)) {
				if (current.equals(adj)) {
					if (increment) setTile(x + xOffset, y + yOffset, current.getStage() + 1);
					else {
						setTile(x + xOffset, y + yOffset, current.getStage() + 2);
						score += getTile(x + xOffset, y + yOffset).getNumber();
					}
					newAnim(PulseAnimation.class, x + xOffset, y + yOffset);
					increment = true;
					setTile(x, y, 0);
				}
			}
		}
		return increment;
	}

	private void newAnim(Class<? extends Animation> animClass, int x, int y) {
		boolean wait = false;
		for (int[] pos : skipRender) {
			if (pos[0] == x && pos[1] == y) {
				wait = true;
				break;
			}
		}
		if (!wait) skipRender.add(new int[] { x, y });
		Animation anim = null;
		if (animClass.getCanonicalName().equals(PulseAnimation.class.getCanonicalName())) {
			anim = new PulseAnimation(panel, getTile(x, y), x, y);
		} else if (animClass.getCanonicalName().equals(GrowAnimation.class.getCanonicalName())) {
			anim = new GrowAnimation(panel, getTile(x, y), x, y);
		} else {
			Log.warn("Unknow animation type");
			return;
		}
		if (wait) anim.pause();
		panel.addAnim(anim);
	}

	public void newRandomTile(int id) {
		if (!isFull()) {
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
	}

	public void newRandomTile() {
		if (getHightestStage() >= 4) newRandomTile(random.nextInt(2) + 1);
		else newRandomTile(1);
	}

	public void setTile(int x, int y, int id) {
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		tiles[x + y * width] = id;
	}

	public Tile getTile(int x, int y) {
		int id = getTileId(x, y);
		if (id == -1) return Tile.edge;
		if (id <= 0) return null;
		if (!tileMap.containsKey(id)) tileMap.put(id, new Tile(id - 1));
		return tileMap.get(id);
	}

	private int getTileId(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return -1;
		return tiles[x + y * width];
	}
	
	public int getScore(){
		return score;
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

	public boolean canMove() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int current = tiles[x + y * width];
				int up = getTileId(x, y - 1);
				int down = getTileId(x, y + 1);
				int left = getTileId(x - 1, y);
				int right = getTileId(x + 1, y);
				if ((current == 0) || (current == 0) || (current == 0) || (current == 0) || (current == up) || (current == down) || (current == left) || (current == right)) return true;
			}
		}
		return false;
	}

	public boolean isFull() {
		boolean check = true;
		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] == 0) check = false;
		}
		return check;
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
