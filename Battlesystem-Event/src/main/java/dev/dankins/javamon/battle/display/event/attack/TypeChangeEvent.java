package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.data.monster.MultiType;

public class TypeChangeEvent implements Event {

	public final String key;
	public final MultiType type;

	public TypeChangeEvent(final String key, final MultiType type) {
		this.key = key;
		this.type = type;
	}

	@Override
	public EventType getType() {
		return EventType.TypeChange;
	}

}
