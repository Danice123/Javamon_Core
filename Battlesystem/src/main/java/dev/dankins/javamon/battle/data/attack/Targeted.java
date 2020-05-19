package dev.dankins.javamon.battle.data.attack;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.effect.Target;

public abstract class Targeted {

	private final Target target;

	protected Targeted(final Target target) {
		this.target = target;
	}

	protected MonsterHandler getTarget(final AttackAction attack, final MonsterHandler target) {
		if (this.target == Target.TARGET) {
			return target;
		} else {
			return attack.user;
		}
	}

}
