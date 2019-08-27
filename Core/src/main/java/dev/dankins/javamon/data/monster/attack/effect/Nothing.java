package dev.dankins.javamon.data.monster.attack.effect;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;

public class Nothing extends Effect {

	@Override
	public void use(final EffectHandler effectHandler, final MonsterInstanceImpl user,
			final MonsterInstanceImpl target, final AttackBase move) {
		effectHandler.print("The attack did nothing!");
	}

}
