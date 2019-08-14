package dev.dankins.javamon.display.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.dankins.javamon.display.AnimationData;
import dev.dankins.javamon.display.AnimationImpl;
import dev.dankins.javamon.display.animation.Animation;

public class AnimationLoader extends SynchronousAssetLoader<Animation, AnimationLoader.Parameters> {

	private final ObjectMapper mapper;

	public AnimationLoader(final ObjectMapper mapper) {
		super(new InternalFileHandleResolver());
		this.mapper = mapper;
	}

	@Override
	public Animation load(final AssetManager manager, final String fileName, final FileHandle file,
			final Parameters parameter) {
		try {
			final AnimationData data = mapper.readValue(file.child("data.yaml").file(),
					AnimationData.class);
			final Texture texture = new Texture(file.child("sprite.png"));
			return new AnimationImpl(texture, data);
		} catch (final IOException e) {
			System.out.println("Error reading animation file: " + file.path());
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

	static public class Parameters extends AssetLoaderParameters<Animation> {
	}
}
