package dev.dankins.javamon.battle.data.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.dankins.javamon.battle.data.attack.Attack;
import dev.dankins.javamon.battle.data.attack.effect.proxy.ProxyEffect;

public class AttackLoader extends SynchronousAssetLoader<Attack, AttackLoader.Parameters> {

	private final ObjectMapper mapper;

	public AttackLoader(final ObjectMapper mapper, final FileHandle directory) {
		super(new FileHandleResolver() {
			@Override
			public FileHandle resolve(final String attackName) {
				return directory.child(attackName.replace(' ', '_') + ".yaml");
			}
		});
		this.mapper = mapper;
		ProxyEffect.OBJECT_MAPPER = mapper;
		ProxyEffect.EFFECT_DIR = directory.child("effect");
	}

	@Override
	public Attack load(final AssetManager manager, final String fileName, final FileHandle file,
			final Parameters parameter) {
		try {
			return mapper.readValue(file.file(), Attack.class);
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

	static public class Parameters extends AssetLoaderParameters<Attack> {
	}
}
