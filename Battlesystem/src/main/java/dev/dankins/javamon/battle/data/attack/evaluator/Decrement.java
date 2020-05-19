package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;

public class Decrement implements Evaluator<Integer> {

	private final Evaluator<Integer> value;

	@JsonCreator
	public Decrement(@JsonProperty("value") final Evaluator<Integer> value) {
		this.value = value;
	}

	@Override
	public Integer evaluate(final AttackAction attack, final MonsterHandler target) {
		return value.evaluate(attack, target) - 1;
	}

}
