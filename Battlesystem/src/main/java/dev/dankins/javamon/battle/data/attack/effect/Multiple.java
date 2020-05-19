package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.RandomNumberGenerator;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;

public class Multiple implements Effect {

	private final List<Effect> effects;
	private final int min;
	private final int max;

	@JsonCreator
	public Multiple(@JsonProperty("effects") final List<Effect> effects,
			@JsonProperty("min") final int min, @JsonProperty("max") final int max) {
		this.effects = effects;
		this.min = min;
		this.max = max;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		final List<Event> results = Lists.newArrayList();
		final int times = RandomNumberGenerator.random.nextInt(max - min) + min;
		for (int i = 0; i < times; i++) {
			for (final Effect effect : effects) {
				results.addAll(effect.use(attack, target));
			}
		}
		return results;
	}

}
