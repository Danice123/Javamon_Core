package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.RandomNumberGenerator;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.display.event.Event;

public class LevelDamage implements Effect {

	int percent;

	boolean random = false;
	int max;
	int min;

	@JsonCreator
	public LevelDamage(@JsonProperty("percent") final int percent,
			@JsonProperty("random") final boolean random, @JsonProperty("max") final int max,
			@JsonProperty("min") final int min) {
		this.percent = percent;
		this.random = random;
		this.max = max;
		this.min = min;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		if (random) {
			percent = (RandomNumberGenerator.random.nextInt(max - min) + min) * 10;
		}
		return Lists.newArrayList(
				target.doDamage((int) (attack.user.getMonster().getLevel() / (100.0 / percent))));
	}

}
