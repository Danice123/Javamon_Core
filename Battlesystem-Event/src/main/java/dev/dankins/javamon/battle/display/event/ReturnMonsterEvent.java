package dev.dankins.javamon.battle.display.event;

import dev.dankins.javamon.battle.data.Monster;
import dev.dankins.javamon.battle.data.Trainer;

public class ReturnMonsterEvent extends GenericEvent {

	public final Trainer trainer;
	public final Monster monster;

	public ReturnMonsterEvent(Trainer trainer, Monster monster) {
		super(EventType.ReturnMonster);
		this.trainer = trainer;
		this.monster = monster;
	}

}
