package dev.dankins.javamon.battle.data;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import dev.dankins.javamon.battle.data.attack.AttackFlag;
import dev.dankins.javamon.battle.data.attack.AttackInstance;
import dev.dankins.javamon.battle.data.attack.effect.CustomStatus;
import dev.dankins.javamon.battle.data.monster.MonsterInstance;
import dev.dankins.javamon.battle.display.event.attack.UpdateHealthEvent;
import dev.dankins.javamon.data.monster.MultiType;
import dev.dankins.javamon.data.monster.Stat;

public class MonsterHandler implements Monster {

	private final String key;
	private final MonsterInstance monster;

	private final EnumMap<Stat, Integer> statChangeLevel;
	private final EnumMap<AttackFlag, Boolean> flags;
	private final Map<String, Integer> counters;
	private final Map<String, Object> store;
	private final Set<CustomStatus> temporaryStatuses;

	private AttackInstance lastUsedMove;
	private Integer lastDamageInstance;
	private Integer multiTurnCount;
	private MultiType currentType;

	public AttackInstance moveHitByThisTurn;

	public MonsterHandler(final String key, final MonsterInstance monster) {
		this.key = key;
		this.monster = monster;
		statChangeLevel = Maps.newEnumMap(Stat.class);
		flags = Maps.newEnumMap(AttackFlag.class);
		counters = Maps.newHashMap();
		store = Maps.newHashMap();
		temporaryStatuses = Sets.newHashSet();

		resetStats();
	}

	public String getKey() {
		return key;
	}

	public MonsterInstance getMonster() {
		return monster;
	}

	public boolean getFlag(final AttackFlag flag) {
		if (flags.containsKey(flag)) {
			return flags.get(flag);
		}
		return false;
	}

	public void setFlag(final AttackFlag flag, final boolean value) {
		flags.put(flag, value);
	}

	public int getCounter(final String counter) {
		if (counters.containsKey(counter)) {
			return counters.get(counter);
		}
		return 0;
	}

	public void setCounter(final String counter, final Integer value) {
		if (value == null) {
			counters.remove(counter);
		} else {
			counters.put(counter, value);
		}
	}

	public Map<String, Object> getStore() {
		return store;
	}

	public boolean isUsingMultiTurnMove() {
		return multiTurnCount != null;
	}

	public void setMultiTurnCount(final int count) {
		multiTurnCount = count;
	}

	public void decrementMultiTurnCount() {
		multiTurnCount--;
		if (multiTurnCount == 0) {
			multiTurnCount = null;
		}
	}

	public Set<CustomStatus> getTemporaryStatuses() {
		return temporaryStatuses;
	}

	public AttackInstance getLastUsedMove() {
		return lastUsedMove;
	}

	public void setLastUsedMove(final AttackInstance attack) {
		lastUsedMove = attack;
	}

	public Integer getLastDamageInstance() {
		return lastDamageInstance;
	}

	public UpdateHealthEvent doDamage(final int damage) {
		final int previousHealth = monster.getCurrentHealth();
		monster.changeHealth(-damage);
		lastDamageInstance = damage;
		return new UpdateHealthEvent(key, previousHealth, monster.getCurrentHealth());
	}

	public MultiType getType() {
		if (currentType != null) {
			return currentType;
		}
		return monster.getBaseMonster().getType();
	}

	public void setType(final MultiType type) {
		currentType = type;
	}

	// Stats

	public int getAttack(final boolean isCrit) {
		if (statChangeLevel.get(Stat.ATTACK) > 0) {
			return Math.round(monster.getAttack() * (statChangeLevel.get(Stat.ATTACK) + 2) / 2);
		}
		if (statChangeLevel.get(Stat.ATTACK) < 0 && !isCrit) {
			return Math.round(monster.getAttack() * 2 / (-statChangeLevel.get(Stat.ATTACK) + 2));
		}
		return monster.getAttack();
	}

	public int getDefense(final boolean isCrit) {
		if (statChangeLevel.get(Stat.DEFENSE) > 0 && !isCrit) {
			return Math.round(monster.getDefense() * (statChangeLevel.get(Stat.DEFENSE) + 2) / 2);
		}
		if (statChangeLevel.get(Stat.DEFENSE) < 0) {
			return Math.round(monster.getDefense() * 2 / (-statChangeLevel.get(Stat.DEFENSE) + 2));
		}
		return monster.getDefense();
	}

	public int getSpecialAttack(final boolean isCrit) {
		if (statChangeLevel.get(Stat.SPECIAL_ATTACK) > 0) {
			return Math.round(monster.getSpecialAttack() * (statChangeLevel.get(Stat.SPECIAL_ATTACK) + 2) / 2);
		}
		if (statChangeLevel.get(Stat.SPECIAL_ATTACK) < 0 && !isCrit) {
			return Math.round(monster.getSpecialAttack() * 2 / (-statChangeLevel.get(Stat.SPECIAL_ATTACK) + 2));
		}
		return monster.getSpecialAttack();
	}

	public int getSpecialDefense(final boolean isCrit) {
		if (statChangeLevel.get(Stat.SPECIAL_DEFENSE) > 0 && !isCrit) {
			return Math.round(monster.getSpecialDefense() * (statChangeLevel.get(Stat.SPECIAL_DEFENSE) + 2) / 2);
		}
		if (statChangeLevel.get(Stat.SPECIAL_DEFENSE) < 0) {
			return Math.round(monster.getSpecialDefense() * 2 / (-statChangeLevel.get(Stat.SPECIAL_DEFENSE) + 2));
		}
		return monster.getSpecialDefense();
	}

	public int getSpeed() {
		if (statChangeLevel.get(Stat.SPEED) > 0) {
			return Math.round(monster.getSpeed() * (statChangeLevel.get(Stat.SPEED) + 2) / 2);
		}
		if (statChangeLevel.get(Stat.SPEED) < 0) {
			return Math.round(monster.getSpeed() * 2 / (statChangeLevel.get(Stat.SPEED) + 2));
		}
		return monster.getSpeed();
	}

	public int getEvasionLevel() {
		return statChangeLevel.get(Stat.EVASION);
	}

	public float getHitModifier(final int targetEvasionLevel) {
		final int modifier = statChangeLevel.get(Stat.ACCURACY) - targetEvasionLevel;
		if (modifier > 0) {
			return 1f * (modifier + 3f) / 3f;
		}
		if (modifier < 0) {
			return 1f * 3f / (modifier + 3f);
		}
		return 1f;
	}

	public boolean modify(final Stat stat, final int amount) {
		if (amount > 0) {
			if (statChangeLevel.get(stat) == 6) {
				return false;
			}
			statChangeLevel.put(stat, Math.min(statChangeLevel.get(stat) + amount, 6));
		} else {
			if (statChangeLevel.get(stat) == -6) {
				return false;
			}
			statChangeLevel.put(stat, Math.max(statChangeLevel.get(stat) + amount, -6));
		}
		return true;
	}

	public void resetStats() {
		statChangeLevel.clear();
		statChangeLevel.put(Stat.ATTACK, 0);
		statChangeLevel.put(Stat.DEFENSE, 0);
		statChangeLevel.put(Stat.SPEED, 0);
		statChangeLevel.put(Stat.SPECIAL_ATTACK, 0);
		statChangeLevel.put(Stat.SPECIAL_DEFENSE, 0);
		statChangeLevel.put(Stat.ACCURACY, 0);
		statChangeLevel.put(Stat.EVASION, 0);
	}

}
