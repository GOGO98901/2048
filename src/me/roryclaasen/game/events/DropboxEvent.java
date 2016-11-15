package me.roryclaasen.game.events;

import me.roryclaasen.game.graphics.Dropbox;

public class DropboxEvent extends GraphicsElementEvent {
	private static final long serialVersionUID = 1L;

	public DropboxEvent(Dropbox source) {
		super(source);
	}

	public final Dropbox getDropbox() {
		return (Dropbox) source;
	}

	public final int getCurrentItemIndex() {
		return getDropbox().getCurrentItemIndex();
	}

	public final String getCurrentItem() {
		return getDropbox().getCurrentItem();
	}
}
