package dev.dankins.javamon.display.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import dev.dankins.javamon.ThreadUtils;
import dev.dankins.javamon.display.RenderInfo;
import dev.dankins.javamon.logic.Game;
import dev.dankins.javamon.logic.Key;

public abstract class Screen {

	protected boolean disposeMe = false;
	protected boolean renderBehind = false;

	private SpriteBatch batch;
	private ShapeRenderer shape;

	private boolean isChild = false;
	// private Screen parent = null;
	private boolean hasChild = false;
	private Screen child = null;
	private boolean initialized = false;

	protected Screen() {
	}

	protected Screen(final Screen parent) {
		if (parent.hasChild) {
			throw new IllegalArgumentException("Parent has child already");
		}
		isChild = true;
		// this.parent = parent;
		parent.child = this;
		parent.hasChild = true;
	}

	public boolean hasChild() {
		return hasChild;
	}

	public Screen getChild() {
		return child;
	}

	public void init(final Game game, final RenderInfo ri) {
		if (!initialized) {
			batch = new SpriteBatch();
			shape = new ShapeRenderer();
			init(game.getAssets(), ri);
			initialized = true;
		}
		if (hasChild) {
			child.init(game, ri);
		}
	}

	protected abstract void init(AssetManager assets, RenderInfo ri);

	protected abstract void renderScreen(RenderHelper rh, float delta);

	protected abstract void tickSelf(float delta);

	protected abstract void handleKey(Key key);

	public void tick(final float delta) {
		tickSelf(delta);
		if (hasChild) {
			child.tick(delta);
			if (child.disposeMe) {
				hasChild = false;
				child.dispose();
				ThreadUtils.notifyOnObject(child);
				child = null;
			}
		}
		if (!isChild && disposeMe) {
			dispose();
		}
	}

	public void render(final RenderInfo ri, final float delta) {
		if (!initialized) {
			return;
		}
		if (hasChild) {
			if (child.renderBehind()) {
				renderScreen(new RenderHelper(ri, batch, shape), delta);
			} else {
				Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			}
			child.render(ri, delta);
		} else {
			if (!isChild) {
				// if (!renderBehind) {
				Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			}
			renderScreen(new RenderHelper(ri, batch, shape), delta);
		}
	}

	private boolean renderBehind() {
		// if (hasChild) {
		// return child.renderBehind();
		// }
		return renderBehind;
	}

	public void input(final Key key) {
		if (hasChild) {
			child.input(key);
			return;
		}
		handleKey(key);
	}

	public void resize(final int width, final int height) {
	}

	public void show() {
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void disposeMe() {
		disposeMe = true;
	}

	private void dispose() {
		batch.dispose();
		shape.dispose();
	}
}
