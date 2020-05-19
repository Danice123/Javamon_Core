package dev.dankins.javamon.data.monster.instance;

import java.util.List;

import dev.dankins.javamon.data.monster.Monster;
import dev.dankins.javamon.data.monster.Stat;
import dev.dankins.javamon.data.monster.Status;
import dev.dankins.javamon.data.monster.attack.Attack;

public interface MonsterInstance {

	Monster getBaseMonster();

	String getName();

	String getId();

	String getOT();

	int getLevel();

	int getExp();

	int getExpToNextLevel();

	int getHealth();

	int getCurrentHealth();

	void changeHealth(int amount);

	float getCurrentHealthPercent();

	int getAttack();

	int getDefense();

	int getSpecialAttack();

	int getSpecialDefense();

	int getSpeed();

	int getIV(Stat stat);

	int getEV(Stat stat);

	Status getStatus();

	void setStatus(Status status);

	List<? extends Attack> getAttacks();

}
