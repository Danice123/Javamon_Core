package dev.dankins.javamon.battle.data.attack.evaluator.attack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.AttackInstance;
import dev.dankins.javamon.battle.data.attack.Targeted;
import dev.dankins.javamon.battle.data.attack.effect.Target;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;

public class LastAttackUsed extends Targeted implements Evaluator<AttackInstance> {

	@JsonCreator
	public LastAttackUsed(@JsonProperty("target") final Target target) {
		super(target);
	}

	@Override
	public AttackInstance evaluate(final AttackAction attack, final MonsterHandler target) {
		return getTarget(attack, target).getLastUsedMove();
	}

}
