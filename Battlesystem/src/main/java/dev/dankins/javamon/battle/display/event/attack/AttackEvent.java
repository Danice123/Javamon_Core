package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.data.attack.AttackInstance;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.GenericEvent;

public class AttackEvent extends GenericEvent {

	public final String key;
	public final AttackInstance attack;

	public AttackEvent(final String key, final AttackInstance attack) {
		super(EventType.Attack);
		this.key = key;
		this.attack = attack;
	}
}
