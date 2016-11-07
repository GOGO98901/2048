package me.roryclaasen.game.handler;

import me.roryclaasen.game.GameThread;

public class GameHandler {

	private static Keyboard keybaord;
	private static Mouse mouse;

	public GameHandler(GameThread thread) {
		keybaord = new Keyboard(thread.getCanvas());
		mouse = new Mouse(thread.getCanvas());
	}

	public void update() {
		keybaord.update();
	}

	public static Keyboard keys() {
		return keybaord;
	}

	public static Mouse mouse() {
		return mouse;
	}
}
