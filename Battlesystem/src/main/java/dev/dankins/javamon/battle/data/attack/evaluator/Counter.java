package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.effect.Target;

public class Counter implements Evaluator<Integer> {

	private final Target target;
	private final String counter;

	@JsonCreator
	public Counter(@JsonProperty("target") final Target target,
			@JsonProperty("counter") final String counter) {
		this.target = target;
		this.counter = counter;
	}

	@Override
	public Integer evaluate(final AttackAction attack, final MonsterHandler target) {
		MonsterHandler p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = attack.user;
		}
		return p.getCounter(counter);
	}

}
