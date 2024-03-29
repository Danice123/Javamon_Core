package dev.dankins.javamon.battle.data.monster;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import dev.dankins.javamon.battle.RandomNumberGenerator;
import dev.dankins.javamon.battle.data.attack.Attack;
import dev.dankins.javamon.battle.data.attack.AttackInstance;
import dev.dankins.javamon.data.monster.Gender;
import dev.dankins.javamon.data.monster.Growth;
import dev.dankins.javamon.data.monster.MonsterSerialized;
import dev.dankins.javamon.data.monster.Stat;
import dev.dankins.javamon.data.monster.Status;

public class MonsterInstance implements dev.dankins.javamon.data.monster.instance.MonsterInstance {

	public final Monster monster;
	public final Gender gender;
	public final Map<Stat, Integer> IV;
	public final Map<Stat, Integer> EV;
	public final String originalTrainer;
	public final long idNumber;

	private String name;
	private boolean customName;

	private int level; // Generated from exp amount?
	private int experience;
	public final AttackSet attacks;

	private int currentHealth;
	private Status status = Status.NONE;
	public int sleepCounter;

	public MonsterInstance(final Monster baseMonster, final int level, final String playerName, final long playerId) {
		monster = baseMonster;
		name = baseMonster.name;
		originalTrainer = playerName;
		idNumber = playerId;
		customName = false;
		gender = Gender.Male;
		// TODO: Gender.generateGender(pokemon.getGenderRate());
		this.level = level;
		experience = Growth.getExpNeeded(baseMonster.growthRate, level);

		// generate IVs
		// TODO: Source random
		IV = new EnumMap<Stat, Integer>(Stat.class);
		final Random random = RandomNumberGenerator.random;
		IV.put(Stat.HEALTH, random.nextInt(32));
		IV.put(Stat.ATTACK, random.nextInt(32));
		IV.put(Stat.DEFENSE, random.nextInt(32));
		IV.put(Stat.SPECIAL_ATTACK, random.nextInt(32));
		IV.put(Stat.SPECIAL_DEFENSE, random.nextInt(32));
		IV.put(Stat.SPEED, random.nextInt(32));
		EV = new EnumMap<Stat, Integer>(Stat.class);
		EV.put(Stat.HEALTH, 0);
		EV.put(Stat.ATTACK, 0);
		EV.put(Stat.DEFENSE, 0);
		EV.put(Stat.SPECIAL_ATTACK, 0);
		EV.put(Stat.SPECIAL_DEFENSE, 0);
		EV.put(Stat.SPEED, 0);

		attacks = new AttackSet(Arrays.stream(baseMonster.getTopFourMoves(level)).filter(attack -> attack != null)
				.map(attack -> new AttackInstance(attack)).collect(Collectors.toList()));
		currentHealth = getHealth();
	}

	public MonsterInstance(final Monster baseMonster, final MonsterSerialized monster) {
		this.monster = baseMonster;

		gender = monster.gender;
		IV = monster.IV;
		EV = monster.EV;
		originalTrainer = monster.originalTrainer;
		idNumber = monster.idNumber;
		attacks = new AttackSet(monster.attacks.stream()
				.map(attack -> new AttackInstance(baseMonster.cachedAttacks.get(attack.attack), attack))
				.collect(Collectors.toList()));
		name = monster.name;
		customName = monster.customName;
		level = monster.level;
		experience = monster.experience;
		currentHealth = monster.currentHealth;
		status = monster.status;
		sleepCounter = monster.sleepCounter;
	}

	@Override
	public Monster getBaseMonster() {
		return monster;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isNameCustom() {
		return customName;
	}

	public void changeName(final String name) {
		this.name = name;
		customName = true;
	}

	@Override
	public long getId() {
		return idNumber;
	}

	@Override
	public String getOT() {
		return originalTrainer;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public int getExp() {
		return experience;
	}

	@Override
	public int getExpToNextLevel() {
		return Growth.getExpNeeded(monster.growthRate, level + 1) - experience;

	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public int getHealth() {
		return calcStats(IV.get(Stat.HEALTH), monster.getBaseHealth(), EV.get(Stat.HEALTH)) + 10 + level;
	}

	@Override
	public int getAttack() {
		// TODO add NATURES
		return calcStats(IV.get(Stat.ATTACK), monster.getBaseAttack(), EV.get(Stat.ATTACK)) + 5;
	}

	@Override
	public int getDefense() {
		return calcStats(IV.get(Stat.DEFENSE), monster.getBaseDefense(), EV.get(Stat.DEFENSE)) + 5;
	}

	@Override
	public int getSpecialAttack() {
		return calcStats(IV.get(Stat.SPECIAL_ATTACK), monster.getBaseSpecialAttack(), EV.get(Stat.SPECIAL_ATTACK)) + 5;
	}

	@Override
	public int getSpecialDefense() {
		return calcStats(IV.get(Stat.SPECIAL_DEFENSE), monster.getBaseSpecialDefense(), EV.get(Stat.SPECIAL_DEFENSE))
				+ 5;
	}

	@Override
	public int getSpeed() {
		return calcStats(IV.get(Stat.SPEED), monster.getBaseSpeed(), EV.get(Stat.SPEED)) + 5;
	}

	private int calcStats(final int iv, final int base, final int ev) {
		return (iv + 2 * base + ev / 4) * level / 100;
	}

	@Override
	public int getIV(final Stat stat) {
		return IV.get(stat);
	}

	@Override
	public Map<Stat, Integer> getIVs() {
		return IV;
	}

	@Override
	public int getEV(final Stat stat) {
		return EV.get(stat);
	}

	@Override
	public Map<Stat, Integer> getEVs() {
		return EV;
	}

	@Override
	public int getCurrentHealth() {
		return currentHealth;
	}

	@Override
	public float getCurrentHealthPercent() {
		return (float) currentHealth / (float) getHealth();
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(final Status status) {
		this.status = status;
	}

	@Override
	public List<AttackInstance> getAttacks() {
		return attacks;
	}

	@Override
	public void changeHealth(final int amount) {
		currentHealth += amount;
		if (currentHealth <= 0) {
			currentHealth = 0;
			status = Status.FAINTED;
		}
		if (currentHealth > getHealth()) {
			currentHealth = getHealth();
		}
	}

	public void changeStat(final Stat stat, final int amount) {
		EV.put(stat, EV.get(stat) + amount);
	}

	public void heal() {
		currentHealth = getHealth();
		for (final AttackInstance attack : attacks) {
			attack.currentUsage = attack.maxUsage;
		}
		status = Status.NONE;
	}

	@Override
	public int getSleepCounter() {
		return sleepCounter;
	}

	public int getExpDrop() {
		// handle exp scaling
		return monster.baseExp * level / 7;
	}

	public Collection<Levelup> addExp(final int exp) {
		if (level >= 100) {
			return Lists.newArrayList();
		}
		experience += exp;

		final Collection<Levelup> levelsGained = Lists.newArrayList();
		while (experience >= Growth.getExpNeeded(monster.growthRate, level + 1)) {
			level++;
			final Levelup levelup = new Levelup();
			levelup.level = level;
			if (monster.learnableAttacks.get(level) != null) {
				levelup.movesToLearn = monster.learnableAttacks.get(level).stream()
						.map(attackName -> monster.cachedAttacks.get(attackName)).collect(Collectors.toList());
			} else {
				levelup.movesToLearn = Lists.newArrayList();
			}
			levelsGained.add(levelup);
		}
		return levelsGained;
	}

	public class Levelup {

		public int level;
		public List<Attack> movesToLearn;
	}

}
