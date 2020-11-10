package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;

public class TypeEffectivenessEvent implements Event {

	public final float amount;

	public TypeEffectivenessEvent(final float amount) {
		this.amount = amount;
	}

	@Override
	public EventType getType() {
		return EventType.TypeEffectiveness;
	}

	public String effectM() {
		if (amount == 1.0) {
			return null;
		}
		if (amount == 4.0) {
			return "It hit at max effectiveness!";
		}
		if (amount == 2.0) {
			return "It was super effective!";
		}
		if (amount == 0.5) {
			return "It wasn't very effective...";
		}
		if (amount == 0.25) {
			return "It hardly did anything...";
		}
		if (amount == 0.0) {
			return "The attack didn't seem to do anything!";
		}
		return null;
	}

}
