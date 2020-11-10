package dev.dankins.javamon.battle.display.event;

import dev.dankins.javamon.battle.data.TrainerHandler;

public class TrainerEvent extends GenericEvent {

	public final TrainerHandler trainer;

	public TrainerEvent(EventType type, TrainerHandler trainer) {
		super(type);
		this.trainer = trainer;
	}

}
