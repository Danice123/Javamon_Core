package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.BattlesystemHook;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;

public class CustomStatus implements Effect {

	private final String name;
	private final Target target;
	private final Map<BattlesystemHook, List<Effect>> events;

	private AttackAction attack;
	private MonsterHandler targetMonster;

	@JsonCreator
	public CustomStatus(@JsonProperty("name") final String name,
			@JsonProperty("target") final Target target,
			@JsonProperty("events") final Map<BattlesystemHook, List<Effect>> events) {
		this.name = name;
		this.target = target;
		this.events = events;
	}

	public String getName() {
		return name;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		MonsterHandler p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = attack.user;
		}

		if (p.getTemporaryStatuses().stream().anyMatch(status -> status.equals(this))) {
			return Lists.newArrayList();
		}
		this.attack = attack;
		targetMonster = p;
		p.getTemporaryStatuses().add(this);
		return Lists.newArrayList(apply(BattlesystemHook.onEffectInit));
	}

	public List<Event> apply(final BattlesystemHook event) {
		if (events.containsKey(event)) {
			return events.get(event).stream().map(effect -> effect.use(attack, targetMonster))
					.flatMap(list -> list.stream()).collect(Collectors.toList());
		}
		return Lists.newArrayList();
	}

	@Override
	public boolean equals(final Object obj) {
		if (CustomStatus.class.isInstance(obj)) {
			return name == ((CustomStatus) obj).name;
		}
		return false;
	}

}
