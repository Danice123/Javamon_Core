package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;

public class UpdateHealthEvent extends Event {

	public UpdateHealthEvent(final String key, final int previousHealth, final int currentHealth) {
		super(EventType.UpdateHealth);
		this.parameters.put("Key", key);
		this.parameters.put("PreviousHealth", previousHealth);
		this.parameters.put("CurrentHealth", currentHealth);
	}

}
