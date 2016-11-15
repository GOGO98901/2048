package me.roryclaasen.game.events;

public interface DropboxEventListener extends GraphicsElementEventListener {

	public void dropboxOpen(DropboxEvent evt);
	public void dropboxSelect(DropboxEvent evt);
}
