package dev.dankins.javamon.data.loader;

import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import dev.dankins.javamon.data.monster.MonsterImpl;
import dev.dankins.javamon.data.monster.attack.AttackBase;

public class MonsterLoader extends SynchronousAssetLoader<MonsterImpl, MonsterLoader.Parameters> {

	private final ObjectMapper mapper;

	public MonsterLoader(final ObjectMapper mapper) {
		super(new MonsterFileResolver());
		this.mapper = mapper;
	}

	@Override
	public MonsterImpl load(final AssetManager assetManager, final String fileName, final FileHandle file, final Parameters parameter) {
		final MonsterImpl monster = loadMonster(file);
		for (final String attackName : getMonsterAttackNames(monster)) {
			monster.cachedAttacks.put(attackName, assetManager.get(attackName, AttackBase.class));
		}
		return monster;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file, final Parameters parameter) {
		final MonsterImpl monster = loadMonster(file);
		final List<AssetDescriptor> deps = Lists.newArrayList();
		for (final String attackName : getMonsterAttackNames(monster)) {
			deps.add(new AssetDescriptor<AttackBase>(attackName, AttackBase.class));
		}
		return Array.with(deps.toArray(new AssetDescriptor[0]));
	}

	private MonsterImpl loadMonster(final FileHandle file) {
		try {
			return mapper.readValue(file.file(), MonsterImpl.class);
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<String> getMonsterAttackNames(final MonsterImpl monster) {
		final List<String> attackNames = Lists.newArrayList();
		monster.learnableAttacks.values().stream().forEach(list -> attackNames.addAll(list));
		return attackNames;
	}

	static public class Parameters extends AssetLoaderParameters<MonsterImpl> {
	}

	public static class MonsterFileResolver implements FileHandleResolver {

		@Override
		public FileHandle resolve(final String monsterName) {
			return Gdx.files.internal("assets/db/monster/" + monsterName + ".yaml");
		}

	}
}
