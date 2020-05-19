package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;

public class Number implements Evaluator<Integer> {

	private final int value;

	@JsonCreator
	public Number(@JsonProperty("value") final int value) {
		this.value = value;
	}

	@Override
	public Integer evaluate(final AttackAction attack, final MonsterHandler target) {
		return value;
	}

}
