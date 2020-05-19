package dev.dankins.javamon.battle.data.attack.require;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.effect.Target;

public class CheckCounter implements Requirement {

	private final Target target;
	private final Map<String, Integer> counters;

	@JsonCreator
	public CheckCounter(@JsonProperty("target") final Target target,
			@JsonProperty("counters") final Map<String, Integer> counters) {
		this.target = target;
		this.counters = counters;
	}

	@Override
	public boolean check(final AttackAction attack, final MonsterHandler target) {
		MonsterHandler p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = attack.user;
		}

		for (final String counter : counters.keySet()) {
			if (counters.get(counter) != p.getCounter(counter)) {
				return false;
			}
		}
		return true;
	}

}
