package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.data.monster.MultiType;

public class TypeChangeEvent implements Event {

	public final MonsterHandler monster;
	public final MultiType type;

	public TypeChangeEvent(final MonsterHandler monster, final MultiType type) {
		this.monster = monster;
		this.type = type;
	}

	@Override
	public EventType getType() {
		return EventType.TypeChange;
	}

}
