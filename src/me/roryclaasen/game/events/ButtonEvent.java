package me.roryclaasen.game.events;

import me.roryclaasen.game.graphics.Button;

public class ButtonEvent extends GraphicsElementEvent {
	private static final long serialVersionUID = 1L;

	public ButtonEvent(Button source) {
		super(source);
	}

	public final Button getButton() {
		return (Button) source;
	}
}