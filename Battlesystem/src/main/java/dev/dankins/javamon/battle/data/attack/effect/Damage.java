package dev.dankins.javamon.battle.data.attack.effect;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.RandomNumberGenerator;
import dev.dankins.javamon.battle.action.AttackAction;
import dev.dankins.javamon.battle.data.MonsterHandler;
import dev.dankins.javamon.battle.data.attack.AttackCounter;
import dev.dankins.javamon.battle.data.attack.Class;
import dev.dankins.javamon.battle.display.event.Event;
import dev.dankins.javamon.battle.display.event.EventType;
import dev.dankins.javamon.battle.display.event.TextEvent;
import dev.dankins.javamon.battle.display.event.attack.TypeEffectivenessEvent;
import dev.dankins.javamon.battle.display.event.attack.UpdateHealthEvent;
import dev.dankins.javamon.data.monster.Status;

public class Damage implements Effect {

	private final int power;
	private final boolean contact;
	private final int drain;
	private final int recoil;
	private final int crit;

	@JsonCreator
	public Damage(@JsonProperty("power") final int power, @JsonProperty("contact") final boolean contact,
			@JsonProperty("drain") final int drain, @JsonProperty("recoil") final int recoil,
			@JsonProperty("crit") final int crit) {
		this.power = power;
		this.contact = contact;
		this.drain = drain;
		this.recoil = recoil;
		this.crit = crit;
	}

	@Override
	public List<Event> use(final AttackAction attack, final MonsterHandler target) {
		final List<Event> results = Lists.newArrayList();

		// Calculate
		final boolean isCrit = isCrit(crit + attack.user.getCounter(AttackCounter.CRITICAL_RATE_BOOST.toString()));
		final int damage = damageCalc(attack, target, isCrit);
		results.add(target.doDamage(damage));
		if (isCrit) {
			results.add(new Event(EventType.CriticalHit));
		}
		results.add(new TypeEffectivenessEvent(target.getType().getEffectiveness(attack.attackInstance.attack.type)));

		// Drain
		if (drain > 0) {
			results.add(new TextEvent(EventType.AttackDisplay,
					attack.user.getMonster().getName() + " drained " + target.getMonster().getName() + "'s health!"));
			final int previousHealth = attack.user.getMonster().getCurrentHealth();
			attack.user.getMonster().changeHealth(damage / (100 / drain));
			results.add(new UpdateHealthEvent(attack.user.getKey(), previousHealth,
					attack.user.getMonster().getCurrentHealth()));
		}

		// Recoil
		if (recoil > 0) {
			results.add(new TextEvent(EventType.AttackDisplay,
					attack.user.getMonster().getName() + " took damage from the attack!"));
			final int previousHealth = attack.user.getMonster().getCurrentHealth();
			attack.user.getMonster().changeHealth(damage / (100 / recoil));
			results.add(new UpdateHealthEvent(attack.user.getKey(), previousHealth,
					attack.user.getMonster().getCurrentHealth()));
		}

		return results;
	}

	public int damageCalc(final AttackAction attack, final MonsterHandler target, final boolean isCrit) {
		float damage = (2f * attack.user.getMonster().getLevel() / 5f + 2f) * power;

		// Physical stat calc
		if (attack.attackInstance.attack.damageClass == Class.PHYSICAL) {
			damage = damage * attack.user.getAttack(isCrit) / target.getDefense(isCrit) / 50f + 2f;

			// Burn
			if (attack.user.getMonster().getStatus() == Status.BURN) {
				damage *= 0.5f;
			}
		}
		// Special stat calc
		if (attack.attackInstance.attack.damageClass == Class.SPECIAL) {
			damage = damage * attack.user.getSpecialAttack(isCrit) / target.getSpecialDefense(isCrit) / 50f + 2f;
		}

		// Crit
		if (isCrit) {
			damage *= 1.5f;
		}

		// Random
		damage *= (85f + RandomNumberGenerator.random.nextInt(16)) / 100f;

		// Stab
		if (attack.user.getMonster().getBaseMonster().isType(attack.attackInstance.attack.type)) {
			damage *= 1.5f;
		}

		// Type
		damage *= target.getType().getEffectiveness(attack.attackInstance.attack.type);

		return Math.round(damage);
	}

	public boolean isCrit(final int rate) {
		final int chance;
		switch (rate) {
		case 0:
			chance = 625;
			break;
		case 1:
			chance = 1250;
			break;
		case 2:
			chance = 2500;
			break;
		case 3:
			chance = 3333;
			break;
		case 4:
			chance = 5000;
			break;
		default:
			chance = 10000;
			break;
		}
		if (chance >= RandomNumberGenerator.random.nextInt(10000)) {
			return true;
		}
		return false;
	}

}
