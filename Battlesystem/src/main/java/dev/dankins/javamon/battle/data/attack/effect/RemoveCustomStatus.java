package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;

public class RemoveCustomStatus implements Effect {

	private final String name;
	private final Target target;

	@JsonCreator
	public RemoveCustomStatus(@JsonProperty("name") final String name,
			@JsonProperty("target") final Target target) {
		this.name = name;
		this.target = target;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		MonsterHandler p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = attack.user;
		}
		p.getTemporaryStatuses().removeIf(status -> status.getName().equals(name));
		return Lists.newArrayList();
	}

}
