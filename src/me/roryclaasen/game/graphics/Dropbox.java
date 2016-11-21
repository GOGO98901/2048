package me.roryclaasen.game.graphics;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.roryclaasen.game.events.DropboxEvent;
import me.roryclaasen.game.events.DropboxEventListener;
import me.roryclaasen.game.events.GraphicsElementEventListener;
import me.roryclaasen.game.handler.GameHandler;
import me.roryclaasen.game.handler.Mouse;
import me.roryclaasen.game.resource.ResourceManager;

public class Dropbox extends GraphicsElement {

	private int border = 5;
	private int arcSize = 10;

	private int height;

	private List<String> items;
	private int current, hover;
	private boolean outside, expanded, select, selected;

	public Dropbox(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public Dropbox(Rectangle bounds) {
		super(bounds);
	}

	protected void init() {
		items = new ArrayList<String>();
	}

	@Override
	protected void renderElement(Graphics g) {
		g.setColor(ResourceManager.colors.PANEL_BACKGROUND.get());
		if (expanded) g.fillRoundRect(bounds.x - border, bounds.y - border, bounds.width + border * 2, bounds.height + height + (border * 3), arcSize, arcSize);
		else g.fillRoundRect(bounds.x - border, bounds.y - border, bounds.width + border * 2, bounds.height + height + (border * 2), arcSize, arcSize);
		g.setColor(ResourceManager.colors.TILE_BLANK.get());
		g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, (int) (arcSize * 0.9), (int) (arcSize * 0.9));
		g.fillRoundRect(bounds.x, bounds.y + bounds.height + border, bounds.width, height, (int) (arcSize * 0.9), (int) (arcSize * 0.9));
		drawString(g, bounds.x, bounds.y, getCurrentItem());
		if (expanded) {
			for (int i = 0; i < items.size(); i++) {
				if (i == hover) {
					g.setColor(ResourceManager.colors.TILE_ONE.get());
					g.fillRoundRect(bounds.x, bounds.y + border + ((i + 1) * bounds.height), bounds.width, bounds.height, (int) (arcSize * 0.9), (int) (arcSize * 0.9));
				}
				drawString(g, bounds.x, bounds.y + border + bounds.height * (i + 1), items.get(i));
			}
		}
	}

	@Override
	protected void updateElement() {
		if (bounds.contains(GameHandler.mouse().getPos()) && !outside && !expanded && !selected) {
			super.updateElement();
			if (GameHandler.mouse().getButton() == Mouse.BTN_LEFT) {
				callListenerOpen();
				select = false;
				expanded = true;
				hover = -1;
			}
		}
		Rectangle exBounds = (Rectangle) bounds.clone();
		exBounds.grow(0, height);
		if (expanded) {
			height = bounds.height * (items.size());
			if (GameHandler.mouse().getButton() == Mouse.BTN_RELEASED) select = true;
			if (select) {
				if (exBounds.contains(GameHandler.mouse().getPos())) {
					int y = GameHandler.mouse().getPos().y - bounds.y;
					hover = ((y / bounds.height) % bounds.height) - 1;
				} else hover = -1;
				if (GameHandler.mouse().getButton() == Mouse.BTN_LEFT) {
					if (hover >= 0) {
						current = hover;
						callListenerSelect();
						expanded = false;
						selected = true;
					}
				}
			}
		} else height = 0;

		if (GameHandler.mouse().getButton() == Mouse.BTN_LEFT) {
			if (!expanded) {
				if (!bounds.contains(GameHandler.mouse().getPos())) {
					outside = true;
					expanded = false;
				}
			} else {

			}
		} else {
			outside = false;
			selected = false;
		}
	}

	private void drawString(Graphics g, int x, int y, String text) {
		g.setFont(ResourceManager.roboto.deriveFont((float) bounds.height * 0.75f));
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int nX = (bounds.width - metrics.stringWidth(text)) / 2;
		int nY = ((bounds.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setColor(ResourceManager.colors.TILE_TEXT.get());
		g.drawString(text, x + nX, y + nY);
	}

	public void addItems(List<String> items) {
		items.addAll(items);
	}

	public void addItem(String item) {
		items.add(item);
	}

	public List<String> getItems() {
		return items;
	}

	public int getCurrentItemIndex() {
		return current;
	}

	public String getCurrentItem() {
		if (items.size() > 0) return items.get(current);
		return "";
	}

	public void callListenerOpen() {
		Iterator<GraphicsElementEventListener> eventListener = listeners.iterator();
		while (eventListener.hasNext()) {
			((DropboxEventListener) eventListener.next()).dropboxOpen(new DropboxEvent(this));
		}
	}

	public void callListenerSelect() {
		Iterator<GraphicsElementEventListener> eventListener = listeners.iterator();
		while (eventListener.hasNext()) {
			((DropboxEventListener) eventListener.next()).dropboxSelect(new DropboxEvent(this));
		}
	}

	@Deprecated
	public void addListener(GraphicsElementEventListener listener) {
		addListener((DropboxEventListener) listener);
	}

	@Deprecated
	public void removeListener(GraphicsElementEventListener listener) {
		removeListener((DropboxEventListener) listener);
	}

	public void addListener(DropboxEventListener listener) {
		listeners.add(listener);
	}

	public void removeListener(DropboxEventListener listener) {
		listeners.remove(listener);
	}
}
