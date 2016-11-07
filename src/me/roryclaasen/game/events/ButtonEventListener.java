package me.roryclaasen.game.events;

public interface ButtonEventListener extends GraphicsElementEventListener {

	public void buttonClick(ButtonEvent evt);
	public void buttonPress(ButtonEvent evt);
}
