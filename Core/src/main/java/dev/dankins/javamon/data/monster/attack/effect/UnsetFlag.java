package dev.dankins.javamon.data.monster.attack.effect;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;

public class UnsetFlag extends Effect {

	Target target;
	String flag;
	String text;

	@Override
	public void use(final EffectHandler effectHandler, final MonsterInstanceImpl user,
			final MonsterInstanceImpl target, final AttackBase move) {
		MonsterInstanceImpl p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = user;
		}

		p.battleStatus.setFlag(flag, false);
		if (text != null) {
			text = text.replace("$target", p.getName());
			if (p.battleStatus.lastMove != -1) {
				text = text.replace("$lastMove",
						p.attacks.get(p.battleStatus.lastMove).attack.name);
			}
			effectHandler.print(text);
		}
	}
}
