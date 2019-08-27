package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;

public class ClearStats extends Effect {

	private final Target target;
	private String text;

	@JsonCreator
	public ClearStats(@JsonProperty("target") final Target target,
			@JsonProperty("text") final String text) {
		this.target = target;
		this.text = text;
	}

	@Override
	public void use(final EffectHandler effectHandler, final MonsterInstanceImpl user,
			final MonsterInstanceImpl target, final AttackBase move) {
		if (this.target == Target.TARGET) {
			target.battleStatus.resetStats();
		} else {
			user.battleStatus.resetStats();
		}
		if (text != null) {
			text = text.replace("$target", target.getName());
			if (target.battleStatus.lastMove != -1) {
				text = text.replace("$lastMove",
						target.attacks.get(target.battleStatus.lastMove).attack.name);
			}
			effectHandler.print(text);
		}
	}

}
