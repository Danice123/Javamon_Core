package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;

public class ClearStats implements Effect {

	private final Target target;

	@JsonCreator
	public ClearStats(@JsonProperty("target") final Target target) {
		this.target = target;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		if (this.target == Target.TARGET) {
			target.resetStats();
		} else {
			attack.user.resetStats();
		}
		return Lists.newArrayList();
	}

}
