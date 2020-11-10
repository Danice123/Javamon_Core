package dev.dankins.javamon.battle.display.event;

import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.TrainerHandler;

public class FaintedMonsterEvent extends GenericEvent {

	public final TrainerHandler trainer;
	public final MonsterHandler monster;

	public FaintedMonsterEvent(TrainerHandler trainer, MonsterHandler monster) {
		super(EventType.FaintMonster);
		this.trainer = trainer;
		this.monster = monster;
	}
}
