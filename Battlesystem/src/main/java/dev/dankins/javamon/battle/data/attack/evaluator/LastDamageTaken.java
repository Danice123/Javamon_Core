package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.Targeted;
import dev.dankins.javamon.battle.data.attack.effect.Target;

public class LastDamageTaken extends Targeted implements Evaluator<Integer> {

	@JsonCreator
	public LastDamageTaken(@JsonProperty("target") final Target target) {
		super(target);
	}

	@Override
	public Integer evaluate(final AttackAction attack, final MonsterHandler target) {
		return getTarget(attack, target).getLastDamageInstance();
	}

}
