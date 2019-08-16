package dev.dankins.javamon.display;

import java.util.Optional;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

import dev.dankins.javamon.display.screen.Screen;
import dev.dankins.javamon.logic.Game;

public class Display implements ApplicationListener {

	static private final String gamePath = "Red.yaml";

	private final Game game;
	// private FPSLogger fpsLogger;

	private Screen screen;
	private final RenderInfo renderInfo;

	public Display(final int width, final int height) {
		renderInfo = new RenderInfo(width, height);
		game = new Game(this, gamePath);
		// fpsLogger = new FPSLogger();
	}

	@Override
	public void render() {
		if (screen != null) {
			final float delta = Gdx.graphics.getDeltaTime();
			screen.init(game, renderInfo);
			screen.tick(delta);
			screen.render(renderInfo, delta);
			// fpsLogger.log();
		}
	}

	@Override
	public void create() {
		new Thread(game).start();
	}

	@Override
	public void dispose() {
		// TODO: Handle dispose
	}

	@Override
	public void pause() {
		// TODO: Handle pause
	}

	@Override
	public void resume() {
		// TODO: Handle resume
	}

	@Override
	public void resize(final int width, final int height) {
		// TODO: Handle resize
	}

	public Optional<Screen> getScreen() {
		return Optional.ofNullable(screen);
	}

	public void setScreen(final Screen screen) {
		if (this.screen != null) {
			this.screen.hide();
		}
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}
}
