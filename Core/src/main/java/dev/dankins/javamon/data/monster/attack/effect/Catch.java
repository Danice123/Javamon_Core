package dev.dankins.javamon.data.monster.attack.effect;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;

public class Catch extends Effect {

	@Override
	public void use(EffectHandler effectHandler, final MonsterInstanceImpl user, final MonsterInstanceImpl target, final AttackBase move) {
		target.battleStatus.setFlag("isCaught", true);
	}

}
