package dev.dankins.javamon.display.animation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Animation {

	TextureRegion getCurrentFrame();

	void next();

	void restart();

}
