package me.roryclaasen.game;

import me.roryclaasen.game.handler.GameHandler;
import me.roryclaasen.game.resource.ResourceManager;
import me.roryclaasen.util.Log;

public class GameThread implements Runnable {

	private Thread _thread;
	private boolean running;

	private GameCanvas canvas;
	private GameHandler handler;
	private GameDisplay display;

	private int fps, ups;

	public GameThread() {
		display = new GameDisplay();
		canvas = new GameCanvas(this);
		handler = new GameHandler(this);
	}

	public boolean init() {
		ResourceManager.init();
		display.init(canvas);
		canvas.init();
		return true;
	}

	public void start() {
		if (running) return;
		running = true;

		display.show();
		canvas.requestFocus();
		Log.info("Starting the game thread");
		_thread = new Thread(this, "2048 (host)");
		_thread.start();

	}

	public void stop() {
		if (running) {
			running = false;
			Log.info("Attempting to stop the thread");
			display.destroy();
			try {
				_thread.join(1000);
				_thread = null;
				Log.info("Stopped");
			} catch (InterruptedException e) {
				Log.warn("Unable to stop the thread");
				Log.stackWarn(e);
			}
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				handler.update();
				canvas.update();
				updates++;
				delta -= 1;
			}
			canvas.render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				ups = updates;
				fps = frames;
				timer += 1000L;
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	public boolean isRunning() {
		return running;
	}

	public GameCanvas getCanvas() {
		return canvas;
	}

	public GameDisplay getDisplay() {
		return display;
	}

	public int getFps() {
		return fps;
	}

	public int getUps() {
		return ups;
	}
}
