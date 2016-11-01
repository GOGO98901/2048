package me.roryclaasen.game.components;

import java.awt.Color;

public class Tile {

	public static int SIZE = 80;
	private int stage;

	public Tile(int stage) {
		this.stage = stage;
	}

	public int getNumber() {
		return (int) Math.pow(2, stage);
	}

	public Color getColor() {
		return Color.BLUE;// TODO Get correct color
	}
}
