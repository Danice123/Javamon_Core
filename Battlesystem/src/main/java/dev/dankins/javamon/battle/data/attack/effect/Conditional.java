package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.require.Requirement;
import dev.dankins.javamon.battle.display.event.Event;

public class Conditional implements Effect {

	private final List<Requirement> requirements;
	private final List<Effect> effects;
	private final List<Effect> effectsElse;

	@JsonCreator
	public Conditional(@JsonProperty("if") final List<Requirement> requirements,
			@JsonProperty("then") final List<Effect> effects,
			@JsonProperty("else") final List<Effect> effectsElse) {
		this.requirements = requirements;
		this.effects = effects;
		this.effectsElse = effectsElse;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		boolean b = true;
		for (final Requirement req : requirements) {
			if (!req.check(attack, target)) {
				b = false;
				break;
			}
		}

		if (b) {
			final List<Event> results = Lists.newArrayList();
			for (final Effect effect : effects) {
				results.addAll(effect.use(attack, target));
			}
			return results;
		} else if (effectsElse != null) {
			final List<Event> results = Lists.newArrayList();
			for (final Effect effect : effectsElse) {
				results.addAll(effect.use(attack, target));
			}
			return results;
		} else {
			return Lists.newArrayList();
		}
	}

}
