package dev.dankins.javamon.battle.data.attack.require;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.AttackFlag;

public class CheckFlag implements Requirement {

	Map<AttackFlag, String> flagsToCheck;

	@JsonCreator
	public CheckFlag(@JsonProperty("flags") final Map<AttackFlag, String> flags) {
		flagsToCheck = flags;
	}

	@Override
	public boolean check(final AttackAction attack, final MonsterHandler target) {
		for (final AttackFlag flag : flagsToCheck.keySet()) {
			if (target.getFlag(flag)) {
				return false;
			}
		}
		return true;
	}
}
