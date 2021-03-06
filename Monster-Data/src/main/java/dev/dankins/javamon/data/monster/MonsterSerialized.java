package dev.dankins.javamon.data.monster;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.attack.AttackSerialized;
import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public class MonsterSerialized {

	public final String monster;
	public final String name;
	public final boolean customName;

	public final Gender gender;
	public final Map<Stat, Integer> IV;
	public final String originalTrainer;
	public final long idNumber;

	public final int level;
	public final int experience;
	public final Map<Stat, Integer> EV;
	public final List<AttackSerialized> attacks;

	public final int currentHealth;
	public final Status status;
	public final int sleepCounter;

	@JsonCreator
	public MonsterSerialized(@JsonProperty("monster") final String monster, @JsonProperty("name") final String name,
			@JsonProperty("customName") final boolean customName, @JsonProperty("gender") final Gender gender,
			@JsonProperty("IV") final Map<Stat, Integer> IV,
			@JsonProperty("originalTrainer") final String originalTrainer,
			@JsonProperty("idNumber") final long idNumber, @JsonProperty("level") final int level,
			@JsonProperty("experience") final int experience, @JsonProperty("EV") final Map<Stat, Integer> EV,
			@JsonProperty("attacks") final List<AttackSerialized> attacks,
			@JsonProperty("currentHealth") final int currentHealth, @JsonProperty("status") final Status status,
			@JsonProperty("sleepCounter") final int sleepCounter) {
		this.monster = monster;
		this.name = name;
		this.customName = customName;
		this.gender = gender;
		this.IV = IV;
		this.originalTrainer = originalTrainer;
		this.idNumber = idNumber;
		this.level = level;
		this.experience = experience;
		this.EV = EV;
		this.attacks = attacks;
		this.currentHealth = currentHealth;
		this.status = status;
		this.sleepCounter = sleepCounter;
	}

	public MonsterSerialized(final MonsterInstance monster) {
		this.monster = monster.getBaseMonster().getName();
		name = monster.getName();
		customName = monster.isNameCustom();
		gender = monster.getGender();
		IV = monster.getIVs();
		originalTrainer = monster.getOT();
		idNumber = monster.getId();
		level = monster.getLevel();
		experience = monster.getExp();
		EV = monster.getEVs();
		attacks = monster.getAttacks().stream().map(attack -> new AttackSerialized(attack))
				.collect(Collectors.toList());
		currentHealth = monster.getCurrentHealth();
		status = monster.getStatus();
		sleepCounter = monster.getSleepCounter();
	}
}
