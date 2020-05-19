package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;

public class UpdateHealthEvent implements Event {

	public final String key;
	public final int previousHealth;
	public final int currentHealth;

	public UpdateHealthEvent(final String key, final int previousHealth, final int currentHealth) {
		this.key = key;
		this.previousHealth = previousHealth;
		this.currentHealth = currentHealth;
	}

	@Override
	public EventType getType() {
		return EventType.UpdateHealth;
	}

}
