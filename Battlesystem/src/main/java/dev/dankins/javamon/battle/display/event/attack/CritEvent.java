package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;

public class CritEvent implements Event {

	@Override
	public EventType getType() {
		return EventType.CriticalHit;
	}

}
