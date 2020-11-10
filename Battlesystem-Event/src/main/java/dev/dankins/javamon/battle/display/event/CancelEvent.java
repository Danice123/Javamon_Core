package dev.dankins.javamon.battle.display.event;

public class CancelEvent implements Event {

	@Override
	public EventType getType() {
		return EventType.Cancel;
	}

}
