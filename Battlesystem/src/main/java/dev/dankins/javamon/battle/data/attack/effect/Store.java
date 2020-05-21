package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.Targeted;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;
import dev.dankins.javamon.battle.display.event.Event;

public class Store extends Targeted implements Effect {

	private final String store;
	private final Evaluator<?> value;

	@JsonCreator
	public Store(@JsonProperty("target") final Target target,
			@JsonProperty("store") final String store,
			@JsonProperty("value") final Evaluator<?> value) {
		super(target);
		this.store = store;
		this.value = value;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		getTarget(attack, target).getStore().put(store, value.evaluate(attack, target));
		return Lists.newArrayList();
	}

}
