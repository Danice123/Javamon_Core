package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;

public class Heal extends Effect {

	private final Integer percent;
	private final Integer rawValue;

	@JsonCreator
	public Heal(@JsonProperty("percent") final Integer percent,
			@JsonProperty("rawValue") final Integer rawValue) {
		this.percent = percent;
		this.rawValue = rawValue;
	}

	@Override
	public void use(final EffectHandler effectHandler, final MonsterInstanceImpl user,
			final MonsterInstanceImpl target, final AttackBase move) {
		if (percent != null) {
			user.changeHealth(user.getHealth() * (percent / 100));
		}
		if (rawValue != null) {
			user.changeHealth(rawValue);
		}
		effectHandler.print(user.getName() + " has been healed!");
	}

}
