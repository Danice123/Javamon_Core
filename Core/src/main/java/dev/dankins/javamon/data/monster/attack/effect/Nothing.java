package dev.dankins.javamon.data.monster.attack.effect;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;

public class Nothing extends Effect {

	@Override
	public void use(final MonsterInstanceImpl user, final MonsterInstanceImpl target, final AttackBase move) {
		// menu.print("The attack did nothing!");
	}

}
