package me.roryclaasen.game.components;

import java.awt.Color;

public class Tile {
	private int stage;

	public Tile(int stage) {
		this.stage = stage;
	}

	public int getNumber() {
		return 2 ^ stage;
	}

	public Color getColor() {
		return Color.BLUE;// TODO Get correct color
	}
}
