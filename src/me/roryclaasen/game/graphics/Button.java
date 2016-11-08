package me.roryclaasen.game.graphics;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;

import me.roryclaasen.game.events.ButtonEvent;
import me.roryclaasen.game.events.ButtonEventListener;
import me.roryclaasen.game.events.GraphicsElementEventListener;
import me.roryclaasen.game.handler.GameHandler;
import me.roryclaasen.game.handler.Mouse;
import me.roryclaasen.game.resource.ResourceManager;

public class Button extends GraphicsElement {
	private int border = 5;
	private int arcSize = 10;

	private String text;
	protected boolean accessed = false, outside = false;

	public Button(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public Button(Rectangle bounds) {
		super(bounds);
	}

	public Button setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	protected void renderElement(Graphics g) {
		g.setColor(ResourceManager.colors.PANEL_BACKGROUND.get());
		g.fillRoundRect(bounds.x - border, bounds.y - border, bounds.width + border * 2, bounds.height + border * 2, arcSize, arcSize);
		g.setColor(ResourceManager.colors.TILE_BLANK.get());
		if (accessed) g.setColor(ResourceManager.colors.TILE_ONE.get());
		g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, (int) (arcSize * 0.9), (int) (arcSize * 0.9));

		g.setFont(ResourceManager.roboto.deriveFont((float) bounds.height * 0.75f));
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int x = (bounds.width - metrics.stringWidth(text)) / 2;
		int y = ((bounds.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setColor(ResourceManager.colors.TILE_TEXT.get());
		g.drawString(text, bounds.x + x, bounds.y + y);
	}

	@Override
	protected void updateElement() {
		if (bounds.contains(GameHandler.mouse().getPos()) && !outside) {
			super.updateElement();
			if (GameHandler.mouse().getButton() == Mouse.BTN_LEFT) {
				callListenerPress();
				accessed = true;
			}
			if (accessed && GameHandler.mouse().getButton() == Mouse.BTN_RELEASED) {
				callListenerClick();
				accessed = false;
			}
		}
		if (GameHandler.mouse().getButton() == Mouse.BTN_LEFT) {
			if (!bounds.contains(GameHandler.mouse().getPos())) outside = true;
		} else outside = false;
	}

	public void callListenerClick() {
		Iterator<GraphicsElementEventListener> eventListener = listeners.iterator();
		while (eventListener.hasNext()) {
			((ButtonEventListener) eventListener.next()).buttonClick(new ButtonEvent(this));
		}
	}

	public void callListenerPress() {
		Iterator<GraphicsElementEventListener> eventListener = listeners.iterator();
		while (eventListener.hasNext()) {
			((ButtonEventListener) eventListener.next()).buttonPress(new ButtonEvent(this));
		}
	}

	@Deprecated
	public void addListener(GraphicsElementEventListener listener) {
		addListener((ButtonEventListener) listener);
	}

	@Deprecated
	public void removeListener(GraphicsElementEventListener listener) {
		removeListener((ButtonEventListener) listener);
	}

	public void addListener(ButtonEventListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ButtonEventListener listener) {
		listeners.remove(listener);
	}
}
