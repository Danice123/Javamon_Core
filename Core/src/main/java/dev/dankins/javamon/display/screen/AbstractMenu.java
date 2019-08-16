package dev.dankins.javamon.display.screen;

import com.badlogic.gdx.assets.AssetManager;

import dev.dankins.javamon.display.RenderInfo;
import dev.dankins.javamon.logic.Key;

public class AbstractMenu extends Screen {

	static private final long INPUT_DELAY = 100;

	private final Menu menu;
	private long lastInputProcessed = System.currentTimeMillis();

	public AbstractMenu(final Screen parent, final Menu menu) {
		super(parent);
		this.menu = menu;
		renderBehind = menu.renderBehind();
	}

	public AbstractMenu(final Menu menu) {
		super();
		this.menu = menu;
		renderBehind = menu.renderBehind();
	}

	@Override
	protected void init(final AssetManager assets, final RenderInfo ri) {
		menu.init(assets, ri);
	}

	@Override
	protected void handleKey(final Key key) {
		if (System.currentTimeMillis() - lastInputProcessed >= INPUT_DELAY) {
			lastInputProcessed = System.currentTimeMillis();
			menu.handleMenuKey(key);
		}
	}

	@Override
	protected void renderScreen(final RenderHelper rh, final float delta) {
		menu.renderScreen(rh, delta);
	}

	@Override
	protected void tickSelf(final float delta) {
		menu.tickSelf(delta);
	}

}
