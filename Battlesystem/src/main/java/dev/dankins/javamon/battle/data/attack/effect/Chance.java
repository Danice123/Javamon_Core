package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.RandomNumberGenerator;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;

public class Chance implements Effect {

	private final int chance;
	private final List<Effect> effects;

	@JsonCreator
	public Chance(@JsonProperty("chance") final int chance,
			@JsonProperty("effects") final List<Effect> effects) {
		this.chance = chance;
		this.effects = effects;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		final List<Event> results = Lists.newArrayList();
		if (RandomNumberGenerator.random.nextInt(100) < chance) {
			for (final Effect effect : effects) {
				results.addAll(effect.use(attack, target));
			}
		}
		return results;
	}

}
