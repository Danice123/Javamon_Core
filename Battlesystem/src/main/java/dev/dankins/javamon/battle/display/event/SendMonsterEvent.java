package dev.dankins.javamon.battle.display.event;

import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.TrainerHandler;

public class SendMonsterEvent extends GenericEvent {

	public final TrainerHandler trainer;
	public final MonsterHandler monster;

	public SendMonsterEvent(TrainerHandler trainer, MonsterHandler monster) {
		super(EventType.SendMonster);
		this.trainer = trainer;
		this.monster = monster;
	}

}
