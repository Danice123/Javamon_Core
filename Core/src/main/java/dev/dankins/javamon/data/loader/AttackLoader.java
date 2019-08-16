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

import dev.dankins.javamon.data.monster.attack.AttackBase;

public class AttackLoader extends SynchronousAssetLoader<AttackBase, AttackLoader.Parameters> {

	private final ObjectMapper mapper;

	public AttackLoader(final ObjectMapper mapper, final FileHandle directory) {
		super(new FileHandleResolver() {
			@Override
			public FileHandle resolve(final String attackName) {
				return directory.child(attackName.replace(' ', '_') + ".yaml");
			}
		});
		this.mapper = mapper;
	}

	@Override
	public AttackBase load(final AssetManager manager, final String fileName, final FileHandle file,
			final Parameters parameter) {
		try {
			return mapper.readValue(file.file(), AttackBase.class);
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Array<AssetDescriptor> getDependencies(final String fileName, final FileHandle file,
			final Parameters parameter) {
		return null;
	}

	static public class Parameters extends AssetLoaderParameters<AttackBase> {
	}
}
