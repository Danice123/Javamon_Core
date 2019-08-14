package dev.dankins.javamon.display.screen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import dev.dankins.javamon.display.RenderInfo;
import dev.dankins.javamon.logic.Key;

public interface Menu {

	void init(AssetManager assets);

	void renderScreen(RenderInfo ri, SpriteBatch batch, ShapeRenderer shape, float delta);

	boolean renderBehind();

	void tickSelf(float delta);

	void handleMenuKey(Key key);

}
