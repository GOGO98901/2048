package me.roryclaasen.game.components;

import java.awt.Color;

import me.roryclaasen.game.resource.ResourceManager;

public class Tile {

	public static final Tile edge = new Tile(-1);

	public static int SIZE = 80;
	private int stage;

	public Tile(int stage) {
		this.stage = stage;
	}

	public int getNumber() {
		return (int) Math.pow(2, stage + 1);
	}

	public int getStage() {
		return stage;
	}

	public Color getColor() {
		switch (stage) {
		case 0:
			return ResourceManager.colors.TILE_ONE.get();
		case 1:
			return ResourceManager.colors.TILE_TWO.get();
		case 2:
			return ResourceManager.colors.TILE_THREE.get();
		case 3:
			return ResourceManager.colors.TILE_FOUR.get();
		case 4:
			return ResourceManager.colors.TILE_FIVE.get();
		case 5:
			return ResourceManager.colors.TILE_SIX.get();
		case 6:
			return ResourceManager.colors.TILE_SEVEN.get();
		case 7:
			return ResourceManager.colors.TILE_EIGHT.get();
		default:
			return ResourceManager.colors.TILE_BLANK.get();
		}
	}

}
