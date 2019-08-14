package dev.dankins.javamon.data.monster.attack.require;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;

public class CheckSelf extends Require {

	boolean isCharging;

	@Override
	public boolean check(final MonsterInstanceImpl user, final MonsterInstanceImpl target, final AttackBase move) {
		if (isCharging && !user.battleStatus.getFlag("isSeeded")) {
			return false;
		}
		return true;
	}

}
