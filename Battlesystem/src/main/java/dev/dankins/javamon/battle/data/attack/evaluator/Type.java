package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.Targeted;
import dev.dankins.javamon.battle.data.attack.effect.Target;
import dev.dankins.javamon.data.monster.MultiType;

public class Type extends Targeted implements Evaluator<MultiType> {

	@JsonCreator
	public Type(@JsonProperty("target") final Target target) {
		super(target);
	}

	@Override
	public MultiType evaluate(final AttackAction attack, final MonsterHandler target) {
		return getTarget(attack, target).getType();
	}

}
