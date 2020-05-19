package dev.dankins.javamon.battle.data.attack.evaluator.attack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.AttackInstance;
import dev.dankins.javamon.battle.data.attack.Class;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;

public class ClassDef implements Evaluator<Class> {

	private final Evaluator<AttackInstance> fromAttack;
	private final Class value;

	@JsonCreator
	public ClassDef(@JsonProperty("fromAttack") final Evaluator<AttackInstance> fromAttack,
			@JsonProperty("value") final Class value) {
		this.fromAttack = fromAttack;
		this.value = value;
	}

	@Override
	public Class evaluate(final AttackAction attack, final MonsterHandler target) {
		if (fromAttack != null) {
			return fromAttack.evaluate(attack, target).attack.damageClass;
		}
		return value;
	}

}
