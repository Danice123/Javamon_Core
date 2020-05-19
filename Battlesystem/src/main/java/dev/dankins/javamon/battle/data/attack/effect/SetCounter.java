package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;
import dev.dankins.javamon.battle.display.event.Event;

public class SetCounter implements Effect {

	private final Target target;
	private final String counter;
	private final Evaluator<Integer> value;

	@JsonCreator
	public SetCounter(@JsonProperty("target") final Target target,
			@JsonProperty("counter") final String counter,
			@JsonProperty("value") final Evaluator<Integer> value) {
		this.target = target;
		this.counter = counter;
		this.value = value;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		MonsterHandler p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = attack.user;
		}

		p.setCounter(counter, value.evaluate(attack, target));
		return Lists.newArrayList();
	}
}
