package dev.dankins.javamon.data.monster.attack.require;

import dev.dankins.javamon.RandomNumberGenerator;
import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;

public class OHKO extends Require {

	@Override
	public boolean check(final MonsterInstanceImpl user, final MonsterInstanceImpl target, final AttackBase move) {
		if (user.getLevel() < target.getLevel()) {
			// menu.print("The attack failed...");
			return true;
		}
		final int chance = 30 + user.getLevel() - target.getLevel();
		if (chance <= RandomNumberGenerator.random.nextInt(100)) {
			return true;
		}
		// menu.print("The attack missed...");
		return false;
	}

}
