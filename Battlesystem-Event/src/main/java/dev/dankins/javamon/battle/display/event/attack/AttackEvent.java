package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.data.monster.attack.Attack;

public class AttackEvent extends Event {

	public AttackEvent(final String key, final Attack attack) {
		super(EventType.Attack);
		this.parameters.put("Key", key);
		this.parameters.put("Attack", attack);
	}
}
