package dev.dankins.javamon.data.monster.attack.effect;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.Status;
import dev.dankins.javamon.data.monster.attack.AttackBase;
import dev.dankins.javamon.data.monster.instance.MonsterInstanceImpl;
import dev.dankins.javamon.logic.battlesystem.EffectHandler;

public class StatusEffect extends Effect {

	private final Target target;
	private final Status status;

	@JsonCreator
	public StatusEffect(@JsonProperty("target") final Target target,
			@JsonProperty("status") final Status status) {
		this.target = target;
		this.status = status;
	}

	@Override
	public void use(final EffectHandler effectHandler, final MonsterInstanceImpl user,
			final MonsterInstanceImpl target, final AttackBase move) {
		if (this.target == Target.TARGET) {
			if (status != Status.FAINTED && target.status != Status.NONE) {
				return;
			}
			target.status = status;
			switch (status) {
			case BURN:
				effectHandler.print(target.getName() + " has been burnt!");
				break;
			case FREEZE:
				effectHandler.print(target.getName() + " has been frozen solid!");
				break;
			case PARALYSIS:
				effectHandler.print(target.getName() + " has been paralyzed!");
				break;
			case POISON:
				effectHandler.print(target.getName() + " has been poisoned!");
				break;
			case POISON_TOXIC:
				effectHandler.print(target.getName() + " has been badly poisoned!");
				break;
			case SLEEP:
				effectHandler.print(target.getName() + " has been put to sleep!");
				break;
			case FAINTED:
				target.changeHealth(-target.getCurrentHealth());
				effectHandler.print("The attack was a OHKO!");
				break;
			default:
				break;
			}
		} else {
			if (status != Status.FAINTED && user.status != Status.NONE) {
				return;
			}
			user.status = status;
			switch (status) {
			case BURN:
				effectHandler.print(user.getName() + " has been burnt!");
				break;
			case FREEZE:
				effectHandler.print(user.getName() + " has been frozen solid!");
				break;
			case PARALYSIS:
				effectHandler.print(user.getName() + " has been paralyzed!");
				break;
			case POISON:
				effectHandler.print(user.getName() + " has been poisoned!");
				break;
			case POISON_TOXIC:
				effectHandler.print(user.getName() + " has been badly poisoned!");
				break;
			case SLEEP:
				effectHandler.print(user.getName() + " has been put to sleep!");
				break;
			case FAINTED:
				user.changeHealth(-user.getCurrentHealth());
				break;
			default:
				break;
			}
		}
	}
}
