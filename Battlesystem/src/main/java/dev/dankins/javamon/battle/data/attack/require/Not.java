package dev.dankins.javamon.battle.data.attack.require;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;

public class Not implements Requirement {

	private final Requirement value;

	@JsonCreator
	public Not(@JsonProperty("value") final Requirement value) {
		this.value = value;
	}

	@Override
	public boolean check(final AttackAction attack, final MonsterHandler target) {
		return !value.check(attack, target);
	}

}
