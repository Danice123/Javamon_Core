package dev.dankins.javamon.data.monster;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public interface MonsterList {

	Monster getMonster(int monsterNumber);

	int getTotalMonsters();

	MonsterInstance generateWild(String monsterName, int level, String playerName, long playerId);

	MonsterInstance loadMonster(MonsterSerialized monster);

}
