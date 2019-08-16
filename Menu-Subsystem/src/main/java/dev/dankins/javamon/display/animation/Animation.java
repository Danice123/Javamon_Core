package dev.dankins.javamon.display.animation;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import dev.dankins.javamon.display.RenderInfo;

public interface Animation {

	void init(final AssetManager assets, final RenderInfo ri);

	TextureRegion getCurrentFrame();

	void next();

	void restart();

}
