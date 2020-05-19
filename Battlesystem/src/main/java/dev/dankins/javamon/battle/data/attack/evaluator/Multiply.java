package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;

public class Multiply implements Evaluator<Integer> {

	private final Evaluator<Integer> value;
	private final int multiplier;

	@JsonCreator
	public Multiply(@JsonProperty("value") final Evaluator<Integer> value,
			@JsonProperty("multiplier") final int multiplier) {
		this.value = value;
		this.multiplier = multiplier;
	}

	@Override
	public Integer evaluate(final AttackAction attack, final MonsterHandler target) {
		return value.evaluate(attack, target) * multiplier;
	}

}
