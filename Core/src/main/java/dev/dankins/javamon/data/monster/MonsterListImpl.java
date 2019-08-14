package dev.dankins.javamon.data.monster;

import java.util.Map;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MonsterListImpl implements MonsterList {

	public final Map<Integer, String> monsterList;
	private final int totalMonsters;
	@JsonIgnore
	private AssetManager assetManager;

	@JsonCreator
	public MonsterListImpl(@JsonProperty("monsterList") final Map<Integer, String> monsterList,
			@JsonProperty("totalMonsters") final int totalMonsters) {
		this.monsterList = monsterList;
		this.totalMonsters = totalMonsters;
	}

	public MonsterImpl getMonster(final String name) {
		return loadMonster(name);
	}

	@Override
	public MonsterImpl getMonster(final int number) {
		return loadMonster(monsterList.get(number));
	}

	private MonsterImpl loadMonster(final String name) {
		final AssetDescriptor<MonsterImpl> asset = new AssetDescriptor<MonsterImpl>(name,
				MonsterImpl.class);
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
}
