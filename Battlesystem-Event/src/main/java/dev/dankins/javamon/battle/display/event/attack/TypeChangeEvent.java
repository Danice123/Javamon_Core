package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.data.monster.MultiType;

public class TypeChangeEvent extends Event {

	public TypeChangeEvent(final String key, final MultiType type) {
		super(EventType.TypeChange);
		this.parameters.put("Key", key);
		this.parameters.put("Type", type);
	}

}
