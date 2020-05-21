package dev.dankins.javamon.battle.data.attack.require;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.Targeted;
import dev.dankins.javamon.battle.data.attack.effect.Target;
import dev.dankins.javamon.data.monster.Status;

public class HasStatus extends Targeted implements Requirement {

	private final String status;

	@JsonCreator
	public HasStatus(@JsonProperty("target") final Target target,
			@JsonProperty("status") final String status) {
		super(target);
		this.status = status;
	}

	@Override
	public boolean check(final AttackAction attack, final MonsterHandler target) {
		if (Status.isStatus(status) != null) {
			return getTarget(attack, target).getMonster().getStatus()
					.equals(Status.isStatus(status));
		}
		return getTarget(attack, target).getTemporaryStatuses().stream()
				.anyMatch(existingStatus -> existingStatus.getName().equals(status));
	}

}
