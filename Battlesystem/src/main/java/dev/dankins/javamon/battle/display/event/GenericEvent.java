package dev.dankins.javamon.battle.display.event;

public class GenericEvent implements Event {

	private final EventType type;

	public GenericEvent(final EventType type) {
		this.type = type;
	}

	@Override
	public EventType getType() {
		return type;
	}

}
