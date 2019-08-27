package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;

public class StaticDamage extends Effect {

	int damage;
	int percent;
	boolean pDamage = false;

	@JsonCreator
	public StaticDamage(@JsonProperty("damage") final int damage, @JsonProperty("percent") final int percent, @JsonProperty("pDamage") final boolean pDamage) {
		this.damage = damage;
		this.percent = percent;
		this.pDamage = pDamage;
	}

	@Override
	public void use(EffectHandler effectHandler, final MonsterInstanceImpl user, final MonsterInstanceImpl target, final AttackBase move) {
		if (pDamage) {
			target.changeHealth((int) -(target.getHealth() / (100.0 / percent)));
		} else {
			target.changeHealth(-damage);
		}

	}

}
