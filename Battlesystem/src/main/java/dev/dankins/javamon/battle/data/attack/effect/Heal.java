package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.attack.UpdateHealthEvent;

public class Heal implements Effect {

	private final Integer percent;
	private final Integer rawValue;

	@JsonCreator
	public Heal(@JsonProperty("percent") final Integer percent,
			@JsonProperty("rawValue") final Integer rawValue) {
		this.percent = percent;
		this.rawValue = rawValue;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		final int previousHealth = attack.user.getMonster().getCurrentHealth();
		if (percent != null) {
			attack.user.getMonster()
					.changeHealth(attack.user.getMonster().getHealth() * (percent / 100));
		}
		if (rawValue != null) {
			attack.user.getMonster().changeHealth(rawValue);
		}
		return Lists.newArrayList(new UpdateHealthEvent(attack.user.getKey(), previousHealth,
				attack.user.getMonster().getCurrentHealth()));
	}

}
