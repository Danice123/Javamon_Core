package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.RandomNumberGenerator;
import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;

public class LevelDamage extends Effect {

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
	public void use(EffectHandler effectHandler, final MonsterInstanceImpl user, final MonsterInstanceImpl target, final AttackBase move) {
		if (random) {
			percent = (RandomNumberGenerator.random.nextInt(max - min) + min) * 10;
		}
		target.changeHealth((int) -(user.getLevel() / (100.0 / percent)));
	}

}
