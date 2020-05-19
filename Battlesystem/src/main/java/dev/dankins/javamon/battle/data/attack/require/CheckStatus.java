package dev.dankins.javamon.battle.data.attack.require;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.data.monster.Status;

public class CheckStatus implements Requirement {

	Map<Status, String> statusesToCheck;

	@JsonCreator
	public CheckStatus(@JsonProperty("statuses") final Map<Status, String> statuses) {
		statusesToCheck = statuses;
	}

	@Override
	public boolean check(final AttackAction attack, final MonsterHandler target) {
		for (final Status status : statusesToCheck.keySet()) {
			if (target.getMonster().getStatus().equals(status)) {
				return false;
			}
		}
		return true;
	}

}
