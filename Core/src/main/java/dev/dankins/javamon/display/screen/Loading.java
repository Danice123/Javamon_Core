package dev.dankins.javamon.display.screen;

import com.badlogic.gdx.assets.AssetManager;

import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.display.RenderInfo;
import dev.dankins.javamon.logic.Key;

public class Loading extends Screen {

	private final AssetManager assets;

	public Loading(final AssetManager assets) {
		this.assets = assets;
	}

	@Override
	protected void init(final AssetManager assets, final RenderInfo ri) {
	}

	@Override
	protected void renderScreen(final RenderHelper rh, final float delta) {
		// rh.withSpriteBatch((batch) -> {
		// rh.ri.font.setColor(0f, 0f, 0f, 1f);
		// rh.text(rh.ri.font, "Loading... " + assets.getProgress() * 100, 10,
		// 11);
		// });
	}

	@Override
	protected void tickSelf(final float delta) {
		if (assets.update()) {
			ThreadUtils.notifyOnObject(this);
		}
	}

	@Override
	protected void handleKey(final Key key) {

	}
}
