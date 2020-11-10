package dev.dankins.javamon.battle.data.monster;

import java.util.Map;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.dankins.javamon.data.monster.MonsterSerialized;

public class MonsterList implements dev.dankins.javamon.data.monster.MonsterList {

	public final Map<Integer, String> monsterList;
	private final int totalMonsters;
	@JsonIgnore
	private AssetManager assetManager;

	@JsonCreator
	public MonsterList(@JsonProperty("monsterList") final Map<Integer, String> monsterList,
			@JsonProperty("totalMonsters") final int totalMonsters) {
		this.monsterList = monsterList;
		this.totalMonsters = totalMonsters;
	}

	public Monster getMonster(final String name) {
		return loadMonster(name);
	}

	@Override
	public Monster getMonster(final int number) {
		return loadMonster(monsterList.get(number));
	}

	private Monster loadMonster(final String name) {
		final AssetDescriptor<Monster> asset = new AssetDescriptor<Monster>(name, Monster.class);
		if (!assetManager.isLoaded(asset)) {
			assetManager.load(asset);
			assetManager.finishLoadingAsset(asset);
		}
		return assetManager.get(asset);
	}

	public void initLoader(final AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	@Override
	public int getTotalMonsters() {
		return totalMonsters;
	}

	@Override
	public dev.dankins.javamon.data.monster.instance.MonsterInstance generateWild(String monsterName, int level,
			String playerName, long playerId) {
		return new MonsterInstance(getMonster(monsterName), level, playerName, playerId);
	}

	@Override
	public dev.dankins.javamon.data.monster.instance.MonsterInstance loadMonster(MonsterSerialized monster) {
		return new MonsterInstance(getMonster(monster.monster), monster);
	}
}
