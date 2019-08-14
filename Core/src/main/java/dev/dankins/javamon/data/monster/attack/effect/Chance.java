package dev.dankins.javamon.data.monster.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.RandomNumberGenerator;
import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;

public class Chance extends Effect {

	private final int chance;
	private final List<Effect> effects;

	@JsonCreator
	public Chance(@JsonProperty("chance") final int chance,
			@JsonProperty("effects") final List<Effect> effects) {
		this.chance = chance;
		this.effects = effects;
	}

	@Override
	public void use(final MonsterInstanceImpl user, final MonsterInstanceImpl target, final AttackBase move) {
		if (RandomNumberGenerator.random.nextInt(100) < chance) {
			for (final Effect effect : effects) {
				effect.use(user, target, move);
			}
		}
	}

}
