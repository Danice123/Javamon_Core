package dev.dankins.javamon.battle.display.event;

import dev.dankins.javamon.battle.data.Monster;
import dev.dankins.javamon.battle.data.Trainer;

public class FaintedMonsterEvent extends GenericEvent {

	public final Trainer trainer;
	public final Monster monster;

	public FaintedMonsterEvent(Trainer trainer, Monster monster) {
		super(EventType.FaintMonster);
		this.trainer = trainer;
		this.monster = monster;
	}
}
