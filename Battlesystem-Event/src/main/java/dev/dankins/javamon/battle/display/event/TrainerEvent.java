package dev.dankins.javamon.battle.display.event;

import dev.dankins.javamon.battle.data.Trainer;

public class TrainerEvent extends GenericEvent {

	public final Trainer trainer;

	public TrainerEvent(EventType type, Trainer trainer) {
		super(type);
		this.trainer = trainer;
	}

}
