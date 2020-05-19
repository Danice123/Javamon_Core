package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.RandomNumberGenerator;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;

public class RandomRange implements Evaluator<Integer> {

	private final int max;
	private final int min;

	@JsonCreator
	public RandomRange(@JsonProperty("max") final int max, @JsonProperty("min") final int min) {
		this.max = max;
		this.min = min;
	}

	@Override
	public Integer evaluate(final AttackAction attack, final MonsterHandler target) {
		return RandomNumberGenerator.random.nextInt(max - min) + min;
	}

}
