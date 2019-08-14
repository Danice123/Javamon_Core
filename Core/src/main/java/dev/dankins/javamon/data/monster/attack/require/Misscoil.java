package dev.dankins.javamon.data.monster.attack.require;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;

public class Misscoil extends Require {

	int recoil;

	@Override
	public boolean check(final MonsterInstanceImpl user, final MonsterInstanceImpl target, final AttackBase move) {
		// if (Action.missCalc(menu, user, target, move)) {
		// menu.print(user.getName() + " missed!");
		// menu.print(user.getName() + " continued on and hurt itself!");
		//
		// final int damage = (int) (user.getHealth() / (100.0 / recoil));
		// user.changeHealth(-damage);
		// return false;
		// }
		return true;
	}
}
