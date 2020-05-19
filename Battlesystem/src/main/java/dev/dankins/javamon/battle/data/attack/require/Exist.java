package dev.dankins.javamon.battle.data.attack.require;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;

public class Exist implements Requirement {

	private final Evaluator<?> value;

	@JsonCreator
	public Exist(@JsonProperty("value") final Evaluator<?> value) {
		this.value = value;
	}

	@Override
	public boolean check(final AttackAction attack, final MonsterHandler target) {
		return value.evaluate(attack, target) != null;
	}

}
