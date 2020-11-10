package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.GenericEvent;
import dev.dankins.javamon.data.monster.attack.Attack;

public class AttackEvent extends GenericEvent {

	public final String key;
	public final Attack attack;

	public AttackEvent(final String key, final Attack attack) {
		super(EventType.Attack);
		this.key = key;
		this.attack = attack;
	}
}
