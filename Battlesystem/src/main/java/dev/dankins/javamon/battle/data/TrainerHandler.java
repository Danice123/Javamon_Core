package dev.dankins.javamon.battle.data;

import dev.dankins.javamon.battle.BattleStateChange;
import dev.dankins.javamon.battle.action.Action;
import dev.dankins.javamon.battle.action.SwitchAction;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;
import dev.dankins.javamon.data.monster.instance.Trainer;

public interface TrainerHandler extends Trainer {

	String getKey();

	MonsterHandler getCurrentMonster();

	@Override
	default MonsterInstance getCurrentMonster_() {
		return getCurrentMonster().getMonster();
	}

	Action getNextAction(TrainerHandler opponent);

	SwitchAction getNextMonster() throws BattleStateChange;

}
