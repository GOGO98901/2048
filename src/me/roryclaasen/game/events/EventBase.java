package me.roryclaasen.game.events;

import java.util.EventObject;

public class EventBase extends EventObject {
	private static final long serialVersionUID = 1L;

	public EventBase(Object source) {
		super(source);
	}
}
