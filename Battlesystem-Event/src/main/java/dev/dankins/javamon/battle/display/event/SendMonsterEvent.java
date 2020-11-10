package dev.dankins.javamon.battle.display.event;

import dev.dankins.javamon.battle.data.Monster;
import dev.dankins.javamon.battle.data.Trainer;

public class SendMonsterEvent extends GenericEvent {

	public final Trainer trainer;
	public final Monster monster;

	public SendMonsterEvent(Trainer trainer, Monster monster) {
		super(EventType.SendMonster);
		this.trainer = trainer;
		this.monster = monster;
	}

}
