package dev.dankins.javamon.battle.display.event.attack;

import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.TextEvent;

public class TypeEffectivenessEvent extends TextEvent {

	public TypeEffectivenessEvent(final float amount) {
		super(EventType.TypeEffectiveness, effectM(amount));
	}

	private static String effectM(float amount) {
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
