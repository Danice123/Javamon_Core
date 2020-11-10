package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;

public class Percent implements Evaluator<Integer> {

	private Evaluator<Integer> of;
	private int percentage;

	@JsonCreator
	public Percent(@JsonProperty("of") Evaluator<Integer> of, @JsonProperty("percentage") int percentage) {
		this.of = of;
		this.percentage = percentage;
	}

	@Override
	public Integer evaluate(AttackAction attack, MonsterHandler target) {
		double val = percentage / 100f;
		return (int) Math.floor(of.evaluate(attack, target) * val);
	}

}
