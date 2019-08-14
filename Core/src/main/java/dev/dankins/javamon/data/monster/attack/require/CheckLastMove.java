package dev.dankins.javamon.data.monster.attack.require;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;

public class CheckLastMove extends Require {

	boolean valid = false;

	@JsonCreator
	public CheckLastMove(@JsonProperty("valid") final boolean valid) {
		this.valid = valid;
	}

	@Override
	public boolean check(final MonsterInstanceImpl user, final MonsterInstanceImpl target, final AttackBase move) {
		if (valid) {
			if (target.battleStatus.lastMove == -1) {
				// menu.print("The move failed...");
				return false;
			}
			if (target.attacks.get(target.battleStatus.lastMove).currentUsage == 0) {
				// menu.print("The move failed...");
				return false;
			}
			if (target.battleStatus.getFlag("isDisabled") && target.battleStatus.getCounter("DisabledMove") == target.battleStatus.lastMove) {
				// menu.print("The move failed...");
				return false;
			}
		}
		return true;
	}

}
