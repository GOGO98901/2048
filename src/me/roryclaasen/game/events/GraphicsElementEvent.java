package me.roryclaasen.game.events;

import me.roryclaasen.game.graphics.GraphicsElement;

public class GraphicsElementEvent extends EventBase {
	private static final long serialVersionUID = 1L;

	public GraphicsElementEvent(GraphicsElement source) {
		super(source);
	}

	public final GraphicsElement getElement() {
		return (GraphicsElement) source;
	}
}