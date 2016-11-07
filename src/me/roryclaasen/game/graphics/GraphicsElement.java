package me.roryclaasen.game.graphics;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.roryclaasen.game.events.GraphicsElementEvent;
import me.roryclaasen.game.events.GraphicsElementEventListener;

public abstract class GraphicsElement {

	private boolean visible = true;

	protected Rectangle bounds;

	protected List<GraphicsElementEventListener> listeners = new ArrayList<GraphicsElementEventListener>();;

	public GraphicsElement(int x, int y, int width, int height) {
		this.bounds = new Rectangle(x, y, width, height);
		init();
	}

	public GraphicsElement(Rectangle bounds) {
		this.bounds = bounds;
		init();
	}

	protected void init() {}

	public Rectangle getBounds() {
		return bounds;
	}

	public void callListenerHover() {
		Iterator<GraphicsElementEventListener> eventListener = listeners.iterator();
		while (eventListener.hasNext()) {
			eventListener.next().hover(new GraphicsElementEvent(this));
		}
	}

	public void addListener(GraphicsElementEventListener listener) {
		listeners.add(listener);
	}

	public void removeListener(GraphicsElementEventListener listener) {
		listeners.remove(listener);
	}

	public final void removeListener(int index) {
		if (index >= 0 && index < listeners.size()) {
			listeners.remove(index);
		}
	}

	public final void clearListeners() {
		listeners.clear();
	}

	public final void render(Graphics g) {
		if (visible) renderE(g);
	}

	public final void update() {
		if (visible) updateE();
	}

	protected abstract void renderE(Graphics g);

	protected void updateE() {}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
