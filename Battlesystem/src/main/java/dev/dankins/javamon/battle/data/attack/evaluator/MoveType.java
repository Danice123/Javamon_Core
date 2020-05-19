package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.Targeted;
import dev.dankins.javamon.battle.data.attack.effect.Target;
import dev.dankins.javamon.data.monster.MultiType;

public class MoveType extends Targeted implements Evaluator<MultiType> {

	private final int slot;

	@JsonCreator
	public MoveType(@JsonProperty("target") final Target target,
			@JsonProperty("slot") final int slot) {
		super(target);
		this.slot = slot;
	}

	@Override
	public MultiType evaluate(final AttackAction attack, final MonsterHandler target) {
		return new MultiType(getTarget(attack, target).getMonster().attacks.get(slot).attack.type);
	}

}
