package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.evaluator.Evaluator;
import dev.dankins.javamon.battle.display.event.Event;

public class StaticDamage implements Effect {

	private final Evaluator<Integer> damage;

	@JsonCreator
	public StaticDamage(@JsonProperty("damage") final Evaluator<Integer> damage) {
		this.damage = damage;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		return Lists.newArrayList(target.doDamage(damage.evaluate(attack, target)));
	}

}
