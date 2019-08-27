package dev.dankins.javamon.data.monster.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.RandomNumberGenerator;
import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;

public class Multiple extends Effect {

	private final List<Effect> effects;
	private final int min;
	private final int max;

	@JsonCreator
	public Multiple(@JsonProperty("effects") final List<Effect> effects,
			@JsonProperty("min") final int min, @JsonProperty("max") final int max) {
		this.effects = effects;
		this.min = min;
		this.max = max;
	}

	@Override
	public void use(final EffectHandler effectHandler, final MonsterInstanceImpl user,
			final MonsterInstanceImpl target, final AttackBase move) {
		final int times = RandomNumberGenerator.random.nextInt(max - min) + min;
		for (int i = 0; i < times; i++) {
			for (final Effect effect : effects) {
				effect.use(effectHandler, user, target, move);
			}
		}
	}

}
