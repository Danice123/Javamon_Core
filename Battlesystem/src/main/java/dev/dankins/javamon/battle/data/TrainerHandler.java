package dev.dankins.javamon.battle.data;

import dev.dankins.javamon.battle.BattleStateChange;
import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.SwitchAction;

public interface TrainerHandler extends Trainer {

	MonsterHandler getCurrentMonster();

	Action getNextAction(TrainerHandler opponent);

	SwitchAction getNextMonster() throws BattleStateChange;

}
