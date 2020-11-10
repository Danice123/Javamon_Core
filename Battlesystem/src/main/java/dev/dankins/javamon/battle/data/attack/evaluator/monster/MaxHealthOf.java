package dev.dankins.javamon.battle.data.attack.evaluator.monster;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.Targeted;
import dev.dankins.javamon.battle.data.attack.effect.Target;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;

public class MaxHealthOf extends Targeted implements Evaluator<Integer> {

	@JsonCreator
	public MaxHealthOf(@JsonProperty("target") final Target target) {
		super(target);
	}

	@Override
	public Integer evaluate(AttackAction attack, MonsterHandler target) {
		return getTarget(attack, target).getMonster().getHealth();
	}

}
