package dev.dankins.javamon.battle.data.attack.effect.proxy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.effect.Effect;
import dev.dankins.javamon.battle.display.event.Event;

public class ProxyEffectDef {

	private final List<Effect> effects;

	@JsonCreator
	public ProxyEffectDef(@JsonProperty("effects") final List<Effect> effects) {
		this.effects = effects;
	}

	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		final List<Event> results = Lists.newArrayList();
		for (final Effect effect : effects) {
			results.addAll(effect.use(attack, target));
		}
		return results;
	}

}
