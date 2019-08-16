package dev.dankins.javamon.display.screen;

import com.badlogic.gdx.assets.AssetManager;

import dev.dankins.javamon.display.RenderInfo;
import dev.dankins.javamon.logic.Key;

public interface Menu {

	void init(AssetManager assets, RenderInfo ri);

	void renderScreen(RenderHelper rh, float delta);

	boolean renderBehind();

	void tickSelf(float delta);

	void handleMenuKey(Key key);

}
