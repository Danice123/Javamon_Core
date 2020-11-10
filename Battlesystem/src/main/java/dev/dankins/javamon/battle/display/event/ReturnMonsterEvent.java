package dev.dankins.javamon.battle.display.event;

import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.TrainerHandler;

public class ReturnMonsterEvent extends GenericEvent {

	public final TrainerHandler trainer;
	public final MonsterHandler monster;

	public ReturnMonsterEvent(TrainerHandler trainer, MonsterHandler monster) {
		super(EventType.ReturnMonster);
		this.trainer = trainer;
		this.monster = monster;
	}

}
