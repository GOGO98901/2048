package me.roryclaasen.game.handler;

import me.roryclaasen.game.GameThread;

public class GameHandler {

	private static Keyboard keybaord;

	public GameHandler(GameThread thread) {
		keybaord = new Keyboard(thread.getCanvas());
	}

	public void update() {
		keybaord.update();
	}

	public static Keyboard keys() {
		return keybaord;
	}
}
