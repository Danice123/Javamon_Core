package dev.dankins.javamon.display.screen;

import com.badlogic.gdx.assets.AssetManager;

import dev.dankins.javamon.display.RenderInfo;
import dev.dankins.javamon.display.screen.Screen;
import dev.dankins.javamon.logic.Key;

public class Loading extends Screen {

	private final AssetManager assets;

	public Loading(final AssetManager assets) {
		this.assets = assets;
	}

	@Override
	protected void init(final AssetManager assets) {
	}

	@Override
	protected void renderScreen(final RenderInfo ri, final float delta) {
		batch.begin();
		ri.font.setColor(0f, 0f, 0f, 1f);
		ri.font.draw(batch, "Loading... " + assets.getProgress() * 100, 10, 11 * ri.getScale());
		batch.end();
	}

	@Override
	protected void tickSelf(final float delta) {
		if (assets.update()) {
			synchronized (this) {
				notify();
			}
		}
	}

	@Override
	protected void handleKey(final Key key) {

	}
}
