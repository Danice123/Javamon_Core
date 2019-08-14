package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;

public class SetFlag extends Effect {

	private final Target target;
	private final String flag;
	private String text;

	@JsonCreator
	public SetFlag(@JsonProperty("target") final Target target, @JsonProperty("flag") final String flag, @JsonProperty("text") final String text) {
		this.target = target;
		this.flag = flag;
		this.text = text;
	}

	@Override
	public void use(final MonsterInstanceImpl user, final MonsterInstanceImpl target, final AttackBase move) {
		MonsterInstanceImpl p;
		if (this.target == Target.TARGET) {
			p = target;
		} else {
			p = user;
		}

		p.battleStatus.setFlag(flag, true);
		if (text != null) {
			text = text.replace("$target", p.getName());
			if (p.battleStatus.lastMove != -1) {
				text = text.replace("$lastMove", p.attacks.get(p.battleStatus.lastMove).attack.name);
			}
			// menu.print(text);
		}
	}
}
