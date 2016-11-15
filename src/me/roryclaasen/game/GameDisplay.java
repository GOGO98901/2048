package me.roryclaasen.game;

import java.awt.Dimension;

import javax.swing.JFrame;

import me.roryclaasen.language.LangUtil;
import me.roryclaasen.util.Log;

public class GameDisplay {
	public static final Dimension SIZE = new Dimension(800, 600);
	private JFrame frame;

	public void init(GameCanvas canvas) {
		frame = new JFrame();
		frame.add(canvas);
		// frame.setResizable(false);
		frame.pack();
		frame.setMinimumSize(SIZE);
		frame.setTitle(LangUtil.get("game.title"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}

	public void show() {
		Log.info("Setting display to be visible");
		frame.setVisible(true);
		frame.requestFocus();
		frame.requestFocusInWindow();
	}

	public void destroy() {
		frame.dispose();
		// frame.setVisible(false);
	}

	public int getWidth() {
		return frame.getWidth();
	}

	public int getHeight() {
		return frame.getHeight();
	}
}
