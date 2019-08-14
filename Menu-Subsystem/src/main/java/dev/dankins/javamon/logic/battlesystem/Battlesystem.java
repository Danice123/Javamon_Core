package dev.dankins.javamon.logic.battlesystem;

import dev.dankins.javamon.data.monster.instance.MonsterInstance;

public interface Battlesystem {

	MonsterInstance getPlayerMonster();

	MonsterInstance getEnemyMonster();

}
