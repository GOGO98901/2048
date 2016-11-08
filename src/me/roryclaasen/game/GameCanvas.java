package me.roryclaasen.game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import me.roryclaasen.game.graphics.Panel;
import me.roryclaasen.game.resource.ResourceManager;
import me.roryclaasen.util.Log;

public class GameCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	private final GameThread _gameThread;

	private int width = 800, height = 600;

	private Panel gamePanel;

	private boolean debug = false;

	public GameCanvas(final GameThread thread) {
		_gameThread = thread;
		this.setSize(width, height);
	}

	public void init() {
		gamePanel = new Panel(this);
	}

	public void update() {
		gamePanel.update();
	}

	public void render() {
		try {
			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				return;
			}
			Graphics g = bs.getDrawGraphics();

			if (debug) {
				g.setColor(ResourceManager.colors.BACKGROUND.get());
				g.fillRect(0, 0, width, height);
				g.setColor(ResourceManager.colors.DEBUG_BACK.get());
				g.fillRect(0, 0, 50, 25);
				g.setColor(ResourceManager.colors.DEBUG.get());
				g.drawString(_gameThread.getFps() + " fps", 2, 11);
				g.drawString(_gameThread.getUps() + " ups", 2, 22);
			}
			
			gamePanel.render(g);

			g.dispose();
			bs.show();
		} catch (Exception e) {
			Log.warn("Exception on render");
			Log.stackWarn(e);
		}
	}

	public GameThread getThread() {
		return _gameThread;
	}

}
