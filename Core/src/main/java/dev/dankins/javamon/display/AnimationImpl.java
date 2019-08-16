package dev.dankins.javamon.display;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dev.dankins.javamon.display.animation.Animation;

public class AnimationImpl implements Animation {

	private final TextureRegion[] frames;
	private int currentFrame = 0;

	public AnimationImpl(final Texture texture, final AnimationData data) {
		final List<TextureRegion> frames = new ArrayList<TextureRegion>();

		for (int y = 0; y * data.y < texture.getHeight(); y++) {
			for (int x = 0; x * data.x < texture.getWidth(); x++) {
				final TextureRegion frame = new TextureRegion(texture, x * data.x, y * data.y,
						data.x, data.y);
				frames.add(frame);
			}
		}
		this.frames = frames.toArray(new TextureRegion[0]);
	}

	@Override
	public void init(final AssetManager assets, final RenderInfo ri) {

	}

	@Override
	public TextureRegion getCurrentFrame() {
		return frames[currentFrame];
	}

	@Override
	public void next() {
		if (currentFrame < frames.length) {
			currentFrame++;
		}
	}

	@Override
	public void restart() {
		currentFrame = 0;
	}

}
