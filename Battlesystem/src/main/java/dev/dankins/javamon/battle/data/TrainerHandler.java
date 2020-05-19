package dev.dankins.javamon.battle.data;

import dev.dankins.javamon.battle.action.Action;

public interface TrainerHandler {

	String getKey();

	MonsterHandler getCurrentMonster();

	Action getNextAction(TrainerHandler opponent);

}
