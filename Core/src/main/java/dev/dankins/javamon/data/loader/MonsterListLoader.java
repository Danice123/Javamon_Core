package dev.dankins.javamon.data.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.dankins.javamon.data.monster.MonsterListImpl;

public class MonsterListLoader
		extends SynchronousAssetLoader<MonsterListImpl, MonsterListLoader.Parameters> {

	private final ObjectMapper mapper;

	public MonsterListLoader(final ObjectMapper mapper, final FileHandle directory) {
		super(new FileHandleResolver() {
			@Override
			public FileHandle resolve(final String monsterName) {
				return directory.child("__index.yaml");
			}
		});
		this.mapper = mapper;
	}

	@Override
	public MonsterListImpl load(final AssetManager assetManager, final String fileName,
			final FileHandle file, final Parameters parameter) {
		try {
			final MonsterListImpl monsterList = mapper.readValue(file.file(),
					MonsterListImpl.class);
			monsterList.initLoader(assetManager);
			return monsterList;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file,
			final Parameters parameter) {
		return null;
	}

	static public class Parameters extends AssetLoaderParameters<MonsterListImpl> {
	}
}
