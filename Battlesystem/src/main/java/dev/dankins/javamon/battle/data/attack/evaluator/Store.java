package dev.dankins.javamon.battle.data.attack.evaluator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.Targeted;
import dev.dankins.javamon.battle.data.attack.effect.Target;

public class Store extends Targeted implements Evaluator<Object> {

	private final String store;

	@JsonCreator
	public Store(@JsonProperty("target") final Target target,
			@JsonProperty("store") final String store) {
		super(target);
		this.store = store;
	}

	@Override
	public Object evaluate(final AttackAction attack, final MonsterHandler target) {
		return getTarget(attack, target).getStore().get(store);
	}

}
